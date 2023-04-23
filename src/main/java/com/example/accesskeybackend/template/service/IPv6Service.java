package com.example.accesskeybackend.template.service;

import com.example.accesskeybackend.template.dto.IPv6Dto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.*;
import java.util.Arrays;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Log4j2
public class IPv6Service {

    private final static String regExForIPv6 = "(" +
            "(\\p{XDigit}{1,4}:){7}\\p{XDigit}{1,4}|" +
            "(\\p{XDigit}{1,4}:){1,7}:|" +
            "\\p{XDigit}{1,4}:){1,6}(:(\\p{XDigit}{1,4}))" +
            "(\\p{XDigit}{1,4}:){1,5}(:(\\p{XDigit}{1,4})){1,2}|" +
            "(\\p{XDigit}{1,4}:){1,4}(:(\\p{XDigit}{1,4}){1,3}|" +
            "(\\p{XDigit}{1,4}:){1,3}(:(\\p{XDigit}{1,4})){1,4}|" +
            "(\\p{XDigit}{1,4}:){1,2}(:(\\p{XDigit}{1,4})){1,5}|" +
            "(\\p{XDigit}{1,4}:)(:(\\p{XDigit}{1,4})){1,6}|" +
            "(:((:(\\p{XDigit}{1,4})){1,7})|:)|" +
            "::(ffff(:0{1,4})?:)?((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])|" +
            "((\\p{XDigit}{1,4}):){1,4}:((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])|" +
            ")";

    public ResponseEntity<IPv6Dto> checkUrlForIPv6Support(String siteUrl) {

        try {
            var uri = new URI(siteUrl);
            var addresses = Inet6Address.getAllByName(uri.getHost());

            var pattern = Pattern.compile(regExForIPv6);

            var ipv6 = Arrays.stream(addresses)
                    .map(InetAddress::getHostAddress)
                    .filter(ip -> pattern.matcher(ip).find())
                    .findFirst().orElse(null);

            return new ResponseEntity<>(new IPv6Dto(ipv6 != null), HttpStatus.OK);
        } catch (URISyntaxException e) {
            log.error(String.format("String parsing error: (%s)", siteUrl));
            throw new RuntimeException(e.getMessage());
        } catch (UnknownHostException e) {
            log.error(String.format("Host %s unknown", siteUrl));
            throw new RuntimeException(e.getMessage());
        }
    }
}