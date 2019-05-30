package com.deepoove.poi.tl.source;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.PictureRenderData;

public class DataTest {
    @Name("Question")
    private String question;
    @Name("A")
    private String a;
    @Name("B")
    private String b;
    @Name("C")
    private String c;
    @Name("D")
    private String d;
    private PictureRenderData logo;

    public DataTest() {}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public PictureRenderData getLogo() {
        return logo;
    }

    public void setLogo(PictureRenderData logo) {
        this.logo = logo;
    }
}
