package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.NotifyInfo;
import cc.mrbird.febs.cos.dao.NotifyInfoMapper;
import cc.mrbird.febs.cos.service.INotifyInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class NotifyInfoServiceImpl extends ServiceImpl<NotifyInfoMapper, NotifyInfo> implements INotifyInfoService {

    /**
     * 分页获取通知信息
     *
     * @param page       分页对象
     * @param notifyInfo 通知信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectNotifyPage(Page<NotifyInfo> page, NotifyInfo notifyInfo) {
        return baseMapper.selectNotifyPage(page, notifyInfo);
    }

    /**
     * 添加消息通知
     *
     * @param userCode 用户编号
     * @param name     用户名称
     * @param content  内容
     * @return 结果
     */
    @Override
    public boolean sendNotify(String userCode, String name, String content) {
        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setContent(content);
        notifyInfo.setUserCode(userCode);
        notifyInfo.setName(name);
        notifyInfo.setDelFlag(0);
        notifyInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return this.save(notifyInfo);
    }
}
