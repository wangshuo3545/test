package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 */
@Data
public class OtherSetting implements Serializable {

    @Schema(description = "域名")
    private String domain;

    @Schema(description = "单点登录域名")
    private String ssoDomain;

    @Schema(description = "IP黑名单")
    private String blacklist;
}
