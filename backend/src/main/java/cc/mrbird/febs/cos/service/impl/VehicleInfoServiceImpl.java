package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.entity.VehicleInfo;
import cc.mrbird.febs.cos.dao.VehicleInfoMapper;
import cc.mrbird.febs.cos.service.IVehicleInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleInfoServiceImpl extends ServiceImpl<VehicleInfoMapper, VehicleInfo> implements IVehicleInfoService {

    private final RepairInfoMapper repairInfoMapper;

    private final OrderInfoMapper orderInfoMapper;

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
     * 设置车辆在厂状态
     *
     * @return 结果
     */
    @Override
    public boolean setVehicleStatus() {
        // 需要更新的车辆状态信息
        List<VehicleInfo> vehicleUpdateList = new ArrayList<>();
        // 需要更新的维修状态信息
        List<RepairInfo> repairUpdateList = new ArrayList<>();
        // 需要更新的订单状态信息
        List<OrderInfo> orderUpdateList = new ArrayList<>();

        // 车辆信息
        List<VehicleInfo> vehicleList = this.list(Wrappers.<VehicleInfo>lambdaQuery().ne(VehicleInfo::getStatus, 3));
        Map<String, VehicleInfo> vehicleMap = vehicleList.stream().collect(Collectors.toMap(VehicleInfo::getVehicleNo, e -> e, (k1, k2) -> k2));
        // 维修信息
        List<RepairInfo> repairList = repairInfoMapper.selectList(Wrappers.<RepairInfo>lambdaQuery().eq(RepairInfo::getRepairStatus, 0));
        Map<String, RepairInfo> repairMap = repairList.stream().collect(Collectors.toMap(RepairInfo::getVehicleNo, e -> e, (k1, k2) -> k2));
        // 订单信息
        List<OrderInfo> orderList = orderInfoMapper.selectList(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getStatus, 0));
        Map<String, List<OrderInfo>> orderMap = orderList.stream().collect(Collectors.groupingBy(OrderInfo::getVehicleNo));

        for (String vehicleNo : vehicleMap.keySet()) {

            // 是否在维修中
            if (CollectionUtil.isNotEmpty(repairMap) && repairMap.get(vehicleNo) != null) {
                RepairInfo repair = repairMap.get(vehicleNo);
                boolean isIn = DateUtil.isIn(new Date(), DateUtil.parseDate(repair.getRepairStart()), DateUtil.parseDate(repair.getRepairEnd()));

            }
            // 是否在使用中

        }

        return false;
    }
}
