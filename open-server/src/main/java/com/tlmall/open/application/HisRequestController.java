package com.tlmall.open.application;

import com.tlmall.open.domain.hisrequest.entity.HisRequestLog;
import com.tlmall.open.domain.hisrequest.service.HisRequestDomainService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author loulan
 * @desc
 */
@RestController
@RequestMapping("/hisRequest")
public class HisRequestController {

    private final Logger logger = Logger.getLogger(HisRequestController.class);
    @Resource
    private HisRequestDomainService hisRequestDomainService;

    @RequestMapping(value = "/getHistory", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object getHistory(String transId, String serviceCode) {
        logger.info("查询历史请求记录：transId => "+transId+";serviceCode => "+serviceCode);
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> paras = new HashMap<>();
        paras.put("transId", transId);
        paras.put("serviceCode", serviceCode);
        res.put("code", "1");
        res.put("desc", "获取数据成功！");
        res.put("data",hisRequestDomainService.queryHisRequest(transId,serviceCode));
        return res;
    }

    /**
     * 获取指定目录下transId文件列表
     */
    @RequestMapping(value = "/getTransIdLogFiles", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object getTransIdLogFiles(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        try {
            //transId命名的日志文件存放的根目录
            String transIdLogFilesRootPath = hisRequestDomainService.getTransLogDir();
            String transId = request.getParameter("transId");
            //transId为空，则列出最多100个文件。transId不为空，则只列出对应的文件。
            List<HisRequestLog> logfiles = hisRequestDomainService.listAllFiles(transId);
            res.put("code", "1");
            res.put("desc", "获取数据成功！");
            res.put("data", logfiles);
            res.put("logPath", transIdLogFilesRootPath);
        } catch (Exception ex) {
            res.put("code", "0");
            res.put("desc", "获取数据失败！");
            logger.error(ex.getMessage(), ex);
        }
        return res;
    }

    /**
     * 获取指定transId文件的内容
     */
    @RequestMapping(value = "/getTransIdLogFileContent", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object getTransIdLogFileContent(String transIdLogFilePath, String transIdLogFileName) {
        Map<String, Object> res = new HashMap<>();
        HisRequestLog hisRequestLog = hisRequestDomainService.readFileContent(transIdLogFilePath, transIdLogFileName);
        if(null != hisRequestLog){
            res.put("code", "1");
            res.put("desc", "获取数据成功！");
            res.put("data", hisRequestLog);
        }else{
            res.put("code", "0");
            res.put("desc", "获取数据失败！");
        }
        return res;
    }
}
