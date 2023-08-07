package com.deepoove.poi.tl.plugin;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.plugin.table.MultipleColumnTableRenderPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@DisplayName("Example for HackLoop MultipleColumnTableRenderPolicy Example")
public class MultipleColumnTableRenderPolicyTest {

    String resource = "src/test/resources/template/render_multiple_column.docx";

    PaymentHackData data = new PaymentHackData();

    @BeforeEach
    public void init() {
        data.setTotal("总共：7200");

        List<Goods> goods = new ArrayList<>();
        Goods good = new Goods();
        good.setCount(4);
        good.setName("墙纸");
        good.setDesc("书房卧室");
        good.setDiscount(1500);
        good.setPrice(400);
        good.setTax(new Random().nextInt(10) + 20);
        good.setTotalPrice(1600);
        good.setPicture(Pictures.ofLocal("src/test/resources/earth.png").size(24, 24).create());

        Goods good1 = new Goods();
        good1.setCount(123);
        good1.setName("菜包子");
        good1.setDesc("厦门");
        good1.setDiscount(1500);
        good1.setPrice(400);
        good1.setTax(new Random().nextInt(10) + 20);
        good1.setTotalPrice(1600);
        good1.setPicture(Pictures.ofLocal("src/test/resources/large.png").fitSize().create());


        Goods good2 = new Goods();
        good2.setCount(5);
        good2.setName("lucky");
        good2.setDesc("蓝猫");
        good2.setDiscount(500);
        good2.setPrice(888);
        good2.setTax(new Random().nextInt(10) + 20);
        good2.setTotalPrice(100);
        good2.setPicture(Pictures.ofLocal("src/test/resources/docx.png").size(20, 20).create());

        goods.add(good);
        goods.add(good1);
        goods.add(good2);
        data.setGoods(goods);
        // same line
        data.setGoods2(goods);


    }

    @Test
    public void testMultipleColumnTableRenderPolicyExample() throws Exception {
        MultipleColumnTableRenderPolicy multipleColumnTableRenderPolicy = new MultipleColumnTableRenderPolicy();
        MultipleColumnTableRenderPolicy multipleSameColumnTableRenderPolicy = new MultipleColumnTableRenderPolicy(true);

        Configure config = Configure.builder().bind("goods", multipleColumnTableRenderPolicy).bind("goods2",
            multipleSameColumnTableRenderPolicy).build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(data);
        template.writeToFile("target/out_render_multiple_column.docx");
    }

}
