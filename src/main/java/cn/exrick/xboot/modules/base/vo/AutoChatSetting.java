package cn.exrick.xboot.modules.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrick
 */
@Data
public class AutoChatSetting implements Serializable {

    @Schema(description = "对话框标题")
    private String title;

    @Schema(description = "机器人头像")
    private String avatar;

    @Schema(description = "初始化系统消息")
    private String sysMessage;

    @Schema(description = "初始化消息")
    private String welcomeMessage;

    @Schema(description = "初始化富文本消息")
    private String initMessage;

    @Schema(description = "快捷短语")
    private String quickReplies;

    @Schema(description = "输入框占位符")
    private String placeholder;

    @Schema(description = "右侧公告类型")
    private String noticeType;

    @Schema(description = "右侧公告标题")
    private String noticeTitle;

    @Schema(description = "右侧公告内容")
    private String noticeContent;

    @Schema(description = "点赞后显示的文本")
    private String textOfGood;

    @Schema(description = "点踩后显示的文本")
    private String textOfBad;

    @Schema(description = "未找到匹配回答答复")
    private String noDataReply;
}
