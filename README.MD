https://github.com/996icu/996.ICU/blob/master/externals/instruction.md
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)

> 先新建本地库sc，执行schema.sql


1) bdp-register
 注册与发现服务
2) bdp-config 
 配置中心
3) bdp-gateway
 网关服务
4) bdp-common
 通用类包
5) bdp-services
 业务数据服务
 
 * 启动顺序：bdp-register => bdp-config => bdp-services => bdp-gateway
