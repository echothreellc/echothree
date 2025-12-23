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

package com.echothree.ui.web.main.action.core.entitytype;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EntityTypeEdit;
import com.echothree.control.user.core.common.form.EditEntityTypeForm;
import com.echothree.control.user.core.common.result.EditEntityTypeResult;
import com.echothree.control.user.core.common.spec.EntityTypeSpec;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Core/EntityType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EntityTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entitytype/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, EntityTypeSpec, EntityTypeEdit, EditEntityTypeForm, EditEntityTypeResult> {
    
    @Override
    protected EntityTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEntityTypeSpec();
        
        spec.setComponentVendorName(findParameter(request, ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName()));
        spec.setEntityTypeName(findParameter(request, ParameterConstants.ORIGINAL_ENTITY_TYPE_NAME, actionForm.getOriginalEntityTypeName()));
        
        return spec;
    }
    
    @Override
    protected EntityTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEntityTypeEdit();

        edit.setEntityTypeName(actionForm.getEntityTypeName());
        edit.setKeepAllHistory(actionForm.getKeepAllHistory().toString());
        edit.setLockTimeout(actionForm.getLockTimeout());
        edit.setLockTimeoutUnitOfMeasureTypeName(actionForm.getLockTimeoutUnitOfMeasureTypeChoice());
        edit.setIsExtensible(actionForm.getIsExtensible().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEntityTypeForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEntityTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditEntityTypeResult result, EntityTypeSpec spec, EntityTypeEdit edit) {
        actionForm.setComponentVendorName(spec.getComponentVendorName());
        actionForm.setOriginalEntityTypeName(spec.getEntityTypeName());
        actionForm.setEntityTypeName(edit.getEntityTypeName());
        actionForm.setKeepAllHistory(Boolean.valueOf(edit.getKeepAllHistory()));
        actionForm.setLockTimeout(edit.getLockTimeout());
        actionForm.setLockTimeoutUnitOfMeasureTypeChoice(edit.getLockTimeoutUnitOfMeasureTypeName());
        actionForm.setIsExtensible(Boolean.valueOf(edit.getIsExtensible()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEntityTypeForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEntityType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    public void setupForwardParameters(EditActionForm actionForm, Map<String, String> parameters) {
        parameters.put(ParameterConstants.COMPONENT_VENDOR_NAME, actionForm.getComponentVendorName());
    }
    
}
