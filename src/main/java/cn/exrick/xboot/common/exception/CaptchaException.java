package cn.exrick.xboot.common.exception;

import lombok.Data;

/**
 * @author Exrick
 */
@Data
public class CaptchaException extends RuntimeException {

    private String msg;

    public CaptchaException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
