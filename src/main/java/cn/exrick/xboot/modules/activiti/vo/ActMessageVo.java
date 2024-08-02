package cn.exrick.xboot.modules.activiti.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Exrick
 */
@Data
public class ActMessageVo {

    @Schema(description = "是否发送站内消息")
    Boolean sendMessage = false;

    @Schema(description = "是否发送短信通知")
    Boolean sendSms = false;

    @Schema(description = "是否发送邮件通知")
    Boolean sendEmail = false;
}
