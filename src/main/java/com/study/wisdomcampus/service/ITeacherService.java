package com.study.wisdomcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.LoginFrom;
import com.study.wisdomcampus.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-03
 */
public interface ITeacherService extends IService<Teacher> {

    /**
     * 教师登录
     * @param loginFrom 登录表单
     * @return 教师对象
     */
    Teacher login(LoginFrom loginFrom);

    /**
     * 教师分页查询
     *
     * @param teacherPage Page<Teacher>对象
     * @param teacherName 教师姓名 模糊匹配
     * @param clazzName 班级名称
     * @return IPage<Teacher>对象
     */
    IPage<Teacher> getClazzsByOpr(Page<Teacher> teacherPage, String teacherName, String clazzName);
}
