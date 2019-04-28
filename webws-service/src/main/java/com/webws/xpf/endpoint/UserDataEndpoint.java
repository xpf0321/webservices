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
