package com.example.testplatform.service;

import com.example.testplatform.mapper.PrePostMapper;
import com.example.testplatform.model.PrePostActionWrapper;
import com.example.testplatform.model.prepostactiontype.PrePostAction;
import com.example.testplatform.model.prepostactiontype.PrePostActionType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PrePostActionService {
    @Autowired
    PrePostMapper prePostMapper;


    /**
     * 新增前置后置动作
     * @param prePostActionWrapper
     * @return
     */
    @Transactional
    public ResponseEntity<?> createPrePostAction(PrePostActionWrapper prePostActionWrapper){
        PrePostActionType type = prePostActionWrapper.getActionType();
        PrePostAction action = prePostActionWrapper.getAction();
        switch (type){
            case SQL:
                prePostMapper.insertPrePostAction(prePostActionWrapper);
                action.setActionId(prePostActionWrapper.getId());
                prePostMapper.insertSqlPrePostAction(action);

        }
        return null;
    }


}
