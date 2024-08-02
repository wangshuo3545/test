package cn.exrick.xboot.modules.quartz.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_quartz_log")
@TableName("t_quartz_log")
@Schema(description = "定时任务执行日志")
public class QuartzLog extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "任务类名")
    private String jobClassName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "执行时间")
    private Date executeTime;

    @Schema(description = "花费时间")
    private Integer costTime;
}