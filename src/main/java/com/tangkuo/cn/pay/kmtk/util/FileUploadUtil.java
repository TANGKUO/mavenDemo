package com.tangkuo.cn.pay.kmtk.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tangkuo.cn.pay.kmtk.netbank.common.CodeDesc;
import com.tangkuo.cn.pay.kmtk.netbank.common.util.Property;

/**
 * 文件上传工具类
 */
public class FileUploadUtil {

	private static Logger log = LoggerFactory.getLogger(FileUploadUtil.class);
	/**
	 * 文件上传存放路径
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	private static String filePrefixPath = Property.getProperty("filesystem.basepath");
	private static String fileManagerUrl = Property.getProperty("filemanager.attached.url");

	// 定义允许上传的文件扩展名
	static Map<String, String> extMap = new HashMap<String, String>() {
		private static final long serialVersionUID = -3551732496893167849L;
		{
			put("image", "gif,jpg,jpeg,png,bmp");
			put("flash", "swf,flv");
			put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
			put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		}
	};

	Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>() {
		private static final long serialVersionUID = -7917404239513100533L;
		{
			put("gif", "47494638"); // GIF
			put("jpg", "FFD8FF"); // JPEG
			put("png", "89504E47"); // PNG
			put("tif", "49492A00"); // TIFF
			put("bmp", "424D"); // Windows Bitmap
		}
	};

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param dir
	 * @return
	 */
	public static ExtTtyResponse fileUpload(HttpServletRequest request, HttpServletResponse response) {
		ExtTtyResponse rsp = new ExtTtyResponse();

		try {
			// 文件不存在
			if (!ServletFileUpload.isMultipartContent(request)) {
				rsp.setCodeDesc(CodeDesc.FILENOTEXITS);
				return rsp;

			}

			String dir = "";
			// 检查目录
			log.info("文件上传地址: " + filePrefixPath);
			File uploadDir = new File(filePrefixPath);
			if (!uploadDir.isDirectory()) {
				rsp.setCodeDesc(CodeDesc.DICNOTEXISTS); // 目录不存在
				return rsp;
			}

			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				rsp.setCodeDesc(CodeDesc.NOWRITEAUTHORITY);
				return rsp;
			}
			if (StringUtils.isEmpty(dir)) {
				dir = "image";
			}

			String savePath = filePrefixPath + "/upload/";
			String url = fileManagerUrl + "/upload/";

			// 创建文件夹
			String ymd = sdf.format(new Date());
			ymd = ymd.substring(0, 4) + ymd.substring(4);
			savePath += (ymd) + "/";
			url += ymd + "/";

			long maxSize = Long.valueOf(Property.getProperty("file.upload.maxsize", "5242880"));

			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			Map<String, String> map = new HashMap<String, String>();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						System.out.println(myFileName);
						// 检查文件大小
						if (file.getSize() > maxSize) {
							rsp.setCodeDesc(CodeDesc.FILESIZETOOLARGE);
							return rsp;
						}
						// 检查扩展名
						String postfix = myFileName.substring(myFileName.lastIndexOf(".") + 1).toLowerCase();
						if (!Arrays.<String>asList(extMap.get(dir).split(",")).contains(postfix)) {
							rsp.setCode(CodeDesc.FILETYPEERROR.code())
									.setMessage("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dir) + "格式。");
							return rsp;
						}
						// 重命名上传后的文件名
						String newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%1$tL",
								new Date(System.currentTimeMillis())) + "." + postfix;

						File tempPath = new File(savePath);
						// 创建目录
						if (!tempPath.isDirectory()) {
							tempPath.mkdirs();
						}
						File localFile = new File(savePath + newFileName);
						file.transferTo(localFile);
						map.put(file.getName(), url + newFileName);
					}
				}
			}
			rsp.setParams(map);
			rsp.setCodeDesc(CodeDesc.SUCCESS);
		} catch (Exception e) {
			log.error("文件上传异常", e);
			rsp.setCodeDesc(CodeDesc.EXCEPTION);
		}

		// 文件保存目录URL
		return rsp;
	}

	/**
	 * 获取map中第一个数据值
	 *
	 * @param <K>
	 *            Key的类型
	 * @param <V>
	 *            Value的类型
	 * @param map
	 *            数据源
	 * @return 返回的值
	 */
	public static <K, V> V getFirstOrNull(Map<K, V> map) {
		V obj = null;
		for (Entry<K, V> entry : map.entrySet()) {
			obj = entry.getValue();
			if (obj != null) {
				break;
			}
		}
		return obj;
	}
}
