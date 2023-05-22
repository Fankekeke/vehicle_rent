package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.VehicleInfo;
import cc.mrbird.febs.cos.service.IVehicleInfoService;
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
@RequestMapping("/cos/vehicle-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleInfoController {

    private final IVehicleInfoService vehicleInfoService;

    /**
     * 分页获取车辆信息
     *
     * @param page      分页对象
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<VehicleInfo> page, VehicleInfo vehicleInfo) {
        return R.ok(vehicleInfoService.selectVehiclePage(page, vehicleInfo));
    }

    /**
     * 获取ID获取车辆详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(vehicleInfoService.getById(id));
    }

    /**
     * 获取车辆信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(vehicleInfoService.list());
    }

    /**
     * 新增车辆信息
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @PostMapping
    public R save(VehicleInfo vehicleInfo) throws FebsException {
        vehicleInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(vehicleInfoService.vehicleAdd(vehicleInfo));
    }

    /**
     * 修改车辆信息
     *
     * @param vehicleInfo 车辆信息
     * @return 结果
     */
    @PutMapping
    public R edit(VehicleInfo vehicleInfo) {
        return R.ok(vehicleInfoService.updateById(vehicleInfo));
    }

    /**
     * 删除车辆信息
     *
     * @param ids ids
     * @return 车辆信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(vehicleInfoService.removeByIds(ids));
    }

}
