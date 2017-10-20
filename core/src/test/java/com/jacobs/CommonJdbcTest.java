package com.jacobs;

import com.alibaba.fastjson.JSON;
import com.mobvoi.data.CommonRowMapper;
import com.mobvoi.data.base.CommonJdbcTemplateFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lichao on 2017/10/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CommonJdbcTest {

  @Autowired
  @Qualifier("commonJdbcTemplateFactory")
  CommonJdbcTemplateFactory commonJdbcTemplateFactory;
//  @Autowired
//  @Qualifier("bussinessJdbcTemplateFactory")
//  CommonJdbcTemplateFactory bussinessTemplateFactory;


  @Test
  public void testJdbc() {
    System.out.println(JSON.toJSONString(commonJdbcTemplateFactory.getReadJdbcTemplate()
        .query("select * from user", CommonRowMapper.getDefault(User.class))));
  }
}
