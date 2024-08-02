package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.constant.MessageConstant;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.Message;
import cn.exrick.xboot.modules.base.entity.MessageSend;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.base.service.MessageSendService;
import cn.exrick.xboot.modules.base.service.MessageService;
import cn.exrick.xboot.modules.base.service.UserService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "消息发送管理接口")
@RequestMapping("/xboot/messageSend")
@Transactional
public class MessageSendController extends XbootBaseController<MessageSend, String> {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageSendService messageSendService;
    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public MessageSendService getService() {
        return messageSendService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @Operation(summary = "多条件分页获取")
    public Result<Page<MessageSend>> getByCondition(MessageSend ms,
                                                    PageVo pv) {

        Page<MessageSend> page = messageSendService.findByCondition(ms, PageUtil.initPage(pv));
        page.getContent().forEach(item -> {
            User u = userService.findById(item.getUserId());
            if (u != null) {
                item.setUsername(u.getUsername()).setNickname(u.getNickname());
            }
            Message m = messageService.findById(item.getMessageId());
            if (m != null) {
                if (m.getIsTemplate()) {
                    Message message = messageSendService.getTemplateMessage(item.getMessageId(),
                            new Gson().fromJson(item.getParams(), HashMap.class));
                    item.setTitle(message.getTitle()).setContent(message.getContent()).setType(m.getType());
                } else {
                    item.setTitle(m.getTitle()).setContent(m.getContent()).setType(m.getType());
                }
            }
        });
        return new ResultUtil<Page<MessageSend>>().setData(page);
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET)
    @Operation(summary = "单条消息标记已读")
    public Result read(@PathVariable String id) {

        User u = securityUtil.getCurrUserSimple();
        MessageSend messageSend = messageSendService.get(id);
        if (!u.getId().equals(messageSend.getUserId())) {
            return ResultUtil.error("您无权操作非本人数据");
        }
        messageSend.setStatus(MessageConstant.MESSAGE_STATUS_READ);
        messageSendService.update(messageSend);
        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/all/{type}", method = RequestMethod.GET)
    @Operation(summary = "批量操作消息")
    public Result batchOperation(@Param("0全部已读 1全部删除已读") @PathVariable Integer type) {

        User u = securityUtil.getCurrUserSimple();
        if (type == 0) {
            messageSendService.updateStatusByUserId(u.getId(), MessageConstant.MESSAGE_STATUS_READ);
        } else if (type == 1) {
            messageSendService.deleteByUserId(u.getId());
        }
        return ResultUtil.success("操作成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Operation(summary = "编辑")
    public Result edit(MessageSend messageSend) {

        if (messageService.findById(messageSend.getMessageId()) != null) {
            messageSendService.update(messageSend);
        }
        return ResultUtil.success("操作成功");
    }
}
