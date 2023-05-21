package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ShopInfo;
import cc.mrbird.febs.cos.service.IShopInfoService;
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
@RequestMapping("/cos/shop-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShopInfoController {

    private final IShopInfoService shopInfoService;

    /**
     * 分页获取车店信息
     *
     * @param page      分页对象
     * @param shopInfo 车店信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ShopInfo> page, ShopInfo shopInfo) {
        return R.ok();
    }

    /**
     * 获取ID获取车店详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(shopInfoService.getById(id));
    }

    /**
     * 获取车店信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(shopInfoService.list());
    }

    /**
     * 新增车店信息
     *
     * @param shopInfo 车店信息
     * @return 结果
     */
    @PostMapping
    public R save(ShopInfo shopInfo) {
        shopInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(shopInfoService.save(shopInfo));
    }

    /**
     * 修改车店信息
     *
     * @param shopInfo 车店信息
     * @return 结果
     */
    @PutMapping
    public R edit(ShopInfo shopInfo) {
        return R.ok(shopInfoService.updateById(shopInfo));
    }

    /**
     * 删除车店信息
     *
     * @param ids ids
     * @return 车店信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(shopInfoService.removeByIds(ids));
    }

}
