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

package com.echothree.control.user.term.server.command;

import com.echothree.control.user.term.common.form.CreateTermForm;
import com.echothree.model.control.term.common.TermTypes;
import com.echothree.model.control.term.server.control.TermControl;
import com.echothree.model.data.term.server.entity.Term;
import com.echothree.model.data.term.server.entity.TermType;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateTermCommand
        extends BaseSimpleCommand<CreateTermForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> standardFieldDefinitions;
    private final static List<FieldDefinition> dateDrivenFieldDefinitions;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("TermName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("TermTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null)
                ));
        
        standardFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("NetDueDays", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("DiscountPercentage", FieldType.FRACTIONAL_PERCENT, true, null, null),
                new FieldDefinition("DiscountDays", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));
        
        dateDrivenFieldDefinitions = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("NetDueDayOfMonth", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("DueNextMonthDays", FieldType.UNSIGNED_INTEGER, true, null, null),
                new FieldDefinition("DiscountPercentage", FieldType.FRACTIONAL_PERCENT, true, null, null),
                new FieldDefinition("DiscountBeforeDayOfMonth", FieldType.UNSIGNED_INTEGER, true, null, null)
                ));
    }
    
    /** Creates a new instance of CreateTermCommand */
    public CreateTermCommand(UserVisitPK userVisitPK, CreateTermForm form) {
        super(userVisitPK, form, null, null, false);
    }
    
    @Override
    protected ValidationResult validate() {
        Validator validator = new Validator(this);
        ValidationResult validationResult = validator.validate(form, FORM_FIELD_DEFINITIONS);
        
        if(!validationResult.getHasErrors()) {
            String termTypeName = form.getTermTypeName();
            
            if(termTypeName.equals(TermTypes.STANDARD.name())) {
                validationResult = validator.validate(form, standardFieldDefinitions);
            } else if(termTypeName.equals(TermTypes.DATE_DRIVEN.name())) {
                validationResult = validator.validate(form, dateDrivenFieldDefinitions);
            }
        }
        
        return validationResult;
    }
    
    @Override
    protected BaseResult execute() {
        var termControl = (TermControl)Session.getModelController(TermControl.class);
        String termName = form.getTermName();
        Term term = termControl.getTermByName(termName);
        
        if(term == null) {
            String termTypeName = form.getTermTypeName();
            TermType termType = termControl.getTermTypeByName(termTypeName);
            
            if(termType != null) {
                var partyPK = getPartyPK();
                var isDefault = Boolean.valueOf(form.getIsDefault());
                var sortOrder = Integer.valueOf(form.getSortOrder());
                var description = form.getDescription();
                
                termTypeName = termType.getTermTypeName();
                term = termControl.createTerm(termName, termType, isDefault, sortOrder, partyPK);
                
                if(termTypeName.equals(TermTypes.STANDARD.name())) {
                    Integer netDueDays = Integer.valueOf(form.getNetDueDays());
                    Integer discountPercentage = Integer.valueOf(form.getDiscountPercentage());
                    Integer discountDays = Integer.valueOf(form.getDiscountDays());
                    
                    termControl.createStandardTerm(term, netDueDays, discountPercentage, discountDays, partyPK);
                } else if(termTypeName.equals(TermTypes.DATE_DRIVEN.name())) {
                    Integer netDueDayOfMonth = Integer.valueOf(form.getNetDueDayOfMonth());
                    Integer dueNextMonthDays = Integer.valueOf(form.getDueNextMonthDays());
                    Integer discountPercentage = Integer.valueOf(form.getDiscountPercentage());
                    Integer discountBeforeDayOfMonth = Integer.valueOf(form.getDiscountBeforeDayOfMonth());
                    
                    termControl.createDateDrivenTerm(term, netDueDayOfMonth, dueNextMonthDays, discountPercentage,
                            discountBeforeDayOfMonth, partyPK);
                }
                
                if(description != null) {
                    termControl.createTermDescription(term, getPreferredLanguage(), description, partyPK);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownTermTypeName.name(), termTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateTermName.name(), termName);
        }
        
        return null;
    }
    
}
