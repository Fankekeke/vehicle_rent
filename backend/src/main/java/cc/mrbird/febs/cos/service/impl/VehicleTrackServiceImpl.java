package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.VehicleTrack;
import cc.mrbird.febs.cos.dao.VehicleTrackMapper;
import cc.mrbird.febs.cos.service.IVehicleTrackService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class VehicleTrackServiceImpl extends ServiceImpl<VehicleTrackMapper, VehicleTrack> implements IVehicleTrackService {

    /**
     * 分页获取车辆轨迹信息
     *
     * @param page      分页对象
     * @param vehicleTrack 车辆轨迹信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectVehicleTrackPage(Page<VehicleTrack> page, VehicleTrack vehicleTrack) {
        return baseMapper.selectVehicleTrackPage(page, vehicleTrack);
    }
}
