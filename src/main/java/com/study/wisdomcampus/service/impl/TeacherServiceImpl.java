package com.study.wisdomcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.wisdomcampus.entity.LoginFrom;
import com.study.wisdomcampus.entity.Teacher;
import com.study.wisdomcampus.mapper.TeacherMapper;
import com.study.wisdomcampus.service.ITeacherService;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Override
    public Teacher login(LoginFrom loginFrom) {
        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper.eq(Teacher::getTno, loginFrom.getUsername())
                .eq(Teacher::getPassword, MD5.encrypt(loginFrom.getPassword()));
        return getOne(teacherLambdaQueryWrapper);
    }

    @Override
    public IPage<Teacher> getClazzsByOpr(Page<Teacher> teacherPage, String teacherName, String clazzName) {
        LambdaQueryWrapper<Teacher> teacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teacherLambdaQueryWrapper
                .eq(StringUtils.hasLength(clazzName), Teacher::getClazzName, clazzName)
                .like(StringUtils.hasLength(teacherName), Teacher::getName, teacherName)
                .orderByAsc(Teacher::getId)
                .orderByDesc(Teacher::getName);
        return page(teacherPage, teacherLambdaQueryWrapper);
    }
}
