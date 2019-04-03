package com.jacobs.module.messagecloud;

import lombok.Data;

/**
 * @author lichao
 * @date 2018/05/14
 */
@Data
public class CommonResponse {
    private int code;//状态码，success: 0，failure: （自定义: 如 httpcode+错误枚举值)
    private String msg;//目前只用在开发环境供开发识别，之后不再客户端展示
    private String data;//success状态存在，表示返回的数据
}
