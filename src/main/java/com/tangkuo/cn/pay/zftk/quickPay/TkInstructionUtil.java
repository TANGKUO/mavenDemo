package com.tangkuo.cn.pay.zftk.quickPay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tangkuo.cn.utils.configuration.PropertiesUtils;

/**
 * 
 * @ClassName: InstructionUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class TkInstructionUtil {
	private final static Log log = LogFactory.getLog(TkInstructionUtil.class);
	public final static String CHARSET = "GBK";
	public final static String FILE_SEPARATOR = System.getProperty("file.separator");
	public final static String LINE_SEPARATOR = System.getProperty("line.separator");

	private static Map<String, Object> country = PropertiesUtils.getProperties("country.properties");

	public static String getCountryName(String countryCode) {
		return (String) country.get(countryCode);
	}

	/**
	 * 
	 * @Title: saveData @Description: TODO(这里用一句话描述这个方法的作用) @param @param
	 *         filePath @param @param context @param @param
	 *         charset @param @throws
	 *         UnsupportedEncodingException @param @throws IOException
	 *         设定文件 @return void 返回类型 @throws
	 */
	public static void saveData(String filePath, String context, String charset)
			throws UnsupportedEncodingException, IOException {
		if (StringUtils.isEmpty(context))
			return;

		if (StringUtils.isEmpty(charset)) {
			charset = CHARSET;
		}

		FileOutputStream out = null;
		try {
			File file = new File(filePath.substring(0, filePath.lastIndexOf(FILE_SEPARATOR)));
			file.mkdirs();

			out = new FileOutputStream(new File(filePath), false);
			out.write(context.getBytes(charset));
			out.flush();
			out.close();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}

	public static void saveData(String filePath, String context) throws UnsupportedEncodingException, IOException {
		saveData(filePath, context, CHARSET);
	}

	/**
	 * 通过socket发送数据
	 * 
	 * @param socket
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static String sendDataGetResult(Socket socket, String content, String reqEncoding, String respEncoding)
			throws IOException {
		OutputStream oStream = null;
		InputStream iStream = null;
		InputStreamReader iStreamReader = null;
		BufferedReader bufferedReader = null;

		if (StringUtils.isEmpty(reqEncoding)) {
			reqEncoding = CHARSET;
		}

		if (StringUtils.isEmpty(respEncoding)) {
			respEncoding = CHARSET;
		}

		try {
			boolean isConnected = socket.isConnected();
			log.info("connection destination：" + isConnected);
			if (isConnected) {
				oStream = socket.getOutputStream();
				oStream.write((content + LINE_SEPARATOR).getBytes(reqEncoding));
				oStream.flush();

				iStream = socket.getInputStream();
				iStreamReader = new InputStreamReader(iStream, respEncoding);
				bufferedReader = new BufferedReader(iStreamReader);

				StringBuilder resultInfo = new StringBuilder();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					resultInfo.append(line);
				}

				return resultInfo.toString();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (null != bufferedReader) {
				bufferedReader.close();
			}
			if (null != iStreamReader) {
				iStreamReader.close();
			}
			if (null != iStream) {
				iStream.close();
			}
			if (null != oStream) {
				oStream.close();
			}
			if (null != socket) {
				socket.close();
			}
		}

		return null;
	}

	public static String sendDataGetResult(Socket socket, String content) throws IOException {
		return sendDataGetResult(socket, content, null, null);
	}

	/**
	 * 
	 * @Title: getStrLen @Description: TODO(获取数据长度) @param @param
	 *         str @param @return 设定文件 @return int 返回类型 @throws
	 */
	public static int getStrLen(String str) {
		int strLen = 0;
		if (str != null) {
			strLen = str.length();
			char[] charArray = str.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if (((int) charArray[i]) > 255) {
					strLen++;
				}
			}
		}
		return strLen;
	}

	/**
	 * 
	 * @Title: isCompanyAccount @Description:
	 *         (根据账户名判断是对公还是对私账号(无法根据账号来区分才使用此方法)) @param @param
	 *         accountName @param @return 设定文件 @return boolean 返回类型 @throws
	 */
	public static boolean isCompanyAccount(String accountName) {
		boolean flag = false;
		if (StringUtils.isNotEmpty(accountName)) {
			accountName = accountName.replace("(", "").replace(")", "");
			if (accountName.matches("[\u4E00-\u9FA5]+")) {// 是中文
				if (accountName.length() > 5 && (accountName.contains("公司") || accountName.contains("市"))) {
					flag = true;
				}
			} else {// 英文
				if (accountName.toLowerCase().contains("co.") || accountName.toLowerCase().contains("company")) {
					flag = true;
				}
			}
		}
		return flag;
	}

	static {
		System.setProperty("sun.jnu.encoding", "UTF-8");
	}
}
