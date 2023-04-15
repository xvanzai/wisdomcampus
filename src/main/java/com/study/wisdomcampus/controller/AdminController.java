package com.study.wisdomcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.Admin;
import com.study.wisdomcampus.service.IAdminService;
import com.study.wisdomcampus.util.MD5;
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
@RequestMapping("/sms/adminController")
public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }


    /**
     * 批量删除 根据传入id集合批量删除
     * @param ids id集合
     * @return 删除结果
     */
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids) {

        boolean b = adminService.removeBatchByIds(ids);
        if (b) {
            return Result.ok();
        }
        return Result.fail();

    }

    /**
     * 添加或修改管理员
     * @param admin 管理员对象
     * @return 操作结果
     */
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin) {

        Integer id = admin.getId();
        if (id == null || 0 == id) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        //接收参数
        //完成添加和修改操作
        boolean b = adminService.saveOrUpdate(admin);

        if (b) {
            return Result.ok();
        }
        return Result.fail();

    }



    @GetMapping("getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              @RequestParam @Nullable String adminName) {

        //分页 带条件查询
        Page<Admin> adminPage = new Page<>(pageNo, pageSize);

        //
        IPage<Admin> pageRs = adminService.getGradeByOpr(adminPage, adminName);

        if (pageRs != null) {
            //封装Result对象并返回
            return Result.ok(pageRs);
        }
        return Result.fail();
    }

}


