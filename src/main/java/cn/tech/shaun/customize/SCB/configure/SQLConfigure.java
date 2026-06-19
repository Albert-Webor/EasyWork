package cn.tech.shaun.customize.SCB.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "easy.work.scb.sql")
public class SQLConfigure {
    private String sqlFile;
    private String namesqlDeptFile;
    private String namesqlReportFile;
}
