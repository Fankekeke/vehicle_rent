package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PaymentRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

    /**
     * 分页获取付款记录信息
     *
     * @param page          分页对象
     * @param paymentRecord 付款记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectPaymentPage(Page<PaymentRecord> page, @Param("paymentRecord") PaymentRecord paymentRecord);
}
