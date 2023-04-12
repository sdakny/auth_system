package com.saitolab.common.security;

import com.saitolab.common.exception.UserCountLockException;
import com.saitolab.entity.SysUser;
import com.saitolab.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser=sysUserService.getByUserName(username);
        if(sysUser==null){
            throw new UsernameNotFoundException("Username does not exist!");
        }else if("1".equals(sysUser.getStatus())){
            throw new UserCountLockException("This user account has been blocked, contact the administrator for details!");
        }
        return new User(sysUser.getUsername(),sysUser.getPassword(),getUserAuthority(sysUser.getId()));
    }

    public List<GrantedAuthority> getUserAuthority(Long userId){
        return new ArrayList<>();
    }
}
