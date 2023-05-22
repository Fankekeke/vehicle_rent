package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.VehicleInfo;
import cc.mrbird.febs.cos.dao.VehicleInfoMapper;
import cc.mrbird.febs.cos.service.IVehicleInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class VehicleInfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> implements IVehicleInfoService {

    /**
     * 分页获取车辆信息
     *
     * @param page      分页对象
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectVehiclePage(Page<VehicleInfo> page, VehicleInfo vehicleInfo) {
        return baseMapper.selectVehiclePage(page, vehicleInfo);
    }
}
