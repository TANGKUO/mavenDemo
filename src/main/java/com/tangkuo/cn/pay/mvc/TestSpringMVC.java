package com.tangkuo.cn.pay.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author tangkuo
 *
 */
@Controller
public class TestSpringMVC {
	@RequestMapping(value="/Hello")  
    public String HelloWorld(Model model){  
        model.addAttribute("message","Hello World!!!");  
        return "HelloWorld";  
    }  
	
	
	public static void main(String[] args) {
		
	}

}
