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

package com.echothree.ui.cli.dataloader.util.data.handler.scale;

import com.echothree.control.user.scale.common.ScaleUtil;
import com.echothree.control.user.scale.common.ScaleService;
import com.echothree.control.user.scale.common.form.ScaleFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ScaleTypeHandler
        extends BaseHandler {

    ScaleService scaleService;
    String scaleTypeName;

    /** Creates a new instance of ScaleTypeHandler */
    public ScaleTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String scaleTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            scaleService = ScaleUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.scaleTypeName = scaleTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("scaleTypeDescription")) {
            var commandForm = ScaleFormFactory.getCreateScaleTypeDescriptionForm();

            commandForm.setScaleTypeName(scaleTypeName);
            commandForm.set(getAttrsMap(attrs));

            scaleService.createScaleTypeDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("scaleType")) {
            initialDataParser.popHandler();
        }
    }

}
