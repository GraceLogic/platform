package com.gracelogic.platform.user.api;

import com.gracelogic.platform.localization.service.LocaleHolder;
import com.gracelogic.platform.user.Path;
import com.gracelogic.platform.user.dto.UserSettingDTO;
import com.gracelogic.platform.user.model.UserSetting;
import com.gracelogic.platform.user.service.UserService;
import com.gracelogic.platform.web.dto.EmptyResponse;
import com.gracelogic.platform.web.dto.ErrorResponse;
import com.gracelogic.platform.web.dto.SingleValueDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = Path.API_SETTING)
@Api(value = Path.API_SETTING, tags = {"Setting API"},
        authorizations = @Authorization(value = "MybasicAuth"))
public class SettingApi extends AbstractAuthorizedController {
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userMessageSource")
    private ResourceBundleMessageSource messageSource;

    @Autowired
    @Qualifier("dbMessageSource")
    private ResourceBundleMessageSource dbMessageSource;

    @ApiOperation(
            value = "getUserSetting",
            notes = "Get user setting by code",
            response = UserSettingDTO.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getUserSetting(@ApiParam(name = "key", value = "key")
                                         @PathVariable(value = "key") String key) {
        if (getUser() == null) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("auth.NOT_AUTHORIZED", messageSource.getMessage("auth.NOT_AUTHORIZED", null, LocaleHolder.getLocale())), HttpStatus.UNAUTHORIZED);
        }

        UserSetting userSetting = userService.getUserSetting(getUser().getId(), key);
        if (userSetting != null) {
            return new ResponseEntity<UserSettingDTO>(UserSettingDTO.prepare(userSetting), HttpStatus.OK);
        } else {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("db.NOT_FOUND", dbMessageSource.getMessage("db.NOT_FOUND", null, LocaleHolder.getLocale())), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(
            value = "saveUserSetting",
            notes = "Save user setting",
            response = EmptyResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @RequestMapping(value = "/{key}/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity saveUserSetting(@ApiParam(name = "key", value = "key")
                                          @PathVariable(value = "key") String key,
                                          @ApiParam(name = "request", value = "request")
                                          @RequestBody SingleValueDTO request) {
        if (getUser() == null) {
            return new ResponseEntity<ErrorResponse>(new ErrorResponse("db.NOT_FOUND", dbMessageSource.getMessage("db.NOT_FOUND", null, LocaleHolder.getLocale())), HttpStatus.UNAUTHORIZED);
        }

        userService.updateUserSetting(getUser().getId(), key, request.getValue());

        return new ResponseEntity<EmptyResponse>(EmptyResponse.getInstance(), HttpStatus.OK);
    }
}
