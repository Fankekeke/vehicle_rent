package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.MapUtils;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.EvaluateInfo;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoController {

    private final IOrderInfoService orderInfoService;

    private final IUserInfoService userInfoService;

    /**
     * 分页获取订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<OrderInfo> page, OrderInfo orderInfo) {
        if (orderInfo.getUserId() != null) {
            UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, orderInfo.getUserId()));
            if (userInfo != null) {
                orderInfo.setUserId(userInfo.getId());
            }
        }
        return R.ok(orderInfoService.selectOrderPage(page, orderInfo));
    }

    /**
     * 添加评价信息
     *
     * @param evaluateInfo 评价信息
     * @return 结果
     */
    @PostMapping("/orderEvaluate")
    public R orderEvaluate(EvaluateInfo evaluateInfo) {
        return R.ok(orderInfoService.orderEvaluate(evaluateInfo));
    }

    /**
     * 设置订单状态
     *
     * @param orderCode 订单编号
     * @param status    状态
     * @return 结果
     */
    @GetMapping("/audit")
    public R audit(@RequestParam("orderCode") String orderCode, @RequestParam("status") Integer status) {
        return R.ok(orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getStatus, status).eq(OrderInfo::getCode, orderCode)));
    }

    /**
     * 管理员对订单分配
     *
     * @param orderCode    订单编号
     * @param driverCode   司机编号
     * @param staffCodeStr 帮运
     * @return 结果
     */
    @GetMapping("/checkOrder")
    public R checkOrder(@RequestParam("orderCode") String orderCode, @RequestParam("driverCode") String driverCode, @RequestParam("staffCodeStr") String staffCodeStr) throws Exception {
        return R.ok(orderInfoService.checkOrder(orderCode, driverCode, staffCodeStr));
    }

    /**
     * 根据用户ID查询未完成订单
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/selectOrderByUserId/{userId}")
    public R selectOrderByUserId(@PathVariable("userId") Integer userId) {
        return R.ok(orderInfoService.selectOrderByUserId(userId));
    }

    /**
     * 计算订单价格
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @PostMapping("/calculate")
    public R calculateAmount(OrderInfo orderInfo) {
        return R.ok(orderInfoService.calculateAmount(orderInfo));
    }

    /**
     * 根据订单编号获取订单详细信息
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @GetMapping("/detail/{orderCode}")
    public R selectDetailByCode(@PathVariable("orderCode") String orderCode) {
        return R.ok(orderInfoService.selectDetailByCode(orderCode));
    }

    /**
     * 查询待分配和未完成订单
     *
     * @return 结果
     */
    @GetMapping("/selectOrderByStatus")
    public R selectOrderByStatus() {
        return R.ok(orderInfoService.selectOrderByStatus());
    }

    /**
     * 主页信息
     *
     * @return 结果
     */
    @GetMapping("/homeData")
    public R homeData(@RequestParam("userCode") String userCode) {
        return R.ok(orderInfoService.homeData(userCode));
    }

    /**
     * 获取ID获取订单详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(orderInfoService.getById(id));
    }

    /**
     * 获取订单信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(orderInfoService.list());
    }

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @PostMapping
    public R save(OrderInfo orderInfo) {
        // 获取用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, orderInfo.getUserId()));
        if (userInfo != null) {
            orderInfo.setUserId(userInfo.getId());
        }
        orderInfo.setCode("OR-" + System.currentTimeMillis());
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 计算距离
        BigDecimal distance = BigDecimal.valueOf(MapUtils.GetDistance(orderInfo.getStartLatitude().doubleValue(), orderInfo.getStartLongitude().doubleValue(), orderInfo.getEndLatitude().doubleValue(), orderInfo.getEndLongitude().doubleValue()));
        orderInfo.setDistanceLength(distance.setScale(2, RoundingMode.HALF_UP));
        LinkedHashMap<String, Object> result = orderInfoService.calculateAmountResult(orderInfo);
        orderInfoService.save(orderInfo);
        return R.ok(result);
    }

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @PutMapping
    public R edit(OrderInfo orderInfo) {
        return R.ok(orderInfoService.updateById(orderInfo));
    }

    /**
     * 删除订单信息
     *
     * @param ids ids
     * @return 订单信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(orderInfoService.removeByIds(ids));
    }

}
