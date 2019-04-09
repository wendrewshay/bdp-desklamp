package com.desklamp.gateway.service;

import com.desklamp.common.utils.DateUtils;
import com.desklamp.gateway.domain.ClientSvcInvoke;
import com.desklamp.gateway.repository.ClientSvcInvokeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 客户服务调用统计逻辑层
 * @author by Joney on 2019/4/9 14:27
 */
@Service
public class ClientSvcInvokeService {

    @Autowired
    private ClientSvcInvokeRepository clientSvcInvokeRepository;

    /**
     * 保存统计数据
     * @param record 记录参数
     * @return
     * @author by Joney on 2019/4/9 14:40
     */
    @Transactional
    public ClientSvcInvoke save(ClientSvcInvoke record) {
        record.setInvokeTime(new Date());
        ClientSvcInvoke entity = clientSvcInvokeRepository.find(
                record.getServiceName(), record.getRequestUri(), record.getClientUser(), DateUtils.todayFirstDate(), DateUtils.todayLastDate());
        if (entity != null) {
            entity.setInvokeCount(entity.getInvokeCount() + 1);
            entity.setInvokeTime(record.getInvokeTime());
        } else {
            record.setInvokeCount(1);
            entity = record;
        }
        return clientSvcInvokeRepository.save(entity);
    }

}
