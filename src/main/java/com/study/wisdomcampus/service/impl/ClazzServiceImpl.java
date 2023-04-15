package com.study.wisdomcampus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.wisdomcampus.entity.Clazz;
import com.study.wisdomcampus.mapper.ClazzMapper;
import com.study.wisdomcampus.service.IClazzService;
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
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements IClazzService {

    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> clazzPage, String clazzName, String gradeName) {
        LambdaQueryWrapper<Clazz> clazzLambdaQueryWrapper = new LambdaQueryWrapper<>();
        clazzLambdaQueryWrapper
                .eq(StringUtils.hasLength(gradeName), Clazz::getGradeName, gradeName)
                .like(StringUtils.hasLength(clazzName), Clazz::getName, clazzName).
                orderByAsc(Clazz::getId)
                .orderByDesc(Clazz::getName);
        return page(clazzPage, clazzLambdaQueryWrapper);
    }
}
