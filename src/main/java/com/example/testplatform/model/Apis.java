package com.example.testplatform.model;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

public class Apis {
    private Long id;

    private String testname;

    private HttpMethod method;

    private URI url;

    private Map<String, String> headers;

    private Map<String, String> parameters;

    private Map<String, ?> expected;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, ?> getExpected() {
        return expected;
    }

    public void setExpected(Map<String, ?> expected) {
        this.expected = expected;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
