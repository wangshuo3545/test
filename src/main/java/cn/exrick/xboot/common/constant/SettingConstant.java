package cn.exrick.xboot.common.constant;

/**
 * @author Exrick
 */
public interface SettingConstant {

    /**
     * 当前使用OSS
     */
    String OSS_USED = "OSS_USED";

    /**
     * 七牛云存储区域 自动判断
     */
    Integer ZONE_AUTO = -1;

    /**
     * 七牛云存储区域 华东
     */
    Integer ZONE_ZERO = 0;

    /**
     * 七牛云存储区域 华北
     */
    Integer ZONE_ONE = 1;

    /**
     * 七牛云存储区域 华南
     */
    Integer ZONE_TWO = 2;

    /**
     * 七牛云存储区域 北美
     */
    Integer ZONE_THREE = 3;

    /**
     * 七牛云存储区域 东南亚
     */
    Integer ZONE_FOUR = 4;

    /**
     * 当前使用短信
     */
    String SMS_USED = "SMS_USED";

    /**
     * 阿里短信配置
     */
    String ALI_SMS = "ALI_SMS";

    /**
     * 腾讯云短信配置
     */
    String TENCENT_SMS = "TENCENT_SMS";

    /**
     * 邮箱配置
     */
    String EMAIL_SETTING = "EMAIL_SETTING";

    /**
     * 其他配置
     */
    String OTHER_SETTING = "OTHER_SETTING";

    /**
     * 机器人配置
     */
    String CHAT_SETTING = "CHAT_SETTING";

    /**
     * 公告配置
     */
    String NOTICE_SETTING = "NOTICE_SETTING";

    /**
     * OSS配置类型
     */
    enum OSS_TYPE {
        // 本地OSS配置
        LOCAL_OSS,
        // 七牛OSS配置
        QINIU_OSS,
        // 阿里OSS配置
        ALI_OSS,
        // 腾讯COS配置
        TENCENT_OSS,
        // Minio配置
        MINIO_OSS;

        public static Boolean isContainName(String type) {
            for (OSS_TYPE item : OSS_TYPE.values()) {
                if (item.name().equals(type)) {
                    return true;
                }
            }
            return false;
        }

        public static Integer getOrdinal(String type) {
            for (OSS_TYPE item : OSS_TYPE.values()) {
                if (item.name().equals(type)) {
                    return item.ordinal();
                }
            }
            return -1;
        }

        public static String getName(Integer type) {
            if (type == null) {
                return "";
            }
            for (OSS_TYPE item : OSS_TYPE.values()) {
                if (type.equals(item.ordinal())) {
                    return item.name();
                }
            }
            return "";
        }
    }

    /**
     * 短信模版类型
     */
    enum SMS_TYPE {
        // 通用
        SMS_COMMON("通用验证码"),
        // 登录验证码
        SMS_LOGIN("登录验证码"),
        // 注册验证码
        SMS_REGISTER("注册验证码"),
        // 修改绑定手机号
        SMS_CHANGE_MOBILE("修改绑定手机号"),
        // 修改密码
        SMS_CHANGE_PASS("修改密码"),
        // 重置密码
        SMS_RESET_PASS("重置密码"),
        // 工作流消息
        SMS_ACTIVITI("工作流消息");

        private final String title;

        SMS_TYPE(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
