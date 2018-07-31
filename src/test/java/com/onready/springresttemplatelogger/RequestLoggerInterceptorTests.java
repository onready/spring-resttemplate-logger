package com.onready.springresttemplatelogger;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class RequestLoggerInterceptorTests {

  private final RestTemplate restTemplate = new RestTemplate();

  @Test
  public void intercept_withSuccessfullyPostRequest_logsRequestAndResponse() {
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>(1);
    interceptors.add(new RequestLoggerInterceptor());
    restTemplate.setInterceptors(interceptors);
    final String testUri = "http://test-uri.com";
    final String responseBody = "Response";
    final String requestBody = "Request";
    MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
    mockRestServiceServer
            .expect(requestTo(testUri))
            .andRespond(withSuccess(responseBody, MediaType.TEXT_PLAIN));

    restTemplate.postForObject(testUri, requestBody, String.class);
  }

}
