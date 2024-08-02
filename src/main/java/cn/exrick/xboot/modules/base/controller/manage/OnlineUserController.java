package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.constant.SecurityConstant;
import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.IpInfoUtil;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.vo.OnlineUserVo;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "在线用户管理接口")
@RequestMapping("/xboot/onlineUser")
@Transactional
public class OnlineUserController {

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @Operation(summary = "分页获取全部")
    public Result<Page<OnlineUserVo>> getAllByPage(@RequestParam(required = false) String key,
                                                   PageVo pageVo) {

        Boolean isAdmin = securityUtil.getCurrUser().hasRole("ROLE_ADMIN");
        List<OnlineUserVo> list = new ArrayList<>();

        if (StrUtil.isNotBlank(key)) {
            key = SecurityConstant.ONLINE_USER_PRE + "*" + key + "*";
        } else {
            key = SecurityConstant.ONLINE_USER_PRE + "*";
        }
        Set<String> keys = redisTemplate.scan(key);
        for (String s : keys) {
            String v = redisTemplate.get(s);
            if (StrUtil.isBlank(v)) {
                continue;
            }
            OnlineUserVo userVo = new Gson().fromJson(v, OnlineUserVo.class);
            userVo.setIpInfo(IpInfoUtil.getIpCity(userVo.getIp()));
            // 在线Demo所需 仅管理员可查看令牌 实际可通过权限菜单控制
            if (!isAdmin) {
                userVo.setAccessToken("");
            }
            list.add(userVo);
        }
        Page<OnlineUserVo> page = new PageImpl<OnlineUserVo>(PageUtil.listToPage(pageVo, list), PageUtil.initPage(pageVo), keys.size());
        return new ResultUtil<Page<OnlineUserVo>>().setData(page);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @Operation(summary = "批量强制下线")
    public Result logout(@RequestBody List<OnlineUserVo> users) {

        for (OnlineUserVo user : users) {
            if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getAccessToken())) {
                return ResultUtil.error("缺少必要字段");
            }
            if (user.getIsJWT()) {
                // JWT模式 添加记录
                redisTemplate.sAdd(SecurityConstant.ONLINE_USER_JWT_LOGOUT_SET_KEY, user.getAccessToken());
                redisTemplate.expire(SecurityConstant.ONLINE_USER_JWT_LOGOUT_SET_KEY, 1L, TimeUnit.DAYS);
                // 删除在线用户记录
                redisTemplate.delete(SecurityConstant.ONLINE_USER_PRE + user.getUsername());
            } else {
                // redis模式
                redisTemplate.delete(SecurityConstant.TOKEN_PRE + user.getAccessToken());
                redisTemplate.delete(SecurityConstant.USER_TOKEN + user.getUsername());
                // 删除在线用户记录
                redisTemplate.delete(SecurityConstant.ONLINE_USER_PRE + user.getUsername() + ":" + user.getAccessToken());
            }
        }
        return ResultUtil.success("操作成功");
    }
}
