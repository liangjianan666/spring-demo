package com.lja.infrastructure.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liangjianan
 * @Description
 * @Date 2021/10/12 14:41
 */
@Data
@ApiModel("学生请求类")
public class StudentRequest {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("编码")
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
