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

package com.echothree.control.user.printer.server.command;

import com.echothree.control.user.printer.common.form.CreatePrinterGroupJobForm;
import com.echothree.control.user.printer.common.result.CreatePrinterGroupJobResult;
import com.echothree.control.user.printer.common.result.PrinterResultFactory;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.printer.server.control.PrinterControl;
import com.echothree.model.control.printer.server.logic.PrinterGroupJobLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.printer.server.entity.PrinterGroup;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.type.ByteArray;
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
import javax.enterprise.context.Dependent;

@Dependent
public class CreatePrinterGroupJobCommand
        extends BaseSimpleCommand<CreatePrinterGroupJobForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.PrinterGroupJob.name(), SecurityRoles.Create.name())
                    )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PrinterGroupName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("MimeTypeName", FieldType.MIME_TYPE, true, null, null),
                new FieldDefinition("Copies", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Priority", FieldType.UNSIGNED_INTEGER, true, 1L, 100L),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L),
                new FieldDefinition("Clob", FieldType.STRING, false, 1L, null)
                ));
    }
    
    /** Creates a new instance of CreatePrinterGroupJobCommand */
    public CreatePrinterGroupJobCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }

    private void createPrinterGroupJob(final PrinterGroup printerGroup, final MimeType mimeType, final ByteArray blob, final String clob,
            final CreatePrinterGroupJobResult result) {
        var copies = Integer.valueOf(form.getCopies());
        var priority = Integer.valueOf(form.getPriority());
        var description = form.getDescription();

        var printerGroupJob = PrinterGroupJobLogic.getInstance().createPrinterGroupJob(this, printerGroup, copies, priority, mimeType,
                getPreferredLanguage(), description, blob, clob, getPartyPK());

        if(!hasExecutionErrors()) {
            result.setEntityRef(printerGroupJob.getPrimaryKey().getEntityRef());
            result.setPrinterGroupJobName(printerGroupJob.getLastDetail().getPrinterGroupJobName());
        }
    }

    @Override
    protected BaseResult execute() {
        var printerControl = Session.getModelController(PrinterControl.class);
        var result = PrinterResultFactory.getCreatePrinterGroupJobResult();
        var printerGroupName = form.getPrinterGroupName();
        var printerGroup = printerControl.getPrinterGroupByName(printerGroupName);

        if(printerGroup != null) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var mimeTypeName = form.getMimeTypeName();
            var mimeType = mimeTypeControl.getMimeTypeByName(mimeTypeName);

            if(mimeType != null) {
                var entityAttributeType = mimeType.getLastDetail().getEntityAttributeType();
                var entityAttributeTypeName = entityAttributeType.getEntityAttributeTypeName();

                if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
                    var blob = form.getBlob();

                    if(blob != null) {
                        createPrinterGroupJob(printerGroup, mimeType, blob, null, result);
                    } else {
                        addExecutionError(ExecutionErrors.MissingBlob.name());
                    }
                } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
                    var clob = form.getClob();

                    if(clob != null) {
                        createPrinterGroupJob(printerGroup, mimeType, null, clob, result);
                    } else {
                        addExecutionError(ExecutionErrors.MissingClob.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownMimeTypeName.name(), mimeTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPrinterGroupName.name(), printerGroupName);
        }

        return result;
    }
    
}
