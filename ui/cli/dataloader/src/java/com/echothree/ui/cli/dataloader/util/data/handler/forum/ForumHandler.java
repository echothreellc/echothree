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

package com.echothree.ui.cli.dataloader.util.data.handler.forum;

import com.echothree.control.user.forum.common.ForumUtil;
import com.echothree.control.user.forum.common.ForumService;
import com.echothree.control.user.forum.common.form.ForumFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
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
            var commandForm = ForumFormFactory.getCreateForumDescriptionForm();

            commandForm.setForumName(forumName);
            commandForm.set(getAttrsMap(attrs));

            forumService.createForumDescription(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumGroupForum")) {
            var commandForm = ForumFormFactory.getCreateForumGroupForumForm();

            commandForm.setForumName(forumName);
            commandForm.set(getAttrsMap(attrs));

            forumService.createForumGroupForum(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumMimeType")) {
            var commandForm = ForumFormFactory.getCreateForumMimeTypeForm();

            commandForm.setForumName(forumName);
            commandForm.set(getAttrsMap(attrs));

            forumService.createForumMimeType(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumPartyRole")) {
            var commandForm = ForumFormFactory.getCreateForumPartyRoleForm();

            commandForm.setForumName(forumName);
            commandForm.set(getAttrsMap(attrs));

            forumService.createForumPartyRole(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("forumPartyTypeRole")) {
            var commandForm = ForumFormFactory.getCreateForumPartyTypeRoleForm();

            commandForm.setForumName(forumName);
            commandForm.set(getAttrsMap(attrs));

            forumService.createForumPartyTypeRole(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("blogEntry")) {
            var commandForm = ForumFormFactory.getCreateBlogEntryForm();

            commandForm.setForumName(forumName);
            commandForm.set(getAttrsMap(attrs));

            var commandResult = forumService.createBlogEntry(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = executionResult.getResult();

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