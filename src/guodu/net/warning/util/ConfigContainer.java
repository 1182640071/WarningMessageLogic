package guodu.net.warning.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigContainer {
	
	private static int port = 0; //端口号
	
	
		/**
		 * 本方法提供配置文件信息加载功能
		 * */
     public static void load(){   	 
    	 Map<?,?> map = loadFunction("common");
    	 port = Integer.parseInt(getInfo("prot","9988", map));
     }
   
     /**
     * 此方法可以获得节点属性
     * @param e 节点
     * @param defult 节点属性为空时的默认值
     * @param map
     * @return result 查询结果
     * */
     public static String getInfo(String e , String  defult , Map<?,?> map)
     {
    	 String result = (String) map.get(e);
    	 if("".equals(result))
    	 {
    		 result = defult;
    	 }
    	 return result;
     }
     
     /**
      * 此方法实现将节点的所有子节点和属性存放到map中并返回
      * @param e 节点
      * @return map 
      * */
     public  static Map<?, ?> loadFunction(String e)
     {
    	//创建解析器
   	  SAXReader saxreader = new SAXReader();
   	  
   	  //读取文档
   	  Document doc = null;
   	  Map<String,String> map = null;
		try {
			doc = saxreader.read(new File("config/config.xml"));
			map = new HashMap<String,String>();
	    	  //获取根，节点
	    	Element root = doc.getRootElement().element(e);
	    	  //将所有节点和属性存放到map中
	    	for ( Iterator<?> iterInner = root.elementIterator(); iterInner.hasNext(); ) {   
	    		Element elementInner = (Element) iterInner.next();
	    	    map.put(elementInner.getName(), root.elementText(elementInner.getName()));
	    	}
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	 return map;
     }
     
	
	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		ConfigContainer.port = port;
	}

	public static void main(String[] args)
     {
    	 load();
    	 System.out.println(port);
     }
}
