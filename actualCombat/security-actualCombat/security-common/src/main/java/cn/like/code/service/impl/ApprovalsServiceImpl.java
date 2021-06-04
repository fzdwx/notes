package cn.like.code.service.impl;

import cn.like.code.entity.Approvals;
import cn.like.code.mapper.ApprovalsMapper;
import cn.like.code.service.ApprovalsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 批准的用户(Approvals)表服务实现类
 *
 * @author like
 * @since 2021-06-04 09:09:13
 */
@Service
public class ApprovalsServiceImpl extends ServiceImpl<ApprovalsMapper, Approvals> implements ApprovalsService {

}