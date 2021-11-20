package com.deepoove.poi.tl.example;

import com.deepoove.poi.data.ChartSingleSeriesRenderData;

import java.util.List;

/**
 * @author WQ
 * @date 2021/11/20
 */
public class SonData {

	private String code;
	private List<ExperienceData> experienceData;
	private ChartSingleSeriesRenderData pie;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ExperienceData> getExperienceData() {
		return experienceData;
	}

	public void setExperienceData(List<ExperienceData> experienceData) {
		this.experienceData = experienceData;
	}

	public ChartSingleSeriesRenderData getPie() {
		return pie;
	}

	public void setPie(ChartSingleSeriesRenderData pie) {
		this.pie = pie;
	}
}
