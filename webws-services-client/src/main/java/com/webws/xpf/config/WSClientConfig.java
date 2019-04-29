package com.webws.xpf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

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
