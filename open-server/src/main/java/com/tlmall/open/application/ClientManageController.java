package com.tlmall.open.application;

import com.tlmall.open.domain.clientmanage.entity.ClientInfo;
import com.tlmall.open.domain.clientmanage.repository.SysCache;
import com.tlmall.open.domain.clientmanage.service.ClientInfoDomainService;
import com.tlmall.open.utils.GsConstants;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author loulan
 * @desc 用户接口层 客户端管理控制器
 * 应用接口层，只做场景接入，不做任何业务实现。
 */
@RestController
@RequestMapping("/sysManage")
public class ClientManageController {

    private final Logger logger = Logger.getLogger(ClientManageController.class);

    @Resource
    private ClientInfoDomainService clientInfoDomainService;

    @RequestMapping(value = "/newSys", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object newSys(@RequestBody ClientInfo client) throws Exception {
        logger.info("SysManageAction.newSys: client=> " + client);
        client.generateKey();
        int opRes = clientInfoDomainService.saveSys(client);
        Map<String, Object> res = new HashMap<>();
        if (opRes > 0) {
            res.put("code", 1);
            res.put("desc", "外围系统创建成功");
        } else {
            res.put("code", 0);
            res.put("desc", "外围系统创建失败。");
        }
        return res;
    }

    @RequestMapping(value = "/deleteSys", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object deleteSys(String sysId) {
        logger.info("SysManageAction.deleteSys: sysid=> " + sysId);
        int opRes = clientInfoDomainService.deleteSysById(sysId);
        return opRes;
    }

    @RequestMapping(value = "/querySys", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object querySys() {
        logger.info("SysManageAction.querySys: ");
        List<ClientInfo> res = clientInfoDomainService.queryAllSys();
        return res;
    }

    @RequestMapping(value = "/passKey", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object passKey(String passKey) {
        Map<String, Object> res = new HashMap<String, Object>();
        if (passKey.equals(GsConstants.PASSKEY)) {
            res.put("result", "0");
        } else {
            res.put("result", "1");
        }
        return res;
    }

    @RequestMapping(value = "/reloadSysCache",method = {RequestMethod.POST})
    public Object reloadSysCache(){
        clientInfoDomainService.reLoadConfig();
        return "client reloaded";
    }
}
