package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.entity.VehicleInfo;
import cc.mrbird.febs.cos.service.IVehicleInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/vehicle-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleInfoController {

    private final IVehicleInfoService vehicleInfoService;

    /**
     * 分页获取车辆信息
     *
     * @param page        分页对象
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<VehicleInfo> page, VehicleInfo vehicleInfo) {
        return R.ok(vehicleInfoService.selectVehiclePage(page, vehicleInfo));
    }

    /**
     * 查询可预定车辆
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 结果
     */
    @GetMapping("/order/check")
    public R selectVehicleByDate(@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) {
        return R.ok(vehicleInfoService.selectVehicleByDate(startDate, endDate));
    }

    /**
     * 根据车辆编号获取日历看板
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     */
    @GetMapping("/vehicle/calendar/{vehicleNo}")
    public R selectVehicleCalendar(@PathVariable("vehicleNo") String vehicleNo) {
        return R.ok(vehicleInfoService.selectVehicleCalendar(vehicleNo));
    }

    /**
     * 车辆信息详情
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     * @throws FebsException 异常
     */
    @GetMapping("/detail/{vehicleNo}")
    public R selectVehicleDetail(@PathVariable("vehicleNo") String vehicleNo) throws FebsException {
        return R.ok(vehicleInfoService.selectVehicleDetail(vehicleNo));
    }

    /**
     * 车辆维修信息详情
     *
     * @param id 维修信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    @GetMapping("/repair/detail/{id}")
    public R selectVehicleRepairDetail(@PathVariable("id") Integer id) throws FebsException {
        return R.ok(vehicleInfoService.selectVehicleRepairDetail(id));
    }

    /**
     * 缴费信息详情
     *
     * @param id 缴费信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    @GetMapping("/payment/record/detail/{id}")
    public R selectPaymentRecordDetail(@PathVariable("id") Integer id) throws FebsException {
        return R.ok(vehicleInfoService.selectPaymentRecordDetail(id));
    }

    /**
     * 订单评价信息详情
     *
     * @param id 评价信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    @GetMapping("/evaluate/detail/{id}")
    public R selectEvaluateDetail(@PathVariable("id") Integer id) throws FebsException {
        return R.ok(vehicleInfoService.selectEvaluateDetail(id));
    }

    /**
     * 订单信息详情
     *
     * @param id 订单信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    @GetMapping("/order/detail/{id}")
    public R selectOrderDetail(@PathVariable("id") Integer id) throws FebsException {
        return R.ok(vehicleInfoService.selectOrderDetail(id));
    }

    /**
     * 添加订单信息
     *
     * @param orderInfo 车辆订单信息
     * @return 结果
     */
    @PostMapping("/order/add")
    public R vehicleOrderAdd(OrderInfo orderInfo) throws FebsException {
        return R.ok(vehicleInfoService.vehicleOrderAdd(orderInfo));
    }

    /**
     * 车辆添加维修
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    @PostMapping("/repair/add")
    public R vehicleRepairAdd(RepairInfo repairInfo) throws FebsException {
        return R.ok(vehicleInfoService.vehicleRepairAdd(repairInfo));
    }

    /**
     * 修改维修信息
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    @PutMapping("/repair/edit")
    public R vehicleRepairEdit(RepairInfo repairInfo) throws FebsException {
        return R.ok();
    }

    /**
     * 获取ID获取车辆详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(vehicleInfoService.getById(id));
    }

    /**
     * 获取车辆信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(vehicleInfoService.list());
    }

    /**
     * 新增车辆信息
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @PostMapping
    public R save(VehicleInfo vehicleInfo) throws FebsException {
        vehicleInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        vehicleInfo.setStatus("0");
        return R.ok(vehicleInfoService.vehicleAdd(vehicleInfo));
    }

    /**
     * 修改车辆信息
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @PutMapping
    public R edit(VehicleInfo vehicleInfo) throws FebsException {
        return R.ok(vehicleInfoService.vehicleEdit(vehicleInfo));
    }

    /**
     * 删除车辆信息
     *
     * @param ids ids
     * @return 车辆信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(vehicleInfoService.removeByIds(ids));
    }

}
