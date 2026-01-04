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
            String languageIsoName = null;
            String description = null;
            String doUpdate = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
                else if(attrs.getQName(i).equals("doUpdate"))
                    doUpdate = attrs.getValue(i);
            }
            
            try {
                var createContentSectionDescriptionForm = ContentFormFactory.getCreateContentSectionDescriptionForm();
                
                createContentSectionDescriptionForm.setContentCollectionName(contentCollectionName);
                createContentSectionDescriptionForm.setContentSectionName(contentSectionName);
                createContentSectionDescriptionForm.setLanguageIsoName(languageIsoName);
                createContentSectionDescriptionForm.setDescription(description);
                
                contentService.createContentSectionDescription(initialDataParser.getUserVisit(), createContentSectionDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("contentPage")) {
            String contentPageName = null;
            String contentPageLayoutName = null;
            String isDefault = null;
            String sortOrder = null;

            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("contentPageName"))
                    contentPageName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("contentPageLayoutName"))
                    contentPageLayoutName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("isDefault"))
                    isDefault = attrs.getValue(i);
                else if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
            }
            
            try {
                var createContentPageForm = ContentFormFactory.getCreateContentPageForm();
                
                createContentPageForm.setContentCollectionName(contentCollectionName);
                createContentPageForm.setContentSectionName(contentSectionName);
                createContentPageForm.setContentPageName(contentPageName);
                createContentPageForm.setContentPageLayoutName(contentPageLayoutName);
                createContentPageForm.setIsDefault(isDefault);
                createContentPageForm.setSortOrder(sortOrder);
                
                contentService.createContentPage(initialDataParser.getUserVisit(), createContentPageForm);
                
                initialDataParser.pushHandler(new ContentPageHandler(initialDataParser, this, contentCollectionName, contentSectionName, contentPageName));
            } catch (Exception e) {
                throw new SAXException(e);
            }
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
