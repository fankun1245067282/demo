package mydocx;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
 
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
 
/**
 *
 * @author andy
 * @date 2016年8月5日
 */
public class WordUtil {
 
    public static void generateWord(Map<String, String> param, String fileSrc, String fileDest) {
        XWPFDocument doc = null;
        OPCPackage pack = null;
        try {
            pack = POIXMLDocument.openPackage(fileSrc);
            doc = new XWPFDocument(pack);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fopts = null;
        try {
            fopts = new FileOutputStream(fileDest);
            doc.write(fopts);
            pack.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeStream(fopts);
        }
    }
 
    public static void closeStream(FileOutputStream fopts) {
        try {
            fopts.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 处理段落,替换关键字
     * 
     * @param paragraphList
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, String> param, XWPFDocument doc)
            throws InvalidFormatException, FileNotFoundException {
        if (paragraphList != null && paragraphList.size() > 0) {
            // 遍历所有段落
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                     System.out.println("text==" + text);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Entry<String, String> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {// 在配置文件中有这个关键字对应的键
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {// 文本替换
                                    // System.out.println("key==" + key);
                                    if (text.contains(key)) {
                                        text = text.replace(key, value.toString());
                                    }
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }
}