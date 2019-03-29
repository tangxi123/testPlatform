package com.example.testplatform.test;

import com.example.testplatform.mapper.PrePostMapper;
import com.example.testplatform.mapper.TCasesMapper;
import com.example.testplatform.model.PrePostActionWrapper;
import com.example.testplatform.model.prepostactiontype.PrePostActionType;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class TestResultListener extends TestListenerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(TestResultListener.class);
    private int status;
    private int id;
    private Date startDateTime;
    private Date endDateTime;
    private Long testTime;

    @Override
    public void onTestFailure(ITestResult result){
        LOG.trace("测试用例失败");
        status = result.getStatus();
        id = Integer.parseInt((String)result.getParameters()[0]);
        startDateTime = result.getTestContext().getStartDate();
        endDateTime = result.getTestContext().getEndDate();
        testTime = result.getEndMillis()-result.getStartMillis();
        LOG.trace("{}",status);
        LOG.trace("{}", id);
        LOG.trace("{}",startDateTime);
        LOG.trace("{}",endDateTime);
        LOG.trace("{}",testTime);
        insertStatus(status,startDateTime,id);


    }

    @Override
    public void onTestSuccess(ITestResult result){
        LOG.trace("测试用例通过");
    }

    @Override
    public void onTestSkipped(ITestResult result){
        LOG.trace("测试用例跳过");
    }

    private void insertStatus(int status,Date startTestTime, int id ){
        SqlSessionFactory sqlSessionFactory = createDataSourceConnection();
        SqlSession session = sqlSessionFactory.openSession();
        try{
            TCasesMapper tCaseMapper = session.getMapper(TCasesMapper.class);
            tCaseMapper.updateTestCaseTestResult(id,status,startTestTime);
        }catch (Exception e){
            LOG.error(e.getMessage(),e);
            throw e;
        }finally {
            session.commit();
            session.close();
        }
    }

    private SqlSessionFactory createDataSourceConnection(){
        LOG.trace(">> TestCaseExecution.createDataSourceConnection()");
        LOG.trace("创建数据库连接");
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-conf.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;

    }
    private DataSource createDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/tplatform");
        dataSource.setUsername("root");
        dataSource.setPassword("tx123456");
        return dataSource;
    }


}
