package com.deepoove.poi.tl.datasource;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;

public class DataSourceTest extends DataSourceBaseTest {

	@Name("header_version")
	private String headerVersion;
	private String hello;
	private PictureRenderData logo;
	private TextRenderData title;
	private TableRenderData changeLog;
	private String website;

	public DataSourceTest() {}

	public String getHeaderVersion() {
		return headerVersion;
	}

	public void setHeaderVersion(String headerVersion) {
		this.headerVersion = headerVersion;
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
