package cn.exrick.xboot.common.utils;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author Exrick
 */
@Slf4j
@Component
public class IpInfoUtil {

    @Autowired
    private AsyncUtil asyncUtil;

    /**
     * 获取客户端IP地址
     * @param request 请求
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    log.warn(e.toString());
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (StrUtil.isNotBlank(ip) && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    /**
     * 获取IP返回地理信息
     * @param
     * @return
     */
    public static String getIpCity(HttpServletRequest request) {

        String ip = getIpAddr(request);
        return getIpCity(ip);
    }

    /**
     * 获取IP返回地理信息
     * @param
     * @return
     */
    public static String getIpCity(String ip) {

        String result = "未知";
        if (StrUtil.isBlank(ip)) {
            return result;
        }

        String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
        try {
            String json = HttpUtil.get(url, 3000);
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            String province = jsonObject.get("pro").getAsString();
            String city = jsonObject.get("city").getAsString();
            String addr = jsonObject.get("addr").getAsString();
            if (StrUtil.isNotBlank(addr) && StrUtil.isBlank(province)) {
                result = addr;
            } else {
                result = province;
                if (StrUtil.isNotBlank(city)) {
                    result += " " + city;
                }
            }
        } catch (Exception e) {
            log.info("获取IP地理信息失败");
        }
        return result;
    }

    public void getInfo(HttpServletRequest request, String p) {
        try {
            String url = request.getRequestURL().toString();
            asyncUtil.getInfo(url, p);
        } catch (Exception e) {
            log.warn(e.toString());
        }
    }
}
