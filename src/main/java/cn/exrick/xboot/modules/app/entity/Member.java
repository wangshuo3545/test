package cn.exrick.xboot.modules.app.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import cn.exrick.xboot.common.constant.MemberConstant;
import cn.exrick.xboot.common.utils.NameUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "app_member")
@TableName("app_member")
@Schema(description = "会员（注册用户）")
public class Member extends XbootBaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名/UID")
    @Column(unique = true, nullable = false)
    private String username;

    @Schema(description = "邀请码")
    private String inviteCode;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "昵称")
    @Size(max = 20, message = "昵称长度不能超过20")
    private String nickname;

    @Schema(description = "手机")
    @Pattern(regexp = NameUtil.regMobile, message = "11位手机号格式不正确")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "性别")
    private String sex;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "生日")
    private Date birth;

    @Schema(description = "积分 默认0")
    private Integer grade = 0;

    @Schema(description = "定位")
    private String position;

    @Schema(description = "地区")
    private String address;

    @Schema(description = "简介")
    private String description;

    @Schema(description = "邀请人")
    private String inviteBy;

    @Schema(description = "会员头像")
    private String avatar = MemberConstant.MEMBER_DEFAULT_AVATAR;

    @Schema(description = "会员类型 默认0普通用户 1会员")
    private Integer type = MemberConstant.MEMBER_TYPE_NORMAL;

    @Schema(description = "状态 默认0正常 -1拉黑禁用")
    private Integer status = MemberConstant.MEMBER_STATUS_NORMAL;

    @Schema(description = "注册平台来源 -1未知 0PC/H5 1安卓 2苹果 3微信 4支付宝 5QQ 6字节 7百度")
    private Integer platform;

    @Schema(description = "拥有权限信息 多个逗号分隔 默认MEMBER")
    private String permissions = MemberConstant.MEMBER_PERMISSION;

    @Schema(description = "VIP状态 默认0未开通 1已开通 2已过期")
    private Integer vipStatus = MemberConstant.MEMBER_VIP_NONE;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "会员开通时间")
    private Date vipStartTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "会员到期时间")
    private Date vipEndTime;
}
