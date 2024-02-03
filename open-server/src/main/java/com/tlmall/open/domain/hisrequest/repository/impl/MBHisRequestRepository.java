package com.tlmall.open.domain.hisrequest.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tlmall.open.domain.hisrequest.entity.HisRequest;
import com.tlmall.open.domain.hisrequest.entity.HisRequestLog;
import com.tlmall.open.domain.hisrequest.infrastructure.HisConfig;
import com.tlmall.open.domain.hisrequest.infrastructure.HisRequestMapper;
import com.tlmall.open.domain.hisrequest.repository.HisRequestRepository;
import com.tlmall.open.utils.GsConstants;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author loulan
 * @desc
 */
@Repository
public class MBHisRequestRepository implements HisRequestRepository {

    @Resource
    private HisRequestMapper hisRequestMapper;


    @Resource
    private HisConfig hisConfig;

    @Override
    public List<HisRequest> queryHisRequest(String transId, String serviceCode) {
        QueryWrapper<HisRequest> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(transId)) {
            queryWrapper.eq("transId", transId);
        }
        if (StrUtil.isNotEmpty(serviceCode)) {
            queryWrapper.eq("serviceCode", serviceCode);
        }
        return hisRequestMapper.selectList(queryWrapper);
    }

    @Override
    public int addHisRequest(HisRequest hisrequest) {
        return hisRequestMapper.insert(hisrequest);
    }

    @Override
    public List<HisRequestLog> ListAllFiles(String transId) {
        String transIdLogFilesRootPath = hisConfig.getTransLogDir();
        List<HisRequestLog> dataList = new ArrayList<>();
        List<File> files = (List<File>) FileUtils.listFiles(new File(transIdLogFilesRootPath), new String[]{GsConstants.LOG_SUFFIX}, false);
        if (files != null && !files.isEmpty()) {
            //按文件最后修改时间倒序排序
            Collections.sort(files, (file1, file2) -> (int) (file2.lastModified() - file1.lastModified()));
            //如果transId不为空，只展现该transId的文件。 否则，返回最近的100个日志文件
            Predicate<File> logFileFilter = (f) -> f.getName().contains(transId.trim());
            if (StrUtil.isNotEmpty(transId)) {
                files = files.stream().filter(logFileFilter).collect(Collectors.toList());
            } else if (files.size() > GsConstants.LOG_FILE_MAX_SIZE) {
                files = files.subList(0, GsConstants.LOG_FILE_MAX_SIZE);
            }
            for (File file : files) {
                HisRequestLog hislog = new HisRequestLog(file.getAbsolutePath(),file.getName());
                dataList.add(hislog);
            }
        }
        return dataList;
    }

    @Override
    public HisRequestLog readFileContent(String transIdLogFilePath, String transIdLogFileName) {
        try {
            String content = FileUtils.readFileToString(new File(transIdLogFilePath), "utf-8");
            return new HisRequestLog(transIdLogFilePath,transIdLogFileName,content);
        } catch (IOException e) {
            return null;
        }
    }
}
