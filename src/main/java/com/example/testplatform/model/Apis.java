package com.example.testplatform.model;

import com.example.testplatform.common.onCreate;
import com.example.testplatform.common.onUpdate;
import com.example.testplatform.model.checkpoint.CheckPoint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.tangxi.testplatform.api.testcase.util.JacksonUtil;

import javax.validation.constraints.*;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Apis {

    @Null(groups = onCreate.class)
    @NotNull(groups = onUpdate.class)
    private Long id;

    @NotBlank(message = "suite字段不能为空")
    @Size(min=1,max = 100,message = "suite字段个数应大于1小于100")
    private String suite;

    @NotBlank(message = "testModule字段不能为空")
    @Size(min=1,max = 100,message = "testModule字段个数应大于1小于100")
    private String testModule;

    @NotBlank(message = "descs字段不能为空")
    @Size(min=1,max = 500,message = "descs字段个数应大于1小于100")
    private String descs;

    @NotBlank(message = "testName字段不能为空")
    @Size(min=1,max = 100,message = "testName字段个数应大于1小于100")
    private String testName;


    private HttpMethod method;


    private URI url;


    private Map<String, String> headers;

    private String parameters;

    //前置动作列表
    private List<String> preActionNames;


    //检查点
    private List<CheckPoint> checkPoints;

    //后置动作
    private List<String> postActionNames;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Override
    public String toString(){
        return JacksonUtil.toJson(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getTestModule() {
        return testModule;
    }

    public void setTestModule(String testModule) {
        this.testModule = testModule;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
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

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
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

    public void setPreActionNames(List<String> preActionNames) {
        this.preActionNames = preActionNames;
    }

    public List<String> getPostActionNames() {
        return postActionNames;
    }

    public void setPostActionNames(List<String> postActionNames) {
        this.postActionNames = postActionNames;
    }

    public List<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(List<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public List<String> getPreActionNames() {
        return preActionNames;
    }

    public void setPreActionNameList(List<String> preActionNameList) {
        this.preActionNames = preActionNames;
    }
}
