package cn.tech.shaun.customize.SCB.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedFileClassdto {
    String sFileName ; //文件名
    List<String> sParm; //类所有字段
    List<String> sMethod; //类中所有方法
    List<String> sSql; //类中所有SQL
    List<String> sComment;//类中所有注释
}
