package com.study.wisdomcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.Grade;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-03
 */
public interface IGradeService extends IService<Grade> {

    /**
     * 年级 条件分页
     * @param gradePage Page<Grade> 对象
     * @param gradeName 年级名称 模糊查询
     * @return Grade分页对象
     */
    IPage<Grade> getGradeByOpr(Page<Grade> gradePage, String gradeName);
}
