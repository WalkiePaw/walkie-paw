package com.WalkiePaw.presentation.domain.oauth.provider;

import com.WalkiePaw.presentation.domain.oauth.dto.OAuthUserinfoResponse;

public interface OAuthProvider {
    OAuthUserinfoResponse getUserInfo(final String code);
}
