// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.ForumService;
import com.echothree.control.user.forum.common.form.CreateBlogEntryForm;
import com.echothree.control.user.forum.common.form.CreateForumDescriptionForm;
import com.echothree.control.user.forum.common.form.CreateForumGroupForumForm;
import com.echothree.control.user.forum.common.form.CreateForumMimeTypeForm;
import com.echothree.control.user.forum.common.form.CreateForumPartyRoleForm;
import com.echothree.control.user.forum.common.form.CreateForumPartyTypeRoleForm;
import com.echothree.control.user.forum.common.form.ForumFormFactory;
import com.echothree.control.user.forum.common.result.CreateBlogEntryResult;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import java.util.Map;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ForumHandler
        extends BaseHandler {

    ForumService forumService;
    String forumName;

    /** Creates a new instance of ForumHandler */
    public ForumHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String forumName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            forumService = ForumUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.forumName = forumName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("forumDescription")) {
            CreateForumDescriptionForm commandForm = ForumFormFactory.getCreateForumDescriptionForm();
            Map<String, Object> attrsMap = getAttrsMap(attrs);

            commandForm.setForumName(forumName);
            commandForm.set(attrsMap);

            forumService.createForumDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumGroupForum")) {
            CreateForumGroupForumForm commandForm = ForumFormFactory.getCreateForumGroupForumForm();
            Map<String, Object> attrsMap = getAttrsMap(attrs);

            commandForm.setForumName(forumName);
            commandForm.set(attrsMap);

            forumService.createForumGroupForum(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumMimeType")) {
            CreateForumMimeTypeForm commandForm = ForumFormFactory.getCreateForumMimeTypeForm();
            Map<String, Object> attrsMap = getAttrsMap(attrs);

            commandForm.setForumName(forumName);
            commandForm.set(attrsMap);

            forumService.createForumMimeType(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumPartyRole")) {
            CreateForumPartyRoleForm commandForm = ForumFormFactory.getCreateForumPartyRoleForm();
            Map<String, Object> attrsMap = getAttrsMap(attrs);

            commandForm.setForumName(forumName);
            commandForm.set(attrsMap);

            forumService.createForumPartyRole(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumPartyTypeRole")) {
            CreateForumPartyTypeRoleForm commandForm = ForumFormFactory.getCreateForumPartyTypeRoleForm();
            Map<String, Object> attrsMap = getAttrsMap(attrs);

            commandForm.setForumName(forumName);
            commandForm.set(attrsMap);

            forumService.createForumPartyTypeRole(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("blogEntry")) {
            CreateBlogEntryForm commandForm = ForumFormFactory.getCreateBlogEntryForm();
            Map<String, Object> attrsMap = getAttrsMap(attrs);

            commandForm.setForumName(forumName);
            commandForm.set(attrsMap);

            CommandResult commandResult = forumService.createBlogEntry(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                ExecutionResult executionResult = commandResult.getExecutionResult();
                CreateBlogEntryResult result = (CreateBlogEntryResult)executionResult.getResult();

                initialDataParser.pushHandler(new BlogEntryHandler(initialDataParser, this, result.getForumMessageName(), result.getEntityRef()));
            }
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("forum")) {
            initialDataParser.popHandler();
        }
    }
}