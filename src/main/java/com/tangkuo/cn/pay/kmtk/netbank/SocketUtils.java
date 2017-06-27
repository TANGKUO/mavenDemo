package com.tangkuo.cn.pay.kmtk.netbank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
* @ClassName: SocketUtils
* @Description: (Socket发送/接收工具类)
* @author tangkuo
* @date 2017年6月27日 上午11:02:17
*
 */
public class SocketUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SocketUtils.class);
	
	/**
	 * 
	 * 方法说明：用于发送消息给银行<br>
	 * 
	 * @param serverIP 银行服务器IP
	 * @param serverPort 端口
	 * @param senderMsg 请求消息
	 * @return 银行返回报文
	 * @throws Exception
	 */
	public  static String tcpClient(String serverIP, int serverPort, byte[] senderMsg) throws Exception{
		if (senderMsg == null || senderMsg.length == 0) {
			logger.error("被发送消息不能为空!");
			return null;
		}
		String respStr = null;
		Socket socket = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try{
			socket = new Socket(serverIP, serverPort);
			socket.setReuseAddress(true);
			socket.setSoTimeout(50000);
			socket.setKeepAlive(true);
			socket.setTcpNoDelay(false);
			
			logger.info("准备发送数据{} " + socket);
			dos = new DataOutputStream(socket.getOutputStream());
			dos.write(senderMsg);
			dos.flush();
			//dos.close();
			logger.info("send bytes=" + senderMsg.length);
			
			dis = new DataInputStream(socket.getInputStream());
			int read = 0;
			byte[] buff = new byte[1024];
			if( (read = dis.read(buff)) > 0 ) {
				respStr = new String(buff, 0, read);
			}
			logger.info("接收数据{} " + respStr);
			
		}finally{
			if (dos != null) {
				dos.close();
			}
			if (dis != null) {
				dis.close();
			}
			if (socket != null) {
				socket.close();
			}
		}
		return respStr;
	}
	
	/**
	 * socketXmlRequestGB18030  通过socket发送xml请求内容用 GB18030
	 * @param host,port,requestXml
	 * @return String
	 * */
	public static String socketXmlRequestGB18030(String host,int port,String requestXml){
		if(StringUtils.isEmpty(requestXml) || StringUtils.isEmpty(host) || port == 0){
			return StringUtils.EMPTY;
		}
		requestXml = XmlFormatter.format(requestXml);
		Socket socket = null;
		String responseData = StringUtils.EMPTY;
		try{
			socket = new Socket(host, port);
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GB18030"));
			wr.write(requestXml);
			wr.flush();
			
			InputStream in = socket.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, "GB18030"));
			StringBuilder data = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				data.append(line);
			}
			responseData = data.toString();
			if(StringUtils.isNotEmpty(responseData)){
				responseData = XmlFormatter.format(responseData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try{
				if(socket !=null) socket.close();
			}catch(Exception e){
				logger.error(e.getMessage(), e);
			}
		}
		return responseData;
	}
	
	/**
	 * socketXmlRequestWithByte  通过socket发送字节流请求内容用
	 * @param host,port,requestXml
	 * @return byte[]
	 * */
	public static byte[] socketXmlRequestWithByte(byte[] reqBytes,int timeOut,String address,int port) {
		Socket socket = null;
		InputStream dis = null;//DataInputStream
		OutputStream dos = null;//DataOutputStream
		try {
			socket = new Socket(address, port);
			dis = socket.getInputStream();//new DataInputStream(
			dos = socket.getOutputStream();//new DataOutputStream(
			socket.setSoTimeout(timeOut);
			dos.write(reqBytes, 0, reqBytes.length);
            dos.flush();
            int totallen=0;
            int count;
            List<byte[]> l = new ArrayList<byte[]>();
	        while(true){
	            byte[] tmp = new byte[1024];
	            try{
	            	count = dis.read(tmp);
	            }catch(Throwable e){
		            e.printStackTrace();
		            break;
	            }
	            if(count<=0){
	            	break;
	            }
	            totallen += count;
	            byte[] tmp2 = new byte[count];
	            System.arraycopy(tmp,0,tmp2,0,count);
	            l.add(tmp2);
	        }
            byte[] arecv = new byte[totallen];
            int pos = 0;
            for(byte[] tmp:l){
	            System.arraycopy(tmp,0,arecv,pos,tmp.length);
	            pos += tmp.length;
            }
            return arecv;
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally{
			//
			IOUtils.closeQuietly(dos);
			//	 
			IOUtils.closeQuietly(dis);
			// 
			IOUtils.closeQuietly(socket);
		}
	}
	
}
