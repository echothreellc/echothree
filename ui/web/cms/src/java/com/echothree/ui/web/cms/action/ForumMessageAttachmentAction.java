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

package com.echothree.ui.web.cms.action;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.form.GetForumMessageAttachmentForm;
import com.echothree.control.user.forum.common.result.GetForumMessageAttachmentResult;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.common.transfer.EntityAttributeTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumMessageAttachmentTransfer;
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
import java.util.Set;
import javax.enterprise.inject.spi.Unmanaged;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.infinispan.Cache;

@SproutAction(
    path = "/ForumMessageAttachment",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "any")
    }
)
public class ForumMessageAttachmentAction
        extends CmsBaseDownloadAction {

    private static Unmanaged<CmsCacheBean> unmanagedCmsCacheBean = new Unmanaged<>(CmsCacheBean.class);

    private static final String fqnCms = "/com/echothree/ui/web/cms/ForumMessageAttachments";
    private static final String cacheTransferObject = "TransferObject";
    private static final String attributeTransferObject = "TransferObject";

    /** Creates a new instance of ForumMessageAttachmentAction */
    public ForumMessageAttachmentAction() {
        super(false, false);
    }

    protected static class ForumMessageAttachmentNames {

        public String forumMessageName;
        public String forumMessageAttachmentSequence;

        public ForumMessageAttachmentNames(HttpServletRequest request) {
            forumMessageName = request.getParameter(ParameterConstants.FORUM_MESSAGE_NAME);
            forumMessageAttachmentSequence = request.getParameter(ParameterConstants.FORUM_MESSAGE_ATTACHMENT_SEQUENCE);
        }

        public boolean hasAllNames() {
            return forumMessageName != null && forumMessageAttachmentSequence != null;
        }

    }

    protected ForumMessageAttachmentTransfer getForumMessageAttachment(HttpServletRequest request, ForumMessageAttachmentNames forumMessageAttachmentNames,
            Set<String> options)
            throws NamingException {
        GetForumMessageAttachmentForm commandForm = ForumUtil.getHome().getGetForumMessageAttachmentForm();
        ForumMessageAttachmentTransfer forumMessageAttachment = null;

        commandForm.setForumMessageName(forumMessageAttachmentNames.forumMessageName);
        commandForm.setForumMessageAttachmentSequence(forumMessageAttachmentNames.forumMessageAttachmentSequence);
        commandForm.setReferrer(request.getHeader("Referer"));
        
        commandForm.setOptions(options);

        CommandResult commandResult = ForumUtil.getHome().getForumMessageAttachment(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetForumMessageAttachmentResult result = (GetForumMessageAttachmentResult)executionResult.getResult();

            forumMessageAttachment = result.getForumMessageAttachment();
        }

        return forumMessageAttachment;
    }

    private String getFqn(ForumMessageAttachmentNames forumMessageAttachmentNames) {
        // Build the Fqn from the most general component, to the most specific component. When the maximum cache size is exceeded,
        // it will prune the last component's contents, and that portion of the fqn off the tree. The first two components will
        // continue to exist.
        return new StringBuilder(fqnCms).append('/')
                .append(forumMessageAttachmentNames.forumMessageName.toLowerCase()).append('-')
                .append(forumMessageAttachmentNames.forumMessageAttachmentSequence.toLowerCase()).toString();
    }

    private ForumMessageAttachmentTransfer getCachedForumMessageAttachment(Cache<String, Object> cache, String fqn) {
        ForumMessageAttachmentTransfer forumMessageAttachment = null;

        if(cache != null && fqn != null) {
            forumMessageAttachment = (ForumMessageAttachmentTransfer)cache.get(fqn + "/" + cacheTransferObject);
        }

        return forumMessageAttachment;
    }

    private void putCachedForumMessageAttachment(Cache<String, Object> cache, String fqn, ForumMessageAttachmentTransfer forumMessageAttachment) {
        if(cache != null && fqn != null) {
            cache.put(fqn + "/" + cacheTransferObject, forumMessageAttachment);
        }
    }

    @Override
    protected String getETag(HttpServletRequest request)
            throws NamingException {
        ForumMessageAttachmentNames forumMessageAttachmentNames = new ForumMessageAttachmentNames(request);
        String eTag = null;

        if(forumMessageAttachmentNames.hasAllNames()) {
            Unmanaged.UnmanagedInstance<CmsCacheBean> cmsCacheBeanInstance = unmanagedCmsCacheBean.newInstance();
            CmsCacheBean cmsCacheBean = cmsCacheBeanInstance.produce().inject().postConstruct().get();
            Cache<String, Object> cache = cmsCacheBean.getCache();
            String fqn = getFqn(forumMessageAttachmentNames);
            ForumMessageAttachmentTransfer forumMessageAttachment = getCachedForumMessageAttachment(cache, fqn);

            if(forumMessageAttachment == null) {
                Set<String> options = new HashSet<>();
                options.add(ForumOptions.ForumMessageAttachmentIncludeBlob);
                options.add(ForumOptions.ForumMessageAttachmentIncludeClob);
                options.add(ForumOptions.ForumMessageAttachmentIncludeETag);

                forumMessageAttachment = getForumMessageAttachment(request, forumMessageAttachmentNames, options);
            }

            if(forumMessageAttachment != null) {
                putCachedForumMessageAttachment(cache, fqn, forumMessageAttachment);
                request.setAttribute(attributeTransferObject, forumMessageAttachment);

                eTag = forumMessageAttachment.geteTag();
            }

            cmsCacheBeanInstance.preDestroy().dispose();
        }

        return eTag;
    }

    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        StreamInfo streamInfo = null;
        ForumMessageAttachmentTransfer forumMessageAttachment = (ForumMessageAttachmentTransfer)request.getAttribute(attributeTransferObject);

        if(forumMessageAttachment != null) {
            MimeTypeTransfer mimeType = forumMessageAttachment.getMimeType();
            EntityAttributeTypeTransfer entityAttributeType = mimeType == null ? null : mimeType.getEntityAttributeType();
            String entityAttributeTypeName = entityAttributeType == null ? null : entityAttributeType.getEntityAttributeTypeName();
            EntityTimeTransfer entityTime = forumMessageAttachment.getForumMessage().getEntityInstance().getEntityTime();
            Long modifiedTime = entityTime.getUnformattedModifiedTime();
            byte bytes[] = null;

            if(EntityAttributeTypes.CLOB.name().equals(entityAttributeTypeName)) {
                bytes = forumMessageAttachment.getClob().getBytes(Charsets.UTF_8);
            } else {
                if(EntityAttributeTypes.BLOB.name().equals(entityAttributeTypeName)) {
                    bytes = forumMessageAttachment.getBlob().byteArrayValue();
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
