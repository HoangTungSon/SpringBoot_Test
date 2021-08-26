package com.example.demo5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getHello() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/", String.class);
        assertThat(response.getBody()).isEqualTo("Hello world!!!");
    }
}
