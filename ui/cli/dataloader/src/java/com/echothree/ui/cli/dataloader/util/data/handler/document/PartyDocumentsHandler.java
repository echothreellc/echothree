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
import com.echothree.util.common.persistence.type.ByteArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PartyDocumentsHandler
        extends BaseHandler {

    DocumentService documentService;
    String partyName;

    boolean inPartyDocument = false;
    Map<String, Object> createPartyDocumentMap = null;
    char []clob = null;

    /** Creates a new instance of PartyDocumentsHandler */
    public PartyDocumentsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String partyName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            documentService = DocumentUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.partyName = partyName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("partyDocument")) {
            createPartyDocumentMap = getAttrsMap(attrs);
            
            inPartyDocument = true;
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        if(inPartyDocument) {
            var oldLength = clob != null? clob.length: 0;
            var newClob = new char[oldLength + length];

            if(clob != null) {
                System.arraycopy(clob, 0, newClob, 0, clob.length);
            }

            System.arraycopy(ch, start, newClob, oldLength, length);

            clob = newClob;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("partyDocument")) {
            var form = DocumentFormFactory.getCreatePartyDocumentForm();

            form.setPartyName(partyName);
            form.set(createPartyDocumentMap);
            form.setClob(clob == null? null: new String(clob));

            var path = (String)createPartyDocumentMap.get("Path");
            if(path != null) {
                var file = new File(path);
                var length = file.length();

                if(length < Integer.MAX_VALUE) {
                    try {
                        InputStream is = new FileInputStream(file);
                        var bytes = new byte[(int)length];
                        var offset = 0;
                        var numRead = 0;

                        while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                            offset += numRead;
                        }

                        // Ensure all the bytes have been read in
                        if (offset < bytes.length) {
                            throw new SAXException("Could not completely read file "+file.getName());
                        }

                        // Close the input stream and return bytes
                        is.close();

                        form.setBlob(new ByteArray(bytes));
                    } catch(FileNotFoundException fnfe) {
                        throw new SAXException(fnfe);
                    } catch(IOException ioe) {
                        throw new SAXException(ioe);
                    }
                }
            }

            documentService.createPartyDocument(initialDataParser.getUserVisit(), form);

            inPartyDocument = false;
            createPartyDocumentMap = null;
            clob = null;
        } else if(localName.equals("partyDocuments")) {
            initialDataParser.popHandler();
        }
    }

}
