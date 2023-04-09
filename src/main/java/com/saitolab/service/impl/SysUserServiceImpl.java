package com.saitolab.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saitolab.entity.SysUser;
import com.saitolab.service.SysUserService;
import com.saitolab.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Willi
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-04-09 20:32:18
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




