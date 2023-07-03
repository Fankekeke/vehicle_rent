package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.VehicleInfoMapper;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateField;
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

    private final IPaymentRecordService paymentRecordService;

    private final IUserInfoService userInfoService;

    private final IShopInfoService shopInfoService;

    private final IOrderEvaluateService orderEvaluateService;

    private final IVehicleTypeInfoService vehicleTypeInfoService;

    private final IBrandInfoService brandInfoService;


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
        int count = this.count(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, vehicleInfo.getVehicleNo()).or().eq(VehicleInfo::getVehicleNumber, vehicleInfo.getVehicleNumber()));
        if (count > 0) {
            throw new FebsException("车辆编号或车牌号码重复！");
        }
        return this.save(vehicleInfo);
    }

    /**
     * 车辆信息修改
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     * @throws FebsException 异常
     */
    @Override
    public boolean vehicleEdit(VehicleInfo vehicleInfo) throws FebsException {
        // 校验车牌号或者车辆编号是否重复
        List<VehicleInfo> vehicleNoCheck = this.list(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, vehicleInfo.getVehicleNo()));
        List<VehicleInfo> vehicleNumberCheck = this.list(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNumber, vehicleInfo.getVehicleNumber()));
        if (vehicleNoCheck.size() > 1 || (vehicleNoCheck.size() == 1 && !vehicleNoCheck.get(0).getId().equals(vehicleInfo.getId()))) {
            throw new FebsException("车辆编号不能重复！");
        }
        if (vehicleNumberCheck.size() > 1 || (vehicleNumberCheck.size() == 1 && !vehicleNumberCheck.get(0).getId().equals(vehicleInfo.getId()))) {
            throw new FebsException("车牌号码不能重复！");
        }
        return this.updateById(vehicleInfo);
    }


    /**
     * 缴费信息详情
     *
     * @param id 缴费信息ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectPaymentRecordDetail(Integer id) throws FebsException {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("payment", null);
                put("order", null);
                put("vehicle", null);
                put("user", null);
            }
        };
        // 获取缴费信息
        PaymentRecord paymentRecord = paymentRecordService.getById(id);
        if (paymentRecord == null) {
            throw new FebsException("缴费信息不存在！");
        }
        result.put("payment", paymentRecord);

        // 订单信息
        OrderInfo orderInfo = orderInfoService.getById(paymentRecord.getOrderId());
        result.put("order", orderInfo);
        // 用户信息
        UserInfo userInfo = userInfoService.getById(orderInfo.getUserId());
        result.put("user", userInfo);
        // 车辆信息
        VehicleInfo vehicle = this.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, orderInfo.getVehicleNo()));
        result.put("vehicle", vehicle);
        return result;
    }

    /**
     * 订单信息详情
     *
     * @param id 订单信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    @Override
    public LinkedHashMap<String, Object> selectOrderDetail(Integer id) throws FebsException {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("order", null);
                put("vehicle", null);
                put("user", null);
            }
        };
        // 订单信息
        OrderInfo order = orderInfoService.getById(id);
        if (order == null) {
            throw new FebsException("订单信息不存在！");
        }
        result.put("order", order);
        // 车辆信息
        VehicleInfo vehicle = this.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, order.getVehicleNo()));
        result.put("vehicle", vehicle);
        // 用户信息
        UserInfo userInfo = userInfoService.getById(order.getUserId());
        result.put("user", userInfo);
        return result;
    }

    /**
     * 车辆信息详情
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     * @throws FebsException 异常
     */
    @Override
    public LinkedHashMap<String, Object> selectVehicleDetail(String vehicleNo) throws FebsException {
        if (StrUtil.isEmpty(vehicleNo)) {
            throw new FebsException("参数不能为空！");
        }
        // 获取车辆信息
        VehicleInfo vehicleInfo = this.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, vehicleNo));
        if (vehicleInfo == null) {
            throw new FebsException("信息不存在！");
        }
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("vehicle", vehicleInfo);
                put("shop", null);
                put("brand", null);
                put("type", null);
            }
        };
        // 车店信息
        ShopInfo shopInfo = shopInfoService.getById(vehicleInfo.getShopId());
        // 品牌信息
        BrandInfo brandInfo = brandInfoService.getById(vehicleInfo.getBrand());
        // 车辆类型
        VehicleTypeInfo vehicleType = vehicleTypeInfoService.getById(vehicleInfo.getUseType());
        result.put("shop", shopInfo);
        result.put("brand", brandInfo);
        result.put("type", vehicleType);
        return result;
    }

    /**
     * 车辆维修信息详情
     *
     * @param id 维修信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    @Override
    public LinkedHashMap<String, Object> selectVehicleRepairDetail(Integer id) throws FebsException {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("vehicle", null);
                put("repair", null);
                put("shop", null);
            }
        };
        // 维修信息
        RepairInfo repairInfo = repairInfoService.getById(id);
        if (repairInfo == null) {
            throw new FebsException("维修信息不存在！");
        }
        result.put("repair", repairInfo);
        // 车辆信息
        VehicleInfo vehicleInfo = this.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, repairInfo.getVehicleNo()));
        result.put("vehicle", vehicleInfo);
        // 车店信息
        ShopInfo shopInfo = shopInfoService.getById(vehicleInfo.getShopId());
        result.put("shop", shopInfo);
        return result;
    }

    /**
     * 订单评价信息详情
     *
     * @param id 评价信息ID
     * @return 结果
     * @throws FebsException 异常
     */
    @Override
    public LinkedHashMap<String, Object> selectEvaluateDetail(Integer id) throws FebsException {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("vehicle", null);
                put("order", null);
                put("user", null);
                put("evaluate", null);
            }
        };
        // 评价信息
        OrderEvaluate evaluate = orderEvaluateService.getById(id);
        if (evaluate == null) {
            throw new FebsException("评价信息不存在！");
        }
        result.put("evaluate", evaluate);
        // 订单信息
        OrderInfo order = orderInfoService.getById(evaluate.getOrderId());
        result.put("order", order);
        // 车辆信息
        VehicleInfo vehicle = this.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, order.getVehicleNo()));
        result.put("vehicle", vehicle);
        // 用户信息
        UserInfo user = userInfoService.getById(order.getUserId());
        result.put("user", user);
        return result;
    }

    /**
     * 根据车辆编号获取车辆维修记录
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     */
    @Override
    public List<LinkedHashMap<String, Object>> selectRepairByVehicle(String vehicleNo) {
        return baseMapper.selectRepairByVehicle(vehicleNo);
    }

    /**
     * 根据车辆编号获取日历看板
     *
     * @param vehicleNo 车辆编号
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectVehicleCalendar(String vehicleNo) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("order", null);
                put("repair", null);
            }
        };
        // 正在使用中的订单
        List<OrderInfo> orderList = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getVehicleNo, vehicleNo)
                .eq(OrderInfo::getStatus, "0"));
        if (CollectionUtil.isNotEmpty(orderList)) {
            LinkedHashMap<String, Object> orderDate = new LinkedHashMap<>();
            for (OrderInfo order : orderList) {
                orderDate.put(order.getCode(), DateUtil.rangeToList(DateUtil.parseDate(order.getStartDate()), DateUtil.parseDate(order.getEndDate()), DateField.DAY_OF_YEAR));
            }
            result.put("order", orderDate);
        }

        // 正在维修
        List<RepairInfo> repairList = repairInfoService.list(Wrappers.<RepairInfo>lambdaQuery().eq(RepairInfo::getVehicleNo, vehicleNo)
                .eq(RepairInfo::getRepairStatus, 0));
        if (CollectionUtil.isEmpty(repairList)) {
            LinkedHashMap<String, Object> repairDate = new LinkedHashMap<>();
            for (RepairInfo repair : repairList) {
                repairDate.put(repair.getCode(), DateUtil.rangeToList(DateUtil.parseDate(repair.getRepairStart()), DateUtil.parseDate(repair.getRepairEnd()), DateField.DAY_OF_YEAR));
            }
            result.put("repair", repairDate);
        }
        return result;
    }

    /**
     * 添加订单信息
     *
     * @param orderInfo 车辆订单信息
     * @return 结果
     */
    @Override
    public boolean vehicleOrderAdd(OrderInfo orderInfo) throws FebsException {
        // 获取车辆信息
        VehicleInfo vehicleInfo = this.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, orderInfo.getVehicleNo()));
        if (vehicleInfo == null) {
            throw new FebsException("车辆信息不存在！");
        }
        if (!"0".equals(vehicleInfo.getStatus())) {
            throw new FebsException("车辆正在使用中！");
        }

        // 设置订单开始租车和结束时间
        orderInfo.setStartDate(DateUtil.formatDateTime(DateUtil.parseDate(orderInfo.getStartDate() + " 00:00:01")));
        orderInfo.setEndDate(DateUtil.formatDateTime(DateUtil.parseDate(orderInfo.getEndDate() + " 23:59:59")));

        // 判断租车日期是否在维修或其它订单中
        List<OrderInfo> orderList = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getVehicleNo, orderInfo.getVehicleNo())
                .eq(OrderInfo::getStatus, "0"));
        if (CollectionUtil.isNotEmpty(orderList)) {
            for (OrderInfo order : orderList) {
                boolean overStartCheck = (DateUtil.compare(DateUtil.parseDate(orderInfo.getStartDate()), DateUtil.parseDate(order.getStartDate())) == -1
                        && DateUtil.compare(DateUtil.parseDate(orderInfo.getEndDate()), DateUtil.parseDate(order.getEndDate())) == -1);
                boolean overEndCheck = (DateUtil.compare(DateUtil.parseDate(orderInfo.getStartDate()), DateUtil.parseDate(order.getStartDate())) == -1
                        && DateUtil.compare(DateUtil.parseDate(orderInfo.getEndDate()), DateUtil.parseDate(order.getEndDate())) == -1);
                if (!overStartCheck || !overEndCheck) {
                    throw new FebsException("所选日期在其他用户使用车辆日期内！");
                }
            }
        }
        // 维修
        List<RepairInfo> repairList = repairInfoService.list(Wrappers.<RepairInfo>lambdaQuery().eq(RepairInfo::getVehicleNo, orderInfo.getVehicleNo())
                .eq(RepairInfo::getRepairStatus, 0));
        if (CollectionUtil.isNotEmpty(repairList)) {
            for (RepairInfo repair : repairList) {
                boolean overStartCheck = (DateUtil.compare(DateUtil.parseDate(orderInfo.getStartDate()), DateUtil.parseDate(repair.getRepairStart())) == -1
                        && DateUtil.compare(DateUtil.parseDate(orderInfo.getEndDate()), DateUtil.parseDate(repair.getRepairEnd())) == -1);
                boolean overEndCheck = (DateUtil.compare(DateUtil.parseDate(orderInfo.getStartDate()), DateUtil.parseDate(repair.getRepairStart())) == -1
                        && DateUtil.compare(DateUtil.parseDate(orderInfo.getEndDate()), DateUtil.parseDate(repair.getRepairEnd())) == -1);
                if (!overStartCheck || !overEndCheck) {
                    throw new FebsException("所选日期在车辆维修日期内！");
                }
            }
        }

        // 车辆状态-判断当前时间是否处于订单开始结束时间内
        boolean isIn = DateUtil.isIn(new DateTime(), DateUtil.parseDate(orderInfo.getStartDate()), DateUtil.parseDate(orderInfo.getEndDate()));
        if (isIn) {
            vehicleInfo.setStatus("1");
            this.updateById(vehicleInfo);
        }
        orderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        orderInfo.setStatus("0");
        orderInfo.setCode("OR-" + System.currentTimeMillis());
        return orderInfoService.save(orderInfo);
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
        boolean repairDateCheck = (DateUtil.compare(new DateTime(), DateUtil.parseDate(repairInfo.getRepairStart())) == -1 && DateUtil.compare(new DateTime(), DateUtil.parseDate(repairInfo.getRepairEnd())) == -1);
        if (!repairDateCheck) {
            throw new FebsException("开始结束时间不能小于当前日期");
        }

        // 是否存在待维修的数据
        int repairCheck = repairInfoService.count(Wrappers.<RepairInfo>lambdaQuery().eq(RepairInfo::getVehicleNo, repairInfo.getVehicleNo()).eq(RepairInfo::getRepairStatus, 0));
        if (repairCheck > 0) {
            throw new FebsException("此车辆已经添加过维修");
        }

        // 设置维修开始结束格式
        repairInfo.setRepairStart(DateUtil.formatDateTime(DateUtil.parseDate(repairInfo.getRepairStart() + " 00:00:01")));
        repairInfo.setRepairEnd(DateUtil.formatDateTime(DateUtil.parseDate(repairInfo.getRepairEnd() + " 23:59:59")));

        // 维修车辆是否使用中
        List<OrderInfo> orderList = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getVehicleNo, repairInfo.getVehicleNo())
                .eq(OrderInfo::getStatus, "0"));
        if (CollectionUtil.isNotEmpty(orderList)) {
            for (OrderInfo order : orderList) {
                boolean overStartCheck = (DateUtil.compare(DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(order.getStartDate())) == -1
                        && DateUtil.compare(DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(order.getEndDate())) == -1);
                boolean overEndCheck = (DateUtil.compare(DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(order.getStartDate())) == -1
                        && DateUtil.compare(DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(order.getEndDate())) == -1);
                if (!overStartCheck || !overEndCheck) {
                    throw new FebsException("维修日期在车辆使用日期内！");
                }
            }
        }

        repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 维修状态-判断维修计划是否处于当前时间内
        boolean isIn = DateUtil.isIn(new DateTime(), DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(repairInfo.getRepairEnd()));
        if (isIn) {
            vehicle.setStatus("2");
            this.updateById(vehicle);
        }
        repairInfo.setRepairStatus("0");
        return repairInfoService.save(repairInfo);
    }

    /**
     * 修改维修信息
     *
     * @param repairInfo 维修信息
     * @return 结果
     */
    @Override
    public boolean vehicleRepairEdit(RepairInfo repairInfo) throws FebsException {
        if (StrUtil.isEmpty(repairInfo.getVehicleNo()) || StrUtil.isEmpty(repairInfo.getRepairStart()) || StrUtil.isEmpty(repairInfo.getRepairEnd())) {
            throw new FebsException("参数不能为空！");
        }

        // 校验车辆信息
        VehicleInfo vehicle = this.getOne(Wrappers.<VehicleInfo>lambdaQuery().eq(VehicleInfo::getVehicleNo, repairInfo.getVehicleNo()));
        if (vehicle == null) {
            throw new FebsException("车辆信息不存在！！");
        }

        // 维修时间校验【开始结束时间不能小于当前日期】
        boolean repairDateCheck = (DateUtil.compare(new DateTime(), DateUtil.parseDate(repairInfo.getRepairStart())) == -1 && DateUtil.compare(new DateTime(), DateUtil.parseDate(repairInfo.getRepairEnd())) == -1);
        if (!repairDateCheck) {
            throw new FebsException("开始结束时间不能小于当前日期");
        }

        // 维修车辆是否使用中
        List<OrderInfo> orderList = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getVehicleNo, repairInfo.getVehicleNo())
                .eq(OrderInfo::getStatus, "0"));
        if (CollectionUtil.isNotEmpty(orderList)) {
            for (OrderInfo order : orderList) {
                boolean overStartCheck = (DateUtil.compare(DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(order.getStartDate())) == -1
                        && DateUtil.compare(DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(order.getEndDate())) == -1);
                boolean overEndCheck = (DateUtil.compare(DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(order.getStartDate())) == -1
                        && DateUtil.compare(DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(order.getEndDate())) == -1);
                if (!overStartCheck || !overEndCheck) {
                    throw new FebsException("维修日期在车辆使用日期内！");
                }
            }
        }
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
