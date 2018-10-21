// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.offer.remote.edit.OfferEditFactory;
import com.echothree.control.user.offer.remote.edit.SourceEdit;
import com.echothree.control.user.offer.remote.form.EditSourceForm;
import com.echothree.control.user.offer.remote.result.EditSourceResult;
import com.echothree.control.user.offer.remote.result.OfferResultFactory;
import com.echothree.control.user.offer.remote.spec.SourceSpec;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.offer.server.entity.Source;
import com.echothree.model.data.offer.server.entity.SourceDetail;
import com.echothree.model.data.offer.server.value.SourceDetailValue;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditSourceCommand
        extends BaseAbstractEditCommand<SourceSpec, SourceEdit, EditSourceResult, Source, Source> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Source.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SourceName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("SourceName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
    }

    /** Creates a new instance of EditSourceCommand */
    public EditSourceCommand(UserVisitPK userVisitPK, EditSourceForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
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
        OfferControl geoControl = (OfferControl)Session.getModelController(OfferControl.class);
        Source source = null;
        String sourceName = spec.getSourceName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            source = geoControl.getSourceByName(sourceName);
        } else { // EditMode.UPDATE
            source = geoControl.getSourceByNameForUpdate(sourceName);
        }

        if(source != null) {
            result.setSource(geoControl.getSourceTransfer(getUserVisit(), source));
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
        OfferControl geoControl = (OfferControl)Session.getModelController(OfferControl.class);

        result.setSource(geoControl.getSourceTransfer(getUserVisit(), source));
    }

    @Override
    public void doLock(SourceEdit edit, Source source) {
        OfferControl geoControl = (OfferControl)Session.getModelController(OfferControl.class);
        SourceDetail sourceDetail = source.getLastDetail();

        edit.setSourceName(sourceDetail.getSourceName());
        edit.setIsDefault(sourceDetail.getIsDefault().toString());
        edit.setSortOrder(sourceDetail.getSortOrder().toString());
    }

    @Override
    public void canUpdate(Source source) {
        OfferControl geoControl = (OfferControl)Session.getModelController(OfferControl.class);
        String sourceName = edit.getSourceName();
        Source duplicateSource = geoControl.getSourceByName(sourceName);

        if(duplicateSource != null && !source.equals(duplicateSource)) {
            addExecutionError(ExecutionErrors.DuplicateSourceName.name(), sourceName);
        }
    }

    @Override
    public void doUpdate(Source source) {
        OfferControl geoControl = (OfferControl)Session.getModelController(OfferControl.class);
        PartyPK partyPK = getPartyPK();
        SourceDetailValue sourceDetailValue = geoControl.getSourceDetailValueForUpdate(source);

        sourceDetailValue.setSourceName(edit.getSourceName());
        sourceDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        sourceDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        geoControl.updateSourceFromValue(sourceDetailValue, partyPK);
    }

}
