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

package com.echothree.control.user.security.server;

import com.echothree.control.user.security.common.SecurityRemote;
import com.echothree.control.user.security.common.form.*;
import com.echothree.control.user.security.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;

@Stateless
public class SecurityBean
        extends SecurityFormsImpl
        implements SecurityRemote, SecurityLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "SecurityBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRoleGroup(UserVisitPK userVisitPK, CreateSecurityRoleGroupForm form) {
        return new CreateSecurityRoleGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupChoices(UserVisitPK userVisitPK, GetSecurityRoleGroupChoicesForm form) {
        return new GetSecurityRoleGroupChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroup(UserVisitPK userVisitPK, GetSecurityRoleGroupForm form) {
        return new GetSecurityRoleGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroups(UserVisitPK userVisitPK, GetSecurityRoleGroupsForm form) {
        return new GetSecurityRoleGroupsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSecurityRoleGroup(UserVisitPK userVisitPK, SetDefaultSecurityRoleGroupForm form) {
        return new SetDefaultSecurityRoleGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRoleGroup(UserVisitPK userVisitPK, EditSecurityRoleGroupForm form) {
        return new EditSecurityRoleGroupCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRoleGroup(UserVisitPK userVisitPK, DeleteSecurityRoleGroupForm form) {
        return new DeleteSecurityRoleGroupCommand().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRoleGroupDescription(UserVisitPK userVisitPK, CreateSecurityRoleGroupDescriptionForm form) {
        return new CreateSecurityRoleGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupDescription(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionForm form) {
        return new GetSecurityRoleGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupDescriptions(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionsForm form) {
        return new GetSecurityRoleGroupDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRoleGroupDescription(UserVisitPK userVisitPK, EditSecurityRoleGroupDescriptionForm form) {
        return new EditSecurityRoleGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRoleGroupDescription(UserVisitPK userVisitPK, DeleteSecurityRoleGroupDescriptionForm form) {
        return new DeleteSecurityRoleGroupDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Security Roles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult checkSecurityRoles(UserVisitPK userVisitPK, CheckSecurityRolesForm form) {
        return new CheckSecurityRolesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult createSecurityRole(UserVisitPK userVisitPK, CreateSecurityRoleForm form) {
        return new CreateSecurityRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoles(UserVisitPK userVisitPK, GetSecurityRolesForm form) {
        return new GetSecurityRolesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRole(UserVisitPK userVisitPK, GetSecurityRoleForm form) {
        return new GetSecurityRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleChoices(UserVisitPK userVisitPK, GetSecurityRoleChoicesForm form) {
        return new GetSecurityRoleChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSecurityRole(UserVisitPK userVisitPK, SetDefaultSecurityRoleForm form) {
        return new SetDefaultSecurityRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRole(UserVisitPK userVisitPK, EditSecurityRoleForm form) {
        return new EditSecurityRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRole(UserVisitPK userVisitPK, DeleteSecurityRoleForm form) {
        return new DeleteSecurityRoleCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRoleDescription(UserVisitPK userVisitPK, CreateSecurityRoleDescriptionForm form) {
        return new CreateSecurityRoleDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleDescription(UserVisitPK userVisitPK, GetSecurityRoleDescriptionForm form) {
        return new GetSecurityRoleDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleDescriptions(UserVisitPK userVisitPK, GetSecurityRoleDescriptionsForm form) {
        return new GetSecurityRoleDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRoleDescription(UserVisitPK userVisitPK, EditSecurityRoleDescriptionForm form) {
        return new EditSecurityRoleDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRoleDescription(UserVisitPK userVisitPK, DeleteSecurityRoleDescriptionForm form) {
        return new DeleteSecurityRoleDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Party Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRolePartyType(UserVisitPK userVisitPK, CreateSecurityRolePartyTypeForm form) {
        return new CreateSecurityRolePartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRolePartyTypes(UserVisitPK userVisitPK, GetSecurityRolePartyTypesForm form) {
        return new GetSecurityRolePartyTypesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRolePartyType(UserVisitPK userVisitPK, GetSecurityRolePartyTypeForm form) {
        return new GetSecurityRolePartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRolePartyType(UserVisitPK userVisitPK, EditSecurityRolePartyTypeForm form) {
        return new EditSecurityRolePartyTypeCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRolePartyType(UserVisitPK userVisitPK, DeleteSecurityRolePartyTypeForm form) {
        return new DeleteSecurityRolePartyTypeCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Templates
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplate(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateForm form) {
        return new CreatePartySecurityRoleTemplateCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplate(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateForm form) {
        return new GetPartySecurityRoleTemplateCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplates(UserVisitPK userVisitPK, GetPartySecurityRoleTemplatesForm form) {
        return new GetPartySecurityRoleTemplatesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateChoices(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateChoicesForm form) {
        return new GetPartySecurityRoleTemplateChoicesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPartySecurityRoleTemplate(UserVisitPK userVisitPK, SetDefaultPartySecurityRoleTemplateForm form) {
        return new SetDefaultPartySecurityRoleTemplateCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartySecurityRoleTemplate(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateForm form) {
        return new EditPartySecurityRoleTemplateCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplate(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateForm form) {
        return new DeletePartySecurityRoleTemplateCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateDescriptionForm form) {
        return new CreatePartySecurityRoleTemplateDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionForm form) {
        return new GetPartySecurityRoleTemplateDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateDescriptions(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionsForm form) {
        return new GetPartySecurityRoleTemplateDescriptionsCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateDescriptionForm form) {
        return new EditPartySecurityRoleTemplateDescriptionCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateDescriptionForm form) {
        return new DeletePartySecurityRoleTemplateDescriptionCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateRoleForm form) {
        return new CreatePartySecurityRoleTemplateRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRoleForm form) {
        return new GetPartySecurityRoleTemplateRoleCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateRoles(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRolesForm form) {
        return new GetPartySecurityRoleTemplateRolesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateRole(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateRoleForm form) {
        return new DeletePartySecurityRoleTemplateRoleCommand().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Training Classes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateTrainingClassForm form) {
        return new CreatePartySecurityRoleTemplateTrainingClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassForm form) {
        return new GetPartySecurityRoleTemplateTrainingClassCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateTrainingClasses(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassesForm form) {
        return new GetPartySecurityRoleTemplateTrainingClassesCommand().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateTrainingClassForm form) {
        return new DeletePartySecurityRoleTemplateTrainingClassCommand().run(userVisitPK, form);
    }
    
}
