package cn.exrick.xboot.modules.social.controller;

import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.base.service.UserService;
import cn.exrick.xboot.modules.social.entity.Social;
import cn.exrick.xboot.modules.social.service.SocialService;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author Exrick
 */
@Slf4j
@Tag(name = "绑定第三方账号接口")
@RequestMapping("/xboot/social")
@RestController
@Transactional
public class RelateController {

    @Autowired
    private UserService userService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @RequestMapping(value = "/relate", method = RequestMethod.POST)
    @Operation(summary = "绑定账号")
    public Result relate(@RequestParam Boolean isLogin,
                         @RequestParam(required = false) String username,
                         @RequestParam(required = false) String password,
                         @RequestParam Integer socialType,
                         @RequestParam String id) {

        if (isLogin) {
            // 用户已登录
            User user = securityUtil.getCurrUserSimple();
            username = user.getUsername();
        } else {
            // 用户未登录
            if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
                return ResultUtil.error("用户名或密码不能为空");
            }
            User user = userService.findByUsername(username);
            if (user == null) {
                return ResultUtil.error("账号或密码错误");
            }
            if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                return ResultUtil.error("账号或密码错误");
            }
        }

        // 从redis中获取表id
        String ID = redisTemplate.get(id);
        if (StrUtil.isBlank(ID)) {
            return ResultUtil.error("无效的id");
        }

        Social s = socialService.findByRelateUsernameAndPlatform(username, socialType);
        if (s != null) {
            return ResultUtil.error("该账户已绑定账号，请先进行解绑操作");
        }
        Social social = socialService.findById(ID);
        if (social == null) {
            return ResultUtil.error("绑定失败，请先进行第三方授权认证");
        }
        if (StrUtil.isNotBlank(social.getRelateUsername())) {
            return ResultUtil.error("该账号已绑定有用户，请先进行解绑操作");
        }
        social.setRelateUsername(username);
        socialService.update(social);

        if (!isLogin) {
            String JWT = securityUtil.getToken(username, true);
            // 存入redis
            String JWTKey = IdUtil.simpleUUID();
            redisTemplate.set(JWTKey, JWT, 2L, TimeUnit.MINUTES);
            return ResultUtil.data(JWTKey);
        } else {
            return ResultUtil.data("绑定成功");
        }
    }

    @RequestMapping(value = "/getJWT", method = RequestMethod.GET)
    @Operation(summary = "获取JWT")
    public Result getJWT(@RequestParam String JWTKey) {

        String JWT = redisTemplate.get(JWTKey);
        if (StrUtil.isBlank(JWT)) {
            return ResultUtil.error("获取JWT失败");
        }
        return ResultUtil.data(JWT);
    }
}
