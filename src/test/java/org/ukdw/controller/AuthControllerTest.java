package org.ukdw.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.ukdw.AppMain;
import org.ukdw.entity.UserAccountEntity;

import java.util.List;

import java.net.http.HttpHeaders;

@SpringBootTest(classes = AppMain.class)
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {
//    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private static final String API_ENDPOINT = "http://localhost:8080";
    private RestTemplate restTemplate;
    @BeforeEach
    public void beforeTest() {
        restTemplate = new RestTemplate();
//        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        // restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
    }


    @Test
    void givenEmailAndPasswordIsValidWhenSignin_thenReturnOk() throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<?> response = testRestTemplate.getForEntity(API_ENDPOINT + "/hello", List.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}