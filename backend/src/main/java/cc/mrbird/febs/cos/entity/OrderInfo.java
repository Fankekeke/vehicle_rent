package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 租车订单
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 订单名称
     */
    private String orderName;

    /**
     * 所属用户
     */
    private Integer userId;

    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 每日租金
     */
    private BigDecimal dayPrice;

    /**
     * 开始租车时间
     */
    private LocalDate startDate;

    /**
     * 归还车辆时间
     */
    private LocalDate endDate;

    /**
     * 取车店铺
     */
    private String takeShop;

    /**
     * 归还车辆店铺
     */
    private String returnShop;

    /**
     * 总价格
     */
    private BigDecimal total;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 备注信息
     */
    private String remark;

    @TableField(exist = false)
    private String vehicleName;

    @TableField(exist = false)
    private String vehicleNumber;

}
