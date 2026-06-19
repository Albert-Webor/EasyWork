package cn.tech.shaun.customize.SCB.configure;

import lombok.Data;
import org.openjdk.jmh.annotations.CompilerControl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "easy.work.scb.feedfile.class")
public class FeedFileClassConfigure {
    private String deptRootPath;
    private String reportRootPath;
}
