package cn.exrick.xboot.modules.base.entity.elasticsearch;

import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.common.utils.ObjectUtil;
import cn.exrick.xboot.common.utils.SnowFlakeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * Elasticsearch文档实体类
 * @author Exrick
 */
@Data
@Document(indexName = "log")
public class EsLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Schema(description = "唯一标识")
    private String id = SnowFlakeUtil.nextId().toString();

    /**
     * 如果使用的是自定义日期格式，则需要使用uuuu作为年份而不是yyyy
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date createTime = new Date();

    @Schema(description = "时间戳 查询时间范围及排序时使用")
    private Long timeMillis = System.currentTimeMillis();

    @Schema(description = "删除标志 默认0")
    private Integer delFlag = CommonConstant.STATUS_NORMAL;

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
