package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.VehicleTrack;
import cc.mrbird.febs.cos.service.IVehicleTrackService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/vehicle-track")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleTrackController {

    private final IVehicleTrackService vehicleTrackService;

    /**
     * 分页获取车辆轨迹信息
     *
     * @param page      分页对象
     * @param vehicleTrack 车辆轨迹信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<VehicleTrack> page, VehicleTrack vehicleTrack) {
        return R.ok();
    }

    /**
     * 获取ID获取车辆轨迹详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(vehicleTrackService.getById(id));
    }

    /**
     * 获取车辆轨迹信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(vehicleTrackService.list());
    }

    /**
     * 新增车辆轨迹信息
     *
     * @param vehicleTrack 车辆轨迹信息
     * @return 结果
     */
    @PostMapping
    public R save(VehicleTrack vehicleTrack) {
        return R.ok(vehicleTrackService.save(vehicleTrack));
    }

    /**
     * 修改车辆轨迹信息
     *
     * @param vehicleTrack 车辆轨迹信息
     * @return 结果
     */
    @PutMapping
    public R edit(VehicleTrack vehicleTrack) {
        return R.ok(vehicleTrackService.updateById(vehicleTrack));
    }

    /**
     * 删除车辆轨迹信息
     *
     * @param ids ids
     * @return 车辆轨迹信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(vehicleTrackService.removeByIds(ids));
    }
    
}
