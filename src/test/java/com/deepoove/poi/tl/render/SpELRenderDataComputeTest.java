package com.deepoove.poi.tl.render;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ELMode;
import com.deepoove.poi.render.SpELRenderDataCompute;

public class SpELRenderDataComputeTest {

    SpELRenderDataCompute spel1;
    SpELRenderDataCompute spel2;

    SpELData data = new SpELData();

    class SpELData {
        private String name = "poi-tl";
        private String empty = null;
        private boolean sex = true;
        private List<Dog> dogs;
        private Dog[] dogsArr;
        @SuppressWarnings("serial")
        private Map<String, Object> data = new HashMap<String, Object>() {
            {
                put("hello", "poi-tl");
            }
        };

        private long price = 88880000;
        private Date time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Dog> getDogs() {
            return dogs;
        }

        public void setDogs(List<Dog> dogs) {
            this.dogs = dogs;
        }

        public Dog[] getDogsArr() {
            return dogsArr;
        }

        public void setDogsArr(Dog[] dogsArr) {
            this.dogsArr = dogsArr;
        }

        public Map<String, Object> getData() {
            return data;
        }

        public void setData(Map<String, Object> data) {
            this.data = data;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(long price) {
            this.price = price;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }

        public String getEmpty() {
            return empty;
        }

        public void setEmpty(String empty) {
            this.empty = empty;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

    }

    class Dog {
        private String name;
        private int age;

        public Dog(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @SuppressWarnings("serial")
    @Before
    public void init() throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put("name", "Sayi");
                put("data", new HashMap<String, Object>() {
                    {
                        put("hello", "poi-tl");
                    }
                });
            }
        };
        spel1 = new SpELRenderDataCompute(map);

        List<Dog> dogs = new ArrayList<SpELRenderDataComputeTest.Dog>();
        dogs.add(new Dog("阿黄", 8));
        dogs.add(new Dog("阿绿", 6));
        dogs.add(new Dog("阿蓝", 5));
        data.setDogs(dogs);

        data.setDogsArr(dogs.toArray(new Dog[] {}));
        data.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-05-20 22:14:10"));

        spel2 = new SpELRenderDataCompute(data);
    }

    @Test
    public void testCompute() {
        // map
        Assert.assertEquals(spel1.compute("['name']"), "Sayi");
        Assert.assertEquals(spel1.compute("['data']['hello']"), "poi-tl");

        Assert.assertEquals(spel2.compute("name"), "poi-tl");
        // 调用method转大写
        Assert.assertEquals(spel2.compute("name.toUpperCase()"), "POI-TL");
        // 空值特殊显示
        Assert.assertEquals(spel2.compute("empty?:'这个字段为空'"), "这个字段为空");
        // 三目运算符
        Assert.assertEquals(spel2.compute("sex ? '男' : '女'"), "男");
        // 同样的时间字段，不同的格式
        Assert.assertEquals(
                spel2.compute("new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(time)"),
                "2019-05-20 22:14:10");
        Assert.assertEquals(
                spel2.compute("new java.text.SimpleDateFormat('yyyy-MM-dd hh:mm').format(time)"),
                "2019-05-20 10:14");

        // 运算符
        Assert.assertEquals(spel2.compute("price"), 88880000l);
        Assert.assertEquals(spel2.compute("price + '元'"), "88880000元");
        Assert.assertEquals(spel2.compute("price/1000 + '千元'"), "88880千元");
        Assert.assertEquals(spel2.compute("price/10000 + '万元'"), "8888万元");

        // 数组、列表
        Assert.assertEquals(spel2.compute("dogs[0].name"), "阿黄");
        Assert.assertEquals(spel2.compute("dogs[1].age"), 6);
        Assert.assertEquals(spel2.compute("dogs[2].name"), "阿蓝");
        Assert.assertEquals(spel2.compute("dogsArr[2].name"), "阿蓝");

        // map
        Assert.assertEquals(spel2.compute("data['hello']"), "poi-tl");

    }

    @Test
    public void testSpELTemplate() throws IOException {
        Configure config = Configure.newBuilder().setElMode(ELMode.SPEL_MODE).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/spel.docx", config)
                .render(data);
        template.writeToFile("out_spel.docx");
    }

}
