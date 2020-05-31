package com.talissonmelo.resource.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardError {

	private String message;
	private Integer status;
}
