package cn.exrick.xboot.modules.activiti.controller.business;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.activiti.entity.ActBusiness;
import cn.exrick.xboot.modules.activiti.entity.business.Leave;
import cn.exrick.xboot.modules.activiti.service.ActBusinessService;
import cn.exrick.xboot.modules.activiti.service.business.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "请假申请接口")
@Transactional
@RequestMapping(value = "/xboot/leave")
public class LeaveController extends XbootBaseController<Leave, String> {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private ActBusinessService actBusinessService;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public LeaveService getService() {
        return leaveService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "添加申请草稿状态")
    public Result add(Leave leave,
                      @RequestParam String procDefId) {

        Leave le = leaveService.save(leave);
        // 保存至我的申请业务
        String userId = securityUtil.getCurrUserSimple().getId();
        ActBusiness actBusiness = new ActBusiness();
        actBusiness.setUserId(userId);
        actBusiness.setTableId(le.getId());
        actBusiness.setProcDefId(procDefId);
        actBusiness.setTitle(leave.getTitle());
        actBusinessService.save(actBusiness);
        return ResultUtil.data(null);
    }
}
