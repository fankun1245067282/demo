package mydocx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ExcelUtil {
	  /**
     * 参考ExcelParseServiceImpl.parseHonorChequeExcelFile
     *
     * @param file 上传的文件
     * @return
     * @throws IOException
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);
	  
    public static List<Map<String, String>> parseExcelFile(File file) throws IOException {
        List<Map<String, String>> dataList = new ArrayList<>();
        boolean isXlsFormat = file.getName().endsWith(".xls");
        try {
            InputStream inputStream = new FileInputStream(file);
            System.out.println("file.exists():"+file.exists());
            Workbook wb = (isXlsFormat ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream));
            Sheet sheet = wb.getSheetAt(0);        //获得第一个表单
            Iterator<Row> rows = sheet.rowIterator();    //获得第一个表单的迭代器

            if (rows.hasNext()) {//跳过表头部分row.getRowNum() < 2
                rows.next();
            }

            Map<String, String> rowMap = null;
                while (rows.hasNext()) {
                    try {
                        Row row = rows.next();    //获得行数据
                        rowMap = generateDataByRow(row);
                     	dataList.add(rowMap);
                    } catch (Throwable t) {
                        LOGGER.error("exception", t);
                    }
                }
  

            inputStream.close();
        } catch (Throwable t) {
            LOGGER.error("exception", t);
            throw new RuntimeException(t);
        }
        return dataList;
    }

    /**
     * 企业融资方
     *
     * @param row
     * @return
     */
    private static Map<String, String> generateDataByRow(Row row) {
        Map<String, String> map = new HashMap<>();
        try {
            Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
            int cellnum = 0;
            String MYZQZR = row.getCell(cellnum++).getStringCellValue();
            String[] arr = MYZQZR.split("-");
            map.put("MYZQZR", arr[1]);//编号
            String USERNAME = row.getCell(cellnum++).getStringCellValue();
            map.put("USERNAME", USERNAME);//甲方名称
            String CARDNUMBER = row.getCell(cellnum++).getStringCellValue();
            map.put("CARDNUMBER", CARDNUMBER);//身份证件号
            long PHONENUMBER = (long)(row.getCell(cellnum++).getNumericCellValue());
            map.put("PHONENUMBER", PHONENUMBER+"");//联系电话
            
            DecimalFormat df=new DecimalFormat(".##");
            double ABCDEF1 = (double)(row.getCell(cellnum++).getNumericCellValue());
            String ABCDEF1Str =  improstr(df.format(ABCDEF1));
            map.put("ABCDEF1", ABCDEF1Str);//剩余在贷本息合计
            
            double ABCDEF2 = (double)(row.getCell(cellnum++).getNumericCellValue());
            String ABCDEF2Str = improstr(df.format(ABCDEF2));
            map.put("ABCDEF2", ABCDEF2Str);//剩余在贷本金
            
            double ABCDEF3 = (double)(row.getCell(cellnum++).getNumericCellValue());
            String ABCDEF3Str = improstr(df.format(ABCDEF3));
            map.put("JKL", ABCDEF3Str);//利息
            
            String AERA = row.getCell(cellnum++).getStringCellValue();
            map.put("AERA", AERA);//所属区域
 
        } catch (Exception e) {
            LOGGER.error("ErpFinancingPartnerControllerException", e);
            map.put("msg", "行字段解析错误");
        }
        return map;
    }

    private static String improstr(String str) {
        if(str.indexOf(".")==str.length()-2) {
        	str+="0";
        }
        return str;
    }
    private String trimstr(String str) {
        return str == null ? null : str.trim();
    }

//    @RequestMapping(value = "/erp/financing/exportFinancingPartnerExcel", method = RequestMethod.GET)
//    public void exportFinancingPartnerExcel(HttpServletRequest request, HttpServletResponse servletResponse) {
//        try {
//            List<ZserpFinancingPartnerExportVo> list = iFinanceingPartnerRemoteService.queryFinancingPartnerLoanInfo();
//            Workbook wkb = financingPartnerXlsService.genWorkbook(list);
//
//            //输出Excel文件
//            String fileName = "融资方平台借款信息" + com.ccmtjf.common.util.DateUtil.getFormatDate(new Date(), com.ccmtjf.common.util.DateUtil.PATTERN_yyyyMMddHHmmss) + ".xlsx";
//            servletResponse.setHeader("Content-disposition", "attachment; filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
//            servletResponse.setContentType("application/vdn.ms-excel;charset=utf-8");
//            OutputStream output = servletResponse.getOutputStream();
//            wkb.write(output);
//            output.close();
//
//            //
//
//        } catch (Throwable t) {
//            throw new RuntimeException(t.getMessage());
//        }
//    }
    public static void main(String[] args) throws IOException {
		File f = new File("D:\\doc\\ZQZR2101-2400.xls");
		List<Map<String, String>> list = parseExcelFile(f);
//		System.out.println(list);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
