package cn.tech;

import cn.tech.shaun.customize.SCB.service.SQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Main {
    @Autowired
    private SQLService sQLService;
    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(Main.class, args);
        SQLService service =
                context.getBean(SQLService.class);
        System.out.println(service.lsSqlAnalysisByInputSqlString("select * from kdpa_sub_acct"));
    }
}