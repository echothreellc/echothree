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
import javax.enterprise.inject.spi.CDI;

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
        return CDI.current().select(CreateSecurityRoleGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupChoices(UserVisitPK userVisitPK, GetSecurityRoleGroupChoicesForm form) {
        return CDI.current().select(GetSecurityRoleGroupChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroup(UserVisitPK userVisitPK, GetSecurityRoleGroupForm form) {
        return CDI.current().select(GetSecurityRoleGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroups(UserVisitPK userVisitPK, GetSecurityRoleGroupsForm form) {
        return CDI.current().select(GetSecurityRoleGroupsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSecurityRoleGroup(UserVisitPK userVisitPK, SetDefaultSecurityRoleGroupForm form) {
        return CDI.current().select(SetDefaultSecurityRoleGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRoleGroup(UserVisitPK userVisitPK, EditSecurityRoleGroupForm form) {
        return CDI.current().select(EditSecurityRoleGroupCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRoleGroup(UserVisitPK userVisitPK, DeleteSecurityRoleGroupForm form) {
        return CDI.current().select(DeleteSecurityRoleGroupCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Security Role Groups
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRoleGroupDescription(UserVisitPK userVisitPK, CreateSecurityRoleGroupDescriptionForm form) {
        return CDI.current().select(CreateSecurityRoleGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupDescription(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionForm form) {
        return CDI.current().select(GetSecurityRoleGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleGroupDescriptions(UserVisitPK userVisitPK, GetSecurityRoleGroupDescriptionsForm form) {
        return CDI.current().select(GetSecurityRoleGroupDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRoleGroupDescription(UserVisitPK userVisitPK, EditSecurityRoleGroupDescriptionForm form) {
        return CDI.current().select(EditSecurityRoleGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRoleGroupDescription(UserVisitPK userVisitPK, DeleteSecurityRoleGroupDescriptionForm form) {
        return CDI.current().select(DeleteSecurityRoleGroupDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Security Roles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult checkSecurityRoles(UserVisitPK userVisitPK, CheckSecurityRolesForm form) {
        return CDI.current().select(CheckSecurityRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult createSecurityRole(UserVisitPK userVisitPK, CreateSecurityRoleForm form) {
        return CDI.current().select(CreateSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoles(UserVisitPK userVisitPK, GetSecurityRolesForm form) {
        return CDI.current().select(GetSecurityRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRole(UserVisitPK userVisitPK, GetSecurityRoleForm form) {
        return CDI.current().select(GetSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleChoices(UserVisitPK userVisitPK, GetSecurityRoleChoicesForm form) {
        return CDI.current().select(GetSecurityRoleChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultSecurityRole(UserVisitPK userVisitPK, SetDefaultSecurityRoleForm form) {
        return CDI.current().select(SetDefaultSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRole(UserVisitPK userVisitPK, EditSecurityRoleForm form) {
        return CDI.current().select(EditSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRole(UserVisitPK userVisitPK, DeleteSecurityRoleForm form) {
        return CDI.current().select(DeleteSecurityRoleCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRoleDescription(UserVisitPK userVisitPK, CreateSecurityRoleDescriptionForm form) {
        return CDI.current().select(CreateSecurityRoleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleDescription(UserVisitPK userVisitPK, GetSecurityRoleDescriptionForm form) {
        return CDI.current().select(GetSecurityRoleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRoleDescriptions(UserVisitPK userVisitPK, GetSecurityRoleDescriptionsForm form) {
        return CDI.current().select(GetSecurityRoleDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRoleDescription(UserVisitPK userVisitPK, EditSecurityRoleDescriptionForm form) {
        return CDI.current().select(EditSecurityRoleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRoleDescription(UserVisitPK userVisitPK, DeleteSecurityRoleDescriptionForm form) {
        return CDI.current().select(DeleteSecurityRoleDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Security Role Party Types
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createSecurityRolePartyType(UserVisitPK userVisitPK, CreateSecurityRolePartyTypeForm form) {
        return CDI.current().select(CreateSecurityRolePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRolePartyTypes(UserVisitPK userVisitPK, GetSecurityRolePartyTypesForm form) {
        return CDI.current().select(GetSecurityRolePartyTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getSecurityRolePartyType(UserVisitPK userVisitPK, GetSecurityRolePartyTypeForm form) {
        return CDI.current().select(GetSecurityRolePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editSecurityRolePartyType(UserVisitPK userVisitPK, EditSecurityRolePartyTypeForm form) {
        return CDI.current().select(EditSecurityRolePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteSecurityRolePartyType(UserVisitPK userVisitPK, DeleteSecurityRolePartyTypeForm form) {
        return CDI.current().select(DeleteSecurityRolePartyTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Templates
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplate(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateForm form) {
        return CDI.current().select(CreatePartySecurityRoleTemplateCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplate(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplateCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplates(UserVisitPK userVisitPK, GetPartySecurityRoleTemplatesForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplatesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateChoices(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateChoicesForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplateChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultPartySecurityRoleTemplate(UserVisitPK userVisitPK, SetDefaultPartySecurityRoleTemplateForm form) {
        return CDI.current().select(SetDefaultPartySecurityRoleTemplateCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartySecurityRoleTemplate(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateForm form) {
        return CDI.current().select(EditPartySecurityRoleTemplateCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplate(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateForm form) {
        return CDI.current().select(DeletePartySecurityRoleTemplateCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Descriptions
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateDescriptionForm form) {
        return CDI.current().select(CreatePartySecurityRoleTemplateDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplateDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateDescriptions(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateDescriptionsForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplateDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editPartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, EditPartySecurityRoleTemplateDescriptionForm form) {
        return CDI.current().select(EditPartySecurityRoleTemplateDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateDescription(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateDescriptionForm form) {
        return CDI.current().select(DeletePartySecurityRoleTemplateDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateRoleForm form) {
        return CDI.current().select(CreatePartySecurityRoleTemplateRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateRole(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRoleForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplateRoleCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateRoles(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateRolesForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplateRolesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateRole(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateRoleForm form) {
        return CDI.current().select(DeletePartySecurityRoleTemplateRoleCommand.class).get().run(userVisitPK, form);
    }
    
    // -------------------------------------------------------------------------
    //   Party Security Role Template Training Classes
    // -------------------------------------------------------------------------
    
    @Override
    public CommandResult createPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, CreatePartySecurityRoleTemplateTrainingClassForm form) {
        return CDI.current().select(CreatePartySecurityRoleTemplateTrainingClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplateTrainingClassCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getPartySecurityRoleTemplateTrainingClasses(UserVisitPK userVisitPK, GetPartySecurityRoleTemplateTrainingClassesForm form) {
        return CDI.current().select(GetPartySecurityRoleTemplateTrainingClassesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deletePartySecurityRoleTemplateTrainingClass(UserVisitPK userVisitPK, DeletePartySecurityRoleTemplateTrainingClassForm form) {
        return CDI.current().select(DeletePartySecurityRoleTemplateTrainingClassCommand.class).get().run(userVisitPK, form);
    }
    
}
