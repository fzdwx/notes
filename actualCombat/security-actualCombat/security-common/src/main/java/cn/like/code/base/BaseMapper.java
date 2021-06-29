package cn.like.code.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * @author like
 * @date 2021/6/2 12:12
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {
    
    /**
     * desc: λ查询 <br>
     * details: 返回一个lambda query
     *
     * @return: {@link LambdaQueryWrapper<T> }
     * @author: like 980650920@qq.com
     * @date 2021-06-29 20:27:41
     */
    default LambdaQueryWrapper<T> lambdaQuery() {
        return Wrappers.lambdaQuery();
    }
}
