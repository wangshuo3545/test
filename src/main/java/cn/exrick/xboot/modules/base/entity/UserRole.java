package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_user_role")
@TableName("t_user_role")
@Schema(description = "用户角色")
public class UserRole extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户唯一id")
    private String userId;

    @Schema(description = "角色唯一id")
    private String roleId;

    @Transient
    @TableField(exist = false)
    @Schema(description = "角色名")
    private String roleName;
}
