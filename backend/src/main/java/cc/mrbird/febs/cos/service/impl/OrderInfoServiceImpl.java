package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.StaffInfoMapper;
import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.service.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final IOrderDistributeService orderDistributeService;

    private final IStaffInfoService staffInfoService;

    private final IBulletinInfoService bulletinInfoService;

    private final UserInfoMapper userInfoMapper;

    private final IPaymentRecordService paymentRecordService;

    private final INotifyInfoService notifyInfoService;

    private final StaffInfoMapper staffInfoMapper;

    private final IEvaluateInfoService evaluateInfoService;

    private final IPriceRulesService priceRulesService;

    /**
     * 分页获取订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, OrderInfo orderInfo) {
        return baseMapper.selectOrderPage(page, orderInfo);
    }

    /**
     * 设置订单状态
     *
     * @param orderCode 订单编号
     * @param status    状态
     * @return 结果
     */
    @Override
    public boolean audit(String orderCode, Integer status) {
        // 获取订单信息
        OrderInfo orderInfo = this.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCode, orderCode));
        // 用户信息
        UserInfo userInfo = userInfoMapper.selectById(orderInfo.getUserId());
        NotifyInfo notifyInfo = new NotifyInfo(userInfo.getCode(), 0, DateUtil.formatDateTime(new Date()), userInfo.getName());
        switch (status) {
            case 1:
                notifyInfo.setContent("你好【" + orderInfo.getCode() + "】，此订单已付款，正在等待管理员分配人员");
                break;
            case 2:
                notifyInfo.setContent("你好【" + orderInfo.getCode() + "】，此订单管理员已分配完成，请等待");
                break;
            case 3:
                notifyInfo.setContent("你好【" + orderInfo.getCode() + "】，此订单已经完成，请进行评价");
                break;
            default:
        }
        notifyInfoService.save(notifyInfo);
        return this.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getStatus, status).eq(OrderInfo::getCode, orderCode));
    }

    /**
     * 管理员对订单分配
     *
     * @param orderCode    订单编号
     * @param driverCode   司机编号
     * @param staffCodeStr 帮运
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkOrder(String orderCode, String driverCode, String staffCodeStr) throws Exception {
        // 获取订单信息
        OrderInfo orderInfo = this.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCode, orderCode));
        if (orderInfo == null) {
            return false;
        }
        // 绑定司机1
        if (StrUtil.isNotEmpty(driverCode) && orderInfo.getVehicleOptions() != null) {
            orderInfo.setDriverCode(driverCode);
        }
        this.updateById(orderInfo);
        // 绑定搬运工
        if (StrUtil.isNotEmpty(staffCodeStr)) {
            orderDistributeService.setOrderStaff(staffCodeStr, orderCode);
        }
        return this.audit(orderCode, 2);
    }

    /**
     * 添加评价信息1
     *
     * @param evaluateInfo 评价信息
     * @return 结果
     */
    @Override
    public boolean orderEvaluate(EvaluateInfo evaluateInfo) {
        // 设置用户编号1
        OrderInfo orderInfo = this.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCode, evaluateInfo.getOrderCode()));
        UserInfo userInfo = userInfoMapper.selectById(orderInfo.getUserId());
        if (userInfo != null) {
            evaluateInfo.setUserCode(userInfo.getCode());
        }
        evaluateInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 计算综合得分
        if (evaluateInfo.getDeliverScore() == null) {
            evaluateInfo.setDeliverScore(BigDecimal.valueOf(80));
        }
        if (evaluateInfo.getPriceScore() == null) {
            evaluateInfo.setPriceScore(BigDecimal.valueOf(80));
        }
        if (evaluateInfo.getQualityScore() == null) {
            evaluateInfo.setQualityScore(BigDecimal.valueOf(80));
        }
        if (evaluateInfo.getScheduleScore() == null) {
            evaluateInfo.setScheduleScore(BigDecimal.valueOf(80));
        }
        if (evaluateInfo.getServiceScore() == null) {
            evaluateInfo.setServiceScore(BigDecimal.valueOf(80));
        }
        // 综合得分公式【(交付得分 + 价格得分 + 质量得分 + 准时得分 + 服务得分) / 5 】
        String expression = "(交付得分 + 价格得分 + 质量得分 + 准时得分 + 服务得分) / 5";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<>();
        env.put("交付得分", evaluateInfo.getDeliverScore());
        env.put("价格得分", evaluateInfo.getPriceScore());
        env.put("质量得分", evaluateInfo.getQualityScore());
        env.put("准时得分", evaluateInfo.getScheduleScore());
        env.put("服务得分", evaluateInfo.getServiceScore());
        evaluateInfo.setOverScore(new BigDecimal(compiledExp.execute(env).toString()));
        return evaluateInfoService.save(evaluateInfo);
    }

    /**
     * 计算订单价格
     * 价格公式【基础金额 + (距离 * 距离单价) + 配送车辆金额 + (配送员数量 * 配送员金额) + 无电梯费用】
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public BigDecimal calculateAmount(OrderInfo orderInfo) {
        // 获取价格计算规则
        List<PriceRules> priceRules = priceRulesService.list();
        // 规则根据编号转MAP
        Map<String, BigDecimal> rulesMap = priceRules.stream().collect(Collectors.toMap(PriceRules::getCode, PriceRules::getUnitPrice));
        BigDecimal vehiclePrice = BigDecimal.ZERO;
        switch (orderInfo.getVehicleOptions()) {
            case 1:
                vehiclePrice = rulesMap.get("LARGE_VEHICLE");
                break;
            case 2:
                vehiclePrice = rulesMap.get("MEDIUM_VEHICLE");
                break;
            case 3:
                vehiclePrice = rulesMap.get("SMALL_VEHICLE");
                break;
            default:
        }
        // 价格公式
        String expression = "基础金额 + (距离 * 距离单价) + 配送车辆金额 + (配送员数量 * 配送员金额) + 无电梯费用";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<>();
        env.put("基础金额", rulesMap.get("BASE_PRICE"));
        env.put("距离", orderInfo.getDistanceLength());
        env.put("距离单价", rulesMap.get("DISTANCE_PRICE"));
        env.put("配送车辆金额", vehiclePrice);
        env.put("配送员数量", orderInfo.getStaffOptions() != null ? orderInfo.getStaffOptions() : 0);
        env.put("配送员金额", rulesMap.get("STAFF_PRICE"));
        env.put("无电梯费用", rulesMap.get("NOT_ELEVATOR"));
        return new BigDecimal(compiledExp.execute(env).toString());
    }

    /**
     * 计算订单价格
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> calculateAmountResult(OrderInfo orderInfo) {
        // 获取价格计算规则
        List<PriceRules> priceRules = priceRulesService.list();
        // 规则根据编号转MAP
        Map<String, BigDecimal> rulesMap = priceRules.stream().collect(Collectors.toMap(PriceRules::getCode, PriceRules::getUnitPrice));
        BigDecimal vehiclePrice = BigDecimal.ZERO;
        switch (orderInfo.getVehicleOptions()) {
            case 1:
                vehiclePrice = rulesMap.get("LARGE_VEHICLE");
                break;
            case 2:
                vehiclePrice = rulesMap.get("MEDIUM_VEHICLE");
                break;
            case 3:
                vehiclePrice = rulesMap.get("SMALL_VEHICLE");
                break;
            default:
        }
        // 价格公式
        String expression = "基础金额 + (距离 * 距离单价) + 配送车辆金额 + (配送员数量 * 配送员金额) + 无电梯费用";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<>();
        env.put("基础金额", rulesMap.get("BASE_PRICE"));
        env.put("距离", orderInfo.getDistanceLength());
        env.put("距离单价", rulesMap.get("DISTANCE_PRICE"));
        env.put("配送车辆金额", vehiclePrice);
        env.put("配送员数量", orderInfo.getStaffOptions() != null ? orderInfo.getStaffOptions() : 0);
        env.put("配送员金额", rulesMap.get("STAFF_PRICE"));
        env.put("无电梯费用", rulesMap.get("NOT_ELEVATOR"));
        orderInfo.setAmount(new BigDecimal(compiledExp.execute(env).toString()));
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("base", rulesMap.get("BASE_PRICE"));
                put("distanceLength", orderInfo.getDistanceLength());
                put("distancePrice", rulesMap.get("DISTANCE_PRICE"));
                put("staffNum", orderInfo.getStaffOptions() != null ? orderInfo.getStaffOptions() : 0);
                put("staffPrice", rulesMap.get("STAFF_PRICE"));
                put("elevator", rulesMap.get("NOT_ELEVATOR"));
                put("amount", orderInfo.getAmount());
                put("orderCode", orderInfo.getCode());
            }
        };
        result.put("vehiclePrice", vehiclePrice);
        return result;
    }

    /**
     * 根据订单编号获取订单详细信息
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectDetailByCode(String orderCode) {
        if (StrUtil.isEmpty(orderCode)) {
            return null;
        }
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 订单信息
        OrderInfo orderInfo = this.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCode, orderCode));
        result.put("order", orderInfo);
        // 员工信息
        List<OrderDistribute> orderDistributeList = orderDistributeService.list(Wrappers.<OrderDistribute>lambdaQuery().eq(OrderDistribute::getOrderCode, orderCode));
        List<String> staffCodes = new ArrayList<>();
        if (StrUtil.isNotEmpty(orderInfo.getDriverCode())) {
            staffCodes.add(orderInfo.getDriverCode());
        }
        if (CollectionUtil.isNotEmpty(orderDistributeList)) {
            orderDistributeList.forEach(e -> {
                staffCodes.add(e.getStaffCode());
            });
        }
        List<StaffInfo> staffInfoList = staffInfoService.list(Wrappers.<StaffInfo>lambdaQuery().in(CollectionUtil.isNotEmpty(staffCodes), StaffInfo::getCode, staffCodes));
        result.put("staff", CollectionUtil.isEmpty(staffCodes) ? Collections.emptyList() : staffInfoList);
        // 评价
        EvaluateInfo evaluate = evaluateInfoService.getOne(Wrappers.<EvaluateInfo>lambdaQuery().eq(EvaluateInfo::getOrderCode, orderCode));
        result.put("evaluate", evaluate);
        // 用户信息
        UserInfo userInfo = userInfoMapper.selectById(orderInfo.getUserId());
        result.put("user", userInfo);
        return result;
    }

    /**
     * 根据用户ID查询未完成订单
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public List<OrderInfo> selectOrderByUserId(Integer userId) {
        // 查询用户信息
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        if (userInfo == null) {
            return Collections.emptyList();
        }
        return this.list(Wrappers.<OrderInfo>lambdaQuery().ne(OrderInfo::getStatus, 3).eq(OrderInfo::getUserId, userInfo.getId()));
    }

    /**
     * 查询待分配和未完成订单
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectOrderByStatus() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 查询所有未分配订单数据
        List<LinkedHashMap<String, Object>> unassigned = baseMapper.selectOrderByStatus(1);
        result.put("unassigned", unassigned);
        // 查询所有正在进行的订单数据
        List<LinkedHashMap<String, Object>> progress = baseMapper.selectOrderByStatus(2);
        progress.forEach(e -> {
            // 查询搬运工,驾驶员信息
            e.put("staff", staffInfoMapper.selectStaffByOrder(e.get("code").toString()));
        });
        result.put("progress", progress);
        return result;
    }

    /**
     * 主页信息
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData(String userCode) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        // 查询员工信息
        List<StaffInfo> staffInfoList = staffInfoService.list(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getStatus, 1));
        Map<Integer, List<StaffInfo>> staffMap = staffInfoList.stream().collect(Collectors.groupingBy(StaffInfo::getType));
        // 司机
        Integer driverNum = CollectionUtil.isEmpty(staffMap.get(1)) ? 0 : staffMap.get(1).size();
        // 搬运工
        Integer staffMoveNum = CollectionUtil.isEmpty(staffMap.get(2)) ? 0 : staffMap.get(2).size();
        // 总订单数量
        Integer orderNum = this.count();
        // 总收益
        List<PaymentRecord> paymentRecordList = paymentRecordService.list();
        BigDecimal amount = CollectionUtil.isEmpty(paymentRecordList) ? BigDecimal.ZERO : paymentRecordList.stream().map(PaymentRecord::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<OrderInfo> orderListMonth = baseMapper.selectOrderInfoByMonth();
        List<OrderInfo> orderListYear = baseMapper.selectOrderInfoByYear();
        // 本月订单量
        Integer orderNumMonth = orderListMonth.size();
        // 本月收益
        BigDecimal orderAmountMonth = orderListMonth.stream().filter(e -> e.getStatus() != 0).map(OrderInfo::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 本年订单量
        Integer orderNumYear = orderListYear.size();
        // 本年收益
        BigDecimal orderAmountYear = orderListYear.stream().filter(e -> e.getStatus() != 0).map(OrderInfo::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 近十天内订单数量统计
        List<LinkedHashMap<String, Object>> orderNumDays = baseMapper.selectOrderNumDays();
        // 近十天内订单收益统计
        List<LinkedHashMap<String, Object>> orderAmountDays = baseMapper.selectOrderAmountDays();
        // 公告
        List<BulletinInfo> bulletinInfoList = bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1));
        // 通知
        List<NotifyInfo> notifyInfoList = notifyInfoService.list(Wrappers.<NotifyInfo>lambdaQuery().eq(NotifyInfo::getUserCode, userCode));
        result.put("orderNumMonth", orderNumMonth);
        result.put("orderAmountMonth", orderAmountMonth);
        result.put("orderNumYear", orderNumYear);
        result.put("orderAmountYear", orderAmountYear);

        result.put("driverNum", driverNum);
        result.put("staffMoveNum", staffMoveNum);
        result.put("orderNum", orderNum);
        result.put("amount", amount);
        result.put("orderNumDays", orderNumDays);
        result.put("orderAmountDays", orderAmountDays);
        result.put("bulletinInfoList", bulletinInfoList);
        result.put("notifyInfoList", notifyInfoList);
        return result;
    }
}
