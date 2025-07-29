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

    private static final String DB_URL = "jdbc:mysql://test.bjpygh.com/course";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "P@gh202o";
    private static final String AUTHOR = "wengjk";
    private static final String OUTPUT_DIR = "/Users/wengjk/project/IdeaProjects/mybatisModelGen/src/main/java/";
    private static final String MAPPER_XML_PATH = "/Users/wengjk/project/IdeaProjects/mybatisModelGen/src/main/resources/mapper";

    // 父包名
    private static final String PARENT_PACKAGE = "com.bjpygh";
    // 模块名
    private static final String MODULE_NAME = "course";
    // 实体类包名
    private static final String MODEL_PO = "model.po";
    // Mapper接口包名
    private static final String MAPPER = "mapper";
    // Service接口包名
    private static final String SERVICE = "service";
    // Service实现类包名
    private static final String SERVICE_IMPL = "service.impl";
    // mapper文件
    private static final String MAPPERS = "mappers";


    // 表名
    private static final String[] TABLES_TO_INCLUDE = {"graduation_project", "graduation_project_category", "question", "question_bank", "question_category", "question_practice_record", "textbook", "textbook_answer", "textbook_category", "textbook_feedback", "textbook_user_requirement", "user_resource_collection"};

    public static void main(String[] args) {
        try {
            System.out.println("开始生成代码...");

            FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                    .globalConfig(builder -> {
                        builder.author(AUTHOR) // 设置作者
                                // 开启 swagger 模式
                                // .enableSwagger()
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
                                .entity(MODEL_PO)
                                .mapper(MAPPER)
                                .service(SERVICE)
                                .serviceImpl(SERVICE_IMPL)
                                .xml(MAPPERS)
                                .pathInfo(Collections.singletonMap(OutputFile.xml, MAPPER_XML_PATH)); // 设置mapperXml生成路径
                    })
                    .strategyConfig(builder ->
                            builder.addInclude(TABLES_TO_INCLUDE) // 设置需要生成的表名
                                    .addTablePrefix("t_", "c_") // 设置过滤表前缀
                                    .entityBuilder() // 启用实体类builder
                                    .enableLombok() // 启用Lombok
                                    // .enableTableFieldAnnotation() // 启用字段注解
                                    .controllerBuilder() // 启用控制器builder
                                    .enableRestStyle() // 启用REST风格
                    )
                    .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板
                    .execute();

            System.out.println("代码生成完成！");
        } catch (Exception e) {
            System.err.println("生成代码时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
