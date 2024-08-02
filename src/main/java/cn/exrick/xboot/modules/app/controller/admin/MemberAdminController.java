package cn.exrick.xboot.modules.app.controller.admin;

import cn.exrick.xboot.common.constant.MemberConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SnowFlakeUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.app.entity.Member;
import cn.exrick.xboot.modules.app.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "会员管理接口")
@RequestMapping("/xboot/app/member")
@CacheConfig(cacheNames = "member")
@Transactional
public class MemberAdminController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @Operation(summary = "多条件分页获取")
    public Result<Page<Member>> getByCondition(Member member,
                                               SearchVo searchVo,
                                               PageVo pageVo) {

        Page<Member> page = memberService.findByCondition(member, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Member>>().setData(page);
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @Operation(summary = "添加用户")
    public Result add(@Valid Member m) {

        if (memberService.findByMobile(m.getMobile()) != null) {
            throw new XbootException("该手机号已被注册");
        }

        Long uid = SnowFlakeUtil.nextId();
        // Username/UID 邀请码
        m.setUsername(uid.toString()).setInviteCode(Long.toString(uid, 32).toUpperCase());

        Member member = memberService.save(m);
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @Operation(summary = "管理员修改资料", description ="需要通过id获取原用户信息 需要mobile更新缓存")
    @CacheEvict(key = "#m.mobile")
    public Result edit(@Valid Member m) {

        Member old = memberService.get(m.getId());

        m.setUsername(old.getUsername()).setPassword(old.getPassword());
        // 若修改了手机和邮箱判断是否唯一
        if (!old.getMobile().equals(m.getMobile()) && memberService.findByMobile(m.getMobile()) != null) {
            return ResultUtil.error("该手机号已绑定其他账户");
        }

        memberService.update(m);
        return ResultUtil.success("修改成功");
    }

    @RequestMapping(value = "/admin/status", method = RequestMethod.POST)
    @Operation(summary = "后台禁用用户")
    public Result disable(@RequestParam String userId,
                          @RequestParam Boolean enable) {

        Member member = memberService.get(userId);
        if (enable) {
            member.setStatus(MemberConstant.MEMBER_STATUS_NORMAL);
        } else {
            member.setStatus(MemberConstant.MEMBER_STATUS_LOCK);
        }
        memberService.update(member);
        // 手动更新缓存
        redisTemplate.delete("member::" + member.getMobile());
        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "批量通过ids删除")
    public Result delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            Member m = memberService.get(id);
            // 删除相关缓存
            redisTemplate.delete("member::" + m.getMobile());
            memberService.delete(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
