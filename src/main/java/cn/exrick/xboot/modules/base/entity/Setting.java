package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_setting")
@TableName("t_setting")
@Schema(description = "配置")
@NoArgsConstructor
public class Setting extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "配置值value")
    private String value;

    public Setting(String id) {

        super.setId(id);
    }
}
