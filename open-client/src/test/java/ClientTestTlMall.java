import com.alibaba.fastjson.JSONObject;
import com.ftoul.ftoulClient.FtClient;
import com.ftoul.ftoulClient.ReqHeader;

/**
 * @author loulan
 * @desc 调用电商实现项目的接口
 */
public class ClientTestTlMall {

    public static void main(String[] args) throws Exception {
        System.setProperty("http.proxySet", "true");
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");

        FtClient ftClient = new FtClient();
        //一、基础参数部分
        //服务提供地址 服务稳定后就可以集成到客户端包中。
        ftClient.setEndPoint("http://localhost:8711/genSI/gsInterfaceAsync");
        //以下三个根据服务处理时长灵活配置。不设也行， 有默认值。
        ftClient.setConnectionRequestTimeout(1000);
        ftClient.setConnectTimeout(30000);
        ftClient.setSocketTimeout(30000);
        //向第三方系统分配
        ftClient.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlDHck/Rq+Isx0i6chZ4rPqQxeeJPJ8Xv96pkcLdx//oWSgvUk1UHiMpDKT8Bx2dZSj0z3MwQBJKTeetUTij66phG++focHBjeAAxPnQZDBIwYtmGr/sMvi2PJeQd/sQpdYOcJyyVXyobRf1YcI9OQV/DzaOrSUohuVVnXod9irlj+sHkrfzmHx6KZmrPozrEena9rfRaQtHRT5Enq0X3jncJq4K25YLBi96uFv4787JNQWqRUUzSHxEIsh+Sc7GRw6wvncAjnq3p17j7Xo/uXZNqIRurutTvscDWv+JwuSav9tEjfAiVSD8lM/JkQdTKk7AODOTyUmaUE8pvrYbhmwIDAQAB");
        String ftRes = "";
        //二、header参数部分
        ReqHeader reqHeader = new ReqHeader();
        //向第三方系统分配
        reqHeader.setSysId("MyApp");
        //用户名，密码，暂时无验证方案。预留。
//        reqHeader.setSysUser("testUser");
//        reqHeader.setSysPwd("testPwd");
        //唯一的请求ID。 重复的transId将直接返回第一次请求的结果。
        reqHeader.setTransId("2021111201518");
        //服务功能码-查询电商项目中的用户信息 服务码与Nacos上注册的服务名对应
        reqHeader.setServiceCode("tulingmall-member");
        ftClient.setReportHeader(reqHeader);

        //三、业务参数部分 由提供的接口文档确定
        JSONObject reportbody = new JSONObject();
        reportbody.put("username", "admin");
        ftClient.setReportBody(reportbody.toJSONString());

        ftRes = ftClient.postRequest();
        //同步返回请求接收结果。具体的业务结果将向sysId配置的返回路径进行推送。
        System.out.println(ftRes);
    }
}
