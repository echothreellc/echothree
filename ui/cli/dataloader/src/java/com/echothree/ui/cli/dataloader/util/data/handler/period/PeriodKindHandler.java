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

package com.echothree.ui.cli.dataloader.util.data.handler.period;

import com.echothree.control.user.period.common.PeriodUtil;
import com.echothree.control.user.period.common.PeriodService;
import com.echothree.control.user.period.common.form.PeriodFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PeriodKindHandler
        extends BaseHandler {

    PeriodService periodService;
    String periodKindName;

    /** Creates a new instance of PeriodKindHandler */
    public PeriodKindHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String periodKindName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            periodService = PeriodUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.periodKindName = periodKindName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("periodKindDescription")) {
            var commandForm = PeriodFormFactory.getCreatePeriodKindDescriptionForm();

            commandForm.setPeriodKindName(periodKindName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(periodService.createPeriodKindDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("periodType")) {
            var commandForm = PeriodFormFactory.getCreatePeriodTypeForm();

            commandForm.setPeriodKindName(periodKindName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(periodService.createPeriodType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new PeriodTypeHandler(initialDataParser, this, periodKindName, commandForm.getPeriodTypeName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("periodKind")) {
            initialDataParser.popHandler();
        }
    }

}
