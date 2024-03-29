package guodu.net.warning.start;

import guodu.net.warning.threads.LogicThreadOne;
import guodu.net.warning.util.Loger;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ClientMinaServerHanlder extends IoHandlerAdapter {
	private static int count = 0;

	// 当一个新客户端连接后触发此方法.
	public void sessionCreated(IoSession session) {
		count++;
		Loger.Info_log.info("[INFO]当前连接数：" + count);
	}

	// 当一个客端端连结进入时 @Override
	public void sessionOpened(IoSession session) throws Exception {
		Loger.Info_log.info("[INFO]client 登陆！address："
				+ session.getRemoteAddress());
	}

	// 当客户端发送的消息到达时:
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		 //设定服务器解析消息的规则是一行一行读取,这里转为String:
		 String s = (String) message;
		 //[20160127]日，至[20160203]日，用户回复的上行TD数为：{4574}!|15117956265!|>,100!|+!|1!|2               
		 Loger.Info_log.info("[INFO]收到消息：" + s);
		 new LogicThreadOne().analyseMessage(s);
		 //测试将消息回送给客户端 
		 session.write("0");
	}

	// 当信息已经传送给客户端后触发此方法.
	public void messageSent(IoSession session, Object message) {
		
	}

	// 当一个客户端关闭时
	public void sessionClosed(IoSession session) {
		count--;
		Loger.Info_log.info("[INFO]one Clinet Disconnect !" + session.getRemoteAddress());
	}

	// 当连接空闲时触发此方法.
	public void sessionIdle(IoSession session, IdleStatus status) {
		System.out.println("连接空闲");
	}

//	// 当接口中其他方法抛出异常未被捕获时触发此方法
//	public void exceptionCaught(IoSession session, Throwable cause) {
//		Loger.Info_log.info("[ERROR]其他方法抛出异常 !" + cause);
//	}

}
