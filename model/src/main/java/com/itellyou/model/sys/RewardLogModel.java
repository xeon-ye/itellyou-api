package com.itellyou.model.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.itellyou.model.user.UserBankType;
import com.itellyou.util.CacheEntity;
import com.itellyou.util.DateUtils;
import com.itellyou.util.annotation.JSONDefault;
import com.itellyou.util.serialize.EnumSerializer;
import com.itellyou.util.serialize.IpDeserializer;
import com.itellyou.util.serialize.IpSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JSONDefault(includes = "base")
public class RewardLogModel implements CacheEntity {
    @JSONField(label = "base")
    private Long id;
    @JSONField(label = "base",serializeUsing = EnumSerializer.class , deserializeUsing = EnumSerializer.class)
    private UserBankType bankType;
    @JSONField(label = "base",serializeUsing = EnumSerializer.class , deserializeUsing = EnumSerializer.class)
    private EntityType dataType;
    @JSONField(label = "base")
    private Long dataKey;
    @JSONField(label = "base")
    private Double amount;
    private Long userId;
    private Long createdUserId = 0l;
    @JSONField(label = "base")
    private LocalDateTime createdTime = DateUtils.toLocalDateTime();
    @JSONField(serializeUsing = IpSerializer.class,deserializeUsing = IpDeserializer.class)
    private Long createdIp = 0l;

    @Override
    public Long cacheKey() {
        return id;
    }
}
