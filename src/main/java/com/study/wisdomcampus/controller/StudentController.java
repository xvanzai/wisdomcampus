package com.study.wisdomcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.Student;
import com.study.wisdomcampus.service.IStudentService;
import com.study.wisdomcampus.util.MD5;
import com.study.wisdomcampus.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    private final IStudentService studentService;

    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }


    /**
     * 批量删除 根据传入id集合批量删除
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> ids) {

        boolean b = studentService.removeBatchByIds(ids);
        if (b) {
            return Result.ok();
        }
        return Result.fail();

    }


    /**
     * 添加或修改学生
     *
     * @param student 学生对象
     * @return 操作结果
     */
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student) {

        Integer id = student.getId();
        if (id == null || 0 == id) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        //接收参数
        //完成添加和修改操作
        boolean b = studentService.saveOrUpdate(student);

        if (b) {
            return Result.ok();
        }
        return Result.fail();
    }


    /**
     * 分页查询 学生
     *
     * @param pageNo   当前页
     * @param pageSize 页大小
     * @param student  封装传入的查询条件
     * @return 带分页数据的结果
     */
    @GetMapping("getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@PathVariable("pageNo") Integer pageNo,
                                  @PathVariable("pageSize") Integer pageSize,
                                  Student student) {

        //分页信息封装Page对象
        Page<Student> studentPage = new Page<>(pageNo, pageSize);
        //进行查询
        IPage<Student> pageRs = studentService.getStudentByOpr(studentPage, student);

        if (pageRs != null) {
            //封装Result对象并返回
            return Result.ok(pageRs);
        }
        return Result.fail();
    }

}
