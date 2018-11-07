// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.security;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.SecurityService;
import com.echothree.control.user.security.common.edit.SecurityRoleEdit;
import com.echothree.control.user.security.common.edit.SecurityRoleGroupDescriptionEdit;
import com.echothree.control.user.security.common.form.CreateSecurityRoleForm;
import com.echothree.control.user.security.common.form.CreateSecurityRoleGroupDescriptionForm;
import com.echothree.control.user.security.common.form.EditSecurityRoleForm;
import com.echothree.control.user.security.common.form.EditSecurityRoleGroupDescriptionForm;
import com.echothree.control.user.security.common.form.SecurityFormFactory;
import com.echothree.control.user.security.common.result.EditSecurityRoleGroupDescriptionResult;
import com.echothree.control.user.security.common.result.EditSecurityRoleResult;
import com.echothree.control.user.security.common.spec.SecurityRoleGroupDescriptionSpec;
import com.echothree.control.user.security.common.spec.SecurityRoleSpec;
import com.echothree.control.user.security.common.spec.SecuritySpecFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SecurityRoleGroupHandler
        extends BaseHandler {
    
    SecurityService securityService;
    String securityRoleGroupName;
    
    /** Creates a new instance of SecurityRoleGroupHandler */
    public SecurityRoleGroupHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String securityRoleGroupName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            securityService = SecurityUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.securityRoleGroupName = securityRoleGroupName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("securityRoleGroupDescription")) {
            SecurityRoleGroupDescriptionSpec spec = SecuritySpecFactory.getSecurityRoleGroupDescriptionSpec();
            EditSecurityRoleGroupDescriptionForm editForm = SecurityFormFactory.getEditSecurityRoleGroupDescriptionForm();

            spec.setSecurityRoleGroupName(securityRoleGroupName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = securityService.editSecurityRoleGroupDescription(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSecurityRoleGroupDescription.name())) {
                    CreateSecurityRoleGroupDescriptionForm createForm = SecurityFormFactory.getCreateSecurityRoleGroupDescriptionForm();

                    createForm.setSecurityRoleGroupName(securityRoleGroupName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = securityService.createSecurityRoleGroupDescription(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLog().error(commandResult);
                    }
                } else {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditSecurityRoleGroupDescriptionResult result = (EditSecurityRoleGroupDescriptionResult)executionResult.getResult();

                if(result != null) {
                    SecurityRoleGroupDescriptionEdit edit = (SecurityRoleGroupDescriptionEdit)result.getEdit();
                    String description = attrs.getValue("description");
                    boolean changed = false;
                    
                    if(!edit.getDescription().equals(description)) {
                        edit.setDescription(description);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = securityService.editSecurityRoleGroupDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = securityService.editSecurityRoleGroupDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    }
                }
            }
        } else if(localName.equals("securityRole")) {
            SecurityRoleSpec spec = SecuritySpecFactory.getSecurityRoleSpec();
            EditSecurityRoleForm editForm = SecurityFormFactory.getEditSecurityRoleForm();

            spec.setSecurityRoleGroupName(securityRoleGroupName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = securityService.editSecurityRole(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSecurityRoleName.name())) {
                    CreateSecurityRoleForm createForm = SecurityFormFactory.getCreateSecurityRoleForm();

                    createForm.setSecurityRoleGroupName(securityRoleGroupName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = securityService.createSecurityRole(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLog().error(commandResult);
                    }
                } else {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditSecurityRoleResult result = (EditSecurityRoleResult)executionResult.getResult();

                if(result != null) {
                    SecurityRoleEdit edit = (SecurityRoleEdit)result.getEdit();
                    String isDefault = attrs.getValue("isDefault");
                    String sortOrder = attrs.getValue("sortOrder");
                    boolean changed = false;
                    
                    if(!edit.getIsDefault().equals(isDefault)) {
                        edit.setIsDefault(isDefault);
                        changed = true;
                    }

                    if(!edit.getSortOrder().equals(sortOrder)) {
                        edit.setSortOrder(sortOrder);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = securityService.editSecurityRole(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = securityService.editSecurityRole(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    }
                }
            }
            
            initialDataParser.pushHandler(new SecurityRoleHandler(initialDataParser, this, securityRoleGroupName, spec.getSecurityRoleName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("securityRoleGroup")) {
            initialDataParser.popHandler();
        }
    }
    
}
