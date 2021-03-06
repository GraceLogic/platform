package com.gracelogic.platform.filestorage.dto;

import com.gracelogic.platform.db.dto.IdObjectDTO;
import com.gracelogic.platform.filestorage.model.StoredFile;

import java.util.UUID;

public class StoredFileDTO extends IdObjectDTO {
    private String extension;
    private UUID referenceObjectId;
    private UUID storeModeId;
    private String storeModeName;
    private String meta;
    private Boolean dataAvailable;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public UUID getReferenceObjectId() {
        return referenceObjectId;
    }

    public void setReferenceObjectId(UUID referenceObjectId) {
        this.referenceObjectId = referenceObjectId;
    }

    public UUID getStoreModeId() {
        return storeModeId;
    }

    public void setStoreModeId(UUID storeModeId) {
        this.storeModeId = storeModeId;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getStoreModeName() {
        return storeModeName;
    }

    public void setStoreModeName(String storeModeName) {
        this.storeModeName = storeModeName;
    }

    public Boolean getDataAvailable() {
        return dataAvailable;
    }

    public void setDataAvailable(Boolean dataAvailable) {
        this.dataAvailable = dataAvailable;
    }

    public static StoredFileDTO prepare(StoredFile storedFile) {
        StoredFileDTO dto = new StoredFileDTO();
        IdObjectDTO.prepare(dto, storedFile);

        dto.setReferenceObjectId(storedFile.getReferenceObjectId());
        dto.setExtension(storedFile.getExtension());
        dto.setDataAvailable(storedFile.getDataAvailable());
        dto.setMeta(storedFile.getMeta());
        if (storedFile.getStoreMode() != null) {
            dto.setStoreModeId(storedFile.getStoreMode().getId());
        }

        return dto;
    }

    public static StoredFileDTO enrich(StoredFileDTO dto, StoredFile storedFile) {
        if (storedFile.getStoreMode() != null) {
            dto.setStoreModeName(storedFile.getStoreMode().getName());
        }

        return dto;
    }
}
