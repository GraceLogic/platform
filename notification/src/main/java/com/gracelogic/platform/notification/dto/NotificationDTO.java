package com.gracelogic.platform.notification.dto;

import com.gracelogic.platform.db.dto.IdObjectDTO;
import com.gracelogic.platform.notification.model.Notification;
import com.gracelogic.platform.db.JsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotificationDTO extends IdObjectDTO {
    private String source;
    private String destination;
    private String title;
    private String body;
    private Map<String, String> fields = new HashMap<>();
    private Integer priority;
    private UUID notificationStateId;
    private String notificationStateName;
    private UUID notificationMethodId;
    private String notificationMethodName;
    private UUID referenceObjectId;

    public UUID getNotificationStateId() {
        return notificationStateId;
    }

    public void setNotificationStateId(UUID notificationStateId) {
        this.notificationStateId = notificationStateId;
    }

    public UUID getNotificationMethodId() {
        return notificationMethodId;
    }

    public void setNotificationMethodId(UUID notificationMethodId) {
        this.notificationMethodId = notificationMethodId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNotificationStateName() {
        return notificationStateName;
    }

    public void setNotificationStateName(String notificationStateName) {
        this.notificationStateName = notificationStateName;
    }

    public String getNotificationMethodName() {
        return notificationMethodName;
    }

    public void setNotificationMethodName(String notificationMethodName) {
        this.notificationMethodName = notificationMethodName;
    }

    public UUID getReferenceObjectId() {
        return referenceObjectId;
    }

    public void setReferenceObjectId(UUID referenceObjectId) {
        this.referenceObjectId = referenceObjectId;
    }

    public static NotificationDTO prepare(Notification model) {
        NotificationDTO dto = new NotificationDTO();
        IdObjectDTO.prepare(dto, model);

        if (model.getNotificationState() != null) {
            dto.setNotificationStateId(model.getNotificationState().getId());
        }
        if (model.getNotificationMethod() != null) {
            dto.setNotificationMethodId(model.getNotificationMethod().getId());
        }
        dto.setSource(model.getSource());
        dto.setDestination(model.getDestination());
        dto.setBody(model.getBody());
        dto.setTitle(model.getTitle());
        dto.setFields(JsonUtils.jsonToMap(model.getFields()));
        dto.setPriority(model.getPriority());
        dto.setReferenceObjectId(model.getReferenceObjectId());
        return dto;
    }

    public static NotificationDTO enrich(NotificationDTO dto, Notification model) {
        if (model.getNotificationMethod() != null) {
            dto.setNotificationMethodName(model.getNotificationMethod().getName());
        }

        if (model.getNotificationState() != null) {
            dto.setNotificationStateName(model.getNotificationState().getName());
        }

        return dto;
    }
}
