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

package com.echothree.model.control.party.common.transfer;

import com.echothree.model.control.accounting.common.transfer.CurrencyTransfer;

public class DepartmentTransfer
        extends PartyTransfer {

    private DivisionTransfer division;
    private String departmentName;
    private String isDefault;
    private String sortOrder;

    /** Creates a new instance of DepartmentTransfer */
    public DepartmentTransfer(String partyName, PartyTypeTransfer partyType, LanguageTransfer preferredLanguage, CurrencyTransfer preferredCurrency, TimeZoneTransfer preferredTimeZone,
            DateTimeFormatTransfer preferredDateTimeFormat, PersonTransfer person, PartyGroupTransfer partyGroup, DivisionTransfer division, String departmentName, String isDefault,
            String sortOrder) {
        super(partyName, partyType, preferredLanguage, preferredCurrency, preferredTimeZone, preferredDateTimeFormat, person, partyGroup, null);

        this.division = division;
        this.departmentName = departmentName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
    }

    public DivisionTransfer getDivision() {
        return division;
    }

    public void setDivision(DivisionTransfer division) {
        this.division = division;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

}
