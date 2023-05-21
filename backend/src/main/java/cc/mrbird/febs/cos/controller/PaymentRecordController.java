package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IPaymentRecordService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/payment-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentRecordController {

    private final IPaymentRecordService paymentRecordService;

    private final IUserInfoService userInfoService;

    private final IOrderInfoService orderInfoService;

    /**
     * 分页获取付款记录信息
     *
     * @param page          分页对象
     * @param paymentRecord 付款记录信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PaymentRecord> page, PaymentRecord paymentRecord) {
        if (paymentRecord.getUserId() != null) {
            UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, paymentRecord.getUserId()));
            if (userInfo != null) {
                paymentRecord.setUserCode(userInfo.getCode());
            }
        }
        return R.ok(paymentRecordService.selectPaymentPage(page, paymentRecord));
    }

    /**
     * 获取ID获取付款记录详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(paymentRecordService.getById(id));
    }

    /**
     * 获取付款记录信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(paymentRecordService.list());
    }

    /**
     * 新增付款记录信息
     *
     * @param userId    用户ID
     * @param orderCode 订单编号
     * @return 结果
     */
    @GetMapping("/savePaymentRecord")
    @Transactional(rollbackFor = Exception.class)
    public R savePaymentRecord(String userId, String orderCode) {
        PaymentRecord paymentRecord = new PaymentRecord();
        // 获取用户信息
        UserInfo userInfo = userInfoService.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        // 订单信息
        OrderInfo orderInfo = orderInfoService.getOne(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getCode, orderCode));
        orderInfoService.audit(orderCode, 1);
        paymentRecord.setUserCode(userInfo.getCode());
        paymentRecord.setOrderCode(orderCode);
        paymentRecord.setAmount(orderInfo.getAmount());
        paymentRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(paymentRecordService.save(paymentRecord));
    }

    /**
     * 新增付款记录信息
     *
     * @param paymentRecord 付款记录信息
     * @return 结果
     */
    @PostMapping
    public R save(PaymentRecord paymentRecord) {
        paymentRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(paymentRecordService.save(paymentRecord));
    }

    /**
     * 修改付款记录信息
     *
     * @param paymentRecord 付款记录信息
     * @return 结果
     */
    @PutMapping
    public R edit(PaymentRecord paymentRecord) {
        return R.ok(paymentRecordService.updateById(paymentRecord));
    }

    /**
     * 删除付款记录信息
     *
     * @param ids ids
     * @return 付款记录信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(paymentRecordService.removeByIds(ids));
    }

}
