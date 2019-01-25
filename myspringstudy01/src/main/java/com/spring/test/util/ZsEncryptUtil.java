package com.spring.test.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class ZsEncryptUtil {

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public static String SHA256(final String strText) {
        return SHA(strText, "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public static String SHA512(final String strText) {
        return SHA(strText, "SHA-512");
    }

    /**
     * 字符串 SHA 加密
     *
     * @return
     */
    private static String SHA(final String strText, final String strType) {
        // 返回值
        String strResult = null;
        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                messageDigest.update(strText.getBytes());
                byte byteBuffer[] = messageDigest.digest();
                StringBuffer strHexString = new StringBuffer();
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }


    public static String DynamicEncrypt(String str, String key, String salt) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(key);  //拼接字符串
        String sha5str = SHA512(sb.toString()); //SHA512算法
        String sha5rstr = new StringBuffer(sha5str).reverse().toString(); // 字符串翻转
        String shastr = SHA256(sha5rstr + salt + sha5str + key);  //SHA256算法
        shastr = shastr.replaceFirst("a", "e");  //替换第一个"a"为"e"
        shastr = shastr.replaceFirst("4", "c");  //替换第一个"4"为"c"
        shastr = shastr.replaceFirst("8", "2");  //替换第一个"8"为"2"

        StringBuffer shaSb = new StringBuffer(shastr);
        System.out.println(shaSb.toString());
        shaSb.replace(24, 25, "0");  //将第24位（从0开始）替换为"0"
        shaSb.replace(51, 52, "0");  //将第51位（从0开始）替换为"0"
        return shaSb.toString();
    }


    public static Boolean VerfyDynamicEncrypt(String str, String key, String salt, String sin) {
        if (salt.length() < 6) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(key);
        String sha5str = SHA512(sb.toString());

        String sha5rstr = new StringBuffer(sha5str).reverse().toString();
        String shastr = SHA256(sha5rstr + salt + sha5str + key);
        shastr = shastr.replaceFirst("a", "e");
        shastr = shastr.replaceFirst("4", "c");
        shastr = shastr.replaceFirst("8", "2");

        StringBuffer shaSb = new StringBuffer(shastr);
        System.out.println(shaSb.toString());

        shaSb.replace(24, 25, "0");
        shaSb.replace(51, 52, "0");

        sin = new StringBuffer(sin).replace(24, 25, "0").replace(51, 52, "0").toString();

        if (sin.equals(shaSb.toString())) {
            return true;
        } else {
            return false;
        }

    }


    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("abcdef");
        System.out.println(sb.reverse().toString());
        System.out.println(sb.replace(2,4,"0"));
        String es = ZsEncryptUtil.DynamicEncrypt("15279184540", "1EfmYOu8BN2XS9l8", "abc111");
        System.out.println("===="+es);

        System.out.println("===="+ZsEncryptUtil.VerfyDynamicEncrypt("15279184540", "1EfmYOu8BN2XS9l8", "abc111", es));

    }


}
