package cn.tech.shaun.customize.SCB.serviceImpl;

import cn.tech.shaun.customize.SCB.dto.SQLAnalysisdto;
import cn.tech.shaun.customize.SCB.service.SQLService;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SQLServiceImpl implements SQLService {

    @Override
    public List<SQLAnalysisdto> lsSqlAnalysisByInputSqlString(String sqlString) {return null;}

    @Override
    public List<SQLAnalysisdto> lsSqlAnalysisByInputSqlFile(MultipartFile sqlFile) {
        return List.of();
    }
}
