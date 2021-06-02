package cn.like.code.base;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author like
 * @date 2021/6/2 12:18
 */
public class BaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> {

}
