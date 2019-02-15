package com.example.testplatform.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiEntityRequest {
    private Long id;

    private String suite;

    private String testmodule;

    private String descs;

    private String testname;

    private HttpMethod method;

    private URI url;

    private Map<String, String> headers;

    private Map<String, String> parameters;

    private Map<String, ?> expected;

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getTestmodule() {
        return testmodule;
    }

    public void setTestmodule(String testModule) {
        this.testmodule = testModule;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public Map<String, ?> getExpected() {
        return expected;
    }

    public void setExpected(Map<String, ?> expected) {
        this.expected = expected;
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
}
