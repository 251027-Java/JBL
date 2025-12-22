package com.example.eureka_client1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class EurekaClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient2Application.class, args);
    }

    // https://github.com/spring-cloud/spring-cloud-netflix/issues/4380#issuecomment-2511465791
    // https://stackoverflow.com/a/76920404
    @Bean("client2Service")
    @LoadBalanced
    RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder().baseUrl("http://eureka-client-1");
    }

    @Bean
    @Primary
    RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}

@RestController
class Controller {

    final RestClient.Builder restClientBuilder;

    public Controller(@Qualifier("client2Service") RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from client 2";
    }

    @GetMapping("/hello-other")
    public String helloa() {
        return restClientBuilder.build().get().uri("/hello").retrieve().body(String.class);
    }
}
