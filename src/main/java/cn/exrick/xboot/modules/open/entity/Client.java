package cn.exrick.xboot.modules.open.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
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
@Table(name = "t_client")
@TableName("t_client")
@Schema(description = "第三方网站client信息")
public class Client extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "网站名称")
    private String name;

    @Schema(description = "网站Logo")
    private String logo;

    @Schema(description = "网站主页")
    private String homeUri;

    @Schema(description = "秘钥")
    private String clientSecret;

    @Schema(description = "成功授权后的回调地址")
    private String redirectUri;

    @Schema(description = "自动授权 默认false")
    private Boolean autoApprove = false;
}
