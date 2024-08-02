package cn.exrick.xboot.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 */
@Data
public class PageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "页号")
    private int pageNumber;

    @Schema(description = "页面大小")
    private int pageSize;

    @Schema(description = "排序字段")
    private String sort;

    @Schema(description = "排序方式 asc/desc")
    private String order;
}
