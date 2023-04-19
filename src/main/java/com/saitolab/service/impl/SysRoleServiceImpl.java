package com.saitolab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saitolab.entity.SysRole;
import com.saitolab.service.SysRoleService;
import com.saitolab.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author yiyi
* @description 针对表【sys_role】的数据库操作Service实现
* @createDate 2023-04-17 17:58:11
*/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
implements SysRoleService{

}
