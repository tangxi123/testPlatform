package com.example.testplatform.controller;
import com.example.testplatform.model.ParameterWrapper;
import com.example.testplatform.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parameter")
public class ParameterController {
    @Autowired
    ParameterService parameterService;

    @PostMapping("/create")
    public ResponseEntity<ParameterWrapper> createParameter(@RequestBody  ParameterWrapper parameterWrapper){
        return parameterService.createParameter(parameterWrapper);
    }


}
