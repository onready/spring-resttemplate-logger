package com.onready.springresttemplatelogger;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class RequestLoggerInterceptorTests {

  private final RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

  @Test
  public void intercept_withSuccessfullyPostRequest_logsRequestAndResponse() {
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>(1);
    interceptors.add(new RequestLoggerInterceptor());
    restTemplate.setInterceptors(interceptors);
    final String testUri = "http://test-uri.com";
    final String responseBody = "Response";
    final String requestBody = "Request";
    MockRestServiceServer mockRestServiceServer = MockRestServiceServer
            .bindTo(restTemplate)
            .bufferContent()
            .build();
    mockRestServiceServer
            .expect(requestTo(testUri))
            .andRespond(withSuccess(responseBody, MediaType.TEXT_PLAIN));

    String response = restTemplate.postForObject(testUri, requestBody, String.class);

    assertEquals(responseBody, response);
  }

}
