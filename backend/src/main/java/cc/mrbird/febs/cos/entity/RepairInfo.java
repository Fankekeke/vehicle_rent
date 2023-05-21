package cc.mrbird.febs.cos.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 车辆维修
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RepairInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 车辆编号
     */
    private String vehicleNo;

    /**
     * 所属车厂
     */
    private String shopCode;

    /**
     * 维修原因
     */
    private String reason;

    /**
     * 维修金额
     */
    private BigDecimal price;

    /**
     * 办理人
     */
    private String chargePerson;

    /**
     * 维修开始时间
     */
    private String repairStart;

    /**
     * 维修结束时间
     */
    private String repairEnd;

    /**
     * 维修店名称
     */
    private String repairShop;

    /**
     * 维修店地址
     */
    private String shopAddress;

    /**
     * 备注
     */
    private String content;

    /**
     * 创建时间
     */
    private String createDate;


}
