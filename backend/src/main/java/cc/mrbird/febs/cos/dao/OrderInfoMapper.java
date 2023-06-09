package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 分页获取订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, @Param("orderInfo") OrderInfo orderInfo);

    /**
     * 根据时间获取订单信息
     *
     * @param year  年份
     * @param month 月份
     * @return 结果
     */
    List<OrderInfo> selectOrderByDate(@Param("year") String year, @Param("month") String month);

    /**
     * 近十天收益统计
     *
     * @param shopCode 车店编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectPriceByDay(@Param("shopCode") String shopCode);

    /**
     * 近十天工单统计
     *
     * @param shopCode 车店编号
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderNumByDay(@Param("shopCode") String shopCode);

    /**
     * 维修统计
     *
     * @param year  年份
     * @param month 月份
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRepairMonth(@Param("year") String year, @Param("month") String month);
}
