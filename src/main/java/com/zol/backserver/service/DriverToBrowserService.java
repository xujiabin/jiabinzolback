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
			logger.info("====测试驱动浏览器，加载驱动====");
			System.setProperty("webdriver.firefox.bin", "/root/data/firefox/firefox");
			WebDriver web =  new FirefoxDriver();//firefox 19.0   selenium 2.31.0
			
			logger.info("====加载完毕，打开页面==");
			
			web.get("http://dealer.zol.com.cn/beijing/");
			logger.info("====打开完毕，退出浏览器===");
			web.quit();
			
			//尊敬的用户，您本次绑定手机所使用的验证码是000560（验证码30分钟后失效）【博雅彩】
		} catch (Exception e) {
			logger.error("不能驱动浏览器",e);
			e.printStackTrace();
		}
	}

}
