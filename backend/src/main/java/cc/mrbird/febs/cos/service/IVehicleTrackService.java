package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.VehicleTrack;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IVehicleTrackService extends IService<VehicleTrack> {

    /**
     * 分页获取车辆轨迹信息
     *
     * @param page      分页对象
     * @param vehicleTrack 车辆轨迹信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectVehicleTrackPage(Page<VehicleTrack> page, VehicleTrack vehicleTrack);
}
