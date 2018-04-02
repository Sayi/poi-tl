package com.deepoove.poi.tl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.ListDataRenderPolicy;
import com.deepoove.poi.policy.TableListRenderPolicy;
import com.deepoove.poi.tl.datasource.MembershipApplication;
import com.deepoove.poi.tl.datasource.MembershipApplicationComp;
import com.deepoove.poi.tl.datasource.MembershipApplicationContact;

/**
 * @author Sayi
 * @version 0.0.4
 */
public class MembershipApplicationTest {

	
//	IMAGE('@'), TEXT('\0'), TABLE('#'), NUMBERIC('*');
	@SuppressWarnings("serial")
	@Test
	public void testRenderObject() throws Exception {
		MembershipApplication ma = new MembershipApplication();
		ma.setBmBirth("1987年11 月23日");
		ma.setBmBirthArea("隆回县");
		ma.setBmBirStr("阴历");
		List<String> bmcIntroductionList = new ArrayList();
		bmcIntroductionList.add("长沙绿佳清洁服务有限公司创办于2007年，注册资金1008万，总部位于深圳，是长沙市工商局、深圳清洁协会等单位批准的甲级资质专业清洁公司,中国质量•服务•诚信AAA企业，中国服务行业十大影响力企业。目前，公司员工总数达800余人，长期合作客户包括：");
		bmcIntroductionList.add("广西谷埠街国际商城、长沙市广电大厦、华融湘江银行、八方小区、中建-衡阳中心、长房-白沙湾、长房-南屏锦源、长房-凤凰苑、保利-麓谷林语、保利花园、保利-天心嘉园、中南大学、民政学院、银桂苑、花样年华、山水芙蓉国际新城、湖南先导城市资源有限公司等。");
		bmcIntroductionList.add("上善若水，厚德以载物。长沙绿佳清洁服务有限公司（以下简称绿佳）自成立之初，始终秉承“一切以顾客满意为标准”，不断进行技术创新、服务升级、管理改革。在国有大中型物业小区、百货商场、5A写字楼、景观广场等驻场保洁；园林施工保养、绿化养护、除四害；外墙清洗、垃圾清运、地面打蜡、抛光、地毯清洗；宾馆、酒店、楼宇拓荒等服务工作中，绿佳人在细节上数年如一日坚持精益求精，作业中力争完美，客户服务真诚用心，立体式运营管理，一切付出 只为更标准、更规范、更专业，得到了客户的充分肯定与赞誉。");
		bmcIntroductionList.add("常言道，不积跬步无以至千里。绿佳深知作为专业的清洁服务公司，任何细节无小事，客户要求都是头等大事。公司在成长的过程中，一直严格把关人员招聘、岗前培训标准、服务质量第三方监督考核等一系列可量化、可溯源的现场作业与管理体系。让客户成为实实在在的回头客，让口碑成为绿佳的金字招牌，绿佳人在追求卓越品质的过程中，始终锲而不舍，不忘创业初心。我们低头做好每一件事，我们也不忘抬头看路，在互联网+时代，绿佳人更是以昂扬的斗志、热血澎湃的激情，为进一步做好清洁服务而宵衣旰食，奋斗不止。");
		ma.setBmcIntroductionList(bmcIntroductionList);
		List<PictureRenderData> bmcLicenceList = new ArrayList();
		bmcLicenceList.add(new PictureRenderData("E:\\git\\poi-tl\\src\\test\\resources\\image002.jpg"));
		bmcLicenceList.add(new PictureRenderData("E:\\git\\poi-tl\\src\\test\\resources\\0-1.png"))	;
		ma.setBmcLicenceList(bmcLicenceList);
		List<PictureRenderData> bmcPictureList = new ArrayList();
		bmcPictureList.add(new PictureRenderData("E:\\git\\poi-tl\\src\\test\\resources\\image007.jpg"));
		bmcPictureList.add(new PictureRenderData("E:\\git\\poi-tl\\src\\test\\resources\\image009.jpg"));
		ma.setBmcPictureList(bmcPictureList);;
		ma.setBmEducation("本科");
		ma.setBmFax("fax");
		ma.setBmFocusresources("政府管辖环卫的职能部门");
		ma.setBmhangye("市政环卫");
		ma.setBmHaveResource("因为本人从事记者有5年，有部分媒体资源。");
		ma.setBmIdcard("430524198510252422");
		ma.setBmMobile("15220228888");
		ma.setBmNation("汉");
		ma.setBmOrgaddress("长沙市芙蓉区八一路418号昊天大厦商务楼12楼 （八一路与迎宾路交汇处）");
		ma.setBmOrgname("长沙市邵阳商会");
		ma.setBmOrgSession("四");
		ma.setBmPhone("0731-84325689");
		ma.setBmPolitic("团员");
		ma.setBmQq("1487260552");
		ma.setBmRealname("赵一");
		ma.setBmSex("女");
		ma.setBmSintroduction("各位领导，同仁，大家好！"
+"我叫赵艳平，来自邵阳市隆回县司门前镇众乐村。今年29岁，大学本科毕业，毕业后几年一直从事记者编辑工作，2015年，我从媒体转行，从事保洁外包工作，虽然隔行如隔山，但是在团队的勤奋和努力下，现与合伙人周剑萍，在保洁外包方面取得一定的成绩。其实，我一直想加入邵阳商会，但忌惮自己生意做得一般般，不好意思加入大咖的组织。现今，经过自己和团队的努力，我们的工作在各方面取得了一点小成绩，所以才斗胆申请加入邵阳商会。我为人诚实守信、性格活泼开朗，如果有幸加入大家庭，我会积极主动融入组织，贡献自己的绵薄之力。");
		ma.setBmStartTime("2017年5月 日");
		ma.setCardBackImagePath(new PictureRenderData("E:\\git\\poi-tl\\src\\test\\resources\\image2.png"));
		ma.setCardInImagePath(new PictureRenderData("E:\\git\\poi-tl\\src\\test\\resources\\image1.png"));
		ma.setChonorBmcName("中国清洁行业甲级资质企业");
		List<MembershipApplicationComp> compList = new ArrayList();
			
		
		MembershipApplicationComp mac1 = new MembershipApplicationComp();
		mac1.setBmcAddress("湖南省长沙市（商会定期寄送报刊，请填写详细地址）芙蓉区芙蓉中路二段59号顺天城1403室");
		mac1.setBmcEmployeenumStr("800人");
		mac1.setBmcFixedassets("2000万");
		mac1.setBmcIncomeMain("市政环卫保洁外包服务，医院、学校、大型商场、小区物业、社区保洁外包服务，大型开荒以及懒人家政平台到家服务");
		mac1.setBmcName("长沙绿佳清洁服务有限公司");
		mac1.setBmcNature("有限责任");
		mac1.setBmcOutputaccount("3000万");
		mac1.setBmcPosition("副总经理");
		mac1.setBmcRegcapital("1008万");
		mac1.setBmcTaxpayable("200万");	
		compList.add(mac1);
		ma.setCompList(compList);
		ma.setDeptName("三组");
		ma.setDuty("人大代表");
		ma.setHonorBmhName("会员个人荣誉1\n会员个人荣誉");
		ma.setInvestAmount("200万");
		List investList = new ArrayList();
		Map investMap1 = new HashMap();
		investMap1.put("bmiName", "长沙市民政学院保洁外包");
		investMap1.put("bmiAmount", "2015年10月");
		investMap1.put("bmiTime", "20万");
		investMap1.put("bmiDesp", "主要是负责民政学院保洁总体外包");
		investList.add(investMap1);
		
		Map investMap2 = new HashMap();
		investMap2.put("bmiName", "保利花园物业保洁外包");
		investMap2.put("bmiAmount", "2012年7月");
		investMap2.put("bmiTime", "200万");
		investMap2.put("bmiDesp", "主要负责保利花园小区保洁外包");
		investList.add(investMap2);
		ma.setInvestList(investList);
		ma.setLiveImagePath(new PictureRenderData("E:\\git\\poi-tl\\src\\test\\resources\\image005.jpg"));
		ma.setStationName("会员");
		ma.setWelfareAmount("100万");
		
		List welfareList = new ArrayList();
		Map welfareMap = new HashMap();
		welfareMap.put("bmwName", "隆回县司门前镇众乐村山村老人物资资质");
		welfareMap.put("bmwAmount", "20000");
		welfareMap.put("bmwTime", "2016年12月");
		welfareList.add(welfareMap);
		
		Map welfareMap1 = new HashMap();
		welfareMap1.put("bmwName", "长沙司门前镇众乐村山村老人物资资质");
		welfareMap1.put("bmwAmount", "100000");
		welfareMap1.put("bmwTime", "3016年12月");
		welfareList.add(welfareMap1);
		
		ma.setWelfareList(welfareList);
		List<MembershipApplicationContact> contactList = new ArrayList<MembershipApplicationContact>();
		MembershipApplicationContact maContact1 = new MembershipApplicationContact();
		maContact1.setBmcMobile("15220228888");
		maContact1.setBmcName("法师");
		maContact1.setBmcQq("283677888");
		maContact1.setBmcRelationName("哈哈");
		contactList.add(maContact1);
		
		MembershipApplicationContact maContact11 = new MembershipApplicationContact();
		maContact11.setBmcMobile("152202288881");
		maContact11.setBmcName("法师1");
		maContact11.setBmcQq("2836778881");
		maContact11.setBmcRelationName("哈哈1");
		contactList.add(maContact11);
		ma.setContactList(contactList);
		
		TableListRenderPolicy clrp = new TableListRenderPolicy();
		//新增语法插件
		Configure plugin = Configure.createDefault();
		//动态持有XWPFTable对象
		
//		plugin.customPolicy("compList", new CompListRenderPolicy());
		
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/newtemplate.docx",plugin).render(ma);
//		template.registerPolicy("contactList", new TableListRenderPolicy());
//		template.render(ma);
		
		FileOutputStream out = new FileOutputStream("out_newtemplate.docx");
		template.write(out);
		template.close();
		out.flush();
		out.close();
		System.out.println("=====MembershipApplicationTest========");
	}
	

}
