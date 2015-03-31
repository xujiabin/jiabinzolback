package com.zol.backserver.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.zol.backserver.dao.CommonDao;
import com.zol.backserver.dao.bean.TData;
import com.zol.backserver.dao.bean.TUrl;
import com.zol.backserver.util.date.DateFormatUtil;

/**
 * 抓取数据
 */
@Transactional
public class DataService {
	
	private Logger logger = Logger.getLogger(DataService.class);
	
	@Autowired
	CommonDao dao;
	
	//抓取错误的数据
	int errorCount = 0;
		
		
	public void getDataMethod(){
		logger.info("==========开始抓取数据==========");
		Map<Integer,String> map = new HashMap<Integer,String>();
		List<TUrl> list = null;
		try {
			  list = dao.queryList(" from TUrl ", null);
		} catch (Exception e1) {
			logger.error("",e1);
			e1.printStackTrace();
		}
		
		//String url = "http://detail.zol.com.cn/368/367005/extraprice_6310_1.shtml";
			for(TUrl t : list){
				String url = t.getUrl();
				try {
					savedata(t.getId(),url);
				} catch (Exception e) {
					map.put(t.getId(), url);
					e.printStackTrace();
					logger.error("",e);
				}
				//暂停2秒进行抓取
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					
				}
			}
			logger.info("====进行错误抓取====");
			errorCount = 0;
			getErrorData(map);
		
	}
	
	
	private void getErrorData(Map<Integer,String> map){
		++errorCount;
		logger.info("==========抓取数据完毕，错误个数："+map.size()+"==========");
		if(map!=null && map.size()>0 && errorCount<5){
			//声明数组，记录key
			List<Integer> keys = new ArrayList<Integer>();
			for(Map.Entry<Integer, String> en : map.entrySet()){
				int id = en.getKey();
				try {
					savedata(id,en.getValue());
				} catch (Exception e) {
					try {
						Thread.sleep(1000 * 5);
					} catch (Exception e1) {
					}
					logger.error("",e);
					continue;
				}
				keys.add(id);
			}
			for(Integer key : keys){
				map.remove(key);
			}
			logger.info("遍历完毕后map的个数："+map.size());
			keys = null;
			if(map.size()<1){
				map = null;
			}else{
				getErrorData(map);
			}
		}
	}
	
	
	
	/**
	 * 保存数据
	 */
	private void savedata(int id,String url)throws Exception{
		Document doc = getURLContentForGet(url);
		logger.info("url:"+url);
		String name = doc.select(".page-title").text();
		logger.info("name:"+name);
		String updatestr = doc.select(".update-time").text();
		updatestr = updatestr.substring(updatestr.indexOf("：")+1,updatestr.indexOf("）"));
		logger.info("updatestr:"+updatestr);
		
		TData td = new TData();
		td.setUrl(url);
		td.setName(new String(name.getBytes("utf-8"),"utf-8"));
		td.setUpdatestr(updatestr);
		td.setUpdates(DateFormatUtil.parse_yyyy_MM_dd_HH_mm(Calendar.getInstance().get(Calendar.YEAR)+"-"+updatestr));
		td.setCreatetime(new Date());
		td.setUrlid(id);
		dao.add(td);
	}
	
	
	
	/**
	 * get方式获取DOM
	 */
	private  Document getURLContentForGet(String url) throws Exception {
		Document doc = Jsoup.connect(url).get();
		return doc;
	}

}
