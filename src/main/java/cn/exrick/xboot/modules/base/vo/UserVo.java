package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
public class UserVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "账号")
    private String username;

    @Schema(description = "昵称")
    private String nickname;
}
