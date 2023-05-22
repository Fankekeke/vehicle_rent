package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/repair-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairInfoController {

    private final IRepairInfoService repairInfoService;

    /**
     * 分页获取维修信息信息
     *
     * @param page      分页对象
     * @param repairInfo 维修信息信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RepairInfo> page, RepairInfo repairInfo) {
        return R.ok(repairInfoService.selectRepairPage(page, repairInfo));
    }

    /**
     * 获取ID获取维修信息详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(repairInfoService.getById(id));
    }

    /**
     * 获取维修信息信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(repairInfoService.list());
    }

    /**
     * 新增维修信息信息
     *
     * @param repairInfo 维修信息信息
     * @return 结果
     */
    @PostMapping
    public R save(RepairInfo repairInfo) {
        repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(repairInfoService.save(repairInfo));
    }

    /**
     * 修改维修信息信息
     *
     * @param repairInfo 维修信息信息
     * @return 结果
     */
    @PutMapping
    public R edit(RepairInfo repairInfo) {
        return R.ok(repairInfoService.updateById(repairInfo));
    }

    /**
     * 删除维修信息信息
     *
     * @param ids ids
     * @return 维修信息信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(repairInfoService.removeByIds(ids));
    }
    
}
