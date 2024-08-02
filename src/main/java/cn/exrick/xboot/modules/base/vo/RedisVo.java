package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
public class RedisVo {

    @Schema(description = "key")
    private String key;

    @Schema(description = "value")
    private String value;

    @Schema(description = "过期时间（秒）")
    private Long expireTime;

    @Schema(description = "是否可编辑")
    private Boolean isEditable;

    public RedisVo(String key) {
        this.key = key;
        this.isEditable = true;
    }
}
