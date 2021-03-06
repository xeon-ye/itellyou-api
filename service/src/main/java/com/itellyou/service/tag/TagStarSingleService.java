package com.itellyou.service.tag;

import com.itellyou.model.tag.TagStarModel;

import java.util.Collection;
import java.util.List;

public interface TagStarSingleService {

    TagStarModel find(Long tagId, Long userId);

    List<TagStarModel> search(Collection<Long> tagIds, Long userId);
}
