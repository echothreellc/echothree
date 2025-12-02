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

package com.echothree.control.user.invoice.server.command;

import com.echothree.control.user.invoice.common.edit.InvoiceEditFactory;
import com.echothree.control.user.invoice.common.edit.InvoiceLineTypeDescriptionEdit;
import com.echothree.control.user.invoice.common.form.EditInvoiceLineTypeDescriptionForm;
import com.echothree.control.user.invoice.common.result.InvoiceResultFactory;
import com.echothree.control.user.invoice.common.spec.InvoiceLineTypeDescriptionSpec;
import com.echothree.model.control.invoice.server.control.InvoiceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditInvoiceLineTypeDescriptionCommand
        extends BaseEditCommand<InvoiceLineTypeDescriptionSpec, InvoiceLineTypeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.InvoiceLineType.name(), SecurityRoles.Description.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("InvoiceTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InvoiceLineTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditInvoiceLineTypeDescriptionCommand */
    public EditInvoiceLineTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var invoiceControl = Session.getModelController(InvoiceControl.class);
        var result = InvoiceResultFactory.getEditInvoiceLineTypeDescriptionResult();
        var invoiceTypeName = spec.getInvoiceTypeName();
        var invoiceType = invoiceControl.getInvoiceTypeByName(invoiceTypeName);
        
        if(invoiceType != null) {
            var invoiceLineTypeName = spec.getInvoiceLineTypeName();
            var invoiceLineType = invoiceControl.getInvoiceLineTypeByName(invoiceType, invoiceLineTypeName);
            
            if(invoiceLineType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var invoiceLineTypeDescription = invoiceControl.getInvoiceLineTypeDescription(invoiceLineType, language);
                        
                        if(invoiceLineTypeDescription != null) {
                            result.setInvoiceLineTypeDescription(invoiceControl.getInvoiceLineTypeDescriptionTransfer(getUserVisit(), invoiceLineTypeDescription));
                            
                            if(lockEntity(invoiceLineType)) {
                                var edit = InvoiceEditFactory.getInvoiceLineTypeDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(invoiceLineTypeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(invoiceLineType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var invoiceLineTypeDescriptionValue = invoiceControl.getInvoiceLineTypeDescriptionValueForUpdate(invoiceLineType, language);
                        
                        if(invoiceLineTypeDescriptionValue != null) {
                            if(lockEntityForUpdate(invoiceLineType)) {
                                try {
                                    var description = edit.getDescription();
                                    
                                    invoiceLineTypeDescriptionValue.setDescription(description);
                                    
                                    invoiceControl.updateInvoiceLineTypeDescriptionFromValue(invoiceLineTypeDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(invoiceLineType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownInvoiceLineTypeName.name(), invoiceLineTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownInvoiceTypeName.name(), invoiceTypeName);
        }
        
        return result;
    }
    
}
