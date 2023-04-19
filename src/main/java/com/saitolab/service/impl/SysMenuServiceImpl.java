package com.saitolab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saitolab.entity.SysMenu;
import com.saitolab.service.SysMenuService;
import com.saitolab.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author yiyi
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2023-04-17 17:58:11
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
implements SysMenuService{

}
