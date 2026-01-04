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
import com.echothree.control.user.security.common.edit.SecurityRoleGroupEdit;
import com.echothree.control.user.security.common.form.SecurityFormFactory;
import com.echothree.control.user.security.common.result.EditSecurityRoleGroupResult;
import com.echothree.control.user.security.common.spec.SecuritySpecFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.common.message.ExecutionErrors;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class SecurityRoleGroupsHandler
        extends BaseHandler {
    
    SecurityService securityService;
    
    /** Creates a new instance of SecurityRoleGroupsHandler */
    public SecurityRoleGroupsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            securityService = SecurityUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("securityRoleGroup")) {
            var spec = SecuritySpecFactory.getSecurityRoleGroupSpec();
            var editForm = SecurityFormFactory.getEditSecurityRoleGroupForm();

            spec.set(getAttrsMap(attrs));

            editForm.setSpec(spec);
            editForm.setEditMode(EditMode.LOCK);

            var commandResult = securityService.editSecurityRoleGroup(initialDataParser.getUserVisit(), editForm);
            
            if(commandResult.hasErrors()) {
                if(commandResult.containsExecutionError(ExecutionErrors.UnknownSecurityRoleGroupName.name())) {
                    var createForm = SecurityFormFactory.getCreateSecurityRoleGroupForm();

                    createForm.set(spec.get());

                    commandResult = securityService.createSecurityRoleGroup(initialDataParser.getUserVisit(), createForm);
                    
                    if(commandResult.hasErrors()) {
                        getLogger().error(commandResult.toString());
                    }
                } else {
                    getLogger().error(commandResult.toString());
                }
            } else {
                var executionResult = commandResult.getExecutionResult();
                var result = (EditSecurityRoleGroupResult)executionResult.getResult();

                if(result != null) {
                    var edit = (SecurityRoleGroupEdit)result.getEdit();
                    var currentParentSecurityRoleGroupName = edit.getParentSecurityRoleGroupName();
                    var parentSecurityRoleGroupName = attrs.getValue("parentSecurityRoleGroupName");
                    var isDefault = attrs.getValue("isDefault");
                    var sortOrder = attrs.getValue("sortOrder");
                    var changed = false;
                    
                    if(currentParentSecurityRoleGroupName == null && parentSecurityRoleGroupName != null) {
                        edit.setParentSecurityRoleGroupName(parentSecurityRoleGroupName);
                        changed = true;
                    } else if(currentParentSecurityRoleGroupName != null && parentSecurityRoleGroupName == null) {
                        edit.setParentSecurityRoleGroupName(null);
                        changed = true;
                    } else if(currentParentSecurityRoleGroupName != null && parentSecurityRoleGroupName != null) {
                        if(!currentParentSecurityRoleGroupName.equals(parentSecurityRoleGroupName)) {
                            edit.setParentSecurityRoleGroupName(parentSecurityRoleGroupName);
                            changed = true;
                        }
                    }

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
                        
                        commandResult = securityService.editSecurityRoleGroup(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    } else {
                        editForm.setEdit(null);
                        editForm.setEditMode(EditMode.ABANDON);
                        
                        commandResult = securityService.editSecurityRoleGroup(initialDataParser.getUserVisit(), editForm);

                        if(commandResult.hasErrors()) {
                            getLogger().error(commandResult.toString());
                        }
                    }
                }
            }
            
            initialDataParser.pushHandler(new SecurityRoleGroupHandler(initialDataParser, this, spec.getSecurityRoleGroupName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("securityRoleGroups")) {
            initialDataParser.popHandler();
        }
    }
    
}
