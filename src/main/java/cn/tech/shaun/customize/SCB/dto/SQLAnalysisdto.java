package cn.tech.shaun.customize.SCB.dto;

import lombok.Data;

import java.util.List;

@Data
public class SQLAnalysisdto {
    List<STable> stable;
    public static class STable{
        List<String> sWhere;
        List<String> sFields;
    }
}
