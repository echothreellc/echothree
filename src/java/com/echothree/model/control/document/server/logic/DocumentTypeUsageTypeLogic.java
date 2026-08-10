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

import com.echothree.control.user.document.common.spec.DocumentTypeUsageTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.document.common.exception.DuplicateDocumentTypeUsageTypeNameException;
import com.echothree.model.control.document.common.exception.UnknownDefaultDocumentTypeUsageTypeException;
import com.echothree.model.control.document.common.exception.UnknownDocumentTypeUsageTypeNameException;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
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
public class DocumentTypeUsageTypeLogic
        extends BaseLogic {

    @Inject
    DocumentControl documentControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected DocumentTypeUsageTypeLogic() {
        super();
    }

    public static DocumentTypeUsageTypeLogic getInstance() {
        return CDI.current().select(DocumentTypeUsageTypeLogic.class).get();
    }

    public DocumentTypeUsageType createDocumentTypeUsageType(final ExecutionErrorAccumulator eea,
            final String documentTypeUsageTypeName, final Boolean isDefault, final Integer sortOrder,
            final Language language, final String description, final BasePK createdBy) {
        var documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName);

        if(documentTypeUsageType == null) {
            documentTypeUsageType = documentControl.createDocumentTypeUsageType(documentTypeUsageTypeName,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                documentControl.createDocumentTypeUsageTypeDescription(documentTypeUsageType, language,
                        description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateDocumentTypeUsageTypeNameException.class, eea,
                    ExecutionErrors.DuplicateDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
        }

        return documentTypeUsageType;
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByName(final ExecutionErrorAccumulator eea,
            final String documentTypeUsageTypeName, final EntityPermission entityPermission) {
        var documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByName(documentTypeUsageTypeName,
                entityPermission);

        if(documentTypeUsageType == null) {
            handleExecutionError(UnknownDocumentTypeUsageTypeNameException.class, eea,
                    ExecutionErrors.UnknownDocumentTypeUsageTypeName.name(), documentTypeUsageTypeName);
        }

        return documentTypeUsageType;
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByName(final ExecutionErrorAccumulator eea,
            final String documentTypeUsageTypeName) {
        return getDocumentTypeUsageTypeByName(eea, documentTypeUsageTypeName, EntityPermission.READ_ONLY);
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByNameForUpdate(final ExecutionErrorAccumulator eea,
            final String documentTypeUsageTypeName) {
        return getDocumentTypeUsageTypeByName(eea, documentTypeUsageTypeName, EntityPermission.READ_WRITE);
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final DocumentTypeUsageTypeUniversalSpec universalSpec, final boolean allowDefault,
            final EntityPermission entityPermission) {
        DocumentTypeUsageType documentTypeUsageType = null;
        var documentTypeUsageTypeName = universalSpec.getDocumentTypeUsageTypeName();
        var parameterCount = (documentTypeUsageTypeName == null ? 0 : 1)
                + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 0 -> {
                if(allowDefault) {
                    documentTypeUsageType = documentControl.getDefaultDocumentTypeUsageType(entityPermission);

                    if(documentTypeUsageType == null) {
                        handleExecutionError(UnknownDefaultDocumentTypeUsageTypeException.class, eea,
                                ExecutionErrors.UnknownDefaultDocumentTypeUsageType.name());
                    }
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                if(documentTypeUsageTypeName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.DocumentTypeUsageType.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        documentTypeUsageType = documentControl.getDocumentTypeUsageTypeByEntityInstance(entityInstance,
                                entityPermission);
                    }
                } else {
                    documentTypeUsageType = getDocumentTypeUsageTypeByName(eea, documentTypeUsageTypeName,
                            entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea,
                            ExecutionErrors.InvalidParameterCount.name());
        }

        return documentTypeUsageType;
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final DocumentTypeUsageTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getDocumentTypeUsageTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_ONLY);
    }

    public DocumentTypeUsageType getDocumentTypeUsageTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final DocumentTypeUsageTypeUniversalSpec universalSpec, final boolean allowDefault) {
        return getDocumentTypeUsageTypeByUniversalSpec(eea, universalSpec, allowDefault, EntityPermission.READ_WRITE);
    }

    public void deleteDocumentTypeUsageType(final ExecutionErrorAccumulator eea,
            final DocumentTypeUsageType documentTypeUsageType, final BasePK deletedBy) {
        documentControl.deleteDocumentTypeUsageType(documentTypeUsageType, deletedBy);
    }

}
