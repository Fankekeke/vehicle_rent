package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PriceRules;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface PriceRulesMapper extends BaseMapper<PriceRules> {

    /**
     * 分页获取价格规则信息
     *
     * @param page         分页对象
     * @param priceRules 价格规则信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPriceRulePage(Page<PriceRules> page, @Param("priceRules") PriceRules priceRules);
}
