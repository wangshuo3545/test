package cn.exrick.xboot.modules.file.manage;

import cn.exrick.xboot.common.constant.SettingConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.modules.base.entity.Setting;
import cn.exrick.xboot.modules.base.service.SettingService;
import cn.exrick.xboot.modules.file.manage.impl.*;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂模式
 * @author Exrick
 */
@Component
public class FileManageFactory {

    @Autowired
    private SettingService settingService;

    @Autowired
    private LocalFileManage localFileManage;

    @Autowired
    private QiniuFileManage qiniuFileManage;

    @Autowired
    private AliFileManage aliFileManage;

    @Autowired
    private TencentFileManage tencentFileManage;

    @Autowired
    private MinioFileManage minioFileManage;

    private static volatile Map<String, FileManage> fileManages;

    /**
     * 单例初始化所有类型文件对应实现类
     * @return
     */
    private Map<String, FileManage> getFileManages() {

        if (fileManages == null) {
            synchronized (this) {
                if (fileManages == null) {
                    fileManages = new HashMap<>(16);
                    fileManages.put(SettingConstant.OSS_TYPE.LOCAL_OSS.name(), localFileManage);
                    fileManages.put(SettingConstant.OSS_TYPE.QINIU_OSS.name(), qiniuFileManage);
                    fileManages.put(SettingConstant.OSS_TYPE.ALI_OSS.name(), aliFileManage);
                    fileManages.put(SettingConstant.OSS_TYPE.TENCENT_OSS.name(), tencentFileManage);
                    fileManages.put(SettingConstant.OSS_TYPE.MINIO_OSS.name(), minioFileManage);
                }
            }
        }
        return fileManages;
    }

    /**
     * 管理文件时使用
     * @return
     */
    public FileManage getFileManage() {

        return getFileManage(null);
    }

    /**
     * 使用配置的服务上传时location传入null 管理文件时需传入存储位置location
     * @param location
     * @return
     */
    public FileManage getFileManage(Integer location) {

        Setting setting = settingService.get(SettingConstant.OSS_USED);
        if (setting == null || StrUtil.isBlank(setting.getValue())) {
            throw new XbootException("您还未配置OSS存储服务");
        }
        String type = setting.getValue();
        Map<String, FileManage> manages = getFileManages();
        for (Map.Entry<String, FileManage> entry : manages.entrySet()) {
            String key = entry.getKey();
            FileManage manage = entry.getValue();
            if ((type.equals(key) && location == null) || key.equals(SettingConstant.OSS_TYPE.getName(location))) {
                return manage;
            }
        }
        throw new XbootException("暂不支持该存储配置，请检查配置");
    }
}
