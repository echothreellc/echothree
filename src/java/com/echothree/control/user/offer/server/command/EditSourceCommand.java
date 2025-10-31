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

package com.echothree.control.user.offer.server.command;

import com.echothree.control.user.offer.common.edit.OfferEditFactory;
import com.echothree.control.user.offer.common.edit.SourceEdit;
import com.echothree.control.user.offer.common.form.EditSourceForm;
import com.echothree.control.user.offer.common.result.EditSourceResult;
import com.echothree.control.user.offer.common.result.OfferResultFactory;
import com.echothree.control.user.offer.common.spec.SourceSpec;
import com.echothree.model.control.offer.server.control.SourceControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditSourceCommand
        extends BaseAbstractEditCommand<SourceSpec, SourceEdit, EditSourceResult, Source, Source> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.Source.name(), SecurityRoles.Edit.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SourceName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("SourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
        );
    }

    /** Creates a new instance of EditSourceCommand */
    public EditSourceCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditSourceResult getResult() {
        return OfferResultFactory.getEditSourceResult();
    }

    @Override
    public SourceEdit getEdit() {
        return OfferEditFactory.getSourceEdit();
    }

    @Override
    public Source getEntity(EditSourceResult result) {
        var sourceControl = Session.getModelController(SourceControl.class);
        Source source;
        var sourceName = spec.getSourceName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            source = sourceControl.getSourceByName(sourceName);
        } else { // EditMode.UPDATE
            source = sourceControl.getSourceByNameForUpdate(sourceName);
        }

        if(source != null) {
            result.setSource(sourceControl.getSourceTransfer(getUserVisit(), source));
        } else {
            addExecutionError(ExecutionErrors.UnknownSourceName.name(), sourceName);
        }

        return source;
    }

    @Override
    public Source getLockEntity(Source source) {
        return source;
    }

    @Override
    public void fillInResult(EditSourceResult result, Source source) {
        var sourceControl = Session.getModelController(SourceControl.class);

        result.setSource(sourceControl.getSourceTransfer(getUserVisit(), source));
    }

    @Override
    public void doLock(SourceEdit edit, Source source) {
        var sourceDetail = source.getLastDetail();

        edit.setSourceName(sourceDetail.getSourceName());
        edit.setIsDefault(sourceDetail.getIsDefault().toString());
        edit.setSortOrder(sourceDetail.getSortOrder().toString());
    }

    @Override
    public void canUpdate(Source source) {
        var sourceControl = Session.getModelController(SourceControl.class);
        var sourceName = edit.getSourceName();
        var duplicateSource = sourceControl.getSourceByName(sourceName);

        if(duplicateSource != null && !source.equals(duplicateSource)) {
            addExecutionError(ExecutionErrors.DuplicateSourceName.name(), sourceName);
        }
    }

    @Override
    public void doUpdate(Source source) {
        var sourceControl = Session.getModelController(SourceControl.class);
        var partyPK = getPartyPK();
        var sourceDetailValue = sourceControl.getSourceDetailValueForUpdate(source);

        sourceDetailValue.setSourceName(edit.getSourceName());
        sourceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        sourceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        sourceControl.updateSourceFromValue(sourceDetailValue, partyPK);
    }

}
