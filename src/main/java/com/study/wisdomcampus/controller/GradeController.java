package com.study.wisdomcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.Grade;
import com.study.wisdomcampus.service.IGradeService;
import com.study.wisdomcampus.util.Result;
import org.springframework.lang.Nullable;
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
@RequestMapping("/sms/gradeController")
public class GradeController {

    private final IGradeService gradeService;

    public GradeController(IGradeService gradeService) {
        this.gradeService = gradeService;
    }

    /**
     * 返回所有年级
     * @return 带有年级对象的Result
     */
    @GetMapping("/getGrades")
    public Result getGrades(){
        //查询所有年级
        List<Grade> list = gradeService.list();
        //返回数据
        return Result.ok(list);
    }

    /**
     * 批量删除 根据传入id集合批量删除
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> ids) {

        boolean b = gradeService.removeBatchByIds(ids);
        if (b) {
            return Result.ok();
        }
        return Result.fail();

    }

    /**
     * 添加或修改年级
     * @param grade 年级对象
     * @return 操作结果
     */
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade) {
        //接收参数
        //完成添加和修改操作
        boolean b = gradeService.saveOrUpdate(grade);

        if (b) {
            return Result.ok();
        }
        return Result.fail();

    }

    /**
     * 查询年级 带分页
     * @param pageNo 当前页码
     * @param pageSize 每页显示的记录数
     * @param gradeName 年级名字 模糊查询
     * @return 返回带年级分页数据的Result对象 或 失败
     */
    @GetMapping("getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            @RequestParam("gradeName") @Nullable String gradeName) {

        //分页 带条件查询
        Page<Grade> gradePage = new Page<>(pageNo, pageSize);

        //
        IPage<Grade> pageRs = gradeService.getGradeByOpr(gradePage, gradeName);

        if (pageRs != null) {
            //封装Result对象并返回
            return Result.ok(pageRs);
        }
        return Result.fail();
    }

}
