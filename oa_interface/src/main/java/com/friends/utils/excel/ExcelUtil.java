package com.friends.utils.excel;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.friends.utils.BeanHelper;
import com.friends.utils.TimeHelper;

/**
 * list<map>或list<object>导出为Excel 名称：ExcelHanlder.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年6月20日 上午9:27:42 <br>
 * 
 * @since 2017年6月20日
 * @authour ChenRenhao
 */
public class ExcelUtil {

	// 默认高度
	private static short DEFAULT_ROW_HEIGHT = 400;
	// 默认宽度
	private static int DEFAULT_CELL_WIDTH = 3000;

	// 向已有XSSFSheet追加行
	// 第一行不能加上+1
	public static void appendRowToSheet(XSSFSheet sheet, String[] contents,boolean isFirstRow) {
		Integer lastRow = sheet.getLastRowNum();
		if (!isFirstRow) {
			lastRow = lastRow + 1;
		}
		XSSFRow newRow = sheet.createRow(lastRow);
		XSSFWorkbook workbook = sheet.getWorkbook();
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN); // 下边框  
		style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框  
		style.setBorderTop(CellStyle.BORDER_THIN);// 上边框  
		style.setBorderRight(CellStyle.BORDER_THIN);// 右边框  
		int index = 0;
		for (String cellContent : contents) {
			XSSFCell newCell = newRow.createCell(index);
			newCell.setCellType(Cell.CELL_TYPE_STRING);
			newCell.setCellValue(cellContent);
			newCell.setCellStyle(style);
			index++;
		}
	}

	/**
	 * @name：
	 * @desc：
	 * @param sheet
	 * @param contents
	 * @param isFirstRow
	 * @param color IndexedColors.GREY_25_PERCENT.getIndex() poi提供的颜色枚举
	 * @author ChenRenhao
	 * @time:  2017年8月2日
	 */
	public static void appendRowToSheetWithColor(XSSFSheet sheet, String[] contents,boolean isFirstRow,short color) {
		Integer lastRow = sheet.getLastRowNum();
		if (!isFirstRow) {
			lastRow = lastRow + 1;
		}
		XSSFRow newRow = sheet.createRow(lastRow);
		XSSFWorkbook workbook = sheet.getWorkbook();
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(color);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN); // 下边框  
		style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框  
		style.setBorderTop(CellStyle.BORDER_THIN);// 上边框  
		style.setBorderRight(CellStyle.BORDER_THIN);// 右边框  
		int index = 0;
		for (String cellContent : contents) {
			XSSFCell newCell = newRow.createCell(index);
			newCell.setCellType(Cell.CELL_TYPE_STRING);
			newCell.setCellValue(cellContent);
			newCell.setCellStyle(style);
			index++;
		}
	}
	

	
	public static void appendRowToSheetWithoutStyle(XSSFSheet sheet, String[] contents,boolean isFirstRow) {
		Integer lastRow = sheet.getLastRowNum();
		if (!isFirstRow) {
			lastRow = lastRow + 1;
		}
		XSSFRow newRow = sheet.createRow(lastRow);
		int index = 0;
		for (String cellContent : contents) {
			XSSFCell newCell = newRow.createCell(index);
			newCell.setCellType(Cell.CELL_TYPE_STRING);
			newCell.setCellValue(cellContent);

			index++;
		}
	}
	
	
	// 向已有XSSFSheet追加行（支持筛选列,传入object）
	public static void appendRowObjectToSheetSelective(XSSFSheet sheet, Object object, String[] columns) {
		try {
			Class clazz = object.getClass();
			XSSFRow newRow = sheet.createRow(sheet.getLastRowNum() + 1);
			for (int i = 0; i < columns.length; i++) {
				XSSFCell newCell = newRow.createCell(i);
				newCell.setCellType(Cell.CELL_TYPE_STRING);
				Field field = clazz.getDeclaredField(columns[i]);
				field.setAccessible(true);
				Object value;
				value = field.get(object);
				if (value == null) {
					value = "";
				}
				// 如果为时间类型 需要进行单独格式化
				if (value instanceof Date) {
					value = TimeHelper.dateToStrLong((Date) value);
				}
				newCell.setCellValue(value.toString());
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	// 向已有XSSFSheet追加行（支持筛选列,传入map）
	public static void appendRowMapToSheetSelective(XSSFSheet sheet, Map<String, Object> map, String[] columns) {
		XSSFRow newRow = sheet.createRow(sheet.getLastRowNum()+1);
		for (int i = 0; i < columns.length; i++) {
			XSSFCell newCell = newRow.createCell(i);
			newCell.setCellType(Cell.CELL_TYPE_STRING);
			Object value = map.get(columns[i]);
			if (value == null) {
				value = "";
			}
			// 如果为时间类型 需要进行单独格式化
			if (value instanceof Date) {
				value = TimeHelper.dateToStrLong((Date) value);
			}
			newCell.setCellValue(value.toString());
		}
	}

	// 向已有XSSFSheet追加空行列
	public static void appendEmptyRowToSheet(XSSFSheet sheet, int num) {
		for (int i = 0; i < num; i++) {
			System.out.println(sheet.getLastRowNum());
			XSSFRow newRow = sheet.createRow(sheet.getLastRowNum()+1);
			XSSFCell newCell = newRow.createCell(1);
			newCell.setCellValue("");
		}
	}

	/**
	 * 导出excel
	 * 
	 * @param book
	 *            工作簿对象，【可选】
	 * @param hanlder
	 *            自定义类型处理【可选】
	 * @param titles
	 *            列标题
	 * @param columns
	 *            列名（Map类型才需要，根据名称取出要输出的列及值，自定义可选）
	 * @param columnsWidth
	 *            宽度
	 * @param height
	 *            行高
	 * @param sheetTitle
	 *            表标题
	 * @param datas
	 *            数据 list<map> 或者list<object>
	 * @return
	 */
	@SuppressWarnings("all")
	public static XSSFWorkbook exportExcel(XSSFWorkbook book, ExcelTypeHanlder hanlder, String[] titles,
			String[] columns, Integer[] columnsWidth, Short height, String sheetTitle, List datas) throws Exception {

		if (book == null) {
			book = new XSSFWorkbook();
		}

		int size = DEFAULT_CELL_WIDTH;

		// 列大小
		if (columnsWidth != null && columnsWidth.length == 1) {
			size = columnsWidth[0];
		}
		if (height == null) {
			height = DEFAULT_ROW_HEIGHT;
		}
		XSSFSheet sheet = book.createSheet(sheetTitle);
		int rowindex = 0;
		XSSFRow firstrow = sheet.createRow(rowindex);
		rowindex++;
		sheet.setDefaultColumnWidth(size);
		firstrow.setHeight(height);

		XSSFFont font = book.createFont();
		font.setBold(true);
		XSSFCellStyle cellstyle = book.createCellStyle();
		cellstyle.setFont(font);
		cellstyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 标题
		if (titles != null) {
			int index = 0;
			for (String title : titles) {
				XSSFCell cell = firstrow.createCell(index);
				cell.setCellStyle(cellstyle);
				cell.setCellValue(title);
				// 列宽度设置
				if (columnsWidth == null || columnsWidth.length == 0 || columnsWidth.length == 1) {
					sheet.setColumnWidth(cell.getColumnIndex(), size);
				} else {
					if ((columnsWidth.length - 1) >= index) {
						sheet.setColumnWidth(cell.getColumnIndex(), columnsWidth[index] == null ? size
								: columnsWidth[index]);
					} else {
						sheet.setColumnWidth(cell.getColumnIndex(), size);
					}
				}
				index++;
			}
		}
		if (datas == null) {
			return book;
		}

		// 写入数据
		for (Object data : datas) {

			// map 类型处理
			if (data instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) data;
				XSSFRow row = sheet.createRow(rowindex);
				rowHanlder(row, columns, hanlder, map);
				row.setHeight(DEFAULT_ROW_HEIGHT);
				rowindex++;
			} else {
				XSSFRow row = sheet.createRow(rowindex);
				row.setHeight(DEFAULT_ROW_HEIGHT);
				if (hanlder != null) {
					hanlder.typeHanlder(data, row);
				}
				objectHanlder(columns, data, row, hanlder);
				rowindex++;
				// 其他处理
			}
		}
		return book;
	}

	/**
	 * 将book内容输出到OutputStream
	 * 
	 * @param book
	 * @param name
	 * @param resp
	 * @param req
	 * @throws IOException
	 * @变更记录 2017年6月20日 上午9:26:19 ChenRenhao创建
	 */
	public static void writer(XSSFWorkbook book, String name, HttpServletResponse resp, HttpServletRequest req)
			throws IOException {
		ServletOutputStream out = null;
		try {
			String userAgent = req.getHeader("User-Agent");
			// name.getBytes("UTF-8")处理safari的乱码问题
			byte[] bytes = userAgent.contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8");
			// 各浏览器基本都支持ISO编码
			name = new String(bytes, "ISO-8859-1");

			resp.setCharacterEncoding("UTF-8");
			resp.addHeader("Content-type", " application/octet-stream");
			resp.addHeader("Content-Disposition", new StringBuffer().append("attachment;filename=").append(name)
					.toString());
			out = resp.getOutputStream();
			book.write(out);

		} finally {
			// if(book!=null){book.close();}
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 写入list<map>类型的行数据
	 * 
	 * @param row
	 * @param columns
	 * @param hanlder
	 * @param map
	 * @变更记录 2017年6月20日 上午9:37:32 ChenRenhao创建
	 */
	public static void rowHanlder(XSSFRow row, String[] columns, ExcelTypeHanlder hanlder, Map map) {
		int i = 0;
		for (String column : columns) {
			XSSFCell cell = row.createCell(i);
			Object val = map.get(column);
			if (hanlder != null && val == null) {
				Object temp = hanlder.dataNullHander(column, map);
				cell.setCellValue(temp != null ? temp.toString() : "");
			} else {
				// 如果为时间类型 需要进行单独格式化
				if (val instanceof Date) {
					val = TimeHelper.dateToStrLong((Date) val);
				}
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(val != null ? val.toString() : "");
			}
			i++;
		}
	}

	/**
	 * 写入list<object>类型的行数据 实质是将object转map,然后调用map类型接口。
	 * 
	 * @param columns
	 * @param data
	 * @param row
	 * @param hanlder
	 * @throws Exception
	 * @变更记录 2017年6月20日 上午9:37:06 ChenRenhao创建
	 */
	public static void objectHanlder(String[] columns, Object data, XSSFRow row, ExcelTypeHanlder hanlder)
			throws Exception {
		BeanInfo bean = Introspector.getBeanInfo(data.getClass());
		PropertyDescriptor[] ps = bean.getPropertyDescriptors();
		Map<String, Object> map = new HashMap<String, Object>(ps.length);
		for (PropertyDescriptor p : ps) {
			String name = p.getName();
			Method m = p.getReadMethod();
			if (m == null) {
				continue;
			}
			Object obj = m.invoke(data);
			if (obj != null) {
				map.put(name, obj);
			}
		}
		rowHanlder(row, columns, hanlder, map);
		row.setHeight(DEFAULT_ROW_HEIGHT);
	}

	/**
	 * 设置了一些默认参数
	 * 
	 * @param book工作簿对象
	 *            ，【可选】
	 * @param titles
	 *            标题
	 * @param columns
	 *            列名（Map类型处理，自定义可选）
	 * @param sheetTitle
	 *            表标题
	 * @param datas
	 *            数据
	 * @return
	 */
	public static XSSFWorkbook exportExcel(XSSFWorkbook book, String[] titles, String[] columns, String sheetTitle,
			List datas) throws Exception {
		return exportExcel(book, null, titles, columns, null, null, sheetTitle, datas);
	}

	/**
	 * 设置了一些默认参数
	 * 
	 * @param titles
	 *            标题
	 * @param columns
	 *            列名（Map类型处理，自定义可选）
	 * @param sheetTitle表标题
	 * @param datas
	 *            数据
	 * @return
	 */
	@SuppressWarnings("all")
	public static XSSFWorkbook exportExcel(String[] titles, String[] columns, String sheetTitle, List datas,
			ExcelTypeHanlder hanlder) throws Exception {
		return exportExcel(null, hanlder, titles, columns, null, null, sheetTitle, datas);
	}

	/**
	 * 设置了一些默认参数
	 * 
	 * @param titles
	 * @param columns
	 * @param sheetTitle
	 * @param datas
	 * @return
	 * @throws Exception
	 * @变更记录 2017年6月20日 上午9:41:47 ChenRenhao创建
	 */
	public static XSSFWorkbook exportExcel(String[] titles, String[] columns, String sheetTitle, List datas)
			throws Exception {
		return exportExcel(null, null, titles, columns, null, null, sheetTitle, datas);
	}

	// 自定义处理对象回调
	public static abstract class ExcelTypeHanlder<T> {
		// 类型处理
		public void typeHanlder(T data, XSSFRow row) {

		}

		// 空数据处理
		public Object dataNullHander(String column, T obj) {
			return null;
		}
	}

	/******************************************** Excel导入 *******************************************/
	
	//检查第一有效行是否含有指定字段
	public static boolean checkImportExecl(InputStream inputStream, int[] columnIndexs,
			String[] columnNames) {
		try {
			
			XSSFWorkbook book = new XSSFWorkbook(inputStream);
			// 遍历sheets
			XSSFSheet sheet;
			XSSFRow row;

			int sheetsNum = book.getNumberOfSheets();
			//只取第一个sheet
//			for (int i = 0; i < sheetsNum; i++) {
			sheet = book.getSheetAt(0);//i
			// 遍历rows
			int firstRowNum = sheet.getFirstRowNum();
			row = sheet.getRow(firstRowNum);
			// 遍历cells
			XSSFCell cell;
			for (int k = 0; k < columnIndexs.length; k++) {
				cell = row.getCell(columnIndexs[k]);
				// 对cell类型进行判断 转为合理的字符串 交与map转对象工具去处理
				String columnName = (String)getCellData(cell);
				if (!(columnNames[k].equals(columnName))) {
					return false;
				}
			}
			return true;
//			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * @name：从Excel加载有效数据
	 * @desc：
	 * @param clazz
	 * @param inputStream
	 * @param columnIndexs
	 * @param columnNames
	 * @param startRowNum
	 * @return
	 * @author ChenRenhao
	 * @time:  2017年7月11日
	 */
	public static <T> List<T> loadListFromExecl(Class<T> clazz, InputStream inputStream, int[] columnIndexs,
			String[] columnNames,Integer startRowNum) {
		try {
			ArrayList<T> ts = new ArrayList<T>();

			XSSFWorkbook book = new XSSFWorkbook(inputStream);
			// 遍历sheets
			XSSFSheet sheet;
			XSSFRow row;

			int sheetsNum = book.getNumberOfSheets();
			//只取第一个sheet
			sheet = book.getSheetAt(0);//i 
			// 遍历rows
			int firstRowNum = sheet.getFirstRowNum();
			int lastRowNum = sheet.getLastRowNum();
			// 有些单元格 可能有空格，xlsx也认为有值。
			
			System.out.println("读取的行数是:"+lastRowNum);
			
			int index = 0;
			for (int i = 1; i <= lastRowNum; i++) {
				XSSFRow row3 = sheet.getRow(i);
//				XSSFCell cell = row3.getCell(0);
//				XSSFRow row2 = sheet.getRow(i);
				if(row3 != null && row3.getCell(0) != null && StringUtils.isNotEmpty(row3.getCell(0).toString())){
					System.out.println("------>这个:"+row3.getCell(0));
					index ++;
				}
			}
			
			for (int j = firstRowNum; j <= index; j++) {
				// 从startRowNum开始计算
				if (j < startRowNum) {
					continue;
				}
				HashMap<String, Object> excelMap = new HashMap<String, Object>();
				T t = null;
				try {
					t = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				row = sheet.getRow(j);
				// 遍历cells
				XSSFCell cell;
				for (int k = 0; k < columnIndexs.length; k++) {
					cell = row.getCell(columnIndexs[k]);
					// 对cell类型进行判断 转为合理的字符串 交与map转对象工具去处理
					Object cellObject = getCellData(cell);
					excelMap.put(columnNames[k], cellObject);
					System.out.println(columnNames[k]);
				}
				BeanHelper.convertMapToObject(excelMap, t);
				ts.add(t);
			}
//			}
			return ts;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 对Excel数据类型进行处理
	 * 
	 * @param cell
	 * @param formula
	 * @return
	 * @变更记录 2017年6月20日 下午3:13:01 ChenRenhao创建
	 */
	private static Object getCellData(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			// 字符串中也有符合时间格式的
			return cell.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return "";
		default:
			return null;
		}
	}

	// 写测试
	public static void writeTest() {
		try {
//			String[] titles = { "ID", "模板名称", "模板类型", "创建时间" };
//			String[] columns = { "templateId", "templateName", "templateType", "createTime" };
//			String sheetTitle = "模板导出测试";
//			List<Template> templates = new ArrayList<Template>();
//			for (int i = 0; i < 3; i++) {
//				Template template = new Template();
//				template.setCreateTime(new Date());
//				template.setTemplateName("name" + i);
//				template.setTemplateId("uuid" + 3 * i);
//				template.setTemplateType(2 + i);
//				templates.add(template);
//			}

			FileOutputStream fileOutputStream = null;

			fileOutputStream = new FileOutputStream(new File("C:/Users/Administrator/Desktop/template.xlsx"));
			XSSFWorkbook book = new XSSFWorkbook();

//			 book = ExcelUtil.exportExcel(titles, columns, sheetTitle, templates);

			String[] colunmNames = { "会员名称", "会员类型", "会员标识","AAAA","GGGGGGG","FFFFFFFF" };
			String[] atterNames = { "memberName", "memberType", "memberCode" };

			XSSFSheet sheet = book.createSheet("会员类型导入模板");
//
			ExcelUtil.appendRowToSheetWithColor(sheet, colunmNames,true,IndexedColors.YELLOW.index);
			ExcelUtil.appendRowToSheetWithColor(sheet, colunmNames,false,IndexedColors.YELLOW.index);
			ExcelUtil.appendRowToSheetWithColor(sheet, colunmNames,false,IndexedColors.YELLOW.index);
			
			sheet.addMergedRegion(new CellRangeAddress(0, 0,0,2));
			sheet.addMergedRegion(new CellRangeAddress(0, 0,3,5));
			XSSFWorkbook workbook = sheet.getWorkbook();
			XSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(CellStyle.BORDER_THIN); // 下边框  
			style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框  
			style.setBorderTop(CellStyle.BORDER_THIN);// 上边框  
			style.setBorderRight(CellStyle.BORDER_THIN);// 右边框  
			
			style.setAlignment(HorizontalAlignment.CENTER);
			sheet.getRow(0).getCell(0).setCellStyle(style);
			sheet.getRow(0).getCell(1).setCellStyle(style);
			sheet.getRow(0).getCell(2).setCellStyle(style);
			sheet.getRow(0).getCell(3).setCellStyle(style);
			sheet.getRow(0).getCell(4).setCellStyle(style);
			sheet.getRow(0).getCell(5).setCellStyle(style);
			
//			// 加入一条范例
			/*MemberLevel memberLevel = new MemberLevel();
			memberLevel.setMemberName("黄金会员");
			memberLevel.setMemberType("会员");
			memberLevel.setMemberCode("03");
			ExcelUtil.appendRowObjectToSheetSelective(sheet, memberLevel, atterNames);*/

			book.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 读取测试
	public static void readTest() {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream("C:/Users/Administrator/Desktop/template.xlsx");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int[] columnIndexs = { 0, 1, 2, 3 };
		String[] columnNames = { "templateId", "templateName", "templateType", "createTime" };

		//List<Template> templates = loadListFromExecl(Template.class, inputStream, columnIndexs, columnNames,1);
		//System.out.println(templates.size());
	}

	// 写测试
	public static void appendTest() {
		/*try {
			String[] titles = { "ID", "模板名称", "模板类型", "创建时间" };
			String[] columns = { "templateId", "templateName", "templateType", "createTime" };
			String sheetTitle = "模板导出测试";
			List<Template> templates = new ArrayList<Template>();
			for (int i = 0; i < 3; i++) {
				Template template = new Template();
				template.setCreateTime(new Date());
				template.setTemplateName("name" + i);
				template.setTemplateId("uuid" + 3 * i);
				template.setTemplateType(2 + i);
				templates.add(template);
			}

			FileOutputStream fileOutputStream = null;

			InputStream inputStream = new FileInputStream("C:/Users/Administrator/Desktop/template.xlsx");
			XSSFWorkbook book = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = book.getSheetAt(book.getActiveSheetIndex());
			appendEmptyRowToSheet(sheet, 2);
			appendRowObjectToSheetSelective(sheet, templates.get(1), columns);
			inputStream.close();
			fileOutputStream = new FileOutputStream(new File("C:/Users/Administrator/Desktop/template.xlsx"));
			book.write(fileOutputStream);
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/

	}

	public static void main(String[] args) {
		writeTest();
//		 readTest();
		// appendTest();
		System.out.println("ok");
	}

}
