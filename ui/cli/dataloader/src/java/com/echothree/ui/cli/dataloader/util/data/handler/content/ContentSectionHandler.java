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
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContentSectionHandler
        extends BaseHandler {
    ContentService contentService;
    String contentCollectionName;
    String contentSectionName;
    
    /** Creates a new instance of ContentSectionHandler */
    public ContentSectionHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contentCollectionName, String contentSectionName) {
        super(initialDataParser, parentHandler);
        
        try {
            contentService = ContentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.contentCollectionName = contentCollectionName;
        this.contentSectionName = contentSectionName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentSectionDescription")) {
            var createContentSectionDescriptionForm = ContentFormFactory.getCreateContentSectionDescriptionForm();

            createContentSectionDescriptionForm.setContentCollectionName(contentCollectionName);
            createContentSectionDescriptionForm.setContentSectionName(contentSectionName);
            createContentSectionDescriptionForm.set(getAttrsMap(attrs));

            contentService.createContentSectionDescription(initialDataParser.getUserVisit(), createContentSectionDescriptionForm);
        } else if(localName.equals("contentPage")) {
            var createContentPageForm = ContentFormFactory.getCreateContentPageForm();

            createContentPageForm.setContentCollectionName(contentCollectionName);
            createContentPageForm.setContentSectionName(contentSectionName);
            createContentPageForm.set(getAttrsMap(attrs));

            contentService.createContentPage(initialDataParser.getUserVisit(), createContentPageForm);

            initialDataParser.pushHandler(new ContentPageHandler(initialDataParser, this, contentCollectionName,
                    contentSectionName, createContentPageForm.getContentPageName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentSection")) {
            initialDataParser.popHandler();
        }
    }
    
}
