package com.deepoove.poi.tl.render;

import java.util.List;

import com.deepoove.poi.expression.Name;
import com.deepoove.poi.tl.example.SegmentData;

public class StoryDataV2 {

    @Name("story_name")
    private String storyName;
    @Name("story_author")
    private String storyAuthor;
    private List<SegmentData> sections;
    @Name("story_source")
    private String storySource;
    private String summary;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<SegmentData> getSections() {
        return sections;
    }

    public void setSections(List<SegmentData> sections) {
        this.sections = sections;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getStoryAuthor() {
        return storyAuthor;
    }

    public void setStoryAuthor(String storyAuthor) {
        this.storyAuthor = storyAuthor;
    }

    public String getStorySource() {
        return storySource;
    }

    public void setStorySource(String storySource) {
        this.storySource = storySource;
    }

}
