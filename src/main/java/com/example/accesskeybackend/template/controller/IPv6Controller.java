package com.example.accesskeybackend.template.controller;

import com.example.accesskeybackend.template.dto.IPv6Dto;
import com.example.accesskeybackend.template.service.IPv6Service;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/web")
@AllArgsConstructor
public class IPv6Controller {

    private final IPv6Service iPv6Service;

    @GetMapping("checkIpv6Support")
    public ResponseEntity<IPv6Dto> checkUrlForSupportIPv6(@RequestParam final String siteUrl) {
        return iPv6Service.checkUrlForIPv6Support(siteUrl);
    }
}
