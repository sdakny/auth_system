package com.saitolab.common.security;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.saitolab.entity.R;
import com.saitolab.entity.SysMenu;
import com.saitolab.entity.SysRole;
import com.saitolab.entity.SysUser;
import com.saitolab.service.SysMenuService;
import com.saitolab.service.SysRoleService;
import com.saitolab.service.SysUserService;
import com.saitolab.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();

        String username=authentication.getName(); // 获取用户名
        // 更新用户最后登录记录
        sysUserService.update(new UpdateWrapper<SysUser>().set("login_date",new Date()).eq("username",username));

        SysUser currentUser = sysUserService.getByUserName(username);

        String token = JwtUtils.genJwtToken(username);

        // 根据用户id获取所有的角色
        List<SysRole> roleList = sysRoleService.list(new QueryWrapper<SysRole>().inSql("id", "SELECT role_id FROM sys_user_role WHERE user_id=" + currentUser.getId()));

        // 遍历角色，获取所有菜单权限
        Set<SysMenu> menuSet=new HashSet<SysMenu>();
        for(SysRole sysRole:roleList){
            List<SysMenu> sysMenuList = sysMenuService.list(new QueryWrapper<SysMenu>().inSql("id", "SELECT menu_id FROM sys_role_menu WHERE role_id=" + sysRole.getId()));
            for(SysMenu sysMenu:sysMenuList){
                menuSet.add(sysMenu);
            }
        }

        List<SysMenu> sysMenuList=new ArrayList<>(menuSet);

        // 排序
        sysMenuList.sort(Comparator.comparing(SysMenu::getOrderNum));

        List<SysMenu> menuList=sysMenuService.buildTreeMenu(sysMenuList);


        outputStream.write(JSONUtil.toJsonStr(R.ok("登录成功").put("authorization",token).put("currentUser",currentUser).put("menuList",menuList)).getBytes());
        outputStream.flush();
        outputStream.close();

    }
}