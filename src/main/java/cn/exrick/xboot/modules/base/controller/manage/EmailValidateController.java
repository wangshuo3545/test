package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.common.constant.SettingConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.*;
import cn.exrick.xboot.common.vo.EmailValidate;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.Setting;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.base.service.SettingService;
import cn.exrick.xboot.modules.base.service.UserService;
import cn.exrick.xboot.modules.base.vo.OtherSetting;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "邮箱验证接口")
@RequestMapping("/xboot/email")
@Transactional
public class EmailValidateController {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private SettingService settingService;

    @Autowired
    private SecurityUtil securityUtil;

    public OtherSetting getOtherSetting() {

        Setting setting = settingService.get(SettingConstant.OTHER_SETTING);
        if (StrUtil.isBlank(setting.getValue())) {
            throw new XbootException("系统未配置访问域名，请联系管理员");
        }
        return new Gson().fromJson(setting.getValue(), OtherSetting.class);
    }

    @RequestMapping(value = "/sendEditCode/{email}", method = RequestMethod.GET)
    @Operation(summary = "发送修改邮箱验证码")
    public Result sendEditCode(@PathVariable String email,
                               HttpServletRequest request) {

        return sendEmailCode(email, "修改邮箱", "【XBoot】修改邮箱验证", "code-email", request);
    }

    @RequestMapping(value = "/sendResetCode/{email}", method = RequestMethod.GET)
    @Operation(summary = "发送重置密码邮箱验证码")
    public Result sendResetCode(@PathVariable String email,
                                HttpServletRequest request) {

        return sendEmailCode(email, "重置密码", "【XBoot】重置密码邮箱验证", "code-email", request);
    }

    /**
     * 发送邮件验证码
     * @param email
     * @param operation
     * @param title
     * @param template
     * @param request
     * @return
     */
    public Result sendEmailCode(String email, String operation, String title, String template, HttpServletRequest request) {

        // 生成验证码 存入相关信息
        EmailValidate e = new EmailValidate();
        e.setOperation(operation);
        // 验证是否注册
        User user = userService.findByEmail(email);
        if ("修改邮箱".equals(operation)) {
            if (user != null) {
                return ResultUtil.error("该邮箱已绑定账号");
            }
            User u = securityUtil.getCurrUserSimple();
            e.setUsername(u.getUsername());
        } else if ("重置密码".equals(operation)) {
            if (user == null) {
                return ResultUtil.error("该邮箱未注册");
            }
            e.setUsername(user.getUsername());
        }

        // IP限流 1分钟限1个请求
        String key = "sendEmailCode:" + IpInfoUtil.getIpAddr(request);
        String value = redisTemplate.get(key);
        if (StrUtil.isNotBlank(value)) {
            return ResultUtil.error("您发送的太频繁啦，请稍后再试");
        }

        String code = CommonUtil.getRandomNum();
        e.setCode(code);
        e.setEmail(email);
        e.setFullUrl(getOtherSetting().getDomain());
        redisTemplate.set(CommonConstant.PRE_EMAIL + email, new Gson().toJson(e, EmailValidate.class), 10L, TimeUnit.MINUTES);

        emailUtil.sendTemplateEmail(email, title, template, e);
        // 请求成功 标记限流
        redisTemplate.set(key, "sended", 1L, TimeUnit.MINUTES);
        return ResultUtil.success("发送成功");
    }

    @RequestMapping(value = "/editEmail", method = RequestMethod.POST)
    @Operation(summary = "修改邮箱或重置密码")
    public Result editEmail(@RequestParam String email) {

        User u = securityUtil.getCurrUser();
        u.setEmail(email);
        userService.update(u);
        // 删除缓存
        redisTemplate.delete("user::" + u.getUsername());
        return ResultUtil.success("修改邮箱成功");
    }
}
