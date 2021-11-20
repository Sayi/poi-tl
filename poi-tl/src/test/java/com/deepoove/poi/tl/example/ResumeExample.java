package com.deepoove.poi.tl.example;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.tl.example.policy.MyDocxDataRenderPolicy;
import com.deepoove.poi.tl.example.policy.MyUpdateExcelPolicy;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
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

        // 模板文档循环合并
        List<ExperienceData> experiences = new ArrayList<ExperienceData>();
        ExperienceData data0 = new ExperienceData();
        data0.setCompany("香港微软");
        data0.setDepartment("Office 事业部");
        data0.setTime("2001.07-2020.06");
        data0.setPosition("BUG工程师");

        ExperienceData data1 = new ExperienceData();
        data1.setCompany("自由职业");
        data1.setDepartment("OpenSource 项目组");
        data1.setTime("2015.07-2020.06");
        data1.setPosition("研发工程师");

        experiences.add(data0);
        experiences.add(data1);
        experiences.add(data0);
        experiences.add(data1);

	    List<SonData> sonDataList = new ArrayList<>();
	    SonData sonData = new SonData();
	    sonData.setCode("SON1");
	    sonData.setExperienceData(experiences);
	    SonData sonData2 = new SonData();
	    sonData2.setCode("SON2");
	    sonData2.setExperienceData(experiences);

	    sonDataList.add(sonData);
//	    sonDataList.add(sonData2);

	    List<NiceXWPFDocument> riskList = new ArrayList<>();
	    for (SonData data : sonDataList) {
		    Configure config = Configure.builder()
			    .bind("experienceData", new MyUpdateExcelPolicy())
			    .build();
		    XWPFTemplate template = XWPFTemplate.compile("src/test/resources/resume/segment.docx", config)
			    .render(data);

		    NiceXWPFDocument xwpfDocument = template.getXWPFDocument();
		    riskList.add(xwpfDocument);
//		    try {
//			    template.writeToFile("out_example_resume_"+data.getCode()+".docx");
//		    } catch (IOException e) {
//			    e.printStackTrace();
//		    }
	    }

	    datas.setRiskList(riskList);
    }

    @Test
    public void testResumeExample() throws Exception {
	    Configure config = Configure.builder()
		    .bind("riskList", new MyDocxDataRenderPolicy())
		    .build();
	    XWPFTemplate template = XWPFTemplate.compile("src/test/resources/resume/resume.docx", config)
		    .render(datas);
        template.writeToFile("out_example_resume.docx");
    }

}
