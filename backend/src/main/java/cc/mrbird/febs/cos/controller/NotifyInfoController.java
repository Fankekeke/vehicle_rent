package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.NotifyInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.INotifyInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/notify-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotifyInfoController {

    private final INotifyInfoService notifyInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取通知信息
     *
     * @param page       分页对象
     * @param notifyInfo 通知信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<NotifyInfo> page, NotifyInfo notifyInfo) {
        return R.ok(notifyInfoService.selectNotifyPage(page, notifyInfo));
    }

    /**
     * 获取ID获取通知详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(notifyInfoService.getById(id));
    }

    /**
     * 获取通知信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(notifyInfoService.list());
    }

    /**
     * 新增通知信息
     *
     * @param notifyInfo 通知信息
     * @return 结果
     */
    @PostMapping
    public R save(NotifyInfo notifyInfo) {
        notifyInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(notifyInfoService.save(notifyInfo));
    }

    /**
     * 根据用户ID查询消息通知1
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/detail/{userId}")
    public R selectListByUserId(@PathVariable("userId") Integer userId) {
        UserInfo user = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        if (user == null) {
            return R.ok(Collections.emptyList());
        }
        return R.ok(notifyInfoService.list(Wrappers.<NotifyInfo>lambdaQuery().eq(NotifyInfo::getUserCode, user.getCode()).eq(NotifyInfo::getDelFlag, 0)));
    }

    @GetMapping("/setReadStatus/{id}")
    public R setReadStatus(@PathVariable("id") Integer id) {
        return R.ok(notifyInfoService.update(Wrappers.<NotifyInfo>lambdaUpdate().set(NotifyInfo::getDelFlag, 1).eq(NotifyInfo::getId, id)));
    }

    /**
     * 修改通知信息
     *
     * @param notifyInfo 通知信息
     * @return 结果
     */
    @PutMapping
    public R edit(NotifyInfo notifyInfo) {
        return R.ok(notifyInfoService.updateById(notifyInfo));
    }

    /**
     * 删除通知信息
     *
     * @param ids ids
     * @return 通知信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(notifyInfoService.removeByIds(ids));
    }
}
