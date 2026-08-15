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

package com.echothree.model.control.document.server.logic;

import com.echothree.control.user.document.common.spec.DocumentTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.document.common.exception.DuplicateDocumentTypeNameException;
import com.echothree.model.control.document.common.exception.UnknownDefaultDocumentTypeException;
import com.echothree.model.control.document.common.exception.UnknownDocumentTypeNameException;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class DocumentTypeLogic
        extends BaseLogic {

    @Inject
    DocumentControl documentControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected DocumentTypeLogic() {
        super();
    }

    public static DocumentTypeLogic getInstance() {
        return CDI.current().select(DocumentTypeLogic.class).get();
    }

    public DocumentType createDocumentType(final ExecutionErrorAccumulator eea, final String documentTypeName,
            final DocumentType parentDocumentType, final MimeTypeUsageType mimeTypeUsageType, final Integer maximumPages,
            final Boolean isDefault, final Integer sortOrder, final Language language, final String description,
            final BasePK createdBy) {
        var documentType = documentControl.getDocumentTypeByName(documentTypeName);

        if(documentType == null) {
            documentType = documentControl.createDocumentType(documentTypeName, parentDocumentType, mimeTypeUsageType,
                    maximumPages, isDefault, sortOrder, createdBy);

            if(description != null) {
                documentControl.createDocumentTypeDescription(documentType, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateDocumentTypeNameException.class, eea,
                    ExecutionErrors.DuplicateDocumentTypeName.name(), documentType.getLastDetail().getDocumentTypeName());
        }

        return documentType;
    }

    public DocumentType getDocumentTypeByName(final ExecutionErrorAccumulator eea, final String documentTypeName,
            final EntityPermission entityPermission) {
        var documentType = documentControl.getDocumentTypeByName(documentTypeName, entityPermission);

        if(documentType == null) {
            handleExecutionError(UnknownDocumentTypeNameException.class, eea,
                    ExecutionErrors.UnknownDocumentTypeName.name(), documentTypeName);
        }

        return documentType;
    }

    public DocumentType getDocumentTypeByName(final ExecutionErrorAccumulator eea, final String documentTypeName) {
        return getDocumentTypeByName(eea, documentTypeName, EntityPermission.READ_ONLY);
    }

    public DocumentType getDocumentTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String documentTypeName) {
        return getDocumentTypeByName(eea, documentTypeName, EntityPermission.READ_WRITE);
    }

    public DocumentType getDocumentTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final DocumentTypeUniversalSpec universalSpec, final boolean allowDefault,
            final EntityPermission entityPermission) {
        DocumentType documentType = null;
        var documentTypeName = universalSpec.getDocumentTypeName();
        var parameterCount = (documentTypeName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    documentType = documentControl.getDefaultDocumentType(entityPermission);

                    if(documentType == null) {
                        handleExecutionError(UnknownDefaultDocumentTypeException.class, eea,
                                ExecutionErrors.UnknownDefaultDocumentType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(documentTypeName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.DocumentType.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        documentType = documentControl.getDocumentTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    documentType = getDocumentTypeByName(eea, documentTypeName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
        }

        return documentType;
    }

    public DocumentType getDocumentTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final DocumentTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getDocumentTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public DocumentType getDocumentTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final DocumentTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getDocumentTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteDocumentType(final ExecutionErrorAccumulator eea, final DocumentType documentType,
            final BasePK deletedBy) {
        documentControl.deleteDocumentType(documentType, deletedBy);
    }

}
