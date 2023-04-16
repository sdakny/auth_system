package com.saitolab.controller;

import com.saitolab.entity.R;
import com.saitolab.entity.SysUser;
import com.saitolab.service.SysUserService;
import com.saitolab.util.JwtUtils;
import com.saitolab.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/user/list")
    public R userList(@RequestHeader(required = false)String token){
        if(StringUtil.isNotEmpty(token)){
            Map<String,Object> resultMap = new HashMap<>();
            List<SysUser> userList = sysUserService.list();
            resultMap.put("userList", userList);
            return R.ok(resultMap);
        }else{
            return R.error(401, "没有权限访问");
        }

    }

    @RequestMapping("/login")
    public R login(){
        String token = JwtUtils.genJwtToken("test1");
        return R.ok().put("token",token);
    }
}
