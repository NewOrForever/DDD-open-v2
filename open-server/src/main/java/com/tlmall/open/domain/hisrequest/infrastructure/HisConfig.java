package com.tlmall.open.domain.hisrequest.infrastructure;

import cn.hutool.core.util.StrUtil;
import com.tlmall.open.utils.GsConstants;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author loulan
 * @desc
 */
@Component
@ConfigurationProperties(prefix = "gs.log")
public class HisConfig {

    private Logger fLogger = Logger.getLogger(this.getClass());

    private String transLogDir;

    public String getTransLogDir() {
        return transLogDir;
    }

    public void setTransLogDir(String transLogDir) {
        this.transLogDir = transLogDir;
    }

    public void addTransLogAppender(Logger logger,String transId){
        String targetFileName=  this.getTransLogDir()+"/"+transId+"."+ GsConstants.LOG_SUFFIX;
        fLogger.info("追加trans日志到"+targetFileName);
        //log4j Appender 可以重复进行add或者remove，因此不需要判断是否已经有fa
        if(null != logger && StrUtil.isNotEmpty(transId)){
            logger.removeAppender("fa");
            FileAppender fa = GsConstants.getTransLogAppender();
            fa.setFile(targetFileName);
            fa.activateOptions();//将配置生效
            logger.removeAllAppenders();
            logger.setAdditivity(true);

            logger.addAppender(fa);
        }
    }

    public void removeTransLogAppender(Logger logger){
        //log4j Appender 可以重复进行add或者remove，因此不需要判断是否已经有fa
        logger.removeAppender("fa");
    }
}
