package com.webws.xpf.config;

import com.webws.xpf.ws.GetCountryRequest;
import com.webws.xpf.ws.GetCountryResponse;
import com.webws.xpf.ws.GetUserRequest;
import com.webws.xpf.ws.GetUserResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;


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

