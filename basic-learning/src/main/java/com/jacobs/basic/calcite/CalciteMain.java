package com.jacobs.basic.calcite;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.volcano.VolcanoPlanner;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParser.Config;
import org.apache.calcite.sql2rel.SqlToRelConverter;

/**
 * @author lichao
 * @date 2019/04/03
 */
public class CalciteMain {

    public static void main(String[] args) throws SqlParseException {
        // Convert query to SqlNode
        String sql = "select price from transactions";
        //构建config，有默认的构造也有带参数的config   返回ConfigImpl   有默认的实现
        Config config = SqlParser.configBuilder()
                                 .build();
        //根据config构建sql解析器   reader是SourceStringReader,String流
        SqlParser parser = SqlParser.create(sql, config);
        //构建parse tree
        SqlNode node = parser.parseQuery();
        //        //VolcanoPlanner会根据动态算法优化查询   cost factory
        //        VolcanoPlanner planner = new VolcanoPlanner();
        //        //行表达式代理   一些常见的字符值会被缓存
        //        RexBuilder rexBuilder = createRexBuilder();
        //        //优化查询期间的环境     RelOptPlanner会把相关的表达式转换成语义上的表达式
        //        RelOptCluster cluster = RelOptCluster.create(planner, rexBuilder);
        //        //创建converter
        //        SqlToRelConverter converter = new SqlToRelConverter();
        //        //通常root为tree的根节点
        //        RelRoot root = converter.convertQuery(node, false, true);
        System.out.println("sql parse finished...");
    }
}
