package mydocx;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
 
/**
 *
 * @author andy
 * @date 2016年8月5日
 */
public class Test2 {
 
    public static void main(String[] args) {
        Test2 test = new Test2();
        Map<String, String> param = new HashMap<String, String>();
        List<String> keyList = new ArrayList<String>();
        List<Object> valueList = new ArrayList<Object>();
 
        // 获取配置文件所有键值对
        Properties p = test.readP();// 配置文件读取的参数
        Iterator<Object> iterator = p.keySet().iterator();
        while (iterator.hasNext()) {
            keyList.add(iterator.next().toString());
        }
        Iterator<Object> iterator2 = p.keySet().iterator();
        while (iterator2.hasNext()) {
            valueList.add(p.getProperty(iterator2.next().toString()));
        }
        for (int i = 0; i < keyList.size(); i++) {
            System.out.println(keyList.get(i) + ":" + valueList.get(i));
//            param.put(keyList.get(i), valueList.get(i));
        }
        // 替换文档关键字
        WordUtil.generateWord(param, param.get("fileSrc").toString(), param.get("fileDest").toString());
    }
 
    /**
     * 读取配置文件
     * 
     * @return
     */
    private Properties readP() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return p;
    }
}