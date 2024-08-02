package cn.exrick.xboot.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Exrick
 */
@Slf4j
@Configuration
public class Swagger2Config {

    @Value("${swagger.title:XBoot}")
    private String title;

    @Value("${swagger.description:Api Documentation}")
    private String description;

    @Value("${swagger.version:1.0}")
    private String version;

    @Value("${swagger.termsOfServiceUrl:http://xboot.exrick.cn}")
    private String termsOfServiceUrl;

    @Value("${swagger.contact.name:Exrick}")
    private String name;

    @Value("${swagger.contact.url:http://exrick.cn}")
    private String url;

    @Value("${swagger.contact.email:1012139570@qq.com}")
    private String email;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title(title).description(description)
                .version(version).contact(new Contact().name(name).url(url).email(email))
                .termsOfService(termsOfServiceUrl));
    }
}
