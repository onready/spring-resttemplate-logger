package com.onready.springresttemplatelogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RequestLoggerInterceptor implements ClientHttpRequestInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(RequestLoggerInterceptor.class);

  public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
    logger.debug("Request URI: {}", httpRequest.getURI().toString());
    logger.debug("Request headers: {}", httpRequest.getHeaders().toString());
    logger.debug("Request method: {}", httpRequest.getMethod().toString());
    logger.debug("Request body: {}", new String(bytes, "UTF-8"));

    ClientHttpResponse clientHttpResponse = clientHttpRequestExecution.execute(httpRequest, bytes);

    logger.debug("Response status code: {}", clientHttpResponse.getStatusCode());
    logger.debug("Response headers: {}", clientHttpResponse.getHeaders().toString());
    logger.debug("Response body: {}", transformResponseBodyToString(clientHttpResponse.getBody()));

    return clientHttpResponse;
  }

  private String transformResponseBodyToString(InputStream inputStream) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
    int i;
    while ((i = reader.read()) != -1) {
      stringBuilder.append((char) i);
    }

    return stringBuilder.toString();
  }

}
