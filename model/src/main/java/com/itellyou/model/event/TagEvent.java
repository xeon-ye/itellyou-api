package com.itellyou.model.event;

import com.itellyou.model.sys.EntityAction;
import com.itellyou.model.common.OperationalModel;
import com.itellyou.model.sys.EntityType;

import java.time.LocalDateTime;

public class TagEvent extends OperationalEvent {

    public TagEvent(Object source, EntityAction action , Long targetId, Long targetUserId, Long createdUserId, LocalDateTime createdTime, Long createdIp) {
        super(source);
        OperationalModel model = new OperationalModel(action, EntityType.TAG,targetId,targetUserId,createdUserId,createdTime,createdIp);
        super.setOperationalModel(model);
    }

    public TagEvent(Object source){
        super(source);
    }
}
