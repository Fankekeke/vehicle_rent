package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.BrandInfo;
import cc.mrbird.febs.cos.dao.BrandInfoMapper;
import cc.mrbird.febs.cos.service.IBrandInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class BrandInfoServiceImpl extends ServiceImpl<BrandInfoMapper, BrandInfo> implements IBrandInfoService {

    /**
     * 分页获取品牌信息
     *
     * @param page      分页对象
     * @param brandInfo 品牌信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectBrandPage(Page<BrandInfo> page, BrandInfo brandInfo) {
        return baseMapper.selectBrandPage(page, brandInfo);
    }
}
