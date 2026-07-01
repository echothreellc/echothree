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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.form.GetScalesForm;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.scale.server.entity.Scale;
import com.echothree.model.data.scale.server.factory.ScaleFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.control.BasePaginatedMultipleEntitiesCommand;
import java.util.Collection;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class GetScalesCommand
        extends BasePaginatedMultipleEntitiesCommand<Scale, GetScalesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = List.of();
    }

    @Inject
    ScaleControl scaleControl;

    /** Creates a new instance of GetScalesCommand */
    public GetScalesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }

    @Override
    protected void handleForm() {
        // No form fields.
    }

    @Override
    protected Long getTotalEntities() {
        return scaleControl.countScales();
    }

    @Override
    protected Collection<Scale> getEntities() {
        return scaleControl.getScales();
    }

    @Override
    protected BaseResult getResult(Collection<Scale> entities) {
        var result = ScaleResultFactory.getGetScalesResult();

        if(entities != null) {
            if(session.hasLimit(ScaleFactory.class)) {
                result.setScaleCount(getTotalEntities());
            }

            result.setScales(scaleControl.getScaleTransfers(getUserVisit(), entities));
        }

        return result;
    }

}
