package cn.tech.shaun.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ShaunFileUtils {
    /*
     * input: String (文件名前缀)
     * return: List<String> (包含绝对路径的匹配文件)
     * desc : 输入目录, 查询该目录以及子目录下所有匹配该文件前缀的文件
     * */
    public static List<String> lsFilesByPrefix(String spath, String sFileNamePrefix) throws IOException {
        List<String> result = new ArrayList<>();
        Files.walk(Paths.get(spath))  // 递归遍历目录
                .filter(Files::isRegularFile) // 只要文件
                .forEach(path -> {String fileName = path.getFileName().toString();
                    if (fileName.startsWith(sFileNamePrefix)) { //前缀匹配到, 则保存该文件名
                        result.add(path.getParent().toString() + "/" + fileName);
                    }});
        return result;
    }

}
