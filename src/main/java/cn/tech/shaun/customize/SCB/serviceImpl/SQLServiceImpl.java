package cn.tech.shaun.customize.SCB.serviceImpl;

import cn.tech.shaun.customize.SCB.dto.SQLAnalysisdto;
import cn.tech.shaun.customize.SCB.dto.SQLAnalysisdto.STable;
import cn.tech.shaun.customize.SCB.dto.SQLAnalysisdto.STable.SWhere;
import cn.tech.shaun.customize.SCB.service.SQLService;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.WithItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SQLServiceImpl implements SQLService {

    @Override
    public List<SQLAnalysisdto> lsSqlAnalysisByInputSqlString(String sqlString) {
        List<SQLAnalysisdto> result = new ArrayList<>();
        try {
            Statement statement = CCJSqlParserUtil.parse(sqlString);
            Set<String> cteNames = new HashSet<>();
            List<PlainSelect> selects = new ArrayList<>();
            if (statement instanceof Select s && s.getWithItemsList() != null) {
                for (WithItem<?> wi : s.getWithItemsList()) {
                    if (wi.getAlias() != null) {
                        cteNames.add(wi.getAlias().getName().toLowerCase());
                    }
                }
            }
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
                    if (!cteNames.contains(name.toLowerCase())) {
                        String alias = table.getAlias() != null ? table.getAlias().getName() : null;
                        tableInfo.add(new String[]{name, alias});
                    }
                } else if (ps.getFromItem() instanceof ParenthesedSelect sub) {
                    tableInfo.add(new String[]{sub.toString(), null});
                }

                if (ps.getJoins() != null) {
                    for (Join join : ps.getJoins()) {
                        if (join.getRightItem() instanceof Table table) {
                            String name = table.getName();
                            if (!cteNames.contains(name.toLowerCase())) {
                                String alias = table.getAlias() != null ? table.getAlias().getName() : null;
                                tableInfo.add(new String[]{name, alias});
                            }
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
                    List<SWhere> whereList = new ArrayList<>();
                    String lowerName = info[0].toLowerCase();
                    String lowerAlias = info[1] != null ? info[1].toLowerCase() : null;
                    for (String cond : conditions) {
                        String lower = cond.toLowerCase();
                        boolean match = (lowerAlias != null && lower.contains(lowerAlias + "."))
                                || lower.contains(lowerName + ".");
                        if (match) {
                            SWhere sw = new SWhere();
                            sw.setCondition(cond);
                            List<String> condFields = new ArrayList<>();
                            try {
                                Expression condExpr = CCJSqlParserUtil.parseExpression(cond);
                                List<Expression> exprStack = new ArrayList<>();
                                exprStack.add(condExpr);
                                while (!exprStack.isEmpty()) {
                                    Expression e = exprStack.remove(exprStack.size() - 1);
                                    if (e instanceof Column col) {
                                        condFields.add(col.toString());
                                    } else if (e instanceof BinaryExpression bin) {
                                        exprStack.add(bin.getRightExpression());
                                        exprStack.add(bin.getLeftExpression());
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            sw.setSFields(condFields);
                            whereList.add(sw);
                        }
                    }
                    t.setSWhere(whereList);
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
