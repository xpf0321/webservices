package com.webws.xpf.controller;

import com.webws.xpf.config.WebClient;
import com.webws.xpf.ws.country.GetCountryResponse;
import com.webws.xpf.ws.user.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

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


}
