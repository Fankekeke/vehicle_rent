package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单分配
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderDistribute implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 员工编号
     */
    private String staffCode;

    /**
     * 分配时间
     */
    private String createDate;

    public OrderDistribute(String orderCode, String staffCode, String createDate) {
        this.orderCode = orderCode;
        this.staffCode = staffCode;
        this.createDate = createDate;
    }

    public OrderDistribute() {}
}
