package cn.tech.shaun.customize.SCB.service;

import cn.tech.shaun.customize.SCB.dto.FeedFileClassdto;

import java.io.IOException;

public interface FeedFileClassService {
    /*
     * input: String (批量交易码前缀)
     * return: FeedFileClassdto (批量交易码对应的类)
     * desc : 输入批量交易码前缀, 返回类中所有的NameSqlID
     * */
    public FeedFileClassdto lsTotalNamesqlID(String fileBatchPrefix) throws IOException;
}
