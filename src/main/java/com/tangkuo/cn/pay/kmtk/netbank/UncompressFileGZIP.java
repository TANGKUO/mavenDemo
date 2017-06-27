package com.tangkuo.cn.pay.kmtk.netbank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import com.tangkuo.cn.pay.TtyException;


/**
 * 
* @ClassName: UncompressFileGZIP
* @Description: (借记卡对账文件减压缩类)
* @author tangkuo
* @date 2017年6月27日 下午3:28:22
*
 */
public class UncompressFileGZIP {
	
	/**  
     * 解压.gz文件
     * @param inFileName Name of the file to be uncompressed  
     * @param isDelete is delete the source gz file
     * @throws IOException 
     */
	public static String doUncompressFile(String inFileName,boolean isDelete) throws IOException {
		String outFileName = null;
    	GZIPInputStream in = null;
    	FileOutputStream out = null;
        try {   
            if (!getExtension(inFileName).equalsIgnoreCase("gz")) {   
            	throw new TtyException("failure","文件名的后缀必须是.gz");
            }
            in = new GZIPInputStream(new FileInputStream(inFileName));   
            outFileName = getFileName(inFileName);   
            out = new FileOutputStream(outFileName);   
  
            byte[] buf = new byte[1024];   
            int len;   
            while((len = in.read(buf)) > 0) {   
                out.write(buf, 0, len);   
            }
            out.flush();
            if(isDelete){
            	File f = new File(inFileName);
            	if(f.exists()){
            		f.delete();
            	}
            }
        }finally{
        	if(null != in){
        		in.close();
        	}
        	if(null != out){
        		out.close();
        	}
        }
        return outFileName;
	}
	
    /**  
     * 解压.gz文件
     * @param inFileName Name of the file to be uncompressed  
     * @throws IOException 
     */   
    public static String doUncompressFile(String inFileName) throws IOException {
    	return doUncompressFile(inFileName, false);
    }
    
    /**  
     * 获取文件的后缀名  
     * @param f Incoming file to get the extension of  
     * @return <code>String</code> representing the extension of the incoming  
     *         file.  
     */   
    private static String getExtension(String f) {   
        String ext = "";   
        int i = f.lastIndexOf('.');   
  
        if (i > 0 &&  i < f.length() - 1) {   
            ext = f.substring(i+1);   
        }        
        return ext;   
    }   
  
    /**  
     * 去掉文件的后缀名
     * Used to extract the filename without its extension.  
     * @param f Incoming file to get the filename  
     * @return <code>String</code> representing the filename without its  
     *         extension.  
     */   
    private static String getFileName(String f) {   
        String fname = "";   
        int i = f.lastIndexOf('.');   
  
        if (i > 0 &&  i < f.length() - 1) {   
            fname = f.substring(0,i);   
        }        
        return fname;   
    } 
  
}