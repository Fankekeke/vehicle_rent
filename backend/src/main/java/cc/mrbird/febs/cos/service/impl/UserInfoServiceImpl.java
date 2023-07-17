package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.dao.UserInfoMapper;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IUserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    private final IOrderInfoService orderInfoService;

    /**
     * 分页获取用户信息
     *
     * @param page      分页对象
     * @param userInfo 用户信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectUserPage(Page<UserInfo> page, UserInfo userInfo) {
        return baseMapper.selectUserPage(page, userInfo);
    }

    /**
     * 根据用户ID用户信息及订单
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectUserByUserId(Integer userId) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("user", null);
                put("order", null);
            }
        };
        UserInfo user = this.getOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUserId, userId));
        result.put("user", user);

        // 订单
        List<OrderInfo> orderList = orderInfoService.list(Wrappers.<OrderInfo>lambdaQuery().eq(OrderInfo::getUserId, user.getId()));
        result.put("order", orderList);
        return result;
    }
}
