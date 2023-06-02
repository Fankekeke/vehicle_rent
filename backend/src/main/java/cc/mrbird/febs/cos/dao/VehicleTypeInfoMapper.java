package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.VehicleTypeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface VehicleTypeInfoMapper extends BaseMapper<VehicleTypeInfo> {

    /**
     * 分页获取车辆类型信息
     *
     * @param page            分页对象
     * @param vehicleTypeInfo 车辆类型信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectVehicleTypePage(Page<VehicleTypeInfo> page, @Param("vehicleTypeInfo") VehicleTypeInfo vehicleTypeInfo);
}
