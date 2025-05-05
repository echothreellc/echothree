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
        return new CreateTaxClassificationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassifications(UserVisitPK userVisitPK, GetTaxClassificationsForm form) {
        return new GetTaxClassificationsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassification(UserVisitPK userVisitPK, GetTaxClassificationForm form) {
        return new GetTaxClassificationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassificationChoices(UserVisitPK userVisitPK, GetTaxClassificationChoicesForm form) {
        return new GetTaxClassificationChoicesCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultTaxClassification(UserVisitPK userVisitPK, SetDefaultTaxClassificationForm form) {
        return new SetDefaultTaxClassificationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editTaxClassification(UserVisitPK userVisitPK, EditTaxClassificationForm form) {
        return new EditTaxClassificationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteTaxClassification(UserVisitPK userVisitPK, DeleteTaxClassificationForm form) {
        return new DeleteTaxClassificationCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Tax Classification Translations
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createTaxClassificationTranslation(UserVisitPK userVisitPK, CreateTaxClassificationTranslationForm form) {
        return new CreateTaxClassificationTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassificationTranslations(UserVisitPK userVisitPK, GetTaxClassificationTranslationsForm form) {
        return new GetTaxClassificationTranslationsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassificationTranslation(UserVisitPK userVisitPK, GetTaxClassificationTranslationForm form) {
        return new GetTaxClassificationTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editTaxClassificationTranslation(UserVisitPK userVisitPK, EditTaxClassificationTranslationForm form) {
        return new EditTaxClassificationTranslationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteTaxClassificationTranslation(UserVisitPK userVisitPK, DeleteTaxClassificationTranslationForm form) {
        return new DeleteTaxClassificationTranslationCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Tax Classifications
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemTaxClassification(UserVisitPK userVisitPK, CreateItemTaxClassificationForm form) {
        return new CreateItemTaxClassificationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemTaxClassifications(UserVisitPK userVisitPK, GetItemTaxClassificationsForm form) {
        return new GetItemTaxClassificationsCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemTaxClassification(UserVisitPK userVisitPK, GetItemTaxClassificationForm form) {
        return new GetItemTaxClassificationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemTaxClassification(UserVisitPK userVisitPK, EditItemTaxClassificationForm form) {
        return new EditItemTaxClassificationCommand().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemTaxClassification(UserVisitPK userVisitPK, DeleteItemTaxClassificationForm form) {
        return new DeleteItemTaxClassificationCommand().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Taxes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTax(UserVisitPK userVisitPK, CreateTaxForm form) {
        return new CreateTaxCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTaxes(UserVisitPK userVisitPK, GetTaxesForm form) {
        return new GetTaxesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTax(UserVisitPK userVisitPK, GetTaxForm form) {
        return new GetTaxCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTax(UserVisitPK userVisitPK, SetDefaultTaxForm form) {
        return new SetDefaultTaxCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTax(UserVisitPK userVisitPK, EditTaxForm form) {
        return new EditTaxCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTax(UserVisitPK userVisitPK, DeleteTaxForm form) {
        return new DeleteTaxCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Tax Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTaxDescription(UserVisitPK userVisitPK, CreateTaxDescriptionForm form) {
        return new CreateTaxDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTaxDescriptions(UserVisitPK userVisitPK, GetTaxDescriptionsForm form) {
        return new GetTaxDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTaxDescription(UserVisitPK userVisitPK, EditTaxDescriptionForm form) {
        return new EditTaxDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTaxDescription(UserVisitPK userVisitPK, DeleteTaxDescriptionForm form) {
        return new DeleteTaxDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Taxes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTax(UserVisitPK userVisitPK, CreateGeoCodeTaxForm form) {
        return new CreateGeoCodeTaxCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeTaxes(UserVisitPK userVisitPK, GetGeoCodeTaxesForm form) {
        return new GetGeoCodeTaxesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeTax(UserVisitPK userVisitPK, DeleteGeoCodeTaxForm form) {
        return new DeleteGeoCodeTaxCommand().run(userVisitPK, form);
    }
    
}
