##webwc-service程序
  这个程序主要使用学习spring相关技术的demo 
 
 目前使用的技术：
 * springboot  约定大约配置
 * spring-cache 缓存
 * redis  非关系型数据库：与spring-cache技术结合,可以先不添加该jar包
 * thymeleaf   web模板
 * spring-ws  spring提供的webService技术
 * mybatis   数据库对象关系映射框架，用于操作数据信息
 * lombok    用于减少代码，提高开发效率
 
 
###  创建项目， 先将项目运行起来
   * 出现异常，是没有配置数据库连接信息：因为导入的mysql包，项目启动时,会检测mysql配置信息，
  ```
Consider the following:
	If you want an embedded database (H2, HSQL or Derby), please put it on the classpath.
	If you have database settings to be loaded from a particular profile you may need to activate it (no profiles are currently active).
  ```

   * 将application.properties修改为application.yml,配置数据库连接信息
   ```
    # 服务端口
    server:
      port: 8999
    # 数据库连接信息
    spring:
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        password: 123123
        username: root
        url: jdbc:mysql://localhost:3306/world
        type: com.alibaba.druid.pool.DruidDataSource

   ```
   * 项目启动ok，添加HomeController.java
   ```
    @RestController
    public class HomeController {
    
        @RequestMapping(name = "/index")
        public String index(){
            System.out.println("12314152534");
            return "index";
        }
    
    }

   ```
   * 访问localhost:8999/index,检测结果
 
 
 ### 使用mybatis-generator插件自动生成 2（dao 、entity 、mapping）
   * 在pom.xml添加插件
   ```androiddatabinding
            <plugin>
                 <groupId>org.mybatis.generator</groupId>
                 <artifactId>mybatis-generator-maven-plugin</artifactId>
                 <version>1.3.5</version>
                 <configuration>
                     <configurationFile>${basedir}/src/main/resources/generator/generatorConfig.xml</configurationFile>
                     <overwrite>true</overwrite>
                     <verbose>true</verbose>
                 </configuration>
                 <dependencies>
                     <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
                     <dependency>
                         <groupId>mysql</groupId>
                         <artifactId>mysql-connector-java</artifactId>
                         <version>5.1.47</version>
                     </dependency>
 
                     <dependency>
                         <groupId>tk.mybatis</groupId>
                         <artifactId>mapper</artifactId>
                         <version>3.4.0</version>
                     </dependency>
                 </dependencies>
             </plugin>  
   
   ```
   * 在resources文件下新建一个文件夹和文件generator/generatorConfig.xml,需要配置项目中的包的位置
    [mybatis自动生成 ](https://blog.csdn.net/weixin_37656125/article/details/84536510)
 
   * 上面两个步骤完成后，启动plugin,如果配置没有问题，就OK了

### 使用lombok 减少代码量
```androiddatabinding
package com.webws.xpf.model;

import lombok.Getter;
import lombok.Setter;
/**
 * 使用 @Setter，@Getter可以替代get set方法，当然lombok里面还有很多好的东西
 */
@Setter
@Getter
public class City {
    private Integer id;

    private String name;

    private String countrycode;

    private String district;

    private Integer population;
}
```


### mybatis 数据操作
   * 通过mybatis的自动生成插件后,配置application.yml的mybatis信息
   ```$xslt
    mybatis:
      type-aliases-package: com.webws.xpf.model
      mapper-locations: mapping/*.xml
   ```
   * 配置Mapper文件
   ```$xslt
       @Repository
       public interface UserInfoMapper {
           int deleteByPrimaryKey(Long uid);
       
           int insert(UserInfo record);
       
           int insertSelective(UserInfo record);
       
           UserInfo selectByPrimaryKey(Long uid);
       
           int updateByPrimaryKeySelective(UserInfo record);
       
           int updateByPrimaryKey(UserInfo record);
       }
   ```  
   
### 配置日记文件 #异常
   
    
 
 
 
  