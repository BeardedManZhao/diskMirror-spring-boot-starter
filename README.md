# ![image](https://github.com/BeardedManZhao/DiskMirror/assets/113756063/b8a15b22-5ca0-4552-aab2-7131c63dc727)  diskMirror-starter

DiskMirror 的 starter，通过引入此类，可以直接实现 diskMirror 在 SpringBoot 中的自动配置，接下来我们将使用案例逐步的演示
diskMirror starter 的使用 需要注意的是，此
starter 最适用于 SpringBoot3 版本，且也推荐使用 SpringBoot3 版本的自动配置模块!!!

## 引入 starter

我们可以直接在 maven 中像下面这样配置好 starter，在下面我们引入了一些依赖，如果需要使用第三方文件系统适配器，则可以直接在
maven 中引入，如果您不需要第三方文件系统，可以不引入
根据您的需求来进行选择，如果您不知道需要的库有哪些，可以查阅 [diskMirror 的主页](https://github.com/BeardedManZhao/DiskMirror.git)

```xml

<dependencies>
    <!-- 导入 SpringBoot 项目的 starter 在这里我们使用的示例是 Web starter 您可以根据您的需要修改 starter 类型 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- 导入 DiskMirror 的 starter TODO 一般来说 只需要导入这个和其它的第三方文件系统依赖即可 -->
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>diskMirror-spring-boot-starter</artifactId>
        <version>1.0.4</version>
    </dependency>
    <!-- 导入 fastjson2 库 这是一个JSON解析库，被 diskMirror 依赖，您可以像上面一样导入 也是一个可选操作 -->
    <dependency>
        <groupId>com.alibaba.fastjson2</groupId>
        <artifactId>fastjson2</artifactId>
        <version>2.0.25</version>
    </dependency>
</dependencies>
```

### 版本对应列表

有时候您在使用 starter 的过程中，可能遇到一些版本不兼容的情况，他们可能会导致一些奇怪的问题，这里我们列一下兼容情况。

如果您不期望自己来进行版本对应的审查，您可以只导入 starter 即可， starter 会自动加载兼容的依赖。

| starter版本  | 其支持的 diskMirror 版本 | 其支持的 zhao-utils 版本 |
|------------|--------------------|--------------------|
| `<= 1.0.2` | `*`                | `*`                |
| `>= 1.0.3` | `>= 1.3.0`         | `>= 1.0.20241026`  |

## 开发基本的 SpringBoot3 项目

在这里您可以根据一些简单的示例来了解如何使用 diskMirror 的盘镜 starter。

### 配置 starter

diskMirror 的 starter 配置文件具有默认数值，下面就是默认的配置文件，同样，您可以按照下面的示例来进行配置文件的修改。

PS 请将 enable-feature 设置为 true!!!

```yaml
disk-mirror:
  # 此配置项目代表的就是是否启用 diskMirror 如果设置为 false 则代表不启用，diskMirror 的starter 将不会被加载
  enabled-feature: false
  # 要使用的盘镜适配器类型 在这里默认数值是本地盘镜适配器，具体的适配器 您可以查阅 top.lingyuzhao.diskMirror.core.DiskMirror 类
  adapter-type: "LocalFSAdapter"
  # 要被盘镜管理的目录 用于存储数据的目录 此目录是真实目录
  root-dir: "/DiskMirror"
  # 一般来说 如果对接带第三方文件系统 而非本次文件系统 则此参数则会派上用场，其代表的就是第三方文件系统的地址
  fs-default-fs: "hdfs://localhost:8020/"
  # 当处理之后，如果处理无错误会返回一个结果状态，此数值代表的就是是否正确处理
  ok-value: "ok!!!!"
  # 返回结果的key 返回结果中 结果状态的字段名字
  res-key: "res"
  # 协议前缀，默认为http 不同协议前缀有不同的意义，用于拼接 url
  protocol-prefix: "http://localhost:80/"
  # 参数 可能会派上用场，在不同的适配器中有不同的实现
  params: { }
  # 用户磁盘配额 每个盘镜空间的磁盘最大空间数值，单位是字节
  user-disk-mirror-space-quota: 134217728
  # 安全密钥
  secure-key: ""
  # 指定的几个用户的空间对应的容量
  space-max-size: { }
  # 图像文件压缩模块配置
  image-compress-module:
    enable: true
    # 设置 png 调色板模式 默认是 RGB_8 代表 8 位压缩
    palette-png: "RGB_8"
    # 设置 调色板生成器，默认是 X255
    palette-generator: "X255"
    # 设置是否支持透明 默认是 false
    transparent: false
```

### MAIN 启动主类

在这里我们为了演示清晰，没有使用复杂架构，直接使用了一个启动主类，也没有设置其它的控制器。

```java
package com.example.springboot3demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import top.lingyuzhao.diskMirror.conf.Config;
import top.lingyuzhao.diskMirror.core.Adapter;

@SpringBootApplication
// 使用此扫描器对 top.lingyuzhao.diskMirror.starter 包进行扫描，就可以实现自动配置了
@ComponentScan("top.lingyuzhao.diskMirror.starter")
public class SpringBoot3DemoApplication {

    public static void main(String[] args) {
        // 获取到运行器上下文
        final var run = SpringApplication.run(SpringBoot3DemoApplication.class, args);
        // 查看一下 diskMirror 是否被加载进来了
        System.out.println(run.getBean(Adapter.class).getConfig().get(Config.PROTOCOL_PREFIX));
    }

}
```

### 启动测试

当看到下面这样的日志，则代表启动成功了，diskMirror 也被成功的集成了进来！！

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.1)

2024-02-13T23:18:29.914+08:00  INFO 6016 --- [           main] c.e.s.SpringBoot3DemoApplication         : Starting SpringBoot3DemoApplication using Java 17.0.9 with PID 6016 (G:\My_Project\IDEA\SpringBoot3Demo\target\classes started by zhao in G:\My_Project\IDEA\SpringBoot3Demo)
2024-02-13T23:18:29.918+08:00  INFO 6016 --- [           main] c.e.s.SpringBoot3DemoApplication         : No active profile set, falling back to 1 default profile: "default"
2024-02-13T23:18:30.256+08:00  INFO 6016 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2024-02-13T23:18:30.261+08:00  INFO 6016 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-02-13T23:18:30.261+08:00  INFO 6016 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.17]
2024-02-13T23:18:30.287+08:00  INFO 6016 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-02-13T23:18:30.288+08:00  INFO 6016 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 352 ms
2024-02-13T23:18:30.309+08:00  INFO 6016 --- [           main] t.l.d.s.c.DiskMirrorAutoConfiguration    : load properties class top.lingyuzhao.diskMirror.starter.conf.properties.DiskMirrorProperties
2024-02-13T23:18:30.309+08:00  INFO 6016 --- [           main] t.l.d.s.c.DiskMirrorAutoConfiguration    : getAdapterType run = adapterType:LocalFSAdapter
2024-02-13T23:18:30.310+08:00  INFO 6016 --- [           main] t.l.d.s.c.DiskMirrorAutoConfiguration    : diskMirror is ok!!!
             'WWWKXXXXNWWNk,     ,kkd7               KWWb,                     
             ;WWN3.....,lNWWk.                       KWWb,                     
             ;WWNl        XWWk.  :XXk,   oKNNWNKo    KWWb,   dXXO:             
             ;WWNl        3WWX7  7WWO7  0WWo:,:O0d,  KWWb, lNWKb:              
             ;WWNl        :WWNl  7WWO7  0WWO,.       KWWbbXWKb:.               
             ;WWNl        kWW03  7WWO7   lXWWWN0o.   KWWNWWW0;                 
             ;WWNl       lWWNo,  7WWO7     .,7dWWN;  KWWOolWWN7                
             'WWNo,..,'oXWWKo'   7WWO7 .lb:    XWNl. KWWb, .KWWk.              
             ;WWWWWWWWWNKOo:.    7WWO7  oNWX0KWWKb:  KWWb,   bWWX'             
              ,'''''''',,.        ,'',    ,;777;,.    '''.    .''',            
KWWNWK,        ,WWNWWd.   ;33:                                                 
KWWbWWO.       XWXkWWd.   ...    ...  .,,   ...  ,,.      .,,,,        ...  .,,
KWWodWWd      OWNlOWWd.  .WWN7   KWW3OWNWl.:WWOlNWNO:  3KWWXXNWWXo.   ,WWX3XWNK
KWWo.OWWo    oWWb;xWWd.  .WWXl   0WWXkl',, ;WWNKb:,,, XWWkl,..,oWWN'  ,WWNKd7,,
KWWo  XWN7  ;WWx3 dWWd.  .WWXl   0WWO3     ;WWWl,    bWW03      OWWk, ,WWWo'   
KWWo  ,NWK',NW0l  dWWd.  .WWXl   0WWd,     ;WWX3     kWWO:      dWMO: ,WWNl    
KWWo   ;WWkKWXl.  dWWd.  .WWXl   0WWd.     ;WWK7     7WWX7      XWWd; ,WWN3    
KWWo    lWWWNo,   dWWd.  .WWXl   0WWd.     ;WWK7      oWWX3,.,7XWWk3  ,WWN3    
kXXo     dXXd:    oXXb.  .KX0l   xXXb.     'KXO7       .o0XNNNXKkl'   .KXKl    
LocalFSAdapter:1.1.2
2024-02-13T23:18:30.419+08:00  INFO 6016 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
2024-02-13T23:18:30.423+08:00  INFO 6016 --- [           main] c.e.s.SpringBoot3DemoApplication         : Started SpringBoot3DemoApplication in 0.661 seconds (process running for 0.9)
http://localhost:80/

```

### 如何使用 diskMirror 的适配器？

通过 starter 获取到的就是 diskMirror
中的适配器对象，您可以通过适配器对象实现有效的文件操作，具体使用方法请参考 [diskMirror 的主页](https://github.com/BeardedManZhao/DiskMirror.git)

## 更新记录

#### 1.0.4

*发布事件：2024-11-22*

- 对于限定条件进行了优化，且对于异常的信息和日志打印进行了优化，易于排查
- 装载了 1.3.2 版本的 diskMirror

#### 1.0.3

*发布时间：2024-11-02*

- 新增了 `image-compress-module` 模块的配置，其可以直接对接到 `DiskMirror v1.3.0` 中的处理模块管理器中~

#### 1.0.2

*发布时间：2024-02-23*

- 针对配置文件的默认数值进行的设定，这使得您可能不需要在配置文件中将所有配置项目进行设置
- 变更了适配器Bean 在 SpringBoot 中的Bean
  的名字，这有助于避免一些命名冲突问题，变更的情况为 `getAdapter -> top.lingyuzhao.diskMirror.core.Adapter`

----

### 1.0.1

*发布时间：2024-02-14*

- 增加了 enable-feature 配置项，使得您可以通过调整此参数来实现是否要启动 diskMirror 的 starter，减少了需要变更 pom.xml
  的风险。
- 增加了针对 zhao-utils 库的自动依赖，您可以不去声明此库的依赖，diskMirror starter 会自动依赖它。

## 更多

----

- diskMirror starter SpringBoot：https://github.com/BeardedManZhao/diskMirror-spring-boot-starter.git
- diskMirror 后端服务器版本（MVC）：https://github.com/BeardedManZhao/DiskMirrorBackEnd.git
- diskMirror 后端服务器版本（SpringBoot）：https://github.com/BeardedManZhao/diskMirror-backEnd-spring-boot.git
- diskMirror Java API 版本：https://github.com/BeardedManZhao/DiskMirror.git
