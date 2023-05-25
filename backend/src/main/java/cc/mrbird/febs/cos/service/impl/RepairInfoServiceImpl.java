package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.cos.dao.VehicleInfoMapper;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.entity.VehicleInfo;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import cc.mrbird.febs.cos.service.IVehicleInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements IRepairInfoService {

    @Resource
    private IVehicleInfoService vehicleInfoService;

    private static final String STR_ONE = "1";
    private static final String STR_TWO = "2";
    private static final String STR_THREE = "3";

    /**
     * 分页获取维修信息信息
     *
     * @param page      分页对象
     * @param repairInfo 维修信息信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectRepairPage(Page<RepairInfo> page, RepairInfo repairInfo) {
        return baseMapper.selectRepairPage(page, repairInfo);
    }

    /**
     * 新增维修信息信息
     *
     * @param repairInfo 维修信息信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean repairAdd(RepairInfo repairInfo) throws FebsException {
        // 维修车辆是否已在维修记录中
        int count = this.count(Wrappers.<RepairInfo>lambdaQuery().eq(RepairInfo::getRepairStatus, 0).eq(RepairInfo::getVehicleNo, repairInfo.getVehicleNo()));
        if (count > 0) {
            throw new FebsException("当前车辆正在维修！");
        }

        // 判断当前日期是否在维修阶段内
        boolean isIn = DateUtil.isIn(new Date(), DateUtil.parseDate(repairInfo.getRepairStart()), DateUtil.parseDate(repairInfo.getRepairEnd()));
        // 更新车辆状态
        if (isIn) {
            vehicleInfoService.update(Wrappers.<VehicleInfo>lambdaUpdate().set(VehicleInfo::getStatus, STR_ONE).eq(VehicleInfo::getVehicleNo, repairInfo.getVehicleNo()));
        }
        return this.save(repairInfo);
    }
}
