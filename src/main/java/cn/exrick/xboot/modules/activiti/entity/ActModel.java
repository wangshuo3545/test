package cn.exrick.xboot.modules.activiti.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
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
@Table(name = "t_act_model")
@TableName("t_act_model")
@Schema(description = "模型")
public class ActModel extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "标识")
    private String modelKey;

    @Schema(description = "版本")
    private Integer version;

    @Schema(description = "描述/备注")
    private String description;
}
