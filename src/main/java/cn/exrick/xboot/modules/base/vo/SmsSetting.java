package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
public class SmsSetting implements Serializable {

    @Schema(description = "服务商")
    private String serviceName;

    @Schema(description = "ak")
    private String accessKey;

    @Schema(description = "sk")
    private String secretKey;

    @Schema(description = "appId")
    private String appId;

    @Schema(description = "签名")
    private String signName;

    @Schema(description = "使用场景模版类型")
    private String type;

    @Schema(description = "模版code")
    private String templateCode;

    @Schema(description = "是否改变secrectKey")
    private Boolean changed;
}
