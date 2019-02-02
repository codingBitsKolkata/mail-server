/**
 * @formatter:off
 *
 */
package com.orastays.mailserver.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@RequestMapping("/download")
public class DownloadController {
	
	private static final Logger logger = LogManager.getLogger(DownloadController.class);
	
	@Value("${project.name}")
	private String projectName;
	
	@RequestMapping("/{fileName:.+}")
    public void downloadRecentLog( HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) {
		
		if (logger.isInfoEnabled()) {
			logger.info("downloadRecentLog -- START");
		}
		
		try{
			String workingDir = System.getProperty("user.dir");
	 	    System.out.println("Current working directory : " + workingDir);
	        String fullPath = workingDir + File.separator + "logs" + File.separator + projectName + File.separator + fileName; 
	        System.out.println("fullPath = " + fullPath);
			File file = new File(fullPath);
			if (file.exists()) {
	
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("downloadRecentLog -- END");
		}
    }
	
	@RequestMapping("/old/{folderName:.+}/{fileName:.+}")
    public void downloadOldLog( HttpServletRequest request, HttpServletResponse response, @PathVariable("folderName") String folderName, @PathVariable("fileName") String fileName) {
		
		if (logger.isInfoEnabled()) {
			logger.info("downloadOldLog -- START");
		}
		
		try{
			String workingDir = System.getProperty("user.dir");
	 	    System.out.println("Current working directory : " + workingDir);
	 	   String fullPath = workingDir + File.separator + "logs" + File.separator + projectName + File.separator + folderName + File.separator + fileName;
	        System.out.println("fullPath = " + fullPath);
			File file = new File(fullPath);
			if (file.exists()) {
	
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (logger.isInfoEnabled()) {
			logger.info("downloadOldLog -- END");
		}
    }
}