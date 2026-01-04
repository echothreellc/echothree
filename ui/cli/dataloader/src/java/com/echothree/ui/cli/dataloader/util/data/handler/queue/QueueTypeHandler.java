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

package com.echothree.ui.cli.dataloader.util.data.handler.queue;

import com.echothree.control.user.queue.common.QueueUtil;
import com.echothree.control.user.queue.common.QueueService;
import com.echothree.control.user.queue.common.form.QueueFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class QueueTypeHandler
        extends BaseHandler {
    
    QueueService queueService;
    String queueTypeName;
    
    /** Creates a new instance of QueueTypeHandler */
    public QueueTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String queueTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            queueService = QueueUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.queueTypeName = queueTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("queueTypeDescription")) {
            var commandForm = QueueFormFactory.getCreateQueueTypeDescriptionForm();

            commandForm.setQueueTypeName(queueTypeName);
            commandForm.set(getAttrsMap(attrs));

            queueService.createQueueTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("queueType")) {
            initialDataParser.popHandler();
        }
    }
    
}
