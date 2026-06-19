package cn.tech.shaun.customize.SCB.service;

import cn.tech.shaun.customize.SCB.dto.SQLAnalysisdto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface SQLService {
    /*
    * input: String (可执行的SQL字符串)
    * return: SQLAnalysisdto (该SQL的每个成分)
    * desc : 输入一段SQL, 分析该SQL的每个成分
    * */
    public List<SQLAnalysisdto> lsSqlAnalysisByInputSqlString(String sqlString);

    /*
     * input: MultipartFile (HTTP上传SQL的文本)
     * return: SQLAnalysisdto (该SQL的每个成分)
     * desc : 上传一个HTTP文本, 分析该文本内SQL的每个成分
     * */
    List<SQLAnalysisdto> lsSqlAnalysisByInputSqlFile(MultipartFile sqlFile);
}
