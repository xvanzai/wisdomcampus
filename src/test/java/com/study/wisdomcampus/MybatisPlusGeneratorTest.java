package com.study.wisdomcampus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class MybatisPlusGeneratorTest {

    @Test
    public void mybatisPlusGeneratorTest() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/zhxy_db",
                        "root", "root")
                .globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:\\JavaStudy\\SpringBoot\\wisdomcampus\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.study") // 设置父包名
                            .moduleName("wisdomcampus") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\JavaStudy\\SpringBoot\\wisdomcampus\\src\\main\\resources")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tb_admin", "tb_clazz", "tb_grade", "tb_student", "tb_teacher") // 设置需要生成的表名
                            .addTablePrefix("tb_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
