package guodu.net.warning.threads;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import guodu.net.warning.db.SaveMessage;
import guodu.net.warning.entity.Message;
import guodu.net.warning.entity.MessageEx;
import guodu.net.warning.util.Loger;

public class LogicThreadOne{
	/**
	 * 分析数据，读取获悉数据
	 * @param message 
	 * */
   public void analyseMessage(String message){
	   Message ms = new Message();
	   try {
		String[] rs = message.split("!\\|");
		   ms.setContent(rs[0]);
		   ms.setMobile(rs[1]);
		   ms.setLimt(rs[2]);
		   ms.setOper(rs[3]);
		   ms.setWarning_type(rs[4]);
		   ms.setYw_type(rs[5]);
	   } catch (Exception e) {
		   Loger.Info_log.info("[ERROR]Message封包失败");
		   e.printStackTrace();
	   }
	       analyseContent(ms);
   }
   
  /**
   * 根据yw类型判断信息的处理方式
   * 1：直接发送  2:阀值判断 默认：直接发送
   * @param （Message）ms
   * */
   public void analyseContent(Message ms){
	   int arg = Integer.parseInt(ms.getYw_type().trim());
	   switch(arg){
	       case 1:{
	    	    SaveMessage.Save(ms);
		        break;
	       }
	       case 2:{
		        getCount(ms);
		        break;
	       }
	       default:{
	    	   ms.setContent("**业务类型错误，请联系管理员**" + ms.getContent());
	    	   SaveMessage.Save(ms);
	    	   break;
	       }
	   }
   }
   
   /**
    * 将信息内容模版中的数据取出来根运算符进行判断
    * 更新MessageEx，将取出的数值放入boject[]中
    * 更新MessageEx，将取出的值按照运算符计算总数存储rs中
    * 判断是否符合发送要求
    * @param Message
    * */
   public void getCount(Message ms){
	   String content = ms.getContent();
	   String a = StringUtils.substringBetween(content,"{","}");
   	   List<String> list = new ArrayList<String>();
   	   while(true){
   		   a = StringUtils.substringBetween(content,"{","}");
   		   content = StringUtils.substringAfter(content, "}"); 
       	   if(null == a || "".equals(a)){
   		      break;
   	       }else{
         	      list.add(a);
   	       }
   	   }
   	   MessageEx ex = new MessageEx();
   	   ex.setMs(ms);
   	   ex.setStr(list.toArray());
   	   ex.setRs(judgeLimt(ex));
   	   Boolean flag = true;
   	   flag = sendJudge(ex);
   	   if(flag){
   	      SaveMessage.Save(ex);
   	   }
   }
   
   /**
    * 最终判断阀值，决定是否发送
    * @param MessageEx
    * @return Boolean
    * */
   public Boolean sendJudge(MessageEx ex){
	   char[] limt_oper = ex.getMs().getLimt().split(",")[0].toCharArray(); //阀值判断符
	   Boolean flag = false;
	   switch(limt_oper[0]){
       case '>':{	 
            flag = ex.getRs() > Integer.parseInt(ex.getMs().getLimt().split(",")[1]);
	        break;
       }
       case '<':{
            flag = ex.getRs() < Integer.parseInt(ex.getMs().getLimt().split(",")[1]);
	        break;
       }      
       default:{
    	   ex.getMs().setContent("**阀值判断符号错误，请联系管理员**" + ex.getMs().getContent());
   	       SaveMessage.Save(ex);
    	   flag = true;
    	   break;
       }
       }
	   return flag;
   }
   
   
   
   /**
    * 更新MessageEx，将取出的值按照运算符计算总数存储rs中
    * @param MessageEx
    * @return int
    * */
   public int judgeLimt(MessageEx ex){
	   char[] oper = ex.getMs().getOper().toCharArray(); //运算符
//	   char[] limt_oper = ex.getMs().getLimt().split(",")[0].toCharArray(); //阀值判断符
	   int rs = 0;
	   switch(oper[0]){
       case '+':{	 
    	    for(Object o : ex.getStr()){
    	    	rs = rs + Integer.parseInt(o.toString());
    	    }
	        break;
       }
       case '-':{
    	    int rsu = Integer.parseInt(ex.getStr()[0].toString());
    	    for(int i = 1 ; i < ex.getStr().length ; i++){
    		    rsu = rsu - Integer.parseInt(ex.getStr()[i].toString());
    	    }
    	    rs = rsu;
	        break;
       }
       default:{
    	   System.out.println("默认");
    	   ex.getMs().setContent("**运算错误，请联系管理员**" + ex.getMs().getContent());
   	       SaveMessage.Save(ex);
    	   break;
       }
	   }
       return rs;
   }
   
   
   public static void main(String[] arg){
//	   String a = "[20160127]日，至[20160203]日，用户回复的上行TD数为：{4574}!|15117956265,15810334106!|>,100!|+!|1!|2";
	   String b = "[20160127]日，至[20160203]日，用户回复的上行TD数为：{4574}!|15117956265,15810334106!|>,100!|+!|1!|2               ";
	   new LogicThreadOne().analyseMessage(b); 
   }
}
