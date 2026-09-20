// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.document.server.control;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.document.common.choice.DocumentChoicesBean;
import com.echothree.model.control.document.common.choice.DocumentTypeChoicesBean;
import com.echothree.model.control.document.common.choice.DocumentTypeUsageTypeChoicesBean;
import com.echothree.model.control.document.common.transfer.DocumentDescriptionTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeDescriptionTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTypeDescriptionTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTypeUsageTypeTransfer;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
import com.echothree.model.control.document.common.transfer.PartyTypeDocumentTypeUsageTypeTransfer;
import com.echothree.model.control.document.server.transfer.DocumentDescriptionTransferCache;
import com.echothree.model.control.document.server.transfer.DocumentTransferCache;
import com.echothree.model.control.document.server.transfer.DocumentTypeDescriptionTransferCache;
import com.echothree.model.control.document.server.transfer.DocumentTypeTransferCache;
import com.echothree.model.control.document.server.transfer.DocumentTypeUsageTransferCache;
import com.echothree.model.control.document.server.transfer.DocumentTypeUsageTypeDescriptionTransferCache;
import com.echothree.model.control.document.server.transfer.DocumentTypeUsageTypeTransferCache;
import com.echothree.model.control.document.server.transfer.PartyDocumentTransferCache;
import com.echothree.model.control.document.server.transfer.PartyTypeDocumentTypeUsageTypeTransferCache;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.common.pk.DocumentPK;
import com.echothree.model.data.document.common.pk.DocumentTypePK;
import com.echothree.model.data.document.common.pk.DocumentTypeUsageTypePK;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentBlob;
import com.echothree.model.data.document.server.entity.DocumentClob;
import com.echothree.model.data.document.server.entity.DocumentDescription;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.document.server.entity.DocumentTypeDescription;
import com.echothree.model.data.document.server.entity.DocumentTypeUsage;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageTypeDescription;
import com.echothree.model.data.document.server.entity.PartyDocument;
import com.echothree.model.data.document.server.entity.PartyTypeDocumentTypeUsageType;
import com.echothree.model.data.document.server.factory.DocumentBlobFactory;
import com.echothree.model.data.document.server.factory.DocumentClobFactory;
import com.echothree.model.data.document.server.factory.DocumentDescriptionFactory;
import com.echothree.model.data.document.server.factory.DocumentDetailFactory;
import com.echothree.model.data.document.server.factory.DocumentFactory;
import com.echothree.model.data.document.server.factory.DocumentTypeDescriptionFactory;
import com.echothree.model.data.document.server.factory.DocumentTypeDetailFactory;
import com.echothree.model.data.document.server.factory.DocumentTypeFactory;
import com.echothree.model.data.document.server.factory.DocumentTypeUsageFactory;
import com.echothree.model.data.document.server.factory.DocumentTypeUsageTypeDescriptionFactory;
import com.echothree.model.data.document.server.factory.DocumentTypeUsageTypeDetailFactory;
import com.echothree.model.data.document.server.factory.DocumentTypeUsageTypeFactory;
import com.echothree.model.data.document.server.factory.PartyDocumentFactory;
import com.echothree.model.data.document.server.factory.PartyTypeDocumentTypeUsageTypeFactory;
import com.echothree.model.data.document.server.value.DocumentBlobValue;
import com.echothree.model.data.document.server.value.DocumentClobValue;
import com.echothree.model.data.document.server.value.DocumentDescriptionValue;
import com.echothree.model.data.document.server.value.DocumentDetailValue;
import com.echothree.model.data.document.server.value.DocumentTypeDescriptionValue;
import com.echothree.model.data.document.server.value.DocumentTypeDetailValue;
import com.echothree.model.data.document.server.value.DocumentTypeUsageTypeDescriptionValue;
import com.echothree.model.data.document.server.value.DocumentTypeUsageTypeDetailValue;
import com.echothree.model.data.document.server.value.DocumentTypeUsageValue;
import com.echothree.model.data.document.server.value.PartyDocumentValue;
import com.echothree.model.data.document.server.value.PartyTypeDocumentTypeUsageTypeValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENTS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_DETAILS_DOCUMENT_TYPE_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_TYPE_USAGES_DOCUMENT_TYPE_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_TYPE_USAGES_DOCUMENT_TYPE_USAGE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_TYPE_USAGE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_TYPE_USAGE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.DOCUMENT_TYPE_USAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.PARTY_DOCUMENTS_DOCUMENT_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.PARTY_TYPE_DOCUMENT_TYPE_USAGE_TYPES_DOCUMENT_TYPE_USAGE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.document.DocumentForeignKeys.PARTY_TYPE_DOCUMENT_TYPE_USAGE_TYPES_PARTY_TYPE_FK;
import static com.echothree.model.jooq.server.tables.document.DocumentBlobs.DocumentBlobs;
import static com.echothree.model.jooq.server.tables.document.DocumentClobs.DocumentClobs;
import static com.echothree.model.jooq.server.tables.document.DocumentDescriptions.DocumentDescriptions;
import static com.echothree.model.jooq.server.tables.document.DocumentDetails.DocumentDetails;
import static com.echothree.model.jooq.server.tables.document.DocumentTypeDescriptions.DocumentTypeDescriptions;
import static com.echothree.model.jooq.server.tables.document.DocumentTypeDetails.DocumentTypeDetails;
import static com.echothree.model.jooq.server.tables.document.DocumentTypeUsageTypeDescriptions.DocumentTypeUsageTypeDescriptions;
import static com.echothree.model.jooq.server.tables.document.DocumentTypeUsageTypeDetails.DocumentTypeUsageTypeDetails;
import static com.echothree.model.jooq.server.tables.document.DocumentTypeUsageTypes.DocumentTypeUsageTypes;
import static com.echothree.model.jooq.server.tables.document.DocumentTypeUsages.DocumentTypeUsages;
import static com.echothree.model.jooq.server.tables.document.DocumentTypes.DocumentTypes;
import static com.echothree.model.jooq.server.tables.document.Documents.Documents;
import static com.echothree.model.jooq.server.tables.document.PartyDocuments.PartyDocuments;
import static com.echothree.model.jooq.server.tables.document.PartyTypeDocumentTypeUsageTypes.PartyTypeDocumentTypeUsageTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;

@CommandScope
public class DocumentControl
        extends BaseModelControl {
    
    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    /** Creates a new instance of DocumentControl */
    protected DocumentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Document Types
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentTypeDetailFactory documentTypeDetailFactory;

    @Inject
    protected DocumentTypeFactory documentTypeFactory;

    @Inject
    DocumentTypeTransferCache documentTypeTransferCache;

    public DocumentType createDocumentType(String documentTypeName, DocumentType parentDocumentType, MimeTypeUsageType mimeTypeUsageType, Integer maximumPages,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultDocumentType = getDefaultDocumentType();
        var defaultFound = defaultDocumentType != null;
        
        if(defaultFound && isDefault) {
            var defaultDocumentTypeDetailValue = getDefaultDocumentTypeDetailValueForUpdate();
            
            defaultDocumentTypeDetailValue.setIsDefault(false);
            updateDocumentTypeFromValue(defaultDocumentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var documentType = documentTypeFactory.create();
        var documentTypeDetail = documentTypeDetailFactory.create(documentType, documentTypeName, parentDocumentType,
                mimeTypeUsageType, maximumPages, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        documentType = documentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                documentType.getPrimaryKey());
        documentType.setActiveDetail(documentTypeDetail);
        documentType.setLastDetail(documentTypeDetail);
        documentType.store();
        
        sendEvent(documentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return documentType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.DocumentType */
    public DocumentType getDocumentTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new DocumentTypePK(entityInstance.getEntityUniqueId());

        return documentTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public DocumentType getDocumentTypeByEntityInstance(EntityInstance entityInstance) {
        return getDocumentTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public DocumentType getDocumentTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getDocumentTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countDocumentTypes() {
        return session.getDslContext()
                .selectCount()
                .from(DocumentTypes)
                .join(DocumentTypeDetails).onKey(DOCUMENT_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public DocumentType getDocumentTypeByName(String documentTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypes.fields())
                .from(DocumentTypes)
                .join(DocumentTypeDetails).onKey(DOCUMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(DocumentTypeDetails.DOCUMENT_TYPE_NAME.eq(documentTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public DocumentType getDocumentTypeByName(String documentTypeName) {
        return getDocumentTypeByName(documentTypeName, EntityPermission.READ_ONLY);
    }

    public DocumentType getDocumentTypeByNameForUpdate(String documentTypeName) {
        return getDocumentTypeByName(documentTypeName, EntityPermission.READ_WRITE);
    }

    public DocumentTypeDetailValue getDocumentTypeDetailValueForUpdate(DocumentType itemDescriptionType) {
        return itemDescriptionType == null ? null : itemDescriptionType.getLastDetailForUpdate().getDocumentTypeDetailValue().clone();
    }

    public DocumentTypeDetailValue getDocumentTypeDetailValueByNameForUpdate(String documentTypeName) {
        return getDocumentTypeDetailValueForUpdate(getDocumentTypeByNameForUpdate(documentTypeName));
    }

    public DocumentType getDefaultDocumentType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypes.fields())
                .from(DocumentTypes)
                .join(DocumentTypeDetails).onKey(DOCUMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(DocumentTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public DocumentType getDefaultDocumentType() {
        return getDefaultDocumentType(EntityPermission.READ_ONLY);
    }

    public DocumentType getDefaultDocumentTypeForUpdate() {
        return getDefaultDocumentType(EntityPermission.READ_WRITE);
    }

    public DocumentTypeDetailValue getDefaultDocumentTypeDetailValueForUpdate() {
        return getDefaultDocumentTypeForUpdate().getLastDetailForUpdate().getDocumentTypeDetailValue().clone();
    }

    private List<DocumentType> getDocumentTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypes.fields())
                .from(DocumentTypes)
                .join(DocumentTypeDetails).onKey(DOCUMENT_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(DocumentTypeDetails.SORT_ORDER, DocumentTypeDetails.DOCUMENT_TYPE_NAME),
                    DocumentTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<DocumentType> getDocumentTypes() {
        return getDocumentTypes(EntityPermission.READ_ONLY);
    }

    public List<DocumentType> getDocumentTypesForUpdate() {
        return getDocumentTypes(EntityPermission.READ_WRITE);
    }

    private List<DocumentType> getDocumentTypesByParentDocumentType(DocumentType parentDocumentType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypes.fields())
                .from(DocumentTypes)
                .join(DocumentTypeDetails).onKey(DOCUMENT_TYPES_ACTIVE_DETAIL_FK)
                .where(DocumentTypeDetails.PARENT_DOCUMENT_TYPE.eq(parentDocumentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(DocumentTypeDetails.SORT_ORDER, DocumentTypeDetails.DOCUMENT_TYPE_NAME),
                    DocumentTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<DocumentType> getDocumentTypesByParentDocumentType(DocumentType parentDocumentType) {
        return getDocumentTypesByParentDocumentType(parentDocumentType, EntityPermission.READ_ONLY);
    }

    public List<DocumentType> getDocumentTypesByParentDocumentTypeForUpdate(DocumentType parentDocumentType) {
        return getDocumentTypesByParentDocumentType(parentDocumentType, EntityPermission.READ_WRITE);
    }

    public DocumentTypeTransfer getDocumentTypeTransfer(UserVisit userVisit, DocumentType documentType) {
        return documentTypeTransferCache.getDocumentTypeTransfer(userVisit, documentType);
    }
    
    public List<DocumentTypeTransfer> getDocumentTypeTransfers(UserVisit userVisit, Collection<DocumentType> documentTypes) {
        List<DocumentTypeTransfer> documentTypeTransfers = new ArrayList<>(documentTypes.size());
        
        documentTypes.forEach((documentType) ->
                documentTypeTransfers.add(documentTypeTransferCache.getDocumentTypeTransfer(userVisit, documentType))
        );
        
        return documentTypeTransfers;
    }
    
    public List<DocumentTypeTransfer> getDocumentTypeTransfers(UserVisit userVisit) {
        return getDocumentTypeTransfers(userVisit, getDocumentTypes());
    }
    
    public DocumentTypeChoicesBean getDocumentTypeChoices(String defaultDocumentTypeChoice, Language language, boolean allowNullChoice) {
        var documentTypes = getDocumentTypes();
        var size = documentTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultDocumentTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var documentType : documentTypes) {
            var documentTypeDetail = documentType.getLastDetail();
            
            var label = getBestDocumentTypeDescription(documentType, language);
            var value = documentTypeDetail.getDocumentTypeName();
            
            labels.add(label == null ? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultDocumentTypeChoice == null ? false: defaultDocumentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && documentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new DocumentTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentDocumentTypeSafe(DocumentType documentType,
            DocumentType parentDocumentType) {
        var safe = true;
        
        if(parentDocumentType != null) {
            Set<DocumentType> parentDocumentTypes = new HashSet<>();
            
            parentDocumentTypes.add(documentType);
            do {
                if(parentDocumentTypes.contains(parentDocumentType)) {
                    safe = false;
                    break;
                }
                
                parentDocumentTypes.add(parentDocumentType);
                parentDocumentType = parentDocumentType.getLastDetail().getParentDocumentType();
            } while(parentDocumentType != null);
        }
        
        return safe;
    }
    
    private void updateDocumentTypeFromValue(DocumentTypeDetailValue documentTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(documentTypeDetailValue.hasBeenModified()) {
            var documentType = documentTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeDetailValue.getDocumentTypePK());
            var documentTypeDetail = documentType.getActiveDetailForUpdate();
            
            documentTypeDetail.setThruTime(session.getStartTime());
            documentTypeDetail.store();

            var documentTypePK = documentTypeDetail.getDocumentTypePK(); // Not updated
            var documentTypeName = documentTypeDetailValue.getDocumentTypeName();
            var parentDocumentTypePK = documentTypeDetailValue.getParentDocumentTypePK();
            var mimeTypeUsageTypePK = documentTypeDetailValue.getMimeTypeUsageTypePK();
            var maximumPages = documentTypeDetailValue.getMaximumPages();
            var isDefault = documentTypeDetailValue.getIsDefault();
            var sortOrder = documentTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultDocumentType = getDefaultDocumentType();
                var defaultFound = defaultDocumentType != null && !defaultDocumentType.equals(documentType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultDocumentTypeDetailValue = getDefaultDocumentTypeDetailValueForUpdate();
                    
                    defaultDocumentTypeDetailValue.setIsDefault(false);
                    updateDocumentTypeFromValue(defaultDocumentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            documentTypeDetail = documentTypeDetailFactory.create(documentTypePK, documentTypeName, parentDocumentTypePK, mimeTypeUsageTypePK,
                    maximumPages, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
            
            documentType.setActiveDetail(documentTypeDetail);
            documentType.setLastDetail(documentTypeDetail);
            
            sendEvent(documentTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateDocumentTypeFromValue(DocumentTypeDetailValue documentTypeDetailValue, BasePK updatedBy) {
        updateDocumentTypeFromValue(documentTypeDetailValue, true, updatedBy);
    }
    
    private void deleteDocumentType(DocumentType documentType, boolean checkDefault, BasePK deletedBy) {
        var documentTypeDetail = documentType.getLastDetailForUpdate();

        deleteDocumentTypesByParentDocumentType(documentType, deletedBy);
        deleteDocumentTypeDescriptionsByDocumentType(documentType, deletedBy);
        deleteDocumentTypeUsagesByDocumentType(documentType, deletedBy);
        deleteDocumentsByDocumentType(documentType, deletedBy);
        
        documentTypeDetail.setThruTime(session.getStartTime());
        documentType.setActiveDetail(null);
        documentType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultDocumentType = getDefaultDocumentType();

            if(defaultDocumentType == null) {
                var documentTypes = getDocumentTypesForUpdate();

                if(!documentTypes.isEmpty()) {
                    var iter = documentTypes.iterator();
                    if(iter.hasNext()) {
                        defaultDocumentType = iter.next();
                    }
                    var documentTypeDetailValue = Objects.requireNonNull(defaultDocumentType).getLastDetailForUpdate().getDocumentTypeDetailValue().clone();

                    documentTypeDetailValue.setIsDefault(true);
                    updateDocumentTypeFromValue(documentTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(documentType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteDocumentType(DocumentType itemDescriptionType, BasePK deletedBy) {
        deleteDocumentType(itemDescriptionType, true, deletedBy);
    }

    private void deleteDocumentTypes(List<DocumentType> itemDescriptionTypes, boolean checkDefault, BasePK deletedBy) {
        itemDescriptionTypes.forEach((itemDescriptionType) -> deleteDocumentType(itemDescriptionType, checkDefault, deletedBy));
    }

    public void deleteDocumentTypes(List<DocumentType> itemDescriptionTypes, BasePK deletedBy) {
        deleteDocumentTypes(itemDescriptionTypes, true, deletedBy);
    }

    private void deleteDocumentTypesByParentDocumentType(DocumentType parentDocumentType, BasePK deletedBy) {
        deleteDocumentTypes(getDocumentTypesByParentDocumentTypeForUpdate(parentDocumentType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentTypeDescriptionFactory documentTypeDescriptionFactory;

    @Inject
    DocumentTypeDescriptionTransferCache documentTypeDescriptionTransferCache;

    public DocumentTypeDescription createDocumentTypeDescription(DocumentType documentType, Language language, String description, BasePK createdBy) {
        var documentTypeDescription = documentTypeDescriptionFactory.create(documentType, language, description,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(documentType.getPrimaryKey(), EventTypes.MODIFY, documentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return documentTypeDescription;
    }
    
    private DocumentTypeDescription getDocumentTypeDescription(DocumentType documentType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypeDescriptions.fields())
                .from(DocumentTypeDescriptions)
                .where(DocumentTypeDescriptions.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()),
                        DocumentTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        DocumentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public DocumentTypeDescription getDocumentTypeDescription(DocumentType documentType, Language language) {
        return getDocumentTypeDescription(documentType, language, EntityPermission.READ_ONLY);
    }
    
    public DocumentTypeDescription getDocumentTypeDescriptionForUpdate(DocumentType documentType, Language language) {
        return getDocumentTypeDescription(documentType, language, EntityPermission.READ_WRITE);
    }
    
    public DocumentTypeDescriptionValue getDocumentTypeDescriptionValue(DocumentTypeDescription documentTypeDescription) {
        return documentTypeDescription == null ? null : documentTypeDescription.getDocumentTypeDescriptionValue().clone();
    }
    
    public DocumentTypeDescriptionValue getDocumentTypeDescriptionValueForUpdate(DocumentType documentType, Language language) {
        return getDocumentTypeDescriptionValue(getDocumentTypeDescriptionForUpdate(documentType, language));
    }
    
    private List<DocumentTypeDescription> getDocumentTypeDescriptionsByDocumentType(DocumentType documentType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(DocumentTypeDescriptions.fields())
                    .from(DocumentTypeDescriptions)
                    .join(Languages).onKey(DOCUMENT_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(DocumentTypeDescriptions.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()),
                            DocumentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    DocumentTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(DocumentTypeDescriptions.fields())
                    .from(DocumentTypeDescriptions)
                    .where(DocumentTypeDescriptions.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()),
                            DocumentTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return documentTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<DocumentTypeDescription> getDocumentTypeDescriptionsByDocumentType(DocumentType documentType) {
        return getDocumentTypeDescriptionsByDocumentType(documentType, EntityPermission.READ_ONLY);
    }
    
    public List<DocumentTypeDescription> getDocumentTypeDescriptionsByDocumentTypeForUpdate(DocumentType documentType) {
        return getDocumentTypeDescriptionsByDocumentType(documentType, EntityPermission.READ_WRITE);
    }
    
    public String getBestDocumentTypeDescription(DocumentType documentType, Language language) {
        String description;
        var documentTypeDescription = getDocumentTypeDescription(documentType, language);
        
        if(documentTypeDescription == null && !language.getIsDefault()) {
            documentTypeDescription = getDocumentTypeDescription(documentType, partyControl.getDefaultLanguage());
        }
        
        if(documentTypeDescription == null) {
            description = documentType.getLastDetail().getDocumentTypeName();
        } else {
            description = documentTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public DocumentTypeDescriptionTransfer getDocumentTypeDescriptionTransfer(UserVisit userVisit, DocumentTypeDescription documentTypeDescription) {
        return documentTypeDescriptionTransferCache.getDocumentTypeDescriptionTransfer(userVisit, documentTypeDescription);
    }
    
    public List<DocumentTypeDescriptionTransfer> getDocumentTypeDescriptionTransfersByDocumentType(UserVisit userVisit, DocumentType documentType) {
        var documentTypeDescriptions = getDocumentTypeDescriptionsByDocumentType(documentType);
        List<DocumentTypeDescriptionTransfer> documentTypeDescriptionTransfers = new ArrayList<>(documentTypeDescriptions.size());
        
        documentTypeDescriptions.forEach((documentTypeDescription) ->
                documentTypeDescriptionTransfers.add(documentTypeDescriptionTransferCache.getDocumentTypeDescriptionTransfer(userVisit, documentTypeDescription))
        );
        
        return documentTypeDescriptionTransfers;
    }
    
    public void updateDocumentTypeDescriptionFromValue(DocumentTypeDescriptionValue documentTypeDescriptionValue, BasePK updatedBy) {
        if(documentTypeDescriptionValue.hasBeenModified()) {
            var documentTypeDescription = documentTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    documentTypeDescriptionValue.getPrimaryKey());
            
            documentTypeDescription.setThruTime(session.getStartTime());
            documentTypeDescription.store();

            var documentType = documentTypeDescription.getDocumentType();
            var language = documentTypeDescription.getLanguage();
            var description = documentTypeDescriptionValue.getDescription();
            
            documentTypeDescription = documentTypeDescriptionFactory.create(documentType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(documentType.getPrimaryKey(), EventTypes.MODIFY, documentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDocumentTypeDescription(DocumentTypeDescription documentTypeDescription, BasePK deletedBy) {
        documentTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(documentTypeDescription.getDocumentTypePK(), EventTypes.MODIFY, documentTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteDocumentTypeDescriptionsByDocumentType(DocumentType documentType, BasePK deletedBy) {
        var documentTypeDescriptions = getDocumentTypeDescriptionsByDocumentTypeForUpdate(documentType);
        
        documentTypeDescriptions.forEach((documentTypeDescription) -> 
                deleteDocumentTypeDescription(documentTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Document Types
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentTypeUsageTypeDetailFactory documentTypeUsageTypeDetailFactory;

    @Inject
    protected DocumentTypeUsageTypeFactory documentTypeUsageTypeFactory;

    @Inject
    DocumentTypeUsageTypeTransferCache documentTypeUsageTypeTransferCache;

    public DocumentTypeUsageType createDocumentTypeUsageType(String documentTypeUsageTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultDocumentTypeUsageType = getDefaultDocumentTypeUsageType();
        var defaultFound = defaultDocumentTypeUsageType != null;

        if(defaultFound && isDefault) {
            var defaultDocumentTypeUsageTypeDetailValue = getDefaultDocumentTypeUsageTypeDetailValueForUpdate();

            defaultDocumentTypeUsageTypeDetailValue.setIsDefault(false);
            updateDocumentTypeUsageTypeFromValue(defaultDocumentTypeUsageTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var documentTypeUsageType = documentTypeUsageTypeFactory.create();
        var documentTypeUsageTypeDetail = documentTypeUsageTypeDetailFactory.create(documentTypeUsageType,
                documentTypeUsageTypeName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        documentTypeUsageType = documentTypeUsageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                documentTypeUsageType.getPrimaryKey());
        documentTypeUsageType.setActiveDetail(documentTypeUsageTypeDetail);
        documentTypeUsageType.setLastDetail(documentTypeUsageTypeDetail);
        documentTypeUsageType.store();

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return documentTypeUsageType;
    }

    /** Assume that the entityInstance passed to this function is an ECHO_THREE.DocumentTypeUsageType */
    public DocumentTypeUsageType getDocumentTypeUsageTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new DocumentTypeUsageTypePK(entityInstance.getEntityUniqueId());

        return documentTypeUsageTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByEntityInstance(EntityInstance entityInstance) {
        return getDocumentTypeUsageTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getDocumentTypeUsageTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countDocumentTypeUsageType() {
        return session.getDslContext()
                .selectCount()
                .from(DocumentTypeUsageTypes)
                .join(DocumentTypeUsageTypeDetails).onKey(DOCUMENT_TYPE_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByName(String documentTypeUsageTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypeUsageTypes.fields())
                .from(DocumentTypeUsageTypes)
                .join(DocumentTypeUsageTypeDetails).onKey(DOCUMENT_TYPE_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(DocumentTypeUsageTypeDetails.DOCUMENT_TYPE_USAGE_TYPE_NAME.eq(documentTypeUsageTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeUsageTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByName(String documentTypeUsageTypeName) {
        return getDocumentTypeUsageTypeByName(documentTypeUsageTypeName, EntityPermission.READ_ONLY);
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByNameForUpdate(String documentTypeUsageTypeName) {
        return getDocumentTypeUsageTypeByName(documentTypeUsageTypeName, EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageTypeDetailValue getDocumentTypeUsageTypeDetailValueForUpdate(DocumentTypeUsageType itemDescriptionType) {
        return itemDescriptionType == null ? null : itemDescriptionType.getLastDetailForUpdate().getDocumentTypeUsageTypeDetailValue().clone();
    }

    public DocumentTypeUsageTypeDetailValue getDocumentTypeUsageTypeDetailValueByNameForUpdate(String documentTypeUsageTypeName) {
        return getDocumentTypeUsageTypeDetailValueForUpdate(getDocumentTypeUsageTypeByNameForUpdate(documentTypeUsageTypeName));
    }

    public DocumentTypeUsageType getDefaultDocumentTypeUsageType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypeUsageTypes.fields())
                .from(DocumentTypeUsageTypes)
                .join(DocumentTypeUsageTypeDetails).onKey(DOCUMENT_TYPE_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .where(DocumentTypeUsageTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeUsageTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public DocumentTypeUsageType getDefaultDocumentTypeUsageType() {
        return getDefaultDocumentTypeUsageType(EntityPermission.READ_ONLY);
    }

    public DocumentTypeUsageType getDefaultDocumentTypeUsageTypeForUpdate() {
        return getDefaultDocumentTypeUsageType(EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageTypeDetailValue getDefaultDocumentTypeUsageTypeDetailValueForUpdate() {
        return getDefaultDocumentTypeUsageTypeForUpdate().getLastDetailForUpdate().getDocumentTypeUsageTypeDetailValue().clone();
    }

    public long countDocumentTypeUsageTypes() {
        return session.getDslContext()
                .selectCount()
                .from(DocumentTypeUsageTypes)
                .join(DocumentTypeUsageTypeDetails).onKey(DOCUMENT_TYPE_USAGE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<DocumentTypeUsageType> getDocumentTypeUsageTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypeUsageTypes.fields())
                .from(DocumentTypeUsageTypes)
                .join(DocumentTypeUsageTypeDetails).onKey(DOCUMENT_TYPE_USAGE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(DocumentTypeUsageTypeDetails.SORT_ORDER, DocumentTypeUsageTypeDetails.DOCUMENT_TYPE_USAGE_TYPE_NAME),
                    DocumentTypeUsageTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeUsageTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<DocumentTypeUsageType> getDocumentTypeUsageTypes() {
        return getDocumentTypeUsageTypes(EntityPermission.READ_ONLY);
    }

    public List<DocumentTypeUsageType> getDocumentTypeUsageTypesForUpdate() {
        return getDocumentTypeUsageTypes(EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageTypeTransfer getDocumentTypeUsageTypeTransfer(UserVisit userVisit, DocumentTypeUsageType documentTypeUsageType) {
        return documentTypeUsageTypeTransferCache.getDocumentTypeUsageTypeTransfer(userVisit, documentTypeUsageType);
    }

    public List<DocumentTypeUsageTypeTransfer> getDocumentTypeUsageTypeTransfers(UserVisit userVisit,
            Collection<DocumentTypeUsageType> documentTypeUsageTypes) {
        List<DocumentTypeUsageTypeTransfer> documentTypeUsageTypeTransfers = new ArrayList<>(documentTypeUsageTypes.size());

        documentTypeUsageTypes.forEach((documentTypeUsageType) ->
                documentTypeUsageTypeTransfers.add(documentTypeUsageTypeTransferCache.getDocumentTypeUsageTypeTransfer(userVisit, documentTypeUsageType))
        );

        return documentTypeUsageTypeTransfers;
    }

    public List<DocumentTypeUsageTypeTransfer> getDocumentTypeUsageTypeTransfers(UserVisit userVisit) {
        return getDocumentTypeUsageTypeTransfers(userVisit, getDocumentTypeUsageTypes());
    }

    public DocumentTypeUsageTypeChoicesBean getDocumentTypeUsageTypeChoices(String defaultDocumentTypeUsageTypeChoice, Language language, boolean allowNullChoice) {
        var documentTypeUsageTypes = getDocumentTypeUsageTypes();
        var size = documentTypeUsageTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultDocumentTypeUsageTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var documentTypeUsageType : documentTypeUsageTypes) {
            var documentTypeUsageTypeDetail = documentTypeUsageType.getLastDetail();

            var label = getBestDocumentTypeUsageTypeDescription(documentTypeUsageType, language);
            var value = documentTypeUsageTypeDetail.getDocumentTypeUsageTypeName();

            labels.add(label == null ? value: label);
            values.add(value);

            var usingDefaultChoice = defaultDocumentTypeUsageTypeChoice == null ? false: defaultDocumentTypeUsageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && documentTypeUsageTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new DocumentTypeUsageTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateDocumentTypeUsageTypeFromValue(DocumentTypeUsageTypeDetailValue documentTypeUsageTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(documentTypeUsageTypeDetailValue.hasBeenModified()) {
            var documentTypeUsageType = documentTypeUsageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeUsageTypeDetailValue.getDocumentTypeUsageTypePK());
            var documentTypeUsageTypeDetail = documentTypeUsageType.getActiveDetailForUpdate();

            documentTypeUsageTypeDetail.setThruTime(session.getStartTime());
            documentTypeUsageTypeDetail.store();

            var documentTypeUsageTypePK = documentTypeUsageTypeDetail.getDocumentTypeUsageTypePK(); // Not updated
            var documentTypeUsageTypeName = documentTypeUsageTypeDetailValue.getDocumentTypeUsageTypeName();
            var isDefault = documentTypeUsageTypeDetailValue.getIsDefault();
            var sortOrder = documentTypeUsageTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultDocumentTypeUsageType = getDefaultDocumentTypeUsageType();
                var defaultFound = defaultDocumentTypeUsageType != null && !defaultDocumentTypeUsageType.equals(documentTypeUsageType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultDocumentTypeUsageTypeDetailValue = getDefaultDocumentTypeUsageTypeDetailValueForUpdate();

                    defaultDocumentTypeUsageTypeDetailValue.setIsDefault(false);
                    updateDocumentTypeUsageTypeFromValue(defaultDocumentTypeUsageTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            documentTypeUsageTypeDetail = documentTypeUsageTypeDetailFactory.create(documentTypeUsageTypePK, documentTypeUsageTypeName, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            documentTypeUsageType.setActiveDetail(documentTypeUsageTypeDetail);
            documentTypeUsageType.setLastDetail(documentTypeUsageTypeDetail);

            sendEvent(documentTypeUsageTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateDocumentTypeUsageTypeFromValue(DocumentTypeUsageTypeDetailValue documentTypeUsageTypeDetailValue, BasePK updatedBy) {
        updateDocumentTypeUsageTypeFromValue(documentTypeUsageTypeDetailValue, true, updatedBy);
    }

    private void deleteDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, boolean checkDefault, BasePK deletedBy) {
        var documentTypeUsageTypeDetail = documentTypeUsageType.getLastDetailForUpdate();

        deleteDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(documentTypeUsageType, deletedBy);
        deleteDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType, deletedBy);
        deletePartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType, deletedBy);

        documentTypeUsageTypeDetail.setThruTime(session.getStartTime());
        documentTypeUsageType.setActiveDetail(null);
        documentTypeUsageType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultDocumentTypeUsageType = getDefaultDocumentTypeUsageType();

            if(defaultDocumentTypeUsageType == null) {
                var documentTypeUsageTypes = getDocumentTypeUsageTypesForUpdate();

                if(!documentTypeUsageTypes.isEmpty()) {
                    var iter = documentTypeUsageTypes.iterator();
                    if(iter.hasNext()) {
                        defaultDocumentTypeUsageType = iter.next();
                    }
                    var documentTypeUsageTypeDetailValue = Objects.requireNonNull(defaultDocumentTypeUsageType).getLastDetailForUpdate().getDocumentTypeUsageTypeDetailValue().clone();

                    documentTypeUsageTypeDetailValue.setIsDefault(true);
                    updateDocumentTypeUsageTypeFromValue(documentTypeUsageTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteDocumentTypeUsageType(DocumentTypeUsageType itemDescriptionType, BasePK deletedBy) {
        deleteDocumentTypeUsageType(itemDescriptionType, true, deletedBy);
    }

    private void deleteDocumentTypeUsageTypes(List<DocumentTypeUsageType> itemDescriptionTypes, boolean checkDefault, BasePK deletedBy) {
        itemDescriptionTypes.forEach((itemDescriptionType) -> deleteDocumentTypeUsageType(itemDescriptionType, checkDefault, deletedBy));
    }

    public void deleteDocumentTypeUsageTypes(List<DocumentTypeUsageType> itemDescriptionTypes, BasePK deletedBy) {
        deleteDocumentTypeUsageTypes(itemDescriptionTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Document Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentTypeUsageTypeDescriptionFactory documentTypeUsageTypeDescriptionFactory;

    @Inject
    DocumentTypeUsageTypeDescriptionTransferCache documentTypeUsageTypeDescriptionTransferCache;

    public DocumentTypeUsageTypeDescription createDocumentTypeUsageTypeDescription(DocumentTypeUsageType documentTypeUsageType, Language language, String description, BasePK createdBy) {
        var documentTypeUsageTypeDescription = documentTypeUsageTypeDescriptionFactory.create(documentTypeUsageType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, documentTypeUsageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return documentTypeUsageTypeDescription;
    }

    private DocumentTypeUsageTypeDescription getDocumentTypeUsageTypeDescription(DocumentTypeUsageType documentTypeUsageType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypeUsageTypeDescriptions.fields())
                .from(DocumentTypeUsageTypeDescriptions)
                .where(DocumentTypeUsageTypeDescriptions.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                        DocumentTypeUsageTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        DocumentTypeUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeUsageTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public DocumentTypeUsageTypeDescription getDocumentTypeUsageTypeDescription(DocumentTypeUsageType documentTypeUsageType, Language language) {
        return getDocumentTypeUsageTypeDescription(documentTypeUsageType, language, EntityPermission.READ_ONLY);
    }

    public DocumentTypeUsageTypeDescription getDocumentTypeUsageTypeDescriptionForUpdate(DocumentTypeUsageType documentTypeUsageType, Language language) {
        return getDocumentTypeUsageTypeDescription(documentTypeUsageType, language, EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageTypeDescriptionValue getDocumentTypeUsageTypeDescriptionValue(DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        return documentTypeUsageTypeDescription == null ? null : documentTypeUsageTypeDescription.getDocumentTypeUsageTypeDescriptionValue().clone();
    }

    public DocumentTypeUsageTypeDescriptionValue getDocumentTypeUsageTypeDescriptionValueForUpdate(DocumentTypeUsageType documentTypeUsageType, Language language) {
        return getDocumentTypeUsageTypeDescriptionValue(getDocumentTypeUsageTypeDescriptionForUpdate(documentTypeUsageType, language));
    }

    private List<DocumentTypeUsageTypeDescription> getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(DocumentTypeUsageTypeDescriptions.fields())
                    .from(DocumentTypeUsageTypeDescriptions)
                    .join(Languages).onKey(DOCUMENT_TYPE_USAGE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(DocumentTypeUsageTypeDescriptions.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                            DocumentTypeUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    DocumentTypeUsageTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(DocumentTypeUsageTypeDescriptions.fields())
                    .from(DocumentTypeUsageTypeDescriptions)
                    .where(DocumentTypeUsageTypeDescriptions.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                            DocumentTypeUsageTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return documentTypeUsageTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<DocumentTypeUsageTypeDescription> getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType) {
        return getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<DocumentTypeUsageTypeDescription> getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageTypeForUpdate(DocumentTypeUsageType documentTypeUsageType) {
        return getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_WRITE);
    }

    public String getBestDocumentTypeUsageTypeDescription(DocumentTypeUsageType documentTypeUsageType, Language language) {
        String description;
        var documentTypeUsageTypeDescription = getDocumentTypeUsageTypeDescription(documentTypeUsageType, language);

        if(documentTypeUsageTypeDescription == null && !language.getIsDefault()) {
            documentTypeUsageTypeDescription = getDocumentTypeUsageTypeDescription(documentTypeUsageType, partyControl.getDefaultLanguage());
        }

        if(documentTypeUsageTypeDescription == null) {
            description = documentTypeUsageType.getLastDetail().getDocumentTypeUsageTypeName();
        } else {
            description = documentTypeUsageTypeDescription.getDescription();
        }

        return description;
    }

    public DocumentTypeUsageTypeDescriptionTransfer getDocumentTypeUsageTypeDescriptionTransfer(UserVisit userVisit, DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        return documentTypeUsageTypeDescriptionTransferCache.getDocumentTypeUsageTypeDescriptionTransfer(userVisit, documentTypeUsageTypeDescription);
    }

    public List<DocumentTypeUsageTypeDescriptionTransfer> getDocumentTypeUsageTypeDescriptionTransfersByDocumentTypeUsageType(UserVisit userVisit, DocumentTypeUsageType documentTypeUsageType) {
        var documentTypeUsageTypeDescriptions = getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(documentTypeUsageType);
        List<DocumentTypeUsageTypeDescriptionTransfer> documentTypeUsageTypeDescriptionTransfers = new ArrayList<>(documentTypeUsageTypeDescriptions.size());

        documentTypeUsageTypeDescriptions.forEach((documentTypeUsageTypeDescription) ->
                documentTypeUsageTypeDescriptionTransfers.add(documentTypeUsageTypeDescriptionTransferCache.getDocumentTypeUsageTypeDescriptionTransfer(userVisit, documentTypeUsageTypeDescription))
        );

        return documentTypeUsageTypeDescriptionTransfers;
    }

    public void updateDocumentTypeUsageTypeDescriptionFromValue(DocumentTypeUsageTypeDescriptionValue documentTypeUsageTypeDescriptionValue, BasePK updatedBy) {
        if(documentTypeUsageTypeDescriptionValue.hasBeenModified()) {
            var documentTypeUsageTypeDescription = documentTypeUsageTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    documentTypeUsageTypeDescriptionValue.getPrimaryKey());

            documentTypeUsageTypeDescription.setThruTime(session.getStartTime());
            documentTypeUsageTypeDescription.store();

            var documentTypeUsageType = documentTypeUsageTypeDescription.getDocumentTypeUsageType();
            var language = documentTypeUsageTypeDescription.getLanguage();
            var description = documentTypeUsageTypeDescriptionValue.getDescription();

            documentTypeUsageTypeDescription = documentTypeUsageTypeDescriptionFactory.create(documentTypeUsageType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, documentTypeUsageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteDocumentTypeUsageTypeDescription(DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription, BasePK deletedBy) {
        documentTypeUsageTypeDescription.setThruTime(session.getStartTime());

        sendEvent(documentTypeUsageTypeDescription.getDocumentTypeUsageTypePK(), EventTypes.MODIFY, documentTypeUsageTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, BasePK deletedBy) {
        var documentTypeUsageTypeDescriptions = getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageTypeForUpdate(documentTypeUsageType);

        documentTypeUsageTypeDescriptions.forEach((documentTypeUsageTypeDescription) -> 
                deleteDocumentTypeUsageTypeDescription(documentTypeUsageTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usages
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentTypeUsageFactory documentTypeUsageFactory;

    @Inject
    DocumentTypeUsageTransferCache documentTypeUsageTransferCache;

    public DocumentTypeUsage createDocumentTypeUsage(DocumentTypeUsageType documentTypeUsageType, DocumentType documentType, Boolean isDefault,
            Integer sortOrder, Integer maximumInstances, BasePK createdBy) {
        var defaultDocumentTypeUsage = getDefaultDocumentTypeUsage(documentTypeUsageType);
        var defaultFound = defaultDocumentTypeUsage != null;

        if(defaultFound && isDefault) {
            var defaultDocumentTypeUsageValue = getDefaultDocumentTypeUsageValueForUpdate(documentTypeUsageType);

            defaultDocumentTypeUsageValue.setIsDefault(false);
            updateDocumentTypeUsageFromValue(defaultDocumentTypeUsageValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var documentTypeUsage = documentTypeUsageFactory.create(documentTypeUsageType, documentType, isDefault, sortOrder,
                maximumInstances, session.getStartTime(), Session.MAX_TIME);

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, documentTypeUsage.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return documentTypeUsage;
    }

    public long countDocumentTypeUsageByDocumentTypeUsageType(final DocumentTypeUsageType documentTypeUsageType) {
        return session.getDslContext()
                .selectCount()
                .from(DocumentTypeUsages)
                .where(DocumentTypeUsages.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                        DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countDocumentTypeUsageByDocumentType(final DocumentType documentType) {
        return session.getDslContext()
                .selectCount()
                .from(DocumentTypeUsages)
                .where(DocumentTypeUsages.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()),
                        DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private DocumentTypeUsage getDocumentTypeUsage(DocumentTypeUsageType documentTypeUsageType, DocumentType documentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypeUsages.fields())
                .from(DocumentTypeUsages)
                .where(DocumentTypeUsages.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                        DocumentTypeUsages.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()),
                        DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeUsageFactory.getEntityFromQuery(entityPermission, query);
    }

    public DocumentTypeUsage getDocumentTypeUsage(DocumentTypeUsageType documentTypeUsageType, DocumentType documentType) {
        return getDocumentTypeUsage(documentTypeUsageType, documentType, EntityPermission.READ_ONLY);
    }

    public DocumentTypeUsage getDocumentTypeUsageForUpdate(DocumentTypeUsageType documentTypeUsageType, DocumentType documentType) {
        return getDocumentTypeUsage(documentTypeUsageType, documentType, EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageValue getDocumentTypeUsageValueForUpdate(DocumentTypeUsage documentTypeUsage) {
        return documentTypeUsage == null ? null : documentTypeUsage.getDocumentTypeUsageValue().clone();
    }

    public DocumentTypeUsageValue getDocumentTypeUsageValueForUpdate(DocumentTypeUsageType documentTypeUsageType, DocumentType documentType) {
        return getDocumentTypeUsageValueForUpdate(getDocumentTypeUsageForUpdate(documentTypeUsageType, documentType));
    }

    private DocumentTypeUsage getDefaultDocumentTypeUsage(DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentTypeUsages.fields())
                .from(DocumentTypeUsages)
                .where(DocumentTypeUsages.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                        DocumentTypeUsages.IS_DEFAULT.eq(true),
                        DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentTypeUsageFactory.getEntityFromQuery(entityPermission, query);
    }

    public DocumentTypeUsage getDefaultDocumentTypeUsage(DocumentTypeUsageType documentTypeUsageType) {
        return getDefaultDocumentTypeUsage(documentTypeUsageType, EntityPermission.READ_ONLY);
    }

    public DocumentTypeUsage getDefaultDocumentTypeUsageForUpdate(DocumentTypeUsageType documentTypeUsageType) {
        return getDefaultDocumentTypeUsage(documentTypeUsageType, EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageValue getDefaultDocumentTypeUsageValueForUpdate(DocumentTypeUsageType documentTypeUsageType) {
        return getDefaultDocumentTypeUsageForUpdate(documentTypeUsageType).getDocumentTypeUsageValue().clone();
    }

    public long countDocumentTypeUsagesByDocumentTypeUsageType(final DocumentTypeUsageType documentTypeUsageType) {
        return session.getDslContext()
                .selectCount()
                .from(DocumentTypeUsages)
                .where(DocumentTypeUsages.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                        DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countDocumentTypeUsagesByDocumentType(final DocumentType documentType) {
        return session.getDslContext()
                .selectCount()
                .from(DocumentTypeUsages)
                .where(DocumentTypeUsages.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()),
                        DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(DocumentTypeUsages.fields())
                    .from(DocumentTypeUsages)
                    .join(DocumentTypes).onKey(DOCUMENT_TYPE_USAGES_DOCUMENT_TYPE_FK)
                    .join(DocumentTypeDetails).onKey(DOCUMENT_TYPES_LAST_DETAIL_FK)
                    .where(DocumentTypeUsages.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                            DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(DocumentTypeDetails.SORT_ORDER, DocumentTypeDetails.DOCUMENT_TYPE_NAME),
                    DocumentTypeUsageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(DocumentTypeUsages.fields())
                    .from(DocumentTypeUsages)
                    .where(DocumentTypeUsages.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                            DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return documentTypeUsageFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType) {
        return getDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentTypeUsageTypeForUpdate(DocumentTypeUsageType documentTypeUsageType) {
        return getDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_WRITE);
    }

    private List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentType(DocumentType documentType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(DocumentTypeUsages.fields())
                    .from(DocumentTypeUsages)
                    .join(DocumentTypeUsageTypes).onKey(DOCUMENT_TYPE_USAGES_DOCUMENT_TYPE_USAGE_TYPE_FK)
                    .join(DocumentTypeUsageTypeDetails).onKey(DOCUMENT_TYPE_USAGE_TYPES_LAST_DETAIL_FK)
                    .where(DocumentTypeUsages.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()),
                            DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(DocumentTypeUsageTypeDetails.SORT_ORDER, DocumentTypeUsageTypeDetails.DOCUMENT_TYPE_USAGE_TYPE_NAME),
                    DocumentTypeUsageFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(DocumentTypeUsages.fields())
                    .from(DocumentTypeUsages)
                    .where(DocumentTypeUsages.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()),
                            DocumentTypeUsages.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return documentTypeUsageFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentType(DocumentType documentType) {
        return getDocumentTypeUsagesByDocumentType(documentType, EntityPermission.READ_ONLY);
    }

    public List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentTypeForUpdate(DocumentType documentType) {
        return getDocumentTypeUsagesByDocumentType(documentType, EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageTransfer getDocumentTypeUsageTransfer(UserVisit userVisit, DocumentTypeUsage documentTypeUsage) {
        return documentTypeUsageTransferCache.getDocumentTypeUsageTransfer(userVisit, documentTypeUsage);
    }

    public List<DocumentTypeUsageTransfer> getDocumentTypeUsageTransfers(UserVisit userVisit, Collection<DocumentTypeUsage> documentTypeUsages) {
        List<DocumentTypeUsageTransfer> documentTypeUsageTransfers = new ArrayList<>(documentTypeUsages.size());

        documentTypeUsages.forEach((documentTypeUsage) ->
                documentTypeUsageTransfers.add(documentTypeUsageTransferCache.getDocumentTypeUsageTransfer(userVisit, documentTypeUsage))
        );

        return documentTypeUsageTransfers;
    }

    public List<DocumentTypeUsageTransfer> getDocumentTypeUsageTransfersByDocumentTypeUsageType(UserVisit userVisit, DocumentTypeUsageType documentTypeUsageType) {
        return getDocumentTypeUsageTransfers(userVisit, getDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType));
    }

    public List<DocumentTypeUsageTransfer> getDocumentTypeUsageTransfersByDocumentType(UserVisit userVisit, DocumentType documentType) {
        return getDocumentTypeUsageTransfers(userVisit, getDocumentTypeUsagesByDocumentType(documentType));
    }

    private void updateDocumentTypeUsageFromValue(DocumentTypeUsageValue documentTypeUsageValue, boolean checkDefault, BasePK updatedBy) {
        if(documentTypeUsageValue.hasBeenModified()) {
            var documentTypeUsage = documentTypeUsageFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeUsageValue.getPrimaryKey());

            documentTypeUsage.setThruTime(session.getStartTime());
            documentTypeUsage.store();

            var documentTypeUsageType = documentTypeUsage.getDocumentTypeUsageType();
            var documentTypeUsageTypePK = documentTypeUsageType.getPrimaryKey(); // Not updated
            var documentTypePK = documentTypeUsage.getDocumentTypePK(); // Not updated
            var isDefault = documentTypeUsageValue.getIsDefault();
            var sortOrder = documentTypeUsageValue.getSortOrder();
            var maximumInstances = documentTypeUsageValue.getMaximumInstances();

            if(checkDefault) {
                var defaultDocumentTypeUsage = getDefaultDocumentTypeUsage(documentTypeUsageType);
                var defaultFound = defaultDocumentTypeUsage != null && !defaultDocumentTypeUsage.equals(documentTypeUsage);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultDocumentTypeUsageValue = getDefaultDocumentTypeUsageValueForUpdate(documentTypeUsageType);

                    defaultDocumentTypeUsageValue.setIsDefault(false);
                    updateDocumentTypeUsageFromValue(defaultDocumentTypeUsageValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            documentTypeUsage = documentTypeUsageFactory.create(documentTypeUsageTypePK, documentTypePK, isDefault,
                    sortOrder, maximumInstances, session.getStartTime(), Session.MAX_TIME);

            sendEvent(documentTypeUsageTypePK, EventTypes.MODIFY, documentTypeUsage.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updateDocumentTypeUsageFromValue(DocumentTypeUsageValue documentTypeUsageValue, BasePK updatedBy) {
        updateDocumentTypeUsageFromValue(documentTypeUsageValue, true, updatedBy);
    }

    private void deleteDocumentTypeUsage(DocumentTypeUsage documentTypeUsage, boolean checkDefault, BasePK deletedBy) {
        var documentTypeUsageType = documentTypeUsage.getDocumentTypeUsageType();

        documentTypeUsage.setThruTime(session.getStartTime());
        documentTypeUsage.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultDocumentTypeUsage = getDefaultDocumentTypeUsage(documentTypeUsageType);

            if(defaultDocumentTypeUsage == null) {
                var documentTypeUsages = getDocumentTypeUsagesByDocumentTypeUsageTypeForUpdate(documentTypeUsageType);

                if(!documentTypeUsages.isEmpty()) {
                    var iter = documentTypeUsages.iterator();
                    if(iter.hasNext()) {
                        defaultDocumentTypeUsage = iter.next();
                    }
                    var documentTypeUsageDetailValue = defaultDocumentTypeUsage.getDocumentTypeUsageValue().clone();

                    documentTypeUsageDetailValue.setIsDefault(true);
                    updateDocumentTypeUsageFromValue(documentTypeUsageDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, documentTypeUsage.getPrimaryKey(), EventTypes.DELETE,deletedBy);
    }

    public void deleteDocumentTypeUsage(DocumentTypeUsage itemDescriptionType, BasePK deletedBy) {
        deleteDocumentTypeUsage(itemDescriptionType, true, deletedBy);
    }

    private void deleteDocumentTypeUsages(List<DocumentTypeUsage> documentTypeUsages, boolean checkDefault, BasePK deletedBy) {
        documentTypeUsages.forEach((documentTypeUsage) -> deleteDocumentTypeUsage(documentTypeUsage, checkDefault, deletedBy));
    }

    public void deleteDocumentTypeUsages(List<DocumentTypeUsage> itemDescriptionTypes, BasePK deletedBy) {
        deleteDocumentTypeUsages(itemDescriptionTypes, true, deletedBy);
    }

    public void deleteDocumentTypeUsagesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, BasePK deletedBy) {
        deleteDocumentTypeUsages(getDocumentTypeUsagesByDocumentTypeUsageTypeForUpdate(documentTypeUsageType), false, deletedBy);
    }

    public void deleteDocumentTypeUsagesByDocumentType(DocumentType documentType, BasePK deletedBy) {
        deleteDocumentTypeUsages(getDocumentTypeUsagesByDocumentTypeForUpdate(documentType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Documents
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentDetailFactory documentDetailFactory;

    @Inject
    protected DocumentFactory documentFactory;

    @Inject
    DocumentTransferCache documentTransferCache;

    public Document createDocument(DocumentType documentType, MimeType mimeType, Integer pages, BasePK createdBy) {
        var sequence = sequenceControl.getDefaultSequence(sequenceControl.getSequenceTypeByName(SequenceTypes.DOCUMENT.name()));
        var documentName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
        
        return createDocument(documentName, documentType, mimeType, pages, createdBy);
    }
    
    public Document createDocument(String documentName, DocumentType documentType, MimeType mimeType, Integer pages, BasePK createdBy) {
        var document = documentFactory.create();
        var documentDetail = documentDetailFactory.create(document, documentName, documentType, mimeType, pages,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        document = documentFactory.getEntityFromPK(EntityPermission.READ_WRITE, document.getPrimaryKey());
        document.setActiveDetail(documentDetail);
        document.setLastDetail(documentDetail);
        document.store();
        
        sendEvent(document.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return document;
    }

    /** Assume that the entityInstance passed to this function is an ECHO_THREE.Document */
    public Document getDocumentByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new DocumentPK(entityInstance.getEntityUniqueId());

        return documentFactory.getEntityFromPK(entityPermission, pk);
    }

    public Document getDocumentByEntityInstance(EntityInstance entityInstance) {
        return getDocumentByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Document getDocumentByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getDocumentByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public long countDocumentsByDocumentType(DocumentType documentType) {
        return session.getDslContext()
                .selectCount()
                .from(Documents)
                .join(DocumentDetails).onKey(DOCUMENTS_ACTIVE_DETAIL_FK)
                .where(DocumentDetails.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public Document getDocumentByName(String documentName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Documents.fields())
                .from(Documents)
                .join(DocumentDetails).onKey(DOCUMENTS_ACTIVE_DETAIL_FK)
                .where(DocumentDetails.DOCUMENT_NAME.eq(documentName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public Document getDocumentByName(String documentName) {
        return getDocumentByName(documentName, EntityPermission.READ_ONLY);
    }
    
    public Document getDocumentByNameForUpdate(String documentName) {
        return getDocumentByName(documentName, EntityPermission.READ_WRITE);
    }
    
    public DocumentDetailValue getDocumentDetailValueForUpdate(Document document) {
        return document == null ? null : document.getLastDetailForUpdate().getDocumentDetailValue().clone();
    }

    public DocumentDetailValue getDocumentDetailValueByNameForUpdate(String documentName) {
        return getDocumentDetailValueForUpdate(getDocumentByNameForUpdate(documentName));
    }

    private List<Document> getDocumentsByDocumentType(DocumentType documentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Documents.fields())
                .from(Documents)
                .join(DocumentDetails).onKey(DOCUMENTS_ACTIVE_DETAIL_FK)
                .where(DocumentDetails.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(DocumentDetails.DOCUMENT_NAME),
                    DocumentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Document> getDocumentsByDocumentType(DocumentType documentType) {
        return getDocumentsByDocumentType(documentType, EntityPermission.READ_ONLY);
    }

    public List<Document> getDocumentsByDocumentTypeForUpdate(DocumentType documentType) {
        return getDocumentsByDocumentType(documentType, EntityPermission.READ_WRITE);
    }

    public DocumentTransfer getDocumentTransfer(UserVisit userVisit, Document document) {
        return documentTransferCache.getDocumentTransfer(userVisit, document);
    }
    
    public void updateDocumentFromValue(DocumentDetailValue documentDetailValue, BasePK updatedBy) {
        if(documentDetailValue.hasBeenModified()) {
            var document = documentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     documentDetailValue.getDocumentPK());
            var documentDetail = document.getActiveDetailForUpdate();

            documentDetail.setThruTime(session.getStartTime());
            documentDetail.store();

            var documentPK = documentDetail.getDocumentPK(); // Not updated
            var documentName = documentDetail.getDocumentName(); // Not updated
            var documentTypePK = documentDetail.getDocumentTypePK(); // Not updated
            var mimeTypePK = documentDetailValue.getMimeTypePK();
            var pages = documentDetailValue.getPages();

            documentDetail = documentDetailFactory.create(documentPK, documentName, documentTypePK, mimeTypePK, pages, session.getStartTime(),
                    Session.MAX_TIME);

            document.setActiveDetail(documentDetail);
            document.setLastDetail(documentDetail);

            sendEvent(documentPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteDocument(Document document, BasePK deletedBy) {
        deleteDocumentDescriptionsByDocument(document, deletedBy);

        var documentDetail = document.getLastDetailForUpdate();
        documentDetail.setThruTime(session.getStartTime());
        documentDetail.store();
        document.setActiveDetail(null);

        var entityAttributeTypeName = documentDetail.getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            deleteDocumentBlobByDocument(document, deletedBy);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            deleteDocumentClobByDocument(document, deletedBy);
        }
        
        sendEvent(document.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteDocuments(List<Document> documents, BasePK deletedBy) {
        documents.forEach((document) -> 
                deleteDocument(document, deletedBy)
        );
    }

    public void deleteDocumentsByDocumentType(DocumentType documentType, BasePK deletedBy) {
        deleteDocuments(getDocumentsByDocumentTypeForUpdate(documentType), deletedBy);
    }

    public void removeDocument(Document document) {

        document.remove();
        entityInstanceControl.removeEntityInstanceByBasePK(document.getPrimaryKey());
    }
    
    // --------------------------------------------------------------------------------
    //   Document Utilities
    // --------------------------------------------------------------------------------

    private void verifyDocumentMimeType(Document document, String entityAttributeTypeName) {
        var documentEntityAttributeTypeName = document.getLastDetail().getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(!documentEntityAttributeTypeName.equals(entityAttributeTypeName)) {
            throw new IllegalArgumentException("Document entityAttributeTypeName is " + documentEntityAttributeTypeName + ", expected " + entityAttributeTypeName);
        }
    }

    // --------------------------------------------------------------------------------
    //   Document Blobs
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentBlobFactory documentBlobFactory;

    public DocumentBlob createDocumentBlob(Document document, ByteArray blob, BasePK createdBy) {
        verifyDocumentMimeType(document, EntityAttributeTypes.BLOB.name());

        var documentBlob = documentBlobFactory.create(document, blob, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(document.getPrimaryKey(), EventTypes.MODIFY, documentBlob.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return documentBlob;
    }
    
    private DocumentBlob getDocumentBlob(Document document, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentBlobs.fields())
                .from(DocumentBlobs)
                .where(DocumentBlobs.DOCUMENT.eq(document.getPrimaryKey()),
                        DocumentBlobs.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentBlobFactory.getEntityFromQuery(entityPermission, query);
    }
    
    
    public DocumentBlob getDocumentBlob(Document document) {
        return getDocumentBlob(document, EntityPermission.READ_ONLY);
    }
    
    public DocumentBlob getDocumentBlobForUpdate(Document document) {
        return getDocumentBlob(document, EntityPermission.READ_WRITE);
    }
    
    public DocumentBlobValue getDocumentBlobValue(DocumentBlob documentBlob) {
        return documentBlob == null ? null : documentBlob.getDocumentBlobValue().clone();
    }
    
    public DocumentBlobValue getDocumentBlobValueForUpdate(Document document) {
        return getDocumentBlobValue(getDocumentBlobForUpdate(document));
    }
    
    public void updateDocumentBlobFromValue(DocumentBlobValue documentBlobValue, BasePK updatedBy) {
        if(documentBlobValue.hasBeenModified()) {
            var documentBlob = documentBlobFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    documentBlobValue.getPrimaryKey());
            
            documentBlob.setThruTime(session.getStartTime());
            documentBlob.store();

            var documentPK = documentBlob.getDocumentPK(); // Not updated
            var blob = documentBlobValue.getBlob();
            
            documentBlob = documentBlobFactory.create(documentPK, blob, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(documentBlob.getDocumentPK(), EventTypes.MODIFY, documentBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDocumentBlob(DocumentBlob documentBlob, BasePK deletedBy) {
        documentBlob.setThruTime(session.getStartTime());
        
        sendEvent(documentBlob.getDocumentPK(), EventTypes.MODIFY, documentBlob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteDocumentBlobByDocument(Document document, BasePK deletedBy) {
        var documentBlob = getDocumentBlobForUpdate(document);
        
        if(documentBlob != null) {
            deleteDocumentBlob(documentBlob, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Document Clobs
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentClobFactory documentClobFactory;

    public DocumentClob createDocumentClob(Document document, String clob, BasePK createdBy) {
        verifyDocumentMimeType(document, EntityAttributeTypes.CLOB.name());

        var documentClob = documentClobFactory.create(document, clob, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(document.getPrimaryKey(), EventTypes.MODIFY, documentClob.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return documentClob;
    }
    
    private DocumentClob getDocumentClob(Document document, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentClobs.fields())
                .from(DocumentClobs)
                .where(DocumentClobs.DOCUMENT.eq(document.getPrimaryKey()),
                        DocumentClobs.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentClobFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public DocumentClob getDocumentClob(Document document) {
        return getDocumentClob(document, EntityPermission.READ_ONLY);
    }
    
    public DocumentClob getDocumentClobForUpdate(Document document) {
        return getDocumentClob(document, EntityPermission.READ_WRITE);
    }
    
    public DocumentClobValue getDocumentClobValue(DocumentClob documentClob) {
        return documentClob == null ? null : documentClob.getDocumentClobValue().clone();
    }
    
    public DocumentClobValue getDocumentClobValueForUpdate(Document document) {
        return getDocumentClobValue(getDocumentClobForUpdate(document));
    }
    
    public void updateDocumentClobFromValue(DocumentClobValue documentClobValue, BasePK updatedBy) {
        if(documentClobValue.hasBeenModified()) {
            var documentClob = documentClobFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    documentClobValue.getPrimaryKey());
            
            documentClob.setThruTime(session.getStartTime());
            documentClob.store();

            var documentPK = documentClob.getDocumentPK(); // Not updated
            var clob = documentClobValue.getClob();
            
            documentClob = documentClobFactory.create(documentPK, clob, session.getStartTime(),
                    Session.MAX_TIME);
            
            sendEvent(documentClob.getDocumentPK(), EventTypes.MODIFY, documentClob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDocumentClob(DocumentClob documentClob, BasePK deletedBy) {
        documentClob.setThruTime(session.getStartTime());
        
        sendEvent(documentClob.getDocumentPK(), EventTypes.MODIFY, documentClob.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteDocumentClobByDocument(Document document, BasePK deletedBy) {
        var documentClob = getDocumentClobForUpdate(document);
        
        if(documentClob != null) {
            deleteDocumentClob(documentClob, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Document Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected DocumentDescriptionFactory documentDescriptionFactory;

    @Inject
    DocumentDescriptionTransferCache documentDescriptionTransferCache;

    public DocumentDescription createDocumentDescription(Document document, Language language, String description, BasePK createdBy) {
        var documentDescription = documentDescriptionFactory.create(document, language,
                description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(document.getPrimaryKey(), EventTypes.MODIFY, documentDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return documentDescription;
    }
    
    private DocumentDescription getDocumentDescription(Document document, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(DocumentDescriptions.fields())
                .from(DocumentDescriptions)
                .where(DocumentDescriptions.DOCUMENT.eq(document.getPrimaryKey()),
                        DocumentDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        DocumentDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return documentDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public DocumentDescription getDocumentDescription(Document document, Language language) {
        return getDocumentDescription(document, language, EntityPermission.READ_ONLY);
    }
    
    public DocumentDescription getDocumentDescriptionForUpdate(Document document, Language language) {
        return getDocumentDescription(document, language, EntityPermission.READ_WRITE);
    }
    
    public DocumentDescriptionValue getDocumentDescriptionValue(DocumentDescription documentDescription) {
        return documentDescription == null ? null : documentDescription.getDocumentDescriptionValue().clone();
    }
    
    public DocumentDescriptionValue getDocumentDescriptionValueForUpdate(Document document, Language language) {
        return getDocumentDescriptionValue(getDocumentDescriptionForUpdate(document, language));
    }
    
    private List<DocumentDescription> getDocumentDescriptionsByDocument(Document document, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(DocumentDescriptions.fields())
                    .from(DocumentDescriptions)
                    .join(Languages).onKey(DOCUMENT_DESCRIPTIONS_LANGUAGE_FK)
                    .where(DocumentDescriptions.DOCUMENT.eq(document.getPrimaryKey()),
                            DocumentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    DocumentDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(DocumentDescriptions.fields())
                    .from(DocumentDescriptions)
                    .where(DocumentDescriptions.DOCUMENT.eq(document.getPrimaryKey()),
                            DocumentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return documentDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<DocumentDescription> getDocumentDescriptionsByDocument(Document document) {
        return getDocumentDescriptionsByDocument(document, EntityPermission.READ_ONLY);
    }
    
    public List<DocumentDescription> getDocumentDescriptionsByDocumentForUpdate(Document document) {
        return getDocumentDescriptionsByDocument(document, EntityPermission.READ_WRITE);
    }
    
    public String getBestDocumentDescription(Document document, Language language) {
        String description;
        var documentDescription = getDocumentDescription(document, language);
        
        if(documentDescription == null && !language.getIsDefault()) {
            documentDescription = getDocumentDescription(document, partyControl.getDefaultLanguage());
        }
        
        if(documentDescription == null) {
            description = document.getLastDetail().getDocumentName();
        } else {
            description = documentDescription.getDescription();
        }
        
        return description;
    }
    
    public DocumentDescriptionTransfer getDocumentDescriptionTransfer(UserVisit userVisit, DocumentDescription documentDescription) {
        return documentDescriptionTransferCache.getDocumentDescriptionTransfer(userVisit, documentDescription);
    }
    
    public List<DocumentDescriptionTransfer> getDocumentDescriptionTransfersByDocument(UserVisit userVisit, Document document) {
        var documentDescriptions = getDocumentDescriptionsByDocument(document);
        List<DocumentDescriptionTransfer> documentDescriptionTransfers = null;
        
        if(documentDescriptions != null) {
            documentDescriptionTransfers = new ArrayList<>(documentDescriptions.size());
            
            for(var documentDescription : documentDescriptions) {
                documentDescriptionTransfers.add(documentDescriptionTransferCache.getDocumentDescriptionTransfer(userVisit, documentDescription));
            }
        }
        
        return documentDescriptionTransfers;
    }
    
    public void updateDocumentDescriptionFromValue(DocumentDescriptionValue documentDescriptionValue, BasePK updatedBy) {
        if(documentDescriptionValue.hasBeenModified()) {
            var documentDescription = documentDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, documentDescriptionValue.getPrimaryKey());
            
            documentDescription.setThruTime(session.getStartTime());
            documentDescription.store();

            var document = documentDescription.getDocument();
            var language = documentDescription.getLanguage();
            var description = documentDescriptionValue.getDescription();
            
            documentDescription = documentDescriptionFactory.create(document, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(document.getPrimaryKey(), EventTypes.MODIFY, documentDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDocumentDescription(DocumentDescription documentDescription, BasePK deletedBy) {
        documentDescription.setThruTime(session.getStartTime());
        
        sendEvent(documentDescription.getDocumentPK(), EventTypes.MODIFY, documentDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }
    
    public void deleteDocumentDescriptionsByDocument(Document document, BasePK deletedBy) {
        var documentDescriptions = getDocumentDescriptionsByDocumentForUpdate(document);
        
        documentDescriptions.forEach((documentDescription) -> 
                deleteDocumentDescription(documentDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Document Type Usage Types
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyTypeDocumentTypeUsageTypeFactory partyTypeDocumentTypeUsageTypeFactory;

    @Inject
    PartyTypeDocumentTypeUsageTypeTransferCache partyTypeDocumentTypeUsageTypeTransferCache;

    public PartyTypeDocumentTypeUsageType createPartyTypeDocumentTypeUsageType(PartyType partyType, DocumentTypeUsageType documentTypeUsageType,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultPartyTypeDocumentTypeUsageType = getDefaultPartyTypeDocumentTypeUsageType(partyType);
        var defaultFound = defaultPartyTypeDocumentTypeUsageType != null;

        if(defaultFound && isDefault) {
            var defaultPartyTypeDocumentTypeUsageTypeValue = getDefaultPartyTypeDocumentTypeUsageTypeValueForUpdate(partyType);

            defaultPartyTypeDocumentTypeUsageTypeValue.setIsDefault(false);
            updatePartyTypeDocumentTypeUsageTypeFromValue(defaultPartyTypeDocumentTypeUsageTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyTypeDocumentTypeUsageType = partyTypeDocumentTypeUsageTypeFactory.create(partyType,
                documentTypeUsageType, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, partyTypeDocumentTypeUsageType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyTypeDocumentTypeUsageType;
    }

    public long countPartyTypeDocumentTypeUsageTypeByPartyType(final PartyType partyType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTypeDocumentTypeUsageTypes)
                .where(PartyTypeDocumentTypeUsageTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTypeDocumentTypeUsageTypeByDocumentTypeUsageType(final DocumentTypeUsageType documentTypeUsageType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTypeDocumentTypeUsageTypes)
                .where(PartyTypeDocumentTypeUsageTypes.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                        PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyTypeDocumentTypeUsageType getPartyTypeDocumentTypeUsageType(PartyType partyType, DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTypeDocumentTypeUsageTypes.fields())
                .from(PartyTypeDocumentTypeUsageTypes)
                .where(PartyTypeDocumentTypeUsageTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        PartyTypeDocumentTypeUsageTypes.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                        PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTypeDocumentTypeUsageTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyTypeDocumentTypeUsageType getPartyTypeDocumentTypeUsageType(PartyType partyType, DocumentTypeUsageType documentTypeUsageType) {
        return getPartyTypeDocumentTypeUsageType(partyType, documentTypeUsageType, EntityPermission.READ_ONLY);
    }

    public PartyTypeDocumentTypeUsageType getPartyTypeDocumentTypeUsageTypeForUpdate(PartyType partyType, DocumentTypeUsageType documentTypeUsageType) {
        return getPartyTypeDocumentTypeUsageType(partyType, documentTypeUsageType, EntityPermission.READ_WRITE);
    }

    public PartyTypeDocumentTypeUsageTypeValue getPartyTypeDocumentTypeUsageTypeValueForUpdate(PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        return partyTypeDocumentTypeUsageType == null ? null : partyTypeDocumentTypeUsageType.getPartyTypeDocumentTypeUsageTypeValue().clone();
    }

    public PartyTypeDocumentTypeUsageTypeValue getPartyTypeDocumentTypeUsageTypeValueForUpdate(PartyType partyType, DocumentTypeUsageType documentTypeUsageType) {
        return getPartyTypeDocumentTypeUsageTypeValueForUpdate(getPartyTypeDocumentTypeUsageTypeForUpdate(partyType, documentTypeUsageType));
    }

    private PartyTypeDocumentTypeUsageType getDefaultPartyTypeDocumentTypeUsageType(PartyType partyType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyTypeDocumentTypeUsageTypes.fields())
                .from(PartyTypeDocumentTypeUsageTypes)
                .where(PartyTypeDocumentTypeUsageTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        PartyTypeDocumentTypeUsageTypes.IS_DEFAULT.eq(true),
                        PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyTypeDocumentTypeUsageTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyTypeDocumentTypeUsageType getDefaultPartyTypeDocumentTypeUsageType(PartyType partyType) {
        return getDefaultPartyTypeDocumentTypeUsageType(partyType, EntityPermission.READ_ONLY);
    }

    public PartyTypeDocumentTypeUsageType getDefaultPartyTypeDocumentTypeUsageTypeForUpdate(PartyType partyType) {
        return getDefaultPartyTypeDocumentTypeUsageType(partyType, EntityPermission.READ_WRITE);
    }

    public PartyTypeDocumentTypeUsageTypeValue getDefaultPartyTypeDocumentTypeUsageTypeValueForUpdate(PartyType partyType) {
        return getDefaultPartyTypeDocumentTypeUsageTypeForUpdate(partyType).getPartyTypeDocumentTypeUsageTypeValue().clone();
    }

    public long countPartyTypeDocumentTypeUsageTypesByPartyType(final PartyType partyType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTypeDocumentTypeUsageTypes)
                .where(PartyTypeDocumentTypeUsageTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                        PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(final DocumentTypeUsageType documentTypeUsageType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyTypeDocumentTypeUsageTypes)
                .where(PartyTypeDocumentTypeUsageTypes.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                        PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByPartyType(PartyType partyType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTypeDocumentTypeUsageTypes.fields())
                    .from(PartyTypeDocumentTypeUsageTypes)
                    .join(DocumentTypeUsageTypes).onKey(PARTY_TYPE_DOCUMENT_TYPE_USAGE_TYPES_DOCUMENT_TYPE_USAGE_TYPE_FK)
                    .join(DocumentTypeUsageTypeDetails).onKey(DOCUMENT_TYPE_USAGE_TYPES_LAST_DETAIL_FK)
                    .where(PartyTypeDocumentTypeUsageTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                            PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypeDocumentTypeUsageTypes.SORT_ORDER, DocumentTypeUsageTypeDetails.SORT_ORDER, DocumentTypeUsageTypeDetails.DOCUMENT_TYPE_USAGE_TYPE_NAME),
                    PartyTypeDocumentTypeUsageTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTypeDocumentTypeUsageTypes.fields())
                    .from(PartyTypeDocumentTypeUsageTypes)
                    .where(PartyTypeDocumentTypeUsageTypes.PARTY_TYPE.eq(partyType.getPrimaryKey()),
                            PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTypeDocumentTypeUsageTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByPartyType(PartyType partyType) {
        return getPartyTypeDocumentTypeUsageTypesByPartyType(partyType, EntityPermission.READ_ONLY);
    }

    public List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByPartyTypeForUpdate(PartyType partyType) {
        return getPartyTypeDocumentTypeUsageTypesByPartyType(partyType, EntityPermission.READ_WRITE);
    }

    private List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyTypeDocumentTypeUsageTypes.fields())
                    .from(PartyTypeDocumentTypeUsageTypes)
                    .join(PartyTypes).onKey(PARTY_TYPE_DOCUMENT_TYPE_USAGE_TYPES_PARTY_TYPE_FK)
                    .where(PartyTypeDocumentTypeUsageTypes.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                            PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME),
                    PartyTypeDocumentTypeUsageTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyTypeDocumentTypeUsageTypes.fields())
                    .from(PartyTypeDocumentTypeUsageTypes)
                    .where(PartyTypeDocumentTypeUsageTypes.DOCUMENT_TYPE_USAGE_TYPE.eq(documentTypeUsageType.getPrimaryKey()),
                            PartyTypeDocumentTypeUsageTypes.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyTypeDocumentTypeUsageTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType) {
        return getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageTypeForUpdate(DocumentTypeUsageType documentTypeUsageType) {
        return getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_WRITE);
    }

    public PartyTypeDocumentTypeUsageTypeTransfer getPartyTypeDocumentTypeUsageTypeTransfer(UserVisit userVisit, PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        return partyTypeDocumentTypeUsageTypeTransferCache.getPartyTypeDocumentTypeUsageTypeTransfer(userVisit, partyTypeDocumentTypeUsageType);
    }

    public List<PartyTypeDocumentTypeUsageTypeTransfer> getPartyTypeDocumentTypeUsageTypeTransfers(UserVisit userVisit, Collection<PartyTypeDocumentTypeUsageType> partyTypeDocumentTypeUsageTypes) {
        List<PartyTypeDocumentTypeUsageTypeTransfer> partyTypeDocumentTypeUsageTypeTransfers = new ArrayList<>(partyTypeDocumentTypeUsageTypes.size());

        partyTypeDocumentTypeUsageTypes.forEach((partyTypeDocumentTypeUsageType) ->
                partyTypeDocumentTypeUsageTypeTransfers.add(partyTypeDocumentTypeUsageTypeTransferCache.getPartyTypeDocumentTypeUsageTypeTransfer(userVisit, partyTypeDocumentTypeUsageType))
        );

        return partyTypeDocumentTypeUsageTypeTransfers;
    }

    public List<PartyTypeDocumentTypeUsageTypeTransfer> getPartyTypeDocumentTypeUsageTypeTransfersByPartyType(UserVisit userVisit, PartyType partyType) {
        return getPartyTypeDocumentTypeUsageTypeTransfers(userVisit, getPartyTypeDocumentTypeUsageTypesByPartyType(partyType));
    }

    public List<PartyTypeDocumentTypeUsageTypeTransfer> getPartyTypeDocumentTypeUsageTypeTransfersByDocumentTypeUsageType(UserVisit userVisit, DocumentTypeUsageType documentTypeUsageType) {
        return getPartyTypeDocumentTypeUsageTypeTransfers(userVisit, getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType));
    }

    private void updatePartyTypeDocumentTypeUsageTypeFromValue(PartyTypeDocumentTypeUsageTypeValue partyTypeDocumentTypeUsageTypeValue, boolean checkDefault, BasePK updatedBy) {
        if(partyTypeDocumentTypeUsageTypeValue.hasBeenModified()) {
            var partyTypeDocumentTypeUsageType = partyTypeDocumentTypeUsageTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypeDocumentTypeUsageTypeValue.getPrimaryKey());

            partyTypeDocumentTypeUsageType.setThruTime(session.getStartTime());
            partyTypeDocumentTypeUsageType.store();

            var partyType = partyTypeDocumentTypeUsageType.getPartyType();
            var partyTypePK = partyType.getPrimaryKey(); // Not updated
            var documentTypeUsageTypePK = partyTypeDocumentTypeUsageType.getDocumentTypeUsageTypePK(); // Not updated
            var isDefault = partyTypeDocumentTypeUsageTypeValue.getIsDefault();
            var sortOrder = partyTypeDocumentTypeUsageTypeValue.getSortOrder();

            if(checkDefault) {
                var defaultPartyTypeDocumentTypeUsageType = getDefaultPartyTypeDocumentTypeUsageType(partyType);
                var defaultFound = defaultPartyTypeDocumentTypeUsageType != null && !defaultPartyTypeDocumentTypeUsageType.equals(partyTypeDocumentTypeUsageType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyTypeDocumentTypeUsageTypeValue = getDefaultPartyTypeDocumentTypeUsageTypeValueForUpdate(partyType);

                    defaultPartyTypeDocumentTypeUsageTypeValue.setIsDefault(false);
                    updatePartyTypeDocumentTypeUsageTypeFromValue(defaultPartyTypeDocumentTypeUsageTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            partyTypeDocumentTypeUsageType = partyTypeDocumentTypeUsageTypeFactory.create(partyTypePK, documentTypeUsageTypePK, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            sendEvent(documentTypeUsageTypePK, EventTypes.MODIFY, partyTypeDocumentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updatePartyTypeDocumentTypeUsageTypeFromValue(PartyTypeDocumentTypeUsageTypeValue partyTypeDocumentTypeUsageTypeValue, BasePK updatedBy) {
        updatePartyTypeDocumentTypeUsageTypeFromValue(partyTypeDocumentTypeUsageTypeValue, true, updatedBy);
    }

    private void deletePartyTypeDocumentTypeUsageType(PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType, boolean checkDefault, BasePK deletedBy) {
        var partyType = partyTypeDocumentTypeUsageType.getPartyType();

        partyTypeDocumentTypeUsageType.setThruTime(session.getStartTime());
        partyTypeDocumentTypeUsageType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultPartyTypeDocumentTypeUsageType = getDefaultPartyTypeDocumentTypeUsageType(partyType);

            if(defaultPartyTypeDocumentTypeUsageType == null) {
                var partyTypeDocumentTypeUsageTypes = getPartyTypeDocumentTypeUsageTypesByPartyTypeForUpdate(partyType);

                if(!partyTypeDocumentTypeUsageTypes.isEmpty()) {
                    var iter = partyTypeDocumentTypeUsageTypes.iterator();
                    if(iter.hasNext()) {
                        defaultPartyTypeDocumentTypeUsageType = iter.next();
                    }
                    var partyTypeDocumentTypeUsageTypeDetailValue = defaultPartyTypeDocumentTypeUsageType.getPartyTypeDocumentTypeUsageTypeValue().clone();

                    partyTypeDocumentTypeUsageTypeDetailValue.setIsDefault(true);
                    updatePartyTypeDocumentTypeUsageTypeFromValue(partyTypeDocumentTypeUsageTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(partyTypeDocumentTypeUsageType.getDocumentTypeUsageTypePK(), EventTypes.MODIFY, partyTypeDocumentTypeUsageType.getPrimaryKey(), EventTypes.DELETE,deletedBy);
    }

    public void deletePartyTypeDocumentTypeUsageType(PartyTypeDocumentTypeUsageType itemDescriptionType, BasePK deletedBy) {
        deletePartyTypeDocumentTypeUsageType(itemDescriptionType, true, deletedBy);
    }

    private void deletePartyTypeDocumentTypeUsageTypes(List<PartyTypeDocumentTypeUsageType> partyTypeDocumentTypeUsageTypes, boolean checkDefault, BasePK deletedBy) {
        partyTypeDocumentTypeUsageTypes.forEach((partyTypeDocumentTypeUsageType) -> deletePartyTypeDocumentTypeUsageType(partyTypeDocumentTypeUsageType, checkDefault, deletedBy));
    }

    public void deletePartyTypeDocumentTypeUsageTypes(List<PartyTypeDocumentTypeUsageType> itemDescriptionTypes, BasePK deletedBy) {
        deletePartyTypeDocumentTypeUsageTypes(itemDescriptionTypes, true, deletedBy);
    }

    public void deletePartyTypeDocumentTypeUsageTypesByPartyType(PartyType partyType, BasePK deletedBy) {
        deletePartyTypeDocumentTypeUsageTypes(getPartyTypeDocumentTypeUsageTypesByPartyTypeForUpdate(partyType), false, deletedBy);
    }

    public void deletePartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, BasePK deletedBy) {
        deletePartyTypeDocumentTypeUsageTypes(getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageTypeForUpdate(documentTypeUsageType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Party Documents
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyDocumentFactory partyDocumentFactory;

    @Inject
    PartyDocumentTransferCache partyDocumentTransferCache;

    public PartyDocument createPartyDocument(Party party, Document document, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var documentType = document.getLastDetail().getDocumentType();
        var defaultPartyDocument = getDefaultPartyDocument(party, documentType);
        var defaultFound = defaultPartyDocument != null;

        if(defaultFound && isDefault) {
            var defaultPartyDocumentValue = getDefaultPartyDocumentValueForUpdate(party, documentType);

            defaultPartyDocumentValue.setIsDefault(false);
            updatePartyDocumentFromValue(defaultPartyDocumentValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var partyDocument = partyDocumentFactory.create(party, document, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyDocument.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyDocument;
    }

    public long countPartyDocumentsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyDocuments)
                .where(PartyDocuments.PARTY.eq(party.getPrimaryKey()),
                        PartyDocuments.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyDocumentsByPartyAndDocumentType(Party party, DocumentType documentType) {
        return session.getDslContext()
                .selectCount()
                .from(PartyDocuments)
                .join(Documents).onKey(PARTY_DOCUMENTS_DOCUMENT_FK)
                .join(DocumentDetails).onKey(DOCUMENTS_ACTIVE_DETAIL_FK)
                .where(PartyDocuments.PARTY.eq(party.getPrimaryKey()),
                        PartyDocuments.THRU_TIME.eq(Session.MAX_TIME),
                        DocumentDetails.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyDocument getPartyDocument(Document document, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyDocuments.fields())
                .from(PartyDocuments)
                .where(PartyDocuments.DOCUMENT.eq(document.getPrimaryKey()),
                        PartyDocuments.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyDocumentFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyDocument getPartyDocument(Document document) {
        return getPartyDocument(document, EntityPermission.READ_ONLY);
    }

    public PartyDocument getPartyDocumentForUpdate(Document document) {
        return getPartyDocument(document, EntityPermission.READ_WRITE);
    }

    public PartyDocumentValue getPartyDocumentValueForUpdate(PartyDocument partyDocument) {
        return partyDocument == null ? null : partyDocument.getPartyDocumentValue().clone();
    }

    public PartyDocumentValue getPartyDocumentValueForUpdate(Document document) {
        return getPartyDocumentValueForUpdate(getPartyDocumentForUpdate(document));
    }

    private PartyDocument getDefaultPartyDocument(Party party, DocumentType documentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyDocuments.fields())
                .from(PartyDocuments)
                .join(Documents).onKey(PARTY_DOCUMENTS_DOCUMENT_FK)
                .join(DocumentDetails).onKey(DOCUMENTS_LAST_DETAIL_FK)
                .where(PartyDocuments.PARTY.eq(party.getPrimaryKey()),
                        PartyDocuments.IS_DEFAULT.eq(true),
                        PartyDocuments.THRU_TIME.eq(Session.MAX_TIME),
                        DocumentDetails.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyDocumentFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyDocument getDefaultPartyDocument(Party party, DocumentType documentType) {
        return getDefaultPartyDocument(party, documentType, EntityPermission.READ_ONLY);
    }

    public PartyDocument getDefaultPartyDocumentForUpdate(Party party, DocumentType documentType) {
        return getDefaultPartyDocument(party, documentType, EntityPermission.READ_WRITE);
    }

    public PartyDocumentValue getDefaultPartyDocumentValueForUpdate(Party party, DocumentType documentType) {
        return getDefaultPartyDocumentForUpdate(party, documentType).getPartyDocumentValue().clone();
    }

    private List<PartyDocument> getPartyDocumentsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyDocuments.fields())
                    .from(PartyDocuments)
                    .join(Documents).onKey(PARTY_DOCUMENTS_DOCUMENT_FK)
                    .join(DocumentDetails).onKey(DOCUMENTS_LAST_DETAIL_FK)
                    .join(DocumentTypes).onKey(DOCUMENT_DETAILS_DOCUMENT_TYPE_FK)
                    .join(DocumentTypeDetails).onKey(DOCUMENT_TYPES_LAST_DETAIL_FK)
                    .where(PartyDocuments.PARTY.eq(party.getPrimaryKey()),
                            PartyDocuments.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(DocumentTypeDetails.SORT_ORDER, DocumentTypeDetails.DOCUMENT_TYPE_NAME, DocumentDetails.DOCUMENT_NAME),
                    PartyDocumentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyDocuments.fields())
                    .from(PartyDocuments)
                    .where(PartyDocuments.PARTY.eq(party.getPrimaryKey()),
                            PartyDocuments.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyDocumentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyDocument> getPartyDocumentsByParty(Party party) {
        return getPartyDocumentsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyDocument> getPartyDocumentsByPartyForUpdate(Party party) {
        return getPartyDocumentsByParty(party, EntityPermission.READ_WRITE);
    }

    private List<PartyDocument> getPartyDocumentsByPartyAndDocumentType(Party party, DocumentType documentType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyDocuments.fields())
                .from(PartyDocuments)
                .join(Documents).onKey(PARTY_DOCUMENTS_DOCUMENT_FK)
                .join(DocumentDetails).onKey(DOCUMENTS_LAST_DETAIL_FK)
                .where(PartyDocuments.PARTY.eq(party.getPrimaryKey()),
                        PartyDocuments.THRU_TIME.eq(Session.MAX_TIME),
                        DocumentDetails.DOCUMENT_TYPE.eq(documentType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(PartyDocuments.SORT_ORDER, DocumentDetails.DOCUMENT_NAME),
                    PartyDocumentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyDocumentFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyDocument> getPartyDocumentsByPartyAndDocumentType(Party party, DocumentType documentType) {
        return getPartyDocumentsByPartyAndDocumentType(party, documentType, EntityPermission.READ_ONLY);
    }

    public List<PartyDocument> getPartyDocumentsByPartyAndDocumentTypeForUpdate(Party party, DocumentType documentType) {
        return getPartyDocumentsByPartyAndDocumentType(party, documentType, EntityPermission.READ_WRITE);
    }

    private PartyDocument getPartyDocumentByDocument(Document document, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyDocuments.fields())
                .from(PartyDocuments)
                .where(PartyDocuments.DOCUMENT.eq(document.getPrimaryKey()),
                        PartyDocuments.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyDocumentFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyDocument getPartyDocumentByDocument(Document document) {
        return getPartyDocumentByDocument(document, EntityPermission.READ_ONLY);
    }

    public PartyDocument getPartyDocumentByDocumentForUpdate(Document document) {
        return getPartyDocumentByDocument(document, EntityPermission.READ_WRITE);
    }

    public PartyDocumentTransfer getPartyDocumentTransfer(UserVisit userVisit, PartyDocument partyDocument) {
        return partyDocumentTransferCache.getPartyDocumentTransfer(userVisit, partyDocument);
    }

    public List<PartyDocumentTransfer> getPartyDocumentTransfers(UserVisit userVisit, Collection<PartyDocument> partyDocuments) {
        List<PartyDocumentTransfer> partyDocumentTransfers = new ArrayList<>(partyDocuments.size());

        partyDocuments.forEach((partyDocument) ->
                partyDocumentTransfers.add(partyDocumentTransferCache.getPartyDocumentTransfer(userVisit, partyDocument))
        );

        return partyDocumentTransfers;
    }

    public List<PartyDocumentTransfer> getPartyDocumentTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyDocumentTransfers(userVisit, getPartyDocumentsByParty(party));
    }

    public DocumentChoicesBean getPartyDocumentChoices(String defaultDocumentChoice, Language language, boolean allowNullChoice, Party party,
            DocumentType documentType) {
        var partyDocuments = getPartyDocumentsByPartyAndDocumentType(party, documentType);
        var size = partyDocuments.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultDocumentChoice == null) {
                defaultValue = "";
            }
        }

        for(var partyDocument : partyDocuments) {
            var document = partyDocument.getDocument();
            var documentDetail = document.getLastDetail();

            var label = getBestDocumentDescription(document, language);
            var value = documentDetail.getDocumentName();

            labels.add(label == null ? value: label);
            values.add(value);

            var usingDefaultChoice = defaultDocumentChoice == null ? false: defaultDocumentChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyDocument.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new DocumentChoicesBean(labels, values, defaultValue);
    }

    private void updatePartyDocumentFromValue(PartyDocumentValue partyDocumentValue, boolean checkDefault, BasePK updatedBy) {
        if(partyDocumentValue.hasBeenModified()) {
            var partyDocument = partyDocumentFactory.getEntityFromPK(EntityPermission.READ_WRITE, partyDocumentValue.getPrimaryKey());

            partyDocument.setThruTime(session.getStartTime());
            partyDocument.store();

            var party = partyDocument.getParty();
            var partyPK = party.getPrimaryKey(); // Not updated
            var document = partyDocument.getDocument();
            var documentPK = document.getPrimaryKey(); // Not updated
            var isDefault = partyDocumentValue.getIsDefault();
            var sortOrder = partyDocumentValue.getSortOrder();

            if(checkDefault) {
                var documentType = document.getLastDetail().getDocumentType();
                var defaultPartyDocument = getDefaultPartyDocument(party, documentType);
                var defaultFound = defaultPartyDocument != null && !defaultPartyDocument.equals(partyDocument);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultPartyDocumentValue = getDefaultPartyDocumentValueForUpdate(party, documentType);

                    defaultPartyDocumentValue.setIsDefault(false);
                    updatePartyDocumentFromValue(defaultPartyDocumentValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            partyDocument = partyDocumentFactory.create(partyPK, documentPK, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(partyPK, EventTypes.MODIFY, partyDocument.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updatePartyDocumentFromValue(PartyDocumentValue partyDocumentValue, BasePK updatedBy) {
        updatePartyDocumentFromValue(partyDocumentValue, true, updatedBy);
    }

    private void deletePartyDocument(PartyDocument partyDocument, boolean checkDefault, BasePK deletedBy) {
        var party = partyDocument.getParty();
        var document = partyDocument.getDocumentForUpdate();

        deleteDocument(document, deletedBy);

        partyDocument.setThruTime(session.getStartTime());
        partyDocument.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var documentType = document.getLastDetail().getDocumentType();
            var defaultPartyDocument = getDefaultPartyDocument(party, documentType);

            if(defaultPartyDocument == null) {
                var partyDocuments = getPartyDocumentsByPartyAndDocumentTypeForUpdate(party, documentType);

                if(!partyDocuments.isEmpty()) {
                    var iter = partyDocuments.iterator();
                    if(iter.hasNext()) {
                        defaultPartyDocument = iter.next();
                    }
                    var partyDocumentDetailValue = defaultPartyDocument.getPartyDocumentValue().clone();

                    partyDocumentDetailValue.setIsDefault(true);
                    updatePartyDocumentFromValue(partyDocumentDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(partyDocument.getPartyPK(), EventTypes.MODIFY, partyDocument.getPrimaryKey(), EventTypes.DELETE,deletedBy);
    }

    public void deletePartyDocument(PartyDocument itemDescriptionType, BasePK deletedBy) {
        deletePartyDocument(itemDescriptionType, true, deletedBy);
    }

    private void deletePartyDocuments(List<PartyDocument> partyDocuments, boolean checkDefault, BasePK deletedBy) {
        partyDocuments.forEach((partyDocument) -> deletePartyDocument(partyDocument, checkDefault, deletedBy));
    }

    public void deletePartyDocuments(List<PartyDocument> itemDescriptionTypes, BasePK deletedBy) {
        deletePartyDocuments(itemDescriptionTypes, true, deletedBy);
    }

    public void deletePartyDocumentsByParty(Party party, BasePK deletedBy) {
        deletePartyDocuments(getPartyDocumentsByPartyForUpdate(party), false, deletedBy);
    }

}
