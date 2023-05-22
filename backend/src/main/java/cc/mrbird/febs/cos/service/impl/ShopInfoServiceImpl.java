package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ShopInfo;
import cc.mrbird.febs.cos.dao.ShopInfoMapper;
import cc.mrbird.febs.cos.service.IShopInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class ShopInfoServiceImpl extends ServiceImpl<ShopInfoMapper, ShopInfo> implements IShopInfoService {

    /**
     * 分页获取车店信息
     *
     * @param page      分页对象
     * @param shopInfo 车店信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectShopPage(Page<ShopInfo> page, ShopInfo shopInfo) {
        return baseMapper.selectShopPage(page, shopInfo);
    }
}
