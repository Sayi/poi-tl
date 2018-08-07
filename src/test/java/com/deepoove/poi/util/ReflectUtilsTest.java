package com.deepoove.poi.util;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.deepoove.poi.util.ReflectUtils.ClassProxy;

/**
 * @author herowzz
 */
public class ReflectUtilsTest {

	private static Province province;
	private static Group group;
	private static Model model;

	@BeforeClass
	public static void postConstruct() {
		province = new Province("beijing");
		group = new Group("group1", province);
		model = new Model(1, "zhangsan", group);
	}

	@Test
	public void testFromCache() {
		Object obj = model;
		ClassProxy proxy1 = ReflectUtils.fromCache(obj);
		ClassProxy proxy2 = ReflectUtils.fromCache(obj);
		Assert.assertEquals(proxy1, proxy2);
		Assert.assertEquals(proxy1.hashCode(), proxy2.hashCode());
	}

	@Test
	public void testProxyGetValue() {
		Object obj = model;
		ClassProxy proxy1 = ReflectUtils.fromCache(obj);
		Assert.assertEquals(proxy1.getValue("model.name").toString(), model.getName());
		Assert.assertEquals(Long.parseLong(proxy1.getValue("model.id").toString()), model.getId().longValue());

		Assert.assertEquals(proxy1.getValue("model.group.name").toString(), group.getName());
		Assert.assertEquals(proxy1.getValue("model.group.province.name").toString(), province.getName());
	}

	@Test
	public void testProxyGetMethodValue() {
		Object obj = model;
		ClassProxy proxy1 = ReflectUtils.fromCache(obj);
		Assert.assertEquals(proxy1.getMethodValue("name").toString(), model.getName());
	}

	public static class Model {
		private Integer id;
		private String name;
		private Group group;

		public Model() {
		}

		public Model(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		public Model(Integer id, String name, Group group) {
			this(id, name);
			this.group = group;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}
	}

	public static class Group {
		private String name;
		private Province province;

		public Group() {
		}

		public Group(String name, Province province) {
			this.name = name;
			this.province = province;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Province getProvince() {
			return province;
		}

		public void setProvince(Province province) {
			this.province = province;
		}
	}

	public static class Province {
		private String name;

		public Province() {
		}

		public Province(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
