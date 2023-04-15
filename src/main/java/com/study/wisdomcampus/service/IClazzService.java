package com.study.wisdomcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.wisdomcampus.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-04-03
 */
public interface IClazzService extends IService<Clazz> {

    /**
     * 班级 条件分页
     * @param clazzPage Page<Clazz> 对象
     * @param clazzName 班级名称 模糊匹配
     * @param gradeName 年级名称
     * @return Clazz分页对象
     */
    IPage<Clazz> getClazzsByOpr(Page<Clazz> clazzPage, String clazzName, String gradeName);
}
