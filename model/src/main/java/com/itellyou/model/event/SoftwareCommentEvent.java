package com.itellyou.model.event;

import com.itellyou.model.common.OperationalModel;
import com.itellyou.model.sys.EntityAction;
import com.itellyou.model.sys.EntityType;

import java.time.LocalDateTime;

public class SoftwareCommentEvent extends OperationalEvent {

    public SoftwareCommentEvent(Object source, EntityAction action , Long targetId, Long targetUserId, Long createdUserId, LocalDateTime createdTime, Long createdIp) {
        super(source);
        OperationalModel model = new OperationalModel(action, EntityType.SOFTWARE_COMMENT,targetId,targetUserId,createdUserId,createdTime,createdIp);
        super.setOperationalModel(model);
    }
}
