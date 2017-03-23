package com.tangkuo.cn.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: www.tk.com</p>   
 * @author   tangkuo
 * @date    2017年3月23日 下午10:34:02
 */
public class TcpClient {

	public static final String encoding = "UTF-8";
	private String Ip = "127.0.0.1";
	private int Port = 59999;
	public static boolean isLocalGateway = false;

	private static final int REQUEST_LENGTH = 8;

	private int timeOut = -1;

	public TcpClient() {
	}

	public TcpClient(String Ip, int Port) {
		this.Ip = Ip;
		this.Port = Port;
	}

	public void executeRemote() throws UnknownHostException, IOException,
			Exception {

		// 准备发送网关报文体
		String sendMessage = "test";

		String lenStr = "00000000"
				+ String.valueOf(sendMessage.getBytes(encoding).length);

		sendMessage = lenStr.substring(lenStr.length() - REQUEST_LENGTH)
				+ sendMessage;

		System.out.println("请求报文：\n" + sendMessage);

		try {
			Socket cSocket = new Socket(Ip, Port);
			cSocket.setSoTimeout(30000);
			OutputStream out = cSocket.getOutputStream();
			System.out.println("发送报文：\n");
			out.write(sendMessage.getBytes(encoding));
			out.flush();

			InputStream in = cSocket.getInputStream();

			int lengthHeadLen = REQUEST_LENGTH;
			int off = 0;
			byte[] lenHeadBuf = new byte[REQUEST_LENGTH];
			while (off < lengthHeadLen) {
				int len = in.read(lenHeadBuf, off, lengthHeadLen - off);
				if (len < 0) {
					// System.out.println("len ====" + len);
				} else {
					off = off + len;
					System.out.println("recv new ="
							+ new String(lenHeadBuf, 0, off));
				}

			}

			int bodyLength;
			try {
				// 解析成int类型
				bodyLength = Integer.parseInt(new String(lenHeadBuf));
			} catch (NumberFormatException ex) {
				throw new Exception("Read package error!"); // 通信异常，读取报文长度失败
			}

			// 读取指定长度的数据
			byte[] bodyData = new byte[bodyLength];
			int offset = 0;
			int readLen = 0;

			while (true) {
				// count++;
				// 读取渠道上送的报文
				readLen = in.read(bodyData, offset, bodyLength - offset);
				if (readLen == -1) {
					break;
				}
				offset += readLen;
				if (offset >= bodyLength) {
					break;
				}
			}
			if (offset < bodyLength) {
				throw new Exception("Read package error!"); // 实际读取的渠道上送报文长度小于渠道报文所申明的长度
			}

			String receivedMessage = new String(bodyData, encoding);
			System.out.println("返回报文：\n" + receivedMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
