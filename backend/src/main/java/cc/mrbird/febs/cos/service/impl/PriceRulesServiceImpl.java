package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PriceRules;
import cc.mrbird.febs.cos.dao.PriceRulesMapper;
import cc.mrbird.febs.cos.service.IPriceRulesService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class PriceRulesServiceImpl extends ServiceImpl<PriceRulesMapper, PriceRules> implements IPriceRulesService {

    /**
     * 分页获取价格规则信息
     *
     * @param page         分页对象
     * @param priceRules 价格规则信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectPriceRulePage(Page<PriceRules> page, PriceRules priceRules) {
        return baseMapper.selectPriceRulePage(page, priceRules);
    }
}
