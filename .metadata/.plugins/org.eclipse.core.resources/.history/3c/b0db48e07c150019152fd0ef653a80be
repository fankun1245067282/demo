package mydocx;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WorkClass {
	public static void main(String[] args) throws IOException {
		File excelFile = new File("D:\\doc\\债权转让3601-3900-何健锋.xls");
		List<Map<String, String>> list = ExcelUtil.parseExcelFile(excelFile);
		File docsrcFile = new File("D:\\doc\\ZQZR-000X1.docx");
		File dictory = new File("D:/doc/doc2/");
		if(dictory.isDirectory()) {
			File[] files = dictory.listFiles();
			for(File f:files) {
				f.delete();
			}
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String,String> map = list.get(i);
			String fileName = "D:/doc/doc2/债权转让协议-ZQZR-"+map.get("MYZQZR")+".docx";
			File dectFile = new File(fileName);
			FileUtil.copyFileUsingFileStreams(docsrcFile, dectFile);
			 // 替换文档关键字
	        WordUtil.generateWord(map, dectFile.getAbsolutePath(), "D:\\doc\\new.docx");
		}
		
		
	}
}
