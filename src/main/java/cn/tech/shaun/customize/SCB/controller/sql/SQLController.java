package cn.tech.shaun.customize.SCB.controller.sql;

import cn.tech.shaun.customize.SCB.dto.SQLAnalysisdto;
import cn.tech.shaun.customize.SCB.service.SQLService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sql")
public class SQLController {
    private final SQLService sqlService;

    @PostMapping("/AP001")
    public List<SQLAnalysisdto> AP001(@RequestBody String sql) {
        return sqlService.lsSqlAnalysisByInputSqlString(sql);
    }
}
