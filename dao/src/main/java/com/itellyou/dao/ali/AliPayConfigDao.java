package com.itellyou.dao.ali;

import com.itellyou.model.ali.AliPayConfigModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AliPayConfigDao {

    @Select("select * from alipay_config where is_default=1 and is_enable = 1 limit 0,1")
    AliPayConfigModel getDefault();
}
