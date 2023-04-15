package com.study.wisdomcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.wisdomcampus.entity.LoginFrom;
import com.study.wisdomcampus.entity.Student;
import com.study.wisdomcampus.mapper.StudentMapper;
import com.study.wisdomcampus.service.IStudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Override
    public Student login(LoginFrom loginFrom) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getSno, loginFrom.getUsername())
                .eq(Student::getPassword, MD5.encrypt(loginFrom.getPassword()));
        return getOne(studentLambdaQueryWrapper);
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> studentPage, Student student) {
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(StringUtils.hasLength(student.getClazzName()), Student::getClazzName, student.getClazzName())
                .like(StringUtils.hasLength(student.getName()), Student::getName, student.getName())
                .orderByAsc(Student::getId)
                .orderByDesc(Student::getName);
        return page(studentPage, studentLambdaQueryWrapper);
    }
}
