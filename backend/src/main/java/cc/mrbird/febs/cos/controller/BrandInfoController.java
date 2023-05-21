package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.BrandInfo;
import cc.mrbird.febs.cos.service.IBrandInfoService;
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
@RequestMapping("/cos/brand-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrandInfoController {

    private final IBrandInfoService brandInfoService;

    /**
     * 分页获取品牌信息
     *
     * @param page      分页对象
     * @param brandInfo 品牌信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<BrandInfo> page, BrandInfo brandInfo) {
        return R.ok();
    }

    /**
     * 获取ID获取品牌详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(brandInfoService.getById(id));
    }

    /**
     * 获取品牌信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(brandInfoService.list());
    }

    /**
     * 新增品牌信息
     *
     * @param brandInfo 品牌信息
     * @return 结果
     */
    @PostMapping
    public R save(BrandInfo brandInfo) {
        brandInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(brandInfoService.save(brandInfo));
    }

    /**
     * 修改品牌信息
     *
     * @param brandInfo 品牌信息
     * @return 结果
     */
    @PutMapping
    public R edit(BrandInfo brandInfo) {
        return R.ok(brandInfoService.updateById(brandInfo));
    }

    /**
     * 删除品牌信息
     *
     * @param ids ids
     * @return 品牌信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(brandInfoService.removeByIds(ids));
    }

}
