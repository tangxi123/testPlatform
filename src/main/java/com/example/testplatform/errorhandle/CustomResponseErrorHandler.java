package com.example.testplatform.errorhandle;

import com.example.testplatform.exception.CustomException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.xml.sax.ErrorHandler;
import sun.nio.ch.IOUtil;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//继承spring boot默认的错误处理
public class CustomResponseErrorHandler extends DefaultResponseErrorHandler{

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String body = convertStreamToString(response.getBody());
//        try{
//            super.handleError(response);
//        }catch (RestClientException scx){
//            throw new CustomException(scx.getMessage(),scx,body);
//        }
        throw new CustomException(response.getStatusText(),body);

    }

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


}


