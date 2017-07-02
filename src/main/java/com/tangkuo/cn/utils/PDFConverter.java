package com.tangkuo.cn.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tangkuo.cn.utils.configuration.PropertiesUtils;

public class PDFConverter {
	
	private static Log log = LogFactory.getLog(PDFConverter.class);
	
	private static int ENVIRONMENT;// 环境 1：windows 2:linux
	private static String PDF2SWF_COMMAND;//PDF2SWF执行命令
	private static String LANGUAGE_DIRECTORY;//PDF2SWF执行所用语言字体目录
    private String fileName;  
    private File pdfFile;  
    private File swfFile;
    
    static{
    	try {
    		ENVIRONMENT = Integer.valueOf(PropertiesUtils.getProperty("fileDirectory.properties","runtimeEnvironment")).intValue();
    		PDF2SWF_COMMAND = PropertiesUtils.getProperty("fileDirectory.properties","pdf2swfCommand");
    		LANGUAGE_DIRECTORY = PropertiesUtils.getProperty("fileDirectory.properties","pdf2swfLanguageDir");
		} catch (Exception e) {
			log.error("PDF 资源文件初始话异常",e);
			ENVIRONMENT = -1;
			PDF2SWF_COMMAND = "";
			LANGUAGE_DIRECTORY = "";
		}
    }
    
    public PDFConverter() {
    	
    }
    
    /**
     * 
     * @param fileFullPathStr PDF文件全路径
     */
    public PDFConverter(String fileFullPathStr) {
        init(fileFullPathStr);
    }
  
    /**
     * 重置PDF文件全路径 
     * @param fileFullPathStr PDF文件全路径
     */
    public void setFile(String fileFullPathStr) {
        init(fileFullPathStr);
    }
  
    /**
     * 初始化
     * @param fileFullPathStr PDF文件全路径
     */
    private void init(String fileFullPathStr) {
        fileName = fileFullPathStr.substring(0, fileFullPathStr.lastIndexOf("."));
        pdfFile = new File(fileName + ".pdf");
        swfFile = new File(fileName + ".swf");
    }
    
      
    /** 
     * PDF转换SWF 
     */ 
    public boolean pdf2swf(){
        if (!swfFile.exists()) {
            if (pdfFile.exists()) {
                try {
                	List<String> params = new ArrayList<String>();
                    params.add(PDF2SWF_COMMAND);
                    if(StringUtils.isBlank(PDF2SWF_COMMAND)){
                    	log.error("PDF2SWF执行命令缺失。。。");
                    	return false;
                    }
                    if(StringUtils.isBlank(LANGUAGE_DIRECTORY)){
                    	log.error("PDF2SWF执行命令参数中语言字体目录不存在。。。");
                    	return false;
                    }
                    if(ENVIRONMENT == 1){
                    	params.add("\"" + pdfFile.getPath() + "\"");
                    	params.add("\"" + swfFile.getPath() + "\"");
                    }else if(ENVIRONMENT == 2){
                    	params.add(pdfFile.getPath());
                    	params.add("-o");
                    	params.add(swfFile.getPath());
                    }else{
                    	log.error("运行环境非指定环境[windows、linux]");
                    	return false;
                    }
                    params.add("-T");
                    params.add("9");
                	params.add("-s");
                    params.add("languagedir=" + LANGUAGE_DIRECTORY);
                    
                    Process process = new ProcessBuilder(params).start();
                    InputStreamWathThread inputWathThread = new InputStreamWathThread(process);  
			        inputWathThread.start(); 
			        ErrorStreamWathThread errorWathThread = new ErrorStreamWathThread(process);  
			        errorWathThread.start();
			        try {  
			            process.waitFor();//等待子进程的结束，子进程就是系统调用文件转换这个新进程  
			            inputWathThread.setOver(true);//转换完，停止对输入流的处理  
			            errorWathThread.setOver(true);//转换完，停止对出错流的处理  
			            if(StringUtils.isNotBlank(errorWathThread.getErroeMsg())){
	                    	log.error("PDF转换SWF异常，异常信息：" + errorWathThread.getErroeMsg());
	                    	if (pdfFile.exists()) {  
	                    		pdfFile.delete();  
	                        }
	                    	if (swfFile.exists()) {  
	                    		swfFile.delete();  
	                        }
	                    	return false;
	                    }
			        } catch (InterruptedException e) {  
			        	log.error("", e);
			        	if (pdfFile.exists()) {  
                    		pdfFile.delete();  
                        }
                    	if (swfFile.exists()) {  
                    		swfFile.delete();  
                        }
			        	return false;
			        } catch (Exception e) {  
			            log.error("", e);
			            if (pdfFile.exists()) {  
                    		pdfFile.delete();  
                        }
                    	if (swfFile.exists()) {  
                    		swfFile.delete();  
                        }
			            return false;
			        }
			        
                    log.info("PDF转换SWF完成。。。"); 
                    return true;
                } catch (Exception e) {
                	log.error("PDF转换成SWF出错", e);
                	if (pdfFile.exists()) {  
                        pdfFile.delete();  
                    }
                	if (swfFile.exists()) {  
                		swfFile.delete();  
                    }
                	return false;
                } finally{
//                	if (pdfFile.exists()) {  
//                        pdfFile.delete();  
//                    }
                }
                  
            } else {
            	log.error("PDF不存在,无法转换");  
            	return false;
            }  
        } else {  
            log.info("SWF["+swfFile.getPath()+"]已经存在不需要转换");
            return true;
        }  
    }
    
    /**
     * 返回SWF文件全路径  
     * @return
     */
    public String getSWFFullPath() {  
        if (swfFile.exists()) {  
            String path = swfFile.getPath();  
            path = path.replaceAll("\\\\", "/");  
            return path;  
        } else {  
            return "";  
        }  
  
    }
    
    /**
     * 处理输入流，防止堵塞
     */
    private class InputStreamWathThread extends Thread{
		private Process process = null;
		private boolean over = false;

		public InputStreamWathThread(Process p) {
			process = p;
			over = false;
		}

		public void run() {
			try {
				if (process == null) {
					log.error("process为null，无法处理文件转换");
					return;
				}
				// 对输入流，可能是一个回车之类的输入
				BufferedReader br = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				
				while (true) {
					if (process == null || over) {
						break;
					}
					String temp;
					while ((temp = br.readLine()) != null) {
						// log.info("输入流信息:" + temp);//如这些信息:NOTICE processing
						// PDF page 10 (595x842:0:0) (move:0:0)等等的打印时提示信息
					}
				}
			} catch (Exception e) {
				log.error("处理PDF2SWF转换输入流异常："+e.getMessage());
			}
		}

		public void setOver(boolean over) {
			this.over = over;
		}
    }
    
    /**
     * 处理出错流，防止堵塞
     */
	private class ErrorStreamWathThread extends Thread {
		private Process process = null;
		private boolean over = false;
		private String erroeMsg = "";

		public ErrorStreamWathThread(Process p) {
			process = p;
			over = false;
		}

		public void run() {
			try {
				if (process == null) {
					System.out.println("No__process");
					return;
				}
				// 对出错流的处理
				BufferedReader br = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));

				while (true) {
					if (process == null || over) {
						//log.info("出错流处理完成");
						break;
					}
					String temp;
					while ((temp = br.readLine()) != null) {
						erroeMsg += "\n";
						erroeMsg += temp;
						// log.info("出错流信息:" + temp);
					}
				}
			} catch (Exception e) {
				log.error("处理PDF2SWF转换出错流异常："+e.getMessage());
			}
		}

		public void setOver(boolean over) {
			this.over = over;
		}

		public String getErroeMsg() {
			return erroeMsg;
		}

	}
    

}
