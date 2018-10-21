// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.uom.remote.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

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
     * @return the language
     */
    public LanguageTransfer getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }

    /**
     * @return the unitOfMeasureType
     */
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }

    /**
     * @param unitOfMeasureType the unitOfMeasureType to set
     */
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }

    /**
     * @return the singularDescription
     */
    public String getSingularDescription() {
        return singularDescription;
    }

    /**
     * @param singularDescription the singularDescription to set
     */
    public void setSingularDescription(String singularDescription) {
        this.singularDescription = singularDescription;
    }

    /**
     * @return the pluralDescription
     */
    public String getPluralDescription() {
        return pluralDescription;
    }

    /**
     * @param pluralDescription the pluralDescription to set
     */
    public void setPluralDescription(String pluralDescription) {
        this.pluralDescription = pluralDescription;
    }

    /**
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
}
