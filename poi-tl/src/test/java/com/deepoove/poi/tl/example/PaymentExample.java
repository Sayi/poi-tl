package com.deepoove.poi.tl.example;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.style.BorderStyle;

@DisplayName("Example for Table")
public class PaymentExample {

    String resource = "src/test/resources/payment/payment.docx";
    PaymentData datas = new PaymentData();

    @BeforeEach
    public void init() {
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

        RowRenderData header = Rows.of("日期", "订单编号", "销售代表", "离岸价", "发货方式", "条款", "税号").bgColor("F2F2F2").center()
                .textColor("7F7f7F").textFontFamily("Hei").textFontSize(9).create();
        RowRenderData row = Rows.of("2018-06-12", "SN18090", "李四", "5000元", "快递", "附录A", "T11090").center().create();
        BorderStyle borderStyle = new BorderStyle();
        borderStyle.setColor("A6A6A6");
        borderStyle.setSize(4);
        borderStyle.setType(XWPFBorderType.SINGLE);
        TableRenderData table = Tables.ofA4MediumWidth().addRow(header).addRow(row).border(borderStyle).center()
                .create();
        datas.setOrder(table);

        DetailData detailTable = new DetailData();
        RowRenderData good = Rows.of("4", "墙纸", "书房+卧室", "1500", "/", "400", "1600").center().create();
        List<RowRenderData> goods = Arrays.asList(good, good, good);
        RowRenderData labor = Rows.of("油漆工", "2", "200", "400").center().create();
        List<RowRenderData> labors = Arrays.asList(labor, labor, labor, labor);
        detailTable.setGoods(goods);
        detailTable.setLabors(labors);
        datas.setDetailTable(detailTable);
    }

    @Test
    public void testPaymentExample() throws Exception {
        Configure config = Configure.builder().bind("detail_table", new DetailTablePolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(datas);
        template.writeToFile("out_example_payment.docx");
    }

}
