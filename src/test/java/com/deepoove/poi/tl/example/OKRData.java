package com.deepoove.poi.tl.example;

import java.util.List;

public class OKRData {

    private User user;
    private List<OKRItem> objectives;

    private List<OKRItem> manageObjectives;

    private String date;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OKRItem> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<OKRItem> objectives) {
        this.objectives = objectives;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<OKRItem> getManageObjectives() {
        return manageObjectives;
    }

    public void setManageObjectives(List<OKRItem> manageObjectives) {
        this.manageObjectives = manageObjectives;
    }

}

class User {
    private String name;
    private String depart;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

}

class OKRItem {
    int index;
    Objective object;
    KeyResult kr1;
    KeyResult kr2;
    KeyResult kr3;

    public Objective getObject() {
        return object;
    }

    public void setObject(Objective object) {
        this.object = object;
    }

    public KeyResult getKr1() {
        return kr1;
    }

    public void setKr1(KeyResult kr1) {
        this.kr1 = kr1;
    }

    public KeyResult getKr2() {
        return kr2;
    }

    public void setKr2(KeyResult kr2) {
        this.kr2 = kr2;
    }

    public KeyResult getKr3() {
        return kr3;
    }

    public void setKr3(KeyResult kr3) {
        this.kr3 = kr3;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}

class Objective {
    private String desc;
    private String progress;

    public Objective(String desc, String progress) {
        this.desc = desc;
        this.progress = progress;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

}

class KeyResult {
    private String desc;
    private String progress;

    public KeyResult(String desc, String progress) {
        this.desc = desc;
        this.progress = progress;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

}
