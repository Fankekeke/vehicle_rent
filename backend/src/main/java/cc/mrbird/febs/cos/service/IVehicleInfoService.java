package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.entity.VehicleInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IVehicleInfoService extends IService<VehicleInfo> {

    /**
     * 分页获取车辆信息
     *
     * @param page      分页对象
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectVehiclePage(Page<VehicleInfo> page, VehicleInfo vehicleInfo);

    /**
     * 新增车辆信息
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    boolean vehicleAdd(VehicleInfo vehicleInfo) throws FebsException;

    /**
     * 车辆添加维修
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    boolean vehicleRepairAdd(RepairInfo repairInfo) throws FebsException;

    /**
     * 设置车辆在厂状态
     *
     * @return 结果
     */
    boolean setVehicleStatus();
}
