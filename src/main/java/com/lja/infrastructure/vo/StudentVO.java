package com.lja.infrastructure.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liangjianan
 * @Description
 * @Date 2021/10/12 14:51
 */
@ApiModel("学生展示VO")
@Data
public class StudentVO {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("编码")
    private String code;
}
