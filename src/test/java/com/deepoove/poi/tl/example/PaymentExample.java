package com.deepoove.poi.tl.example;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.TableStyle;

/**
 * 付款通知书：表格操作示例
 * @author Sayi
 * @version 
 */
public class PaymentExample {
    
    PaymentData datas = new PaymentData();
    
    Style headTextStyle = new Style();
    TableStyle headStyle = new TableStyle();
    TableStyle rowStyle = new TableStyle();
    
    @Before
    public void init(){
        headTextStyle.setFontFamily("Hei");
        headTextStyle.setFontSize(9);
        headTextStyle.setColor("7F7F7F");
        
        headStyle.setBackgroundColor("F2F2F2");
        headStyle.setAlign(STJc.CENTER);
        
        rowStyle = new TableStyle();
        rowStyle.setAlign(STJc.CENTER);
        
        
        
        datas.setNO("KB.6890451");
        datas.setID("ZHANG_SAN_091");
        datas.setTaitou("深圳XX家装有限公司");
        datas.setConsignee("丙丁");
        
        datas.setSubtotal("8000");
        datas.setTax("600");
        datas.setTransform("120");
        datas.setOther("250");
        datas.setUnpay("6600");
        datas.setTotal("总共：7200");

        
        RowRenderData header = RowRenderData.build(new TextRenderData("日期", headTextStyle),
                new TextRenderData("订单编号", headTextStyle), new TextRenderData("销售代表", headTextStyle),
                new TextRenderData("离岸价", headTextStyle), new TextRenderData("发货方式", headTextStyle),
                new TextRenderData("条款", headTextStyle), new TextRenderData("税号", headTextStyle));
        header.setRowStyle(headStyle);
        
        RowRenderData row = RowRenderData.build("2018-06-12", "SN18090", "李四", "5000元", "快递", "附录A", "T11090");
        row.setRowStyle(rowStyle);
        MiniTableRenderData  miniTableRenderData = new MiniTableRenderData(header, Arrays.asList(row), MiniTableRenderData.WIDTH_A4_MEDIUM_FULL);
        miniTableRenderData.setStyle(headStyle);
        datas.setOrder(miniTableRenderData);
        
        DetailData detailTable = new DetailData();
        RowRenderData good = RowRenderData.build("4", "墙纸", "书房+卧室", "1500", "/", "400", "1600");
        good.setRowStyle(rowStyle);
        List<RowRenderData> goods = Arrays.asList(good, good, good);
        RowRenderData  labor = RowRenderData.build("油漆工", "2", "200", "400");
        labor.setRowStyle(rowStyle);
        List<RowRenderData> labors = Arrays.asList(labor, labor, labor, labor);
        detailTable.setGoods(goods);
        detailTable.setLabors(labors);
        datas.setDetailTable(detailTable);
    }

    @Test
    public void testResumeExample() throws Exception {
        Configure config = Configure.newBuilder().customPolicy("detail_table", new DetailTablePolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/付款通知书.docx", config).render(datas);
        FileOutputStream out = new FileOutputStream("out_付款通知书.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
