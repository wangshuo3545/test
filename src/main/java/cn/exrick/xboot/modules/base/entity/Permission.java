package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.CommonConstant;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_permission")
@TableName("t_permission")
@Schema(description = "菜单权限")
public class Permission extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单/权限名称")
    private String name;

    @Schema(description = "始终显示 默认是")
    private Boolean showAlways = true;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "类型 -1顶部菜单 0页面 1具体操作")
    private Integer type;

    @Schema(description = "菜单标题")
    private String title;

    @Schema(description = "页面路径/资源链接url")
    private String path;

    @Schema(description = "前端组件")
    private String component;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "按钮权限类型")
    private String buttonType;

    @Schema(description = "是否为站内菜单 默认true")
    private Boolean isMenu = true;

    @Schema(description = "网页链接")
    private String url;

    @Schema(description = "是否启用多语言 默认false")
    private Boolean localize = false;

    @Schema(description = "i18n渲染key")
    private String i18n;

    @Schema(description = "顶部菜单打开方式")
    private String description;

    @Schema(description = "父id")
    @Column(nullable = false)
    private String parentId;

    @Schema(description = "是否为父节点(含子节点) 默认false")
    private Boolean isParent = false;

    @Schema(description = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Schema(description = "是否启用 0启用 -1禁用")
    private Integer status = CommonConstant.STATUS_NORMAL;

    @Transient
    @TableField(exist = false)
    @Schema(description = "子菜单/权限")
    private List<Permission> children;

    @Transient
    @TableField(exist = false)
    @Schema(description = "页面拥有的权限类型")
    private List<String> permTypes;

    @Transient
    @TableField(exist = false)
    @Schema(description = "父节点名称")
    private String parentTitle;

    @Transient
    @TableField(exist = false)
    @Schema(description = "节点展开 前端所需")
    private Boolean expand = true;

    @Transient
    @TableField(exist = false)
    @Schema(description = "是否勾选 前端所需")
    private Boolean checked = false;

    @Transient
    @TableField(exist = false)
    @Schema(description = "是否选中 前端所需")
    private Boolean selected = false;
}
