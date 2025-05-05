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

package com.echothree.control.user.license.server;

import com.echothree.control.user.license.common.LicenseRemote;
import com.echothree.control.user.license.common.form.*;
import com.echothree.control.user.license.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class LicenseBean
        extends LicenseFormsImpl
        implements LicenseRemote, LicenseLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "LicenseBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   License Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLicenseType(UserVisitPK userVisitPK, CreateLicenseTypeForm form) {
        return new CreateLicenseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLicenseTypeChoices(UserVisitPK userVisitPK, GetLicenseTypeChoicesForm form) {
        return new GetLicenseTypeChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLicenseType(UserVisitPK userVisitPK, GetLicenseTypeForm form) {
        return new GetLicenseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLicenseTypes(UserVisitPK userVisitPK, GetLicenseTypesForm form) {
        return new GetLicenseTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultLicenseType(UserVisitPK userVisitPK, SetDefaultLicenseTypeForm form) {
        return new SetDefaultLicenseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLicenseType(UserVisitPK userVisitPK, EditLicenseTypeForm form) {
        return new EditLicenseTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLicenseType(UserVisitPK userVisitPK, DeleteLicenseTypeForm form) {
        return new DeleteLicenseTypeCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   License Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createLicenseTypeDescription(UserVisitPK userVisitPK, CreateLicenseTypeDescriptionForm form) {
        return new CreateLicenseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLicenseTypeDescription(UserVisitPK userVisitPK, GetLicenseTypeDescriptionForm form) {
        return new GetLicenseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getLicenseTypeDescriptions(UserVisitPK userVisitPK, GetLicenseTypeDescriptionsForm form) {
        return new GetLicenseTypeDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editLicenseTypeDescription(UserVisitPK userVisitPK, EditLicenseTypeDescriptionForm form) {
        return new EditLicenseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteLicenseTypeDescription(UserVisitPK userVisitPK, DeleteLicenseTypeDescriptionForm form) {
        return new DeleteLicenseTypeDescriptionCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   License Utilities
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult updateLicense(UserVisitPK userVisitPK) {
        return new UpdateLicenseCommand().run(userVisitPK);
    }
    
}
