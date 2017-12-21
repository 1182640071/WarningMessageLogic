package guodu.net.warning.db;

import guodu.net.warning.entity.Message;
import guodu.net.warning.entity.MessageEx;
import guodu.net.warning.util.Loger;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveMessage {
	 private static int number = 1; //序列号
	 /**
	  * 获取1至9999的四位数据
	  * */
     private synchronized static int getNumber(){
    	if(number > 9999){
    		number = 1;
    	}
    	return number++;
     }
     
     private synchronized static String getCode(){
    	 String code = String.valueOf(getNumber());
    	 while(code.length() < 4){
        	 StringBuffer sb = new StringBuffer();
    		 sb = sb.append("0").append(code);
    		 code = sb.toString();
    	 }
    	 code = "WX" + code + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
    	 return code;
     }
     
	 public static void Save(MessageEx ex){
		    String content = ex.getMs().getContent().replace("[", "").replace("]", "").replace("{", "").replace("}", "");
			String sql="insert into GD_WX_INFOMATION (id , content , desmobile ,warning_type) values ('" + getCode() + "' , '" + content + "' , '" + ex.getMs().getMobile()  + "' , '"+ ex.getMs().getWarning_type() +"')";
			Connection conn = null;
			Statement st = null;
			DBManagerConnection_Main DBManagerConnection = DBManagerConnection_Main.getInstance();
			try {
				conn = DBManagerConnection.getConnection();
			    if (null == conn) {
				    Loger.Info_log.info("[ERROR]数据库连接失败");
					return;
				}	
			st = conn.createStatement();
			Loger.Info_log.info("[DEBUG]执行sql语句" + sql);
			st.executeUpdate(sql);	
			Loger.Info_log.info("[INFO]插入数据入待发表，内容：" + content + " ;手机号：" + ex.getMs().getMobile());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBManagerConnection_Main.closekind(st , conn);
			}
	 }			
	 
	 public static void Save(Message ex){
		    String content = ex.getContent().replace("[", "").replace("]", "").replace("{", "").replace("}", "");
			String sql="insert into GD_WX_INFOMATION (id , content , desmobile , warning_type) values ('" + getCode() + "' , '" + content + "' , '" + ex.getMobile() + "' , '"+ ex.getWarning_type() +"')";
			Connection conn = null;
			Statement st = null;
			DBManagerConnection_Main DBManagerConnection = DBManagerConnection_Main.getInstance();
			try {
				conn = DBManagerConnection.getConnection();
			    if (null == conn) {
				    Loger.Info_log.info("[ERROR]数据库连接失败");
					return;
				}	
			st = conn.createStatement()	;
			st.executeUpdate(sql);	
			Loger.Info_log.info("[INFO]插入数据入待发表，内容：" + content + " ;手机号：" + ex.getMobile());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				DBManagerConnection_Main.closekind(st , conn);
			}
	 }	
	 
	 
     public static void main(String[] arg){
    	 while(true){
        	 System.out.println(getCode());   		 
    	 }
     }
}
