package cc.mrbird.febs.cos.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单评价
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderEvaluate implements Serializable {

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
     * 评分
     */
    private Integer score;

    /**
     * 评价用户
     */
    private Integer userId;

    /**
     * 评价备注
     */
    private String remark;

    /**
     * 照片
     */
    private String images;

    /**
     * 评价时间
     */
    private String createDate;

    @TableField(exist = false)
    private String orderName;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String vehicleNumber;

    @TableField(exist = false)
    private String vehicleName;

}
