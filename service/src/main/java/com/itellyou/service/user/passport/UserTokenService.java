package com.itellyou.service.user.passport;

import com.itellyou.model.user.UserInfoModel;

public interface UserTokenService {

    UserInfoModel find(String token, Long time);

    UserInfoModel find(String token);
}
