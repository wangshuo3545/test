package cn.exrick.xboot.modules.activiti.entity;

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
@Table(name = "t_act_node")
@TableName("t_act_node")
@Schema(description = "节点")
public class ActNode extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "节点id")
    private String nodeId;

    @Schema(description = "节点关联类型 0角色 1用户 2部门")
    private Integer type;

    @Schema(description = "关联其他表id")
    private String relateId;
}
