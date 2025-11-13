// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.control.core.server.control.EntityInstanceControl;
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
import com.echothree.model.control.document.server.transfer.DocumentTransferCaches;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DocumentControl
        extends BaseModelControl {
    
    /** Creates a new instance of DocumentControl */
    protected DocumentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Document Transfer Caches
    // --------------------------------------------------------------------------------
    
    private DocumentTransferCaches documentTransferCaches;
    
    public DocumentTransferCaches getDocumentTransferCaches() {
        if(documentTransferCaches == null) {
            documentTransferCaches = new DocumentTransferCaches(userVisit, this);
        }
        
        return documentTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Document Types
    // --------------------------------------------------------------------------------
    
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

        var documentType = DocumentTypeFactory.getInstance().create();
        var documentTypeDetail = DocumentTypeDetailFactory.getInstance().create(documentType, documentTypeName, parentDocumentType,
                mimeTypeUsageType, maximumPages, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        documentType = DocumentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                documentType.getPrimaryKey());
        documentType.setActiveDetail(documentTypeDetail);
        documentType.setLastDetail(documentTypeDetail);
        documentType.store();
        
        sendEvent(documentType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return documentType;
    }
    
    private static final Map<EntityPermission, String> getDocumentTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypes, documenttypedetails " +
                "WHERE dcmnttyp_activedetailid = dcmnttypdt_documenttypedetailid " +
                "AND dcmnttypdt_documenttypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypes, documenttypedetails " +
                "WHERE dcmnttyp_activedetailid = dcmnttypdt_documenttypedetailid " +
                "AND dcmnttypdt_documenttypename = ? " +
                "FOR UPDATE");
        getDocumentTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private DocumentType getDocumentTypeByName(String documentTypeName, EntityPermission entityPermission) {
        return DocumentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDocumentTypeByNameQueries, documentTypeName);
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

    private static final Map<EntityPermission, String> getDefaultDocumentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypes, documenttypedetails " +
                "WHERE dcmnttyp_activedetailid = dcmnttypdt_documenttypedetailid " +
                "AND dcmnttypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypes, documenttypedetails " +
                "WHERE dcmnttyp_activedetailid = dcmnttypdt_documenttypedetailid " +
                "AND dcmnttypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultDocumentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private DocumentType getDefaultDocumentType(EntityPermission entityPermission) {
        return DocumentTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultDocumentTypeQueries);
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

    private static final Map<EntityPermission, String> getDocumentTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypes, documenttypedetails " +
                "WHERE dcmnttyp_activedetailid = dcmnttypdt_documenttypedetailid " +
                "ORDER BY dcmnttypdt_sortorder, dcmnttypdt_documenttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypes, documenttypedetails " +
                "WHERE dcmnttyp_activedetailid = dcmnttypdt_documenttypedetailid " +
                "FOR UPDATE");
        getDocumentTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<DocumentType> getDocumentTypes(EntityPermission entityPermission) {
        return DocumentTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getDocumentTypesQueries);
    }

    public List<DocumentType> getDocumentTypes() {
        return getDocumentTypes(EntityPermission.READ_ONLY);
    }

    public List<DocumentType> getDocumentTypesForUpdate() {
        return getDocumentTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDocumentTypesByParentDocumentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypes, documenttypedetails " +
                "WHERE dcmnttyp_activedetailid = dcmnttypdt_documenttypedetailid AND dcmnttypdt_parentdocumenttypeid = ? " +
                "ORDER BY dcmnttypdt_sortorder, dcmnttypdt_documenttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypes, documenttypedetails " +
                "WHERE dcmnttyp_activedetailid = dcmnttypdt_documenttypedetailid AND dcmnttypdt_parentdocumenttypeid = ? " +
                "FOR UPDATE");
        getDocumentTypesByParentDocumentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<DocumentType> getDocumentTypesByParentDocumentType(DocumentType parentDocumentType,
            EntityPermission entityPermission) {
        return DocumentTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getDocumentTypesByParentDocumentTypeQueries,
                parentDocumentType);
    }

    public List<DocumentType> getDocumentTypesByParentDocumentType(DocumentType parentDocumentType) {
        return getDocumentTypesByParentDocumentType(parentDocumentType, EntityPermission.READ_ONLY);
    }

    public List<DocumentType> getDocumentTypesByParentDocumentTypeForUpdate(DocumentType parentDocumentType) {
        return getDocumentTypesByParentDocumentType(parentDocumentType, EntityPermission.READ_WRITE);
    }

    public DocumentTypeTransfer getDocumentTypeTransfer(UserVisit userVisit, DocumentType documentType) {
        return getDocumentTransferCaches().getDocumentTypeTransferCache().getDocumentTypeTransfer(userVisit, documentType);
    }
    
    public List<DocumentTypeTransfer> getDocumentTypeTransfers(UserVisit userVisit) {
        var documentTypes = getDocumentTypes();
        List<DocumentTypeTransfer> documentTypeTransfers = new ArrayList<>(documentTypes.size());
        var documentTypeTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeTransferCache();
        
        documentTypes.forEach((documentType) ->
                documentTypeTransfers.add(documentTypeTransferCache.getDocumentTypeTransfer(documentType))
        );
        
        return documentTypeTransfers;
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
            var documentType = DocumentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeDetailValue.getDocumentTypePK());
            var documentTypeDetail = documentType.getActiveDetailForUpdate();
            
            documentTypeDetail.setThruTime(session.START_TIME_LONG);
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
            
            documentTypeDetail = DocumentTypeDetailFactory.getInstance().create(documentTypePK, documentTypeName, parentDocumentTypePK, mimeTypeUsageTypePK,
                    maximumPages, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
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
        
        documentTypeDetail.setThruTime(session.START_TIME_LONG);
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
    
    public DocumentTypeDescription createDocumentTypeDescription(DocumentType documentType, Language language, String description, BasePK createdBy) {
        var documentTypeDescription = DocumentTypeDescriptionFactory.getInstance().create(documentType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(documentType.getPrimaryKey(), EventTypes.MODIFY, documentTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return documentTypeDescription;
    }
    
    private static final Map<EntityPermission, String> getDocumentTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypedescriptions " +
                "WHERE dcmnttypd_dcmnttyp_documenttypeid = ? AND dcmnttypd_lang_languageid = ? AND dcmnttypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypedescriptions " +
                "WHERE dcmnttypd_dcmnttyp_documenttypeid = ? AND dcmnttypd_lang_languageid = ? AND dcmnttypd_thrutime = ? " +
                "FOR UPDATE");
        getDocumentTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private DocumentTypeDescription getDocumentTypeDescription(DocumentType documentType, Language language, EntityPermission entityPermission) {
        return DocumentTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getDocumentTypeDescriptionQueries,
                documentType, language, Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getDocumentTypeDescriptionsByDocumentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypedescriptions, languages " +
                "WHERE dcmnttypd_dcmnttyp_documenttypeid = ? AND dcmnttypd_thrutime = ? AND dcmnttypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypedescriptions " +
                "WHERE dcmnttypd_dcmnttyp_documenttypeid = ? AND dcmnttypd_thrutime = ? " +
                "FOR UPDATE");
        getDocumentTypeDescriptionsByDocumentTypeQueries = Collections.unmodifiableMap(queryMap);
    }
    
    private List<DocumentTypeDescription> getDocumentTypeDescriptionsByDocumentType(DocumentType documentType, EntityPermission entityPermission) {
        return DocumentTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getDocumentTypeDescriptionsByDocumentTypeQueries,
                documentType, Session.MAX_TIME);
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
        return getDocumentTransferCaches().getDocumentTypeDescriptionTransferCache().getDocumentTypeDescriptionTransfer(userVisit, documentTypeDescription);
    }
    
    public List<DocumentTypeDescriptionTransfer> getDocumentTypeDescriptionTransfersByDocumentType(UserVisit userVisit, DocumentType documentType) {
        var documentTypeDescriptions = getDocumentTypeDescriptionsByDocumentType(documentType);
        List<DocumentTypeDescriptionTransfer> documentTypeDescriptionTransfers = new ArrayList<>(documentTypeDescriptions.size());
        var documentTypeDescriptionTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeDescriptionTransferCache();
        
        documentTypeDescriptions.forEach((documentTypeDescription) ->
                documentTypeDescriptionTransfers.add(documentTypeDescriptionTransferCache.getDocumentTypeDescriptionTransfer(documentTypeDescription))
        );
        
        return documentTypeDescriptionTransfers;
    }
    
    public void updateDocumentTypeDescriptionFromValue(DocumentTypeDescriptionValue documentTypeDescriptionValue, BasePK updatedBy) {
        if(documentTypeDescriptionValue.hasBeenModified()) {
            var documentTypeDescription = DocumentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    documentTypeDescriptionValue.getPrimaryKey());
            
            documentTypeDescription.setThruTime(session.START_TIME_LONG);
            documentTypeDescription.store();

            var documentType = documentTypeDescription.getDocumentType();
            var language = documentTypeDescription.getLanguage();
            var description = documentTypeDescriptionValue.getDescription();
            
            documentTypeDescription = DocumentTypeDescriptionFactory.getInstance().create(documentType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(documentType.getPrimaryKey(), EventTypes.MODIFY, documentTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDocumentTypeDescription(DocumentTypeDescription documentTypeDescription, BasePK deletedBy) {
        documentTypeDescription.setThruTime(session.START_TIME_LONG);
        
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

        var documentTypeUsageType = DocumentTypeUsageTypeFactory.getInstance().create();
        var documentTypeUsageTypeDetail = DocumentTypeUsageTypeDetailFactory.getInstance().create(documentTypeUsageType,
                documentTypeUsageTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        documentTypeUsageType = DocumentTypeUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                documentTypeUsageType.getPrimaryKey());
        documentTypeUsageType.setActiveDetail(documentTypeUsageTypeDetail);
        documentTypeUsageType.setLastDetail(documentTypeUsageTypeDetail);
        documentTypeUsageType.store();

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return documentTypeUsageType;
    }

    private static final Map<EntityPermission, String> getDocumentTypeUsageTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypes, documenttypeusagetypedetails " +
                "WHERE dcmnttyputyp_activedetailid = dcmnttyputypdt_documenttypeusagetypedetailid " +
                "AND dcmnttyputypdt_documenttypeusagetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypes, documenttypeusagetypedetails " +
                "WHERE dcmnttyputyp_activedetailid = dcmnttyputypdt_documenttypeusagetypedetailid " +
                "AND dcmnttyputypdt_documenttypeusagetypename = ? " +
                "FOR UPDATE");
        getDocumentTypeUsageTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private DocumentTypeUsageType getDocumentTypeUsageTypeByName(String documentTypeUsageTypeName, EntityPermission entityPermission) {
        return DocumentTypeUsageTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDocumentTypeUsageTypeByNameQueries, documentTypeUsageTypeName);
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

    private static final Map<EntityPermission, String> getDefaultDocumentTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypes, documenttypeusagetypedetails " +
                "WHERE dcmnttyputyp_activedetailid = dcmnttyputypdt_documenttypeusagetypedetailid " +
                "AND dcmnttyputypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypes, documenttypeusagetypedetails " +
                "WHERE dcmnttyputyp_activedetailid = dcmnttyputypdt_documenttypeusagetypedetailid " +
                "AND dcmnttyputypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultDocumentTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private DocumentTypeUsageType getDefaultDocumentTypeUsageType(EntityPermission entityPermission) {
        return DocumentTypeUsageTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultDocumentTypeUsageTypeQueries);
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

    private static final Map<EntityPermission, String> getDocumentTypeUsageTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypes, documenttypeusagetypedetails " +
                "WHERE dcmnttyputyp_activedetailid = dcmnttyputypdt_documenttypeusagetypedetailid " +
                "ORDER BY dcmnttyputypdt_sortorder, dcmnttyputypdt_documenttypeusagetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypes, documenttypeusagetypedetails " +
                "WHERE dcmnttyputyp_activedetailid = dcmnttyputypdt_documenttypeusagetypedetailid " +
                "FOR UPDATE");
        getDocumentTypeUsageTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<DocumentTypeUsageType> getDocumentTypeUsageTypes(EntityPermission entityPermission) {
        return DocumentTypeUsageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getDocumentTypeUsageTypesQueries);
    }

    public List<DocumentTypeUsageType> getDocumentTypeUsageTypes() {
        return getDocumentTypeUsageTypes(EntityPermission.READ_ONLY);
    }

    public List<DocumentTypeUsageType> getDocumentTypeUsageTypesForUpdate() {
        return getDocumentTypeUsageTypes(EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageTypeTransfer getDocumentTypeUsageTypeTransfer(UserVisit userVisit, DocumentTypeUsageType documentTypeUsageType) {
        return getDocumentTransferCaches().getDocumentTypeUsageTypeTransferCache().getDocumentTypeUsageTypeTransfer(userVisit, documentTypeUsageType);
    }

    public List<DocumentTypeUsageTypeTransfer> getDocumentTypeUsageTypeTransfers(UserVisit userVisit) {
        var documentTypeUsageTypes = getDocumentTypeUsageTypes();
        List<DocumentTypeUsageTypeTransfer> documentTypeUsageTypeTransfers = new ArrayList<>(documentTypeUsageTypes.size());
        var documentTypeUsageTypeTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeUsageTypeTransferCache();

        documentTypeUsageTypes.forEach((documentTypeUsageType) ->
                documentTypeUsageTypeTransfers.add(documentTypeUsageTypeTransferCache.getDocumentTypeUsageTypeTransfer(documentTypeUsageType))
        );

        return documentTypeUsageTypeTransfers;
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
            var documentTypeUsageType = DocumentTypeUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeUsageTypeDetailValue.getDocumentTypeUsageTypePK());
            var documentTypeUsageTypeDetail = documentTypeUsageType.getActiveDetailForUpdate();

            documentTypeUsageTypeDetail.setThruTime(session.START_TIME_LONG);
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

            documentTypeUsageTypeDetail = DocumentTypeUsageTypeDetailFactory.getInstance().create(documentTypeUsageTypePK, documentTypeUsageTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

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

        documentTypeUsageTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public DocumentTypeUsageTypeDescription createDocumentTypeUsageTypeDescription(DocumentTypeUsageType documentTypeUsageType, Language language, String description, BasePK createdBy) {
        var documentTypeUsageTypeDescription = DocumentTypeUsageTypeDescriptionFactory.getInstance().create(documentTypeUsageType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, documentTypeUsageTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return documentTypeUsageTypeDescription;
    }

    private static final Map<EntityPermission, String> getDocumentTypeUsageTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypedescriptions " +
                "WHERE dcmnttyputypd_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttyputypd_lang_languageid = ? AND dcmnttyputypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypedescriptions " +
                "WHERE dcmnttyputypd_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttyputypd_lang_languageid = ? AND dcmnttyputypd_thrutime = ? " +
                "FOR UPDATE");
        getDocumentTypeUsageTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private DocumentTypeUsageTypeDescription getDocumentTypeUsageTypeDescription(DocumentTypeUsageType documentTypeUsageType, Language language, EntityPermission entityPermission) {
        return DocumentTypeUsageTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getDocumentTypeUsageTypeDescriptionQueries,
                documentTypeUsageType, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypedescriptions, languages " +
                "WHERE dcmnttyputypd_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttyputypd_thrutime = ? AND dcmnttyputypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusagetypedescriptions " +
                "WHERE dcmnttyputypd_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttyputypd_thrutime = ? " +
                "FOR UPDATE");
        getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<DocumentTypeUsageTypeDescription> getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        return DocumentTypeUsageTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageTypeQueries,
                documentTypeUsageType, Session.MAX_TIME);
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
        return getDocumentTransferCaches().getDocumentTypeUsageTypeDescriptionTransferCache().getDocumentTypeUsageTypeDescriptionTransfer(userVisit, documentTypeUsageTypeDescription);
    }

    public List<DocumentTypeUsageTypeDescriptionTransfer> getDocumentTypeUsageTypeDescriptionTransfersByDocumentTypeUsageType(UserVisit userVisit, DocumentTypeUsageType documentTypeUsageType) {
        var documentTypeUsageTypeDescriptions = getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(documentTypeUsageType);
        List<DocumentTypeUsageTypeDescriptionTransfer> documentTypeUsageTypeDescriptionTransfers = new ArrayList<>(documentTypeUsageTypeDescriptions.size());
        var documentTypeUsageTypeDescriptionTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeUsageTypeDescriptionTransferCache();

        documentTypeUsageTypeDescriptions.forEach((documentTypeUsageTypeDescription) ->
                documentTypeUsageTypeDescriptionTransfers.add(documentTypeUsageTypeDescriptionTransferCache.getDocumentTypeUsageTypeDescriptionTransfer(documentTypeUsageTypeDescription))
        );

        return documentTypeUsageTypeDescriptionTransfers;
    }

    public void updateDocumentTypeUsageTypeDescriptionFromValue(DocumentTypeUsageTypeDescriptionValue documentTypeUsageTypeDescriptionValue, BasePK updatedBy) {
        if(documentTypeUsageTypeDescriptionValue.hasBeenModified()) {
            var documentTypeUsageTypeDescription = DocumentTypeUsageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    documentTypeUsageTypeDescriptionValue.getPrimaryKey());

            documentTypeUsageTypeDescription.setThruTime(session.START_TIME_LONG);
            documentTypeUsageTypeDescription.store();

            var documentTypeUsageType = documentTypeUsageTypeDescription.getDocumentTypeUsageType();
            var language = documentTypeUsageTypeDescription.getLanguage();
            var description = documentTypeUsageTypeDescriptionValue.getDescription();

            documentTypeUsageTypeDescription = DocumentTypeUsageTypeDescriptionFactory.getInstance().create(documentTypeUsageType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, documentTypeUsageTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteDocumentTypeUsageTypeDescription(DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription, BasePK deletedBy) {
        documentTypeUsageTypeDescription.setThruTime(session.START_TIME_LONG);

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

        var documentTypeUsage = DocumentTypeUsageFactory.getInstance().create(documentTypeUsageType, documentType, isDefault, sortOrder,
                maximumInstances, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, documentTypeUsage.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return documentTypeUsage;
    }

    private static final Map<EntityPermission, String> getDocumentTypeUsageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusages " +
                "WHERE dcmnttypu_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttypu_dcmnttyp_documenttypeid = ? AND dcmnttypu_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusages " +
                "WHERE dcmnttypu_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttypu_dcmnttyp_documenttypeid = ? AND dcmnttypu_thrutime = ? " +
                "FOR UPDATE");
        getDocumentTypeUsageQueries = Collections.unmodifiableMap(queryMap);
    }

    private DocumentTypeUsage getDocumentTypeUsage(DocumentTypeUsageType documentTypeUsageType, DocumentType documentType, EntityPermission entityPermission) {
        return DocumentTypeUsageFactory.getInstance().getEntityFromQuery(entityPermission, getDocumentTypeUsageQueries,
                documentTypeUsageType, documentType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getDefaultDocumentTypeUsageQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusages " +
                "WHERE dcmnttypu_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttypu_isdefault = 1 AND dcmnttypu_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusages " +
                "WHERE dcmnttypu_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttypu_isdefault = 1 AND dcmnttypu_thrutime = ? " +
                "FOR UPDATE");
        getDefaultDocumentTypeUsageQueries = Collections.unmodifiableMap(queryMap);
    }

    private DocumentTypeUsage getDefaultDocumentTypeUsage(DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        return DocumentTypeUsageFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultDocumentTypeUsageQueries,
                documentTypeUsageType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getDocumentTypeUsagesByDocumentTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusages, documenttypes, documenttypedetails " +
                "WHERE dcmnttypu_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttypu_thrutime = ? " +
                "AND dcmnttypu_dcmnttyp_documenttypeid = dcmnttyp_documenttypeid AND dcmnttyp_lastdetailid = dcmnttypdt_documenttypedetailid " +
                "ORDER BY dcmnttypdt_sortorder, dcmnttypdt_documenttypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusages " +
                "WHERE dcmnttypu_dcmnttyputyp_documenttypeusagetypeid = ? AND dcmnttypu_thrutime = ? " +
                "FOR UPDATE");
        getDocumentTypeUsagesByDocumentTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        return DocumentTypeUsageFactory.getInstance().getEntitiesFromQuery(entityPermission, getDocumentTypeUsagesByDocumentTypeUsageTypeQueries,
                documentTypeUsageType, Session.MAX_TIME);
    }

    public List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType) {
        return getDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentTypeUsageTypeForUpdate(DocumentTypeUsageType documentTypeUsageType) {
        return getDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDocumentTypeUsagesByDocumentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documenttypeusages, documenttypeusagetypes, documenttypeusagetypedetails " +
                "WHERE dcmnttypu_dcmnttyp_documenttypeid = ? AND dcmnttypu_thrutime = ? " +
                "AND dcmnttypu_dcmnttyputyp_documenttypeusagetypeid = dcmnttyputyp_documenttypeusagetypeid AND dcmnttyputyp_lastdetailid = dcmnttyputypdt_documenttypeusagetypedetailid " +
                "ORDER BY dcmnttyputypdt_sortorder, dcmnttyputypdt_documenttypeusagetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documenttypeusages " +
                "WHERE dcmnttypu_dcmnttyp_documenttypeid = ? AND dcmnttypu_thrutime = ? " +
                "FOR UPDATE");
        getDocumentTypeUsagesByDocumentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentType(DocumentType documentType, EntityPermission entityPermission) {
        return DocumentTypeUsageFactory.getInstance().getEntitiesFromQuery(entityPermission, getDocumentTypeUsagesByDocumentTypeQueries,
                documentType, Session.MAX_TIME);
    }

    public List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentType(DocumentType documentType) {
        return getDocumentTypeUsagesByDocumentType(documentType, EntityPermission.READ_ONLY);
    }

    public List<DocumentTypeUsage> getDocumentTypeUsagesByDocumentTypeForUpdate(DocumentType documentType) {
        return getDocumentTypeUsagesByDocumentType(documentType, EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageTransfer getDocumentTypeUsageTransfer(UserVisit userVisit, DocumentTypeUsage documentTypeUsage) {
        return getDocumentTransferCaches().getDocumentTypeUsageTransferCache().getDocumentTypeUsageTransfer(userVisit, documentTypeUsage);
    }

    public List<DocumentTypeUsageTransfer> getDocumentTypeUsageTransfers(UserVisit userVisit, Collection<DocumentTypeUsage> documentTypeUsages) {
        List<DocumentTypeUsageTransfer> documentTypeUsageTransfers = new ArrayList<>(documentTypeUsages.size());
        var documentTypeUsageTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeUsageTransferCache();

        documentTypeUsages.forEach((documentTypeUsage) ->
                documentTypeUsageTransfers.add(documentTypeUsageTransferCache.getDocumentTypeUsageTransfer(documentTypeUsage))
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
            var documentTypeUsage = DocumentTypeUsageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeUsageValue.getPrimaryKey());

            documentTypeUsage.setThruTime(session.START_TIME_LONG);
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

            documentTypeUsage = DocumentTypeUsageFactory.getInstance().create(documentTypeUsageTypePK, documentTypePK, isDefault,
                    sortOrder, maximumInstances, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(documentTypeUsageTypePK, EventTypes.MODIFY, documentTypeUsage.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updateDocumentTypeUsageFromValue(DocumentTypeUsageValue documentTypeUsageValue, BasePK updatedBy) {
        updateDocumentTypeUsageFromValue(documentTypeUsageValue, true, updatedBy);
    }

    private void deleteDocumentTypeUsage(DocumentTypeUsage documentTypeUsage, boolean checkDefault, BasePK deletedBy) {
        var documentTypeUsageType = documentTypeUsage.getDocumentTypeUsageType();

        documentTypeUsage.setThruTime(session.START_TIME_LONG);
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
    
    public Document createDocument(DocumentType documentType, MimeType mimeType, Integer pages, BasePK createdBy) {
        var sequenceControl = Session.getModelController(SequenceControl.class);
        var sequence = sequenceControl.getDefaultSequence(sequenceControl.getSequenceTypeByName(SequenceTypes.DOCUMENT.name()));
        var documentName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createDocument(documentName, documentType, mimeType, pages, createdBy);
    }
    
    public Document createDocument(String documentName, DocumentType documentType, MimeType mimeType, Integer pages, BasePK createdBy) {
        var document = DocumentFactory.getInstance().create();
        var documentDetail = DocumentDetailFactory.getInstance().create(document, documentName, documentType, mimeType, pages,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        document = DocumentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, document.getPrimaryKey());
        document.setActiveDetail(documentDetail);
        document.setLastDetail(documentDetail);
        document.store();
        
        sendEvent(document.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return document;
    }
    
    public long countDocumentsByDocumentType(DocumentType documentType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM documents, documentdetails " +
                "WHERE dcmntdt_dcmnttyp_documenttypeid = ?",
                documentType);
    }

    private Document getDocumentByName(String documentName, EntityPermission entityPermission) {
        Document document;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM documents, documentdetails " +
                        "WHERE dcmnt_activedetailid = dcmntdt_documentdetailid AND dcmntdt_documentname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM documents, documentdetails " +
                        "WHERE dcmnt_activedetailid = dcmntdt_documentdetailid AND dcmntdt_documentname = ? " +
                        "FOR UPDATE";
            }

            var ps = DocumentFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, documentName);
            
            document = DocumentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return document;
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

    private static final Map<EntityPermission, String> getDocumentsByDocumentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM documents, documentdetails " +
                "WHERE dcmnt_activedetailid = dcmnt_activedetailid " +
                "AND dcmntdt_dcmnttyp_documenttypeid = ? " +
                "ORDER BY dcmntdt_documentname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM documents, documentdetails " +
                "WHERE dcmnt_activedetailid = dcmnt_activedetailid " +
                "AND dcmntdt_dcmnttyp_documenttypeid = ? " +
                "FOR UPDATE");
        getDocumentsByDocumentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<Document> getDocumentsByDocumentType(DocumentType documentType, EntityPermission entityPermission) {
        return DocumentFactory.getInstance().getEntitiesFromQuery(entityPermission, getDocumentsByDocumentTypeQueries,
                documentType, Session.MAX_TIME);
    }

    public List<Document> getDocumentsByDocumentType(DocumentType documentType) {
        return getDocumentsByDocumentType(documentType, EntityPermission.READ_ONLY);
    }

    public List<Document> getDocumentsByDocumentTypeForUpdate(DocumentType documentType) {
        return getDocumentsByDocumentType(documentType, EntityPermission.READ_WRITE);
    }

    public DocumentTransfer getDocumentTransfer(UserVisit userVisit, Document document) {
        return getDocumentTransferCaches().getDocumentTransferCache().getDocumentTransfer(userVisit, document);
    }
    
    public void updateDocumentFromValue(DocumentDetailValue documentDetailValue, BasePK updatedBy) {
        if(documentDetailValue.hasBeenModified()) {
            var document = DocumentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     documentDetailValue.getDocumentPK());
            var documentDetail = document.getActiveDetailForUpdate();

            documentDetail.setThruTime(session.START_TIME_LONG);
            documentDetail.store();

            var documentPK = documentDetail.getDocumentPK(); // Not updated
            var documentName = documentDetail.getDocumentName(); // Not updated
            var documentTypePK = documentDetail.getDocumentTypePK(); // Not updated
            var mimeTypePK = documentDetailValue.getMimeTypePK();
            var pages = documentDetailValue.getPages();

            documentDetail = DocumentDetailFactory.getInstance().create(documentPK, documentName, documentTypePK, mimeTypePK, pages, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            document.setActiveDetail(documentDetail);
            document.setLastDetail(documentDetail);

            sendEvent(documentPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteDocument(Document document, BasePK deletedBy) {
        deleteDocumentDescriptionsByDocument(document, deletedBy);

        var documentDetail = document.getLastDetailForUpdate();
        documentDetail.setThruTime(session.START_TIME_LONG);
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
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

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

    public DocumentBlob createDocumentBlob(Document document, ByteArray blob, BasePK createdBy) {
        verifyDocumentMimeType(document, EntityAttributeTypes.BLOB.name());

        var documentBlob = DocumentBlobFactory.getInstance().create(document, blob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(document.getPrimaryKey(), EventTypes.MODIFY, documentBlob.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return documentBlob;
    }
    
    private DocumentBlob getDocumentBlob(Document document, EntityPermission entityPermission) {
        DocumentBlob documentBlob;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM documentblobs " +
                        "WHERE dcmntb_dcmnt_documentid = ? AND dcmntb_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM documentblobs " +
                        "WHERE dcmntb_dcmnt_documentid = ? AND dcmntb_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = DocumentBlobFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, document.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            documentBlob = DocumentBlobFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return documentBlob;
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
            var documentBlob = DocumentBlobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    documentBlobValue.getPrimaryKey());
            
            documentBlob.setThruTime(session.START_TIME_LONG);
            documentBlob.store();

            var documentPK = documentBlob.getDocumentPK(); // Not updated
            var blob = documentBlobValue.getBlob();
            
            documentBlob = DocumentBlobFactory.getInstance().create(documentPK, blob, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(documentBlob.getDocumentPK(), EventTypes.MODIFY, documentBlob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDocumentBlob(DocumentBlob documentBlob, BasePK deletedBy) {
        documentBlob.setThruTime(session.START_TIME_LONG);
        
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
    
    public DocumentClob createDocumentClob(Document document, String clob, BasePK createdBy) {
        verifyDocumentMimeType(document, EntityAttributeTypes.CLOB.name());

        var documentClob = DocumentClobFactory.getInstance().create(document, clob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(document.getPrimaryKey(), EventTypes.MODIFY, documentClob.getPrimaryKey(), EventTypes.MODIFY, createdBy);
        
        return documentClob;
    }
    
    private DocumentClob getDocumentClob(Document document, EntityPermission entityPermission) {
        DocumentClob documentClob;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM documentclobs " +
                        "WHERE dcmntc_dcmnt_documentid = ? AND dcmntc_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM documentclobs " +
                        "WHERE dcmntc_dcmnt_documentid = ? AND dcmntc_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = DocumentClobFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, document.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            documentClob = DocumentClobFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return documentClob;
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
            var documentClob = DocumentClobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    documentClobValue.getPrimaryKey());
            
            documentClob.setThruTime(session.START_TIME_LONG);
            documentClob.store();

            var documentPK = documentClob.getDocumentPK(); // Not updated
            var clob = documentClobValue.getClob();
            
            documentClob = DocumentClobFactory.getInstance().create(documentPK, clob, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEvent(documentClob.getDocumentPK(), EventTypes.MODIFY, documentClob.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDocumentClob(DocumentClob documentClob, BasePK deletedBy) {
        documentClob.setThruTime(session.START_TIME_LONG);
        
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
    
    public DocumentDescription createDocumentDescription(Document document, Language language, String description, BasePK createdBy) {
        var documentDescription = DocumentDescriptionFactory.getInstance().create(document, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(document.getPrimaryKey(), EventTypes.MODIFY, documentDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return documentDescription;
    }
    
    private DocumentDescription getDocumentDescription(Document document, Language language, EntityPermission entityPermission) {
        DocumentDescription documentDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM documentdescriptions " +
                        "WHERE dcmntd_dcmnt_documentid = ? AND dcmntd_lang_languageid = ? AND dcmntd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM documentdescriptions " +
                        "WHERE dcmntd_dcmnt_documentid = ? AND dcmntd_lang_languageid = ? AND dcmntd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = DocumentDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, document.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            documentDescription = DocumentDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return documentDescription;
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
        List<DocumentDescription> documentDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM documentdescriptions, languages " +
                        "WHERE dcmntd_dcmnt_documentid = ? AND dcmntd_thrutime = ? AND dcmntd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM documentdescriptions " +
                        "WHERE dcmntd_dcmnt_documentid = ? AND dcmntd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = DocumentDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, document.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            documentDescriptions = DocumentDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return documentDescriptions;
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
        return getDocumentTransferCaches().getDocumentDescriptionTransferCache().getDocumentDescriptionTransfer(userVisit, documentDescription);
    }
    
    public List<DocumentDescriptionTransfer> getDocumentDescriptionTransfersByDocument(UserVisit userVisit, Document document) {
        var documentDescriptions = getDocumentDescriptionsByDocument(document);
        List<DocumentDescriptionTransfer> documentDescriptionTransfers = null;
        
        if(documentDescriptions != null) {
            documentDescriptionTransfers = new ArrayList<>(documentDescriptions.size());
            
            for(var documentDescription : documentDescriptions) {
                documentDescriptionTransfers.add(getDocumentTransferCaches().getDocumentDescriptionTransferCache().getDocumentDescriptionTransfer(userVisit, documentDescription));
            }
        }
        
        return documentDescriptionTransfers;
    }
    
    public void updateDocumentDescriptionFromValue(DocumentDescriptionValue documentDescriptionValue, BasePK updatedBy) {
        if(documentDescriptionValue.hasBeenModified()) {
            var documentDescription = DocumentDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, documentDescriptionValue.getPrimaryKey());
            
            documentDescription.setThruTime(session.START_TIME_LONG);
            documentDescription.store();

            var document = documentDescription.getDocument();
            var language = documentDescription.getLanguage();
            var description = documentDescriptionValue.getDescription();
            
            documentDescription = DocumentDescriptionFactory.getInstance().create(document, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(document.getPrimaryKey(), EventTypes.MODIFY, documentDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteDocumentDescription(DocumentDescription documentDescription, BasePK deletedBy) {
        documentDescription.setThruTime(session.START_TIME_LONG);
        
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

        var partyTypeDocumentTypeUsageType = PartyTypeDocumentTypeUsageTypeFactory.getInstance().create(partyType,
                documentTypeUsageType, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, partyTypeDocumentTypeUsageType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyTypeDocumentTypeUsageType;
    }

    private static final Map<EntityPermission, String> getPartyTypeDocumentTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytypedocumenttypeusagetypes " +
                "WHERE ptypdcmnttyputyp_ptyp_partytypeid = ? AND ptypdcmnttyputyp_dcmnttyputyp_documenttypeusagetypeid = ? AND ptypdcmnttyputyp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytypedocumenttypeusagetypes " +
                "WHERE ptypdcmnttyputyp_ptyp_partytypeid = ? AND ptypdcmnttyputyp_dcmnttyputyp_documenttypeusagetypeid = ? AND ptypdcmnttyputyp_thrutime = ? " +
                "FOR UPDATE");
        getPartyTypeDocumentTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyTypeDocumentTypeUsageType getPartyTypeDocumentTypeUsageType(PartyType partyType, DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        return PartyTypeDocumentTypeUsageTypeFactory.getInstance().getEntityFromQuery(entityPermission, getPartyTypeDocumentTypeUsageTypeQueries,
                partyType, documentTypeUsageType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getDefaultPartyTypeDocumentTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytypedocumenttypeusagetypes " +
                "WHERE ptypdcmnttyputyp_ptyp_partytypeid = ? AND ptypdcmnttyputyp_isdefault = 1 AND ptypdcmnttyputyp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytypedocumenttypeusagetypes " +
                "WHERE ptypdcmnttyputyp_ptyp_partytypeid = ? AND ptypdcmnttyputyp_isdefault = 1 AND ptypdcmnttyputyp_thrutime = ? " +
                "FOR UPDATE");
        getDefaultPartyTypeDocumentTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyTypeDocumentTypeUsageType getDefaultPartyTypeDocumentTypeUsageType(PartyType partyType, EntityPermission entityPermission) {
        return PartyTypeDocumentTypeUsageTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPartyTypeDocumentTypeUsageTypeQueries,
                partyType, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getPartyTypeDocumentTypeUsageTypesByPartyTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytypedocumenttypeusagetypes, documenttypeusagetypes, documenttypeusagetypedetails " +
                "WHERE ptypdcmnttyputyp_ptyp_partytypeid = ? AND ptypdcmnttyputyp_thrutime = ? " +
                "AND ptypdcmnttyputyp_dcmnttyputyp_documenttypeusagetypeid = dcmnttyputyp_documenttypeusagetypeid AND dcmnttyputyp_lastdetailid = dcmnttyputypdt_documenttypeusagetypedetailid " +
                "ORDER BY ptypdcmnttyputyp_sortorder, dcmnttyputypdt_sortorder, dcmnttyputypdt_documenttypeusagetypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytypedocumenttypeusagetypes " +
                "WHERE ptypdcmnttyputyp_ptyp_partytypeid = ? AND ptypdcmnttyputyp_thrutime = ? " +
                "FOR UPDATE");
        getPartyTypeDocumentTypeUsageTypesByPartyTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByPartyType(PartyType partyType, EntityPermission entityPermission) {
        return PartyTypeDocumentTypeUsageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTypeDocumentTypeUsageTypesByPartyTypeQueries,
                partyType, Session.MAX_TIME);
    }

    public List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByPartyType(PartyType partyType) {
        return getPartyTypeDocumentTypeUsageTypesByPartyType(partyType, EntityPermission.READ_ONLY);
    }

    public List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByPartyTypeForUpdate(PartyType partyType) {
        return getPartyTypeDocumentTypeUsageTypesByPartyType(partyType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partytypedocumenttypeusagetypes, partytypes " +
                "WHERE ptypdcmnttyputyp_dcmnttyputyp_documenttypeusagetypeid = ? AND ptypdcmnttyputyp_thrutime = ? " +
                "AND ptypdcmnttyputyp_ptyp_partytypeid = ptyp_partytypeid " +
                "ORDER BY ptyp_sortorder, ptyp_partytypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partytypedocumenttypeusagetypes " +
                "WHERE ptypdcmnttyputyp_dcmnttyputyp_documenttypeusagetypeid = ? AND ptypdcmnttyputyp_thrutime = ? " +
                "FOR UPDATE");
        getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, EntityPermission entityPermission) {
        return PartyTypeDocumentTypeUsageTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageTypeQueries,
                documentTypeUsageType, Session.MAX_TIME);
    }

    public List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType) {
        return getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_ONLY);
    }

    public List<PartyTypeDocumentTypeUsageType> getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageTypeForUpdate(DocumentTypeUsageType documentTypeUsageType) {
        return getPartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType, EntityPermission.READ_WRITE);
    }

    public PartyTypeDocumentTypeUsageTypeTransfer getPartyTypeDocumentTypeUsageTypeTransfer(UserVisit userVisit, PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType) {
        return getDocumentTransferCaches().getPartyTypeDocumentTypeUsageTypeTransferCache().getPartyTypeDocumentTypeUsageTypeTransfer(userVisit, partyTypeDocumentTypeUsageType);
    }

    public List<PartyTypeDocumentTypeUsageTypeTransfer> getPartyTypeDocumentTypeUsageTypeTransfers(UserVisit userVisit, Collection<PartyTypeDocumentTypeUsageType> partyTypeDocumentTypeUsageTypes) {
        List<PartyTypeDocumentTypeUsageTypeTransfer> partyTypeDocumentTypeUsageTypeTransfers = new ArrayList<>(partyTypeDocumentTypeUsageTypes.size());
        var partyTypeDocumentTypeUsageTypeTransferCache = getDocumentTransferCaches(userVisit).getPartyTypeDocumentTypeUsageTypeTransferCache();

        partyTypeDocumentTypeUsageTypes.forEach((partyTypeDocumentTypeUsageType) ->
                partyTypeDocumentTypeUsageTypeTransfers.add(partyTypeDocumentTypeUsageTypeTransferCache.getPartyTypeDocumentTypeUsageTypeTransfer(partyTypeDocumentTypeUsageType))
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
            var partyTypeDocumentTypeUsageType = PartyTypeDocumentTypeUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypeDocumentTypeUsageTypeValue.getPrimaryKey());

            partyTypeDocumentTypeUsageType.setThruTime(session.START_TIME_LONG);
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

            partyTypeDocumentTypeUsageType = PartyTypeDocumentTypeUsageTypeFactory.getInstance().create(partyTypePK, documentTypeUsageTypePK, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(documentTypeUsageTypePK, EventTypes.MODIFY, partyTypeDocumentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void updatePartyTypeDocumentTypeUsageTypeFromValue(PartyTypeDocumentTypeUsageTypeValue partyTypeDocumentTypeUsageTypeValue, BasePK updatedBy) {
        updatePartyTypeDocumentTypeUsageTypeFromValue(partyTypeDocumentTypeUsageTypeValue, true, updatedBy);
    }

    private void deletePartyTypeDocumentTypeUsageType(PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType, boolean checkDefault, BasePK deletedBy) {
        var partyType = partyTypeDocumentTypeUsageType.getPartyType();

        partyTypeDocumentTypeUsageType.setThruTime(session.START_TIME_LONG);
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

        var partyDocument = PartyDocumentFactory.getInstance().create(party, document, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyDocument.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyDocument;
    }

    public long countPartyDocumentsByPartyAndDocumentType(Party party, DocumentType documentType) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM partydocuments, documents, documentdetails "
                + "WHERE pardcmnt_par_partyid = ? AND pardcmnt_thrutime = ? "
                + "AND pardcmnt_dcmnt_documentid = dcmnt_documentid AND dcmnt_activedetailid = dcmntdt_documentdetailid "
                + "AND dcmntdt_dcmnttyp_documenttypeid = ?",
                party, Session.MAX_TIME, documentType);
    }

    private static final Map<EntityPermission, String> getPartyDocumentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partydocuments " +
                "WHERE pardcmnt_dcmnt_documentid = ? AND pardcmnt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partydocuments " +
                "WHERE pardcmnt_dcmnt_documentid = ? AND pardcmnt_thrutime = ? " +
                "FOR UPDATE");
        getPartyDocumentQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyDocument getPartyDocument(Document document, EntityPermission entityPermission) {
        return PartyDocumentFactory.getInstance().getEntityFromQuery(entityPermission, getPartyDocumentQueries,
                document, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getDefaultPartyDocumentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partydocuments, documents, documentdetails " +
                "WHERE pardcmnt_par_partyid = ? AND pardcmnt_isdefault = 1 AND pardcmnt_thrutime = ? " +
                "AND pardcmnt_dcmnt_documentid = dcmnt_documentid AND dcmnt_lastdetailid = dcmntdt_documentdetailid " +
                "AND dcmntdt_dcmnttyp_documenttypeid = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partydocuments, documents, documentdetails " +
                "WHERE pardcmnt_par_partyid = ? AND pardcmnt_isdefault = 1 AND pardcmnt_thrutime = ? " +
                "AND pardcmnt_dcmnt_documentid = dcmnt_documentid AND dcmnt_lastdetailid = dcmntdt_documentdetailid " +
                "AND dcmntdt_dcmnttyp_documenttypeid = ? " +
                "FOR UPDATE");
        getDefaultPartyDocumentQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyDocument getDefaultPartyDocument(Party party, DocumentType documentType, EntityPermission entityPermission) {
        return PartyDocumentFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultPartyDocumentQueries,
                party, Session.MAX_TIME, documentType);
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

    private static final Map<EntityPermission, String> getPartyDocumentsByPartyQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partydocuments, documents, documentdetails, documenttypes, documenttypedetails " +
                "WHERE pardcmnt_par_partyid = ? AND pardcmnt_thrutime = ? " +
                "AND pardcmnt_dcmnt_documentid = dcmnt_documentid AND dcmnt_lastdetailid = dcmntdt_documentdetailid " +
                "AND dcmntdt_dcmnttyp_documenttypeid = dcmnttyp_documenttypeid AND dcmnttyp_lastdetailid = dcmnttypdt_documenttypedetailid " +
                "ORDER BY dcmnttypdt_sortorder, dcmnttypdt_documenttypename, dcmntdt_documentname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partydocuments " +
                "WHERE pardcmnt_par_partyid = ? AND pardcmnt_thrutime = ? " +
                "FOR UPDATE");
        getPartyDocumentsByPartyQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyDocument> getPartyDocumentsByParty(Party party, EntityPermission entityPermission) {
        return PartyDocumentFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyDocumentsByPartyQueries,
                party, Session.MAX_TIME);
    }

    public List<PartyDocument> getPartyDocumentsByParty(Party party) {
        return getPartyDocumentsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyDocument> getPartyDocumentsByPartyForUpdate(Party party) {
        return getPartyDocumentsByParty(party, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyDocumentsByPartyAndDocumentTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partydocuments, documents, documentdetails " +
                "WHERE pardcmnt_par_partyid = ? AND pardcmnt_thrutime = ? " +
                "AND pardcmnt_dcmnt_documentid = dcmnt_documentid AND dcmnt_lastdetailid = dcmntdt_documentdetailid " +
                "AND dcmntdt_dcmnttyp_documenttypeid = ? " +
                "ORDER BY pardcmnt_sortorder, dcmntdt_documentname " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partydocuments, documents, documentdetails " +
                "WHERE pardcmnt_par_partyid = ? AND pardcmnt_thrutime = ? " +
                "AND pardcmnt_dcmnt_documentid = dcmnt_documentid AND dcmnt_lastdetailid = dcmntdt_documentdetailid " +
                "AND dcmntdt_dcmnttyp_documenttypeid = ? " +
                "FOR UPDATE");
        getPartyDocumentsByPartyAndDocumentTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<PartyDocument> getPartyDocumentsByPartyAndDocumentType(Party party, DocumentType documentType, EntityPermission entityPermission) {
        return PartyDocumentFactory.getInstance().getEntitiesFromQuery(entityPermission, getPartyDocumentsByPartyAndDocumentTypeQueries,
                party, Session.MAX_TIME, documentType);
    }

    public List<PartyDocument> getPartyDocumentsByPartyAndDocumentType(Party party, DocumentType documentType) {
        return getPartyDocumentsByPartyAndDocumentType(party, documentType, EntityPermission.READ_ONLY);
    }

    public List<PartyDocument> getPartyDocumentsByPartyAndDocumentTypeForUpdate(Party party, DocumentType documentType) {
        return getPartyDocumentsByPartyAndDocumentType(party, documentType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getPartyDocumentByDocumentQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM partydocuments, partytypes " +
                "WHERE pardcmnt_dcmnt_documentid = ? AND pardcmnt_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM partydocuments " +
                "WHERE pardcmnt_dcmnt_documentid = ? AND pardcmnt_thrutime = ? " +
                "FOR UPDATE");
        getPartyDocumentByDocumentQueries = Collections.unmodifiableMap(queryMap);
    }

    private PartyDocument getPartyDocumentByDocument(Document document, EntityPermission entityPermission) {
        return PartyDocumentFactory.getInstance().getEntityFromQuery(entityPermission, getPartyDocumentByDocumentQueries,
                document, Session.MAX_TIME);
    }

    public PartyDocument getPartyDocumentByDocument(Document document) {
        return getPartyDocumentByDocument(document, EntityPermission.READ_ONLY);
    }

    public PartyDocument getPartyDocumentByDocumentForUpdate(Document document) {
        return getPartyDocumentByDocument(document, EntityPermission.READ_WRITE);
    }

    public PartyDocumentTransfer getPartyDocumentTransfer(UserVisit userVisit, PartyDocument partyDocument) {
        return getDocumentTransferCaches().getPartyDocumentTransferCache().getPartyDocumentTransfer(userVisit, partyDocument);
    }

    public List<PartyDocumentTransfer> getPartyDocumentTransfers(UserVisit userVisit, Collection<PartyDocument> partyDocuments) {
        List<PartyDocumentTransfer> partyDocumentTransfers = new ArrayList<>(partyDocuments.size());
        var partyDocumentTransferCache = getDocumentTransferCaches(userVisit).getPartyDocumentTransferCache();

        partyDocuments.forEach((partyDocument) ->
                partyDocumentTransfers.add(partyDocumentTransferCache.getPartyDocumentTransfer(partyDocument))
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
            var partyDocument = PartyDocumentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyDocumentValue.getPrimaryKey());

            partyDocument.setThruTime(session.START_TIME_LONG);
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

            partyDocument = PartyDocumentFactory.getInstance().create(partyPK, documentPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

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

        partyDocument.setThruTime(session.START_TIME_LONG);
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
