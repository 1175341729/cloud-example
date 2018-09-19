package com.springcloud.example.common;

import com.springcloud.example.common.enums.Singleton;
import com.springcloud.example.common.util.CalendarUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
	}

}
