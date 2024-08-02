package cn.exrick.xboot.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 * 前后端交互数据标准
 */
@Data
@Schema(description = "结果对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "成功标志")
    private boolean success;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "返回代码")
    private Integer code;

    @Schema(description = "时间戳")
    private long timestamp = System.currentTimeMillis();

    @Schema(description = "结果对象")
    private T result;
}
