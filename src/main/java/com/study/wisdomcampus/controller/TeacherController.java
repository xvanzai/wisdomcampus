package com.study.wisdomcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.Teacher;
import com.study.wisdomcampus.service.ITeacherService;
import com.study.wisdomcampus.util.MD5;
import com.study.wisdomcampus.util.Result;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    private final ITeacherService teacherService;

    public TeacherController(ITeacherService teacherService) {
        this.teacherService = teacherService;
    }


    /**
     * 批量删除 根据传入id集合批量删除
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids) {

        boolean b = teacherService.removeBatchByIds(ids);
        if (b) {
            return Result.ok();
        }
        return Result.fail();

    }


    /**
     * 添加或修改 教师
     *
     * @param teacher 教师对象
     * @return 操作结果
     */
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {

        Integer id = teacher.getId();
        if (id == null || 0 == id) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        //接收参数
        //完成添加和修改操作
        boolean b = teacherService.saveOrUpdate(teacher);

        if (b) {
            return Result.ok();
        }
        return Result.fail();
    }


    /**
     * 分页查询教师
     * @param pageNo 当前页码
     * @param pageSize 每页显示记录条数
     * @param teacherName 教师姓名 模糊查询
     * @param clazzName 班级名称
     * @return 带教师分页数据的Result对象
     */
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers( @PathVariable("pageNo") Integer pageNo,
                               @PathVariable("pageSize") Integer pageSize,
                               @RequestParam("name") @Nullable String teacherName,
                               @RequestParam("clazzName") @Nullable String clazzName){

        //分页
        Page<Teacher> teacherPage = new Page<>(pageNo,pageSize);

        //根据条件查询分页
        IPage<Teacher> pageRs = teacherService.getClazzsByOpr(teacherPage,teacherName,clazzName);

        if (pageRs != null) {
            //封装Result对象并返回
            return Result.ok(pageRs);
        }
        return Result.fail();
    }

}
