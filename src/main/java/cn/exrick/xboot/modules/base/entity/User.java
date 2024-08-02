package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.UserConstant;
import cn.exrick.xboot.common.utils.NameUtil;
import cn.exrick.xboot.modules.base.vo.PermissionDTO;
import cn.exrick.xboot.modules.base.vo.RoleDTO;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_user")
@TableName("t_user")
@Schema(description = "用户")
public class User extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "登录名")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = NameUtil.regUsername, message = "登录账号不能包含中文、特殊字符 长度不能>16")
    private String username;

    @Schema(description = "密码")
    @NotNull(message = "不能为空")
    private String password;

    @Schema(description = "用户名/昵称/姓名")
    private String nickname;

    @Schema(description = "手机")
    @Pattern(regexp = NameUtil.regMobile, message = "11位手机号格式不正确")
    private String mobile;

    @Schema(description = "邮箱")
    @Pattern(regexp = NameUtil.regEmail, message = "邮箱格式不正确")
    private String email;

    @Schema(description = "省市县地址")
    private String address;

    @Schema(description = "街道地址")
    private String street;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "密码强度")
    @Column(length = 2)
    private String passStrength;

    @Schema(description = "用户头像")
    private String avatar = UserConstant.USER_DEFAULT_AVATAR;

    @Schema(description = "用户类型 0普通用户 1管理员")
    private Integer type = UserConstant.USER_TYPE_NORMAL;

    @Schema(description = "状态 默认0正常 -1拉黑")
    private Integer status = UserConstant.USER_STATUS_NORMAL;

    @Schema(description = "描述/详情/备注")
    private String description;

    @Schema(description = "所属部门id")
    private String departmentId;

    @Schema(description = "所属部门名称")
    private String departmentTitle;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "生日")
    private Date birth;

    @Transient
    @TableField(exist = false)
    @Schema(description = "用户拥有角色")
    private List<RoleDTO> roles;

    @Transient
    @TableField(exist = false)
    @Schema(description = "用户拥有的权限")
    private List<PermissionDTO> permissions;

    @Transient
    @TableField(exist = false)
    @Schema(description = "导入数据时使用")
    private Integer defaultRole;

    /**
     * 是否拥有某角色
     * @param title
     * @return
     */
    public Boolean hasRole(String title) {

        if (StrUtil.isBlank(title)) {
            return false;
        }
        for (RoleDTO r : this.roles) {
            if (title.equals(r.getName())) {
                return true;
            }
        }
        return false;
    }
}
