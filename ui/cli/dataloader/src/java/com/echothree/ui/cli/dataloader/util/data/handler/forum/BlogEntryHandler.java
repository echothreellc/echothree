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
import com.echothree.control.user.forum.common.result.CreateBlogCommentResult;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.core.EntityAttributesHandler;
import com.echothree.ui.cli.dataloader.util.data.handler.tag.EntityTagsHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class BlogEntryHandler
        extends BaseHandler {

    ForumService forumService;
    String forumMessageName;
    String entityRef;

    /** Creates a new instance of BlogEntryHandler */
    public BlogEntryHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String forumMessageName, String entityRef)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            forumService = ForumUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.forumMessageName = forumMessageName;
        this.entityRef = entityRef;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("blogComment")) {
            var commandForm = ForumFormFactory.getCreateBlogCommentForm();
            var attrsMap = getAttrsMap(attrs);

            commandForm.setParentForumMessageName(forumMessageName);
            commandForm.set(attrsMap);

            var commandResult = forumService.createBlogComment(initialDataParser.getUserVisit(), commandForm);

            if(!commandResult.hasErrors()) {
                var executionResult = commandResult.getExecutionResult();
                var result = (CreateBlogCommentResult)executionResult.getResult();

                initialDataParser.pushHandler(new BlogEntryHandler(initialDataParser, this, result.getForumMessageName(), result.getEntityRef()));
            }
        } else if(localName.equals("entityAttributes")) {
            initialDataParser.pushHandler(new EntityAttributesHandler(initialDataParser, this, entityRef));
        } else if(localName.equals("entityTags")) {
            initialDataParser.pushHandler(new EntityTagsHandler(initialDataParser, this, entityRef));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("blogEntry") || localName.equals("blogComment")) {
            initialDataParser.popHandler();
        }
    }
}