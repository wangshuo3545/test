package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.CommonConstant;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_role")
@TableName("t_role")
@Schema(description = "角色")
public class Role extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色名 以ROLE_开头")
    private String name;

    @Schema(description = "是否为注册默认角色")
    private Boolean defaultRole;

    @Schema(description = "数据权限类型 0全部默认 1自定义 2本部门及以下 3本部门 4仅本人")
    private Integer dataType = CommonConstant.DATA_TYPE_ALL;

    @Schema(description = "备注")
    private String description;

    @Transient
    @TableField(exist = false)
    @Schema(description = "拥有权限")
    private List<RolePermission> permissions;

    @Transient
    @TableField(exist = false)
    @Schema(description = "拥有数据权限")
    private List<RoleDepartment> departments;
}
