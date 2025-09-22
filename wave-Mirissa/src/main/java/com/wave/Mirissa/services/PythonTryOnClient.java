package com.wave.Mirissa.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.File;

@Service
public class PythonTryOnClient {

    private final WebClient webClient;


    public PythonTryOnClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(20 * 1024 * 1024)) // 20 MB
                .build();

        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:5000")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
                .exchangeStrategies(strategies)
                .build();
    }

    public String compose(File userImageFile, String necklaceUrl) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("userImage", new FileSystemResource(userImageFile));
        formData.add("necklaceUrl", necklaceUrl);

        return webClient.post()
                .uri("/tryon")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(formData))
                .retrieve()
                .bodyToMono(String.class)   // ✅ JSON string
                .block();
    }
}
