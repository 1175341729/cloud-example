package com.springcloud.example.common;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.springcloud.example.common.enums.Singleton;
import com.springcloud.example.common.util.CalendarUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CommonApplicationTests {
	@Test
	public void contextLoads() {
		CalendarUtil instance1 = Singleton.INSTANCE.getInstance();
		CalendarUtil instance2 = Singleton.INSTANCE.getInstance();
		CalendarUtil instance3 = Singleton.INSTANCE.getInstance();
		System.out.println(instance1 == instance2);
		System.out.println(instance2 == instance3);
		System.out.println(instance1 == instance3);

		ArrayList<String> codes = Lists.newArrayList("123", "456", "789");
		Singleton.INSTANCE.setCodes(codes);

		List<String> codes1 = Singleton.INSTANCE.getCodes();
		codes1.add("add");

		List<String> codes2 = Singleton.INSTANCE.getCodes();
		log.info("codes：{}", JSON.toJSONString(codes2));
	}

}
