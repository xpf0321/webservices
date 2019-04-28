package com.webws.xpf.endpoint;

import com.webws.xpf.ws.country.Country;
import com.webws.xpf.ws.country.Currency;
import com.webws.xpf.ws.country.GetCountryRequest;
import com.webws.xpf.ws.country.GetCountryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://xpf.webws.com/ws/country";
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        System.out.println("---------------------进入getCountry--------------------");
        GetCountryResponse response = new GetCountryResponse();
        Country poland = new Country();
        poland.setName("Poland-" + request.getName());
        poland.setCapital("Warsaw");
        poland.setCurrency(Currency.PLN);
        poland.setPopulation(38186860);
        response.setCountry(poland);
        return response;
    }
}

