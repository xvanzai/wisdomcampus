package com.study.wisdomcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.wisdomcampus.entity.Admin;
import com.study.wisdomcampus.entity.LoginFrom;
import com.study.wisdomcampus.mapper.AdminMapper;
import com.study.wisdomcampus.service.IAdminService;
import com.study.wisdomcampus.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-03
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Override
    public Admin login(LoginFrom loginFrom) {
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(Admin::getName, loginFrom.getUsername())
                .eq(Admin::getPassword, MD5.encrypt(loginFrom.getPassword()));
        return getOne(adminLambdaQueryWrapper);
    }

    @Override
    public IPage<Admin> getGradeByOpr(Page<Admin> adminPage, String adminName) {
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.like(StringUtils.hasLength(adminName), Admin::getName, adminName).
                orderByAsc(Admin::getId)
                .orderByDesc(Admin::getName);
        return page(adminPage,adminLambdaQueryWrapper);
    }
}
