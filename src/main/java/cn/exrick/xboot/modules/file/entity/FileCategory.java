package cn.exrick.xboot.modules.file.entity;

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
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_file_category")
@TableName("t_file_category")
@Schema(description = "文件分类")
public class FileCategory extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "名称")
    private String title;

    @Schema(description = "父id")
    @Column(nullable = false)
    private String parentId;

    @Schema(description = "是否为父节点(含子节点) 默认false")
    private Boolean isParent = false;

    @Schema(description = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Schema(description = "收藏")
    private Boolean isCollect;

    @Transient
    @TableField(exist = false)
    @Schema(description = "父节点名称")
    private String parentTitle;

    @Transient
    @TableField(exist = false)
    @Schema(description = "剩余删除时间")
    private String restDelTime;
}
