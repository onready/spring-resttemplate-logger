# Spring Rest Template Logger

A simple way to log Spring RestTemplate requests and responses.

## Requirements

- Spring web 4+
- Slf4j 1.7.7+

## How it works
This project includes a new RestTemplate interceptor (RequestLoggerInterceptor) that logs the request and response. For this, you have to add this interceptor to your RestTemplate instance. Also, you will need to change the default request factory for a BufferingClientHttpRequestFactory, this is necessary to log de response body.

## How to use

#### Without Spring Boot

Add the dependency to `pom.xml`

```xml
<dependency>
   <groupId>com.onready</groupId>
   <artifactId>spring-resttemplate-logger</artifactId>
   <version>1.0.0</version>
</dependency>
```

Then, configure your RestTemplate instance: 

```java
RestTemplate restTemplate = new RestTemplate();
restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
if (CollectionUtils.isEmpty(interceptors)) {
  interceptors = new ArrayList<ClientHttpRequestInterceptor>(1);
}
interceptors.add(new RequestLoggerInterceptor());
restTemplate.setInterceptors(interceptors);
``` 

#### With Spring Boot
There is a Spring boot starter for this project

```xml
<dependency>
   <groupId>com.onready</groupId>
   <artifactId>spring-resttemplate-logger-starter</artifactId>
   <version>1.0.0</version>
</dependency>
```

If you have a RestTemplate bean, it will autoconfigure it with the interceptor and the request factory.


## Important
In order to view the logs, the package `com.onready.springrequestlogger` must be log in debug mode. 