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

package com.echothree.control.user.license.common;

import com.echothree.control.user.license.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface LicenseService
        extends LicenseForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   License Types
    // --------------------------------------------------------------------------------
    
    CommandResult createLicenseType(UserVisitPK userVisitPK, CreateLicenseTypeForm form);
    
    CommandResult getLicenseTypeChoices(UserVisitPK userVisitPK, GetLicenseTypeChoicesForm form);
    
    CommandResult getLicenseType(UserVisitPK userVisitPK, GetLicenseTypeForm form);
    
    CommandResult getLicenseTypes(UserVisitPK userVisitPK, GetLicenseTypesForm form);
    
    CommandResult setDefaultLicenseType(UserVisitPK userVisitPK, SetDefaultLicenseTypeForm form);
    
    CommandResult editLicenseType(UserVisitPK userVisitPK, EditLicenseTypeForm form);
    
    CommandResult deleteLicenseType(UserVisitPK userVisitPK, DeleteLicenseTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   License Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createLicenseTypeDescription(UserVisitPK userVisitPK, CreateLicenseTypeDescriptionForm form);
    
    CommandResult getLicenseTypeDescription(UserVisitPK userVisitPK, GetLicenseTypeDescriptionForm form);
    
    CommandResult getLicenseTypeDescriptions(UserVisitPK userVisitPK, GetLicenseTypeDescriptionsForm form);
    
    CommandResult editLicenseTypeDescription(UserVisitPK userVisitPK, EditLicenseTypeDescriptionForm form);
    
    CommandResult deleteLicenseTypeDescription(UserVisitPK userVisitPK, DeleteLicenseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   License Utilities
    // --------------------------------------------------------------------------------
    
    CommandResult updateLicense(UserVisitPK userVisitPK);
    
}
