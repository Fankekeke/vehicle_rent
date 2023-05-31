package cc.mrbird.febs.cos.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 车辆管理
 *
 * @author FanK
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VehicleInfo implements Serializable {

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
     * 车牌号
     */
    private String vehicleNumber;

    /**
     * 车辆颜色
     */
    private String vehicleColor;

    /**
     * 车辆名称
     */
    private String name;

    /**
     * 发动机号码
     */
    private String engineNo;

    /**
     * 载客数量
     */
    private Integer carryPassengers;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 所属品牌
     */
    private Integer brand;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 车辆状态（0.空闲 1.使用中 2.维修中 3.已报废）
     */
    private String status;

    /**
     * 出场日期
     */
    private String factoryDate;

    /**
     * 车辆类型
     */
    private Integer useType;

    /**
     * 排放标准
     */
    private String emissionStandard;

    /**
     * 燃料类型（1.燃油 2.柴油 3.油电混动 4.电能）
     */
    private String fuelType;

    /**
     * 照片
     */
    private String images;

    /**
     * 备注
     */
    private String content;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 所属车店
     */
    private Integer shopId;
}
