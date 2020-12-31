// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
        return new CreateSecurityRoleGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleGroupChoices(UserVisitPK userVisitPK, GetSecurityRoleGroupChoicesForm form) {
        return new GetSecurityRoleGroupChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleGroup(UserVisitPK userVisitPK, GetSecurityRoleGroupForm form) {
        return new GetSecurityRoleGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleGroups(UserVisitPK userVisitPK, GetSecurityRoleGroupsForm form) {
        return new GetSecurityRoleGroupsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSecurityRoleGroup(UserVisitPK userVisitPK, SetDefaultSecurityRoleGroupForm form) {
        return new SetDefaultSecurityRoleGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSecurityRoleGroup(UserVisitPK userVisitPK, EditSecurityRoleGroupForm form) {
        return new EditSecurityRoleGroupCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSecurityRoleGroup(UserVisitPK userVisitPK, DeleteSecurityRoleGroupForm form) {
        return new DeleteSecurityRoleGroupCommand(userVisitPK, form).run();
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRoleGroupDescription(UserVisitPK userVisitPK, CreateSecurityRoleGroupDescriptionForm form) {
        return new CreateSecurityRoleGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleGroupDescription(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionForm form) {
        return new GetSecurityRoleGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleGroupDescriptions(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionsForm form) {
        return new GetSecurityRoleGroupDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSecurityRoleGroupDescription(UserVisitPK userVisitPK, EditSecurityRoleGroupDescriptionForm form) {
        return new EditSecurityRoleGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSecurityRoleGroupDescription(UserVisitPK userVisitPK, DeleteSecurityRoleGroupDescriptionForm form) {
        return new DeleteSecurityRoleGroupDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Security Roles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult checkSecurityRoles(UserVisitPK userVisitPK, CheckSecurityRolesForm form) {
        return new CheckSecurityRolesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult createSecurityRole(UserVisitPK userVisitPK, CreateSecurityRoleForm form) {
        return new CreateSecurityRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoles(UserVisitPK userVisitPK, GetSecurityRolesForm form) {
        return new GetSecurityRolesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRole(UserVisitPK userVisitPK, GetSecurityRoleForm form) {
        return new GetSecurityRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleChoices(UserVisitPK userVisitPK, GetSecurityRoleChoicesForm form) {
        return new GetSecurityRoleChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultSecurityRole(UserVisitPK userVisitPK, SetDefaultSecurityRoleForm form) {
        return new SetDefaultSecurityRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSecurityRole(UserVisitPK userVisitPK, EditSecurityRoleForm form) {
        return new EditSecurityRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSecurityRole(UserVisitPK userVisitPK, DeleteSecurityRoleForm form) {
        return new DeleteSecurityRoleCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRoleDescription(UserVisitPK userVisitPK, CreateSecurityRoleDescriptionForm form) {
        return new CreateSecurityRoleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleDescription(UserVisitPK userVisitPK, GetSecurityRoleDescriptionForm form) {
        return new GetSecurityRoleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRoleDescriptions(UserVisitPK userVisitPK, GetSecurityRoleDescriptionsForm form) {
        return new GetSecurityRoleDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSecurityRoleDescription(UserVisitPK userVisitPK, EditSecurityRoleDescriptionForm form) {
        return new EditSecurityRoleDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSecurityRoleDescription(UserVisitPK userVisitPK, DeleteSecurityRoleDescriptionForm form) {
        return new DeleteSecurityRoleDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Party Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRolePartyType(UserVisitPK userVisitPK, CreateSecurityRolePartyTypeForm form) {
        return new CreateSecurityRolePartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRolePartyTypes(UserVisitPK userVisitPK, GetSecurityRolePartyTypesForm form) {
        return new GetSecurityRolePartyTypesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getSecurityRolePartyType(UserVisitPK userVisitPK, GetSecurityRolePartyTypeForm form) {
        return new GetSecurityRolePartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editSecurityRolePartyType(UserVisitPK userVisitPK, EditSecurityRolePartyTypeForm form) {
        return new EditSecurityRolePartyTypeCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deleteSecurityRolePartyType(UserVisitPK userVisitPK, DeleteSecurityRolePartyTypeForm form) {
        return new DeleteSecurityRolePartyTypeCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Templates
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplate(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateForm form) {
        return new CreatePartySecurityRoleTemplateCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplate(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateForm form) {
        return new GetPartySecurityRoleTemplateCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplates(UserVisitPK userVisitPK, GetPartySecurityRoleTemplatesForm form) {
        return new GetPartySecurityRoleTemplatesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateChoices(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateChoicesForm form) {
        return new GetPartySecurityRoleTemplateChoicesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult setDefaultPartySecurityRoleTemplate(UserVisitPK userVisitPK, SetDefaultPartySecurityRoleTemplateForm form) {
        return new SetDefaultPartySecurityRoleTemplateCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartySecurityRoleTemplate(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateForm form) {
        return new EditPartySecurityRoleTemplateCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplate(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateForm form) {
        return new DeletePartySecurityRoleTemplateCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateDescriptionForm form) {
        return new CreatePartySecurityRoleTemplateDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionForm form) {
        return new GetPartySecurityRoleTemplateDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateDescriptions(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionsForm form) {
        return new GetPartySecurityRoleTemplateDescriptionsCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult editPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateDescriptionForm form) {
        return new EditPartySecurityRoleTemplateDescriptionCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateDescriptionForm form) {
        return new DeletePartySecurityRoleTemplateDescriptionCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateRoleForm form) {
        return new CreatePartySecurityRoleTemplateRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRoleForm form) {
        return new GetPartySecurityRoleTemplateRoleCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateRoles(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRolesForm form) {
        return new GetPartySecurityRoleTemplateRolesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateRole(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateRoleForm form) {
        return new DeletePartySecurityRoleTemplateRoleCommand(userVisitPK, form).run();
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Training Classes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateTrainingClassForm form) {
        return new CreatePartySecurityRoleTemplateTrainingClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassForm form) {
        return new GetPartySecurityRoleTemplateTrainingClassCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateTrainingClasses(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassesForm form) {
        return new GetPartySecurityRoleTemplateTrainingClassesCommand(userVisitPK, form).run();
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateTrainingClassForm form) {
        return new DeletePartySecurityRoleTemplateTrainingClassCommand(userVisitPK, form).run();
    }
    
}
