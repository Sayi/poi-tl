package com.deepoove.poi.tl.example;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.ChartSingleSeriesRenderData;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.policy.NiceXWPFDocumentRenderPolicy;
import com.deepoove.poi.tl.example.policy.MyUpdateExcelPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Example for NiceXWPFDocument")
public class NiceXWPFDocumentExample {

	NiceXWPFDocumentData datas = new NiceXWPFDocumentData();

    @BeforeEach
    public void init() {
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

	    List<ExperienceData> experiences2 = new ArrayList<ExperienceData>();
	    experiences2.add(data1);
	    experiences2.add(data0);

	    ChartSingleSeriesRenderData pie = Charts.ofPie("111", new String[]{"研发工程师", "BUG工程师"})
		    .series("职位占比", new Integer[]{20, 30})
		    .create();

	    List<SonData> sonDataList = new ArrayList<>();
	    SonData sonData = new SonData();
	    sonData.setCode("SON1");
	    sonData.setExperienceData(experiences);
		sonData.setPie(pie);

	    SonData sonData2 = new SonData();
	    sonData2.setCode("SON2");
	    sonData2.setExperienceData(experiences2);
		sonData2.setPie(pie);

	    sonDataList.add(sonData);
	    sonDataList.add(sonData2);


	    datas.setSonDataList(sonDataList);
		datas.setPie(pie);
    }

    @Test
    public void testExample() throws Exception {
	    Configure sonConfig = Configure.builder()
		    .bind("experienceData", new MyUpdateExcelPolicy())
		    .build();
	    Configure config = Configure.builder()
		    .bind("sonDataList", new NiceXWPFDocumentRenderPolicy("src/test/resources/nice/son.docx", sonConfig))
		    .build();
	    XWPFTemplate template = XWPFTemplate.compile("src/test/resources/nice/main.docx", config)
		    .render(datas);
        template.writeToFile("out_example_nice.docx");
    }

}
