package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 车辆类型
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VehicleTypeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类型编号
     */
    private String code;

    /**
     * 车辆类型名称
     */
    private String name;

    /**
     * 车门数
     */
    private Integer doors;

    /**
     * 车座数
     */
    private Integer seats;

    /**
     * 适合行李数
     */
    private String luggage;

    /**
     * 出行类别
     */
    private String travelCategory;

    /**
     * 创建时间
     */
    private String createDate;


}
