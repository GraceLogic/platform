package com.gracelogic.platform.user.api;

import com.gracelogic.platform.db.dto.EntityListResponse;
import com.gracelogic.platform.property.dto.PropertyDTO;
import com.gracelogic.platform.user.Path;
import com.gracelogic.platform.user.dto.UserSessionDTO;
import com.gracelogic.platform.user.model.UserSession;
import com.gracelogic.platform.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping(value = Path.API_SESSION)
public class SessionApi extends AbstractAuthorizedController {

    @Autowired
    @Qualifier("dbMessageSource")
    private ResourceBundleMessageSource messageSource;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('SESSION:SHOW')")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSessions(  @RequestParam(value = "userId", required = false) UUID userId,
                                        @RequestParam(value = "enrich", required = false, defaultValue = "false") Boolean enrich,
                                        @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                        @RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
                                        @RequestParam(value = "sortField", required = false, defaultValue = "el.created") String sortField,
                                        @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir) {

        EntityListResponse<UserSessionDTO> sessions = userService.getSessionsPaged(userId, enrich, count, null, start, sortField, sortDir);
        return new ResponseEntity<>(sessions, HttpStatus.OK);

    }
}