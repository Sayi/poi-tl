package com.deepoove.poi.tl.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Charts;
import com.deepoove.poi.data.Pictures;

@DisplayName("AnimalExmample test case")
public class AnimalExample {

    @SuppressWarnings("serial")
    @Test
    public void testAnimal() throws Exception {

        Map<String, Object> elephant = new HashMap<String, Object>() {
            {
                put("name", "大象");
                put("chart",
                        Charts.ofMultiSeries("大象生存现状", new String[] { "2018年", "2019年", "2020年" })
                                .addSeries("成年象", new Integer[] { 500, 600, 700 })
                                .addSeries("幼象", new Integer[] { 200, 300, 400 })
                                .addSeries("全部", new Integer[] { 700, 900, 1100 })
                                .create());

            }
        };
        Map<String, Object> giraffe = new HashMap<String, Object>() {
            {
                put("name", "长颈鹿");
                put("picture", Pictures.ofLocal("src/test/resources/lu.png").size(100, 120).create());
                put("chart",
                        Charts.ofMultiSeries("长颈鹿生存现状", new String[] { "2018年", "2019年", "2020年" })
                                .addSeries("成年鹿", new Integer[] { 500, 600, 700 })
                                .addSeries("幼鹿", new Integer[] { 200, 300, 400 })
                                .create());

            }
        };
        List<Map<String, Object>> animals = Arrays.asList(elephant, giraffe);
        XWPFTemplate.compile("src/test/resources/animal/animal.docx").render(new HashMap<String, Object>() {
            {
                put("animals", animals);
            }
        }).writeToFile("out_example_animal.docx");
    }

}
