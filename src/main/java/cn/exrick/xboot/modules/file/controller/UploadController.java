package cn.exrick.xboot.modules.file.controller;

import cn.exrick.xboot.common.constant.SettingConstant;
import cn.exrick.xboot.common.exception.LimitException;
import cn.exrick.xboot.common.limit.RedisRaterLimiter;
import cn.exrick.xboot.common.utils.Base64DecodeMultipartFile;
import cn.exrick.xboot.common.utils.CommonUtil;
import cn.exrick.xboot.common.utils.IpInfoUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.entity.Setting;
import cn.exrick.xboot.modules.base.service.SettingService;
import cn.exrick.xboot.modules.base.vo.OssSetting;
import cn.exrick.xboot.modules.file.entity.File;
import cn.exrick.xboot.modules.file.manage.FileManageFactory;
import cn.exrick.xboot.modules.file.service.FileService;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Tag(name = "文件上传接口")
@RequestMapping("/xboot/upload")
@Transactional
public class UploadController {

    @Value("${xboot.maxUploadFile}")
    private Long maxUploadFile;

    @Autowired
    private RedisRaterLimiter redisRaterLimiter;

    @Autowired
    private FileManageFactory fileManageFactory;

    @Autowired
    private SettingService settingService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @Operation(summary = "文件上传")
    public Result upload(@RequestParam(required = false) MultipartFile file,
                         @RequestParam(required = false) String base64,
                         @RequestParam(required = false, defaultValue = "0") String categoryId,
                         HttpServletRequest request) {

        if (file == null && StrUtil.isBlank(base64)) {
            return ResultUtil.error("缺少文件参数");
        }

        if (file != null && file.getSize() > maxUploadFile * 1024 * 1024) {
            return ResultUtil.error("文件大小过大，不能超过" + maxUploadFile + "MB");
        }

        Setting setting = settingService.get(SettingConstant.OSS_USED);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            return ResultUtil.error(501, "您还未配置OSS存储服务");
        }

        // IP限流 在线Demo所需 5分钟限1个请求
        Boolean token = redisRaterLimiter.acquireByRedis("upload:" + IpInfoUtil.getIpAddr(request), 1L, 300000L);
        if (!token) {
            throw new LimitException("上传那么多干嘛，等等再传吧");
        }

        if (StrUtil.isNotBlank(base64)) {
            // base64上传
            file = Base64DecodeMultipartFile.base64Convert(base64);
        }
        String result = "";
        String fKey = CommonUtil.renamePic(file.getOriginalFilename());
        File f = new File();
        try {
            InputStream inputStream = file.getInputStream();
            // 上传至第三方云服务或服务器
            result = fileManageFactory.getFileManage().inputStreamUpload(inputStream, fKey, file);
            // 保存数据信息至数据库
            f.setLocation(SettingConstant.OSS_TYPE.getOrdinal(setting.getValue()))
                    .setTitle(file.getOriginalFilename()).setSize(file.getSize())
                    .setType(file.getContentType()).setFKey(fKey).setUrl(result).setCategoryId(categoryId);
            fileService.save(f);
        } catch (Exception e) {
            log.error(e.toString());
            return ResultUtil.error(e.toString());
        }
        if (setting.getValue().equals(SettingConstant.OSS_TYPE.LOCAL_OSS.name())) {
            OssSetting os = new Gson().fromJson(settingService.get(SettingConstant.OSS_TYPE.LOCAL_OSS.name()).getValue(), OssSetting.class);
            result = os.getHttp() + os.getEndpoint() + "/" + f.getId();
        }
        return ResultUtil.data(result);
    }
}
