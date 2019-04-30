### 集成spring-ws 客户端 
  * 添加jar包
```androiddatabinding
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
  * 在resources下新建一个包schemas添加 wsdl文件
```androiddatabinding
<wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:sch="http://xpf.webws.com/ws/user" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tns="http://xpf.webws.com/ws/user" targetNamespace="http://xpf.webws.com/ws/user">
    <wsdl:types>
        <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" elementFormDefault="qualified" targetNamespace="http://xpf.webws.com/ws/user">
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
    </wsdl:types>
    <wsdl:message name="getUserResponse">
        <wsdl:part element="tns:getUserResponse" name="getUserResponse"> </wsdl:part>
    </wsdl:message>
    <wsdl:message name="getUserRequest">
        <wsdl:part element="tns:getUserRequest" name="getUserRequest"> </wsdl:part>
    </wsdl:message>
    <wsdl:portType name="userPort">
        <wsdl:operation name="getUser">
            <wsdl:input message="tns:getUserRequest" name="getUserRequest"> </wsdl:input>
            <wsdl:output message="tns:getUserResponse" name="getUserResponse"> </wsdl:output>
        </wsdl:operation>
    </wsdl:portType>
    <wsdl:binding name="userPortSoap11" type="tns:userPort">
        <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
        <wsdl:operation name="getUser">
            <soap:operation soapAction=""/>
            <wsdl:input name="getUserRequest">
                <soap:body use="literal"/>
            </wsdl:input>
            <wsdl:output name="getUserResponse">
                <soap:body use="literal"/>
            </wsdl:output>
        </wsdl:operation>
    </wsdl:binding>
    <wsdl:service name="userPortService">
        <wsdl:port binding="tns:userPortSoap11" name="userPortSoap11">
            <soap:address/>
        </wsdl:port>
    </wsdl:service>
</wsdl:definitions>
```
  * 添加根据wsdl文件自动生成java文件的plugin，启动插件
```androiddatabinding
            <plugin>
                <groupId>org.jvnet.jaxb2.maven2</groupId>
                <artifactId>maven-jaxb2-plugin</artifactId>
                <version>0.12.3</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <schemaLanguage>WSDL</schemaLanguage>
                    <generatePackage>com.webws.xpf.ws.user</generatePackage>
                    <generateDirectory>${basedir}/src/main/java</generateDirectory>
                    <schemas>
                        <schema>
                            <fileset>
                                <!-- Defaults to schemaDirectory. -->
                                <directory>${basedir}/src/main/resources/schemas</directory>
                                <!-- Defaults to schemaIncludes. -->
                                <includes>
                                    <include>*.wsdl</include>
                                </includes>
                                <!-- Defaults to schemaIncludes -->
                                <!--<excludes>-->
                                <!--<exclude>*.xs</exclude>-->
                                <!--</excludes>-->
                            </fileset>
                            <!--<url>http://localhost:8080/ws/countries.wsdl</url>-->
                        </schema>
                    </schemas>
                </configuration>
            </plugin>
```
 * 根据插件生成Java文件后
```androiddatabinding
   ws包
        country包
                Country-*.java
        user 包
                User-*.java
```
  * 添加客户端访问  WebClient
```androiddatabinding
public class WebClient extends WebServiceGatewaySupport {

    public GetCountryResponse getCountry(String name){

        GetCountryRequest request = new GetCountryRequest();
        request.setName(name);
        GetCountryResponse response =(GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(
                "http://localhost:8999/ws/countries.wsdl",request

        );
        return response;
    }

    public GetUserResponse getUser(){
        GetUserRequest request=new GetUserRequest();
        GetUserResponse response=(GetUserResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8999/ws/user.wsdl",request);
        return response;
    }
}

```
  * 配置 ws config
```androiddatabinding
@Configuration
public class WSClientConfig {


    @Bean
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths("com.webws.xpf.ws.user","com.webws.xpf.ws.country");
        return marshaller;
    }

    @Bean
    public WebClient webClient(Jaxb2Marshaller marshaller){

        WebClient client = new WebClient();
        //client.set
        //client.setDefaultUri("http://localhost:8999/ws/countries.wsdl");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
```
  * 添加controller 
```androiddatabinding
 @Autowired
    private WebClient webClient;

    @RequestMapping("callCountryWs")
    public Object callCountryWs(){
        GetCountryResponse response= webClient.getCountry("hello");

        System.out.println("getCountry输出："+response.getCountry().getName());
        System.out.println("toString输出："+response.getCountry().toString());


        return response;

    }
    @RequestMapping("callUserWs")
    public Object callUserWs(){
        GetUserResponse response = webClient.getUser();
        System.out.println(response.getUser().getName());
        System.out.println(response.getUser().getRole());

        return response;

    }
```
启动项目，在确定ws服务端启动的情况下，访问上方url:localhost:8080/callUserWs


### 集成Mybatis 的扩展
   * 添加jar包
```androiddatabinding
  <!--mybatis start-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.1</version>
        </dependency>
        <!--mybatis扩展-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>1.1.1</version>
        </dependency>
  <!--mybatis end-->
```
  * 添加MyMapper.java
```androiddatabinding
package com.webws.xpf.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 Mapper接口：基本的增删查改
 MySqlMapper接口：针对MySql的额外补充接口，支持批量插入
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}

```
   * 添加mapper层
```androiddatabinding
package com.webws.xpf.mapper;

import com.webws.xpf.model.User;
import com.webws.xpf.utils.MyMapper;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends MyMapper<User> {

    @Select("select * from user where name = #{username}")
    User findByUserName(String userName);
}
```
  * 在application.java 中添加@MapperScan
```androiddatabinding
@SpringBootApplication
@MapperScan("com.webws.xpf.mapper")
public class WebwsServicesClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebwsServicesClientApplication.class, args);
    }

}
```
  * 添加controller
```androiddatabinding
    @RequestMapping("index")
    public Object index(){

        User user=userMapper.selectByPrimaryKey(1l);
        System.out.println(user.toString());
        return user;

    }
```



### 集成spring缓存
   * 添加jar包
   ```
    <!--缓存 start-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
    <!--缓存 end-->
   ```
   
   * 给service中的数据访问方法，添加  @Cacheable
```androiddatabinding
    @Override
    @Cacheable(value = "user")
    public User findByUserName(String username) {
        System.out.println("---------------进入数据库----------------");
        return userInfoMapper.findByUserName(username);
    }

```
   * 开启缓存
```androiddatabinding
@SpringBootApplication
@MapperScan("com.webws.xpf.mapper")
@EnableCaching
public class WebwsServicesClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebwsServicesClientApplication.class, args);
    }

}
```
### 开启自定义缓存（redis）
  * 添加 jar包
```androiddatabinding
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

  * 添加redis配置到 application.yml 
```androiddatabinding
 redis:
    database: 0
    host: localhost
    port: 6379
    password:
    jedis:
      pool:
        max-wait: -1ms
        max-active: 8
        max-idle: 8
        min-idle: 0
    timeout: 20000
```

  * 添加RedisCacheConfig
```androiddatabinding
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Bean(name="redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        System.out.print("-------------------进入redisTemplate--------------------------");
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Object> serializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
        //RedisSerializer<String> redisSerializer=new StringRedisSerializer();        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        System.out.print("-------------------进入cacheManager--------------------------");
        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        // 设置缓存的默认过期时间，也是使用Duration设置
        config = config.entryTtl(Duration.ofSeconds(5L))
                .disableCachingNullValues();     // 不缓存空值

        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("my-redis-cache1", config);
        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(12L)));

        // 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }
}
```
  * 配置缓存
```androiddatabinding
@Service
public class UserServiceImpl extends BaseService<Userinfo> implements UserService {

    @Autowired
    UserinfoMapper userInfoMapper;

    @Override
    @Cacheable(value = "my-redis-cache1")
    public Userinfo findByUserName(String username) {
        System.out.println("---------------进入数据库----------------");
        return userInfoMapper.findByName(username);
    }
}
```
   * 在controller设置
```androiddatabinding
    @RequestMapping("index")
    public Object index(){

        Userinfo user=userinfoMapper.selectByPrimaryKey(1l);
        System.out.println(user.toString());
        System.out.println("----------------------获取数据 看看是否是缓存中的数据-----------------------");
        Userinfo user1=userService.findByUserName("test");
        return user1;

    }
```
    
    启动项目，测试

    
