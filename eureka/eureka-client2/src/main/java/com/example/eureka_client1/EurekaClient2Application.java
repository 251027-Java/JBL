package com.example.eureka_client1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class EurekaClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient2Application.class, args);
    }

    @Bean
    RestClient restClient() {
        return RestClient.create();
    }
}

@RestController
class Controller {

    private final RestClient restClient;
    private final DiscoveryClient  discoveryClient;

    public Controller(RestClient restClient, DiscoveryClient discoveryClient) {
        this.restClient = restClient;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from client 2";
    }

    @GetMapping("/helloa")
    public String helloa() {
        ServiceInstance instance =
                discoveryClient.getInstances("eureka-client-1").getFirst();

        String response =
                restClient.get().uri(instance.getUri() + "/hello").retrieve().body(String.class);

        return response;
    }
}
