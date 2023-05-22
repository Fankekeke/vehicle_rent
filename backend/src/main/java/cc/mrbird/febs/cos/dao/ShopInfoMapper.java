package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ShopInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ShopInfoMapper extends BaseMapper<ShopInfo> {

    /**
     * 分页获取车店信息
     *
     * @param page      分页对象
     * @param shopInfo 车店信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectShopPage(Page<ShopInfo> page, @Param("shopInfo") ShopInfo shopInfo);
}
