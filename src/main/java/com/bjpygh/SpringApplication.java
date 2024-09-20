package com.bjpygh;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * @author wengjk
 * @date 2024年09月20日
 * @className SpringApplication
 * @describe 基于mybatis模版引擎生成代码
 */
public class SpringApplication {

    private static final String DB_URL = "jdbc:mysql://test.bjpygh.com/pygh";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "P@gh202o";
    private static final String AUTHOR = "wengjk";
    private static final String OUTPUT_DIR = "/Users/piaoyangguohai/IdeaProjects/mybatisModelGen/src/main/java/";
    private static final String MAPPER_XML_PATH = "/Users/piaoyangguohai/IdeaProjects/mybatisModelGen/src/main/resources/mapper";

    // 父包名
    private static final String PARENT_PACKAGE = "com.bjpygh";
    // 模块名
    private static final String MODULE_NAME = "demandApply";

    // 表名
    private static final String[] TABLES_TO_INCLUDE = {"da_offline_invitation", "da_offline_official_people"};

    public static void main(String[] args) {
        try {
            System.out.println("开始生成代码...");

            FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                    .globalConfig(builder -> {
                        builder.author(AUTHOR) // 设置作者
                                .enableSwagger() // 开启 swagger 模式
                                .outputDir(OUTPUT_DIR);
                    })
                    .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                        if (typeCode == Types.SMALLINT) {
                            return DbColumnType.INTEGER; // 自定义类型转换
                        }
                        return typeRegistry.getColumnType(metaInfo);
                    }))
                    .packageConfig(builder -> {
                        builder.parent(PARENT_PACKAGE) // 设置父包名
                                .moduleName(MODULE_NAME) // 设置父包模块名
                                .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_XML_PATH)); // 设置mapperXml生成路径
                    })
                    .strategyConfig(builder -> {
                        builder.addInclude(TABLES_TO_INCLUDE); // 设置需要生成的表名
                    })
                    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板
                    .execute();

            System.out.println("代码生成完成！");
        } catch (Exception e) {
            System.err.println("生成代码时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
