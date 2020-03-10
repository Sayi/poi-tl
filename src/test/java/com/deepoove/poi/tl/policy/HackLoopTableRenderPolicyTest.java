package com.deepoove.poi.tl.policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.HackLoopTableRenderPolicy;
import com.deepoove.poi.util.BytePictureUtils;

@DisplayName("Example for Hack Table")
public class HackLoopTableRenderPolicyTest {

    String resource = "src/test/resources/payment/payment_hack.docx";
    PaymentHackData data1 = new PaymentHackData();
    PaymentHackData data2 = new PaymentHackData();

    @BeforeEach
    public void init() {
        data1.setSubtotal("8000");
        data1.setTax("600");
        data1.setTransform("120");
        data1.setOther("250");
        data1.setUnpay("6600");
        data1.setTotal("总共：7200");

        List<Goods> goods = new ArrayList<>();
        Goods good = new Goods();
        good.setCount(4);
        good.setName("墙纸");
        good.setDesc("书房卧室");
        good.setDiscount(1500);
        good.setPrice(400);
        good.setTax(new Random().nextInt(10) + 20);
        good.setTotalPrice(1600);
        good.setPicture(new PictureRenderData(24, 24, ".png", BytePictureUtils.getUrlBufferedImage("http://deepoove.com/images/icecream.png")));
        goods.add(good);
        goods.add(good);
        goods.add(good);
        goods.add(good);
        data1.setGoods(goods);

        List<Labor> labors = new ArrayList<>();
        Labor labor = new Labor();
        labor.setCategory("油漆工");
        labor.setPeople(2);
        labor.setPrice(400);
        labor.setTotalPrice(1600);
        labors.add(labor);
        labors.add(labor);
        labors.add(labor);
        labors.add(labor);
        data1.setLabors(labors);
        
        data2.setSubtotal("8002");
        data2.setTax("602");
        data2.setTransform("122");
        data2.setOther("252");
        data2.setUnpay("6602");
        data2.setTotal("总共：7202");
        
        data2.setGoods(goods);
    }

    @SuppressWarnings("serial")
    @Test
    public void testPaymentHackExample() throws Exception {
        HackLoopTableRenderPolicy hackLoopTableRenderPolicy = new HackLoopTableRenderPolicy();
        Configure config = Configure.newBuilder().bind("goods", hackLoopTableRenderPolicy)
                .bind("labors", hackLoopTableRenderPolicy).build();
        XWPFTemplate template = XWPFTemplate.compile(resource, config).render(new HashMap<String, Object>() {
            {
                put("datas", Arrays.asList(data1, data2));
            }
        });
        template.writeToFile("out_example_payment_hack.docx");
    }

}
