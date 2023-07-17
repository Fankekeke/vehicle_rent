package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.*;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cc.mrbird.febs.cos.service.IOrderInfoService;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final IBulletinInfoService bulletinInfoService;

    private final VehicleInfoMapper vehicleMapper;

    private final VehicleTypeInfoMapper vehicleTypeMapper;

    private final BrandInfoMapper brandMapper;

    private RepairInfoMapper repairInfoMapper;

    private ShopInfoMapper shopInfoMapper;

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
     * 主页数据统计
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("monthOrderNum", 0);
                put("monthOrderTotal", 0);
                put("yearOrderNum", 0);
                put("yearOrderTotal", 0);
                put("vehicleNum", 0);
                put("repairNum", 0);
                put("totalPrice", 0);
                put("totalNum", 0);
                put("shopNum", 0);
            }
        };

        // 车辆数量
        int vehicleNum = vehicleMapper.selectCount(Wrappers.lambdaQuery());
        result.put("vehicleNum", vehicleNum);

        // 维修次数
        int repairNum = repairInfoMapper.selectCount(Wrappers.lambdaQuery());
        result.put("repairNum", repairNum);

        // 总订单金额
        List<OrderInfo> orderAllList = this.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getStatus, "1"));
        BigDecimal totalPrice = orderAllList.stream().map(OrderInfo::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalPrice", totalPrice);

        // 总订单数量
        int totalNum = orderAllList.size();
        result.put("totalNum", totalNum);

        // 车店数量
        int shopNum = shopInfoMapper.selectCount(Wrappers.lambdaQuery());
        result.put("shopNum", shopNum);

        // 获取当前月份及当前月份
        String year = StrUtil.toString(DateUtil.year(new Date()));
        String month = StrUtil.toString(DateUtil.month(new Date()));

        List<OrderInfo> orderList = baseMapper.selectOrderByDate(year, null);
        if (CollectionUtil.isNotEmpty(orderList)) {
            Map<String, List<OrderInfo>> orderMonthMap = orderList.stream().collect(Collectors.groupingBy(OrderInfo::getMonth));
            Map<String, List<OrderInfo>> orderYearMap = orderList.stream().collect(Collectors.groupingBy(OrderInfo::getYear));

            List<OrderInfo> currentMonthList = orderMonthMap.get(month);
            List<OrderInfo> currentYearList = orderYearMap.get(month);
            // 本月订单量
            result.put("monthOrderNum", CollectionUtil.isNotEmpty(currentMonthList) ? currentMonthList.size() : 0);
            // 本月收益
            result.put("monthOrderTotal", CollectionUtil.isNotEmpty(currentMonthList) ? currentMonthList.stream().map(OrderInfo::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO);
            // 本年订单量
            result.put("yearOrderNum", CollectionUtil.isNotEmpty(currentYearList) ? currentYearList.size() : 0);
            // 本年收益
            result.put("yearOrderTotal", CollectionUtil.isNotEmpty(currentYearList) ? currentYearList.stream().map(OrderInfo::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO);
        }

        // 近十天收益统计
        List<LinkedHashMap<String, Object>> priceDayList = baseMapper.selectPriceByDay(null);
        result.put("priceDayList", priceDayList);
        // 近十天工单统计
        List<LinkedHashMap<String, Object>> orderNumDayList = baseMapper.selectOrderNumByDay(null);
        result.put("orderNumDayList", orderNumDayList);
        // 本月维修统计
        List<LinkedHashMap<String, Object>> repairRate = baseMapper.selectRepairMonth(year, month);
        result.put("repairRate", repairRate);

        List<VehicleInfo> vehicleList = vehicleMapper.selectList(Wrappers.<VehicleInfo>lambdaQuery().ne(VehicleInfo::getStatus, "3"));

        List<VehicleTypeInfo> typeList = vehicleTypeMapper.selectList(Wrappers.lambdaQuery());
        Map<Integer, String> typeMap = typeList.stream().collect(Collectors.toMap(VehicleTypeInfo::getId, VehicleTypeInfo::getName));
        // 车辆类型统计
        Map<String, Object> vehicleTypeRate = new HashMap<>(16);
        Map<Integer, List<VehicleInfo>> vehicleTypeMap = vehicleList.stream().collect(Collectors.groupingBy(VehicleInfo::getUseType));
        typeMap.forEach((key, value) -> {
            List<VehicleInfo> tempList = vehicleTypeMap.get(key);
            vehicleTypeRate.put(value, CollectionUtil.isNotEmpty(tempList) ? tempList.size() : 0);
        });
        result.put("vehicleTypeRate", vehicleTypeRate);

        List<BrandInfo> brandList = brandMapper.selectList(Wrappers.lambdaQuery());
        Map<Integer, String> brandMap = brandList.stream().collect(Collectors.toMap(BrandInfo::getId, BrandInfo::getName));
        // 车辆品牌统计
        Map<String, Object> vehicleBrandRate = new HashMap<>(16);
        Map<Integer, List<VehicleInfo>> vehicleBrandMap = vehicleList.stream().collect(Collectors.groupingBy(VehicleInfo::getBrand));
        brandMap.forEach((key, value) -> {
            List<VehicleInfo> tempList = vehicleBrandMap.get(key);
            vehicleBrandRate.put(value, CollectionUtil.isNotEmpty(tempList) ? tempList.size() : 0);
        });
        result.put("vehicleBrandRate", vehicleBrandRate);

        // 燃油类型统计 1.燃油 2.柴油 3.油电混动 4.电能
        Map<String, Object> vehicleFuelRate = new HashMap<>(16);
        Map<String, List<VehicleInfo>> vehicleFuelMap = vehicleList.stream().collect(Collectors.groupingBy(VehicleInfo::getFuelType));
        Map<String, String> fuelMap = new HashMap<String, String>() {
            {
                put("1", "燃油");
                put("2", "柴油");
                put("3", "油电混动");
                put("4", "电能");
            }
        };
        fuelMap.forEach((key, value) -> {
            List<VehicleInfo> tempList = vehicleFuelMap.get(key);
            vehicleFuelRate.put(value, CollectionUtil.isNotEmpty(tempList) ? tempList.size() : 0);
        });
        result.put("vehicleFuelRate", vehicleFuelRate);

        // 公告信息
        result.put("bulletinInfoList", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));
        return result;
    }
}
