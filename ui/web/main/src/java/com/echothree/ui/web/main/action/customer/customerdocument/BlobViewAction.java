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

package com.echothree.ui.web.main.action.customer.customerdocument;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.result.GetPartyDocumentResult;
import com.echothree.model.control.document.common.DocumentOptions;
import com.echothree.ui.web.main.framework.ByteArrayStreamInfo;
import com.echothree.ui.web.main.framework.MainBaseDownloadAction;
import com.echothree.ui.web.main.framework.MainBaseDownloadAction.StreamInfo;
import com.echothree.ui.web.main.framework.ParameterConstants;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

@SproutAction(
    path = "/Customer/CustomerDocument/BlobView",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "true")
    }
)
public class BlobViewAction
        extends MainBaseDownloadAction {
    
    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        StreamInfo streamInfo = null;
        var documentName = request.getParameter(ParameterConstants.DOCUMENT_NAME);
        var commandForm = DocumentUtil.getHome().getGetPartyDocumentForm();

        commandForm.setDocumentName(documentName);

        Set<String> options = new HashSet<>();
        options.add(DocumentOptions.DocumentIncludeBlob);
        commandForm.setOptions(options);

        var commandResult = DocumentUtil.getHome().getPartyDocument(getUserVisitPK(request), commandForm);
        var executionResult = commandResult.getExecutionResult();
        var result = (GetPartyDocumentResult)executionResult.getResult();

        var partyDocument = result.getPartyDocument();

        if(partyDocument != null) {
            var document = partyDocument.getDocument();
            var mimeType = document.getMimeType().getMimeTypeName();
            var byteArrayInputStream = new ByteArrayInputStream(document.getBlob().byteArrayValue());

            streamInfo = new ByteArrayStreamInfo(mimeType, byteArrayInputStream, null, null);
        }

        return streamInfo;
    }
    
}
