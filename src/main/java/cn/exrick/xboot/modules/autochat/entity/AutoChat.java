package cn.exrick.xboot.modules.autochat.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Accessors(chain = true)
@Table(name = "t_auto_chat")
@TableName("t_auto_chat")
@Schema(description = "问答助手客服")
public class AutoChat extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "问题标题")
    private String title;

    @Schema(description = "关键词")
    private String keywords;

    @Schema(description = "回答")
    private String content;

    @Schema(description = "热门消息")
    private Boolean hot = false;

    @Schema(description = "开启反馈（赞踩）")
    private Boolean evaluable = true;

    @Schema(description = "点赞数")
    private Integer good = 0;

    @Schema(description = "踩数")
    private Integer bad = 0;

    @Schema(description = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Transient
    @TableField(exist = false)
    @Schema(description = "回答纯文本")
    private String contentText;
}
