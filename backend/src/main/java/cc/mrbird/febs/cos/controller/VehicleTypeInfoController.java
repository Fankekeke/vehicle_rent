package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.VehicleTypeInfo;
import cc.mrbird.febs.cos.service.IVehicleTypeInfoService;
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
@RequestMapping("/cos/vehicle-type-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleTypeInfoController {

    private final IVehicleTypeInfoService vehicleTypeInfoService;

    /**
     * 分页获取车辆类型信息
     *
     * @param page            分页对象
     * @param vehicleTypeInfo 车辆类型信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<VehicleTypeInfo> page, VehicleTypeInfo vehicleTypeInfo) {
        return R.ok(vehicleTypeInfoService.selectVehicleTypePage(page, vehicleTypeInfo));
    }

    /**
     * 获取ID获取车辆类型详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(vehicleTypeInfoService.getById(id));
    }

    /**
     * 获取车辆类型信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(vehicleTypeInfoService.list());
    }

    /**
     * 新增车辆类型信息
     *
     * @param vehicleTypeInfo 车辆类型信息
     * @return 结果
     */
    @PostMapping
    public R save(VehicleTypeInfo vehicleTypeInfo) {
        vehicleTypeInfo.setCode("TP-" + System.currentTimeMillis());
        vehicleTypeInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(vehicleTypeInfoService.save(vehicleTypeInfo));
    }

    /**
     * 修改车辆类型信息
     *
     * @param vehicleTypeInfo 车辆类型信息
     * @return 结果
     */
    @PutMapping
    public R edit(VehicleTypeInfo vehicleTypeInfo) {
        return R.ok(vehicleTypeInfoService.updateById(vehicleTypeInfo));
    }

    /**
     * 删除车辆类型信息
     *
     * @param ids ids
     * @return 车辆类型信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(vehicleTypeInfoService.removeByIds(ids));
    }


}
