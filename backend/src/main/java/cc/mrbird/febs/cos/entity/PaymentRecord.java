package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 缴费记录
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PaymentRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属订单
     */
    private Integer orderId;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 缴费状态（0.未付款 1.已付款）
     */
    private String payStatus;

    /**
     * 下单时间
     */
    private String createDate;

    /**
     * 付款时间
     */
    private String payTime;


}
