package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 */
@Data
public class NoticeSetting implements Serializable {

    @Schema(description = "公告开关")
    private Boolean open;

    @Schema(description = "展示页面")
    private String position;

    @Schema(description = "展示时长")
    private Integer duration;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告内容")
    private String content;
}
