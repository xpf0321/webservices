package com.example.ws.control;

import com.example.ws.GetCountryResponse;
import com.example.ws.config.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private WebClient webClient;

    @RequestMapping("callws")
    public Object callws(){
        GetCountryResponse response= webClient.getCountry("hello");

        System.out.println("getCountry输出："+response.getCountry().getName());
        System.out.println("toString输出："+response.getCountry().toString());


        return response;

    }


}
