# ![image](https://github.com/BeardedManZhao/DiskMirror/assets/113756063/b8a15b22-5ca0-4552-aab2-7131c63dc727)  diskMirror-starter

DiskMirror 的 starter，通过引入此类，可以直接实现 diskMirror 在 SpringBoot 中的自动配置，接下来我们将使用案例逐步的演示 diskMirror starter 的使用 需要注意的是，此
starter 最适用于 SpringBoot3 版本，且也推荐使用 SpringBoot3 版本的自动配置模块!!!

## 引入 starter

我们可以直接在 maven 中像下面这样配置好 starter，在下面我们引入了一些依赖，如果需要使用第三方文件系统适配器，则可以直接在 maven 中引入，如果您不需要第三方文件系统，可以不引入
根据您的需求来进行选择，如果您不知道需要的库有哪些，可以查阅 [diskMirror 的主页](https://github.com/BeardedManZhao/DiskMirror.git)

```xml

<dependencies>
    <!-- 导入 SpringBoot 项目的 starter 在这里我们使用的示例是 Web starter 您可以根据您的需要修改 starter 类型 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- 导入 DiskMirror 的 starter -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>diskMirror-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>
    <!-- 导入 diskMirror 的 库 这是一个可选操作 diskMirror starter 会自动配置 -->
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>diskMirror</artifactId>
        <version>1.1.2</version>
    </dependency>
    <!-- 导入 zhao utils 的库 这是一个工具类，被 diskMirror 依赖，您可以像上面一样导入 -->
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>zhao-utils</artifactId>
        <version>1.0.20240121</version>
    </dependency>
    <!-- 导入 fastjson2 库 这是一个JSON解析库，被 diskMirror 依赖，您可以像上面一样导入 -->
    <dependency>
        <groupId>com.alibaba.fastjson2</groupId>
        <artifactId>fastjson2</artifactId>
        <version>2.0.25</version>
    </dependency>
</dependencies>
```

## 开发基本的 SpringBoot3 项目

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

----

- diskMirror starter SpringBoot：https://github.com/BeardedManZhao/diskMirror-spring-boot-starter.git
- diskMirror 后端服务器版本：https://github.com/BeardedManZhao/DiskMirrorBackEnd.git
- diskMirror Java API 版本：https://github.com/BeardedManZhao/DiskMirror.git