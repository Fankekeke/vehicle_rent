package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.dao.PaymentRecordMapper;
import cc.mrbird.febs.cos.service.IPaymentRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author FanK
 */
@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements IPaymentRecordService {

}
