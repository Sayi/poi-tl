package com.deepoove.poi.util;

import java.util.HashMap;
import java.util.Map;

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
	private static Map<String, Object> map = new HashMap<String, Object>();

	@BeforeClass
	public static void postConstruct() {
		province = new Province("beijing");
		group = new Group("group1", province);
		model = new Model(1, "zhangsan", group);

		map.put("id", "123");
		map.put("model", model);
	}

	@Test
	public void testFromCache() {
		Object obj = model;
		ClassProxy proxy1 = ReflectUtils.fromCache(obj);
		ClassProxy proxy2 = ReflectUtils.fromCache(obj);
		Assert.assertEquals(proxy1, proxy2);
		Assert.assertEquals(proxy1.hashCode(), proxy2.hashCode());

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("id", "123");
		map2.put("model", model);
		ClassProxy proxy3 = ReflectUtils.fromCache(map, "map1");
		ClassProxy proxy4 = ReflectUtils.fromCache(map2, "map2");
		ClassProxy proxy5 = ReflectUtils.fromCache(map2, "map1");
		Assert.assertNotEquals(proxy3, proxy4);
		Assert.assertEquals(proxy3, proxy5);
	}

	@Test
	public void testProxyGetValue() {
		Object obj = model;
		ClassProxy proxy1 = ReflectUtils.fromCache(obj);
		Assert.assertEquals(proxy1.getValue("model.name").toString(), model.getName());
		Assert.assertEquals(Long.parseLong(proxy1.getValue("model.id").toString()), model.getId().longValue());

		Assert.assertEquals(proxy1.getValue("model.group.name").toString(), group.getName());
		Assert.assertEquals(proxy1.getValue("model.group.province.name").toString(), province.getName());

		ClassProxy proxyMap = ReflectUtils.fromCache(map, "map");
		Assert.assertEquals(proxyMap.getValue("map.id").toString(), "123");
		Assert.assertEquals(proxyMap.getValue("map.model.group.name").toString(), group.getName());
		Assert.assertEquals(proxyMap.getValue("map.model.group.province.name").toString(), province.getName());
	}

	@Test
	public void testProxyGetMethodValue() {
		Object obj = model;
		ClassProxy proxy1 = ReflectUtils.fromCache(obj);
		Assert.assertEquals(proxy1.getMethodValue("name").toString(), model.getName());

		ClassProxy proxyMap = ReflectUtils.fromCache(map, "map");
		Assert.assertEquals(proxyMap.getMethodValue("id").toString(), "123");
		Assert.assertEquals(((Model) proxyMap.getMethodValue("model")).getName(), model.getName());
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
