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

package com.echothree.util.server.control;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.form.ValidationResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.Validator;
import java.util.List;
import java.util.concurrent.Future;

public abstract class BaseSimpleCommand<F extends BaseForm>
        extends BaseCommand {

    protected F form;
    private List<FieldDefinition> formFieldDefinitions;
    boolean allowLimits;

    private void init(F form, List<FieldDefinition> formFieldDefinitions, boolean allowLimits) {
        this.form = form;
        this.formFieldDefinitions = formFieldDefinitions;
        this.allowLimits = allowLimits;
    }

    protected BaseSimpleCommand(CommandSecurityDefinition commandSecurityDefinition,
            List<FieldDefinition> formFieldDefinitions, boolean allowLimits) {
        super(commandSecurityDefinition);
        
        init(form, formFieldDefinitions, allowLimits);
    }
    
    protected BaseSimpleCommand(CommandSecurityDefinition commandSecurityDefinition, boolean allowLimits) {
        super(commandSecurityDefinition);
        
        init(null, null, allowLimits);
    }

    @Override
    protected void setupSession() {
        super.setupSession();

        if(form != null) {
            session.setOptions(form.getOptions());
            session.setTransferProperties(form.getTransferProperties());

            if(allowLimits) {
                session.setLimits(form.getLimits());
            }
        }
    }

    protected F getForm() {
        return form;
    }

    protected List<FieldDefinition> getFormFieldDefinitions() {
        return formFieldDefinitions;
    }

    protected void setupValidator(Validator validator) {
        // Nothing.
    }

    protected ValidationResult validate(Validator validator) {
        return validator.validate(form, getFormFieldDefinitions());
    }

    @Override
    protected ValidationResult validate() {
        ValidationResult validationResult = null;

        if(getFormFieldDefinitions() != null) {
            var validator = new Validator(this);

            setupValidator(validator);
            validationResult = validate(validator);
        }

        return validationResult;
    }

    protected void setupPreferredClobMimeType() {
        var preferredClobMimeTypeName = form.getPreferredClobMimeTypeName();
        
        if(preferredClobMimeTypeName != null) {
            var mimeTypeControl = Session.getModelController(MimeTypeControl.class);
            var preferredClobMimeType = mimeTypeControl.getMimeTypeByName(preferredClobMimeTypeName);

            if(preferredClobMimeType == null) {
                addExecutionError(ExecutionErrors.UnknownPreferredClobMimeTypeName.name(), preferredClobMimeTypeName);
            } else {
                var preferredClobMimeTypeDetail = preferredClobMimeType.getLastDetail();
                var entityAttributeTypeName = preferredClobMimeTypeDetail.getEntityAttributeType().getEntityAttributeTypeName();
                
                // Must be a CLOB and for now the preferred MIME type must be HTML.
                if(!entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())
                        || !preferredClobMimeTypeDetail.getMimeTypeName().equals(MimeTypes.TEXT_HTML.mimeTypeName())) {
                    addExecutionError(ExecutionErrors.InvalidPreferredClobMimeType.name(), preferredClobMimeTypeName, entityAttributeTypeName);
                } else {
                    session.setPreferredClobMimeType(preferredClobMimeType);
                }
            }
        }
    }
    
    private void initForRun(F form) {
        this.form = form;
    }

    public Future<CommandResult> runAsync(UserVisitPK userVisitPK, F form) {
        initForRun(form);

        return super.runAsync(userVisitPK);
    }

    public CommandResult run(UserVisitPK userVisitPK, F form) {
        initForRun(form);

        return super.run(userVisitPK);
    }

    // Perform security and validation for GraphQL queries.
    public boolean canQueryByGraphQl(UserVisitPK userVisitPK, F form) {
        initForRun(form);
        setUserVisitPK(userVisitPK);

        initSession();

        var securityResult = security();
        var canQuery = false;

        if(securityResult == null || !securityResult.getHasMessages()) {
            var validationResult = validate();

            if((validationResult == null || !validationResult.getHasErrors())) {
                canQuery = true;
            }
        }

        return canQuery;
    }
}
