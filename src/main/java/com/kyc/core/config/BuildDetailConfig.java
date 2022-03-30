package com.kyc.core.config;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static com.kyc.core.util.DateUtil.getDifference;

@Configuration
@ConditionalOnProperty(value = "management.info.build.enabled",havingValue = "false")
public class BuildDetailConfig {

    private LocalDateTime startUp;

    @PostConstruct
    public void init(){
        startUp = LocalDateTime.now();
    }

    @Bean
    public InfoContributor buildDetails(BuildProperties buildProperties) {


        return info ->
                info.withDetail("name", buildProperties.getName())
                        .withDetail("version", buildProperties.getVersion())
                        .withDetail("created",LocalDateTime.ofInstant(buildProperties.getTime(),ZoneId.of("UTC-6")))
                        .withDetail("running",getTimeRunning(startUp.atZone(ZoneId.of("UTC-6")).toInstant()))
                        .build();
    }

    private Long getTimeRunning(Instant time){
        return getDifference(LocalDateTime.ofInstant(time, ZoneId.of("UTC-6")),
                LocalDateTime.now(), ChronoUnit.MINUTES);
    }
}
