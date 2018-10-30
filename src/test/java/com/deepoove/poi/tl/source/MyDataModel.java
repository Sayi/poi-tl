package com.deepoove.poi.tl.source;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;

public class MyDataModel extends DataSourceBaseTest {

    private String word;
    private String time;
    private String what;
    private NumbericRenderData feature;
    @Name("solution_compare")
    private MiniTableRenderData solutionCompare;
    private PictureRenderData portrait;
    private String author;
    private String introduce;
    private String header;

    public void setWhat(String what) {
        this.what = what;
    }

    public String getWhat() {
        return this.what;
    }

    public void setFeature(NumbericRenderData feature) {
        this.feature = feature;
    }

    public NumbericRenderData getFeature() {
        return this.feature;
    }

    public MiniTableRenderData getSolutionCompare() {
        return solutionCompare;
    }

    public void setSolutionCompare(MiniTableRenderData solutionCompare) {
        this.solutionCompare = solutionCompare;
    }

    public void setPortrait(PictureRenderData portrait) {
        this.portrait = portrait;
    }

    public PictureRenderData getPortrait() {
        return this.portrait;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIntroduce() {
        return this.introduce;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return this.header;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
