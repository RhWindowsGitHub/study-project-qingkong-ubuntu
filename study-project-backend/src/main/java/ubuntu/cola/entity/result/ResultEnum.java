package ubuntu.cola.entity.result;

import lombok.Getter;

/**
 * @author Cola_Ubuntu
 * @name ResultEnum
 * @DATE 2023/4/27 下午12:48
 * @description ResultEnum
 */
@Getter
public enum ResultEnum {

    SUCCESS(200,"成功"),
    WARN_ICMP(300,"重定向"),
    ERROR_CLIENT(400,"客户端错误"),
    ERROR_FORBIDDEN(403,"未授权 || 用户名或密码错误"),
    ERROR_NOTFOUND(404,"找不到请求资源"),
    ERROR_SERVE(500, "服务端错误，请联系管理员 1203952894@qq.com");

    private final Integer status;
    private final String message;

    private ResultEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
