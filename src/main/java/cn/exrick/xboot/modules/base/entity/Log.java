package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.utils.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_log")
@TableName("t_log")
@Schema(description = "日志")
public class Log extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "方法操作名称")
    private String name;

    @Schema(description = "日志类型 0登陆日志 1操作日志")
    private Integer logType;

    @Schema(description = "请求路径")
    private String requestUrl;

    @Schema(description = "请求类型")
    private String requestType;

    @Schema(description = "请求参数")
    private String requestParam;

    @Schema(description = "请求用户")
    private String username;

    @Schema(description = "ip")
    private String ip;

    @Schema(description = "ip信息")
    private String ipInfo;

    @Schema(description = "设备信息")
    private String device;

    @Schema(description = "花费时间")
    private Integer costTime;

    /**
     * 转换请求参数为Json
     * @param paramMap
     */
    public void setMapToParams(Map<String, String[]> paramMap) {

        this.requestParam = ObjectUtil.mapToString(paramMap);
    }
}
