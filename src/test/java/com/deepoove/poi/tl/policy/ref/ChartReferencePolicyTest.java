package com.deepoove.poi.tl.policy.ref;

import java.util.HashMap;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

public class ChartReferencePolicyTest {

    @Test
    public void testBarChart() throws Exception {

        Configure configure = Configure.newBuilder()
                .referencePolicy(new MyChartReferenceRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/chart_bar.docx", configure)
                .render(new HashMap<>());

        template.writeToFile("out_chart_bar.docx");
    }

}
