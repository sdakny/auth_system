package com.saitolab.common.security;

import com.saitolab.common.constant.JwtConstant;
import com.saitolab.entity.CheckResult;
import com.saitolab.entity.SysUser;
import com.saitolab.service.SysUserService;
import com.saitolab.util.JwtUtils;
import com.saitolab.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    private static final String URL_WHITELIST[] ={
            "/login",
            "/logout",
            "/captcha",
            "/password",
            "/image/**"
    } ;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token=request.getHeader("token");
        System.out.println("请求url:"+request.getRequestURI());
        // 如果token是空或者url在白名单里 则放行 让后面的springsecurity认证过滤器去认证
        if(StringUtil.isEmpty(token)|| new ArrayList<String>(Arrays.asList(URL_WHITELIST)).contains(request.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }
        CheckResult checkResult = JwtUtils.validateJWT(token);
        if(!checkResult.isSuccess()){
            switch (checkResult.getErrCode()){
                case JwtConstant.JWT_ERRCODE_NULL: throw new JwtException("Token不存在");
                case JwtConstant.JWT_ERRCODE_FAIL: throw new JwtException("Token验证不通过");
                case JwtConstant.JWT_ERRCODE_EXPIRE: throw new JwtException("Token过期");
            }
        }

        Claims claims=JwtUtils.parseJWT(token);
        String username=claims.getSubject();
        SysUser sysUser = sysUserService.getByUserName(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,null,myUserDetailService.getUserAuthority(sysUser.getId()));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);
    }
}
