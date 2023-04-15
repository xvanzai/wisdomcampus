package com.study.wisdomcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.wisdomcampus.entity.Admin;
import com.study.wisdomcampus.entity.LoginFrom;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-03
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 管理员登录
     * @param loginFrom 登录表单
     * @return 管理员对象
     */
    Admin login(LoginFrom loginFrom);

    /**
     * 管理员 分页查询
     * @param adminPage Page<Admin>
     * @param adminName 管理员姓名 模糊查询条件
     * @return IPage<Admin>
     */
    IPage<Admin> getGradeByOpr(Page<Admin> adminPage, String adminName);
}
