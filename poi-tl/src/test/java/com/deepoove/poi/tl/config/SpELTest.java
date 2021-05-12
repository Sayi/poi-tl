package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.render.compute.EnvModel;
import com.deepoove.poi.render.compute.SpELRenderDataCompute;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("Spring Expression language test case")
public class SpELTest {

    SpELRenderDataCompute spelForMap;
    SpELRenderDataCompute spelForBean;
    SpELRenderDataCompute spelForFunction;

    SpELData data;
    Map<String, Method> spELFunction;

    @SuppressWarnings("serial")
    @BeforeEach
    public void init() throws ParseException, NoSuchMethodException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "Sayi");
        map.put("localDate", LocalDate.parse("2021-03-08"));
        map.put("data", new HashMap<String, Object>() {
            {
                put("hello", "poi-tl");
            }
        });
        data = new SpELData();
        List<Dog> dogs = new ArrayList<SpELTest.Dog>();
        dogs.add(new Dog("阿黄", 8));
        dogs.add(new Dog("阿绿", 6));
        dogs.add(new Dog("阿蓝", 5));
        data.setDogs(dogs);
        data.setDogsArr(dogs.toArray(new Dog[] {}));
        data.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-05-20 22:14:10"));

        spELFunction = new HashMap<>();
        Method substringStaticMethod = StringUtils.class.getDeclaredMethod("substring", String.class, int.class);
        spELFunction.put("substringStaticMethod", substringStaticMethod);

        spelForFunction = new SpELRenderDataCompute(EnvModel.ofModel(data), true, spELFunction);
        spelForMap = new SpELRenderDataCompute(EnvModel.ofModel(map));
        spelForBean = new SpELRenderDataCompute(EnvModel.ofModel(data));
    }

    @Test
    public void testSpELCompute() {
        // map
        assertEquals(spelForMap.compute("['name']"), "Sayi");
        assertEquals(spelForMap.compute("['data']['hello']"), "poi-tl");
        // map accessor
        assertEquals(spelForMap.compute("name"), "Sayi");
        assertEquals(spelForMap.compute("data.hello"), "poi-tl");
        // static class method
        assertEquals(
                spelForMap.compute("localDate.format(T(java.time.format.DateTimeFormatter).ofPattern('yyyy年MM月dd日'))"),
                "2021年03月08日");

        assertEquals(spelForBean.compute("name"), "poi-tl");
        assertEquals(spelForBean.compute("name == 'poi-tl'"), true);
        // 调用method转大写
        assertEquals(spelForBean.compute("name.toUpperCase()"), "POI-TL");
        // 空值特殊显示
        assertEquals(spelForBean.compute("empty?:'这个字段为空'"), "这个字段为空");
        // 三目运算符
        assertEquals(spelForBean.compute("sex ? '男' : '女'"), "男");
        // 同样的时间字段，不同的格式
        assertEquals(spelForBean.compute("new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').format(time)"),
                "2019-05-20 22:14:10");
        assertEquals(spelForBean.compute("new java.text.SimpleDateFormat('yyyy-MM-dd hh:mm').format(time)"),
                "2019-05-20 10:14");

        // 运算符
        assertEquals(spelForBean.compute("price"), 88880000L);
        assertEquals(spelForBean.compute("price + '元'"), "88880000元");
        assertEquals(spelForBean.compute("price + '元' == '88880000元'"), true);
        assertEquals(spelForBean.compute("price/1000 + '千元'"), "88880千元");
        assertEquals(spelForBean.compute("price/10000 + '万元'"), "8888万元");

        // 数组、列表
        assertEquals(spelForBean.compute("dogs[0].name"), "阿黄");
        assertEquals(spelForBean.compute("dogs[1].age"), 6);
        assertEquals(spelForBean.compute("dogs[2].name"), "阿蓝");
        assertEquals(spelForBean.compute("dogsArr[2].name"), "阿蓝");

        // map
        assertEquals(spelForBean.compute("data['hello']"), "poi-tl");

        // el function
        assertEquals(spelForFunction.compute("#substringStaticMethod('spElFunction', 2)"), "ElFunction");
    }

    @Test
    public void testSpELTemplate() throws IOException {
        Configure config = Configure.builder().useSpringEL(spELFunction).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/config_spel.docx", config)
                .render(data);

        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        XWPFParagraph paragraph = document.getParagraphArray(0);
        assertEquals(paragraph.getText(), "poi-tl");
        paragraph = document.getParagraphArray(1);
        assertEquals(paragraph.getText(), "lowCase:poi-tlUpcase:POI-TL");
        paragraph = document.getParagraphArray(2);
        assertEquals(paragraph.getText(), "这个字段为空");
        paragraph = document.getParagraphArray(3);
        assertEquals(paragraph.getText(), "男");
        paragraph = document.getParagraphArray(4);
        assertEquals(paragraph.getText(), "2019-05-20 22:14:10");
        paragraph = document.getParagraphArray(5);
        assertEquals(paragraph.getText(), "2019-05-20 10:14");
        paragraph = document.getParagraphArray(6);
        assertEquals(paragraph.getText(), "88880000");
        paragraph = document.getParagraphArray(7);
        assertEquals(paragraph.getText(), "8888万元");
        paragraph = document.getParagraphArray(8);
        assertEquals(paragraph.getText(), "阿黄");
        paragraph = document.getParagraphArray(9);
        assertEquals(paragraph.getText(), "6");
        paragraph = document.getParagraphArray(10);
        assertEquals(paragraph.getText(), "阿蓝");
        paragraph = document.getParagraphArray(11);
        assertEquals(paragraph.getText(), "ElFunction");

        document.close();

    }

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

}
