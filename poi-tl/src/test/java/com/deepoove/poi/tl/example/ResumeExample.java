package com.deepoove.poi.tl.example;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.Style;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Example for Resume")
public class ResumeExample {

    ResumeData datas = new ResumeData();

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

        // 模板文档循环合并
        List<ExperienceData> experiences = new ArrayList<ExperienceData>();
        ExperienceData data0 = new ExperienceData();
        data0.setCompany("香港微软");
        data0.setDepartment("Office 事业部");
        data0.setTime("2001.07-2020.06");
        data0.setPosition("BUG工程师");
        textRenderData = new TextRenderData("负责生产BUG，然后修复BUG，同时有效实施招聘行为");
        textRenderData.setStyle(style);
        data0.setResponsibility(new NumberingRenderData(NumberingFormat.LOWER_ROMAN, textRenderData, textRenderData));
        ExperienceData data1 = new ExperienceData();
        data1.setCompany("自由职业");
        data1.setDepartment("OpenSource 项目组");
        data1.setTime("2015.07-2020.06");
        data1.setPosition("研发工程师");
        textRenderData = new TextRenderData("开源项目的迭代和维护工作");
        textRenderData.setStyle(style);
        TextRenderData textRenderData1 = new TextRenderData("持续集成、Swagger文档等工具调研");
        textRenderData1.setStyle(style);
        data1.setResponsibility(Numberings.of(NumberingFormat.LOWER_ROMAN).addItem(textRenderData)
                .addItem(textRenderData1).addItem(textRenderData).create());
        experiences.add(data0);
        experiences.add(data1);
        experiences.add(data0);
        experiences.add(data1);
        datas.setExperience(
                Includes.ofLocal("src/test/resources/resume/segment.docx").setRenderModel(experiences).create());
    }

    @Test
    public void testResumeExample() throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/resume/resume.docx").render(datas);
        template.writeToFile("out_example_resume.docx");
    }

}
