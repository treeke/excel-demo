package com.excel;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelDemo {
	
	@Autowired
	private ExcelDao dao;
	
	@Autowired
	private ExcelUtil utils;
	
	public void make() throws Exception {
		List<Map<String, String>> queryAll = dao.queryAll();
		List<Map<String, String>> Feild = dao.queryFeild();
		utils.exportExcel("ExcelDemo", queryAll, Feild);
	}
	

}
