package cn.tech.shaun.customize.SCB.dto;

import lombok.Data;

import java.util.List;

@Data
public class SQLAnalysisdto {
    List<STable> stable;
    @Data
    public static class STable{
        String name;
        List<SWhere> sWhere;
        @Data
        public static class SWhere{
            String condition;
            List<String> sFields;
        }
    }
}
