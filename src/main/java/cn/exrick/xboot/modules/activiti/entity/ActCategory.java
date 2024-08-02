package cn.exrick.xboot.modules.activiti.entity;

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

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_act_category")
@TableName("t_act_category")
@Schema(description = "流程分类")
public class ActCategory extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "分类名称")
    private String title;

    @Schema(description = "父id")
    @Column(nullable = false)
    private String parentId;

    @Schema(description = "是否为父节点(含子节点) 默认false")
    private Boolean isParent = false;

    @Schema(description = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Schema(description = "类型 0分组 1分类")
    private Integer type;

    @Schema(description = "是否启用 0启用 -1禁用")
    private Integer status = CommonConstant.STATUS_NORMAL;

    @Transient
    @TableField(exist = false)
    @Schema(description = "父节点名称")
    private String parentTitle;
}
