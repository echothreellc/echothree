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
import com.echothree.control.user.license.common.result.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.VoidResult;

public interface LicenseService
        extends LicenseForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   License Types
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createLicenseType(UserVisitPK userVisitPK, CreateLicenseTypeForm form);
    
    CommandResult<GetLicenseTypeChoicesResult> getLicenseTypeChoices(UserVisitPK userVisitPK, GetLicenseTypeChoicesForm form);
    
    CommandResult<GetLicenseTypeResult> getLicenseType(UserVisitPK userVisitPK, GetLicenseTypeForm form);
    
    CommandResult<GetLicenseTypesResult> getLicenseTypes(UserVisitPK userVisitPK, GetLicenseTypesForm form);
    
    CommandResult<VoidResult> setDefaultLicenseType(UserVisitPK userVisitPK, SetDefaultLicenseTypeForm form);
    
    CommandResult<EditLicenseTypeResult> editLicenseType(UserVisitPK userVisitPK, EditLicenseTypeForm form);
    
    CommandResult<VoidResult> deleteLicenseType(UserVisitPK userVisitPK, DeleteLicenseTypeForm form);
    
    // --------------------------------------------------------------------------------
    //   License Type Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> createLicenseTypeDescription(UserVisitPK userVisitPK, CreateLicenseTypeDescriptionForm form);
    
    CommandResult<GetLicenseTypeDescriptionResult> getLicenseTypeDescription(UserVisitPK userVisitPK, GetLicenseTypeDescriptionForm form);
    
    CommandResult<GetLicenseTypeDescriptionsResult> getLicenseTypeDescriptions(UserVisitPK userVisitPK, GetLicenseTypeDescriptionsForm form);
    
    CommandResult<EditLicenseTypeDescriptionResult> editLicenseTypeDescription(UserVisitPK userVisitPK, EditLicenseTypeDescriptionForm form);
    
    CommandResult<VoidResult> deleteLicenseTypeDescription(UserVisitPK userVisitPK, DeleteLicenseTypeDescriptionForm form);
    
    // --------------------------------------------------------------------------------
    //   License Utilities
    // --------------------------------------------------------------------------------
    
    CommandResult<VoidResult> updateLicense(UserVisitPK userVisitPK);
    
}
