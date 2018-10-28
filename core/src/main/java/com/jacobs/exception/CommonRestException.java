package com.jacobs.exception;

import com.alibaba.fastjson.annotation.JSONField;
import javax.ws.rs.WebApplicationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by lichao on 2017/4/12.
 */
@NoArgsConstructor
public class CommonRestException extends WebApplicationException {

    @Getter
    private ExFactor exFactor;

    public CommonRestException(ExFactor exFactor) {
        this.exFactor = exFactor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommonExceptionModel {

        @JSONField(
            name = "http_code"
        )
        int httpCode;
        @JSONField(
            name = "error_code"
        )
        int errCode;
        @JSONField(
            name = "error_msg"
        )
        String errorMsg;
        @JSONField(
            name = "display_msg"
        )
        String detailMsg;
        @JSONField(
            name = "request_uri"
        )
        String requestUri;
    }
}
