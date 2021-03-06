package com.gracelogic.platform.survey.dto.admin;

import com.gracelogic.platform.db.dto.IdObjectDTO;
import com.gracelogic.platform.survey.model.SurveyAnswerVariant;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SurveyAnswerVariantDTO extends IdObjectDTO {
    private UUID surveyQuestionId;
    private String text;
    private Integer sortOrder;
    private Boolean defaultVariant; // Вариант выбран по умолчанию
    private Boolean customVariant;
    private Integer weight; // Вес ответа для автоматической обработки

    /**
     * Survey logic triggers with type HIDE_QUESTION / SHOW_QUESTION for web
     */
    private List<SurveyLogicTriggerDTO> webLogicTriggers;

    /**
     * Logic triggers that must be added to this answer variant
     */
    // CLIENT->SERVER
    private List<SurveyLogicTriggerDTO> logicTriggersToAdd = new LinkedList<>();


    public static SurveyAnswerVariantDTO prepare(SurveyAnswerVariant model) {
        SurveyAnswerVariantDTO dto = new SurveyAnswerVariantDTO();
        IdObjectDTO.prepare(dto, model);
        if (model.getSurveyQuestion() != null) {
            dto.setSurveyQuestionId(model.getSurveyQuestion().getId());
        }
        dto.setText(model.getText());
        dto.setSortOrder(model.getSortOrder());
        dto.setDefaultVariant(model.getDefaultVariant());
        dto.setCustomVariant(model.isCustomVariant());
        dto.setWeight(model.getWeight());
        return dto;
    }

    public UUID getSurveyQuestionId() {
        return surveyQuestionId;
    }

    public void setSurveyQuestionId(UUID surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Boolean getDefaultVariant() {
        return defaultVariant;
    }

    public void setDefaultVariant(Boolean defaultVariant) {
        this.defaultVariant = defaultVariant;
    }

    public Boolean getCustomVariant() {
        return customVariant;
    }

    public void setCustomVariant(Boolean customVariant) {
        this.customVariant = customVariant;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<SurveyLogicTriggerDTO> getLogicTriggersToAdd() {
        return logicTriggersToAdd;
    }

    public void setLogicTriggersToAdd(List<SurveyLogicTriggerDTO> logicTriggersToAdd) {
        this.logicTriggersToAdd = logicTriggersToAdd;
    }

    public List<SurveyLogicTriggerDTO> getWebLogicTriggers() {
        return webLogicTriggers;
    }

    public void setWebLogicTriggers(List<SurveyLogicTriggerDTO> webLogicTriggers) {
        this.webLogicTriggers = webLogicTriggers;
    }
}
