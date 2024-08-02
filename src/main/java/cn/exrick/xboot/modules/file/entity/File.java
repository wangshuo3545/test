package cn.exrick.xboot.modules.file.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@Table(name = "t_file")
@TableName("t_file")
@Schema(description = "文件")
public class File extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "原文件名")
    private String title;

    @Schema(description = "存储文件名")
    private String fKey;

    @Schema(description = "大小")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long size;

    @Schema(description = "文件类型")
    private String type;

    @Schema(description = "路径")
    private String url;

    @Schema(description = "存储位置 0本地 1七牛 2阿里 3腾讯 4MinIO")
    private Integer location;

    @Schema(description = "类别id")
    private String categoryId;

    @Schema(description = "收藏")
    private Boolean isCollect;

    @Transient
    @TableField(exist = false)
    @Schema(description = "上传者用户名")
    private String nickname;

    @Transient
    @TableField(exist = false)
    @Schema(description = "剩余删除时间")
    private String restDelTime;
}
