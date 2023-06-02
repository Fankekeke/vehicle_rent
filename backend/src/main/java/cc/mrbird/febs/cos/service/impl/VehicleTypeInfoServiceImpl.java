package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.VehicleTypeInfo;
import cc.mrbird.febs.cos.dao.VehicleTypeInfoMapper;
import cc.mrbird.febs.cos.service.IVehicleTypeInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class VehicleTypeInfoServiceImpl extends ServiceImpl<VehicleTypeInfoMapper, VehicleTypeInfo> implements IVehicleTypeInfoService {

    /**
     * 分页获取车辆类型信息
     *
     * @param page            分页对象
     * @param vehicleTypeInfo 车辆类型信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectVehicleTypePage(Page<VehicleTypeInfo> page, VehicleTypeInfo vehicleTypeInfo) {
        return baseMapper.selectVehicleTypePage(page, vehicleTypeInfo);
    }
}
