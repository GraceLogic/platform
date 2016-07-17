package com.gracelogic.platform.oauth.service;

import com.gracelogic.platform.oauth.OauthConstants;
import com.gracelogic.platform.oauth.dto.AuthDTO;
import com.gracelogic.platform.property.service.PropertyService;
import com.gracelogic.platform.user.model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.Map;

/**
 * Author: Igor Parkhomenko
 * Date: 21.10.2015
 * Time: 13:15
 */
@Service("ok")
public class OkOAuthServiceProviderImpl extends AbstractOauthProvider implements OAuthServiceProvider {
    //private static final String CLIENT_ID = "1174674176";
    //private static final String CLIENT_SECRET = "9479EBB4B0ABBB28B41F18DD";
    private static final String ACCESS_TOKEN_ENDPOINT = "https://api.odnoklassniki.ru/oauth/token.do";

    private static final String API_ENDPOINT = "http://api.odnoklassniki.ru/fb.do";
    //private static final String PUBLIC_KEY = "CBAMBFAHEBABABABA";

    @Autowired
    private PropertyService propertyService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User accessToken(String code, String redirectUri) {
        String CLIENT_ID = propertyService.getPropertyValue("oauth:ok_client_id");
        String CLIENT_SECRET = propertyService.getPropertyValue("oauth:ok_client_secret");
        String PUBLIC_KEY = propertyService.getPropertyValue("oauth:ok_public_key");

        String sRedirectUri = redirectUri;
        if (StringUtils.isEmpty(redirectUri)) {
            sRedirectUri = getRedirectUrl(OauthConstants.AuthProviderConstants.OK.name());

            try {
                sRedirectUri = URLEncoder.encode(sRedirectUri, "UTF-8");
            }
            catch (Exception ignored) {}
        }

        Map<Object, Object> response = OAuthUtils.postJsonQuery(String.format("%s?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s", ACCESS_TOKEN_ENDPOINT, CLIENT_ID, CLIENT_SECRET, code, sRedirectUri), null);

        if (response == null) {
            return null;
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAccessToken(response.get("access_token") != null ? (String) response.get("access_token") : null);

        String s = String.format("application_key=%sformat=jsonmethod=users.getCurrentUser", PUBLIC_KEY);
        String md51 = DigestUtils.md5Hex(String.format("%s%s", authDTO.getAccessToken(), CLIENT_SECRET));
        String sign = DigestUtils.md5Hex(String.format("%s%s", s, md51));

        response = OAuthUtils.postJsonQuery(String.format("%s?application_key=%s&method=users.getCurrentUser&format=json&access_token=%s&sig=%s", API_ENDPOINT, PUBLIC_KEY, authDTO.getAccessToken(), sign), null);
        authDTO.setUserId(response.get("uid") != null ? (String) response.get("uid") : null);
        authDTO.setFirstName(response.get("first_name") != null ? (String) response.get("first_name") : null);
        authDTO.setLastName(response.get("last_name") != null ? (String) response.get("last_name") : null);
        authDTO.setNickname(null);
        authDTO.setEmail(null);

        return processAuth(OauthConstants.AuthProviderConstants.OK.getValue(), code, authDTO);
    }

    @Override
    public String buildAuthRedirect() {
        String CLIENT_ID = propertyService.getPropertyValue("oauth:ok_client_id");
        String sRedirectUri = buildRedirectUri(null);

        return String.format("https://connect.ok.ru/oauth/authorize?client_id=%s&scope=VALUABLE_ACCESS&response_type=code&layout=w&redirect_uri=%s", CLIENT_ID, sRedirectUri);
    }

    @Override
    public String buildRedirectUri(String additionalParameters) {
        String sRedirectUri = getRedirectUrl(OauthConstants.AuthProviderConstants.OK.name());
        if (!StringUtils.isEmpty(additionalParameters)) {
            sRedirectUri = sRedirectUri + additionalParameters;
        }
        try {
            sRedirectUri = URLEncoder.encode(sRedirectUri, "UTF-8");
        }
        catch (Exception ignored) {}

        return sRedirectUri;
    }
}
