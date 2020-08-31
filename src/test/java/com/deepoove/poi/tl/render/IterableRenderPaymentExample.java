package com.deepoove.poi.tl.render;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.tl.example.DetailData;
import com.deepoove.poi.tl.example.DetailTablePolicy;
import com.deepoove.poi.tl.example.PaymentData;

@DisplayName("Foreach table example")
public class IterableRenderPaymentExample {

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

        Style headTextStyle = new Style();
        headTextStyle.setFontFamily("Hei");
        headTextStyle.setFontSize(9);
        headTextStyle.setColor("7F7F7F");
        CellRenderData cell0 = Cells.of(Texts.of("日期").style(headTextStyle).create()).create();
        CellRenderData cell1 = Cells.of(Texts.of("订单编号").style(headTextStyle).create()).create();
        CellRenderData cell2 = Cells.of(Texts.of("销售代表").style(headTextStyle).create()).create();
        CellRenderData cell3 = Cells.of(Texts.of("离岸价").style(headTextStyle).create()).create();
        CellRenderData cell4 = Cells.of(Texts.of("发货方式").style(headTextStyle).create()).create();
        CellRenderData cell5 = Cells.of(Texts.of("条款").style(headTextStyle).create()).create();
        CellRenderData cell6 = Cells.of(Texts.of("税号").style(headTextStyle).create()).create();
        RowRenderData header = Rows.of(cell0, cell1, cell2, cell3, cell4, cell5, cell6).bgColor("F2F2F2").center()
                .create();

        RowRenderData row = Rows.of("2018-06-12", "SN18090", "李四", "5000元", "快递", "附录A", "T11090").center().create();
        TableRenderData table = Tables.ofA4MediumWidth().addRow(header).addRow(row).center().create();
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

    @SuppressWarnings("serial")
    @Test
    public void testIterablePaymentExample() throws Exception {

        List<PaymentData> orders = new ArrayList<PaymentData>();
        orders.add(datas);
        orders.add(datas);

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("orders", orders);
            }
        };

        Configure config = Configure.newBuilder().bind("detail_table", new DetailTablePolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_payment.docx", config);
        template.render(datas);
        FileOutputStream out = new FileOutputStream("out_iterable_payment.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
