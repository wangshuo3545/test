package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author Exrick
 */
@Data
public class MenuVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "父id")
    private String parentId;

    @Schema(description = "菜单/权限名称")
    private String name;

    @Schema(description = "始终显示")
    private Boolean showAlways;

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

    @Schema(description = "是否为站内菜单")
    private Boolean isMenu;

    @Schema(description = "网页链接")
    private String url;

    @Schema(description = "是否启用多语言 默认false")
    private Boolean localize = false;

    @Schema(description = "i18n渲染key")
    private String i18n;

    @Schema(description = "描述/备注")
    private String description;

    @Schema(description = "按钮权限类型")
    private String buttonType;

    @Schema(description = "子菜单/权限")
    private List<MenuVo> children;

    @Schema(description = "页面拥有的权限类型")
    private List<String> permTypes;
}
