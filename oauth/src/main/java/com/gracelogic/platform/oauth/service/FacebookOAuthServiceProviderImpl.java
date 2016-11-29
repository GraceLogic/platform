package com.gracelogic.platform.oauth.service;

import com.gracelogic.platform.oauth.DataConstants;
import com.gracelogic.platform.oauth.dto.OAuthDTO;
import com.gracelogic.platform.property.service.PropertyService;
import com.gracelogic.platform.user.model.User;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Author: Igor Parkhomenko
 * Date: 21.10.2015
 * Time: 13:15
 */
@Service("facebook")
public class FacebookOAuthServiceProviderImpl extends AbstractOauthProvider implements OAuthServiceProvider {
    private static Logger logger = Logger.getLogger(FacebookOAuthServiceProviderImpl.class);

    //private static final String CLIENT_ID = "1845606962375917";
    //private static final String CLIENT_SECRET = "88bec87320505bbf7f0a5ef1112524dd";
    private static final String ACCESS_TOKEN_ENDPOINT = "https://graph.facebook.com/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";
    private static final String API_ENDPOINT = "https://graph.facebook.com/me?access_token=%s&fields=id,name,last_name,first_name,email";

    @Autowired
    private PropertyService propertyService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User accessToken(String code, String redirectUri) {
        String CLIENT_ID = propertyService.getPropertyValue("oauth:facebook_client_id");
        String CLIENT_SECRET = propertyService.getPropertyValue("oauth:facebook_client_secret");

        String sRedirectUri = redirectUri;
        if (StringUtils.isEmpty(redirectUri)) {
            sRedirectUri = getRedirectUrl(DataConstants.OAuthProviders.FACEBOOK.name());

            try {
                sRedirectUri = URLEncoder.encode(sRedirectUri, "UTF-8");
            }
            catch (Exception ignored) {}
        }
        String accessTokenResponse = OAuthUtils.getQuery(String.format(ACCESS_TOKEN_ENDPOINT, CLIENT_ID, sRedirectUri, CLIENT_SECRET, code));
        String accessToken = null;
        if (!StringUtils.isEmpty(accessTokenResponse)) {
            StringTokenizer st = new StringTokenizer(accessTokenResponse, "&");
            while (st.hasMoreTokens()) {
                StringTokenizer stt = new StringTokenizer(st.nextToken(), "=");

                if (StringUtils.equalsIgnoreCase(stt.nextToken(), "access_token")) {
                    accessToken = stt.nextToken();
                    break;
                }
            }
        }
        if (accessToken == null) {
            return null;
        }

        OAuthDTO OAuthDTO = new OAuthDTO();
        OAuthDTO.setAccessToken(accessToken);

        Map<Object, Object> response = OAuthUtils.getJsonQuery(String.format(API_ENDPOINT, OAuthDTO.getAccessToken()));
        OAuthDTO.setUserId(response.get("id") != null ? StringEscapeUtils.unescapeJava((String) response.get("id")) : null);
        OAuthDTO.setFirstName(response.get("first_name") != null ? StringEscapeUtils.unescapeJava((String) response.get("first_name")) : null);
        OAuthDTO.setLastName(response.get("last_name") != null ? StringEscapeUtils.unescapeJava((String) response.get("last_name")) : null);
        OAuthDTO.setEmail(response.get("email") != null ? StringEscapeUtils.unescapeJava((String) response.get("email")) : null);

        return processAuth(DataConstants.OAuthProviders.FACEBOOK.getValue(), code, OAuthDTO);
    }

    @Override
    public String buildAuthRedirect() {
        String CLIENT_ID = propertyService.getPropertyValue("oauth:facebook_client_id");
        String sRedirectUri = buildRedirectUri(null);

        return String.format("https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s&response_type=code&scope=public_profile,email", CLIENT_ID, sRedirectUri);
    }

    @Override
    public String buildRedirectUri(String additionalParameters) {
        String sRedirectUri = getRedirectUrl(DataConstants.OAuthProviders.FACEBOOK.name());
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