package Demo;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;




public class TestDemo {

	private static String fformatStr = "https://api4.udcredit.com/dsp-front/4.1/dsp-front/default/pubkey/%s/product_code/%s/out_order_id/%s/signature/%s";
	public static String apiCall(String url, String pubkey, String secretkey, String serviceCode, String outOrderId,
			Map<String, String> parameter) throws Exception {

		if (parameter == null || parameter.isEmpty())
			throw new Exception("error ! the parameter Map can't be null.");

		StringBuffer bodySb = new StringBuffer("{");
		for (Map.Entry<String, String> entry : parameter.entrySet()) {
			bodySb.append("'").append(entry.getKey()).append("':'").append(entry.getValue()).append("',");
		}
		String bodyStr = bodySb.substring(0, bodySb.length() - 1) + "}";
		String signature = md5(bodyStr + "|" + secretkey);
		url = String.format(fformatStr, pubkey, serviceCode, System.currentTimeMillis() + " ", signature);
		System.out.println("requestUrl=>" + url);
		System.out.println("request parameter body=>" + bodyStr);
		HttpResponse r = makePostRequest(url, bodyStr);
		return EntityUtils.toString(r.getEntity());
	}

	private static final CloseableHttpClient client = HttpClientBuilder.create().build();

	private static HttpResponse makePostRequest(String uri, String jsonData)
			throws ClientProtocolException, IOException {

		HttpPost httpPost = new HttpPost(URIUtil.encodeQuery(uri, "utf-8"));
		httpPost.setEntity(new StringEntity(jsonData, "UTF-8"));
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json; charset=utf-8");
		return client.execute(httpPost);

	}

	private static String md5(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data.toString().getBytes("utf-8"));
		return bytesToHex(md.digest());
	}

	private static String bytesToHex(byte[] ch) {
		StringBuffer ret = new StringBuffer("");
		for (int i = 0; i < ch.length; i++)
			ret.append(byteToHex(ch[i]));
		return ret.toString();
	}

	/**
	 * 字节转换�?16进制字符�?
	 */
	private static String byteToHex(byte ch) {
		String str[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		return str[ch >> 4 & 0xF] + str[ch & 0xF];
	}

	/**
	 * 注：demo中调用方法的参数均为范例，不具有实际意义，请商户自行替换，测试接口的入参信息要以实际为准并有效
	 *  pubkey、secretkey：key值由有盾相关人员下发至商户联系人邮箱，请商户自行获取替换
	 *  product_code：产品编号已在'有盾实名验证服务接口文档'中第1节1.4小节列出，请商户按照实际接入产品替换对应的产品编码
	 *  out_order_id：商户订单号由商户自定义传入的唯一且不大于32位的字符串
	 */
	public static void testDemo3(){
		try {
			String url="https://api4.udcredit.com/dsp-front/4.1/dsp-front/default/pubkey/%s/product_code/%s/out_order_id/%s/signature/%s";
			String publicKey="9beba297-4a3d-4a11-af4c-ff42df285eb2";
			String privateKey="5ee841ce-59d4-40b2-b0de-0c21128dbd13";
			String merid="O1001S0401";
			
			Map<String,String> map = new HashMap<>();
			map.put("id_no", "341224198801061514");
			map.put("id_name", "樊坤");
			map.put("bank_card_no", "6217001210068257664");
			map.put("mobile", "18221537853");
//			id_name	身份证姓名	Y	String	身份证姓名
//			id_no	身份证号码	Y	String	身份证号码(15或18位)
//			bank_card_no	银行卡号	Y	String	银行卡号
//			mobile	手机号码	Y	String	开户时预留手机号码
			//https://api4.udcredit.com
			
			String outOrderId = "zsjr"+System.currentTimeMillis();
			System.out.println("outOrderId:"+outOrderId);
			
			String result = TestDemo.apiCall(url,publicKey, privateKey, merid,outOrderId, map);
			System.err.println(result);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	//有盾官方文档
	//https://static.udcredit.com/doc/idsafe/dataservice/V40/
	public static void main(String[] args) {
		testDemo3();
	}
}
