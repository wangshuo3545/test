package cn.exrick.xboot.modules.activiti.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.ActivitiConstant;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_act_business")
@TableName("t_act_business")
@Schema(description = "业务申请")
public class ActBusiness extends XbootBaseEntity {

    @Schema(description = "申请标题")
    private String title;

    @Schema(description = "创建用户id")
    private String userId;

    @Schema(description = "关联表id")
    private String tableId;

    @Schema(description = "流程定义id")
    private String procDefId;

    @Schema(description = "流程实例id")
    private String procInstId;

    @Schema(description = "状态 0草稿默认 1处理中 2结束")
    private Integer status = ActivitiConstant.STATUS_TO_APPLY;

    @Schema(description = "结果状态 0未提交默认 1处理中 2通过 3驳回")
    private Integer result = ActivitiConstant.RESULT_TO_SUBMIT;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "提交申请时间")
    private Date applyTime;

    @Transient
    @TableField(exist = false)
    @Schema(description = "流程版本")
    private Integer version;

    @Transient
    @TableField(exist = false)
    @Schema(description = "分配用户id")
    private String[] assignees;

    @Transient
    @TableField(exist = false)
    @Schema(description = "所属流程名")
    private String processName;

    @Transient
    @TableField(exist = false)
    @Schema(description = "前端路由名")
    private String routeName;

    @Transient
    @TableField(exist = false)
    @Schema(description = "任务优先级 默认0")
    private Integer priority = 0;

    @Transient
    @TableField(exist = false)
    @Schema(description = "当前任务")
    private String currTaskName;

    @Transient
    @TableField(exist = false)
    @Schema(description = "第一个节点是否为网关")
    private Boolean firstGateway = false;

    @Transient
    @JsonIgnore
    @TableField(exist = false)
    @Schema(description = "流程设置参数")
    private Map<String, Object> params = new HashMap<>(16);
}
