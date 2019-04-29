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
   
### 配置日记文件 #异常：logging 包冲突，添加jar文件时，包里可能包含logging
   * 导包
   ```$xslt
    <!---导日志包之前，先将项目中自带的logging 移除-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>

   ```  
   * 添加日志文件  log4j2.xml
   
   ```angular2html
<?xml version="1.0" encoding="UTF-8"?>

<Configuration stauts="INFO">

    <Appenders>
        <!--添加一个控制台追加器-->
        <Console name="Console" target="SYSTEM_OUT" follow="true">
             <PatternLayout>
                 <pattern>[%-5p] xpf %d %c - %m%n</pattern>
             </PatternLayout>
        </Console>

        <File name="File" fileName="jzh.log">
            <PatternLayout>
                <pattern>
                    [%-5p] xpf %d %c - %m%n
                </pattern>
            </PatternLayout>
            <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
        </File>
        <File name="dataBaseFile" fileName="dataBase.log">
            <PatternLayout>
                <pattern>
                    [%-5p] xpf %d %c - %m%n
                </pattern>
            </PatternLayout>
            <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
        </File>
    </Appenders>
    <Loggers>
        <Root level = "debug">
            <AppenderRef ref="Console"/>
        </Root>
        <Logger name="org.hibernate" level="info" additivity="false">
            <AppenderRef ref="dataBaseFile"/>
        </Logger>
        <Logger name="org.mybatis" level="info" additivity="false">
            <AppenderRef ref="dataBaseFile"/>
        </Logger>
        <Logger name="org.springframework" level="info" additivity="false">
            <AppenderRef ref="File"/>
        </Logger>
    </Loggers>

</Configuration>


```
 ### 配置spring-ws 就是spring提供的webservice
   * 添加jar包
   ```$xslt
 <!--spring webservices start-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-ws</artifactId>
            <version>1.4.7.RELEASE</version>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>wsdl4j</groupId>
            <artifactId>wsdl4j</artifactId>
        </dependency>
 <!--spring webservices end-->
```
 * 编写.xsd文件，根据需求扩展
 ```$xslt
<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tns="http://xpf.webws.com/ws/user"
           targetNamespace="http://xpf.webws.com/ws/user" elementFormDefault="qualified">
    <xs:element name="getUserRequest">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="name" type="xs:string"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
    <xs:element name="getUserResponse">
        <xs:complexType>
            <xs:sequence>
                <xs:element name="User" type="tns:User"/>
            </xs:sequence>
        </xs:complexType>
    </xs:element>
    <xs:complexType name="User">
        <xs:sequence>
            <xs:element name="name" type="xs:string"/>
            <xs:element name="role" type="xs:string"/>
        </xs:sequence>
    </xs:complexType>
</xs:schema>
```
 * 使用plugin插件生成xsd的相关java文件
 ```$xslt
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>jaxb2-maven-plugin</artifactId>
                <version>1.6</version>
                <executions>
                    <execution>
                        <id>xjc</id>
                        <goals>
                            <goal>xjc</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <schemaDirectory>${project.basedir}/src/main/resources//schema</schemaDirectory>
                    <outputDirectory>${project.basedir}/src/main/java</outputDirectory>
                    <clearOutputDir>false</clearOutputDir>
                </configuration>
            </plugin>   
```
  打开maven projects，点击plugin:jaxb2:xjc生成文件
  
### 配置WSConfig.java
```$xslt
package com.webws.xpf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WSConfig extends WsConfigurerAdapter {

    private Logger logger= LoggerFactory.getLogger(WSConfig.class);
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        logger.info("---------------------进入 WebServiceConfig  messageDispatcherServlet-----------------------------");
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }
    @Bean(name = "countries")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        logger.info("---------------------进入 WebServiceConfig countries -----------------------------");

        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountriesPort");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }


    @Bean
    public XsdSchema countriesSchema() {
        logger.info("---------------------进入 WebServiceConfig countriesSchema -----------------------------");

        return new SimpleXsdSchema(new ClassPathResource("schema/countries.xsd"));
    }


    @Bean(name = "user")
    public DefaultWsdl11Definition defaultWsdl11Definition1(XsdSchema userSchema) {
        logger.info("---------------------进入 WebServiceConfig userData  -----------------------------");

        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("userPort");
        wsdl11Definition.setSchema(userSchema);
        return wsdl11Definition;
    }


    @Bean
    public XsdSchema userSchema() {
        logger.info("---------------------进入 WebServiceConfig UserDataSchema -----------------------------");

        return new SimpleXsdSchema(new ClassPathResource("schema/user.xsd"));
    }


}

```
  * 添加Endpoint文件
```$xslt
package com.webws.xpf.endpoint;

import com.webws.xpf.mapper.SysRoleMapper;
import com.webws.xpf.mapper.UserInfoMapper;
import com.webws.xpf.model.SysRole;
import com.webws.xpf.model.UserInfo;
import com.webws.xpf.ws.user.GetUserRequest;
import com.webws.xpf.ws.user.GetUserResponse;
import com.webws.xpf.ws.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserDataEndpoint {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;
    private static final String NAMESPACE_URI = "http://xpf.webws.com/ws/user";
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUserData(@RequestPayload GetUserRequest request) {
        System.out.println("---------------------getUserData--------------------");
        GetUserResponse response = new GetUserResponse();
        User userdata=new User();
        UserInfo userInfo=userInfoMapper.selectByPrimaryKey(1l);
        SysRole role=  sysRoleMapper.selectByPrimaryKey(1l);
        userdata.setName(userInfo.getName());
        userdata.setRole(role.getRole());
        response.setUser(userdata);
        return response;
    }

}

```

启动项目，访问：http://localhost:8999/ws/user.wsdl

到了这一步，webserivces的服务端是好了