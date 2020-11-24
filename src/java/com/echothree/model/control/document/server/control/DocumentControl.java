// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.document.server.transfer.DocumentTransferCaches;
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
import com.echothree.model.data.core.common.pk.MimeTypePK;
import com.echothree.model.data.core.common.pk.MimeTypeUsageTypePK;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.common.pk.DocumentPK;
import com.echothree.model.data.document.common.pk.DocumentTypePK;
import com.echothree.model.data.document.common.pk.DocumentTypeUsageTypePK;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentBlob;
import com.echothree.model.data.document.server.entity.DocumentClob;
import com.echothree.model.data.document.server.entity.DocumentDescription;
import com.echothree.model.data.document.server.entity.DocumentDetail;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.document.server.entity.DocumentTypeDescription;
import com.echothree.model.data.document.server.entity.DocumentTypeDetail;
import com.echothree.model.data.document.server.entity.DocumentTypeUsage;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageTypeDescription;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageTypeDetail;
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
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.common.pk.PartyTypePK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DocumentControl
        extends BaseModelControl {
    
    /** Creates a new instance of DocumentControl */
    public DocumentControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Document Transfer Caches
    // --------------------------------------------------------------------------------
    
    private DocumentTransferCaches documentTransferCaches;
    
    public DocumentTransferCaches getDocumentTransferCaches(UserVisit userVisit) {
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
        DocumentType defaultDocumentType = getDefaultDocumentType();
        boolean defaultFound = defaultDocumentType != null;
        
        if(defaultFound && isDefault) {
            DocumentTypeDetailValue defaultDocumentTypeDetailValue = getDefaultDocumentTypeDetailValueForUpdate();
            
            defaultDocumentTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateDocumentTypeFromValue(defaultDocumentTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        DocumentType documentType = DocumentTypeFactory.getInstance().create();
        DocumentTypeDetail documentTypeDetail = DocumentTypeDetailFactory.getInstance().create(documentType, documentTypeName, parentDocumentType,
                mimeTypeUsageType, maximumPages, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        documentType = DocumentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                documentType.getPrimaryKey());
        documentType.setActiveDetail(documentTypeDetail);
        documentType.setLastDetail(documentTypeDetail);
        documentType.store();
        
        sendEventUsingNames(documentType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
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
        return getDocumentTransferCaches(userVisit).getDocumentTypeTransferCache().getDocumentTypeTransfer(documentType);
    }
    
    public List<DocumentTypeTransfer> getDocumentTypeTransfers(UserVisit userVisit) {
        List<DocumentType> documentTypes = getDocumentTypes();
        List<DocumentTypeTransfer> documentTypeTransfers = new ArrayList<>(documentTypes.size());
        DocumentTypeTransferCache documentTypeTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeTransferCache();
        
        documentTypes.forEach((documentType) ->
                documentTypeTransfers.add(documentTypeTransferCache.getDocumentTypeTransfer(documentType))
        );
        
        return documentTypeTransfers;
    }
    
    public DocumentTypeChoicesBean getDocumentTypeChoices(String defaultDocumentTypeChoice, Language language, boolean allowNullChoice) {
        List<DocumentType> documentTypes = getDocumentTypes();
        int size = documentTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultDocumentTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(DocumentType documentType: documentTypes) {
            DocumentTypeDetail documentTypeDetail = documentType.getLastDetail();
            
            String label = getBestDocumentTypeDescription(documentType, language);
            String value = documentTypeDetail.getDocumentTypeName();
            
            labels.add(label == null ? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultDocumentTypeChoice == null ? false: defaultDocumentTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && documentTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new DocumentTypeChoicesBean(labels, values, defaultValue);
    }
    
    public boolean isParentDocumentTypeSafe(DocumentType documentType,
            DocumentType parentDocumentType) {
        boolean safe = true;
        
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
            DocumentType documentType = DocumentTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeDetailValue.getDocumentTypePK());
            DocumentTypeDetail documentTypeDetail = documentType.getActiveDetailForUpdate();
            
            documentTypeDetail.setThruTime(session.START_TIME_LONG);
            documentTypeDetail.store();
            
            DocumentTypePK documentTypePK = documentTypeDetail.getDocumentTypePK(); // Not updated
            String documentTypeName = documentTypeDetailValue.getDocumentTypeName();
            DocumentTypePK parentDocumentTypePK = documentTypeDetailValue.getParentDocumentTypePK();
            MimeTypeUsageTypePK mimeTypeUsageTypePK = documentTypeDetailValue.getMimeTypeUsageTypePK();
            Integer maximumPages = documentTypeDetailValue.getMaximumPages();
            Boolean isDefault = documentTypeDetailValue.getIsDefault();
            Integer sortOrder = documentTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                DocumentType defaultDocumentType = getDefaultDocumentType();
                boolean defaultFound = defaultDocumentType != null && !defaultDocumentType.equals(documentType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    DocumentTypeDetailValue defaultDocumentTypeDetailValue = getDefaultDocumentTypeDetailValueForUpdate();
                    
                    defaultDocumentTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateDocumentTypeFromValue(defaultDocumentTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            documentTypeDetail = DocumentTypeDetailFactory.getInstance().create(documentTypePK, documentTypeName, parentDocumentTypePK, mimeTypeUsageTypePK,
                    maximumPages, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            documentType.setActiveDetail(documentTypeDetail);
            documentType.setLastDetail(documentTypeDetail);
            
            sendEventUsingNames(documentTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateDocumentTypeFromValue(DocumentTypeDetailValue documentTypeDetailValue, BasePK updatedBy) {
        updateDocumentTypeFromValue(documentTypeDetailValue, true, updatedBy);
    }
    
    private void deleteDocumentType(DocumentType documentType, boolean checkDefault, BasePK deletedBy) {
        DocumentTypeDetail documentTypeDetail = documentType.getLastDetailForUpdate();

        deleteDocumentTypesByParentDocumentType(documentType, deletedBy);
        deleteDocumentTypeDescriptionsByDocumentType(documentType, deletedBy);
        deleteDocumentTypeUsagesByDocumentType(documentType, deletedBy);
        deleteDocumentsByDocumentType(documentType, deletedBy);
        
        documentTypeDetail.setThruTime(session.START_TIME_LONG);
        documentType.setActiveDetail(null);
        documentType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            DocumentType defaultDocumentType = getDefaultDocumentType();

            if(defaultDocumentType == null) {
                List<DocumentType> documentTypes = getDocumentTypesForUpdate();

                if(!documentTypes.isEmpty()) {
                    Iterator<DocumentType> iter = documentTypes.iterator();
                    if(iter.hasNext()) {
                        defaultDocumentType = iter.next();
                    }
                    DocumentTypeDetailValue documentTypeDetailValue = Objects.requireNonNull(defaultDocumentType).getLastDetailForUpdate().getDocumentTypeDetailValue().clone();

                    documentTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateDocumentTypeFromValue(documentTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(documentType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
        DocumentTypeDescription documentTypeDescription = DocumentTypeDescriptionFactory.getInstance().create(documentType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(documentType.getPrimaryKey(), EventTypes.MODIFY.name(), documentTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
        DocumentTypeDescription documentTypeDescription = getDocumentTypeDescription(documentType, language);
        
        if(documentTypeDescription == null && !language.getIsDefault()) {
            documentTypeDescription = getDocumentTypeDescription(documentType, getPartyControl().getDefaultLanguage());
        }
        
        if(documentTypeDescription == null) {
            description = documentType.getLastDetail().getDocumentTypeName();
        } else {
            description = documentTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public DocumentTypeDescriptionTransfer getDocumentTypeDescriptionTransfer(UserVisit userVisit, DocumentTypeDescription documentTypeDescription) {
        return getDocumentTransferCaches(userVisit).getDocumentTypeDescriptionTransferCache().getDocumentTypeDescriptionTransfer(documentTypeDescription);
    }
    
    public List<DocumentTypeDescriptionTransfer> getDocumentTypeDescriptionTransfersByDocumentType(UserVisit userVisit, DocumentType documentType) {
        List<DocumentTypeDescription> documentTypeDescriptions = getDocumentTypeDescriptionsByDocumentType(documentType);
        List<DocumentTypeDescriptionTransfer> documentTypeDescriptionTransfers = new ArrayList<>(documentTypeDescriptions.size());
        DocumentTypeDescriptionTransferCache documentTypeDescriptionTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeDescriptionTransferCache();
        
        documentTypeDescriptions.forEach((documentTypeDescription) ->
                documentTypeDescriptionTransfers.add(documentTypeDescriptionTransferCache.getDocumentTypeDescriptionTransfer(documentTypeDescription))
        );
        
        return documentTypeDescriptionTransfers;
    }
    
    public void updateDocumentTypeDescriptionFromValue(DocumentTypeDescriptionValue documentTypeDescriptionValue, BasePK updatedBy) {
        if(documentTypeDescriptionValue.hasBeenModified()) {
            DocumentTypeDescription documentTypeDescription = DocumentTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    documentTypeDescriptionValue.getPrimaryKey());
            
            documentTypeDescription.setThruTime(session.START_TIME_LONG);
            documentTypeDescription.store();
            
            DocumentType documentType = documentTypeDescription.getDocumentType();
            Language language = documentTypeDescription.getLanguage();
            String description = documentTypeDescriptionValue.getDescription();
            
            documentTypeDescription = DocumentTypeDescriptionFactory.getInstance().create(documentType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(documentType.getPrimaryKey(), EventTypes.MODIFY.name(), documentTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteDocumentTypeDescription(DocumentTypeDescription documentTypeDescription, BasePK deletedBy) {
        documentTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(documentTypeDescription.getDocumentTypePK(), EventTypes.MODIFY.name(), documentTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteDocumentTypeDescriptionsByDocumentType(DocumentType documentType, BasePK deletedBy) {
        List<DocumentTypeDescription> documentTypeDescriptions = getDocumentTypeDescriptionsByDocumentTypeForUpdate(documentType);
        
        documentTypeDescriptions.forEach((documentTypeDescription) -> 
                deleteDocumentTypeDescription(documentTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Document Types
    // --------------------------------------------------------------------------------

    public DocumentTypeUsageType createDocumentTypeUsageType(String documentTypeUsageTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        DocumentTypeUsageType defaultDocumentTypeUsageType = getDefaultDocumentTypeUsageType();
        boolean defaultFound = defaultDocumentTypeUsageType != null;

        if(defaultFound && isDefault) {
            DocumentTypeUsageTypeDetailValue defaultDocumentTypeUsageTypeDetailValue = getDefaultDocumentTypeUsageTypeDetailValueForUpdate();

            defaultDocumentTypeUsageTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateDocumentTypeUsageTypeFromValue(defaultDocumentTypeUsageTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        DocumentTypeUsageType documentTypeUsageType = DocumentTypeUsageTypeFactory.getInstance().create();
        DocumentTypeUsageTypeDetail documentTypeUsageTypeDetail = DocumentTypeUsageTypeDetailFactory.getInstance().create(documentTypeUsageType,
                documentTypeUsageTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        documentTypeUsageType = DocumentTypeUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                documentTypeUsageType.getPrimaryKey());
        documentTypeUsageType.setActiveDetail(documentTypeUsageTypeDetail);
        documentTypeUsageType.setLastDetail(documentTypeUsageTypeDetail);
        documentTypeUsageType.store();

        sendEventUsingNames(documentTypeUsageType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

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
        return getDocumentTransferCaches(userVisit).getDocumentTypeUsageTypeTransferCache().getDocumentTypeUsageTypeTransfer(documentTypeUsageType);
    }

    public List<DocumentTypeUsageTypeTransfer> getDocumentTypeUsageTypeTransfers(UserVisit userVisit) {
        List<DocumentTypeUsageType> documentTypeUsageTypes = getDocumentTypeUsageTypes();
        List<DocumentTypeUsageTypeTransfer> documentTypeUsageTypeTransfers = new ArrayList<>(documentTypeUsageTypes.size());
        DocumentTypeUsageTypeTransferCache documentTypeUsageTypeTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeUsageTypeTransferCache();

        documentTypeUsageTypes.forEach((documentTypeUsageType) ->
                documentTypeUsageTypeTransfers.add(documentTypeUsageTypeTransferCache.getDocumentTypeUsageTypeTransfer(documentTypeUsageType))
        );

        return documentTypeUsageTypeTransfers;
    }

    public DocumentTypeUsageTypeChoicesBean getDocumentTypeUsageTypeChoices(String defaultDocumentTypeUsageTypeChoice, Language language, boolean allowNullChoice) {
        List<DocumentTypeUsageType> documentTypeUsageTypes = getDocumentTypeUsageTypes();
        int size = documentTypeUsageTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultDocumentTypeUsageTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(DocumentTypeUsageType documentTypeUsageType: documentTypeUsageTypes) {
            DocumentTypeUsageTypeDetail documentTypeUsageTypeDetail = documentTypeUsageType.getLastDetail();

            String label = getBestDocumentTypeUsageTypeDescription(documentTypeUsageType, language);
            String value = documentTypeUsageTypeDetail.getDocumentTypeUsageTypeName();

            labels.add(label == null ? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultDocumentTypeUsageTypeChoice == null ? false: defaultDocumentTypeUsageTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && documentTypeUsageTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new DocumentTypeUsageTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateDocumentTypeUsageTypeFromValue(DocumentTypeUsageTypeDetailValue documentTypeUsageTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(documentTypeUsageTypeDetailValue.hasBeenModified()) {
            DocumentTypeUsageType documentTypeUsageType = DocumentTypeUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeUsageTypeDetailValue.getDocumentTypeUsageTypePK());
            DocumentTypeUsageTypeDetail documentTypeUsageTypeDetail = documentTypeUsageType.getActiveDetailForUpdate();

            documentTypeUsageTypeDetail.setThruTime(session.START_TIME_LONG);
            documentTypeUsageTypeDetail.store();

            DocumentTypeUsageTypePK documentTypeUsageTypePK = documentTypeUsageTypeDetail.getDocumentTypeUsageTypePK(); // Not updated
            String documentTypeUsageTypeName = documentTypeUsageTypeDetailValue.getDocumentTypeUsageTypeName();
            Boolean isDefault = documentTypeUsageTypeDetailValue.getIsDefault();
            Integer sortOrder = documentTypeUsageTypeDetailValue.getSortOrder();

            if(checkDefault) {
                DocumentTypeUsageType defaultDocumentTypeUsageType = getDefaultDocumentTypeUsageType();
                boolean defaultFound = defaultDocumentTypeUsageType != null && !defaultDocumentTypeUsageType.equals(documentTypeUsageType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    DocumentTypeUsageTypeDetailValue defaultDocumentTypeUsageTypeDetailValue = getDefaultDocumentTypeUsageTypeDetailValueForUpdate();

                    defaultDocumentTypeUsageTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateDocumentTypeUsageTypeFromValue(defaultDocumentTypeUsageTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            documentTypeUsageTypeDetail = DocumentTypeUsageTypeDetailFactory.getInstance().create(documentTypeUsageTypePK, documentTypeUsageTypeName, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            documentTypeUsageType.setActiveDetail(documentTypeUsageTypeDetail);
            documentTypeUsageType.setLastDetail(documentTypeUsageTypeDetail);

            sendEventUsingNames(documentTypeUsageTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateDocumentTypeUsageTypeFromValue(DocumentTypeUsageTypeDetailValue documentTypeUsageTypeDetailValue, BasePK updatedBy) {
        updateDocumentTypeUsageTypeFromValue(documentTypeUsageTypeDetailValue, true, updatedBy);
    }

    private void deleteDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, boolean checkDefault, BasePK deletedBy) {
        DocumentTypeUsageTypeDetail documentTypeUsageTypeDetail = documentTypeUsageType.getLastDetailForUpdate();

        deleteDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(documentTypeUsageType, deletedBy);
        deleteDocumentTypeUsagesByDocumentTypeUsageType(documentTypeUsageType, deletedBy);
        deletePartyTypeDocumentTypeUsageTypesByDocumentTypeUsageType(documentTypeUsageType, deletedBy);

        documentTypeUsageTypeDetail.setThruTime(session.START_TIME_LONG);
        documentTypeUsageType.setActiveDetail(null);
        documentTypeUsageType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            DocumentTypeUsageType defaultDocumentTypeUsageType = getDefaultDocumentTypeUsageType();

            if(defaultDocumentTypeUsageType == null) {
                List<DocumentTypeUsageType> documentTypeUsageTypes = getDocumentTypeUsageTypesForUpdate();

                if(!documentTypeUsageTypes.isEmpty()) {
                    Iterator<DocumentTypeUsageType> iter = documentTypeUsageTypes.iterator();
                    if(iter.hasNext()) {
                        defaultDocumentTypeUsageType = iter.next();
                    }
                    DocumentTypeUsageTypeDetailValue documentTypeUsageTypeDetailValue = Objects.requireNonNull(defaultDocumentTypeUsageType).getLastDetailForUpdate().getDocumentTypeUsageTypeDetailValue().clone();

                    documentTypeUsageTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateDocumentTypeUsageTypeFromValue(documentTypeUsageTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(documentTypeUsageType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
        DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription = DocumentTypeUsageTypeDescriptionFactory.getInstance().create(documentTypeUsageType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY.name(), documentTypeUsageTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription = getDocumentTypeUsageTypeDescription(documentTypeUsageType, language);

        if(documentTypeUsageTypeDescription == null && !language.getIsDefault()) {
            documentTypeUsageTypeDescription = getDocumentTypeUsageTypeDescription(documentTypeUsageType, getPartyControl().getDefaultLanguage());
        }

        if(documentTypeUsageTypeDescription == null) {
            description = documentTypeUsageType.getLastDetail().getDocumentTypeUsageTypeName();
        } else {
            description = documentTypeUsageTypeDescription.getDescription();
        }

        return description;
    }

    public DocumentTypeUsageTypeDescriptionTransfer getDocumentTypeUsageTypeDescriptionTransfer(UserVisit userVisit, DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription) {
        return getDocumentTransferCaches(userVisit).getDocumentTypeUsageTypeDescriptionTransferCache().getDocumentTypeUsageTypeDescriptionTransfer(documentTypeUsageTypeDescription);
    }

    public List<DocumentTypeUsageTypeDescriptionTransfer> getDocumentTypeUsageTypeDescriptionTransfersByDocumentTypeUsageType(UserVisit userVisit, DocumentTypeUsageType documentTypeUsageType) {
        List<DocumentTypeUsageTypeDescription> documentTypeUsageTypeDescriptions = getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(documentTypeUsageType);
        List<DocumentTypeUsageTypeDescriptionTransfer> documentTypeUsageTypeDescriptionTransfers = new ArrayList<>(documentTypeUsageTypeDescriptions.size());
        DocumentTypeUsageTypeDescriptionTransferCache documentTypeUsageTypeDescriptionTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeUsageTypeDescriptionTransferCache();

        documentTypeUsageTypeDescriptions.forEach((documentTypeUsageTypeDescription) ->
                documentTypeUsageTypeDescriptionTransfers.add(documentTypeUsageTypeDescriptionTransferCache.getDocumentTypeUsageTypeDescriptionTransfer(documentTypeUsageTypeDescription))
        );

        return documentTypeUsageTypeDescriptionTransfers;
    }

    public void updateDocumentTypeUsageTypeDescriptionFromValue(DocumentTypeUsageTypeDescriptionValue documentTypeUsageTypeDescriptionValue, BasePK updatedBy) {
        if(documentTypeUsageTypeDescriptionValue.hasBeenModified()) {
            DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription = DocumentTypeUsageTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    documentTypeUsageTypeDescriptionValue.getPrimaryKey());

            documentTypeUsageTypeDescription.setThruTime(session.START_TIME_LONG);
            documentTypeUsageTypeDescription.store();

            DocumentTypeUsageType documentTypeUsageType = documentTypeUsageTypeDescription.getDocumentTypeUsageType();
            Language language = documentTypeUsageTypeDescription.getLanguage();
            String description = documentTypeUsageTypeDescriptionValue.getDescription();

            documentTypeUsageTypeDescription = DocumentTypeUsageTypeDescriptionFactory.getInstance().create(documentTypeUsageType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY.name(), documentTypeUsageTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteDocumentTypeUsageTypeDescription(DocumentTypeUsageTypeDescription documentTypeUsageTypeDescription, BasePK deletedBy) {
        documentTypeUsageTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(documentTypeUsageTypeDescription.getDocumentTypeUsageTypePK(), EventTypes.MODIFY.name(), documentTypeUsageTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageType(DocumentTypeUsageType documentTypeUsageType, BasePK deletedBy) {
        List<DocumentTypeUsageTypeDescription> documentTypeUsageTypeDescriptions = getDocumentTypeUsageTypeDescriptionsByDocumentTypeUsageTypeForUpdate(documentTypeUsageType);

        documentTypeUsageTypeDescriptions.forEach((documentTypeUsageTypeDescription) -> 
                deleteDocumentTypeUsageTypeDescription(documentTypeUsageTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Document Type Usages
    // --------------------------------------------------------------------------------

    public DocumentTypeUsage createDocumentTypeUsage(DocumentTypeUsageType documentTypeUsageType, DocumentType documentType, Boolean isDefault,
            Integer sortOrder, Integer maximumInstances, BasePK createdBy) {
        DocumentTypeUsage defaultDocumentTypeUsage = getDefaultDocumentTypeUsage(documentTypeUsageType);
        boolean defaultFound = defaultDocumentTypeUsage != null;

        if(defaultFound && isDefault) {
            DocumentTypeUsageValue defaultDocumentTypeUsageValue = getDefaultDocumentTypeUsageValueForUpdate(documentTypeUsageType);

            defaultDocumentTypeUsageValue.setIsDefault(Boolean.FALSE);
            updateDocumentTypeUsageFromValue(defaultDocumentTypeUsageValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        DocumentTypeUsage documentTypeUsage = DocumentTypeUsageFactory.getInstance().create(documentTypeUsageType, documentType, isDefault, sortOrder,
                maximumInstances, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY.name(), documentTypeUsage.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        return getDocumentTransferCaches(userVisit).getDocumentTypeUsageTransferCache().getDocumentTypeUsageTransfer(documentTypeUsage);
    }

    public List<DocumentTypeUsageTransfer> getDocumentTypeUsageTransfers(UserVisit userVisit, List<DocumentTypeUsage> documentTypeUsages) {
        List<DocumentTypeUsageTransfer> documentTypeUsageTransfers = new ArrayList<>(documentTypeUsages.size());
        DocumentTypeUsageTransferCache documentTypeUsageTransferCache = getDocumentTransferCaches(userVisit).getDocumentTypeUsageTransferCache();

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
            DocumentTypeUsage documentTypeUsage = DocumentTypeUsageFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     documentTypeUsageValue.getPrimaryKey());

            documentTypeUsage.setThruTime(session.START_TIME_LONG);
            documentTypeUsage.store();

            DocumentTypeUsageType documentTypeUsageType = documentTypeUsage.getDocumentTypeUsageType();
            DocumentTypeUsageTypePK documentTypeUsageTypePK = documentTypeUsageType.getPrimaryKey(); // Not updated
            DocumentTypePK documentTypePK = documentTypeUsage.getDocumentTypePK(); // Not updated
            Boolean isDefault = documentTypeUsageValue.getIsDefault();
            Integer sortOrder = documentTypeUsageValue.getSortOrder();
            Integer maximumInstances = documentTypeUsageValue.getMaximumInstances();

            if(checkDefault) {
                DocumentTypeUsage defaultDocumentTypeUsage = getDefaultDocumentTypeUsage(documentTypeUsageType);
                boolean defaultFound = defaultDocumentTypeUsage != null && !defaultDocumentTypeUsage.equals(documentTypeUsage);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    DocumentTypeUsageValue defaultDocumentTypeUsageValue = getDefaultDocumentTypeUsageValueForUpdate(documentTypeUsageType);

                    defaultDocumentTypeUsageValue.setIsDefault(Boolean.FALSE);
                    updateDocumentTypeUsageFromValue(defaultDocumentTypeUsageValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            documentTypeUsage = DocumentTypeUsageFactory.getInstance().create(documentTypeUsageTypePK, documentTypePK, isDefault,
                    sortOrder, maximumInstances, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(documentTypeUsageTypePK, EventTypes.MODIFY.name(), documentTypeUsage.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void updateDocumentTypeUsageFromValue(DocumentTypeUsageValue documentTypeUsageValue, BasePK updatedBy) {
        updateDocumentTypeUsageFromValue(documentTypeUsageValue, true, updatedBy);
    }

    private void deleteDocumentTypeUsage(DocumentTypeUsage documentTypeUsage, boolean checkDefault, BasePK deletedBy) {
        DocumentTypeUsageType documentTypeUsageType = documentTypeUsage.getDocumentTypeUsageType();

        documentTypeUsage.setThruTime(session.START_TIME_LONG);
        documentTypeUsage.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            DocumentTypeUsage defaultDocumentTypeUsage = getDefaultDocumentTypeUsage(documentTypeUsageType);

            if(defaultDocumentTypeUsage == null) {
                List<DocumentTypeUsage> documentTypeUsages = getDocumentTypeUsagesByDocumentTypeUsageTypeForUpdate(documentTypeUsageType);

                if(!documentTypeUsages.isEmpty()) {
                    Iterator<DocumentTypeUsage> iter = documentTypeUsages.iterator();
                    if(iter.hasNext()) {
                        defaultDocumentTypeUsage = iter.next();
                    }
                    DocumentTypeUsageValue documentTypeUsageDetailValue = defaultDocumentTypeUsage.getDocumentTypeUsageValue().clone();

                    documentTypeUsageDetailValue.setIsDefault(Boolean.TRUE);
                    updateDocumentTypeUsageFromValue(documentTypeUsageDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY.name(), documentTypeUsage.getPrimaryKey(), EventTypes.DELETE.name(),deletedBy);
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
        Sequence sequence = sequenceControl.getDefaultSequence(sequenceControl.getSequenceTypeByName(SequenceTypes.DOCUMENT.name()));
        String documentName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
        
        return createDocument(documentName, documentType, mimeType, pages, createdBy);
    }
    
    public Document createDocument(String documentName, DocumentType documentType, MimeType mimeType, Integer pages, BasePK createdBy) {
        Document document = DocumentFactory.getInstance().create();
        DocumentDetail documentDetail = DocumentDetailFactory.getInstance().create(document, documentName, documentType, mimeType, pages,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        document = DocumentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, document.getPrimaryKey());
        document.setActiveDetail(documentDetail);
        document.setLastDetail(documentDetail);
        document.store();
        
        sendEventUsingNames(document.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
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
            
            PreparedStatement ps = DocumentFactory.getInstance().prepareStatement(query);
            
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
        return getDocumentTransferCaches(userVisit).getDocumentTransferCache().getDocumentTransfer(document);
    }
    
    public void updateDocumentFromValue(DocumentDetailValue documentDetailValue, BasePK updatedBy) {
        if(documentDetailValue.hasBeenModified()) {
            Document document = DocumentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     documentDetailValue.getDocumentPK());
            DocumentDetail documentDetail = document.getActiveDetailForUpdate();

            documentDetail.setThruTime(session.START_TIME_LONG);
            documentDetail.store();

            DocumentPK documentPK = documentDetail.getDocumentPK(); // Not updated
            String documentName = documentDetail.getDocumentName(); // Not updated
            DocumentTypePK documentTypePK = documentDetail.getDocumentTypePK(); // Not updated
            MimeTypePK mimeTypePK = documentDetailValue.getMimeTypePK();
            Integer pages = documentDetailValue.getPages();

            documentDetail = DocumentDetailFactory.getInstance().create(documentPK, documentName, documentTypePK, mimeTypePK, pages, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            document.setActiveDetail(documentDetail);
            document.setLastDetail(documentDetail);

            sendEventUsingNames(documentPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void deleteDocument(Document document, BasePK deletedBy) {
        deleteDocumentDescriptionsByDocument(document, deletedBy);
        
        DocumentDetail documentDetail = document.getLastDetailForUpdate();
        documentDetail.setThruTime(session.START_TIME_LONG);
        documentDetail.store();
        document.setActiveDetail(null);
        
        String entityAttributeTypeName = documentDetail.getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            deleteDocumentBlobByDocument(document, deletedBy);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            deleteDocumentClobByDocument(document, deletedBy);
        }
        
        sendEventUsingNames(document.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
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
        getCoreControl().removeEntityInstanceByBasePK(document.getPrimaryKey());
    }
    
    // --------------------------------------------------------------------------------
    //   Document Utilities
    // --------------------------------------------------------------------------------

    private void verifyDocumentMimeType(Document document, String entityAttributeTypeName) {
        String documentEntityAttributeTypeName = document.getLastDetail().getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(!documentEntityAttributeTypeName.equals(entityAttributeTypeName)) {
            throw new IllegalArgumentException("Document entityAttributeTypeName is " + documentEntityAttributeTypeName + ", expected " + entityAttributeTypeName);
        }
    }

    // --------------------------------------------------------------------------------
    //   Document Blobs
    // --------------------------------------------------------------------------------

    public DocumentBlob createDocumentBlob(Document document, ByteArray blob, BasePK createdBy) {
        verifyDocumentMimeType(document, EntityAttributeTypes.BLOB.name());

        DocumentBlob documentBlob = DocumentBlobFactory.getInstance().create(document, blob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(document.getPrimaryKey(), EventTypes.MODIFY.name(), documentBlob.getPrimaryKey(), EventTypes.MODIFY.name(), createdBy);
        
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
            
            PreparedStatement ps = DocumentBlobFactory.getInstance().prepareStatement(query);
            
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
            DocumentBlob documentBlob = DocumentBlobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    documentBlobValue.getPrimaryKey());
            
            documentBlob.setThruTime(session.START_TIME_LONG);
            documentBlob.store();
            
            DocumentPK documentPK = documentBlob.getDocumentPK(); // Not updated
            ByteArray blob = documentBlobValue.getBlob();
            
            documentBlob = DocumentBlobFactory.getInstance().create(documentPK, blob, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(documentBlob.getDocumentPK(), EventTypes.MODIFY.name(), documentBlob.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteDocumentBlob(DocumentBlob documentBlob, BasePK deletedBy) {
        documentBlob.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(documentBlob.getDocumentPK(), EventTypes.MODIFY.name(), documentBlob.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteDocumentBlobByDocument(Document document, BasePK deletedBy) {
        DocumentBlob documentBlob = getDocumentBlobForUpdate(document);
        
        if(documentBlob != null) {
            deleteDocumentBlob(documentBlob, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Document Clobs
    // --------------------------------------------------------------------------------
    
    public DocumentClob createDocumentClob(Document document, String clob, BasePK createdBy) {
        verifyDocumentMimeType(document, EntityAttributeTypes.CLOB.name());

        DocumentClob documentClob = DocumentClobFactory.getInstance().create(document, clob, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(document.getPrimaryKey(), EventTypes.MODIFY.name(), documentClob.getPrimaryKey(), EventTypes.MODIFY.name(), createdBy);
        
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
            
            PreparedStatement ps = DocumentClobFactory.getInstance().prepareStatement(query);
            
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
            DocumentClob documentClob = DocumentClobFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    documentClobValue.getPrimaryKey());
            
            documentClob.setThruTime(session.START_TIME_LONG);
            documentClob.store();
            
            DocumentPK documentPK = documentClob.getDocumentPK(); // Not updated
            String clob = documentClobValue.getClob();
            
            documentClob = DocumentClobFactory.getInstance().create(documentPK, clob, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            sendEventUsingNames(documentClob.getDocumentPK(), EventTypes.MODIFY.name(), documentClob.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteDocumentClob(DocumentClob documentClob, BasePK deletedBy) {
        documentClob.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(documentClob.getDocumentPK(), EventTypes.MODIFY.name(), documentClob.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteDocumentClobByDocument(Document document, BasePK deletedBy) {
        DocumentClob documentClob = getDocumentClobForUpdate(document);
        
        if(documentClob != null) {
            deleteDocumentClob(documentClob, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Document Descriptions
    // --------------------------------------------------------------------------------
    
    public DocumentDescription createDocumentDescription(Document document, Language language, String description, BasePK createdBy) {
        DocumentDescription documentDescription = DocumentDescriptionFactory.getInstance().create(document, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(document.getPrimaryKey(), EventTypes.MODIFY.name(), documentDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = DocumentDescriptionFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = DocumentDescriptionFactory.getInstance().prepareStatement(query);
            
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
        DocumentDescription documentDescription = getDocumentDescription(document, language);
        
        if(documentDescription == null && !language.getIsDefault()) {
            documentDescription = getDocumentDescription(document, getPartyControl().getDefaultLanguage());
        }
        
        if(documentDescription == null) {
            description = document.getLastDetail().getDocumentName();
        } else {
            description = documentDescription.getDescription();
        }
        
        return description;
    }
    
    public DocumentDescriptionTransfer getDocumentDescriptionTransfer(UserVisit userVisit, DocumentDescription documentDescription) {
        return getDocumentTransferCaches(userVisit).getDocumentDescriptionTransferCache().getDocumentDescriptionTransfer(documentDescription);
    }
    
    public List<DocumentDescriptionTransfer> getDocumentDescriptionTransfersByDocument(UserVisit userVisit, Document document) {
        List<DocumentDescription> documentDescriptions = getDocumentDescriptionsByDocument(document);
        List<DocumentDescriptionTransfer> documentDescriptionTransfers = null;
        
        if(documentDescriptions != null) {
            documentDescriptionTransfers = new ArrayList<>(documentDescriptions.size());
            
            for(DocumentDescription documentDescription: documentDescriptions) {
                documentDescriptionTransfers.add(getDocumentTransferCaches(userVisit).getDocumentDescriptionTransferCache().getDocumentDescriptionTransfer(documentDescription));
            }
        }
        
        return documentDescriptionTransfers;
    }
    
    public void updateDocumentDescriptionFromValue(DocumentDescriptionValue documentDescriptionValue, BasePK updatedBy) {
        if(documentDescriptionValue.hasBeenModified()) {
            DocumentDescription documentDescription = DocumentDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, documentDescriptionValue.getPrimaryKey());
            
            documentDescription.setThruTime(session.START_TIME_LONG);
            documentDescription.store();
            
            Document document = documentDescription.getDocument();
            Language language = documentDescription.getLanguage();
            String description = documentDescriptionValue.getDescription();
            
            documentDescription = DocumentDescriptionFactory.getInstance().create(document, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(document.getPrimaryKey(), EventTypes.MODIFY.name(), documentDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteDocumentDescription(DocumentDescription documentDescription, BasePK deletedBy) {
        documentDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(documentDescription.getDocumentPK(), EventTypes.MODIFY.name(), documentDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }
    
    public void deleteDocumentDescriptionsByDocument(Document document, BasePK deletedBy) {
        List<DocumentDescription> documentDescriptions = getDocumentDescriptionsByDocumentForUpdate(document);
        
        documentDescriptions.forEach((documentDescription) -> 
                deleteDocumentDescription(documentDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Party Type Document Type Usage Types
    // --------------------------------------------------------------------------------

    public PartyTypeDocumentTypeUsageType createPartyTypeDocumentTypeUsageType(PartyType partyType, DocumentTypeUsageType documentTypeUsageType,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        PartyTypeDocumentTypeUsageType defaultPartyTypeDocumentTypeUsageType = getDefaultPartyTypeDocumentTypeUsageType(partyType);
        boolean defaultFound = defaultPartyTypeDocumentTypeUsageType != null;

        if(defaultFound && isDefault) {
            PartyTypeDocumentTypeUsageTypeValue defaultPartyTypeDocumentTypeUsageTypeValue = getDefaultPartyTypeDocumentTypeUsageTypeValueForUpdate(partyType);

            defaultPartyTypeDocumentTypeUsageTypeValue.setIsDefault(Boolean.FALSE);
            updatePartyTypeDocumentTypeUsageTypeFromValue(defaultPartyTypeDocumentTypeUsageTypeValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType = PartyTypeDocumentTypeUsageTypeFactory.getInstance().create(partyType,
                documentTypeUsageType, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(documentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY.name(), partyTypeDocumentTypeUsageType.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        return getDocumentTransferCaches(userVisit).getPartyTypeDocumentTypeUsageTypeTransferCache().getPartyTypeDocumentTypeUsageTypeTransfer(partyTypeDocumentTypeUsageType);
    }

    public List<PartyTypeDocumentTypeUsageTypeTransfer> getPartyTypeDocumentTypeUsageTypeTransfers(UserVisit userVisit, List<PartyTypeDocumentTypeUsageType> partyTypeDocumentTypeUsageTypes) {
        List<PartyTypeDocumentTypeUsageTypeTransfer> partyTypeDocumentTypeUsageTypeTransfers = new ArrayList<>(partyTypeDocumentTypeUsageTypes.size());
        PartyTypeDocumentTypeUsageTypeTransferCache partyTypeDocumentTypeUsageTypeTransferCache = getDocumentTransferCaches(userVisit).getPartyTypeDocumentTypeUsageTypeTransferCache();

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
            PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType = PartyTypeDocumentTypeUsageTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyTypeDocumentTypeUsageTypeValue.getPrimaryKey());

            partyTypeDocumentTypeUsageType.setThruTime(session.START_TIME_LONG);
            partyTypeDocumentTypeUsageType.store();

            PartyType partyType = partyTypeDocumentTypeUsageType.getPartyType();
            PartyTypePK partyTypePK = partyType.getPrimaryKey(); // Not updated
            DocumentTypeUsageTypePK documentTypeUsageTypePK = partyTypeDocumentTypeUsageType.getDocumentTypeUsageTypePK(); // Not updated
            Boolean isDefault = partyTypeDocumentTypeUsageTypeValue.getIsDefault();
            Integer sortOrder = partyTypeDocumentTypeUsageTypeValue.getSortOrder();

            if(checkDefault) {
                PartyTypeDocumentTypeUsageType defaultPartyTypeDocumentTypeUsageType = getDefaultPartyTypeDocumentTypeUsageType(partyType);
                boolean defaultFound = defaultPartyTypeDocumentTypeUsageType != null && !defaultPartyTypeDocumentTypeUsageType.equals(partyTypeDocumentTypeUsageType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PartyTypeDocumentTypeUsageTypeValue defaultPartyTypeDocumentTypeUsageTypeValue = getDefaultPartyTypeDocumentTypeUsageTypeValueForUpdate(partyType);

                    defaultPartyTypeDocumentTypeUsageTypeValue.setIsDefault(Boolean.FALSE);
                    updatePartyTypeDocumentTypeUsageTypeFromValue(defaultPartyTypeDocumentTypeUsageTypeValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            partyTypeDocumentTypeUsageType = PartyTypeDocumentTypeUsageTypeFactory.getInstance().create(partyTypePK, documentTypeUsageTypePK, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(documentTypeUsageTypePK, EventTypes.MODIFY.name(), partyTypeDocumentTypeUsageType.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void updatePartyTypeDocumentTypeUsageTypeFromValue(PartyTypeDocumentTypeUsageTypeValue partyTypeDocumentTypeUsageTypeValue, BasePK updatedBy) {
        updatePartyTypeDocumentTypeUsageTypeFromValue(partyTypeDocumentTypeUsageTypeValue, true, updatedBy);
    }

    private void deletePartyTypeDocumentTypeUsageType(PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType, boolean checkDefault, BasePK deletedBy) {
        PartyType partyType = partyTypeDocumentTypeUsageType.getPartyType();

        partyTypeDocumentTypeUsageType.setThruTime(session.START_TIME_LONG);
        partyTypeDocumentTypeUsageType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            PartyTypeDocumentTypeUsageType defaultPartyTypeDocumentTypeUsageType = getDefaultPartyTypeDocumentTypeUsageType(partyType);

            if(defaultPartyTypeDocumentTypeUsageType == null) {
                List<PartyTypeDocumentTypeUsageType> partyTypeDocumentTypeUsageTypes = getPartyTypeDocumentTypeUsageTypesByPartyTypeForUpdate(partyType);

                if(!partyTypeDocumentTypeUsageTypes.isEmpty()) {
                    Iterator<PartyTypeDocumentTypeUsageType> iter = partyTypeDocumentTypeUsageTypes.iterator();
                    if(iter.hasNext()) {
                        defaultPartyTypeDocumentTypeUsageType = iter.next();
                    }
                    PartyTypeDocumentTypeUsageTypeValue partyTypeDocumentTypeUsageTypeDetailValue = defaultPartyTypeDocumentTypeUsageType.getPartyTypeDocumentTypeUsageTypeValue().clone();

                    partyTypeDocumentTypeUsageTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updatePartyTypeDocumentTypeUsageTypeFromValue(partyTypeDocumentTypeUsageTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(partyTypeDocumentTypeUsageType.getDocumentTypeUsageTypePK(), EventTypes.MODIFY.name(), partyTypeDocumentTypeUsageType.getPrimaryKey(), EventTypes.DELETE.name(),deletedBy);
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
        DocumentType documentType = document.getLastDetail().getDocumentType();
        PartyDocument defaultPartyDocument = getDefaultPartyDocument(party, documentType);
        boolean defaultFound = defaultPartyDocument != null;

        if(defaultFound && isDefault) {
            PartyDocumentValue defaultPartyDocumentValue = getDefaultPartyDocumentValueForUpdate(party, documentType);

            defaultPartyDocumentValue.setIsDefault(Boolean.FALSE);
            updatePartyDocumentFromValue(defaultPartyDocumentValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        PartyDocument partyDocument = PartyDocumentFactory.getInstance().create(party, document, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyDocument.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
                "WHERE pardcmnt_par_partyid = ? AND dcmnttyputypdt_documenttypeusagetypename = ? " +
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
        return getDocumentTransferCaches(userVisit).getPartyDocumentTransferCache().getPartyDocumentTransfer(partyDocument);
    }

    public List<PartyDocumentTransfer> getPartyDocumentTransfers(UserVisit userVisit, List<PartyDocument> partyDocuments) {
        List<PartyDocumentTransfer> partyDocumentTransfers = new ArrayList<>(partyDocuments.size());
        PartyDocumentTransferCache partyDocumentTransferCache = getDocumentTransferCaches(userVisit).getPartyDocumentTransferCache();

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
        List<PartyDocument> partyDocuments = getPartyDocumentsByPartyAndDocumentType(party, documentType);
        int size = partyDocuments.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultDocumentChoice == null) {
                defaultValue = "";
            }
        }

        for(PartyDocument partyDocument: partyDocuments) {
            Document document = partyDocument.getDocument();
            DocumentDetail documentDetail = document.getLastDetail();

            String label = getBestDocumentDescription(document, language);
            String value = documentDetail.getDocumentName();

            labels.add(label == null ? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultDocumentChoice == null ? false: defaultDocumentChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && partyDocument.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new DocumentChoicesBean(labels, values, defaultValue);
    }

    private void updatePartyDocumentFromValue(PartyDocumentValue partyDocumentValue, boolean checkDefault, BasePK updatedBy) {
        if(partyDocumentValue.hasBeenModified()) {
            PartyDocument partyDocument = PartyDocumentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, partyDocumentValue.getPrimaryKey());

            partyDocument.setThruTime(session.START_TIME_LONG);
            partyDocument.store();

            Party party = partyDocument.getParty();
            PartyPK partyPK = party.getPrimaryKey(); // Not updated
            Document document = partyDocument.getDocument();
            DocumentPK documentPK = document.getPrimaryKey(); // Not updated
            Boolean isDefault = partyDocumentValue.getIsDefault();
            Integer sortOrder = partyDocumentValue.getSortOrder();

            if(checkDefault) {
                DocumentType documentType = document.getLastDetail().getDocumentType();
                PartyDocument defaultPartyDocument = getDefaultPartyDocument(party, documentType);
                boolean defaultFound = defaultPartyDocument != null && !defaultPartyDocument.equals(partyDocument);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    PartyDocumentValue defaultPartyDocumentValue = getDefaultPartyDocumentValueForUpdate(party, documentType);

                    defaultPartyDocumentValue.setIsDefault(Boolean.FALSE);
                    updatePartyDocumentFromValue(defaultPartyDocumentValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            partyDocument = PartyDocumentFactory.getInstance().create(partyPK, documentPK, isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);

            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partyDocument.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void updatePartyDocumentFromValue(PartyDocumentValue partyDocumentValue, BasePK updatedBy) {
        updatePartyDocumentFromValue(partyDocumentValue, true, updatedBy);
    }

    private void deletePartyDocument(PartyDocument partyDocument, boolean checkDefault, BasePK deletedBy) {
        Party party = partyDocument.getParty();
        Document document = partyDocument.getDocumentForUpdate();

        deleteDocument(document, deletedBy);

        partyDocument.setThruTime(session.START_TIME_LONG);
        partyDocument.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            DocumentType documentType = document.getLastDetail().getDocumentType();
            PartyDocument defaultPartyDocument = getDefaultPartyDocument(party, documentType);

            if(defaultPartyDocument == null) {
                List<PartyDocument> partyDocuments = getPartyDocumentsByPartyAndDocumentTypeForUpdate(party, documentType);

                if(!partyDocuments.isEmpty()) {
                    Iterator<PartyDocument> iter = partyDocuments.iterator();
                    if(iter.hasNext()) {
                        defaultPartyDocument = iter.next();
                    }
                    PartyDocumentValue partyDocumentDetailValue = defaultPartyDocument.getPartyDocumentValue().clone();

                    partyDocumentDetailValue.setIsDefault(Boolean.TRUE);
                    updatePartyDocumentFromValue(partyDocumentDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(partyDocument.getPartyPK(), EventTypes.MODIFY.name(), partyDocument.getPrimaryKey(), EventTypes.DELETE.name(),deletedBy);
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
