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

package com.echothree.control.user.invoice.server;

import com.echothree.control.user.invoice.common.InvoiceRemote;
import com.echothree.control.user.invoice.common.form.*;
import com.echothree.control.user.invoice.common.result.*;
import com.echothree.control.user.invoice.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
    public CommandResult<VoidResult> createInvoiceLineUseType(UserVisitPK userVisitPK, CreateInvoiceLineUseTypeForm form) {
        return CDI.current().select(CreateInvoiceLineUseTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInvoiceLineUseTypeDescription(UserVisitPK userVisitPK, CreateInvoiceLineUseTypeDescriptionForm form) {
        return CDI.current().select(CreateInvoiceLineUseTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInvoiceRoleType(UserVisitPK userVisitPK, CreateInvoiceRoleTypeForm form) {
        return CDI.current().select(CreateInvoiceRoleTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Role Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInvoiceRoleTypeDescription(UserVisitPK userVisitPK, CreateInvoiceRoleTypeDescriptionForm form) {
        return CDI.current().select(CreateInvoiceRoleTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInvoiceType(UserVisitPK userVisitPK, CreateInvoiceTypeForm form) {
        return CDI.current().select(CreateInvoiceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInvoiceTypeChoicesResult> getInvoiceTypeChoices(UserVisitPK userVisitPK, GetInvoiceTypeChoicesForm form) {
        return CDI.current().select(GetInvoiceTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInvoiceTypeResult> getInvoiceType(UserVisitPK userVisitPK, GetInvoiceTypeForm form) {
        return CDI.current().select(GetInvoiceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInvoiceTypesResult> getInvoiceTypes(UserVisitPK userVisitPK, GetInvoiceTypesForm form) {
        return CDI.current().select(GetInvoiceTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultInvoiceType(UserVisitPK userVisitPK, SetDefaultInvoiceTypeForm form) {
        return CDI.current().select(SetDefaultInvoiceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditInvoiceTypeResult> editInvoiceType(UserVisitPK userVisitPK, EditInvoiceTypeForm form) {
        return CDI.current().select(EditInvoiceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInvoiceType(UserVisitPK userVisitPK, DeleteInvoiceTypeForm form) {
        return CDI.current().select(DeleteInvoiceTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInvoiceTypeDescription(UserVisitPK userVisitPK, CreateInvoiceTypeDescriptionForm form) {
        return CDI.current().select(CreateInvoiceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInvoiceTypeDescriptionsResult> getInvoiceTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceTypeDescriptionsForm form) {
        return CDI.current().select(GetInvoiceTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditInvoiceTypeDescriptionResult> editInvoiceTypeDescription(UserVisitPK userVisitPK, EditInvoiceTypeDescriptionForm form) {
        return CDI.current().select(EditInvoiceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInvoiceTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceTypeDescriptionForm form) {
        return CDI.current().select(DeleteInvoiceTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Time Types
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInvoiceTimeType(UserVisitPK userVisitPK, CreateInvoiceTimeTypeForm form) {
        return CDI.current().select(CreateInvoiceTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInvoiceTimeTypeChoicesResult> getInvoiceTimeTypeChoices(UserVisitPK userVisitPK, GetInvoiceTimeTypeChoicesForm form) {
        return CDI.current().select(GetInvoiceTimeTypeChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInvoiceTimeTypeResult> getInvoiceTimeType(UserVisitPK userVisitPK, GetInvoiceTimeTypeForm form) {
        return CDI.current().select(GetInvoiceTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInvoiceTimeTypesResult> getInvoiceTimeTypes(UserVisitPK userVisitPK, GetInvoiceTimeTypesForm form) {
        return CDI.current().select(GetInvoiceTimeTypesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> setDefaultInvoiceTimeType(UserVisitPK userVisitPK, SetDefaultInvoiceTimeTypeForm form) {
        return CDI.current().select(SetDefaultInvoiceTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInvoiceTimeTypeResult> editInvoiceTimeType(UserVisitPK userVisitPK, EditInvoiceTimeTypeForm form) {
        return CDI.current().select(EditInvoiceTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInvoiceTimeType(UserVisitPK userVisitPK, DeleteInvoiceTimeTypeForm form) {
        return CDI.current().select(DeleteInvoiceTimeTypeCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Invoice Time Type Descriptions
    // --------------------------------------------------------------------------------

    @Override
    public CommandResult<VoidResult> createInvoiceTimeTypeDescription(UserVisitPK userVisitPK, CreateInvoiceTimeTypeDescriptionForm form) {
        return CDI.current().select(CreateInvoiceTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInvoiceTimeTypeDescriptionResult> getInvoiceTimeTypeDescription(UserVisitPK userVisitPK, GetInvoiceTimeTypeDescriptionForm form) {
        return CDI.current().select(GetInvoiceTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<GetInvoiceTimeTypeDescriptionsResult> getInvoiceTimeTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceTimeTypeDescriptionsForm form) {
        return CDI.current().select(GetInvoiceTimeTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<EditInvoiceTimeTypeDescriptionResult> editInvoiceTimeTypeDescription(UserVisitPK userVisitPK, EditInvoiceTimeTypeDescriptionForm form) {
        return CDI.current().select(EditInvoiceTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult<VoidResult> deleteInvoiceTimeTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceTimeTypeDescriptionForm form) {
        return CDI.current().select(DeleteInvoiceTimeTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }

    // --------------------------------------------------------------------------------
    //   Invoice Line Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInvoiceLineType(UserVisitPK userVisitPK, CreateInvoiceLineTypeForm form) {
        return CDI.current().select(CreateInvoiceLineTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInvoiceLineTypeChoicesResult> getInvoiceLineTypeChoices(UserVisitPK userVisitPK, GetInvoiceLineTypeChoicesForm form) {
        return CDI.current().select(GetInvoiceLineTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInvoiceLineTypeResult> getInvoiceLineType(UserVisitPK userVisitPK, GetInvoiceLineTypeForm form) {
        return CDI.current().select(GetInvoiceLineTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInvoiceLineTypesResult> getInvoiceLineTypes(UserVisitPK userVisitPK, GetInvoiceLineTypesForm form) {
        return CDI.current().select(GetInvoiceLineTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> setDefaultInvoiceLineType(UserVisitPK userVisitPK, SetDefaultInvoiceLineTypeForm form) {
        return CDI.current().select(SetDefaultInvoiceLineTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditInvoiceLineTypeResult> editInvoiceLineType(UserVisitPK userVisitPK, EditInvoiceLineTypeForm form) {
        return CDI.current().select(EditInvoiceLineTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInvoiceLineType(UserVisitPK userVisitPK, DeleteInvoiceLineTypeForm form) {
        return CDI.current().select(DeleteInvoiceLineTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Invoice Line Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult<VoidResult> createInvoiceLineTypeDescription(UserVisitPK userVisitPK, CreateInvoiceLineTypeDescriptionForm form) {
        return CDI.current().select(CreateInvoiceLineTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<GetInvoiceLineTypeDescriptionsResult> getInvoiceLineTypeDescriptions(UserVisitPK userVisitPK, GetInvoiceLineTypeDescriptionsForm form) {
        return CDI.current().select(GetInvoiceLineTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<EditInvoiceLineTypeDescriptionResult> editInvoiceLineTypeDescription(UserVisitPK userVisitPK, EditInvoiceLineTypeDescriptionForm form) {
        return CDI.current().select(EditInvoiceLineTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult<VoidResult> deleteInvoiceLineTypeDescription(UserVisitPK userVisitPK, DeleteInvoiceLineTypeDescriptionForm form) {
        return CDI.current().select(DeleteInvoiceLineTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
}
