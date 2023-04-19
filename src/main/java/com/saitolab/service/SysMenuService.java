package com.saitolab.service;

import com.saitolab.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author yiyi
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2023-04-17 17:58:11
*/
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> buildTreeMenu(List<SysMenu> sysMenuList);
}
