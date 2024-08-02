package cn.exrick.xboot.modules.quartz.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.CommonConstant;
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
@Table(name = "t_quartz_job")
@TableName("t_quartz_job")
@Schema(description = "定时任务")
public class QuartzJob extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "任务名")
    private String title;

    @Schema(description = "任务类名")
    private String jobClassName;

    @Schema(description = "cron表达式")
    private String cronExpression;

    @Schema(description = "失效执行策略")
    private Integer misfirePolicy;

    @Schema(description = "是否记录执行日志")
    private Boolean isRecordLog;

    @Schema(description = "参数")
    private String parameter;

    @Schema(description = "状态 0正常 -1停止")
    private Integer status = CommonConstant.STATUS_NORMAL;
}
