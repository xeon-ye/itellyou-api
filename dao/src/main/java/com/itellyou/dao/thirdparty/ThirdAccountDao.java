package com.itellyou.dao.thirdparty;

import com.itellyou.model.thirdparty.ThirdAccountModel;
import com.itellyou.model.thirdparty.ThirdAccountType;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ThirdAccountDao {
    /**
     * 新增第三方账号
     * @param model UserThirdAccountModel
     * @return 受影响行数
     */
    int insert(ThirdAccountModel model);

    /**
     * 根据用户编号和第三方账户类型删除已绑定的第三方账号
     * @param userId 用户编号
     * @param type 第三方账户类型
     * @return 受影响行数
     */
    int deleteByUserIdAndType(@Param("userId") Long userId, @Param("type") ThirdAccountType type);

    @MapKey("type")
    Map<ThirdAccountType , ThirdAccountModel> searchByUserId(Long userId);

    /**
     * 根据第三方账户类型，和KEY查询
     * @param type
     * @param key
     * @return
     */
    ThirdAccountModel searchByTypeAndKey(@Param("type") ThirdAccountType type, @Param("key") String key);
    /**
     * 查询
     * @param userId
     * @param type
     * @param beginTime
     * @param endTime
     * @param ip
     * @param order
     * @param offset
     * @param limit
     * @return
     */
    List<ThirdAccountModel> search(@Param("userId") Long userId,
                                   @Param("type") ThirdAccountType type,
                                   @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
                                   @Param("ip") Long ip,
                                   @Param("order") Map<String, String> order,
                                   @Param("offset") Integer offset,
                                   @Param("limit") Integer limit);

    /**
     * 计算查询记录条数
     * @param userId
     * @param type
     * @param beginTime
     * @param endTime
     * @param ip
     * @return
     */
    int count(@Param("userId") Long userId,
              @Param("type") ThirdAccountType type,
              @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
              @Param("ip") Long ip);
}
