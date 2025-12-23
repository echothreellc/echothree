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

package com.echothree.ui.web.main.action.advertising.source;

import com.echothree.control.user.offer.common.OfferUtil;
import com.echothree.control.user.offer.common.edit.SourceEdit;
import com.echothree.control.user.offer.common.form.EditSourceForm;
import com.echothree.control.user.offer.common.result.EditSourceResult;
import com.echothree.control.user.offer.common.spec.SourceSpec;
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
    path = "/Advertising/Source/Edit",
    mappingClass = SecureActionMapping.class,
    name = "SourceEdit",
    properties = {
        @SproutProperty(property = "secure", value = "true")
    },
    forwards = {
        @SproutForward(name = "Display", path = "/action/Advertising/Source/Main", redirect = true),
        @SproutForward(name = "Form", path = "/advertising/source/edit.jsp")
    }
)
public class EditAction
        extends MainBaseEditAction<EditActionForm, SourceSpec, SourceEdit, EditSourceForm, EditSourceResult> {

    @Override
    protected SourceSpec getSpec(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var spec = OfferUtil.getHome().getSourceSpec();

        spec.setSourceName(findParameter(request, ParameterConstants.ORIGINAL_SOURCE_NAME, actionForm.getOriginalSourceName()));

        return spec;
    }

    @Override
    protected SourceEdit getEdit(HttpServletRequest request, EditActionForm actionForm)
            throws NamingException {
        var edit = OfferUtil.getHome().getSourceEdit();

        edit.setSourceName(actionForm.getSourceName());
        edit.setIsDefault(actionForm.getIsDefault().toString());
        edit.setSortOrder(actionForm.getSortOrder());

        return edit;
    }

    @Override
    protected EditSourceForm getForm()
            throws NamingException {
        return OfferUtil.getHome().getEditSourceForm();
    }

    @Override
    protected void setupActionForm(HttpServletRequest request, EditActionForm actionForm, EditSourceResult result, SourceSpec spec, SourceEdit edit) {
        actionForm.setOriginalSourceName(spec.getSourceName());
        actionForm.setSourceName(edit.getSourceName());
        actionForm.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        actionForm.setSortOrder(edit.getSortOrder());
    }

    @Override
    protected CommandResult doEdit(HttpServletRequest request, EditSourceForm commandForm)
            throws Exception {
        return OfferUtil.getHome().editSource(getUserVisitPK(request), commandForm);
    }

    @Override
    protected void setupTransferForForm(HttpServletRequest request, EditActionForm actionForm, EditSourceResult result) {
        request.setAttribute(AttributeConstants.SOURCE, result.getSource());
    }

}
