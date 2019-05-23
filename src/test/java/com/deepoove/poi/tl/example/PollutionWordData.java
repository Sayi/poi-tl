package com.deepoove.poi.tl.example;

import com.deepoove.poi.data.*;
import com.deepoove.poi.data.style.TableStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 定义一个空气污染生成word的处理对象
 */
public class PollutionWordData {

    private String startTime;
    private String endTime;
    private String publishTime; //发布时间
    private String content; //内容
    private String makeUser; //预报员
    private List<ColorPicture> imgs;
    private List<CityForecast> cityForecasts;
    // 等级说明
    private MiniTableRenderData levelExplainsData;

    private DocxRenderData cityDoc;

    private DocxRenderData imgDoc;


    public PollutionWordData(){

        TableStyle headStyle = new TableStyle();
        headStyle.setBackgroundColor("F2F2F2");
        headStyle.setAlign(STJc.LEFT);

        TableStyle bodyStyle = new TableStyle();
        bodyStyle.setBackgroundColor("fffff2");
        bodyStyle.setAlign(STJc.LEFT);

        RowRenderData header = RowRenderData.build(new TextRenderData("等级"), new TextRenderData("颜色"), new TextRenderData("评价"),new TextRenderData("描述"));
        header.setStyle(headStyle);
        RowRenderData row0 = RowRenderData.build("一级","绿色","好","非常有利于空气污染物稀释、消散和清除");
        row0.setStyle(bodyStyle);
        RowRenderData row1 = RowRenderData.build("二级","深绿","较好","较有利于空气污染物稀释、消散和清除");
        row1.setStyle(bodyStyle);
        RowRenderData row2 = RowRenderData.build("三级","黄色","一般","对空气污染物稀释、消散和清除无明显影响");
        row2.setStyle(bodyStyle);
        RowRenderData row3 = RowRenderData.build("四级","深黄","较差","不利于空气污染物稀释、消散和清除");
        row3.setStyle(bodyStyle);
        RowRenderData row4 = RowRenderData.build("五级","红色","差","很不利于空气污染物稀释、消散和清除");
        row4.setStyle(bodyStyle);
        RowRenderData row5 = RowRenderData.build("六级","深红","极差","极不利于空气污染物稀释、消散和清除");
        row5.setStyle(bodyStyle);
        this.levelExplainsData=new MiniTableRenderData(header, Arrays.asList(row0, row1, row2,row3,row4,row5));
    }


    public void setImgs(List<PollutionWordData.ColorPicture> imgs){
        this.imgs = imgs;
        File docxFile=new File("src/test/resources/tableDoc/pollutionImg.docx");
        this.imgDoc = new DocxRenderData(docxFile, imgs );
    }


    public void setCityForecasts(List<PollutionWordData.CityForecast> cityForecasts){
        this.cityForecasts = cityForecasts;
        File docxFile=new File("src/test/resources/tableDoc/pollutionCityForecast.docx");
        this.cityDoc=new DocxRenderData(docxFile, cityForecasts );
    }


    public static class ColorPicture{
        private PictureRenderData imgPic;
        private String timeTitle;
        private PictureRenderData colorCode;

        public ColorPicture(String timeTitle,byte[] imgbytes){
            this.timeTitle=timeTitle;
            this.colorCode=new PictureRenderData(50,60, "src/test/resources/tableDoc/colorLev.png");
            this.imgPic=new PictureRenderData(200, 300, ".jpg", imgbytes);
        }

        public PictureRenderData getImgPic() {
            return imgPic;
        }

        public void setImgPic(PictureRenderData imgPic) {
            this.imgPic = imgPic;
        }

        public String getTimeTitle() {
            return timeTitle;
        }

        public void setTimeTitle(String timeTitle) {
            this.timeTitle = timeTitle;
        }

        public PictureRenderData getColorCode() {
            return colorCode;
        }

        public void setColorCode(PictureRenderData colorCode) {
            this.colorCode = colorCode;
        }
    }

    public static class CityForecast{
        private String city;
        private String forcecast;

        public CityForecast(String city,String forcecast){
            this.city=city;
            this.forcecast=forcecast.replace("null","");
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getForcecast() {
            return forcecast;
        }

        public void setForcecast(String forcecast) {
            this.forcecast = forcecast;
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMakeUser() {
        return makeUser;
    }

    public void setMakeUser(String makeUser) {
        this.makeUser = makeUser;
    }

    public List<ColorPicture> getImgs() {
        return imgs;
    }

    public List<CityForecast> getCityForecasts() {
        return cityForecasts;
    }

    public MiniTableRenderData getLevelExplainsData() {
        return levelExplainsData;
    }

    public void setLevelExplainsData(MiniTableRenderData levelExplainsData) {
        this.levelExplainsData = levelExplainsData;
    }

    public DocxRenderData getCityDoc() {
        return cityDoc;
    }

    public void setCityDoc(DocxRenderData cityDoc) {
        this.cityDoc = cityDoc;
    }

    public DocxRenderData getImgDoc() {
        return imgDoc;
    }

    public void setImgDoc(DocxRenderData imgDoc) {
        this.imgDoc = imgDoc;
    }
}

