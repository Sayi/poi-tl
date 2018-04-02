package com.deepoove.poi.tl.datasource;

import java.util.List;

import com.deepoove.poi.data.PictureRenderData;

public class MembershipApplication
{
	private String bmOrgSession;  // 商会届次
	private String bmOrgname;  //  商会名称
//	private String remark1;  // 账  号：43001528061052503122       开户行：建行长沙长岛路支行
	private String bmOrgaddress;  //商会地址
//	private String remark2;  // 电话/传真：0731—84311166 
	private PictureRenderData cardInImagePath;  //身份证照片
	private PictureRenderData cardBackImagePath;  //身份证反面照片
	private String bmSintroduction;  //个人介绍
	private PictureRenderData liveImagePath;  //个人生活照片
	private String deptName;  //会员所在小组
	private String stationName;  //会员最大的职务
	private String bmhangye;  //	会员所在行业
	private String bmStartTime;  //会员入会日期
	private String bmRealname;  //会员真实姓名
	private String bmSex;  //性别
	private String bmNation;  //名族
	private String bmBirthArea;  //籍贯 区
	private String bmEducation;  //学历
	private String bmBirth;  //生日
	private String bmBirStr;  //出生日期类型
	private String bmIdcard;  //身份证号码
	private String bmPolitic;  //政治面貌
	private String bmMobile;  //手机号码
	private String bmPhone;  //联系电话
	private String bmFax;  //传真
	private String bmQq;  //qq号码
	private List<MembershipApplicationContact> contactList;  //联系人集合
	private List<MembershipApplicationComp> compList;  //公司集合
	private String duty;  //社会职务
	private String bmFocusresources;  //关注的资源
	private String bmHaveResource;  //会员拥有的资源	
	private String investAmount;  //投资项目总金额
	private List investList;  //投资项目集合
	private String welfareAmount;  //公益项目总金额
	private List welfareList;  //公益项目集合
	private String chonorBmcName;  //公司荣耀
	private String honorBmhName;  //个人荣耀
	private List<PictureRenderData> bmcLicenceList;  //公司营业执照
	private List<String> bmcIntroductionList;  //企业简介
	private List<PictureRenderData> bmcPictureList;  //企业LOGO,企业日常照
	
	private String cardInImagePathStr;  //附件：会员身份证 正反面
	private String bmcLicenceListStr;  //附件：三证合一营业执照
	private String bmcIntroductionStr;  //附件：企业简介
	private String bmSintroductionStr;  //附件：个人简介
	private String liveImagePathStr;  //附件：个人照片
	private String bmcPictureListStr;  //附件：企业LOGO及办公室照片

	public void setBmOrgSession(String bmOrgSession)
	{
		this.bmOrgSession = bmOrgSession;
	}

	public String getBmOrgSession()
	{
		return this.bmOrgSession;
	}

	public void setBmOrgname(String bmOrgname)
	{
		this.bmOrgname = bmOrgname;
	}

	public String getBmOrgname()
	{
		return this.bmOrgname;
	}


	public void setBmOrgaddress(String bmOrgaddress)
	{
		this.bmOrgaddress = bmOrgaddress;
	}

	public String getBmOrgaddress()
	{
		return this.bmOrgaddress;
	}


	public void setCardInImagePath(PictureRenderData cardInImagePath)
	{
		this.cardInImagePath = cardInImagePath;
		if(cardInImagePath!=null)
		{
			this.cardInImagePathStr="附件：会员身份证 正反面";
		}
	}

	public PictureRenderData getCardInImagePath()
	{
		return this.cardInImagePath;
	}

	public void setCardBackImagePath(PictureRenderData cardBackImagePath)
	{
		this.cardBackImagePath = cardBackImagePath;
		if(cardBackImagePath!=null)
		{
			this.cardInImagePathStr="附件：会员身份证 正反面";
		}
	}

	public PictureRenderData getCardBackImagePath()
	{
		return this.cardBackImagePath;
	}

	public void setBmSintroduction(String bmSintroduction)
	{
		this.bmSintroduction = bmSintroduction;
		if(bmSintroduction!=null&&!bmSintroduction.isEmpty())
		{
			this.bmSintroductionStr ="附件：个人简介";
		}
	}

	public String getBmSintroduction()
	{
		return this.bmSintroduction;
	}

	public void setLiveImagePath(PictureRenderData liveImagePath)
	{
		this.liveImagePath = liveImagePath;
		if(liveImagePath!=null)
		{
			liveImagePathStr="附件：个人照片";
		}
	}

	public PictureRenderData getLiveImagePath()
	{
		return this.liveImagePath;
	}

	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}

	public String getDeptName()
	{
		return this.deptName;
	}

	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}

	public String getStationName()
	{
		return this.stationName;
	}

	public void setBmhangye(String bmhangye)
	{
		this.bmhangye = bmhangye;
	}

	public String getBmhangye()
	{
		return this.bmhangye;
	}

	public void setBmStartTime(String bmStartTime)
	{
		this.bmStartTime = bmStartTime;
	}

	public String getBmStartTime()
	{
		return this.bmStartTime;
	}

	public void setBmRealname(String bmRealname)
	{
		this.bmRealname = bmRealname;
	}

	public String getBmRealname()
	{
		return this.bmRealname;
	}

	public void setBmSex(String bmSex)
	{
		this.bmSex = bmSex;
	}

	public String getBmSex()
	{
		return this.bmSex;
	}

	public void setBmNation(String bmNation)
	{
		this.bmNation = bmNation;
	}

	public String getBmNation()
	{
		return this.bmNation;
	}

	public void setBmBirthArea(String bmBirthArea)
	{
		this.bmBirthArea = bmBirthArea;
	}

	public String getBmBirthArea()
	{
		return this.bmBirthArea;
	}

	public void setBmEducation(String bmEducation)
	{
		this.bmEducation = bmEducation;
	}

	public String getBmEducation()
	{
		return this.bmEducation;
	}

	public void setBmBirth(String bmBirth)
	{
		this.bmBirth = bmBirth;
	}

	public String getBmBirth()
	{
		return this.bmBirth;
	}

	public void setBmBirStr(String bmBirStr)
	{
		this.bmBirStr = bmBirStr;
	}

	public String getBmBirStr()
	{
		return this.bmBirStr;
	}

	public void setBmIdcard(String bmIdcard)
	{
		this.bmIdcard = bmIdcard;
	}

	public String getBmIdcard()
	{
		return this.bmIdcard;
	}

	public void setBmPolitic(String bmPolitic)
	{
		this.bmPolitic = bmPolitic;
	}

	public String getBmPolitic()
	{
		return this.bmPolitic;
	}

	public void setBmMobile(String bmMobile)
	{
		this.bmMobile = bmMobile;
	}

	public String getBmMobile()
	{
		return this.bmMobile;
	}

	public void setBmPhone(String bmPhone)
	{
		this.bmPhone = bmPhone;
	}

	public String getBmPhone()
	{
		return this.bmPhone;
	}

	public void setBmFax(String bmFax)
	{
		this.bmFax = bmFax;
	}

	public String getBmFax()
	{
		return this.bmFax;
	}

	public void setBmQq(String bmQq)
	{
		this.bmQq = bmQq;
	}

	public String getBmQq()
	{
		return this.bmQq;
	}




	public void setDuty(String duty)
	{
		this.duty = duty;
	}

	public String getDuty()
	{
		return this.duty;
	}

	public void setBmFocusresources(String bmFocusresources)
	{
		this.bmFocusresources = bmFocusresources;
	}

	public String getBmFocusresources()
	{
		return this.bmFocusresources;
	}

	public void setBmHaveResource(String bmHaveResource)
	{
		this.bmHaveResource = bmHaveResource;
	}

	public String getBmHaveResource()
	{
		return this.bmHaveResource;
	}

	public void setInvestAmount(String investAmount)
	{
		this.investAmount = investAmount;
	}

	public String getInvestAmount()
	{
		return this.investAmount;
	}


	public void setWelfareAmount(String welfareAmount)
	{
		this.welfareAmount = welfareAmount;
	}

	public String getWelfareAmount()
	{
		return this.welfareAmount;
	}





	public void setChonorBmcName(String chonorBmcName)
	{
		this.chonorBmcName = chonorBmcName;
	}

	public String getChonorBmcName()
	{
		return this.chonorBmcName;
	}

	public void setHonorBmhName(String honorBmhName)
	{
		this.honorBmhName = honorBmhName;
	}

	

	public List<MembershipApplicationContact> getContactList()
	{
		return contactList;
	}

	public void setContactList(List<MembershipApplicationContact> contactList)
	{
		this.contactList = contactList;
	}

	public List<MembershipApplicationComp> getCompList()
	{
		return compList;
	}

	public void setCompList(List<MembershipApplicationComp> compList)
	{
		this.compList = compList;
	}

	public List getInvestList()
	{
		return investList;
	}

	public void setInvestList(List investList)
	{
		this.investList = investList;
	}

	public List getWelfareList()
	{
		return welfareList;
	}

	public void setWelfareList(List welfareList)
	{
		this.welfareList = welfareList;
	}

	

	



	


	public List<String> getBmcIntroductionList()
	{
		return bmcIntroductionList;
	}

	public void setBmcIntroductionList(List<String> bmcIntroductionList)
	{
		this.bmcIntroductionList = bmcIntroductionList;
		if(bmcIntroductionList!=null&&bmcIntroductionList.size()>0)
		{
			this.bmcIntroductionStr = "附件：企业简介";
		}
		
	}

	public List<PictureRenderData> getBmcLicenceList()
	{
		return bmcLicenceList;
	}

	public void setBmcLicenceList(List<PictureRenderData> bmcLicenceList)
	{
		this.bmcLicenceList = bmcLicenceList;
		if(bmcLicenceList!=null&&bmcLicenceList.size()>0)
		{
			this.bmcLicenceListStr = "附件：三证合一营业执照";
		}
	}

	public List<PictureRenderData> getBmcPictureList()
	{
		return bmcPictureList;
	}

	public void setBmcPictureList(List<PictureRenderData> bmcPictureList)
	{
		this.bmcPictureList = bmcPictureList;
		if(bmcPictureList!=null&&bmcPictureList.size()>0)
		{
			this.bmcPictureListStr="附件：企业LOGO及办公室照片";
		}
	}

	public String getHonorBmhName()
	{
		return honorBmhName;
	}

	public String getCardInImagePathStr()
	{
		return cardInImagePathStr;
	}

	public void setCardInImagePathStr(String cardInImagePathStr)
	{
		this.cardInImagePathStr = cardInImagePathStr;
	}

	public String getBmcLicenceListStr()
	{
		return bmcLicenceListStr;
	}

	public void setBmcLicenceListStr(String bmcLicenceListStr)
	{
		this.bmcLicenceListStr = bmcLicenceListStr;
	}

	public String getBmcIntroductionStr()
	{
		return bmcIntroductionStr;
	}

	public void setBmcIntroductionStr(String bmcIntroductionStr)
	{
		this.bmcIntroductionStr = bmcIntroductionStr;
	}

	public String getBmSintroductionStr()
	{
		return bmSintroductionStr;
	}

	public void setBmSintroductionStr(String bmSintroductionStr)
	{
		this.bmSintroductionStr = bmSintroductionStr;
	}

	public String getLiveImagePathStr()
	{
		return liveImagePathStr;
	}

	public void setLiveImagePathStr(String liveImagePathStr)
	{
		this.liveImagePathStr = liveImagePathStr;
	}

	public String getBmcPictureListStr()
	{
		return bmcPictureListStr;
	}

	public void setBmcPictureListStr(String bmcPictureListStr)
	{
		this.bmcPictureListStr = bmcPictureListStr;
	}
	
	
	
}

