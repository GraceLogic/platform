package com.gracelogic.platform.oauth.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracelogic.platform.oauth.DataConstants;
import com.gracelogic.platform.oauth.dto.OAuthDTO;
import com.gracelogic.platform.oauth.service.AbstractOauthProvider;
import com.gracelogic.platform.oauth.service.OAuthServiceProvider;
import com.gracelogic.platform.oauth.service.OAuthUtils;
import com.gracelogic.platform.property.service.PropertyService;
import com.gracelogic.platform.user.model.User;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

@Service("esia")
public class EsiaOAuthServiceProviderImpl extends AbstractOauthProvider implements OAuthServiceProvider {
    private static final String ACCESS_TOKEN_ENDPOINT = "http://safecity.amfitel.ru:2111/aas/oauth2/te";

    private static final String API_ENDPOINT = "http://safecity.amfitel.ru:2111/rs/prns";

    @Autowired
    private PropertyService propertyService;

    private static Logger logger = Logger.getLogger(EsiaOAuthServiceProviderImpl.class);

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User processAuthorization(String code, String redirectUri) {
        String CLIENT_ID = propertyService.getPropertyValue("oauth:esia_client_id");
        String CLIENT_SECRET = propertyService.getPropertyValue("oauth:esia_client_secret");

        String sRedirectUri = redirectUri;
        if (StringUtils.isEmpty(redirectUri)) {
            sRedirectUri = getRedirectUrl(DataConstants.OAuthProviders.ESIA.name());

            try {
                sRedirectUri = URLEncoder.encode(sRedirectUri, "UTF-8");
            }
            catch (Exception ignored) {}
        }

        String credentials = new String(CLIENT_ID + ":" + CLIENT_SECRET);
        logger.info("Credentials: " + credentials);
        Map response = postQueryWithAuthenticationReturnJson(String.format("%s?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s", ACCESS_TOKEN_ENDPOINT, CLIENT_ID, CLIENT_SECRET, code, sRedirectUri), "Basic " + new String(Base64.encodeBase64(credentials.getBytes())));

        if (response == null) {
            return null;
        }

        OAuthDTO OAuthDTO = new OAuthDTO();
        OAuthDTO.setAccessToken(response.get("access_token") != null ? (String) response.get("access_token") : null);
        OAuthDTO.setUserId(response.get("urn:esia:sbj_id") != null ? String.valueOf(response.get("urn:esia:sbj_id")) : null);

        response = getQueryWithAuthenticationReturnJson(String.format("%s/%s", API_ENDPOINT, OAuthDTO.getUserId()), "Bearer " + OAuthDTO.getAccessToken());

        OAuthDTO.setLastName(response.get("name") != null ? (String) response.get("name") : null);

        return processAuthorization(DataConstants.OAuthProviders.ESIA.getValue(), code, OAuthDTO);
    }

    @Override
    public String buildAuthRedirect() {
        String sRedirectUri = buildRedirectUri(null);
        String CLIENT_ID = propertyService.getPropertyValue("oauth:esia_client_id");

        return String.format("http://safecity.amfitel.ru:2111/aas/oauth2/ac?response_type=code&scope=read&client_id=%s&redirect_uri=%s", CLIENT_ID, sRedirectUri);
    }

    @Override
    public String buildRedirectUri(String additionalParameters) {
        String sRedirectUri = getRedirectUrl(DataConstants.OAuthProviders.ESIA.name());
        if (!StringUtils.isEmpty(additionalParameters)) {
            sRedirectUri = sRedirectUri + additionalParameters;
        }
        try {
            sRedirectUri = URLEncoder.encode(sRedirectUri, "UTF-8");
        }
        catch (Exception ignored) {}

        return sRedirectUri;
    }

    private static Map<Object, Object> getQueryWithAuthenticationReturnJson(String url, String authentication) {
        logger.info("Request to: " + url);

        CloseableHttpClient httpClient = HttpClients.custom().build();

        HttpGet method = new HttpGet(url);

        method.addHeader("Authorization", authentication);

        method.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter("http.protocol.single-cookie-header", true);

        Map<Object, Object> result = null;
        String response = null;

        MappingJackson2HttpMessageConverter c = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = c.getObjectMapper();

        try {
            CloseableHttpResponse httpResult = httpClient.execute(method);
            logger.info("Request status: " + httpResult);

            HttpEntity entity = httpResult.getEntity();
            if (entity != null) {
                if (httpResult.getStatusLine().getStatusCode() == 200) {
                    response = EntityUtils.toString(entity);
                }
                EntityUtils.consume(entity);
                logger.info("Response body: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            try {
                result = objectMapper.readValue(response, new TypeReference<Map<Object, Object>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    private static Map<Object, Object> postQueryWithAuthenticationReturnJson(String url, String authentication) {
        logger.info("Request to: " + url);

        CloseableHttpClient httpClient = HttpClients.custom().build();

        HttpPost method = new HttpPost(url);

        method.addHeader("Authorization", authentication);
        method.addHeader("Accept", "application/json");

        method.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter("http.protocol.single-cookie-header", true);

        Map<Object, Object> result = null;
        String response = null;

        MappingJackson2HttpMessageConverter c = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = c.getObjectMapper();

        try {
            CloseableHttpResponse httpResult = httpClient.execute(method);
            logger.info("Request status: " + httpResult);

            HttpEntity entity = httpResult.getEntity();
            if (entity != null) {
                if (httpResult.getStatusLine().getStatusCode() == 200) {
                    response = EntityUtils.toString(entity);
                }
                EntityUtils.consume(entity);
                logger.info("Response body: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            try {
                result = objectMapper.readValue(response, new TypeReference<Map<Object, Object>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}
