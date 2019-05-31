package com.deepoove.poi.tl.el;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.deepoove.poi.el.ELObject;
import com.deepoove.poi.exception.ExpressionEvalException;

public class ELObjectTest {

    public class Person {
        private User user;
        private String work;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }
    }

    public class One {
        protected String place;
        protected boolean enable;

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

    }

    public class User extends One {
        private String name;
        private int age;
        private List<String> alias;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getAlias() {
            return alias;
        }

        public void setAlias(List<String> alias) {
            this.alias = alias;
        }

    }

    @SuppressWarnings("serial")
    @Test
    public void testApp() {
        Person person = new Person();
        person.setWork("deepoove.com");
        final User user = new User();
        user.setName("Sayi");
        user.setAge(18);
        user.setEnable(false);
        user.setPlace("浙江杭州");
        List<String> asList = Arrays.asList("ada");
        user.setAlias(asList);
        person.setUser(user);

        ELObject elObject = new ELObject(person);
        testEL(user, asList, elObject);

        ELObject elMap = new ELObject(new HashMap<String, User>() {
            {
                put("user", user);
            }
        });
        testEL(user, asList, elMap);

    }

    @SuppressWarnings("serial")
    @Test
    public void testMap() {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("header", "Deeply love what you love.");
                put("name", "Poi-tl");
                put("word", "模板引擎");
                put("time", "2019-05-31");
                put("what",
                        "Java Word模板引擎： Minimal Microsoft word(docx) templating with {{template}} in Java. It works by expanding tags in a template using values provided in a JavaMap or JavaObject.");
                put("author", new Object());
            }
        };
        ELObject elObject = ELObject.create(datas);
        Assert.assertEquals(elObject.eval("header"), "Deeply love what you love.");
        Assert.assertEquals(elObject.eval("name"), "Poi-tl");
        Assert.assertEquals(elObject.eval("word"), "模板引擎");
        Assert.assertEquals(elObject.eval("time"), "2019-05-31");
    }

    private void testEL(final User user, List<String> asList, ELObject elObject) {
        Assert.assertEquals(user, elObject.eval("user"));
        Assert.assertEquals(18, elObject.eval("user.age"));
        Assert.assertEquals("Sayi", elObject.eval("user.name"));
        Assert.assertEquals("Sayi", elObject.eval("user.name"));
        Assert.assertEquals("浙江杭州", elObject.eval("user.place"));
        Assert.assertEquals(false, elObject.eval("user.enable"));
        Assert.assertEquals(asList, elObject.eval("user.alias"));

        try {
            elObject.eval("user.alias.name..");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e instanceof ExpressionEvalException);
        }

        try {
            elObject.eval("user.alias.name");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e instanceof ExpressionEvalException);
        }

        try {
            elObject.eval("user.sex");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.assertTrue(e instanceof ExpressionEvalException);
        }
        try {
            elObject.eval("ada");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ExpressionEvalException);
        }
    }
}
