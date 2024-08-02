package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 */
@Data
public class OssSetting implements Serializable {

    @Schema(description = "服务商")
    private String serviceName;

    @Schema(description = "ak")
    private String accessKey;

    @Schema(description = "sk")
    private String secretKey;

    @Schema(description = "endpoint域名")
    private String endpoint;

    @Schema(description = "bucket空间")
    private String bucket;

    @Schema(description = "http")
    private String http;

    @Schema(description = "zone存储区域")
    private Integer zone;

    @Schema(description = "bucket存储区域")
    private String bucketRegion;

    @Schema(description = "本地存储路径")
    private String filePath;

    @Schema(description = "是否改变secrectKey")
    private Boolean changed;
}
