package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.NotifyInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface INotifyInfoService extends IService<NotifyInfo> {

    /**
     * 分页获取通知信息
     *
     * @param page       分页对象
     * @param notifyInfo 通知信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectNotifyPage(Page<NotifyInfo> page, NotifyInfo notifyInfo);

    /**
     * 添加消息通知
     *
     * @param userCode 用户编号
     * @param name     用户名称
     * @param content  内容
     * @return 结果
     */
    boolean sendNotify(String userCode, String name, String content);
}
