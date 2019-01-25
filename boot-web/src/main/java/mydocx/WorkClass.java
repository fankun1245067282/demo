package mydocx;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WorkClass {
	public static void main(String[] args) throws IOException {
		String rootPath = "D:\\doc\\";
		String fileName = "债权转让0001-0300-张康维.xls";//导入的excel文件
		String templateName = "ZQZR-000X1.docx";//模板名称
		String folderName = fileName.substring(0,fileName.indexOf("."));
		System.out.println("folderName："+folderName);
		File excelFile = new File(rootPath+fileName);
		List<Map<String, String>> list = ExcelUtil.parseExcelFile(excelFile);
		File docsrcFile = new File(rootPath+templateName);
		File dictory = new File(rootPath+folderName);
		if(dictory.exists()) {
			if(dictory.isDirectory()) {
				File[] files = dictory.listFiles();
				for(File f:files) {
					f.delete();
				}
			}else {
				dictory.delete();
				dictory.mkdirs();
			}
		}else {
			dictory.mkdirs();
		}

		for (int i = 0; i < list.size(); i++) {
			Map<String,String> map = list.get(i);
			String subfileName = rootPath+folderName+"\\债权转让协议-ZQZR-"+map.get("MYZQZR")+".docx";
			File dectFile = new File(subfileName);
			FileUtil.copyFileUsingFileStreams(docsrcFile, dectFile);
			 // 替换文档关键字
	        WordUtil.generateWord(map, dectFile.getAbsolutePath(), "D:\\doc\\new.docx");
		}
		
		
	}
}
