package com.orastays.mailserver.controller;

import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.orastays.mailserver.exceptions.FormExceptions;
import com.orastays.mailserver.exceptions.MailSendException;
import com.orastays.mailserver.helper.AuthConstant;
import com.orastays.mailserver.helper.MessageUtil;
import com.orastays.mailserver.helper.Util;
import com.orastays.mailserver.model.MailModel;
import com.orastays.mailserver.model.ResponseModel;
import com.orastays.mailserver.service.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
@Api(value = "Email", description = "Rest API for Email", tags = "Email API")
public class MailController {

	private static final Logger logger = LogManager.getLogger(MailController.class);
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MessageUtil messageUtil;
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value = "/send-mail", method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "Send Email", response = ResponseModel.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 201, message = "Please Try after Sometime!!!"),
			@ApiResponse(code = 602, message = "Please provide Email Id"),
			@ApiResponse(code = 603, message = "Please provide Email Body"),
			@ApiResponse(code = 604, message = "Please provide Email Subject") })
	public ResponseEntity<ResponseModel> sendMail(@RequestBody MailModel mailModel) {

		if (logger.isInfoEnabled()) {
			logger.info("sendMail -- START");
		}

		ResponseModel responseModel = new ResponseModel();

		try {
			mailService.sendMail(mailModel);
			responseModel.setResponseBody(messageUtil.getBundle("email.send.success"));
			responseModel.setResponseCode(messageUtil.getBundle(AuthConstant.COMMON_SUCCESS_CODE));
			responseModel.setResponseMessage(messageUtil.getBundle(AuthConstant.COMMON_SUCCESS_MESSAGE));
		} catch (FormExceptions fe) {

			for (Entry<String, Exception> entry : fe.getExceptions().entrySet()) {
				responseModel.setResponseCode(entry.getKey());
				responseModel.setResponseMessage(entry.getValue().getMessage());
				break;
			}
		} catch (MailSendException e) {
			e.printStackTrace();
			responseModel.setResponseBody(messageUtil.getBundle("email.send.failure"));
			responseModel.setResponseCode(messageUtil.getBundle(AuthConstant.COMMON_ERROR_CODE));
			responseModel.setResponseMessage(messageUtil.getBundle(AuthConstant.COMMON_ERROR_MESSAGE));
		}

		Util.printLog(responseModel, AuthConstant.OUTGOING, "Send Email", request);

		if (logger.isInfoEnabled()) {
			logger.info("sendMail -- END");
		}
		
		if (responseModel.getResponseCode().equals(messageUtil.getBundle(AuthConstant.COMMON_SUCCESS_CODE))) {
			return new ResponseEntity<>(responseModel, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
		}
	}
}
