package com.study.wisdomcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.wisdomcampus.entity.Grade;
import com.study.wisdomcampus.mapper.GradeMapper;
import com.study.wisdomcampus.service.IGradeService;
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
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements IGradeService {

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> gradePage, String gradeName) {
        LambdaQueryWrapper<Grade> gradeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        gradeLambdaQueryWrapper.like(StringUtils.hasLength(gradeName), Grade::getName, gradeName).
                orderByAsc(Grade::getId)
                .orderByDesc(Grade::getName);
        return page(gradePage, gradeLambdaQueryWrapper);
    }
}
