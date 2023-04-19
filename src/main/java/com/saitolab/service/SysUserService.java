package com.saitolab.service;

import com.saitolab.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author yiyi
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2023-04-09 20:32:18
*/
public interface SysUserService extends IService<SysUser> {

    SysUser getByUserName(String username);

    String getUserAuthorityInfo(Long userId);

}
