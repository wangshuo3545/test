package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
public class PermissionDTO {

    @Schema(description = "菜单标题")
    private String title;

    @Schema(description = "页面路径/资源链接url")
    private String path;
}
