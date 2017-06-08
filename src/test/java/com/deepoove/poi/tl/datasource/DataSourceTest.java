package com.deepoove.poi.tl.datasource;

import java.util.ArrayList;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;

public class DataSourceTest extends DataSourceBaseTest {

	private String header_version;
	private String hello;
	private PictureRenderData logo;
	private TextRenderData title;
	private TableRenderData changeLog;
	private String website;

	public DataSourceTest() {
	}

	public String getHeader_version() {
		return header_version;
	}

	public void setHeader_version(String header_version) {
		this.header_version = header_version;
	}

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}

	public PictureRenderData getLogo() {
		return logo;
	}

	public void setLogo(PictureRenderData logo) {
		this.logo = logo;
	}

	public TextRenderData getTitle() {
		return title;
	}

	public void setTitle(TextRenderData title) {
		this.title = title;
	}

	public TableRenderData getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(TableRenderData changeLog) {
		this.changeLog = changeLog;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
