package com.gracelogic.platform.survey.api;

import com.gracelogic.platform.db.dto.EntityListResponse;
import com.gracelogic.platform.db.exception.ObjectNotFoundException;
import com.gracelogic.platform.localization.service.LocaleHolder;
import com.gracelogic.platform.survey.Path;
import com.gracelogic.platform.survey.dto.admin.SurveyAnswerVariantDTO;
import com.gracelogic.platform.survey.model.SurveyAnswerVariant;
import com.gracelogic.platform.survey.service.SurveyService;
import com.gracelogic.platform.user.api.AbstractAuthorizedController;
import com.gracelogic.platform.web.dto.EmptyResponse;
import com.gracelogic.platform.web.dto.ErrorResponse;
import com.gracelogic.platform.web.dto.IDResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(value = Path.API_SURVEY_QUESTION_ANSWER_VARIANT)
@Api(value = Path.API_SURVEY_QUESTION_ANSWER_VARIANT, tags = {"Survey answer variant API"})
public class SurveyAnswerVariantApi extends AbstractAuthorizedController {
    @Autowired
    private SurveyService surveyService;

    @Autowired
    @Qualifier("dbMessageSource")
    private ResourceBundleMessageSource messageSource;

    @ApiOperation(
            value = "getSurveyAnswerVariants",
            notes = "Get list of survey answer variants",
            response =  EntityListResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @PreAuthorize("hasAuthority('SURVEY:SHOW')")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSurveyAnswerVariants(@RequestParam(value = "description", required = false) String description,
                                     @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                     @RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
                                     @RequestParam(value = "sortField", required = false, defaultValue = "el.created") String sortField,
                                     @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir) {


        EntityListResponse<SurveyAnswerVariantDTO> properties = surveyService.getSurveyAnswerVariantsPaged(description, count, null, start, sortField, sortDir);
        return new ResponseEntity<EntityListResponse<SurveyAnswerVariantDTO>>(properties, HttpStatus.OK);
    }

    @ApiOperation(
            value = "getSurveyAnswerVariant",
            notes = "Get survey answer variant",
            response = SurveyAnswerVariantDTO.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @PreAuthorize("hasAuthority('SURVEY:SHOW')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public ResponseEntity getSurveyAnswerVariant(@PathVariable(value = "id") UUID id) {
        try {
            SurveyAnswerVariantDTO dto = surveyService.getSurveyAnswerVariant(id);
            return new ResponseEntity<SurveyAnswerVariantDTO>(dto, HttpStatus.OK);
        } catch (ObjectNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse("db.NOT_FOUND", messageSource.getMessage("db.NOT_FOUND", null,
                    LocaleHolder.getLocale())), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(
            value = "saveSurveyAnswerVariant",
            notes = "Save survey answer variant",
            response = IDResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @PreAuthorize("hasAuthority('SURVEY:SAVE')")
    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @ResponseBody
    public ResponseEntity saveSurveyAnswerVariant(@RequestBody SurveyAnswerVariantDTO dto) {
        try {
            SurveyAnswerVariant surveyAnswerVariant = surveyService.saveSurveyAnswerVariant(dto);
            return new ResponseEntity<IDResponse>(new IDResponse(surveyAnswerVariant.getId()), HttpStatus.OK);
        } catch (ObjectNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse("db.NOT_FOUND", messageSource.getMessage("db.NOT_FOUND", null, LocaleHolder.getLocale())), HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(
            value = "deleteSurveyAnswerVariant",
            notes = "Delete survey answer variant",
            response = EmptyResponse.class
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @PreAuthorize("hasAuthority('SURVEY:DELETE')")
    @RequestMapping(method = RequestMethod.POST, value = "/{id}/delete")
    @ResponseBody
    public ResponseEntity deleteSurveyAnswerVariant(@PathVariable(value = "id") UUID id) {
        try {
            surveyService.deleteSurveyAnswerVariant(id);
            return new ResponseEntity<EmptyResponse>(EmptyResponse.getInstance(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("db.FAILED_TO_DELETE", messageSource.getMessage("db.FAILED_TO_DELETE", null, LocaleHolder.getLocale())), HttpStatus.BAD_REQUEST);
        }
    }
}