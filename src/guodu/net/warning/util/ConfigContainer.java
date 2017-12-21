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
	
	private static int port = 0; //�˿ں�
	
	
		/**
		 * �������ṩ�����ļ���Ϣ���ع���
		 * */
     public static void load(){   	 
    	 Map<?,?> map = loadFunction("common");
    	 port = Integer.parseInt(getInfo("prot","9988", map));
     }
   
     /**
     * �˷������Ի�ýڵ�����
     * @param e �ڵ�
     * @param defult �ڵ�����Ϊ��ʱ��Ĭ��ֵ
     * @param map
     * @return result ��ѯ���
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
      * �˷���ʵ�ֽ��ڵ�������ӽڵ�����Դ�ŵ�map�в�����
      * @param e �ڵ�
      * @return map 
      * */
     public  static Map<?, ?> loadFunction(String e)
     {
    	//����������
   	  SAXReader saxreader = new SAXReader();
   	  
   	  //��ȡ�ĵ�
   	  Document doc = null;
   	  Map<String,String> map = null;
		try {
			doc = saxreader.read(new File("config/config.xml"));
			map = new HashMap<String,String>();
	    	  //��ȡ�����ڵ�
	    	Element root = doc.getRootElement().element(e);
	    	  //�����нڵ�����Դ�ŵ�map��
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
