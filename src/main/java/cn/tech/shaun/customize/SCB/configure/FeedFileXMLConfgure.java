package cn.tech.shaun.customize.SCB.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "easy.work.scb.feedfile.xml")
public class FeedFileXMLConfgure {
}
