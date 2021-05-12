/*
 * Copyright 2014-2021 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.xwpf;

import java.io.IOException;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChartSpace;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTExternalData;

public class EnhancedXWPFChart extends XWPFChart {

    public EnhancedXWPFChart() {
        super();
    }

    public EnhancedXWPFChart(PackagePart part) throws IOException, XmlException {
        super(part);
    }

    @Override
    public void setExternalId(String id) {
        CTChartSpace ctChartSpace = getCTChartSpace();
        CTExternalData externalData = ctChartSpace.isSetExternalData() ? ctChartSpace.getExternalData()
                : ctChartSpace.addNewExternalData();
        externalData.setId(id);
        if (!externalData.isSetAutoUpdate()) {
            externalData.addNewAutoUpdate().setVal(true);
        }
    }
}
