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

package com.echothree.model.control.vendor.common;

public interface VendorOptions {
    
    String VendorIncludeUuid = "VendorIncludeUuid";
    String VendorIncludeVendorItems = "VendorIncludeVendorItems";
    String VendorIncludeBillingAccounts = "VendorIncludeBillingAccounts";
    String VendorIncludeInvoicesFrom = "VendorIncludeInvoicesFrom";
    String VendorIncludeInvoicesTo = "VendorIncludeInvoicesTo";
    String VendorIncludePurchasingComments = "VendorIncludePurchasingComments";
    String VendorIncludePartyCreditLimits = "VendorIncludePartyCreditLimits";
    String VendorIncludePartyTerm = "VendorIncludePartyTerm";
    String VendorIncludePartyFreeOnBoard = "VendorIncludePartyFreeOnBoard";
    String VendorIncludeSubscriptions = "VendorIncludeSubscriptions";
    String VendorIncludeCommunicationEvents = "VendorIncludeCommunicationEvents";
    String VendorIncludeEntityAttributeGroups = "VendorIncludeEntityAttributeGroups";
    String VendorIncludeTagScopes = "VendorIncludeTagScopes";
    
    String VendorItemIncludeVendorItemCosts = "VendorItemIncludeVendorItemCosts";
    String VendorItemIncludePurchasingComments = "VendorItemIncludePurchasingComments";
    String VendorItemIncludeEntityAttributeGroups = "VendorItemIncludeEntityAttributeGroups";
    String VendorItemIncludeTagScopes = "VendorItemIncludeTagScopes";

}
