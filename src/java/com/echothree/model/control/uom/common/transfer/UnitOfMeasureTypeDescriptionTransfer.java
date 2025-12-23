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

package com.echothree.model.control.uom.common.transfer;

import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class UnitOfMeasureTypeDescriptionTransfer
        extends BaseTransfer {

    private LanguageTransfer language;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private String singularDescription;
    private String pluralDescription;
    private String symbol;
    
    /** Creates a new instance of UnitOfMeasureTypeDescriptionTransfer */
    public UnitOfMeasureTypeDescriptionTransfer(LanguageTransfer language, UnitOfMeasureTypeTransfer unitOfMeasureType,
            String singularDescription, String pluralDescription, String symbol) {
        this.language = language;
        this.unitOfMeasureType = unitOfMeasureType;
        this.singularDescription = singularDescription;
        this.pluralDescription = pluralDescription;
        this.symbol = symbol;
    }
    
    /**
     * Returns the language.
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * Sets the language.
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    /**
     * Returns the unitOfMeasureType.
     * @return the unitOfMeasureType
     */
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    /**
     * Sets the unitOfMeasureType.
     * @param unitOfMeasureType the unitOfMeasureType to set
     */
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    /**
     * Returns the singularDescription.
     * @return the singularDescription
     */
    public String getSingularDescription() {
        return singularDescription;
    }

    /**
     * Sets the singularDescription.
     * @param singularDescription the singularDescription to set
     */
    public void setSingularDescription(String singularDescription) {
        this.singularDescription = singularDescription;
    }

    /**
     * Returns the pluralDescription.
     * @return the pluralDescription
     */
    public String getPluralDescription() {
        return pluralDescription;
    }

    /**
     * Sets the pluralDescription.
     * @param pluralDescription the pluralDescription to set
     */
    public void setPluralDescription(String pluralDescription) {
        this.pluralDescription = pluralDescription;
    }

    /**
     * Returns the symbol.
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol.
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
}
