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
import com.echothree.control.user.security.common.result.*;
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
    
    CommandResult<?> createSecurityRoleGroup(UserVisitPK userVisitPK, CreateSecurityRoleGroupForm form);
    
    CommandResult<GetSecurityRoleGroupChoicesResult> getSecurityRoleGroupChoices(UserVisitPK userVisitPK, GetSecurityRoleGroupChoicesForm form);
    
    CommandResult<GetSecurityRoleGroupResult> getSecurityRoleGroup(UserVisitPK userVisitPK, GetSecurityRoleGroupForm form);
    
    CommandResult<GetSecurityRoleGroupsResult> getSecurityRoleGroups(UserVisitPK userVisitPK, GetSecurityRoleGroupsForm form);
    
    CommandResult<?> setDefaultSecurityRoleGroup(UserVisitPK userVisitPK, SetDefaultSecurityRoleGroupForm form);
    
    CommandResult<EditSecurityRoleGroupResult> editSecurityRoleGroup(UserVisitPK userVisitPK, EditSecurityRoleGroupForm form);
    
    CommandResult<?> deleteSecurityRoleGroup(UserVisitPK userVisitPK, DeleteSecurityRoleGroupForm form);
    
    // --------------------------------------------------------------------------------
    //   Security Role Group Descriptions
    // --------------------------------------------------------------------------------
    
    CommandResult<?> createSecurityRoleGroupDescription(UserVisitPK userVisitPK, CreateSecurityRoleGroupDescriptionForm form);
    
    CommandResult<GetSecurityRoleGroupDescriptionResult> getSecurityRoleGroupDescription(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionForm form);
    
    CommandResult<GetSecurityRoleGroupDescriptionsResult> getSecurityRoleGroupDescriptions(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionsForm form);
    
    CommandResult<EditSecurityRoleGroupDescriptionResult> editSecurityRoleGroupDescription(UserVisitPK userVisitPK, EditSecurityRoleGroupDescriptionForm form);
    
    CommandResult<?> deleteSecurityRoleGroupDescription(UserVisitPK userVisitPK, DeleteSecurityRoleGroupDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Security Roles
    // -------------------------------------------------------------------------
    
    CommandResult<?> checkSecurityRoles(UserVisitPK userVisitPK, CheckSecurityRolesForm form);
    
    CommandResult<?> createSecurityRole(UserVisitPK userVisitPK, CreateSecurityRoleForm form);
    
    CommandResult<GetSecurityRolesResult> getSecurityRoles(UserVisitPK userVisitPK, GetSecurityRolesForm form);
    
    CommandResult<GetSecurityRoleResult> getSecurityRole(UserVisitPK userVisitPK, GetSecurityRoleForm form);
    
    CommandResult<GetSecurityRoleChoicesResult> getSecurityRoleChoices(UserVisitPK userVisitPK, GetSecurityRoleChoicesForm form);
    
    CommandResult<?> setDefaultSecurityRole(UserVisitPK userVisitPK, SetDefaultSecurityRoleForm form);
    
    CommandResult<EditSecurityRoleResult> editSecurityRole(UserVisitPK userVisitPK, EditSecurityRoleForm form);
    
    CommandResult<?> deleteSecurityRole(UserVisitPK userVisitPK, DeleteSecurityRoleForm form);
    
    // -------------------------------------------------------------------------
    //   Security Role Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSecurityRoleDescription(UserVisitPK userVisitPK, CreateSecurityRoleDescriptionForm form);
    
    CommandResult<GetSecurityRoleDescriptionResult> getSecurityRoleDescription(UserVisitPK userVisitPK, GetSecurityRoleDescriptionForm form);
    
    CommandResult<GetSecurityRoleDescriptionsResult> getSecurityRoleDescriptions(UserVisitPK userVisitPK, GetSecurityRoleDescriptionsForm form);
    
    CommandResult<EditSecurityRoleDescriptionResult> editSecurityRoleDescription(UserVisitPK userVisitPK, EditSecurityRoleDescriptionForm form);
    
    CommandResult<?> deleteSecurityRoleDescription(UserVisitPK userVisitPK, DeleteSecurityRoleDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Security Role Party Types
    // -------------------------------------------------------------------------
    
    CommandResult<?> createSecurityRolePartyType(UserVisitPK userVisitPK, CreateSecurityRolePartyTypeForm form);
    
    CommandResult<GetSecurityRolePartyTypesResult> getSecurityRolePartyTypes(UserVisitPK userVisitPK, GetSecurityRolePartyTypesForm form);
    
    CommandResult<GetSecurityRolePartyTypeResult> getSecurityRolePartyType(UserVisitPK userVisitPK, GetSecurityRolePartyTypeForm form);
    
    CommandResult<EditSecurityRolePartyTypeResult> editSecurityRolePartyType(UserVisitPK userVisitPK, EditSecurityRolePartyTypeForm form);
    
    CommandResult<?> deleteSecurityRolePartyType(UserVisitPK userVisitPK, DeleteSecurityRolePartyTypeForm form);
    
    // -------------------------------------------------------------------------
    //   Party Security Role Templates
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartySecurityRoleTemplate(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateForm form);
    
    CommandResult<GetPartySecurityRoleTemplateResult> getPartySecurityRoleTemplate(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateForm form);
    
    CommandResult<GetPartySecurityRoleTemplatesResult> getPartySecurityRoleTemplates(UserVisitPK userVisitPK, GetPartySecurityRoleTemplatesForm form);
    
    CommandResult<GetPartySecurityRoleTemplateChoicesResult> getPartySecurityRoleTemplateChoices(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateChoicesForm form);
    
    CommandResult<?> setDefaultPartySecurityRoleTemplate(UserVisitPK userVisitPK, SetDefaultPartySecurityRoleTemplateForm form);
    
    CommandResult<EditPartySecurityRoleTemplateResult> editPartySecurityRoleTemplate(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateForm form);
    
    CommandResult<?> deletePartySecurityRoleTemplate(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateForm form);
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Descriptions
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateDescriptionForm form);
    
    CommandResult<GetPartySecurityRoleTemplateDescriptionResult> getPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionForm form);
    
    CommandResult<GetPartySecurityRoleTemplateDescriptionsResult> getPartySecurityRoleTemplateDescriptions(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionsForm form);
    
    CommandResult<EditPartySecurityRoleTemplateDescriptionResult> editPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateDescriptionForm form);
    
    CommandResult<?> deletePartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateDescriptionForm form);
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateRoleForm form);
    
    CommandResult<GetPartySecurityRoleTemplateRoleResult> getPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRoleForm form);
    
    CommandResult<GetPartySecurityRoleTemplateRolesResult> getPartySecurityRoleTemplateRoles(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRolesForm form);
    
    CommandResult<?> deletePartySecurityRoleTemplateRole(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateRoleForm form);
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Training Classes
    // -------------------------------------------------------------------------
    
    CommandResult<?> createPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateTrainingClassForm form);
    
    CommandResult<GetPartySecurityRoleTemplateTrainingClassResult> getPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassForm form);
    
    CommandResult<GetPartySecurityRoleTemplateTrainingClassesResult> getPartySecurityRoleTemplateTrainingClasses(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassesForm form);
    
    CommandResult<?> deletePartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateTrainingClassForm form);
    
}
