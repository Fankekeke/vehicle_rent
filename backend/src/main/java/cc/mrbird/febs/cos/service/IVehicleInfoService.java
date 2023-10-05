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
     * 查询可预定车辆
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 结果
     */
    List<VehicleInfo> selectVehicleByDate(String startDate, String endDate);

    /**
     * 新增车辆信息
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     * @throws FebsException 异常
     */
    boolean vehicleAdd(VehicleInfo vehicleInfo) throws FebsException;

    /**
     * 车辆信息修改
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     * @throws FebsException 异常
     */
    boolean vehicleEdit(VehicleInfo vehicleInfo) throws FebsException;

    /**
     * 缴费信息详情
     *
     * @param id 缴费信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    LinkedHashMap<String, Object> selectPaymentRecordDetail(Integer id) throws FebsException;

    /**
     * 订单信息详情
     *
     * @param id 订单信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    LinkedHashMap<String, Object> selectOrderDetail(Integer id) throws FebsException;

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
     * 订单评价信息详情
     *
     * @param id 评价信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    LinkedHashMap<String, Object> selectEvaluateDetail(Integer id) throws FebsException;

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
     * 添加缴费记录
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    boolean payRecordAddress(String orderCode);

    /**
     * 车辆添加维修
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    boolean vehicleRepairAdd(RepairInfo repairInfo) throws FebsException;

    /**
     * 修改维修信息
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    boolean vehicleRepairEdit(RepairInfo repairInfo) throws FebsException;

    /**
     * 设置车辆在厂状态
     *
     * @return 结果
     */
    boolean setVehicleStatus();
}
