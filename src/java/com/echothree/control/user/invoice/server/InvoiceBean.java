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

package com.echothree.control.user.invoice.server;

import com.echothree.control.user.invoice.common.InvoiceRemote;
import com.echothree.control.user.invoice.common.form.*;
import com.echothree.control.user.invoice.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class InvoiceBean
        extends InvoiceFormsImpl
        implements InvoiceRemote, InvoiceLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "InvoiceBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Use Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceLineUseType(UserVisitPK userVisitPK, CreateInvoiceLineUseTypeForm form) {
        return new CreateInvoiceLineUseTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceLineUseTypeDescription(UserVisitPK userVisitPK, CreateInvoiceLineUseTypeDescriptionForm form) {
        return new CreateInvoiceLineUseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceRoleType(UserVisitPK userVisitPK, CreateInvoiceRoleTypeForm form) {
        return new CreateInvoiceRoleTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceRoleTypeDescription(UserVisitPK userVisitPK, CreateInvoiceRoleTypeDescriptionForm form) {
        return new CreateInvoiceRoleTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceType(UserVisitPK userVisitPK, CreateInvoiceTypeForm form) {
        return new CreateInvoiceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInvoiceTypeChoices(UserVisitPK userVisitPK, GetInvoiceTypeChoicesForm form) {
        return new GetInvoiceTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInvoiceType(UserVisitPK userVisitPK, GetInvoiceTypeForm form) {
        return new GetInvoiceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInvoiceTypes(UserVisitPK userVisitPK, GetInvoiceTypesForm form) {
        return new GetInvoiceTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultInvoiceType(UserVisitPK userVisitPK, SetDefaultInvoiceTypeForm form) {
        return new SetDefaultInvoiceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInvoiceType(UserVisitPK userVisitPK, EditInvoiceTypeForm form) {
        return new EditInvoiceTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInvoiceType(UserVisitPK userVisitPK, DeleteInvoiceTypeForm form) {
        return new DeleteInvoiceTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceTypeDescription(UserVisitPK userVisitPK, CreateInvoiceTypeDescriptionForm form) {
        return new CreateInvoiceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInvoiceTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceTypeDescriptionsForm form) {
        return new GetInvoiceTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInvoiceTypeDescription(UserVisitPK userVisitPK, EditInvoiceTypeDescriptionForm form) {
        return new EditInvoiceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInvoiceTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceTypeDescriptionForm form) {
        return new DeleteInvoiceTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createInvoiceTimeType(UserVisitPK userVisitPK, CreateInvoiceTimeTypeForm form) {
        return new CreateInvoiceTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInvoiceTimeTypeChoices(UserVisitPK userVisitPK, GetInvoiceTimeTypeChoicesForm form) {
        return new GetInvoiceTimeTypeChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInvoiceTimeType(UserVisitPK userVisitPK, GetInvoiceTimeTypeForm form) {
        return new GetInvoiceTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInvoiceTimeTypes(UserVisitPK userVisitPK, GetInvoiceTimeTypesForm form) {
        return new GetInvoiceTimeTypesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultInvoiceTimeType(UserVisitPK userVisitPK, SetDefaultInvoiceTimeTypeForm form) {
        return new SetDefaultInvoiceTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInvoiceTimeType(UserVisitPK userVisitPK, EditInvoiceTimeTypeForm form) {
        return new EditInvoiceTimeTypeCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInvoiceTimeType(UserVisitPK userVisitPK, DeleteInvoiceTimeTypeForm form) {
        return new DeleteInvoiceTimeTypeCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Invoice Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createInvoiceTimeTypeDescription(UserVisitPK userVisitPK, CreateInvoiceTimeTypeDescriptionForm form) {
        return new CreateInvoiceTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInvoiceTimeTypeDescription(UserVisitPK userVisitPK, GetInvoiceTimeTypeDescriptionForm form) {
        return new GetInvoiceTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getInvoiceTimeTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceTimeTypeDescriptionsForm form) {
        return new GetInvoiceTimeTypeDescriptionsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editInvoiceTimeTypeDescription(UserVisitPK userVisitPK, EditInvoiceTimeTypeDescriptionForm form) {
        return new EditInvoiceTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteInvoiceTimeTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceTimeTypeDescriptionForm form) {
        return new DeleteInvoiceTimeTypeDescriptionCommand().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Invoice Line Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceLineType(UserVisitPK userVisitPK, CreateInvoiceLineTypeForm form) {
        return new CreateInvoiceLineTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInvoiceLineTypeChoices(UserVisitPK userVisitPK, GetInvoiceLineTypeChoicesForm form) {
        return new GetInvoiceLineTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInvoiceLineType(UserVisitPK userVisitPK, GetInvoiceLineTypeForm form) {
        return new GetInvoiceLineTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInvoiceLineTypes(UserVisitPK userVisitPK, GetInvoiceLineTypesForm form) {
        return new GetInvoiceLineTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultInvoiceLineType(UserVisitPK userVisitPK, SetDefaultInvoiceLineTypeForm form) {
        return new SetDefaultInvoiceLineTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInvoiceLineType(UserVisitPK userVisitPK, EditInvoiceLineTypeForm form) {
        return new EditInvoiceLineTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInvoiceLineType(UserVisitPK userVisitPK, DeleteInvoiceLineTypeForm form) {
        return new DeleteInvoiceLineTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceLineTypeDescription(UserVisitPK userVisitPK, CreateInvoiceLineTypeDescriptionForm form) {
        return new CreateInvoiceLineTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getInvoiceLineTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceLineTypeDescriptionsForm form) {
        return new GetInvoiceLineTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editInvoiceLineTypeDescription(UserVisitPK userVisitPK, EditInvoiceLineTypeDescriptionForm form) {
        return new EditInvoiceLineTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteInvoiceLineTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceLineTypeDescriptionForm form) {
        return new DeleteInvoiceLineTypeDescriptionCommand().run(userVisitPK, form);
    }
    
}
