package ru.leverx.dealerStatistics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("ru.leverx.dealerStatistics")
@EnableWebMvc
@EnableJpaRepositories("ru.leverx.dealerStatistics.repository")
public class DealerStatisticsConfiguration implements WebMvcConfigurer {
    //WebConfig
    private final ApplicationContext applicationContext;

    @Autowired
    public DealerStatisticsConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
