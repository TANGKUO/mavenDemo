package com.tangkuo.cn.utils.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 
* @ClassName: ValiCodeServlet
* @Description: (验证码自动生成器,自定义输出规则.)
* @author tangkuo
* @date 2017年7月2日 下午2:34:36
*
 */
public class ValiCodeServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("image/jpeg");
        //设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response);
            //输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }

}
