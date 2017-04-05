/**
 * 
 */
package com.csgg.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 *
 */
@Controller
public class TestController {

	@RequestMapping("/test/test.act")
	public String showSometing(String xxx) {
		System.out.println("-------------------------------------------");
		return "show over";
	}
}
