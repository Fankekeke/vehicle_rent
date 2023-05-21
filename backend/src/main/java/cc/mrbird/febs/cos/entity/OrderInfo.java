package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单信息
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
     * 用户ID
     */
    private Integer userId;

    /**
     * 是否需要车辆（1.大型车 2.中型车 3.小型车）
     */
    private Integer vehicleOptions;

    /**
     * 是否需要搬运工（0.不需要 1.一个 2.两个 3.三个）
     */
    private Integer staffOptions;

    /**
     * 起始地址
     */
    private String startAddress;

    /**
     * 运输地址
     */
    private String endAddress;

    /**
     * 初始经度
     */
    private BigDecimal startLongitude;

    /**
     * 初始纬度
     */
    private BigDecimal startLatitude;

    /**
     * 运输经度
     */
    private BigDecimal endLongitude;

    /**
     * 运输纬度
     */
    private BigDecimal endLatitude;

    /**
     * 距离（千米）
     */
    private BigDecimal distanceLength;

    /**
     * 图册
     */
    private String images;

    /**
     * 订单状态（0.未支付 1.等待分配 2.正在赶往 3.运输完成）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 价格
     */
    private BigDecimal amount;

    /**
     * 司机编号
     */
    private String driverCode;

    /**
     * 是否有电梯（0：无 1：有）
     */
    private Integer hasElevator;

    @TableField(exist = false)
    private String userName;

    private String remark;
}
