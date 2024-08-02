package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.MessageConstant;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_message_send")
@TableName("t_message_send")
@Schema(description = "消息发送详情")
public class MessageSend extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "关联消息id")
    private String messageId;

    @Schema(description = "关联用户id")
    private String userId;

    @Schema(description = "消息参数")
    private String params;

    @Schema(description = "状态 0默认未读 1已读 2回收站")
    private Integer status = MessageConstant.MESSAGE_STATUS_UNREAD;

    @Transient
    @TableField(exist = false)
    @Schema(description = "发送登录名")
    private String username;

    @Transient
    @TableField(exist = false)
    @Schema(description = "发送用户名")
    private String nickname;

    @Transient
    @TableField(exist = false)
    @Schema(description = "消息标题")
    private String title;

    @Transient
    @TableField(exist = false)
    @Schema(description = "消息内容")
    private String content;

    @Transient
    @TableField(exist = false)
    @Schema(description = "消息类型")
    private String type;
}
