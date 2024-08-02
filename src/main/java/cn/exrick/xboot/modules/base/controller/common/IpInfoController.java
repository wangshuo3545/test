package cn.exrick.xboot.modules.base.controller.common;

import cn.exrick.xboot.common.utils.IpInfoUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "IP接口")
@RequestMapping("/xboot/common/ip")
@Transactional
public class IpInfoController {

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Operation(summary = "IP相关信息")
    public Result info(HttpServletRequest request) {

        String result = IpInfoUtil.getIpCity(request);
        return ResultUtil.data(result);
    }
}
