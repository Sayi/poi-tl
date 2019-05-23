package com.deepoove.poi.tl.example;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.TableDocumentRenderPolicy;
import com.deepoove.poi.util.BytePictureUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * demo 生成word的表格中嵌套图片和文字的效果
 * @author aofavx
 */

public class PollutionWordExample {

    PollutionWordData pollutionWordData=null;

    @Before
    public void init(){
        pollutionWordData=new PollutionWordData();
        pollutionWordData.setStartTime("11日20:00");
        pollutionWordData.setEndTime("14日20:00");
        pollutionWordData.setPublishTime("2018年7月11日 20时");
        pollutionWordData.setMakeUser("aofavx");
        pollutionWordData.setContent("预报内容预报内容预报内容预报内容预报内容预报内容预报内容\n" +
                "123预报内容预报内容预报内容预报内容");

        List<PollutionWordData.ColorPicture> colorPictureList=new ArrayList<PollutionWordData.ColorPicture>();
        colorPictureList.add(new PollutionWordData.ColorPicture("未来0-12小时", BytePictureUtils.getLocalByteArray(new File("src/test/resources/tableDoc/p_ea7ad9ad21df418b8a23538c55f83e03.jpg"))));
        colorPictureList.add(new PollutionWordData.ColorPicture("未来12-24小时",BytePictureUtils.getLocalByteArray(new File("src/test/resources/tableDoc/p_ea7ad9ad21df418b8a23538c55f83e03.jpg"))));
        colorPictureList.add(new PollutionWordData.ColorPicture("未来24-48小时",BytePictureUtils.getLocalByteArray(new File("src/test/resources/tableDoc/p_ea7ad9ad21df418b8a23538c55f83e03.jpg"))));
        colorPictureList.add(new PollutionWordData.ColorPicture("未来48-72小时",BytePictureUtils.getLocalByteArray(new File("src/test/resources/tableDoc/p_ea7ad9ad21df418b8a23538c55f83e03.jpg"))));

        pollutionWordData.setImgs(colorPictureList);

        List<PollutionWordData.CityForecast> cityForecasts=new ArrayList<PollutionWordData.CityForecast>();
        cityForecasts.add(new PollutionWordData.CityForecast("银川地区","   今天夜间，空气污染气象条件四级,不利于空气污染物稀释、消散和清除\n" +
                "    明天白天，空气污染气象条件三级,对空气污染物稀释、消散和清除无明显影响\n" +
                "    14日夜间到15日白天，空气污染气象条件四级,不利于空气污染物稀释、消散和清除\n" +
                "    15日夜间到16日白天，空气污染气象条件三级,对空气污染物稀释、消散和清除无明显影响"));
        cityForecasts.add(new PollutionWordData.CityForecast("贺兰城区","    今天夜间，空气污染气象条件四级,不利于空气污染物稀释、消散和清除\n" +
                "    明天白天，空气污染气象条件三级,对空气污染物稀释、消散和清除无明显影响\n" +
                "    14日夜间到15日白天，空气污染气象条件四级,不利于空气污染物稀释、消散和清除\n" +
                "    15日夜间到16日白天，空气污染气象条件三级,对空气污染物稀释、消散和清除无明显影响"));
        cityForecasts.add(new PollutionWordData.CityForecast("永宁城区","   今天夜间，空气污染气象条件三级,对空气污染物稀释、消散和清除无明显影响\n" +
                "    明天白天，空气污染气象条件三级,对空气污染物稀释、消散和清除无明显影响\n" +
                "    14日夜间到15日白天，空气污染气象条件三级,对空气污染物稀释、消散和清除无明显影响\n" +
                "    15日夜间到16日白天，空气污染气象条件三级,对空气污染物稀释、消散和清除无明显影响"));
        pollutionWordData.setCityForecasts(cityForecasts);
    }

    public int cols=2;
    public void out(String filePath) throws Exception{
        Configure config = Configure.newBuilder().customPolicy("imgDoc", new TableDocumentRenderPolicy(cols)).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/tableDoc//pollution.docx",config).render(pollutionWordData);
        FileOutputStream out = new FileOutputStream(filePath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    public OutputStream out(){
        OutputStream outputStream=new ByteArrayOutputStream();
        XWPFTemplate template=null;
        try {
            Configure config = Configure.newBuilder().customPolicy("imgDoc", new TableDocumentRenderPolicy(cols)).build();
            template = XWPFTemplate.compile("src/test/resources/tableDoc/pollution.docx",config).render(pollutionWordData);
            template.write(outputStream);
        } catch (IOException e) {
            return null;
        }finally {
            try {
                if(template!=null){
                    template.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream;
    }

    @Test
    public void testResumeExample() throws Exception {
        out("src/test/resources/tableDoc/tableDoc生成.docx");
    }

}
