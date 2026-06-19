package cn.tech;

import cn.tech.shaun.customize.SCB.service.FeedFileClassService;
import cn.tech.shaun.customize.SCB.service.SQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Main {
    @Autowired
    private SQLService sQLService;
    @Autowired
    private FeedFileClassService feedFileClassService;
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context =
                SpringApplication.run(Main.class, args);
        FeedFileClassService service =
                context.getBean(FeedFileClassService.class);
        service.lsTotalNamesqlID("ff208");
    }
}