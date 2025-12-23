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

package com.echothree.ui.web.main.action.core.entityattributegroup;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.edit.EntityAttributeGroupEdit;
import com.echothree.control.user.core.common.form.EditEntityAttributeGroupForm;
import com.echothree.control.user.core.common.result.EditEntityAttributeGroupResult;
import com.echothree.control.user.core.common.spec.EntityAttributeGroupSpec;
import com.echothree.ui.web.main.framework.AttributeConstants;
import com.echothree.ui.web.main.framework.MainBaseEditAction;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForward;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

@SproutAction(
    path = "/Core/EntityAttributeGroup/Edit",
    mappingClass = SecureActionMapping.class,
    name = "EntityAttributeGroupEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Core/EntityAttributeGroup/Main", redirect = true),
        @SproutForward(name = "Form", path = "/core/entityattributegroup/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, EntityAttributeGroupSpec, EntityAttributeGroupEdit, EditEntityAttributeGroupForm, EditEntityAttributeGroupResult> {
    
    @Override
    protected EntityAttributeGroupSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = CoreUtil.getHome().getEntityAttributeGroupSpec();
        
        spec.setEntityAttributeGroupName(findParameter(request, ParameterConstants.ORIGINAL_ENTITY_ATTRIBUTE_GROUP_NAME, actionForm.getOriginalEntityAttributeGroupName()));
        
        return spec;
    }
    
    @Override
    protected EntityAttributeGroupEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = CoreUtil.getHome().getEntityAttributeGroupEdit();

        edit.setEntityAttributeGroupName(actionForm.getEntityAttributeGroupName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        return edit;
    }
    
    @Override
    protected EditEntityAttributeGroupForm getForm()
            throws NamingException {
        return CoreUtil.getHome().getEditEntityAttributeGroupForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditEntityAttributeGroupResult result, EntityAttributeGroupSpec spec, EntityAttributeGroupEdit edit) {
        actionForm.setOriginalEntityAttributeGroupName(spec.getEntityAttributeGroupName());
        actionForm.setEntityAttributeGroupName(edit.getEntityAttributeGroupName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditEntityAttributeGroupForm commandForm)
            throws Exception {
        return CoreUtil.getHome().editEntityAttributeGroup(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditEntityAttributeGroupResult result) {
        request.setAttribute(AttributeConstants.ENTITY_ATTRIBUTE_GROUP, result.getEntityAttributeGroup());
    }

}
