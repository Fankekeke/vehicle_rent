package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PriceRules;
import cc.mrbird.febs.cos.service.IPriceRulesService;
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
@RequestMapping("/cos/price-rules")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceRulesController {

    private final IPriceRulesService priceRulesService;

    /**
     * 分页获取价格规则信息
     *
     * @param page         分页对象
     * @param priceRules 价格规则信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PriceRules> page, PriceRules priceRules) {
        return R.ok(priceRulesService.selectPriceRulePage(page, priceRules));
    }

    /**
     * 获取ID获取价格规则详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(priceRulesService.getById(id));
    }

    /**
     * 获取价格规则信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(priceRulesService.list());
    }

    /**
     * 新增价格规则信息
     *
     * @param priceRules 价格规则信息
     * @return 结果
     */
    @PostMapping
    public R save(PriceRules priceRules) {
        priceRules.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(priceRulesService.save(priceRules));
    }

    /**
     * 修改价格规则信息
     *
     * @param priceRules 价格规则信息
     * @return 结果
     */
    @PutMapping
    public R edit(PriceRules priceRules) {
        return R.ok(priceRulesService.updateById(priceRules));
    }

    /**
     * 删除价格规则信息
     *
     * @param ids ids
     * @return 价格规则信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(priceRulesService.removeByIds(ids));
    }

}
