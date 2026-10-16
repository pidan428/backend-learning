package top.zqzhang.redis.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SUCCESS(200, "success"),
    BAD_REQUEST(400, "参数错误"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "系统繁忙，请稍后再试"),
    // ↓ 新增（课件只在箭头里提示"枚举类中补充了相应的业务异常"，没给具体写法，这行是我按你现有编码风格补的）
    SEND_MESSAGE_ERROR(30004, "短信发送失败");

    //业务异常
    //ACCOUNT_NOT_FOUND_ERROR(30001,"账号不存在"),
    //PASSWORD_ERROR(30002,"密码错误"),
    //ACCOUNT_ERROR(30003,"账号被禁用");

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

