package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.VehicleTypeInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IVehicleTypeInfoService extends IService<VehicleTypeInfo> {

    /**
     * 分页获取车辆类型信息
     *
     * @param page            分页对象
     * @param vehicleTypeInfo 车辆类型信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectVehicleTypePage(Page<VehicleTypeInfo> page, VehicleTypeInfo vehicleTypeInfo);
}
