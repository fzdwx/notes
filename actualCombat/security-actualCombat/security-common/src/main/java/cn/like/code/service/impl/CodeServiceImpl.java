package cn.like.code.service.impl;

import cn.like.code.entity.Code;
import cn.like.code.mapper.CodeMapper;
import cn.like.code.service.CodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 授权码(Code)表服务实现类
 *
 * @author like
 * @since 2021-06-04 09:09:21
 */
@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements CodeService {

}