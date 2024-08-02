package cn.exrick.xboot.modules.activiti.vo;

import cn.exrick.xboot.modules.base.entity.Department;
import cn.exrick.xboot.modules.base.entity.Role;
import cn.exrick.xboot.modules.base.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author Exrick
 */
@Data
public class ProcessNodeVo {

    @Schema(description = "节点id")
    private String id;

    @Schema(description = "节点名")
    private String title;

    @Schema(description = "节点类型 0开始 1用户任务 2结束 3排他网关")
    private Integer type;

    @Schema(description = "关联角色")
    private List<Role> roles;

    @Schema(description = "关联用户")
    private List<User> users;

    @Schema(description = "关联部门")
    private List<Department> departments;

    @Schema(description = "多级连续部门负责人")
    private Boolean chooseDepHeader = false;

    @Schema(description = "自选用户")
    private Boolean customUser = false;

    @Schema(description = "节点展开 前端所需")
    private Boolean expand = true;
}
