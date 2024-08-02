package cn.exrick.xboot.modules.base.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.CommonConstant;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Exrick
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_dict_data")
@TableName("t_dict_data")
@Schema(description = "字典数据")
public class DictData extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "数据名称")
    private String title;

    @Schema(description = "数据值")
    private String value;

    @Schema(description = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @Schema(description = "是否启用 0启用 -1禁用")
    private Integer status = CommonConstant.STATUS_NORMAL;

    @Schema(description = "备注")
    private String description;

    @Schema(description = "所属字典")
    private String dictId;
}
