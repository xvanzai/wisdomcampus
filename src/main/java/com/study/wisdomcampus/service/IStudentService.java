package com.study.wisdomcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.LoginFrom;
import com.study.wisdomcampus.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-03
 */
public interface IStudentService extends IService<Student> {

    /**
     * 学生登录
     * @param loginFrom 登录表单
     * @return 学生对象
     */
    Student login(LoginFrom loginFrom);

    /**
     * 学生 分页查询
     * @param studentPage Page<Student>
     * @param student Student对象 条件
     * @return IPage<Student>
     */
    IPage<Student> getStudentByOpr(Page<Student> studentPage, Student student);
}
