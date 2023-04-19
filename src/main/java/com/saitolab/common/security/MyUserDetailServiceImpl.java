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

    /**
     * 获取用户权限信息 包括角色 菜单权限信息
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {
        // 格式ROLE_admin,ROLE_common,system:user:resetPwd,system:role:delete,system:user:list,system:menu:query,system:menu:list,system:menu:add,system:user:delete,system:role:list,system:role:menu,system:user:edit,system:user:query,system:role:edit,system:user:add,system:user:role,system:menu:delete,system:role:add,system:role:query,system:menu:edit
        String authority=sysUserService.getUserAuthorityInfo(userId);
        System.out.println("authority="+authority);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
