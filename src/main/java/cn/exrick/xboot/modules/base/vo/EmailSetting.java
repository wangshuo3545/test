package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 */
@Data
public class EmailSetting implements Serializable {

    @Schema(description = "邮箱服务器")
    private String host;

    @Schema(description = "发送者邮箱账号")
    private String username;

    @Schema(description = "邮箱授权码")
    private String password;

    @Schema(description = "是否改变secrectKey")
    private Boolean changed;
}
