package com.zol.backserver.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zol.backserver.service.QueryReferTimeService;




public class TestServer extends BaseTest{
	
	@Autowired
	QueryReferTimeService queryservice;
	
	
	@Test
	public void testJms(){
//		queryservice.queryTime(1);
			String url = "http://detail.zol.com.cn/cell_phone_index/subcate57_0_list_1_0_1_1_0_2.html";
	}
	
	
}
