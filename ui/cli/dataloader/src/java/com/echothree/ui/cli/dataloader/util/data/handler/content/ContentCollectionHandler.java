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

package com.echothree.ui.cli.dataloader.util.data.handler.content;

import com.echothree.control.user.content.common.ContentUtil;
import com.echothree.control.user.content.common.ContentService;
import com.echothree.control.user.content.common.form.ContentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContentCollectionHandler
        extends BaseHandler {

    ContentService contentService;
    String contentCollectionName;
    
    /** Creates a new instance of ContentCollectionHandler */
    public ContentCollectionHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contentCollectionName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        contentService = lookupService(ContentService.class, ContentUtil::getHome);
        
        this.contentCollectionName = contentCollectionName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentCollectionDescription")) {
            var commandForm = ContentFormFactory.getCreateContentCollectionDescriptionForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, contentService.createContentCollectionDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("contentSection")) {
            var commandForm = ContentFormFactory.getCreateContentSectionForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, contentService.createContentSection(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new ContentSectionHandler(initialDataParser, this, contentCollectionName,
                    commandForm.getContentSectionName()));
        } else if(localName.equals("contentCatalog")) {
            var commandForm = ContentFormFactory.getCreateContentCatalogForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, contentService.createContentCatalog(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new ContentCatalogHandler(initialDataParser, this, contentCollectionName,
                    commandForm.getContentCatalogName()));
        } else if(localName.equals("contentForum")) {
            var commandForm = ContentFormFactory.getCreateContentForumForm();

            commandForm.setContentCollectionName(contentCollectionName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(commandForm, contentService.createContentForum(initialDataParser.getUserVisit(), commandForm));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentCollection")) {
            initialDataParser.popHandler();
        }
    }
    
}
