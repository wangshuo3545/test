package cn.exrick.xboot.modules.activiti.entity.business;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_leave")
@TableName("t_leave")
@Schema(description = "请假申请")
public class Leave extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "请假类型")
    private String type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "说明")
    private String description;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "开始日期")
    private Date startDate;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "截止日期")
    private Date endDate;

    @Schema(description = "请假时长（小时）")
    private Integer duration;
}
