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

package com.echothree.control.user.scale.server.command;

import com.echothree.control.user.scale.common.form.GetScaleUseTypeDescriptionsForm;
import com.echothree.control.user.scale.common.result.ScaleResultFactory;
import com.echothree.model.control.scale.server.control.ScaleControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GetScaleUseTypeDescriptionsCommand
        extends BaseSimpleCommand<GetScaleUseTypeDescriptionsForm> {
    
   private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ScaleUseTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of GetScaleUseTypeDescriptionsCommand */
    public GetScaleUseTypeDescriptionsCommand() {
        super(null, FORM_FIELD_DEFINITIONS, true);
    }
    
   @Override
    protected BaseResult execute() {
        var scaleControl = Session.getModelController(ScaleControl.class);
       var result = ScaleResultFactory.getGetScaleUseTypeDescriptionsResult();
       var scaleUseTypeName = form.getScaleUseTypeName();
       var scaleUseType = scaleControl.getScaleUseTypeByName(scaleUseTypeName);
        
        if(scaleUseType != null) {
            result.setScaleUseType(scaleControl.getScaleUseTypeTransfer(getUserVisit(), scaleUseType));
            result.setScaleUseTypeDescriptions(scaleControl.getScaleUseTypeDescriptionTransfersByScaleUseType(getUserVisit(), scaleUseType));
        } else {
            addExecutionError(ExecutionErrors.UnknownScaleUseTypeName.name(), scaleUseTypeName);
        }
        
        return result;
    }
    
}
