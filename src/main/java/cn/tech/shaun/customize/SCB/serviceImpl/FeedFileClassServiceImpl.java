package cn.tech.shaun.customize.SCB.serviceImpl;

import cn.tech.shaun.customize.SCB.configure.FeedFileClassConfigure;
import cn.tech.shaun.customize.SCB.dto.FeedFileClassdto;
import cn.tech.shaun.customize.SCB.service.FeedFileClassService;
import cn.tech.shaun.utils.ShaunFileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedFileClassServiceImpl implements FeedFileClassService {
    private final FeedFileClassConfigure fileProperties;
    @Override
    public FeedFileClassdto lsTotalNamesqlID(String fileBatchPrefix) throws IOException {
        FeedFileClassdto outDto = new FeedFileClassdto();
        //给一个目录, 返回该目录以及子目录下, 查处所有以fileBatchPrefix作为前缀的文件
        List<String> deptResult = ShaunFileUtils.lsFilesByPrefix(fileProperties.getDeptRootPath(),fileBatchPrefix);
        if(deptResult.size() <= 0){
            deptResult = ShaunFileUtils.lsFilesByPrefix(fileProperties.getReportRootPath(),fileBatchPrefix);
        }
        if(deptResult.size() > 0){
            outDto.setSFileName(deptResult.get(0));
            System.out.println(outDto.getSFileName().toString());
        }
        return null;
    }
}
