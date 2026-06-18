package cn.tech.SCB.service;

import cn.tech.SCB.configure.SQLConfigure;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SQLService {
    private final SQLConfigure sqlConfigure;

    /*
    * 输入参数 : SQL所在的文件
    * */
    List<String> listTotalTableWithSqlFile(String sqlFile){
        return null;
    }
    /*
     * 输入参数1 : NameSql文件; 输入参数2 : nameSqlID
     * */
    List<String> listTotalTableWithNameSqlFile(String nameSqlFile, String NameSqlID){
            return null;
    }

}
