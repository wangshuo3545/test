package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.constant.ActivitiConstant;
import cn.exrick.xboot.common.constant.MessageConstant;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.base.entity.Message;
import cn.exrick.xboot.modules.base.entity.MessageSend;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.base.service.MessageSendService;
import cn.exrick.xboot.modules.base.service.MessageService;
import cn.exrick.xboot.modules.base.service.UserService;
import cn.hutool.http.HtmlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "消息内容管理接口")
@RequestMapping("/xboot/message")
@Transactional
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageSendService sendService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @Operation(summary = "多条件分页获取")
    public Result<Page<Message>> getByCondition(Message message,
                                                SearchVo searchVo,
                                                PageVo pageVo) {

        Page<Message> page = messageService.findByCondition(message, searchVo, PageUtil.initPage(pageVo));
        page.forEach(e -> {
            e.setContentText(HtmlUtil.cleanHtmlTag(e.getContent()));
        });
        return new ResultUtil<Page<Message>>().setData(page);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @Operation(summary = "通过id获取")
    public Result<Message> get(@PathVariable String id) {

        Message message = messageService.get(id);
        message.setContentText(HtmlUtil.filter(message.getContent()));
        return new ResultUtil<Message>().setData(message);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "添加消息")
    public Result addMessage(Message message) {

        Message m = messageService.save(message);
        // 保存消息发送表
        List<MessageSend> messageSends = new ArrayList<>();
        if (MessageConstant.MESSAGE_RANGE_ALL.equals(message.getRange())) {
            // 全体
            List<User> allUser = userService.getAll();
            allUser.forEach(u -> {
                MessageSend ms = new MessageSend().setMessageId(m.getId()).setUserId(u.getId());
                messageSends.add(ms);
            });
            sendService.saveOrUpdateAll(messageSends);
            // 推送
            messagingTemplate.convertAndSend("/topic/subscribe", "您收到了新的系统消息");
        } else if (MessageConstant.MESSAGE_RANGE_USER.equals(message.getRange())) {
            // 指定用户
            for (String id : message.getUserIds()) {
                MessageSend ms = new MessageSend().setMessageId(m.getId()).setUserId(id);
                messageSends.add(ms);
            }
            sendService.saveOrUpdateAll(messageSends);
            // 推送
            for (String id : message.getUserIds()) {
                messagingTemplate.convertAndSendToUser(id, "/queue/subscribe", "您收到了新的消息");
            }
        }
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Operation(summary = "编辑消息")
    public Result editMessage(Message message) {

        Message m = messageService.update(message);
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @Operation(summary = "删除消息")
    public Result delMessage(@RequestParam String[] ids) {

        for (String id : ids) {
            if (ActivitiConstant.MESSAGE_ID.isContainId(id)) {
                return ResultUtil.error("抱歉，无法删除工作流相关系统消息");
            }
            messageService.delete(id);
            // 删除发送表
            sendService.deleteByMessageId(id);
        }
        return ResultUtil.success("编辑成功");
    }
}
