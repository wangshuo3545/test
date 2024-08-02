package cn.exrick.xboot.modules.activiti.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.ActivitiConstant;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_act_process")
@TableName("t_act_process")
@Schema(description = "流程定义")
public class ActProcess extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "流程名称")
    private String name;

    @Schema(description = "流程标识名称")
    private String processKey;

    @Schema(description = "版本")
    private Integer version;

    @Schema(description = "部署id")
    private String deploymentId;

    @Schema(description = "所属分类")
    private String categoryId;

    @Schema(description = "xml文件名")
    private String xmlName;

    @Schema(description = "流程图片名")
    private String diagramName;

    @Schema(description = "描述/备注")
    private String description;

    @Schema(description = "最新版本")
    private Boolean latest;

    @Schema(description = "流程状态 部署后默认1激活")
    private Integer status = ActivitiConstant.PROCESS_STATUS_ACTIVE;

    @Schema(description = "关联前端表单路由名")
    private String routeName;

    @Schema(description = "关联业务表名")
    private String businessTable;

    @Schema(description = "是否所有人可见")
    private Boolean allUser;

    @Schema(description = "所属分类名称")
    private String categoryTitle;
}
