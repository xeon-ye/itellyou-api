package com.itellyou.service.article;

import com.itellyou.model.article.ArticleDetailModel;
import com.itellyou.model.article.ArticleSourceType;
import com.itellyou.model.article.ArticleTotalDetailModel;
import com.itellyou.model.sys.PageModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ArticleSearchService {

    List<ArticleDetailModel> search(Collection<Long> ids, String mode, Long columnId, Long userId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
                                    Collection<Long> tags,
                                    Integer minComment, Integer maxComment,
                                    Integer minView, Integer maxView,
                                    Integer minSupport, Integer maxSupport,
                                    Integer minOppose, Integer maxOppose,
                                    Integer minStars, Integer maxStars,
                                    Long beginTime, Long endTime,
                                    Long ip,
                                    Map<String, String> order, Integer offset, Integer limit);
    int count(Collection<Long> ids, String mode, Long columnId, Long userId, ArticleSourceType sourceType, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
              Collection<Long> tags,
              Integer minComment, Integer maxComment,
              Integer minView, Integer maxView,
              Integer minSupport, Integer maxSupport,
              Integer minOppose, Integer maxOppose,
              Integer minStars, Integer maxStars,
              Long beginTime, Long endTime, Long ip);

    List<ArticleDetailModel> search(Collection<Long> ids, String mode, Long columnId, Long userId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent,
                                    Long beginTime, Long endTime,
                                    Map<String, String> order, Integer offset, Integer limit);

    List<ArticleDetailModel> search(Long columnId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
                                    Collection<Long> tags,
                                    Integer minComment, Integer maxComment,
                                    Integer minView, Integer maxView,
                                    Integer minSupport, Integer maxSupport,
                                    Integer minOppose, Integer maxOppose,
                                    Integer minStars, Integer maxStars,
                                    Long beginTime, Long endTime,
                                    Map<String, String> order, Integer offset, Integer limit);

    List<ArticleDetailModel> search(Long columnId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
                                    Collection<Long> tags,
                                    Integer minComment, Integer maxComment,
                                    Integer minView, Integer maxView,
                                    Integer minSupport, Integer maxSupport,
                                    Integer minOppose, Integer maxOppose,
                                    Integer minStars, Integer maxStars,
                                    Long beginTime, Long endTime, Integer offset, Integer limit);

    int count(Long columnId, ArticleSourceType sourceType, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
              Collection<Long> tags,
                    Integer minComment, Integer maxComment,
                    Integer minView, Integer maxView,
                    Integer minSupport, Integer maxSupport,
                    Integer minOppose, Integer maxOppose,
                    Integer minStars, Integer maxStars,
                    Long beginTime, Long endTime);

    List<ArticleDetailModel> search(Long columnId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
                                    Long beginTime, Long endTime,
                                    Map<String, String> order, Integer offset, Integer limit);

    List<ArticleDetailModel> search(Long columnId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
                                    Long beginTime, Long endTime, Integer offset, Integer limit);

    int count(Long columnId, ArticleSourceType sourceType, Boolean isDisabled, Boolean isDeleted, Boolean isPublished, Long beginTime, Long endTime);

    PageModel<ArticleDetailModel> page(Collection<Long> ids, String mode, Long columnId, Long userId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
                                       Collection<Long> tags,
                                       Integer minComment, Integer maxComment,
                                       Integer minView, Integer maxView,
                                       Integer minSupport, Integer maxSupport,
                                       Integer minOppose, Integer maxOppose,
                                       Integer minStars, Integer maxStars,
                                       Long beginTime, Long endTime,
                                       Long ip,
                                       Map<String, String> order, Integer offset, Integer limit);

    PageModel<ArticleDetailModel> page(Long columnId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
                                       Collection<Long> tags,
                                       Integer minComment, Integer maxComment,
                                       Integer minView, Integer maxView,
                                       Integer minSupport, Integer maxSupport,
                                       Integer minOppose, Integer maxOppose,
                                       Integer minStars, Integer maxStars,
                                       Long beginTime, Long endTime,
                                       Map<String, String> order, Integer offset, Integer limit);

    PageModel<ArticleDetailModel> page(Long columnId, Long searchUserId, ArticleSourceType sourceType, Boolean hasContent, Boolean isDisabled, Boolean isDeleted, Boolean isPublished,
                                       Long beginTime, Long endTime,
                                       Integer offset,
                                       Integer limit);

    ArticleDetailModel getDetail(Long id, String mode, Long userId);

    ArticleDetailModel getDetail(Long id, String mode);

    ArticleDetailModel getDetail(Long id, Long userId, Long searchUserId);

    ArticleDetailModel getDetail(Long id, Long userId);

    ArticleDetailModel getDetail(Long id);

    List<ArticleTotalDetailModel> totalByUser(Collection<Long> userIds,Long searchUserId,
                                              Boolean isDisabled, Boolean isPublished, Boolean isDeleted, Long beginTime, Long endTime,
                                              Map<String, String> order,
                                              Integer offset,
                                              Integer limit);
}
