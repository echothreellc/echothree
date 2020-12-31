// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.control.user.tax.server;

import com.echothree.control.user.tax.common.TaxRemote;
import com.echothree.control.user.tax.common.form.*;
import com.echothree.control.user.tax.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class TaxBean
        extends TaxFormsImpl
        implements TaxRemote, TaxLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "TaxBean is alive!";
    }
    
    // -------------------------------------------------------------------------
    //   Tax Classifications
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createTaxClassification(UserVisitPK userVisitPK, CreateTaxClassificationForm form) {
        return new CreateTaxClassificationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTaxClassifications(UserVisitPK userVisitPK, GetTaxClassificationsForm form) {
        return new GetTaxClassificationsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTaxClassification(UserVisitPK userVisitPK, GetTaxClassificationForm form) {
        return new GetTaxClassificationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTaxClassificationChoices(UserVisitPK userVisitPK, GetTaxClassificationChoicesForm form) {
        return new GetTaxClassificationChoicesCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult setDefaultTaxClassification(UserVisitPK userVisitPK, SetDefaultTaxClassificationForm form) {
        return new SetDefaultTaxClassificationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editTaxClassification(UserVisitPK userVisitPK, EditTaxClassificationForm form) {
        return new EditTaxClassificationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteTaxClassification(UserVisitPK userVisitPK, DeleteTaxClassificationForm form) {
        return new DeleteTaxClassificationCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Tax Classification Translations
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createTaxClassificationTranslation(UserVisitPK userVisitPK, CreateTaxClassificationTranslationForm form) {
        return new CreateTaxClassificationTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTaxClassificationTranslations(UserVisitPK userVisitPK, GetTaxClassificationTranslationsForm form) {
        return new GetTaxClassificationTranslationsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getTaxClassificationTranslation(UserVisitPK userVisitPK, GetTaxClassificationTranslationForm form) {
        return new GetTaxClassificationTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editTaxClassificationTranslation(UserVisitPK userVisitPK, EditTaxClassificationTranslationForm form) {
        return new EditTaxClassificationTranslationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteTaxClassificationTranslation(UserVisitPK userVisitPK, DeleteTaxClassificationTranslationForm form) {
        return new DeleteTaxClassificationTranslationCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Item Tax Classifications
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemTaxClassification(UserVisitPK userVisitPK, CreateItemTaxClassificationForm form) {
        return new CreateItemTaxClassificationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemTaxClassifications(UserVisitPK userVisitPK, GetItemTaxClassificationsForm form) {
        return new GetItemTaxClassificationsCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult getItemTaxClassification(UserVisitPK userVisitPK, GetItemTaxClassificationForm form) {
        return new GetItemTaxClassificationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult editItemTaxClassification(UserVisitPK userVisitPK, EditItemTaxClassificationForm form) {
        return new EditItemTaxClassificationCommand(userVisitPK, form).run();
    }

    @Override
    public CommandResult deleteItemTaxClassification(UserVisitPK userVisitPK, DeleteItemTaxClassificationForm form) {
        return new DeleteItemTaxClassificationCommand(userVisitPK, form).run();
    }

    // -------------------------------------------------------------------------
    //   Taxes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTax(UserVisitPK userVisitPK, CreateTaxForm form) {
        return new CreateTaxCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTaxes(UserVisitPK userVisitPK, GetTaxesForm form) {
        return new GetTaxesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTax(UserVisitPK userVisitPK, GetTaxForm form) {
        return new GetTaxCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultTax(UserVisitPK userVisitPK, SetDefaultTaxForm form) {
        return new SetDefaultTaxCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTax(UserVisitPK userVisitPK, EditTaxForm form) {
        return new EditTaxCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTax(UserVisitPK userVisitPK, DeleteTaxForm form) {
        return new DeleteTaxCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Tax Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTaxDescription(UserVisitPK userVisitPK, CreateTaxDescriptionForm form) {
        return new CreateTaxDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getTaxDescriptions(UserVisitPK userVisitPK, GetTaxDescriptionsForm form) {
        return new GetTaxDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editTaxDescription(UserVisitPK userVisitPK, EditTaxDescriptionForm form) {
        return new EditTaxDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteTaxDescription(UserVisitPK userVisitPK, DeleteTaxDescriptionForm form) {
        return new DeleteTaxDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Taxes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTax(UserVisitPK userVisitPK, CreateGeoCodeTaxForm form) {
        return new CreateGeoCodeTaxCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getGeoCodeTaxes(UserVisitPK userVisitPK, GetGeoCodeTaxesForm form) {
        return new GetGeoCodeTaxesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteGeoCodeTax(UserVisitPK userVisitPK, DeleteGeoCodeTaxForm form) {
        return new DeleteGeoCodeTaxCommand(userVisitPK, form).run();
    }
    
}
