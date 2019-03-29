package com.example.testplatform.controller;

import com.example.testplatform.model.PrePostActionWrapper;
import com.example.testplatform.service.PrePostActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prepostaction")
public class PrePostActionController {

    @Autowired
    PrePostActionService prePostActionService;

    /**
     * 新增前置后置动作
     * @param prePostActionWrapper
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createPrePostAction(@RequestBody PrePostActionWrapper prePostActionWrapper){
        return prePostActionService.createPrePostAction(prePostActionWrapper);
    }
}
