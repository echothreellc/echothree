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

package com.echothree.control.user.tax.server;

import com.echothree.control.user.tax.common.TaxRemote;
import com.echothree.control.user.tax.common.form.*;
import com.echothree.control.user.tax.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassifications(UserVisitPK userVisitPK, GetTaxClassificationsForm form) {
        return CDI.current().select(GetTaxClassificationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassification(UserVisitPK userVisitPK, GetTaxClassificationForm form) {
        return CDI.current().select(GetTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassificationChoices(UserVisitPK userVisitPK, GetTaxClassificationChoicesForm form) {
        return CDI.current().select(GetTaxClassificationChoicesCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult setDefaultTaxClassification(UserVisitPK userVisitPK, SetDefaultTaxClassificationForm form) {
        return CDI.current().select(SetDefaultTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editTaxClassification(UserVisitPK userVisitPK, EditTaxClassificationForm form) {
        return CDI.current().select(EditTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteTaxClassification(UserVisitPK userVisitPK, DeleteTaxClassificationForm form) {
        return CDI.current().select(DeleteTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Tax Classification Translations
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createTaxClassificationTranslation(UserVisitPK userVisitPK, CreateTaxClassificationTranslationForm form) {
        return CDI.current().select(CreateTaxClassificationTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassificationTranslations(UserVisitPK userVisitPK, GetTaxClassificationTranslationsForm form) {
        return CDI.current().select(GetTaxClassificationTranslationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getTaxClassificationTranslation(UserVisitPK userVisitPK, GetTaxClassificationTranslationForm form) {
        return CDI.current().select(GetTaxClassificationTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editTaxClassificationTranslation(UserVisitPK userVisitPK, EditTaxClassificationTranslationForm form) {
        return CDI.current().select(EditTaxClassificationTranslationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteTaxClassificationTranslation(UserVisitPK userVisitPK, DeleteTaxClassificationTranslationForm form) {
        return CDI.current().select(DeleteTaxClassificationTranslationCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Item Tax Classifications
    // -------------------------------------------------------------------------

    @Override
    public CommandResult createItemTaxClassification(UserVisitPK userVisitPK, CreateItemTaxClassificationForm form) {
        return CDI.current().select(CreateItemTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemTaxClassifications(UserVisitPK userVisitPK, GetItemTaxClassificationsForm form) {
        return CDI.current().select(GetItemTaxClassificationsCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult getItemTaxClassification(UserVisitPK userVisitPK, GetItemTaxClassificationForm form) {
        return CDI.current().select(GetItemTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult editItemTaxClassification(UserVisitPK userVisitPK, EditItemTaxClassificationForm form) {
        return CDI.current().select(EditItemTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    @Override
    public CommandResult deleteItemTaxClassification(UserVisitPK userVisitPK, DeleteItemTaxClassificationForm form) {
        return CDI.current().select(DeleteItemTaxClassificationCommand.class).get().run(userVisitPK, form);
    }

    // -------------------------------------------------------------------------
    //   Taxes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTax(UserVisitPK userVisitPK, CreateTaxForm form) {
        return CDI.current().select(CreateTaxCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTaxes(UserVisitPK userVisitPK, GetTaxesForm form) {
        return CDI.current().select(GetTaxesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTax(UserVisitPK userVisitPK, GetTaxForm form) {
        return CDI.current().select(GetTaxCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultTax(UserVisitPK userVisitPK, SetDefaultTaxForm form) {
        return CDI.current().select(SetDefaultTaxCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTax(UserVisitPK userVisitPK, EditTaxForm form) {
        return CDI.current().select(EditTaxCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTax(UserVisitPK userVisitPK, DeleteTaxForm form) {
        return CDI.current().select(DeleteTaxCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Tax Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createTaxDescription(UserVisitPK userVisitPK, CreateTaxDescriptionForm form) {
        return CDI.current().select(CreateTaxDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getTaxDescriptions(UserVisitPK userVisitPK, GetTaxDescriptionsForm form) {
        return CDI.current().select(GetTaxDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editTaxDescription(UserVisitPK userVisitPK, EditTaxDescriptionForm form) {
        return CDI.current().select(EditTaxDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteTaxDescription(UserVisitPK userVisitPK, DeleteTaxDescriptionForm form) {
        return CDI.current().select(DeleteTaxDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Geo Code Taxes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createGeoCodeTax(UserVisitPK userVisitPK, CreateGeoCodeTaxForm form) {
        return CDI.current().select(CreateGeoCodeTaxCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getGeoCodeTaxes(UserVisitPK userVisitPK, GetGeoCodeTaxesForm form) {
        return CDI.current().select(GetGeoCodeTaxesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteGeoCodeTax(UserVisitPK userVisitPK, DeleteGeoCodeTaxForm form) {
        return CDI.current().select(DeleteGeoCodeTaxCommand.class).get().run(userVisitPK, form);
    }
    
}
