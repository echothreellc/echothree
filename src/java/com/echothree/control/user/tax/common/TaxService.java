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

package com.echothree.control.user.tax.common;

import com.echothree.control.user.tax.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface TaxService
        extends TaxForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // -------------------------------------------------------------------------
    //   Tax Classifications
    // -------------------------------------------------------------------------

    CommandResult createTaxClassification(UserVisitPK userVisitPK, CreateTaxClassificationForm form);

    CommandResult getTaxClassifications(UserVisitPK userVisitPK, GetTaxClassificationsForm form);

    CommandResult getTaxClassification(UserVisitPK userVisitPK, GetTaxClassificationForm form);

    CommandResult getTaxClassificationChoices(UserVisitPK userVisitPK, GetTaxClassificationChoicesForm form);

    CommandResult setDefaultTaxClassification(UserVisitPK userVisitPK, SetDefaultTaxClassificationForm form);

    CommandResult editTaxClassification(UserVisitPK userVisitPK, EditTaxClassificationForm form);

    CommandResult deleteTaxClassification(UserVisitPK userVisitPK, DeleteTaxClassificationForm form);

    // -------------------------------------------------------------------------
    //   Tax Classification Translations
    // -------------------------------------------------------------------------

    CommandResult createTaxClassificationTranslation(UserVisitPK userVisitPK, CreateTaxClassificationTranslationForm form);

    CommandResult getTaxClassificationTranslations(UserVisitPK userVisitPK, GetTaxClassificationTranslationsForm form);

    CommandResult getTaxClassificationTranslation(UserVisitPK userVisitPK, GetTaxClassificationTranslationForm form);

    CommandResult editTaxClassificationTranslation(UserVisitPK userVisitPK, EditTaxClassificationTranslationForm form);

    CommandResult deleteTaxClassificationTranslation(UserVisitPK userVisitPK, DeleteTaxClassificationTranslationForm form);
    
    // -------------------------------------------------------------------------
    //   Item Tax Classifications
    // -------------------------------------------------------------------------

    CommandResult createItemTaxClassification(UserVisitPK userVisitPK, CreateItemTaxClassificationForm form);

    CommandResult getItemTaxClassifications(UserVisitPK userVisitPK, GetItemTaxClassificationsForm form);

    CommandResult getItemTaxClassification(UserVisitPK userVisitPK, GetItemTaxClassificationForm form);

    CommandResult editItemTaxClassification(UserVisitPK userVisitPK, EditItemTaxClassificationForm form);

    CommandResult deleteItemTaxClassification(UserVisitPK userVisitPK, DeleteItemTaxClassificationForm form);

    // -------------------------------------------------------------------------
    //   Taxes
    // -------------------------------------------------------------------------
    
    CommandResult createTax(UserVisitPK userVisitPK, CreateTaxForm form);
    
    CommandResult getTaxes(UserVisitPK userVisitPK, GetTaxesForm form);
    
    CommandResult getTax(UserVisitPK userVisitPK, GetTaxForm form);
    
    CommandResult setDefaultTax(UserVisitPK userVisitPK, SetDefaultTaxForm form);
    
    CommandResult editTax(UserVisitPK userVisitPK, EditTaxForm form);
    
    CommandResult deleteTax(UserVisitPK userVisitPK, DeleteTaxForm form);
    
    // --------------------------------------------------------------------------------
    //   Tax Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createTaxDescription(UserVisitPK userVisitPK, CreateTaxDescriptionForm form);
    
    CommandResult getTaxDescriptions(UserVisitPK userVisitPK, GetTaxDescriptionsForm form);
    
    CommandResult editTaxDescription(UserVisitPK userVisitPK, EditTaxDescriptionForm form);
    
    CommandResult deleteTaxDescription(UserVisitPK userVisitPK, DeleteTaxDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Geo Code Taxes
    // -------------------------------------------------------------------------
    
    CommandResult createGeoCodeTax(UserVisitPK userVisitPK, CreateGeoCodeTaxForm form);
    
    CommandResult getGeoCodeTaxes(UserVisitPK userVisitPK, GetGeoCodeTaxesForm form);
    
    CommandResult deleteGeoCodeTax(UserVisitPK userVisitPK, DeleteGeoCodeTaxForm form);
    
}
