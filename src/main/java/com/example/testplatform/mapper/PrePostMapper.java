package com.example.testplatform.mapper;

import com.example.testplatform.model.ParameterWrapper;
import com.example.testplatform.model.PrePostActionWrapper;
import com.example.testplatform.model.prepostactiontype.PrePostAction;
import com.example.testplatform.model.prepostactiontype.SqlPrePostAction;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PrePostMapper {
    //插入prepostaction主表数据
    int insertPrePostAction(PrePostActionWrapper prePostActionWrapper);

    //插入类型为Sql的prepostactionsql从表数据
    int insertSqlPrePostAction(PrePostAction prePostAction);

    //根据Id查询prepostaction主表数据
    PrePostActionWrapper selectPrePostActionWrapperById(int id);

    //根据参数名字查询prepostaction主表数据
    PrePostActionWrapper selectPrePostActionWrapperByName(String name);
}
