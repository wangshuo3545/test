package cn.exrick.xboot.modules.base.vo;

import cn.exrick.xboot.common.constant.SecurityConstant;
import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.CommonUtil;
import cn.exrick.xboot.common.utils.IpInfoUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Exrick
 */
@Data
@Accessors(chain = true)
@Schema(description = "在线登录用户信息")
@AllArgsConstructor
public class OnlineUserVo {

    @Schema(description = "令牌")
    private String accessToken;

    @Schema(description = "账号")
    private String username;

    @Schema(description = "ip")
    private String ip;

    @Schema(description = "ip信息")
    private String ipInfo;

    @Schema(description = "设备信息")
    private String device;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "第一次活跃时间")
    private Date firstActiveTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "上次活跃时间")
    private Date lastActiveTime;

    @Schema(description = "持续活跃时间（分钟）")
    private Long activeTime;

    @Schema(description = "是否JWT交互令牌")
    private Boolean isJWT;

    public OnlineUserVo() {

    }

    /**
     * 构造新在线用户
     * @param accessToken
     * @param username
     * @param isJWT
     * @param request
     */
    public OnlineUserVo(String accessToken, String username, Boolean isJWT, HttpServletRequest request) {

        this.accessToken = accessToken;
        this.username = username;
        this.ip = IpInfoUtil.getIpAddr(request);
        this.device = CommonUtil.getDeviceInfo(request);
        Date date = new Date();
        this.firstActiveTime = date;
        this.lastActiveTime = date;
        this.activeTime = 0L;
        this.isJWT = isJWT;
    }

    /**
     * 更新在线用户信息
     * @param accessToken
     * @param username
     * @param isJWT
     * @param redisTemplate
     * @param request
     */
    public static void update(String accessToken, String username, Boolean isJWT, RedisTemplateHelper redisTemplate, HttpServletRequest request) {

        OnlineUserVo onlineUser;
        String key = isJWT ? SecurityConstant.ONLINE_USER_PRE + username : SecurityConstant.ONLINE_USER_PRE + username + ":" + accessToken;
        String v = redisTemplate.get(key);
        if (StrUtil.isBlank(v)) {
            onlineUser = new OnlineUserVo(accessToken, username, isJWT, request);
        } else {
            onlineUser = new Gson().fromJson(v, OnlineUserVo.class);
            // 更新信息
            onlineUser.setIp(IpInfoUtil.getIpAddr(request));
            onlineUser.setDevice(CommonUtil.getDeviceInfo(request));
            // 更新持续活跃时间
            onlineUser.setLastActiveTime(new Date());
            onlineUser.setActiveTime(DateUtil.between(onlineUser.getFirstActiveTime(), onlineUser.getLastActiveTime(), DateUnit.MINUTE));
        }
        redisTemplate.set(key, new Gson().toJson(onlineUser), 30L, TimeUnit.MINUTES);
    }
}
