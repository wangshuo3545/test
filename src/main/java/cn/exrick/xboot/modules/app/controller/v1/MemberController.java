package cn.exrick.xboot.modules.app.controller.v1;

import cn.exrick.xboot.common.annotation.SystemLog;
import cn.exrick.xboot.common.enums.LogType;
import cn.exrick.xboot.common.utils.NameUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.utils.SnowFlakeUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.app.entity.Member;
import cn.exrick.xboot.modules.app.service.MemberService;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "会员接口")
@RequestMapping(value = "/xboot/app/v1/member/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private EntityManager entityManager;

    @RequestMapping(value = "/quickLogin", method = RequestMethod.POST)
    @Operation(summary = "手机号快捷登录/注册")
    @SystemLog(description = "快捷登录", type = LogType.MEMBER_LOGIN)
    public Result quickLogin(@RequestParam String mobile,
                             @RequestParam(required = false) String inviteCode,
                             @RequestParam(required = false, defaultValue = "-1") Integer platform) {

        if (!NameUtil.mobile(mobile)) {
            return ResultUtil.error("11位手机号格式不正确");
        }
        Boolean isNew = false;
        String inviteBy = null;
        if (StrUtil.isNotBlank(inviteCode)) {
            Long uid = Long.parseLong(inviteCode, 32);
            Member inviter = memberService.findByUsername(uid.toString());
            if (inviter == null) {
                return ResultUtil.error("邀请码不正确");
            }
            inviteBy = inviter.getUsername();
        }
        Member member = memberService.findByMobile(mobile);
        if (member == null) {
            // 先注册
            String nickname = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
            Long uid = SnowFlakeUtil.nextId();
            member = new Member().setUsername(uid.toString()).setInviteCode(Long.toString(uid, 32).toUpperCase()).
                    setMobile(mobile).setNickname(nickname).setPlatform(platform).setInviteBy(inviteBy);
            memberService.save(member);
            isNew = true;
        }
        // 登录
        String appToken = securityUtil.getAppToken(member, platform);
        Map<String, Object> result = new HashMap<>(16);
        result.put("isNew", isNew);
        result.put("token", appToken);
        return ResultUtil.data(result);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Operation(summary = "获取当前登录会员信息接口")
    public Result<Member> getUserInfo() {

        Member member = securityUtil.getCurrMember();
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.detach(member);
        member.setPassword(null);
        return ResultUtil.data(member);
    }
}
