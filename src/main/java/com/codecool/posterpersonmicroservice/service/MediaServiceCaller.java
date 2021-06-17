package com.codecool.posterpersonmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MediaServiceCaller {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${mediaservice.url}")
    private String baseUrl;

    public ResponseEntity<Long> submitImage(long requesterId, MultipartFile file) {
        HttpHeaders httpHeaders = new HttpHeaders();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForEntity(baseUrl + "/" + requesterId, requestEntity, Long.class);
    }
}
