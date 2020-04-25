package com.itellyou.model.article;

import com.alibaba.fastjson.annotation.JSONField;
import com.itellyou.util.annotation.JSONDefault;
import com.itellyou.util.serialize.IpLongSerializer;
import com.itellyou.util.serialize.TimestampSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JSONDefault(includes = "base")
public class ArticleStarModel {
    private Long articleId;
    @JSONField(serializeUsing = TimestampSerializer.class,label = "base")
    private Long createdTime=0l;
    private Long createdUserId=0l;
    @JSONField(serializeUsing = IpLongSerializer.class)
    private Long createdIp;
}
