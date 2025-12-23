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

package com.echothree.ui.cli.dataloader.util.data.handler.security;

import com.echothree.control.user.security.common.SecurityService;
import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.edit.SecurityRoleEdit;
import com.echothree.control.user.security.common.edit.SecurityRoleGroupDescriptionEdit;
import com.echothree.control.user.security.common.form.SecurityFormFactory;
import com.echothree.control.user.security.common.result.EditSecurityRoleGroupDescriptionResult;
import com.echothree.control.user.security.common.result.EditSecurityRoleResult;
import com.echothree.control.user.security.common.spec.SecuritySpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
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
            var spec = SecuritySpecFactory.getSecurityRoleGroupDescriptionSpec();
            var editForm = SecurityFormFactory.getEditSecurityRoleGroupDescriptionForm();

            spec.setSecurityRoleGroupName(securityRoleGroupName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = securityService.editSecurityRoleGroupDescription(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSecurityRoleGroupDescription.name())) {
                    var createForm = SecurityFormFactory.getCreateSecurityRoleGroupDescriptionForm();

                    createForm.set(spec.get());

                    commandResult = securityService.createSecurityRoleGroupDescription(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSecurityRoleGroupDescriptionResult)executionResult.getResult();

                if(result != null) {
                    var edit = (SecurityRoleGroupDescriptionEdit)result.getEdit();
                    var description = attrs.getValue("description");
                    var changed = false;
                    
                    if(!edit.getDescription().equals(description)) {
                        edit.setDescription(description);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = securityService.editSecurityRoleGroupDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = securityService.editSecurityRoleGroupDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
        } else if(localName.equals("securityRole")) {
            var spec = SecuritySpecFactory.getSecurityRoleSpec();
            var editForm = SecurityFormFactory.getEditSecurityRoleForm();

            spec.setSecurityRoleGroupName(securityRoleGroupName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = securityService.editSecurityRole(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSecurityRoleName.name())) {
                    var createForm = SecurityFormFactory.getCreateSecurityRoleForm();

                    createForm.setSecurityRoleGroupName(securityRoleGroupName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = securityService.createSecurityRole(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSecurityRoleResult)executionResult.getResult();

                if(result != null) {
                    var edit = (SecurityRoleEdit)result.getEdit();
                    var isDefault = attrs.getValue("isDefault");
                    var sortOrder = attrs.getValue("sortOrder");
                    var changed = false;
                    
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
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = securityService.editSecurityRole(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
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
