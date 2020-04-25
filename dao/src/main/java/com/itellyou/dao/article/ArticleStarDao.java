package com.itellyou.dao.article;

import com.itellyou.model.article.ArticleStarDetailModel;
import com.itellyou.model.article.ArticleStarModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ArticleStarDao {
    int insert(ArticleStarModel model);
    int delete(@Param("articleId") Long articleId, @Param("userId") Long userId);
    List<ArticleStarDetailModel> search(@Param("articleId") Long articleId,
                                        @Param("userId") Long userId,
                                        @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
                                        @Param("ip") Long ip,
                                        @Param("order") Map<String, String> order,
                                        @Param("offset") Integer offset,
                                        @Param("limit") Integer limit);
    int count(@Param("articleId") Long articleId,
              @Param("userId") Long userId,
              @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
              @Param("ip") Long ip);
}
