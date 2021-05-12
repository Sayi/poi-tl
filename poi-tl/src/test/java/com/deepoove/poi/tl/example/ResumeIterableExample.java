package com.deepoove.poi.tl.example;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.tl.render.ResumeDataV2;

@DisplayName("Foreach resume example")
public class ResumeIterableExample {

    ResumeDataV2 datas = new ResumeDataV2();

    @BeforeEach
    public void init() {
        datas.setPortrait(Pictures.ofLocal("src/test/resources/sayi.png").size(100, 100).create());
        datas.setName("卅一");
        datas.setJob("BUG工程师");
        datas.setPhone("18080809090");
        datas.setSex("男");
        datas.setProvince("杭州");
        datas.setBirthday("2000.08.19");
        datas.setEmail("adasai90@gmail.com");
        datas.setAddress("浙江省杭州市西湖区古荡1号");
        datas.setEnglish("CET6 620");
        datas.setUniversity("美国斯坦福大学");
        datas.setProfession("生命工程");
        datas.setEducation("学士");
        datas.setRank("班级排名 1/36");
        datas.setHobbies("音乐、画画、乒乓球、旅游、读书\nhttps://github.com/Sayi");

        // 技术栈部分
        TextRenderData textRenderData = new TextRenderData("SpringBoot、SprigCloud、Mybatis");
        Style style = Style.builder().buildFontSize(10).buildColor("7F7F7F").buildFontFamily("微软雅黑").build();
        textRenderData.setStyle(style);
        datas.setStack(Numberings.of(textRenderData, textRenderData, textRenderData).create());

        /*
         * {{?experiences}} {{company}} {{department}} {{time}} {{position}}
         * {{*responsibility}} {{/experiences}}
         */
        List<ExperienceData> experiences = new ArrayList<ExperienceData>();
        ExperienceData data0 = new ExperienceData();
        data0.setCompany("香港微软");
        data0.setDepartment("Office 事业部");
        data0.setTime("2001.07-2020.06");
        data0.setPosition("BUG工程师");
        textRenderData = new TextRenderData("负责生产BUG，然后修复BUG，同时有效实施招聘行为");
        textRenderData.setStyle(style);
        data0.setResponsibility(Numberings.of(textRenderData, textRenderData).create());
        ExperienceData data1 = new ExperienceData();
        data1.setCompany("自由职业");
        data1.setDepartment("OpenSource 项目组");
        data1.setTime("2015.07-2020.06");
        data1.setPosition("研发工程师");
        textRenderData = new TextRenderData("开源项目的迭代和维护工作");
        textRenderData.setStyle(style);
        TextRenderData textRenderData1 = new TextRenderData("持续集成、Swagger文档等工具调研");
        textRenderData1.setStyle(style);
        data1.setResponsibility(Numberings.of(textRenderData, textRenderData1, textRenderData).create());
        experiences.add(data0);
        experiences.add(data1);
        experiences.add(data0);
        experiences.add(data1);
        datas.setExperiences(experiences);
    }

    @Test
    public void testResumeExample() throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_resume.docx").render(datas);

        FileOutputStream out = new FileOutputStream("out_example_resume_iterable.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
