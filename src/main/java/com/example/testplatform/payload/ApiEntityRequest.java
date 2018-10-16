package com.example.testplatform.payload;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URL;
import java.util.Map;

public class ApiEntityRequest {

    private String testname;

    private HttpMethod method;

    private URI url;

    private Map<String, String> headers;

    private Object body;

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

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
