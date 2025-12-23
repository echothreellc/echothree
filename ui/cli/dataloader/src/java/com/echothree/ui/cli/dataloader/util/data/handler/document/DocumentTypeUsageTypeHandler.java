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

package com.echothree.ui.cli.dataloader.util.data.handler.document;

import com.echothree.control.user.document.common.DocumentUtil;
import com.echothree.control.user.document.common.DocumentService;
import com.echothree.control.user.document.common.form.DocumentFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DocumentTypeUsageTypeHandler
        extends BaseHandler {

    DocumentService documentService;
    String documentTypeUsageTypeName;

    /** Creates a new instance of DocumentTypeUsageTypeHandler */
    public DocumentTypeUsageTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String documentTypeUsageTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            documentService = DocumentUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.documentTypeUsageTypeName = documentTypeUsageTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("documentTypeUsageTypeDescription")) {
            var commandForm = DocumentFormFactory.getCreateDocumentTypeUsageTypeDescriptionForm();

            commandForm.setDocumentTypeUsageTypeName(documentTypeUsageTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(documentService.createDocumentTypeUsageTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("partyTypeDocumentTypeUsageType")) {
            var commandForm = DocumentFormFactory.getCreatePartyTypeDocumentTypeUsageTypeForm();

            commandForm.setDocumentTypeUsageTypeName(documentTypeUsageTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(documentService.createPartyTypeDocumentTypeUsageType(initialDataParser.getUserVisit(), commandForm));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("documentTypeUsageType")) {
            initialDataParser.popHandler();
        }
    }

}
