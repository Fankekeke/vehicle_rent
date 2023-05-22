package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.BrandInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IBrandInfoService extends IService<BrandInfo> {

    /**
     * 分页获取品牌信息
     *
     * @param page      分页对象
     * @param brandInfo 品牌信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectBrandPage(Page<BrandInfo> page, BrandInfo brandInfo);
}
