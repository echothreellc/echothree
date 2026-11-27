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

public class ContentPageLayoutHandler
        extends BaseHandler {

    ContentService contentService;
    String contentPageLayoutName;
    
    /** Creates a new instance of ContentPageLayoutHandler */
    public ContentPageLayoutHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contentPageLayoutName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        contentService = lookupService(ContentService.class, ContentUtil::getHome);
        
        this.contentPageLayoutName = contentPageLayoutName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentPageLayoutDescription")) {
            var createContentPageLayoutDescriptionForm = ContentFormFactory.getCreateContentPageLayoutDescriptionForm();

            createContentPageLayoutDescriptionForm.setContentPageLayoutName(contentPageLayoutName);
            createContentPageLayoutDescriptionForm.set(getAttrsMap(attrs));

            contentService.createContentPageLayoutDescription(initialDataParser.getUserVisit(), createContentPageLayoutDescriptionForm);
        } else if(localName.equals("contentPageLayoutArea")) {
            var createContentPageLayoutAreaForm = ContentFormFactory.getCreateContentPageLayoutAreaForm();

            createContentPageLayoutAreaForm.setContentPageLayoutName(contentPageLayoutName);
            createContentPageLayoutAreaForm.set(getAttrsMap(attrs));

            contentService.createContentPageLayoutArea(initialDataParser.getUserVisit(), createContentPageLayoutAreaForm);

            initialDataParser.pushHandler(new ContentPageLayoutAreaHandler(initialDataParser, this, contentPageLayoutName,
                    createContentPageLayoutAreaForm.getSortOrder()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentPageLayout")) {
            initialDataParser.popHandler();
        }
    }
    
}
