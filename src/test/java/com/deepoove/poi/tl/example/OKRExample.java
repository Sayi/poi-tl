package com.deepoove.poi.tl.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

@DisplayName("Example for OKR")
public class OKRExample {

    @Test
    public void testOKRExample() throws Exception {

        OKRData data = new OKRData();
        User user = new User();
        user.setName("李彦宏");
        user.setDepart("百度CEO");
        data.setUser(user);

        List<OKRItem> objectives = new ArrayList<OKRItem>();
        OKRItem item = new OKRItem();
        item.setIndex(1);
        item.setObject(new Objective("打造一个空前繁荣、强大的百度移动生态", "4%"));
        item.setKr1(new KeyResult("恪守安全可控、引人向上、忠诚服务、降低门槛的产品价值观，持续优化用户体验，提升百度系产品的总时长份额", "5%"));
        item.setKr2(new KeyResult("恪守良币驱逐劣币的商业价值观，实现在爱惜品牌口碑、优化用户体验基础上的收入增长", "1%"));
        item.setKr3(new KeyResult("产品要有创新，不能总是me too，me later", "1%"));
        objectives.add(item);

        item = new OKRItem();
        item.setIndex(2);
        item.setObject(new Objective("主流AI赛道模式跑通，实现可持续增长", "10%"));
        item.setKr1(new KeyResult("小度小度进入千家万户", "15%"));
        item.setKr2(new KeyResult("智能驾驶、智能交通找到规模化发展路径", "0%"));
        item.setKr3(new KeyResult("云及AI2B业务至少在个万亿级行业成为第一", "1%"));
        objectives.add(item);
        data.setObjectives(objectives);
        
        List<OKRItem> manageObjectives = new ArrayList<OKRItem>();
        item = new OKRItem();
        item.setIndex(1);
        item.setObject(new Objective("提升百度的组织能力，有效支撑住业务规模的高速增长，不拖战略的后腿", "1%"));
        item.setKr1(new KeyResult("全公司成功推行OKR制度，有效降低沟通协调成本，激励大家为更高目标奋斗取得比KPI管理更好的业绩", "1%"));
        item.setKr2(new KeyResult("激发从ESTAFF到一线员工的主人翁意识，使之比2018年更有意愿有能力自我驱动管理好各自负责的领域", "0%"));
        item.setKr3(new KeyResult("建立合理的管理人员新陈代谢机制，打造出不少于2名业界公认的优秀领军人物", "1%"));
        manageObjectives.add(item);
        data.setManageObjectives(manageObjectives);

        data.setDate("2020-01-31");

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/okr/OKR.docx").render(data);
        template.writeToFile("out_example_okr.docx");

    }

}
