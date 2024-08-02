package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
public class RoleDTO {

    @Schema(description = "id")
    private String id;

    @Schema(description = "角色名 以ROLE_开头")
    private String name;

    @Schema(description = "备注")
    private String description;
}
