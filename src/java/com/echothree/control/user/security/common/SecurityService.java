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

package com.echothree.control.user.security.common;

import com.echothree.control.user.security.common.form.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

public interface SecurityService
        extends SecurityForms {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    String ping();
    
    // --------------------------------------------------------------------------------
    //   Security Role Groups
    // --------------------------------------------------------------------------------
    
    CommandResult createSecurityRoleGroup(UserVisitPK userVisitPK, CreateSecurityRoleGroupForm form);
    
    CommandResult getSecurityRoleGroupChoices(UserVisitPK userVisitPK, GetSecurityRoleGroupChoicesForm form);
    
    CommandResult getSecurityRoleGroup(UserVisitPK userVisitPK, GetSecurityRoleGroupForm form);
    
    CommandResult getSecurityRoleGroups(UserVisitPK userVisitPK, GetSecurityRoleGroupsForm form);
    
    CommandResult setDefaultSecurityRoleGroup(UserVisitPK userVisitPK, SetDefaultSecurityRoleGroupForm form);
    
    CommandResult editSecurityRoleGroup(UserVisitPK userVisitPK, EditSecurityRoleGroupForm form);
    
    CommandResult deleteSecurityRoleGroup(UserVisitPK userVisitPK, DeleteSecurityRoleGroupForm form);
    
    // --------------------------------------------------------------------------------
    //   Security Role Group Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult createSecurityRoleGroupDescription(UserVisitPK userVisitPK, CreateSecurityRoleGroupDescriptionForm form);
    
    CommandResult getSecurityRoleGroupDescription(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionForm form);
    
    CommandResult getSecurityRoleGroupDescriptions(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionsForm form);
    
    CommandResult editSecurityRoleGroupDescription(UserVisitPK userVisitPK, EditSecurityRoleGroupDescriptionForm form);
    
    CommandResult deleteSecurityRoleGroupDescription(UserVisitPK userVisitPK, DeleteSecurityRoleGroupDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Security Roles
    // -------------------------------------------------------------------------
    
    CommandResult checkSecurityRoles(UserVisitPK userVisitPK, CheckSecurityRolesForm form);
    
    CommandResult createSecurityRole(UserVisitPK userVisitPK, CreateSecurityRoleForm form);
    
    CommandResult getSecurityRoles(UserVisitPK userVisitPK, GetSecurityRolesForm form);
    
    CommandResult getSecurityRole(UserVisitPK userVisitPK, GetSecurityRoleForm form);
    
    CommandResult getSecurityRoleChoices(UserVisitPK userVisitPK, GetSecurityRoleChoicesForm form);
    
    CommandResult setDefaultSecurityRole(UserVisitPK userVisitPK, SetDefaultSecurityRoleForm form);
    
    CommandResult editSecurityRole(UserVisitPK userVisitPK, EditSecurityRoleForm form);
    
    CommandResult deleteSecurityRole(UserVisitPK userVisitPK, DeleteSecurityRoleForm form);
    
    // -------------------------------------------------------------------------
    //   Security Role Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createSecurityRoleDescription(UserVisitPK userVisitPK, CreateSecurityRoleDescriptionForm form);
    
    CommandResult getSecurityRoleDescription(UserVisitPK userVisitPK, GetSecurityRoleDescriptionForm form);
    
    CommandResult getSecurityRoleDescriptions(UserVisitPK userVisitPK, GetSecurityRoleDescriptionsForm form);
    
    CommandResult editSecurityRoleDescription(UserVisitPK userVisitPK, EditSecurityRoleDescriptionForm form);
    
    CommandResult deleteSecurityRoleDescription(UserVisitPK userVisitPK, DeleteSecurityRoleDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Security Role Party Types
    // -------------------------------------------------------------------------
    
    CommandResult createSecurityRolePartyType(UserVisitPK userVisitPK, CreateSecurityRolePartyTypeForm form);
    
    CommandResult getSecurityRolePartyTypes(UserVisitPK userVisitPK, GetSecurityRolePartyTypesForm form);
    
    CommandResult getSecurityRolePartyType(UserVisitPK userVisitPK, GetSecurityRolePartyTypeForm form);
    
    CommandResult editSecurityRolePartyType(UserVisitPK userVisitPK, EditSecurityRolePartyTypeForm form);
    
    CommandResult deleteSecurityRolePartyType(UserVisitPK userVisitPK, DeleteSecurityRolePartyTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Party Security Role Templates
    // -------------------------------------------------------------------------
    
    CommandResult createPartySecurityRoleTemplate(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateForm form);
    
    CommandResult getPartySecurityRoleTemplate(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateForm form);
    
    CommandResult getPartySecurityRoleTemplates(UserVisitPK userVisitPK, GetPartySecurityRoleTemplatesForm form);
    
    CommandResult getPartySecurityRoleTemplateChoices(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateChoicesForm form);
    
    CommandResult setDefaultPartySecurityRoleTemplate(UserVisitPK userVisitPK, SetDefaultPartySecurityRoleTemplateForm form);
    
    CommandResult editPartySecurityRoleTemplate(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateForm form);
    
    CommandResult deletePartySecurityRoleTemplate(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateForm form);
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult createPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateDescriptionForm form);
    
    CommandResult getPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionForm form);
    
    CommandResult getPartySecurityRoleTemplateDescriptions(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionsForm form);
    
    CommandResult editPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateDescriptionForm form);
    
    CommandResult deletePartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // -------------------------------------------------------------------------
    
    CommandResult createPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateRoleForm form);
    
    CommandResult getPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRoleForm form);
    
    CommandResult getPartySecurityRoleTemplateRoles(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRolesForm form);
    
    CommandResult deletePartySecurityRoleTemplateRole(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateRoleForm form);
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Training Classes
    // -------------------------------------------------------------------------
    
    CommandResult createPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateTrainingClassForm form);
    
    CommandResult getPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassForm form);
    
    CommandResult getPartySecurityRoleTemplateTrainingClasses(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassesForm form);
    
    CommandResult deletePartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateTrainingClassForm form);
    
}
