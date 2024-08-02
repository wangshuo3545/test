package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_role_permission")
@TableName("t_role_permission")
@Schema(description = "角色权限")
public class RolePermission extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色id")
    private String roleId;

    @Schema(description = "权限id")
    private String permissionId;
}
