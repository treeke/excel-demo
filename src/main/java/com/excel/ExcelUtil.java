package com.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
/**
 * Excel导出实现类
 * @author 11277
 *
 * 2018.11.28
 */
@Component
public class ExcelUtil {

	public void exportExcel(String sheetName,List<Map<String,String>> list,List<Map<String,String>> field) {
		//生成Excel对象
		XSSFWorkbook workbook = makeExcel(sheetName,list,field);  
		//文件输出流输出文件
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(sheetName+".xlsx"));
			workbook.write(fos);
			fos.close();
		} catch (IOException e) {
			System.out.println("----------生成表格失败----------");
			e.printStackTrace();
		}finally {
			//输出流对象强制置空
			fos = null;
		}

	}

	/**
	 * 创建Excel对象，根据数据生成对应的表格文件
	 * @param sheetName  传入标题
	 * @return
	 */
	private XSSFWorkbook makeExcel(String sheetName,List<Map<String, String>> list, List<Map<String, String>> field) {
		//列名集合
		List<String> headers = new ArrayList<String>();
		//数据集合
		List<String> data = new ArrayList<String>();
		//行数标记
		int r = 0;
		//初始化头部数据
		headers = initHeaders(field, headers);
		//初始化表格数据
		data = initData(list, headers, data);

		// 声明一个工作薄  
		XSSFWorkbook workbook = new XSSFWorkbook();  
		// 生成一个表格  
		XSSFSheet sheet = workbook.createSheet(sheetName);
		/*//统一设置列宽 
        sheet.setDefaultColumnWidth(15);
        // 产生表格标题行  
        XSSFRow row = sheet.createRow(0);  
        for (int i = 0; i < headers.size(); i++) {  
            XSSFCell cell = row.createCell(i);  
            XSSFRichTextString text = new XSSFRichTextString(headers.get(i));  
            cell.setCellValue(text);
        }*/
		for (int i = 0; i < headers.size(); i++){  
			//单独设置每列的宽度
			sheet.setColumnWidth(i, headers.get(i).length()*400); // 单独设置每列的宽  
		}  

		// 创建第0行 也就是标题  
		XSSFRow row1 = sheet.createRow(r++);  
		row1.setHeightInPoints(50);// 设备标题的高度  
		// 第三步创建标题的单元格样式style2以及字体样式headerFont1  
		XSSFCellStyle style2 = workbook.createCellStyle();  
		style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
		style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
		//style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);  
		XSSFFont headerFont1 = (XSSFFont) workbook.createFont(); // 创建字体样式  
		headerFont1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
		headerFont1.setFontName("黑体"); // 设置字体类型  
		headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小  
		style2.setFont(headerFont1); // 为标题样式设置字体样式  

		XSSFCell cell1 = row1.createCell(0);// 创建标题第一列  
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,  
				headers.size() - 1)); // 合并第0到第17列  
		cell1.setCellValue(sheetName); // 设置值标题  
		cell1.setCellStyle(style2); // 设置标题样式  



		// 创建第1行 也就是表头  
		XSSFRow row = sheet.createRow(r++);  
		row.setHeightInPoints(37);// 设置表头高度  
		// 第四步，创建表头单元格样式 以及表头的字体样式  
		XSSFCellStyle style = workbook.createCellStyle();  
		style.setWrapText(true);// 设置自动换行  
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式  

		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
		style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
		style.setBorderTop(XSSFCellStyle.BORDER_THIN);  

		XSSFFont headerFont = (XSSFFont) workbook.createFont(); // 创建字体样式  
		headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD); // 字体加粗  
		headerFont.setFontName("黑体"); // 设置字体类型  
		headerFont.setFontHeightInPoints((short) 10); // 设置字体大小  
		style.setFont(headerFont); // 为标题样式设置字体样式  

		// 第四.一步，创建表头的列  
		for (int i = 0; i < headers.size(); i++){  
			XSSFCell cell = row.createCell(i);  
			cell.setCellValue(headers.get(i));  
			cell.setCellStyle(style);  
		}  

		// 第五步，创建单元格，并设置值  
		for (int i = 0; i < data.size();){  
			row = sheet.createRow(r++);  
			row.setHeight((short)1000);

			// 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中  
			XSSFCellStyle datastyle = workbook.createCellStyle();  
			datastyle.setWrapText(true);// 设置自动换行  
			datastyle  
			.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式  
			datastyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 左右居中  

			// 设置边框  
			datastyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
			datastyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
			datastyle.setBorderRight(XSSFCellStyle.BORDER_THIN);  
			datastyle.setBorderTop(XSSFCellStyle.BORDER_THIN);  
			XSSFCell datacell = null; 

			for (int j = 0; j < headers.size(); j++) {
				datacell = row.createCell(j);  
				datacell.setCellValue(data.get(i));  
				datacell.setCellStyle(datastyle); 
				++i;
			}

		}
		//返回文件对象
		return workbook;
	}
    
	/**
	 * 初始化头部数据
	 * @param list  查询的数据集合
	 * @param headers  初始化完成的头部数据
	 * @param data   初始化的数据集合
	 * @return
	 */
	private List<String> initData(List<Map<String, String>> list, List<String> headers, List<String> data) {
		for (Map<String, String> map : list) {
			for (int i = 0; i < headers.size(); i++) {
				if(map.get(headers.get(i)) == null) {
					map.put(headers.get(i), "空");
				}
				data.add(String.valueOf(map.get(headers.get(i))));
			}
		}
		return data;
	}

	/**
	 * 初始化头部数据 
	 * @param field  查询的属性集合
	 * @param headers  初始化的头部数据
	 * @return
	 */
	private List<String> initHeaders(List<Map<String, String>> field, List<String> headers) {
		for (Map<String, String> map : field) {
			String string = map.get("Field");
			headers.add(string);
		}
		return headers;
	}

}
