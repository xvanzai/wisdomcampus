package com.study.wisdomcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.Clazz;
import com.study.wisdomcampus.service.IClazzService;
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
@RequestMapping("/sms/clazzController")
public class ClazzController {


    private final IClazzService clazzService;

    public ClazzController(IClazzService clazzService) {
        this.clazzService = clazzService;
    }

    /**
     * 返回所有班级
     *
     * @return 带有班级对象的Result
     */
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        //查询所有年级
        List<Clazz> list = clazzService.list();
        //返回数据
        return Result.ok(list);
    }


    /**
     * 批量删除 根据传入id集合批量删除
     *
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids) {

        boolean b = clazzService.removeBatchByIds(ids);
        if (b) {
            return Result.ok();
        }
        return Result.fail();

    }

    /**
     * 添加或修改clazz 有id修改 无id添加
     *
     * @param clazz Clazz对象
     * @return 操作结果
     */
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz) {

        //接收参数
        //完成添加和修改操作
        boolean b = clazzService.saveOrUpdate(clazz);
        if (b) {
            return Result.ok();
        }
        return Result.fail();

    }


    /**
     * 根据条件查询班级并分页
     *
     * @param pageNo    当前页码
     * @param pageSize  单页显示记录条数
     * @param clazzName 模糊查询 班级名称
     * @param gradeName 匹配 年级名称
     * @return 班级分页对象
     */
    @GetMapping("getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            @RequestParam("name") @Nullable String clazzName,
            @RequestParam("gradeName") @Nullable String gradeName) {

        //分页 带条件查询
        Page<Clazz> clazzPage = new Page<>(pageNo, pageSize);

        IPage<Clazz> pageRs = clazzService.getClazzsByOpr(clazzPage, clazzName, gradeName);

        if (pageRs != null) {
            //封装Result对象并返回
            return Result.ok(pageRs);
        }
        return Result.fail();
    }

}
