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

package com.echothree.control.user.rating.server.command;

import com.echothree.control.user.rating.common.form.GetRatingTypeListItemChoicesForm;
import com.echothree.control.user.rating.common.result.RatingResultFactory;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.rating.server.logic.RatingLogic;
import com.echothree.model.control.rating.server.logic.RatingTypeLogic;
import com.echothree.model.data.rating.server.entity.RatingType;
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
public class GetRatingTypeListItemChoicesCommand
        extends BaseSimpleCommand<GetRatingTypeListItemChoicesForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, false, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("RatingName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("DefaultRatingTypeListItemChoice", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("AllowNullChoice", FieldType.BOOLEAN, true, null, null)
                ));
    }
    
    /** Creates a new instance of GetRatingTypeListItemsCommand */
    public GetRatingTypeListItemChoicesCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = RatingResultFactory.getGetRatingTypeListItemChoicesResult();
        var componentVendorName = form.getComponentVendorName();
        var entityTypeName = form.getEntityTypeName();
        var ratingTypeName = form.getRatingTypeName();
        var ratingName = form.getRatingName();
        var parameterCount = (componentVendorName == null && entityTypeName == null && ratingTypeName == null ? 0 : 1) + (ratingName == null ? 0 : 1);
        
        if(parameterCount == 1) {
            RatingType ratingType = null;
            
            if(ratingName == null) {
                ratingType = RatingTypeLogic.getInstance().getRatingTypeByName(this, componentVendorName, entityTypeName, ratingTypeName);
            } else {
                var rating = RatingLogic.getInstance().getRatingByName(this, ratingName);
                
                if(!hasExecutionErrors()) {
                    ratingType = rating.getLastDetail().getRatingTypeListItem().getLastDetail().getRatingType();
                }
            }
            
            if(!hasExecutionErrors()) {
                var ratingControl = Session.getModelController(RatingControl.class);
                var allowNullChoice = Boolean.parseBoolean(form.getAllowNullChoice());

                result.setRatingTypeListItemChoices(ratingControl.getRatingTypeListItemChoices(form.getDefaultRatingTypeListItemChoice(), getPreferredLanguage(),
                        allowNullChoice, ratingType));
            }
        } else {
            addExecutionError(ExecutionErrors.InvalidParameterCount.name());
        }
        
        return result;
    }
    
}
