package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.entity.VehicleInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IVehicleInfoService extends IService<VehicleInfo> {

    /**
     * 分页获取车辆信息
     *
     * @param page        分页对象
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
     * 缴费信息详情
     *
     * @param id 缴费信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    LinkedHashMap<String, Object> selectPaymentRecordDetail(Integer id) throws FebsException;

    /**
     * 车辆信息详情
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     * @throws FebsException 异常
     */
    LinkedHashMap<String, Object> selectVehicleDetail(String vehicleNo) throws FebsException;

    /**
     * 车辆维修信息详情
     *
     * @param id 维修信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    LinkedHashMap<String, Object> selectVehicleRepairDetail(Integer id) throws FebsException;

    /**
     * 根据车辆编号获取车辆维修记录
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRepairByVehicle(String vehicleNo);

    /**
     * 根据车辆编号获取日历看板
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     */
    LinkedHashMap<String, Object> selectVehicleCalendar(String vehicleNo);

    /**
     * 添加订单信息
     *
     * @param orderInfo 车辆订单信息
     * @return 结果
     */
    boolean vehicleOrderAdd(OrderInfo orderInfo) throws FebsException;

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
