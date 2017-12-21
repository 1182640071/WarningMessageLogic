package guodu.net.warning.start;

import guodu.net.warning.util.ConfigContainer;
import guodu.net.warning.util.Loger;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Start {
	  /** 
     * @param args 
     */ 
    public static void main(String[] args) { 
    	ConfigContainer.load();
        //����һ����������server��Socket ����NIO 
        SocketAcceptor acceptor = new NioSocketAcceptor(); 
        /*---------���ն���---------*/ 
        //�����������ݵĹ����� 
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain(); 
        //�趨������һ����(/r/n)�Ķ�ȡ���� 
        chain.addLast("mychin", new ProtocolCodecFilter(new TextLineCodecFactory()   )); 
//      chain.addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        //�趨������������Զ���Ϊ��λ��ȡ���� 
        ProtocolCodecFilter filter= new ProtocolCodecFilter(new ObjectSerializationCodecFactory()); 
        chain.addLast("objectFilter",filter); 
        //�趨��������Ϣ������ 
        acceptor.setHandler(new ClientMinaServerHanlder()); 
        //�������󶨵Ķ˿� 
        int bindPort = ConfigContainer.getPort(); 
        //�󶨶˿ڣ����������� 
        try { 
            acceptor.bind(new InetSocketAddress(bindPort)); 
        } catch (IOException e) { 
			Loger.Info_log.info("[ERROR]Mina Server start for error!" + bindPort);
            e.printStackTrace(); 
        } 
		Loger.Info_log.info("[INFO]Mina Server run done! on port:"+bindPort);
    }   
}
