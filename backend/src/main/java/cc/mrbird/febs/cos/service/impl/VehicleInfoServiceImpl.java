package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.entity.VehicleInfo;
import cc.mrbird.febs.cos.dao.VehicleInfoMapper;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import cc.mrbird.febs.cos.service.IVehicleInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleInfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> implements IVehicleInfoService {

    private final IRepairInfoService repairInfoService;

    private final IOrderInfoService orderInfoService;

    /**
     * 分页获取车辆信息
     *
     * @param page        分页对象
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectVehiclePage(Page<VehicleInfo> page, VehicleInfo vehicleInfo) {
        return baseMapper.selectVehiclePage(page, vehicleInfo);
    }

    /**
     * 新增车辆信息
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @Override
    public boolean vehicleAdd(VehicleInfo vehicleInfo) throws FebsException {
        // 校验车辆编号
        if (StrUtil.isEmpty(vehicleInfo.getVehicleNo())) {
            throw new FebsException("车辆编号不能为空！");
        }
        int count = this.count(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, vehicleInfo.getVehicleNo()));
        if (count > 0) {
            throw new FebsException("车辆编号重复！");
        }
        return this.save(vehicleInfo);
    }

    /**
     * 车辆添加维修
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    @Override
    public boolean vehicleRepairAdd(RepairInfo repairInfo) throws FebsException {
        // 校验车辆信息
        if (StrUtil.isEmpty(repairInfo.getVehicleNo()) || StrUtil.isEmpty(repairInfo.getRepairStart()) || StrUtil.isEmpty(repairInfo.getRepairEnd())) {
            throw new FebsException("参数不能为空！");
        }

        VehicleInfo vehicle = this.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, repairInfo.getVehicleNo()));
        if (vehicle == null) {
            throw new FebsException("车辆信息不存在！！");
        }

        // 维修时间校验【开始结束时间不能小于当前日期】
        boolean repairDateCheck = (DateUtil.compare(new DateTime(), DateUtil.parseDate("2023-05-24")) == -1);

        repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 维修状态-判断维修计划是否处于当前时间内
        boolean isIn = DateUtil.isIn(new DateTime(), DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(repairInfo.getRepairEnd()));
        if (isIn) {
            vehicle.setStatus("2");
            this.updateById(vehicle);
        }
        repairInfo.setRepairStatus("0");
        return false;
    }

    /**
     * 设置车辆在厂状态
     *
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setVehicleStatus() {
        // 需要更新的车辆状态信息
        Map<String, String> vehicleUpdateList = new HashMap<>();
        // 需要更新的维修状态信息
        List<RepairInfo> repairUpdateList = new ArrayList<>();
        // 需要更新的订单状态信息
        List<OrderInfo> orderUpdateList = new ArrayList<>();

        // 车辆信息
        List<VehicleInfo> vehicleList = this.list(Wrappers.<VehicleInfo>lambdaQuery().ne(VehicleInfo::getStatus, 3));
        Map<String, VehicleInfo> vehicleMap = vehicleList.stream().collect(Collectors.toMap(VehicleInfo::getVehicleNo, e -> e, (k1, k2) -> k2));
        // 维修信息
        List<RepairInfo> repairList = repairInfoService.list(Wrappers.<RepairInfo>lambdaQuery().eq(RepairInfo::getRepairStatus, 0));
        Map<String, RepairInfo> repairMap = repairList.stream().collect(Collectors.toMap(RepairInfo::getVehicleNo, e -> e, (k1, k2) -> k2));
        // 订单信息
        List<OrderInfo> orderList = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getStatus, 0));
        Map<String, List<OrderInfo>> orderMap = orderList.stream().collect(Collectors.groupingBy(OrderInfo::getVehicleNo));

        for (String vehicleNo : vehicleMap.keySet()) {
            // 车辆维修状态
            boolean repairFinish = true;
            // 订单状态
            boolean orderFinish = true;
            // 是否在维修中
            if (CollectionUtil.isNotEmpty(repairMap) && repairMap.get(vehicleNo) != null) {
                RepairInfo repair = repairMap.get(vehicleNo);
                boolean isIn = DateUtil.isIn(new Date(), DateUtil.parseDate(repair.getRepairStart()), DateUtil.parseDate(repair.getRepairEnd()));
                if (isIn) {
                    vehicleUpdateList.put(vehicleNo, "2");
                    repair.setRepairStatus("0");
                    repairUpdateList.add(repair);
                    repairFinish = false;
                }
                // 如果维修结束时间大于当前时间（维修状态已完成）
                if (DateUtil.compare(new DateTime(), DateUtil.parseDate(repair.getRepairEnd())) >= 0) {
                    repair.setRepairStatus("1");
                    repairUpdateList.add(repair);
                    repairFinish = true;
                }
            }
            // 是否在使用中
            if (CollectionUtil.isNotEmpty(orderMap) && CollectionUtil.isNotEmpty(orderMap.get(vehicleNo))) {
                List<OrderInfo> orders = orderMap.get(vehicleNo);

                for (OrderInfo order : orders) {
                    // 是否在开始截至日期内
                    boolean isIn = DateUtil.isIn(new Date(), DateUtil.parseDate(order.getStartDate()), DateUtil.parseDate(order.getEndDate()));
                    if (isIn) {
                        orderFinish = false;
                        order.setStatus("0");
                        orderUpdateList.add(order);
                        vehicleUpdateList.put(vehicleNo, "1");
                    }
                    // 如果结束时间大于当前时间（订单状态已完成）
                    if (DateUtil.compare(new DateTime(), DateUtil.parseDate(order.getEndDate())) >= 0) {
                        order.setStatus("1");
                        orderUpdateList.add(order);
                    }
                }
            }

            // 车辆状态更新
            if (repairFinish && orderFinish) {
                vehicleUpdateList.put(vehicleNo, "0");
            }
        }
        // 更新状态
        if (CollectionUtil.isNotEmpty(vehicleUpdateList)) {
            vehicleUpdateList.forEach((key, value) -> {
                this.update(Wrappers.<VehicleInfo>lambdaUpdate().set(VehicleInfo::getStatus, value).eq(VehicleInfo::getVehicleNo, key));
            });
        }
        if (CollectionUtil.isNotEmpty(repairUpdateList)) {
            repairInfoService.updateBatchById(repairUpdateList);
        }
        if (CollectionUtil.isNotEmpty(orderUpdateList)) {
            orderInfoService.updateBatchById(orderUpdateList);
        }
        return true;
    }
}
