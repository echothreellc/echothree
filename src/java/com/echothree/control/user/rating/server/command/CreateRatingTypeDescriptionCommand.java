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

import com.echothree.control.user.rating.common.form.CreateRatingTypeDescriptionForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CreateRatingTypeDescriptionCommand
        extends BaseSimpleCommand<CreateRatingTypeDescriptionForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ComponentVendorName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("EntityTypeName", FieldType.ENTITY_TYPE_NAME, true, null, null),
                new FieldDefinition("RatingTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of CreateRatingTypeDescriptionCommand */
    public CreateRatingTypeDescriptionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var componentVendorName = form.getComponentVendorName();
        var componentVendor = componentControl.getComponentVendorByName(componentVendorName);
        
        if(componentVendor != null) {
            var entityTypeName = form.getEntityTypeName();
            var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName);
            
            if(entityType != null) {
                var ratingControl = Session.getModelController(RatingControl.class);
                var ratingTypeName = form.getRatingTypeName();
                var ratingType = ratingControl.getRatingTypeByName(entityType, ratingTypeName);
                
                if(ratingType != null) {
                    var partyControl = Session.getModelController(PartyControl.class);
                    var languageIsoName = form.getLanguageIsoName();
                    var language = partyControl.getLanguageByIsoName(languageIsoName);
                    
                    if(language != null) {
                        var ratingTypeDescription = ratingControl.getRatingTypeDescription(ratingType, language);
                        
                        if(ratingTypeDescription == null) {
                            var description = form.getDescription();
                            
                            ratingControl.createRatingTypeDescription(ratingType, language, description, getPartyPK());
                            } else {
                                addExecutionError(ExecutionErrors.DuplicateRatingTypeDescription.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                        }
                } else {
                    addExecutionError(ExecutionErrors.UnknownRatingTypeName.name(), ratingTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEntityTypeName.name(), entityTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownComponentVendorName.name(), componentVendorName);
        }
        
        return null;
    }
    
}
