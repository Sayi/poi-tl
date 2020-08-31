package com.deepoove.poi.tl.source;

import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.el.Name;

public class MyDataModel extends DataSourceBaseTest {

    private String word;
    private String time;
    private String what;
    private NumberingRenderData feature;
    @Name("solution_compare")
    private TableRenderData solutionCompare;
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

    public void setFeature(NumberingRenderData feature) {
        this.feature = feature;
    }

    public NumberingRenderData getFeature() {
        return this.feature;
    }

    public TableRenderData getSolutionCompare() {
        return solutionCompare;
    }

    public void setSolutionCompare(TableRenderData solutionCompare) {
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
