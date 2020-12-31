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

package com.echothree.ui.cli.dataloader.data.handler.security;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.SecurityService;
import com.echothree.control.user.security.common.edit.SecurityRoleDescriptionEdit;
import com.echothree.control.user.security.common.form.CreateSecurityRoleDescriptionForm;
import com.echothree.control.user.security.common.form.EditSecurityRoleDescriptionForm;
import com.echothree.control.user.security.common.form.SecurityFormFactory;
import com.echothree.control.user.security.common.result.EditSecurityRoleDescriptionResult;
import com.echothree.control.user.security.common.spec.SecurityRoleDescriptionSpec;
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

public class SecurityRoleHandler
        extends BaseHandler {
    
    SecurityService securityService;
    String securityRoleGroupName;
    String securityRoleName;
    
    /** Creates a new instance of SecurityRoleHandler */
    public SecurityRoleHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String securityRoleGroupName, String securityRoleName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            securityService = SecurityUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.securityRoleGroupName = securityRoleGroupName;
        this.securityRoleName = securityRoleName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("securityRoleDescription")) {
            SecurityRoleDescriptionSpec spec = SecuritySpecFactory.getSecurityRoleDescriptionSpec();
            EditSecurityRoleDescriptionForm editForm = SecurityFormFactory.getEditSecurityRoleDescriptionForm();

            spec.setSecurityRoleGroupName(securityRoleGroupName);
            spec.setSecurityRoleName(securityRoleName);
            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);
            
            CommandResult commandResult = securityService.editSecurityRoleDescription(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSecurityRoleDescription.name())) {
                    CreateSecurityRoleDescriptionForm createForm = SecurityFormFactory.getCreateSecurityRoleDescriptionForm();

                    createForm.setSecurityRoleGroupName(securityRoleGroupName);
                    createForm.setSecurityRoleName(securityRoleName);
                    createForm.set(getAttrsMap(attrs));

                    commandResult = securityService.createSecurityRoleDescription(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLog().error(commandResult);
                    }
                } else {
                    getLog().error(commandResult);
                }
            } else {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                EditSecurityRoleDescriptionResult result = (EditSecurityRoleDescriptionResult)executionResult.getResult();

                if(result != null) {
                    SecurityRoleDescriptionEdit edit = (SecurityRoleDescriptionEdit)result.getEdit();
                    String description = attrs.getValue("description");
                    boolean changed = false;
                    
                    if(!edit.getDescription().equals(description)) {
                        edit.setDescription(description);
                        changed = true;
                    }

                    if(changed) {
                        editForm.setEdit(edit);
                        editForm.setEditMode(EditMode.UPDATE);
                        
                        commandResult = securityService.editSecurityRoleDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = securityService.editSecurityRoleDescription(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLog().error(commandResult);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("securityRole")) {
            initialDataParser.popHandler();
        }
    }
    
}
