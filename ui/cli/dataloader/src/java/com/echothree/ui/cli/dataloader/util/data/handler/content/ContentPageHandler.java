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

public class ContentPageHandler
        extends BaseHandler {
    ContentService contentService;
    String contentCollectionName;
    String contentSectionName;
    String contentPageName;
    
    boolean inContentPageArea = false;
    String sortOrder = null;
    String contentPageAreaUrl = null;
    String languageIsoName = null;
    String description = null;
    String mimeTypeName = null;
    char []contentPageAreaClob = null;
    
    /** Creates a new instance of ContentPageHandler */
    public ContentPageHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String contentCollectionName, String contentSectionName,
    String contentPageName) {
        super(initialDataParser, parentHandler);
        
        try {
            contentService = ContentUtil.getHome();
        } catch (NamingException ne) {
            // TODO: Handle Exception
        }
        
        this.contentCollectionName = contentCollectionName;
        this.contentSectionName = contentSectionName;
        this.contentPageName = contentPageName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("contentPageDescription")) {
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
                var createContentPageDescriptionForm = ContentFormFactory.getCreateContentPageDescriptionForm();
                
                createContentPageDescriptionForm.setContentCollectionName(contentCollectionName);
                createContentPageDescriptionForm.setContentSectionName(contentSectionName);
                createContentPageDescriptionForm.setContentPageName(contentPageName);
                createContentPageDescriptionForm.setLanguageIsoName(languageIsoName);
                createContentPageDescriptionForm.setDescription(description);
                
                contentService.createContentPageDescription(initialDataParser.getUserVisit(), createContentPageDescriptionForm);
            } catch (Exception e) {
                throw new SAXException(e);
            }
        } else if(localName.equals("contentPageArea")) {
            var count = attrs.getLength();
            for(var i = 0; i < count; i++) {
                if(attrs.getQName(i).equals("sortOrder"))
                    sortOrder = attrs.getValue(i);
                else if(attrs.getQName(i).equals("contentPageAreaUrl"))
                    contentPageAreaUrl = attrs.getValue(i);
                else if(attrs.getQName(i).equals("languageIsoName"))
                    languageIsoName = attrs.getValue(i);
                else if(attrs.getQName(i).equals("description"))
                    description = attrs.getValue(i);
                else if(attrs.getQName(i).equals("mimeTypeName"))
                    mimeTypeName = attrs.getValue(i);
            }
            
            inContentPageArea = true;
        }
    }
    
    @Override
    public void characters(char ch[], int start, int length)
    throws SAXException {
        if(inContentPageArea) {
            var oldLength = contentPageAreaClob != null? contentPageAreaClob.length: 0;
            var newClob = new char[oldLength + length];
            
            if(contentPageAreaClob != null)
                System.arraycopy(contentPageAreaClob, 0, newClob, 0, contentPageAreaClob.length);
            System.arraycopy(ch, start, newClob, oldLength, length);
            contentPageAreaClob = newClob;
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("contentPageArea")) {
            var createContentPageAreaForm = ContentFormFactory.getCreateContentPageAreaForm();
            
            createContentPageAreaForm.setContentCollectionName(contentCollectionName);
            createContentPageAreaForm.setContentSectionName(contentSectionName);
            createContentPageAreaForm.setContentPageName(contentPageName);
            createContentPageAreaForm.setSortOrder(sortOrder);
            createContentPageAreaForm.setContentPageAreaUrl(contentPageAreaUrl);
            createContentPageAreaForm.setLanguageIsoName(languageIsoName);
            createContentPageAreaForm.setDescription(description);
            createContentPageAreaForm.setContentPageAreaClob(contentPageAreaClob == null? null: new String(contentPageAreaClob));
            createContentPageAreaForm.setMimeTypeName(mimeTypeName);
            
            contentService.createContentPageArea(initialDataParser.getUserVisit(), createContentPageAreaForm);
            
            inContentPageArea = false;
            sortOrder = null;
            contentPageAreaUrl = null;
            languageIsoName = null;
            description = null;
            mimeTypeName = null;
            contentPageAreaClob = null;
        } else if(localName.equals("contentPage")) {
            initialDataParser.popHandler();
        }
    }
    
}
