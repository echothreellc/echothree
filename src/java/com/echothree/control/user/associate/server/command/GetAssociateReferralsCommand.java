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

package com.echothree.control.user.associate.server.command;

import com.echothree.control.user.associate.common.form.GetAssociateReferralsForm;
import com.echothree.control.user.associate.common.result.AssociateResultFactory;
import com.echothree.model.control.associate.server.control.AssociateControl;
import com.echothree.model.control.associate.server.logic.AssociateLogic;
import com.echothree.model.data.associate.server.entity.Associate;
import com.echothree.model.data.associate.server.entity.AssociateReferral;
import com.echothree.model.data.associate.server.factory.AssociateReferralFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetAssociateReferralsCommand
        extends BasePaginatedMultipleEntitiesCommand<AssociateReferral, GetAssociateReferralsForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("AssociateProgramName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("AssociateName", FieldType.ENTITY_NAME, true, null, null)
        );
    }

    @Inject
    AssociateControl associateControl;

    @Inject
    AssociateLogic associateLogic;

    /** Creates a new instance of GetAssociateReferralsCommand */
    public GetAssociateReferralsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
    Associate associate;

    @Override
    protected void handleForm() {
        associate = associateLogic.getAssociateByName(this, form.getAssociateProgramName(), form.getAssociateName());
    }

    @Override
    protected Long getTotalEntities() {
        return hasExecutionErrors() ? null : associateControl.countAssociateReferralsByAssociate(associate);
    }

    @Override
    protected Collection<AssociateReferral> getEntities() {
        return hasExecutionErrors() ? null : associateControl.getAssociateReferralsByAssociate(associate);
    }

    @Override
    protected BaseResult getResult(final Collection<AssociateReferral> entities) {
        var result = AssociateResultFactory.getGetAssociateReferralsResult();

        if(entities != null) {
            var userVisit = getUserVisit();

            result.setAssociate(associateControl.getAssociateTransfer(userVisit, associate));

            if(session.hasLimit(AssociateReferralFactory.class)) {
                result.setAssociateReferralCount(getTotalEntities());
            }

            result.setAssociateReferrals(associateControl.getAssociateReferralTransfers(entities, userVisit));
        }

        return result;
    }
    
}
