package com.jacobs;

import com.alibaba.fastjson.JSON;
import com.mobvoi.data.CommonParameterSource;
import com.mobvoi.data.CommonRowMapper;
import com.mobvoi.data.Sqls;
import com.mobvoi.data.base.CommonJdbcTemplateFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

  private NamedParameterJdbcTemplate getWriteJdbcTemplate() {
    return new NamedParameterJdbcTemplate(this.commonJdbcTemplateFactory.getWriteJdbcTemplate());
  }

  String insertSql = Sqls.getInsertSql(User.class, "user");


  @Test
  public void testJdbc() {
    System.out.println(JSON.toJSONString(commonJdbcTemplateFactory.getReadJdbcTemplate()
        .query("select * from user", CommonRowMapper.getDefault(User.class))));
  }

  @Test
  public void testInsertWithAnnotation() {
    User user = User.builder()
        .eid("300000000")
        .email("wanglu@tju.edu.cn")
        .password("123456")
        .isRegistered(1)
        .build();
    System.out
        .println(getWriteJdbcTemplate().update(insertSql, CommonParameterSource.getDefault(user)));
  }
}