// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.web.cms.action;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.form.GetPartyDocumentForm;
import com.echothree.control.user.document.common.result.GetPartyDocumentResult;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.common.transfer.EntityAttributeTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.document.common.DocumentOptions;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.common.transfer.PartyDocumentTransfer;
import com.echothree.ui.web.cms.framework.ByteArrayStreamInfo;
import com.echothree.ui.web.cms.framework.CmsBaseDownloadAction;
import com.echothree.ui.web.cms.framework.ParameterConstants;
import com.echothree.ui.web.cms.persistence.CmsCacheBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import com.google.common.base.Charsets;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.enterprise.inject.spi.Unmanaged;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.infinispan.Cache;

@SproutAction(
    path = "/PartyDocument",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "any")
    }
)
public class PartyDocumentAction
        extends CmsBaseDownloadAction {

    private static Unmanaged<CmsCacheBean> unmanagedCmsCacheBean = new Unmanaged<>(CmsCacheBean.class);

    private static final String fqnCms = "/com/echothree/ui/web/cms/PartyDocuments";
    private static final String cacheTransferObject = "TransferObject";
    private static final String attributeTransferObject = "TransferObject";

    /** Creates a new instance of PartyDocumentAction */
    public PartyDocumentAction() {
        super(false, false);
    }

    protected static class PartyDocumentNames {

        public String documentName;

        public PartyDocumentNames(HttpServletRequest request) {
            documentName = request.getParameter(ParameterConstants.DOCUMENT_NAME);
        }

        public boolean hasAllNames() {
            return documentName != null;
        }

    }

    protected PartyDocumentTransfer getPartyDocument(HttpServletRequest request, PartyDocumentNames partyDocumentNames, Set<String> options)
            throws NamingException {
        GetPartyDocumentForm commandForm = DocumentUtil.getHome().getGetPartyDocumentForm();
        PartyDocumentTransfer partyDocument = null;

        commandForm.setDocumentName(partyDocumentNames.documentName);
        commandForm.setReferrer(request.getHeader("Referer"));
        
        commandForm.setOptions(options);

        CommandResult commandResult = DocumentUtil.getHome().getPartyDocument(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetPartyDocumentResult result = (GetPartyDocumentResult)executionResult.getResult();

            partyDocument = result.getPartyDocument();
        }

        return partyDocument;
    }

    private String getFqn(PartyDocumentNames partyDocumentNames) {
        // Build the Fqn from the most general component, to the most specific component. When the maximum cache size is exceeded,
        // it will prune the last component's contents, and that portion of the fqn off the tree. The first two components will
        // continue to exist.
        return new StringBuilder(fqnCms).append('/')
                .append(partyDocumentNames.documentName.toLowerCase(Locale.getDefault())).toString();
    }

    private PartyDocumentTransfer getCachedPartyDocument(Cache<String, Object> cache, String fqn) {
        PartyDocumentTransfer partyDocument = null;

        if(cache != null && fqn != null) {
            partyDocument = (PartyDocumentTransfer)cache.get(fqn + "/" + cacheTransferObject);
        }

        return partyDocument;
    }

    private void putCachedPartyDocument(Cache<String, Object> cache, String fqn, PartyDocumentTransfer partyDocument) {
        if(cache != null && fqn != null) {
            cache.put(fqn + "/" + cacheTransferObject, partyDocument);
        }
    }

    @Override
    protected String getETag(HttpServletRequest request)
            throws NamingException {
        PartyDocumentNames partyDocumentNames = new PartyDocumentNames(request);
        String eTag = null;

        if(partyDocumentNames.hasAllNames()) {
            Unmanaged.UnmanagedInstance<CmsCacheBean> cmsCacheBeanInstance = unmanagedCmsCacheBean.newInstance();
            CmsCacheBean cmsCacheBean = cmsCacheBeanInstance.produce().inject().postConstruct().get();
            Cache<String, Object> cache = cmsCacheBean.getCache();
            String fqn = getFqn(partyDocumentNames);
            PartyDocumentTransfer partyDocument = getCachedPartyDocument(cache, fqn);

            if(partyDocument == null) {
                Set<String> options = new HashSet<>();
                options.add(DocumentOptions.DocumentIncludeBlob);
                options.add(DocumentOptions.DocumentIncludeClob);
                options.add(DocumentOptions.DocumentIncludeETag);

                partyDocument = getPartyDocument(request, partyDocumentNames, options);
            }

            if(partyDocument != null) {
                putCachedPartyDocument(cache, fqn, partyDocument);
                request.setAttribute(attributeTransferObject, partyDocument);

                eTag = partyDocument.getDocument().geteTag();
            }

            cmsCacheBeanInstance.preDestroy().dispose();
        }

        return eTag;
    }

    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        StreamInfo streamInfo = null;
        PartyDocumentTransfer partyDocument = (PartyDocumentTransfer)request.getAttribute(attributeTransferObject);

        if(partyDocument != null) {
            DocumentTransfer document = partyDocument.getDocument();
            MimeTypeTransfer mimeType = document.getMimeType();
            EntityAttributeTypeTransfer entityAttributeType = mimeType == null ? null : mimeType.getEntityAttributeType();
            String entityAttributeTypeName = entityAttributeType == null ? null : entityAttributeType.getEntityAttributeTypeName();
            EntityTimeTransfer entityTime = document.getEntityInstance().getEntityTime();
            Long modifiedTime = entityTime.getUnformattedModifiedTime();
            byte bytes[] = null;

            if(EntityAttributeTypes.CLOB.name().equals(entityAttributeTypeName)) {
                bytes = document.getClob().getBytes(Charsets.UTF_8);
            } else {
                if(EntityAttributeTypes.BLOB.name().equals(entityAttributeTypeName)) {
                    bytes = document.getBlob().byteArrayValue();
                }
            }

            if(bytes != null) {
                streamInfo = new ByteArrayStreamInfo(mimeType == null ? MimeTypes.TEXT_PLAIN.mimeTypeName() : mimeType.getMimeTypeName(),
                        new ByteArrayInputStream(bytes), modifiedTime == null ? entityTime.getUnformattedCreatedTime() : modifiedTime);
            }
        }

        return streamInfo;
    }
    
}
