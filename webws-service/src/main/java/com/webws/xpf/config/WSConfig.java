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
