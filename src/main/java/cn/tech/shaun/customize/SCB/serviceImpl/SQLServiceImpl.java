package cn.tech.shaun.customize.SCB.serviceImpl;

import cn.tech.shaun.customize.SCB.dto.SQLAnalysisdto;
import cn.tech.shaun.customize.SCB.dto.SQLAnalysisdto.STable;
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
    public List<SQLAnalysisdto> lsSqlAnalysisByInputSqlString(String sqlString) {
        List<SQLAnalysisdto> result = new ArrayList<>();
        try {
            Statement statement = CCJSqlParserUtil.parse(sqlString);
            List<PlainSelect> selects = new ArrayList<>();
            if (statement instanceof PlainSelect ps) {
                selects.add(ps);
            } else if (statement instanceof SetOperationList setOpList) {
                for (Select body : setOpList.getSelects()) {
                    if (body instanceof PlainSelect ps) {
                        selects.add(ps);
                    }
                }
            }
            for (PlainSelect ps : selects) {
                List<STable> tables = new ArrayList<>();
                List<String> fields = new ArrayList<>();
                if (ps.getSelectItems() != null) {
                    for (SelectItem<?> item : ps.getSelectItems()) {
                        fields.add(item.toString());
                    }
                }

                List<String> conditions = new ArrayList<>();
                if (ps.getWhere() != null) {
                    List<Expression> stack = new ArrayList<>();
                    stack.add(ps.getWhere());
                    while (!stack.isEmpty()) {
                        Expression expr = stack.remove(stack.size() - 1);
                        if (expr instanceof AndExpression and) {
                            stack.add(and.getRightExpression());
                            stack.add(and.getLeftExpression());
                        } else {
                            conditions.add(expr.toString());
                        }
                    }
                }

                List<String[]> tableInfo = new ArrayList<>();

                if (ps.getFromItem() instanceof Table table) {
                    String name = table.getName();
                    String alias = table.getAlias() != null ? table.getAlias().getName() : null;
                    tableInfo.add(new String[]{name, alias});
                } else if (ps.getFromItem() instanceof ParenthesedSelect sub) {
                    tableInfo.add(new String[]{sub.toString(), null});
                }

                if (ps.getJoins() != null) {
                    for (Join join : ps.getJoins()) {
                        if (join.getRightItem() instanceof Table table) {
                            String name = table.getName();
                            String alias = table.getAlias() != null ? table.getAlias().getName() : null;
                            tableInfo.add(new String[]{name, alias});
                            if (join.getOnExpression() != null) {
                                conditions.add(join.getOnExpression().toString());
                            }
                        } else if (join.getRightItem() instanceof ParenthesedSelect sub) {
                            tableInfo.add(new String[]{sub.toString(), null});
                        }
                    }
                }

                for (String[] info : tableInfo) {
                    STable t = new STable();
                    t.setName(info[0]);
                    List<String> conds = new ArrayList<>();
                    String lowerName = info[0].toLowerCase();
                    String lowerAlias = info[1] != null ? info[1].toLowerCase() : null;
                    for (String cond : conditions) {
                        String lower = cond.toLowerCase();
                        if ((lowerAlias != null && lower.contains(lowerAlias + "."))
                                || lower.contains(lowerName + ".")) {
                            conds.add(cond);
                        }
                    }
                    t.setSWhere(conds);
                    t.setSFields(fields);
                    tables.add(t);
                }

                SQLAnalysisdto dto = new SQLAnalysisdto();
                dto.setStable(tables);
                result.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("SQL parse error: " + sqlString, e);
        }
        return result;
    }

    @Override
    public List<SQLAnalysisdto> lsSqlAnalysisByInputSqlFile(MultipartFile sqlFile) {
        return List.of();
    }
}
