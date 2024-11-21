package org.ukdw.authservice.config;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();

    private final OAuth2 oauth2 = new OAuth2();

    private final EmailRegex emailRegex = new EmailRegex();

    private final NotificationConfig notificationConfig = new NotificationConfig();

    @Setter
    private List<String> excludeFilter;

    @Getter
    @Setter
    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationDay;
    }

    @Getter
    @Setter
    public static final class OAuth2 {
        private String googleClientId;
        private String googleSecret;
    }

    @Getter
    @Setter
    public static final class EmailRegex {
        private String ti;
        private String si;
        private String staff;
    }

    @Getter
    @Setter
    public static final class NotificationConfig {
        private String sound;
        private String color;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}