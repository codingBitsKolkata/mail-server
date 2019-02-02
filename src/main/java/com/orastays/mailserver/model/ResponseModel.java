package com.orastays.mailserver.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class ResponseModel {

	@ApiModelProperty(notes = "Response Code", required =false)
	private String responseCode;
	
	@ApiModelProperty(notes = "Response Message", required =false)
	private String responseMessage;
	
	@ApiModelProperty(notes = "Response", required =false)
	private Object responseBody;

}
