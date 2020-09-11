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

package com.echothree.control.user.document.server.command;

import com.echothree.control.user.document.common.form.CreatePartyDocumentForm;
import com.echothree.control.user.document.common.result.CreatePartyDocumentResult;
import com.echothree.control.user.document.common.result.DocumentResultFactory;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.document.server.DocumentControl;
import com.echothree.model.control.document.server.logic.DocumentLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.entity.MimeTypeUsage;
import com.echothree.model.data.core.server.entity.MimeTypeUsageType;
import com.echothree.model.data.document.server.entity.Document;
import com.echothree.model.data.document.server.entity.DocumentType;
import com.echothree.model.data.document.server.entity.DocumentTypeUsage;
import com.echothree.model.data.document.server.entity.DocumentTypeUsageType;
import com.echothree.model.data.document.server.entity.PartyDocument;
import com.echothree.model.data.document.server.entity.PartyTypeDocumentTypeUsageType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreatePartyDocumentCommand
        extends BaseSimpleCommand<CreatePartyDocumentForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PartyDocument.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("DocumentTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L),
                new FieldDefinition("Clob", FieldType.STRING, false, 1L, null)
                ));
    }
    
    /** Creates a new instance of CreatePartyDocumentCommand */
    public CreatePartyDocumentCommand(UserVisitPK userVisitPK, CreatePartyDocumentForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    private void createPartyDocument(Party party, DocumentType documentType, MimeType mimeType, ByteArray blob, String clob, CreatePartyDocumentResult result) {
        var isDefault = Boolean.valueOf(form.getIsDefault());
        var sortOrder = Integer.valueOf(form.getSortOrder());
        var description = form.getDescription();

        PartyDocument partyDocument = DocumentLogic.getInstance().createPartyDocument(this, party, documentType, mimeType, isDefault, sortOrder,
                getPreferredLanguage(), description, blob, clob, getPartyPK());

        if(!hasExecutionErrors()) {
            Document document = partyDocument.getDocument();

            result.setEntityRef(document.getPrimaryKey().getEntityRef());
            result.setDocumentName(document.getLastDetail().getDocumentName());
        }
    }

    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        CreatePartyDocumentResult result = DocumentResultFactory.getCreatePartyDocumentResult();
        String partyName = form.getPartyName();
        Party party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var documentControl = (DocumentControl)Session.getModelController(DocumentControl.class);
            String documentTypeName = form.getDocumentTypeName();
            DocumentType documentType = documentControl.getDocumentTypeByName(documentTypeName);

            if(documentType != null) {
                List<DocumentTypeUsage> documentTypeUsages = documentControl.getDocumentTypeUsagesByDocumentType(documentType);
                PartyType partyType = party.getLastDetail().getPartyType();
                Integer maximumInstances = null;
                boolean foundPartyTypeDocumentTypeUsageType = false;

                for(DocumentTypeUsage documentTypeUsage : documentTypeUsages) {
                    DocumentTypeUsageType documentTypeUsageType = documentTypeUsage.getDocumentTypeUsageType();
                    PartyTypeDocumentTypeUsageType partyTypeDocumentTypeUsageType = documentControl.getPartyTypeDocumentTypeUsageType(partyType, documentTypeUsageType);

                    if(partyTypeDocumentTypeUsageType != null) {
                        foundPartyTypeDocumentTypeUsageType = true;

                        if(maximumInstances == null) {
                            maximumInstances = documentTypeUsage.getMaximumInstances();
                        } else {
                            Integer foundMaximumInstances = documentTypeUsage.getMaximumInstances();

                            if(foundMaximumInstances != null && foundMaximumInstances > maximumInstances) {
                                maximumInstances = foundMaximumInstances;
                            }
                        }
                    }
                }

                if(foundPartyTypeDocumentTypeUsageType) {
                    if(maximumInstances != null) {
                        Long instances = documentControl.countPartyDocumentsByPartyAndDocumentType(party, documentType);

                        if(instances >= maximumInstances) {
                            addExecutionError(ExecutionErrors.MaximumInstancesExceeded.name());
                        }
                    }

                    if(!hasExecutionErrors()) {
                        var coreControl = getCoreControl();
                        String mimeTypeName = form.getMimeTypeName();
                        MimeType mimeType = coreControl.getMimeTypeByName(mimeTypeName);

                        if(mimeType != null) {
                            MimeTypeUsageType mimeTypeUsageType = documentType.getLastDetail().getMimeTypeUsageType();
                            MimeTypeUsage mimeTypeUsage = mimeTypeUsageType == null ? null : coreControl.getMimeTypeUsage(mimeType, mimeTypeUsageType);

                            if(mimeTypeUsageType == null || mimeTypeUsage != null) {
                                EntityAttributeType entityAttributeType = mimeType.getLastDetail().getEntityAttributeType();
                                String entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

                                if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                                    ByteArray blob = form.getBlob();

                                    if(blob != null) {
                                        createPartyDocument(party, documentType, mimeType, blob, null, result);
                                    } else {
                                        addExecutionError(ExecutionErrors.MissingBlob.name());
                                    }
                                } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                                    String clob = form.getClob();

                                    if(clob != null) {
                                        createPartyDocument(party, documentType, mimeType, null, clob, result);
                                    } else {
                                        addExecutionError(ExecutionErrors.MissingClob.name());
                                    }
                                } else {
                                    addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.UnknownMimeTypeUsage.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownPartyTypeDocumentTypeUsageType.name());
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownPartyName.name(), documentTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }

        return result;
    }
    
}
