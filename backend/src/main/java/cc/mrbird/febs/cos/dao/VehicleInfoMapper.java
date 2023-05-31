package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.VehicleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface VehicleInfoMapper extends BaseMapper<VehicleInfo> {

    /**
     * 分页获取车辆信息
     *
     * @param page      分页对象
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectVehiclePage(Page<VehicleInfo> page, @Param("vehicleInfo") VehicleInfo vehicleInfo);

    /**
     * 根据车辆编号获取车辆维修记录
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRepairByVehicle(@Param("vehicleNo") String vehicleNo);
}
