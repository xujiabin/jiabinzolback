package com.zol.backserver.service;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



/**
 * 驱动浏览器操作
 */
public class DriverToBrowserService {
	
	private Logger logger = Logger.getLogger(DriverToBrowserService.class);
	
	
	public  void testLexin(){
		try {
			logger.info("====testLexin===1=");
			System.setProperty("webdriver.firefox.bin", "/root/data/firefox/firefox");
			WebDriver web =  new FirefoxDriver();//firefox 19.0   selenium 2.31.0
			
			logger.info("====testLexin==22222==");
			
			web.get("http://dealer.zol.com.cn/beijing/");
			logger.info("====333333333===");
			web.quit();
			
			//尊敬的用户，您本次绑定手机所使用的验证码是000560（验证码30分钟后失效）【博雅彩】
		} catch (Exception e) {
			logger.error("不能驱动浏览器",e);
			e.printStackTrace();
		}
	}

}
