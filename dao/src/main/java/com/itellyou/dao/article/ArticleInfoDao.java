package com.itellyou.dao.article;

import com.itellyou.model.article.ArticleInfoModel;
import com.itellyou.model.article.ArticleSourceType;
import com.itellyou.model.article.ArticleTotalModel;
import com.itellyou.model.common.DataUpdateStepModel;
import com.itellyou.model.sys.VoteType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ArticleInfoDao {
    int insert(ArticleInfoModel infoModel);

    /**
     * 批量增加计数，请确保id必须已存在
     * @param models
     * @return
     */
    int addStep(@Param("models") DataUpdateStepModel... models);

    List<ArticleInfoModel> search(@Param("ids") Collection<Long> ids, @Param("mode") String mode, @Param("columnId") Long columnId, @Param("userId") Long userId,
                                    @Param("sourceType") ArticleSourceType sourceType,
                                    @Param("isDisabled") Boolean isDisabled, @Param("isPublished") Boolean isPublished, @Param("isDeleted") Boolean isDeleted,
                                    @Param("minComment") Integer minComment, @Param("maxComment") Integer maxComment,
                                    @Param("minView") Integer minView, @Param("maxView") Integer maxView,
                                    @Param("minSupport") Integer minSupport, @Param("maxSupport") Integer maxSupport,
                                    @Param("minOppose") Integer minOppose, @Param("maxOppose") Integer maxOppose,
                                    @Param("minStar") Integer minStar, @Param("maxStar") Integer maxStar,
                                    @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
                                    @Param("ip") Long ip,
                                    @Param("order") Map<String, String> order,
                                    @Param("offset") Integer offset,
                                    @Param("limit") Integer limit);
    int count(@Param("ids") Collection<Long> ids, @Param("mode") String mode,@Param("columnId") Long columnId, @Param("userId") Long userId,
                    @Param("sourceType") ArticleSourceType sourceType,
                    @Param("isDisabled") Boolean isDisabled, @Param("isPublished") Boolean isPublished, @Param("isDeleted") Boolean isDeleted,
                    @Param("minComment") Integer minComment, @Param("maxComment") Integer maxComment,
                    @Param("minView") Integer minView, @Param("maxView") Integer maxView,
                    @Param("minSupport") Integer minSupport, @Param("maxSupport") Integer maxSupport,
                    @Param("minOppose") Integer minOppose, @Param("maxOppose") Integer maxOppose,
                    @Param("minStar") Integer minStar, @Param("maxStar") Integer maxStar,
                    @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,@Param("ip") Long ip);

    int updateVersion(@Param("id") Long id, @Param("version") Integer version, @Param("draft") Integer draft, @Param("isPublished") Boolean isPublished, @Param("time") Long time, @Param("ip") Long ip, @Param("userId") Long userId);
    int updateView(@Param("id") Long id, @Param("viewCount") Integer viewCount);

    ArticleInfoModel findById(Long id);

    int updateComments(@Param("id") Long id, @Param("value") Integer value);

    int updateStars(@Param("id") Long id, @Param("value") Integer value);

    int updateVote(@Param("type") VoteType type, @Param("value") Integer value, @Param("id") Long id);

    int updateMetas(@Param("id") Long id,@Param("customDescription") String customDescription,@Param("cover") String cover);

    int updateDeleted(@Param("deleted") boolean deleted, @Param("id") Long id);

    int updateInfo(@Param("id") Long id,
                   @Param("title") String title,
                   @Param("description") String description,
                   @Param("columnId") Long columnId,
                   @Param("sourceType") ArticleSourceType sourceType,
                   @Param("sourceData") String sourceData,
                   @Param("time") Long time,
                   @Param("ip") Long ip,
                   @Param("userId") Long userId);

    List<ArticleTotalModel> totalByUser(@Param("userIds") Collection<Long> userIds,
                                        @Param("isDisabled") Boolean isDisabled, @Param("isPublished") Boolean isPublished, @Param("isDeleted") Boolean isDeleted, @Param("beginTime") Long beginTime, @Param("endTime") Long endTime,
                                        @Param("order") Map<String, String> order,
                                        @Param("offset") Integer offset,
                                        @Param("limit") Integer limit);
}
