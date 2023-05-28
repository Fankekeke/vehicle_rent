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
 * 车店管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShopInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 车店编号
     */
    private String shopAddress;

    /**
     * 车店名称
     */
    private String name;

    /**
     * 编号
     */
    private String code;

    /**
     * 图片
     */
    private String images;

    /**
     * 所属账户
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 标识 0.休息 1.营业
     */
    private String delFlag;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;


}
