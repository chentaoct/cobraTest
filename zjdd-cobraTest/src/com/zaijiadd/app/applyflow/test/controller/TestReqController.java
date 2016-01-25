package com.zaijiadd.app.applyflow.test.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestReqController {
	/**
	 * 
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/postInputStreamNote",method=RequestMethod.POST)
	@ResponseBody
	public void postInputStreamNote(@RequestBody String body) throws IOException{
		System.out.println(body);
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/postInputStreamNotePathVariable/{user}",method=RequestMethod.POST)
	@ResponseBody
	public void postInputStreamNote(@PathVariable int user) throws IOException{
		System.out.println(user);
	}
	/**
	 * 
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/postInputStreamNoteRequestParam",method=RequestMethod.POST)
	@ResponseBody
	public void postInputStreamNoteRequestParam(@RequestParam String body) throws IOException{
		System.out.println(body);
	}
	/**
	 * 
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/postInputStream",method=RequestMethod.POST)
	@ResponseBody
	public void postInputStream(HttpServletRequest request) throws IOException{
		StringBuilder info = parseReq(request);
		System.out.println(info.toString());
	}
	private StringBuilder parseReq(HttpServletRequest request)
			throws IOException, UnsupportedEncodingException {
		StringBuilder info = new StringBuilder();
		InputStream inputStream = request.getInputStream();
		BufferedInputStream buf = new BufferedInputStream(inputStream);
		byte[] buffer=new byte[1024];
		int iRead;
		while ((iRead=buf.read(buffer))!=-1) {
			info.append(new String(buffer, 0,iRead,"UTF-8"));
		}
		return info;
	}
	
	/**
	 * 
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/getInputStream",method=RequestMethod.GET)
	@ResponseBody
	public void getInputStream(HttpServletRequest request) throws IOException{
		StringBuilder info = parseReq(request);
		
		System.out.println(info.toString());
	}
	
	@RequestMapping(value="/postParameter",method=RequestMethod.POST)
	@ResponseBody
	public void postParameter(HttpServletRequest request) throws IOException{
//		 String user = request.getParameter("user");
//		 System.out.println(user);
		 StringBuilder info = parseReq(request);
			System.out.println(info.toString());
		
	}
	
	
	@RequestMapping(value="/getParameter",method=RequestMethod.GET)
	@ResponseBody
	public void getParameter(HttpServletRequest request) throws IOException{
		 String user = request.getParameter("user");
		 StringBuilder info = parseReq(request);
			System.out.println(info.toString());
		 System.out.println(user);
	}
	
	@RequestMapping(value="/postHeader",method=RequestMethod.POST)
	@ResponseBody
	public void test2(HttpServletRequest request) throws IOException{
		 String user = request.getHeader("user");
		 System.out.println(user);
	}
	@RequestMapping(value="/getHeader",method=RequestMethod.GET)
	@ResponseBody
	public void getHeader(HttpServletRequest request) throws IOException{
		 String user = request.getParameter("user");
		 StringBuilder info = parseReq(request);
			System.out.println(info.toString());
		 System.out.println(user);
		 String user2 = request.getHeader("user");
		 System.out.println(user);
		 
	}

}
