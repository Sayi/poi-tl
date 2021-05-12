package com.deepoove.poi.tl.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.exception.ExpressionEvalException;
import com.deepoove.poi.expression.DefaultEL;
import com.deepoove.poi.expression.Name;

@DisplayName("Default EL test case")
public class DefaultELTest {

    @SuppressWarnings("serial")
    @Test
    public void test4Bean() {
        Person person = new Person();
        person.setWork("deepoove.com");
        person.setAliasName("Alias");
        final User user = new User();
        user.setName("Sayi");
        user.setAge(18);
        user.setEnable(false);
        user.setPlace("浙江杭州");
        List<String> asList = Arrays.asList("ada");
        user.setAlias(asList);
        person.setUser(user);

        DefaultEL elObject = new DefaultEL(person);
        assertEquals(person.getAliasName(), elObject.eval("aliasName"));
        assertEquals(person.getAliasName(), elObject.eval("alias_name"));
        assertEquals(person.getWork(), elObject.eval("work"));
        assertEquals(person, elObject.eval("#this"));
        testEL(user, asList, elObject);

        DefaultEL elMap = new DefaultEL(new HashMap<String, User>() {
            {
                put("user", user);
            }
        });
        testEL(user, asList, elMap);

    }

    private void testEL(final User user, List<String> asList, DefaultEL elObject) {
        assertEquals(user, elObject.eval("user"));
        assertEquals(18, elObject.eval("user.age"));
        assertEquals(user.getName(), elObject.eval("user.name"));
        assertEquals(user.getPlace(), elObject.eval("user.place"));
        assertEquals(user.isEnable(), elObject.eval("user.enable"));
        assertEquals(asList, elObject.eval("user.alias"));

        try {
            elObject.eval("user.alias.name..");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof ExpressionEvalException);
        }

        try {
            elObject.eval("user.alias.name");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof ExpressionEvalException);
        }

        try {
            elObject.eval("user.sex");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof ExpressionEvalException);
        }
        try {
            elObject.eval("ada");
        } catch (Exception e) {
            assertTrue(e instanceof ExpressionEvalException);
        }
    }

    public class Person {
        private User user;
        private String work;
        @Name("alias_name")
        private String aliasName;

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

        public String getAliasName() {
            return aliasName;
        }

        public void setAliasName(String aliasName) {
            this.aliasName = aliasName;
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
}
