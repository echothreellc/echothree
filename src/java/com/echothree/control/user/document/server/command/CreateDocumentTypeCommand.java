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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.form.CreateDocumentTypeForm;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateDocumentTypeCommand
        extends BaseSimpleCommand<CreateDocumentTypeForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.DocumentType.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("ParentDocumentTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MimeTypeUsageTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("MaximumPages", FieldType.SIGNED_INTEGER, false, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateDocumentTypeCommand */
    public CreateDocumentTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var documentControl = Session.getModelController(DocumentControl.class);
        var documentTypeName = form.getDocumentTypeName();
        var documentType = documentControl.getDocumentTypeByName(documentTypeName);

        if(documentType == null) {
            var parentDocumentTypeName = form.getParentDocumentTypeName();
            DocumentType parentDocumentType = null;

            if(parentDocumentTypeName != null) {
                parentDocumentType = documentControl.getDocumentTypeByName(parentDocumentTypeName);
            }

            if(parentDocumentTypeName == null || parentDocumentType != null) {
                MimeTypeUsageType mimeTypeUsageType = null;
                var mimeTypeUsageTypeName = form.getMimeTypeUsageTypeName();

                if(mimeTypeUsageTypeName != null) {
                    var mimeTypeControl = Session.getModelController(MimeTypeControl.class);

                    mimeTypeUsageType = mimeTypeControl.getMimeTypeUsageTypeByName(mimeTypeUsageTypeName);
                }

                if(mimeTypeUsageTypeName == null || mimeTypeUsageType != null) {
                    var partyPK = getPartyPK();
                    var strMaximumPageCount = form.getMaximumPages();
                    var maximumPageCount = strMaximumPageCount == null ? null : Integer.valueOf(form.getMaximumPages());
                    var isDefault = Boolean.valueOf(form.getIsDefault());
                    var sortOrder = Integer.valueOf(form.getSortOrder());
                    var description = form.getDescription();

                    documentType = documentControl.createDocumentType(documentTypeName, parentDocumentType, mimeTypeUsageType, maximumPageCount, isDefault,
                            sortOrder, partyPK);

                    if(description != null) {
                        documentControl.createDocumentTypeDescription(documentType, getPreferredLanguage(), description, partyPK);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownMimeTypeUsageTypeName.name(), mimeTypeUsageTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownParentDocumentTypeName.name(), parentDocumentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateDocumentTypeName.name(), documentTypeName);
        }

        return null;
    }
    
}
