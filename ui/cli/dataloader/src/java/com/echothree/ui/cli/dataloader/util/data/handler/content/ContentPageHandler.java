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
import java.util.Map;
import java.util.Set;
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
    private Map<String, Object> contentPageAreaAttrs;
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
            var createContentPageDescriptionForm = ContentFormFactory.getCreateContentPageDescriptionForm();

            createContentPageDescriptionForm.setContentCollectionName(contentCollectionName);
            createContentPageDescriptionForm.setContentSectionName(contentSectionName);
            createContentPageDescriptionForm.setContentPageName(contentPageName);
            createContentPageDescriptionForm.set(getAttrsMap(attrs));

            contentService.createContentPageDescription(initialDataParser.getUserVisit(), createContentPageDescriptionForm);
        } else if(localName.equals("contentPageArea")) {
            contentPageAreaAttrs = getAttrsMap(attrs);
            contentPageAreaAttrs.keySet().removeAll(Set.of("ContentCollectionName", "ContentSectionName", "ContentPageName", "ContentPageAreaClob"));
            
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
            createContentPageAreaForm.setContentPageAreaClob(contentPageAreaClob == null? null: new String(contentPageAreaClob));
            createContentPageAreaForm.set(contentPageAreaAttrs);
            
            contentService.createContentPageArea(initialDataParser.getUserVisit(), createContentPageAreaForm);
            
            inContentPageArea = false;
            contentPageAreaAttrs = null;
            contentPageAreaClob = null;
        } else if(localName.equals("contentPage")) {
            initialDataParser.popHandler();
        }
    }
    
}
