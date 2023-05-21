package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.entity.OrderDistribute;
import cc.mrbird.febs.cos.dao.OrderDistributeMapper;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.service.IOrderDistributeService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class OrderDistributeServiceImpl extends ServiceImpl<OrderDistributeMapper, OrderDistribute> implements IOrderDistributeService {

    /**
     * 设置订单员工
     *
     * @param staffListStr 员工编号
     * @param orderCode    订单编号
     * @return 结果
     */
    @Override
    public boolean setOrderStaff(String staffListStr, String orderCode) throws Exception {
        if (StrUtil.isEmpty(staffListStr)) {
            throw new FebsException("员工编号不能为空");
        }
        List<String> staffCodeList = StrUtil.splitTrim(staffListStr, ",");
        // 删除旧订单绑定
        this.remove(Wrappers.<OrderDistribute>lambdaQuery().eq(OrderDistribute::getOrderCode, orderCode));
        // 重新绑定
        List<OrderDistribute> orderDistributeList = new ArrayList<>();
        staffCodeList.forEach(e -> {
            orderDistributeList.add(new OrderDistribute(orderCode, e, DateUtil.formatDateTime(new Date())));
        });
        return this.saveBatch(orderDistributeList);
    }
}
