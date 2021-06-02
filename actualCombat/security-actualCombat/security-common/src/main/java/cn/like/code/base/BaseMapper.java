package cn.like.code.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * @author like
 * @date 2021/6/2 12:12
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    default LambdaQueryWrapper<T> lambdaQuery() {
        return Wrappers.lambdaQuery();
    }
}
