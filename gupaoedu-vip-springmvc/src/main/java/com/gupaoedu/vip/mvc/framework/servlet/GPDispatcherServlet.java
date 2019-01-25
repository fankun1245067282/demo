package com.gupaoedu.vip.mvc.framework.servlet;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gupaoedu.vip.mvc.framework.annotation.GPController;
import com.gupaoedu.vip.mvc.framework.annotation.GPRequestMapping;
import com.gupaoedu.vip.mvc.framework.annotation.GPRequestParam;
import com.gupaoedu.vip.mvc.framework.context.GPApplicationContext;

public class GPDispatcherServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String LOCATION = "contextConfigLocation";
	
	private List<Handler> handlerMapping = new ArrayList<Handler>();

	private Map<Handler,HandlerAdapter> adapterMapping = new HashMap<Handler, HandlerAdapter>();
	
	private List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
	
	//初始化我们的IOC容器
	@Override
	public void init(ServletConfig config) throws ServletException {
	
		//IOC容器必须要先初始化
		//假装容器已启动
		GPApplicationContext context = new GPApplicationContext(config.getInitParameter(LOCATION));
		
//		Map<String, Object> ioc = context.getAll();
		
		//请求解析
		initMultipartResolver(context);
		//多语言、国际化
		initLocaleResolver(context);
		//主题View层的
		initThemeResolver(context);
		
		//============== 重要 ================
		//解析url和Method的关联关系(method的对象object)
		initHandlerMappings(context);
		//适配器（匹配的过程）,（主要是方法的参数的类型和序号之间的关系，【注解中的名称】参数名称和序号之间的关系）
		initHandlerAdapters(context);
		//============== 重要 ================
		
		
		//异常解析（略）
		initHandlerExceptionResolvers(context);
		//视图转发（根据视图名字匹配到一个具体模板）（略）
		initRequestToViewNameTranslator(context);
		
		//解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
		//就是把fileName和file保存在map中
		initViewResolvers(context);
		
		initFlashMapManager(context);
		
		System.out.println("GPSpring MVC is init.");
	}
	
	
	//请求解析
	private void initMultipartResolver(GPApplicationContext context){}
	//多语言、国际化
	private void initLocaleResolver(GPApplicationContext context){}
	//主题View层的
	private void initThemeResolver(GPApplicationContext context){}
	//解析url和Method的关联关系（还有method的对象，object）
	private void initHandlerMappings(GPApplicationContext context){
		Map<String,Object> ioc = context.getAll();
		if(ioc.isEmpty()){ return;}
		
		//只要是由Cotroller修饰类，里面方法全部找出来
		//而且这个方法上应该要加了RequestMaping注解，如果没加这个注解，这个方法是不能被外界来访问的
		for (Entry<String, Object> entry : ioc.entrySet()) {
			
			Class<?> clazz = entry.getValue().getClass();
			if(!clazz.isAnnotationPresent(GPController.class)){ continue;}
			
			
			String url = "";
			
			if(clazz.isAnnotationPresent(GPRequestMapping.class)){
				GPRequestMapping requestMapping = clazz.getAnnotation(GPRequestMapping.class);
				url = requestMapping.value();
			}
			
			//扫描Controller下面的所有的方法
			Method [] methods = clazz.getMethods();
			for (Method method : methods) {
				
				if(!method.isAnnotationPresent(GPRequestMapping.class)){ continue;}
				
				GPRequestMapping requestMapping = method.getAnnotation(GPRequestMapping.class);
				String regex = (url + requestMapping.value()).replaceAll("/+", "/");
				Pattern pattern = Pattern.compile(regex);
				
				handlerMapping.add(new Handler(pattern,entry.getValue(),method));
				
				System.out.println("Mapping: " + regex + " --》  " +  method.toString());
				
			}
			
		}
		
		//RequestMapping 会配置一个url，那么一个url就对应一个方法，并将这个关系保存到Map中
		
		
	}
	//适配器（匹配的过程）
	//主要是用来动态匹配我们参数的
	private void initHandlerAdapters(GPApplicationContext context){
		
		if(handlerMapping.isEmpty()){ return;}
		//参数类型作为key，参数的索引号作为值
		Map<String,Integer> paramMapping = new HashMap<String,Integer>();
		
		//只需要取出来具体的某个方法
		for (Handler handler : handlerMapping) {
			
			//把这个方法上面所有的参数全部获取到
			Class<?> [] paramsTypes = handler.method.getParameterTypes();
			 
			//有顺序，但是通过反射，没法拿到我们参数名字 
			//匹配自定参数列表
			//方法参数类型.....
			for (int i = 0;i < paramsTypes.length ; i ++) {
				
				Class<?> type = paramsTypes[i];
				
				if(type == HttpServletRequest.class ||
				   type == HttpServletResponse.class){
					paramMapping.put(type.getName(), i);//参数类型名称和序号绑定？？？
				}
			}
			
			
			//这里是匹配Request和Response
			Annotation [][] pa = handler.method.getParameterAnnotations();
			//二维数组的中的每个元素与参数列表对应，有的元素为空数组，因为没有注解
			//有的可能有几个注解
			
			System.out.println("=============================method\n"+handler.method+"\n===========getParameterAnnotations==================\n"+Arrays.toString(pa)+
					"\n=============================");
			
			for (int i = 0; i < pa.length; i ++) {
				for(Annotation a : pa[i]){
					System.out.println("pa["+i+"].length : "+pa[i].length+"\n");
					if(a instanceof GPRequestParam){
						String paramName = ((GPRequestParam) a).value();
						if(!"".equals(paramName.trim())){
							paramMapping.put(paramName, i);//参数名称和序号绑定
						}
						
					}
				}
			}
			
			adapterMapping.put(handler, new HandlerAdapter(paramMapping));
		}
		
	}
	//异常解析
	private void initHandlerExceptionResolvers(GPApplicationContext context){}
	//视图转发（根据视图名字匹配到一个具体模板）
	private void initRequestToViewNameTranslator(GPApplicationContext context){}
	//解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
	private void initViewResolvers(GPApplicationContext context){
		
		//模板一般是不会放到WebRoot下的，而是放在WEB-INF下，或者classes下
		//这样就避免了用户直接请求到模板
		//加载模板的个数，存储到缓存中
		//检查模板中的语法错误
		
		String tempateRoot = context.getConfig().getProperty("templateRoot");
		
		//归根到底就是一个文件，普通文件
		String rootPath = this.getClass().getClassLoader().getResource(tempateRoot).getFile();
		
		File rootDir = new File(rootPath);
		for (File template : rootDir.listFiles()) {
			viewResolvers.add(new ViewResolver(template.getName(),template));
		}
		
		
		
		
	}
	//
	private void initFlashMapManager(GPApplicationContext context){}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("this is a get request");
		this.doPost(req, resp);
	}

	
	
	//在这里调用自己写的Controller的方法
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try{
			System.out.println("this is a post request");
			doDispatch(req,resp);
		}catch(Exception e){
			resp.getWriter().write("500 Exception, Msg :" + Arrays.toString(e.getStackTrace()));
		}
	}

	//handler中保存method uri object,根据request.uri获取handler
	private Handler getHandler(HttpServletRequest req){
		//循环handlerMapping
		if(handlerMapping.isEmpty()){ return null; }
		System.out.println("");
		
		//
		String url = req.getRequestURI();
		String contextPath = req.getContextPath();
		url = url.replace(contextPath, "").replaceAll("/+", "/");
		
		
		for (Handler handler : handlerMapping) {
		
			Matcher matcher = handler.pattern.matcher(url);
			
			if(!matcher.matches()){ continue;}
			
			return handler;
		}
		
		return null;
		
	}
	
	//HandlerAdapter  参数类型和参数序号之间的关系，含有注解的参数，注解中名称和序号的关系
	private HandlerAdapter getHandlerAdapter(Handler handler){
		if(adapterMapping.isEmpty()){return null;}
		return adapterMapping.get(handler);
	}
	
	
	private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		
		try{
			//先取出来一个Handler，从HandlerMapping取
			Handler handler = getHandler(req);
			if(handler == null){
				resp.getWriter().write("404 Not Found");
				return ;
			}
			
			//再取出来一个适配器
			//再由适配去调用我们具体的方法
			HandlerAdapter ha = getHandlerAdapter(handler);
			GPModelAndView mv = ha.handle(req, resp, handler);
			
			
			//写一个咕泡模板框架
			//Veloctiy #
			//Freemark  #
			//JSP   ${name}
			
			//咕泡模板   @{name}
			applyDefaultViewName(resp, mv);
			
		}catch(Exception e){
			throw e;
		}
		
		
	}
	
	public void applyDefaultViewName(HttpServletResponse resp,GPModelAndView mv) throws Exception{
		if(null == mv){ return;}
		if(viewResolvers.isEmpty()){ return;}
		
		for (ViewResolver resolver : viewResolvers) {
			if(!mv.getView().equals(resolver.getViewName())){ continue; }
			
			String r = resolver.parse(mv);
			
			if(r != null){
				resp.getWriter().write(r);
				break;
			}
		}
	}
	
	
	
	/**
	 * 方法适配器
	 * @author Tom
	 *
	 */
	private class HandlerAdapter{
		
		private Map<String,Integer> paramMapping;
		
		public HandlerAdapter(Map<String,Integer> paramMapping){
			this.paramMapping = paramMapping;
		}
		
		//主要目的是用反射调用url对应的method，（获取参数列表，进行方法的反射调用）
		public GPModelAndView handle(HttpServletRequest req, HttpServletResponse resp,Handler handler) throws Exception{
			
			//为什么要传req、为什么要穿resp、为什么传handler
			Class<?> [] paramTypes = handler.method.getParameterTypes();
			
			//要想给参数赋值，只能通过索引号来找到具体的某个参数
			
			Object [] paramValues = new Object[paramTypes.length];//保存参数列表值
			
			Map<String,String[]> params = req.getParameterMap();
			
			for (Entry<String, String[]> param : params.entrySet()) {
				String str = Arrays.toString(param.getValue());
				String value = str.replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
				
				//没有注解的就不处理。。。
				if(!this.paramMapping.containsKey(param.getKey())){continue;}
				
				int index = this.paramMapping.get(param.getKey());
				
				//单个赋值是不行的，（这个是有注解的参数列表值）
				paramValues[index] = castStringValue(value,paramTypes[index]);
			}
			
			//request 和 response 要赋值
			String reqName = HttpServletRequest.class.getName();
			if(this.paramMapping.containsKey(reqName)){
				int reqIndex = this.paramMapping.get(reqName);
				paramValues[reqIndex] = req;
			}
			
			
			String resqName = HttpServletResponse.class.getName();
				if(this.paramMapping.containsKey(resqName)){
				int respIndex = this.paramMapping.get(resqName);
				paramValues[respIndex] = resp;
			}
			
			boolean isModelAndView = handler.method.getReturnType() == GPModelAndView.class;
			Object r = handler.method.invoke(handler.controller, paramValues);
			if(isModelAndView){
				return (GPModelAndView)r;
			}else{
				return null;
			}
			
		}
		
		
		private Object castStringValue(String value,Class<?> clazz){
			if(clazz == String.class){
				return value;
			}else if(clazz == Integer.class){
				return Integer.valueOf(value);
			}else if(clazz == int.class){
				return Integer.valueOf(value).intValue();
			}else{
				return null;
			}
		}
		
	}
	
	/**
	 * HandlerMapping 定义
	 * @author Tom
	 *
	 */
	private class Handler{
		
		protected Object controller;
		protected Method method;
		protected Pattern pattern;
		
		protected Handler(Pattern pattern,Object controller,Method method){
			this.pattern = pattern;
			this.controller = controller;
			this.method = method;
		}
	}
	
	
	private class ViewResolver{
		private String viewName;
		private File file;
		
		protected ViewResolver(String viewName,File file){
			this.viewName = viewName;
			this.file = file;
		}
		
		protected String parse(GPModelAndView mv) throws Exception{
			
			StringBuffer sb = new StringBuffer();
			
			RandomAccessFile ra = new RandomAccessFile(this.file, "r");
			
			try{
				//模板框架的语法是非常复杂，但是，原理是一样的
				//无非都是用正则表达式来处理字符串而已
				//就这么简单，不要认为这个模板框架的语法是有多么的高大上
				//来我现在来做一个最接地气的模板，也就是咕泡学院独创的模板语法
				String line = null;
				while(null != (line = ra.readLine())){
					Matcher m = matcher(line);
					while (m.find()) {
						for (int i = 1; i <= m.groupCount(); i ++) {
							String paramName = m.group(i);
							Object paramValue = mv.getModel().get(paramName);
							if(null == paramValue){ continue; }
							line = line.replaceAll("@\\{" + paramName + "\\}", paramValue.toString());
						}
					}
					
					sb.append(line);
				}
			}finally{
				ra.close();
			}
			return sb.toString();
		}

		private Matcher matcher(String str){
			Pattern pattern = Pattern.compile("@\\{(.+?)\\}",Pattern.CASE_INSENSITIVE);
			Matcher m = pattern.matcher(str);
			return m;
		}
		
		
		public String getViewName() {
			return viewName;
		}
		
	}
	
}
