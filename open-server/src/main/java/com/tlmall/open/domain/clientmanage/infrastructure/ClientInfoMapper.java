package com.tlmall.open.domain.clientmanage.infrastructure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tlmall.open.domain.clientmanage.entity.ClientInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author loulan
 * @desc
 */
@Mapper
public interface ClientInfoMapper extends BaseMapper<ClientInfo> {
}
