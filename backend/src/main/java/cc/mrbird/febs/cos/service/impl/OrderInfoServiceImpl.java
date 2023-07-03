package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.dao.OrderInfoMapper;
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

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private IBulletinInfoService bulletinInfoService;

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
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData(Integer userId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        // 获取当前月份及当前月份
        String year = StrUtil.toString(DateUtil.year(new Date()));
        String month = StrUtil.toString(DateUtil.month(new Date()));

        List<OrderInfo> orderList = baseMapper.selectOrderByDate(year, null);
        if (CollectionUtil.isNotEmpty(orderList)) {
            Map<String, List<OrderInfo>> orderMonthMap = orderList.stream().collect(Collectors.groupingBy(OrderInfo::getMonth));
            Map<String, List<OrderInfo>> orderYearMap = orderList.stream().collect(Collectors.groupingBy(OrderInfo::getYear));

            List<OrderInfo> currentMonthList = orderMonthMap.get(month);
            // 本月订单量
            result.put("monthOrderNum", CollectionUtil.isNotEmpty(currentMonthList) ? currentMonthList.size() : 0);
            // 本月收益
            result.put("monthOrderTotal", CollectionUtil.isNotEmpty(currentMonthList) ? currentMonthList.size() : 0);
            // 本年订单量

            // 本年收益
        }

        // 近十天收益统计

        // 近十天工单统计

        // 本月维修统计

        // 车辆类型统计

        // 公告信息
        result.put("bulletinInfoList", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery().eq(BulletinInfo::getRackUp, 1)));
        return result;
    }
}
