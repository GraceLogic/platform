package com.gracelogic.platform.content.api;

import com.gracelogic.platform.content.Path;
import com.gracelogic.platform.content.dto.ElementDTO;
import com.gracelogic.platform.content.dto.SectionDTO;
import com.gracelogic.platform.content.service.ContentService;
import com.gracelogic.platform.db.dto.DateFormatConstants;
import com.gracelogic.platform.db.dto.EntityListResponse;
import com.gracelogic.platform.user.api.AbstractAuthorizedController;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Author: Igor Parkhomenko
 * Date: 16.07.2016
 * Time: 21:58
 */
@Controller
@RequestMapping(value = Path.API_CONTENT)
@Api(value = Path.API_CONTENT, description = "Контроллер управления контентом", authorizations = @Authorization(value = "MybasicAuth"))
public class ContentApi extends AbstractAuthorizedController {
    @Autowired
    private ContentService contentService;

    @ApiOperation(value = "sections", notes = "Получить список секций в иерархическом виде")
    @ApiResponses({@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 500, message = "Something exceptional happened")})
    @RequestMapping(value = "/sections", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSections(@ApiParam(name = "parentId", value = "parentId") @RequestParam(value = "parentId", required = false) UUID parentId,
                                      @ApiParam(name = "onlyActive", value = "onlyActive") @RequestParam(value = "onlyActive", required = false, defaultValue = "true") Boolean onlyActive) {
        List<SectionDTO> sectionDTOs = contentService.getSectionsHierarchically(parentId, onlyActive);

        return new ResponseEntity<List<SectionDTO>>(sectionDTOs, HttpStatus.OK);
    }

    @ApiOperation(value = "elements", notes = "Получить список элементов")
    @ApiResponses({@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 500, message = "Something exceptional happened")})
    @RequestMapping(value = "/elements", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getElements(@ApiParam(name = "sectionIds", value = "sectionIds") @RequestParam(value = "sectionIds", required = false) String sSectionIds,
                                      @ApiParam(name = "active", value = "active") @RequestParam(value = "active", required = false, defaultValue = "true") Boolean active,
                                      @ApiParam(name = "validOnDate", value = "validOnDate") @RequestParam(value = "validOnDate", required = false) String sValidOnDate,
                                      @ApiParam(name = "start", value = "start") @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @ApiParam(name = "page", value = "page") @RequestParam(value = "page", required = false) Integer page,
                                      @ApiParam(name = "count", value = "count") @RequestParam(value = "count", required = false, defaultValue = "10") Integer length,
                                      @ApiParam(name = "sortField", value = "sortField") @RequestParam(value = "sortField", required = false, defaultValue = "el.created") String sortField,
                                      @ApiParam(name = "sortDir", value = "sortDir") @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir) {

        Date validOnDate = null;

        try {
            if (!StringUtils.isEmpty(sValidOnDate)) {
                validOnDate = DateFormatConstants.DEFAULT_DATE_FORMAT.get().parse(sValidOnDate);
            }
        } catch (Exception ignored) {
        }

        List<UUID> sectionIds = new LinkedList<>();
        if (!StringUtils.isEmpty(sSectionIds)) {
            for (String s : sSectionIds.split(",")) {
                sectionIds.add(UUID.fromString(s));
            }
        }

        EntityListResponse<ElementDTO> elements = contentService.getElementsPaged(sectionIds, active, validOnDate, null, length, page, start, sortField, sortDir);

        return new ResponseEntity<EntityListResponse<ElementDTO>>(elements, HttpStatus.OK);
    }


}