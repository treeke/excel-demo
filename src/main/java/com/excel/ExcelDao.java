package com.excel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExcelDao {
	
	@Select("SELECT * FROM user")
	List<Map<String,String>> queryAll();
	
	@Select("SHOW COLUMNS FROM user")
	List<Map<String,String>> queryFeild();

}
