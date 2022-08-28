package com.github.backend.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MyPageable {

	@ApiModelProperty(value = "페이지 번호(0~N), default = 0")
	private int page;

	@ApiModelProperty(value = "페이지 크기, default = 10")
	private int size;

}
