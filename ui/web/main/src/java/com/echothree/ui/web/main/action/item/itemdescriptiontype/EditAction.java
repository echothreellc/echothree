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

package com.echothree.ui.web.main.action.item.itemdescriptiontype;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.edit.ItemDescriptionTypeEdit;
import com.echothree.control.user.item.common.form.EditItemDescriptionTypeForm;
import com.echothree.control.user.item.common.result.EditItemDescriptionTypeResult;
import com.echothree.control.user.item.common.spec.ItemDescriptionTypeSpec;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
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
    path = "/Item/ItemDescriptionType/Edit",
    mappingClass = SecureActionMapping.class,
    name = "ItemDescriptionTypeEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Item/ItemDescriptionType/Main", redirect = true),
        @SproutForward(name = "Form", path = "/item/itemdescriptiontype/edit/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, ItemDescriptionTypeSpec, ItemDescriptionTypeEdit, EditItemDescriptionTypeForm, EditItemDescriptionTypeResult> {
    
    @Override
    protected ItemDescriptionTypeSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = ItemUtil.getHome().getItemDescriptionTypeUniversalSpec();
        
        spec.setItemDescriptionTypeName(findParameter(request, ParameterConstants.ORIGINAL_ITEM_DESCRIPTION_TYPE_NAME, actionForm.getOriginalItemDescriptionTypeName()));
        
        return spec;
    }
    
    @Override
    protected ItemDescriptionTypeEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = ItemUtil.getHome().getItemDescriptionTypeEdit();
        var mimeTypeUsageTypeName = actionForm.getMimeTypeUsageTypeName();

        edit.setItemDescriptionTypeName(actionForm.getItemDescriptionTypeName());
        edit.setParentItemDescriptionTypeName(actionForm.getParentItemDescriptionTypeChoice());
        edit.setUseParentIfMissing(actionForm.getUseParentIfMissing().toString());
        edit.setCheckContentWebAddress(actionForm.getCheckContentWebAddress().toString());
        edit.setIncludeInIndex(actionForm.getIncludeInIndex().toString());
        edit.setIndexDefault(actionForm.getIndexDefault().toString());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());
        edit.setDescription(actionForm.getDescription());

        if(mimeTypeUsageTypeName != null) {
            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                edit.setMinimumHeight(actionForm.getMinimumHeight());
                edit.setMinimumWidth(actionForm.getMinimumWidth());
                edit.setMaximumHeight(actionForm.getMaximumHeight());
                edit.setMaximumWidth(actionForm.getMaximumWidth());
                edit.setPreferredHeight(actionForm.getPreferredHeight());
                edit.setPreferredWidth(actionForm.getPreferredWidth());
                edit.setPreferredMimeTypeName(actionForm.getPreferredMimeTypeChoice());
                edit.setQuality(actionForm.getQuality());
                edit.setScaleFromParent(actionForm.getScaleFromParent().toString());
            }
        }

        return edit;
    }
    
    @Override
    protected EditItemDescriptionTypeForm getForm()
            throws NamingException {
        return ItemUtil.getHome().getEditItemDescriptionTypeForm();
    }
    
    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditItemDescriptionTypeResult result, ItemDescriptionTypeSpec spec, ItemDescriptionTypeEdit edit) {
        var mimeTypeUsageType = result.getItemDescriptionType().getMimeTypeUsageType();
        var mimeTypeUsageTypeName = mimeTypeUsageType == null ? null : mimeTypeUsageType.getMimeTypeUsageTypeName();

        actionForm.setOriginalItemDescriptionTypeName(spec.getItemDescriptionTypeName());
        actionForm.setItemDescriptionTypeName(edit.getItemDescriptionTypeName());
        actionForm.setParentItemDescriptionTypeChoice(edit.getParentItemDescriptionTypeName());
        actionForm.setUseParentIfMissing(Boolean.valueOf(edit.getUseParentIfMissing()));
        actionForm.setCheckContentWebAddress(Boolean.valueOf(edit.getCheckContentWebAddress()));
        actionForm.setIncludeInIndex(Boolean.valueOf(edit.getIncludeInIndex()));
        actionForm.setIndexDefault(Boolean.valueOf(edit.getIndexDefault()));
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
        actionForm.setDescription(edit.getDescription());

        if(mimeTypeUsageTypeName != null) {
            actionForm.setMimeTypeUsageTypeName(mimeTypeUsageTypeName);

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                actionForm.setMinimumHeight(edit.getMinimumHeight());
                actionForm.setMinimumWidth(edit.getMinimumWidth());
                actionForm.setMaximumHeight(edit.getMaximumHeight());
                actionForm.setMaximumWidth(edit.getMaximumWidth());
                actionForm.setPreferredHeight(edit.getPreferredHeight());
                actionForm.setPreferredWidth(edit.getPreferredWidth());
                actionForm.setPreferredMimeTypeChoice(edit.getPreferredMimeTypeName());
                actionForm.setQuality(edit.getQuality());
                actionForm.setScaleFromParent(Boolean.valueOf(edit.getScaleFromParent()));
            }
        }
    }
    
    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditItemDescriptionTypeForm commandForm)
            throws Exception {
        return ItemUtil.getHome().editItemDescriptionType(getUserVisitPK(request), commandForm);
    }
    
    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditItemDescriptionTypeResult result) {
        request.setAttribute(AttributeConstants.ITEM_DESCRIPTION_TYPE, result.getItemDescriptionType());
    }

}
