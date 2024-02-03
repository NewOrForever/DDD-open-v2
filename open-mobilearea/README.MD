## 手机号码归属地查询服务
作为单体架构的一个本地服务，插入到open-server中。

## 本地服务插入规范:
服务端接入规范：
- 本地服务只要提供GsService接口的实现类，serviceCode()方法为该服务负责响应的前端请求码。doBusi()进行具体的业务处理。  
- 增加SPI配置文件，指向实现类即可。    
open-server会使用SPI机制加载GsService接口的所有实现类。