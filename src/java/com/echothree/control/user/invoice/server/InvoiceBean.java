// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
        return new CreateInvoiceLineUseTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceLineUseTypeDescription(UserVisitPK userVisitPK, CreateInvoiceLineUseTypeDescriptionForm form) {
        return new CreateInvoiceLineUseTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceRoleType(UserVisitPK userVisitPK, CreateInvoiceRoleTypeForm form) {
        return new CreateInvoiceRoleTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceRoleTypeDescription(UserVisitPK userVisitPK, CreateInvoiceRoleTypeDescriptionForm form) {
        return new CreateInvoiceRoleTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceType(UserVisitPK userVisitPK, CreateInvoiceTypeForm form) {
        return new CreateInvoiceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInvoiceTypeChoices(UserVisitPK userVisitPK, GetInvoiceTypeChoicesForm form) {
        return new GetInvoiceTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInvoiceType(UserVisitPK userVisitPK, GetInvoiceTypeForm form) {
        return new GetInvoiceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInvoiceTypes(UserVisitPK userVisitPK, GetInvoiceTypesForm form) {
        return new GetInvoiceTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultInvoiceType(UserVisitPK userVisitPK, SetDefaultInvoiceTypeForm form) {
        return new SetDefaultInvoiceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInvoiceType(UserVisitPK userVisitPK, EditInvoiceTypeForm form) {
        return new EditInvoiceTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInvoiceType(UserVisitPK userVisitPK, DeleteInvoiceTypeForm form) {
        return new DeleteInvoiceTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceTypeDescription(UserVisitPK userVisitPK, CreateInvoiceTypeDescriptionForm form) {
        return new CreateInvoiceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInvoiceTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceTypeDescriptionsForm form) {
        return new GetInvoiceTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInvoiceTypeDescription(UserVisitPK userVisitPK, EditInvoiceTypeDescriptionForm form) {
        return new EditInvoiceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInvoiceTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceTypeDescriptionForm form) {
        return new DeleteInvoiceTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createInvoiceTimeType(UserVisitPK userVisitPK, CreateInvoiceTimeTypeForm form) {
        return new CreateInvoiceTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getInvoiceTimeTypeChoices(UserVisitPK userVisitPK, GetInvoiceTimeTypeChoicesForm form) {
        return new GetInvoiceTimeTypeChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getInvoiceTimeType(UserVisitPK userVisitPK, GetInvoiceTimeTypeForm form) {
        return new GetInvoiceTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getInvoiceTimeTypes(UserVisitPK userVisitPK, GetInvoiceTimeTypesForm form) {
        return new GetInvoiceTimeTypesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultInvoiceTimeType(UserVisitPK userVisitPK, SetDefaultInvoiceTimeTypeForm form) {
        return new SetDefaultInvoiceTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editInvoiceTimeType(UserVisitPK userVisitPK, EditInvoiceTimeTypeForm form) {
        return new EditInvoiceTimeTypeCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteInvoiceTimeType(UserVisitPK userVisitPK, DeleteInvoiceTimeTypeForm form) {
        return new DeleteInvoiceTimeTypeCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Invoice Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult createInvoiceTimeTypeDescription(UserVisitPK userVisitPK, CreateInvoiceTimeTypeDescriptionForm form) {
        return new CreateInvoiceTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getInvoiceTimeTypeDescription(UserVisitPK userVisitPK, GetInvoiceTimeTypeDescriptionForm form) {
        return new GetInvoiceTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getInvoiceTimeTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceTimeTypeDescriptionsForm form) {
        return new GetInvoiceTimeTypeDescriptionsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editInvoiceTimeTypeDescription(UserVisitPK userVisitPK, EditInvoiceTimeTypeDescriptionForm form) {
        return new EditInvoiceTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteInvoiceTimeTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceTimeTypeDescriptionForm form) {
        return new DeleteInvoiceTimeTypeDescriptionCommand(userVisitPK, form).run();
    }

    // --------------------------------------------------------------------------------
    //   Invoice Line Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceLineType(UserVisitPK userVisitPK, CreateInvoiceLineTypeForm form) {
        return new CreateInvoiceLineTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInvoiceLineTypeChoices(UserVisitPK userVisitPK, GetInvoiceLineTypeChoicesForm form) {
        return new GetInvoiceLineTypeChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInvoiceLineType(UserVisitPK userVisitPK, GetInvoiceLineTypeForm form) {
        return new GetInvoiceLineTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInvoiceLineTypes(UserVisitPK userVisitPK, GetInvoiceLineTypesForm form) {
        return new GetInvoiceLineTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultInvoiceLineType(UserVisitPK userVisitPK, SetDefaultInvoiceLineTypeForm form) {
        return new SetDefaultInvoiceLineTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInvoiceLineType(UserVisitPK userVisitPK, EditInvoiceLineTypeForm form) {
        return new EditInvoiceLineTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInvoiceLineType(UserVisitPK userVisitPK, DeleteInvoiceLineTypeForm form) {
        return new DeleteInvoiceLineTypeCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createInvoiceLineTypeDescription(UserVisitPK userVisitPK, CreateInvoiceLineTypeDescriptionForm form) {
        return new CreateInvoiceLineTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getInvoiceLineTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceLineTypeDescriptionsForm form) {
        return new GetInvoiceLineTypeDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editInvoiceLineTypeDescription(UserVisitPK userVisitPK, EditInvoiceLineTypeDescriptionForm form) {
        return new EditInvoiceLineTypeDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteInvoiceLineTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceLineTypeDescriptionForm form) {
        return new DeleteInvoiceLineTypeDescriptionCommand(userVisitPK, form).run();
    }
    
}
