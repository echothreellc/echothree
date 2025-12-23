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

public class PeriodKindsHandler
        extends BaseHandler {

    PeriodService periodService;

    /** Creates a new instance of PeriodKindsHandler */
    public PeriodKindsHandler(InitialDataParser initialDataParser, BaseHandler parentHandler)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            periodService = PeriodUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("periodKind")) {
            var commandForm = PeriodFormFactory.getCreatePeriodKindForm();

            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(periodService.createPeriodKind(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new PeriodKindHandler(initialDataParser, this, commandForm.getPeriodKindName()));
        } else if(localName.equals("fiscalYear")) {
            var commandForm = PeriodFormFactory.getCreateFiscalYearForm();

            commandForm.set(getAttrsMap(attrs));

            periodService.createFiscalYear(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("periodKinds")) {
            initialDataParser.popHandler();
        }
    }

}
