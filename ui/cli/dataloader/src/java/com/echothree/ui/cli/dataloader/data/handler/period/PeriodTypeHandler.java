// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.dataloader.data.handler.period;

import com.echothree.control.user.period.common.PeriodUtil;
import com.echothree.control.user.period.remote.PeriodService;
import com.echothree.control.user.period.remote.form.CreatePeriodTypeDescriptionForm;
import com.echothree.control.user.period.remote.form.PeriodFormFactory;
import com.echothree.ui.cli.dataloader.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class PeriodTypeHandler
        extends BaseHandler {

    PeriodService periodService;
    String periodKindName;
    String periodTypeName;

    /** Creates a new instance of PeriodTypeHandler */
    public PeriodTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String periodKindName, String periodTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);

        try {
            periodService = PeriodUtil.getHome();
        } catch(NamingException ne) {
            throw new SAXException(ne);
        }

        this.periodKindName = periodKindName;
        this.periodTypeName = periodTypeName;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("periodTypeDescription")) {
            CreatePeriodTypeDescriptionForm commandForm = PeriodFormFactory.getCreatePeriodTypeDescriptionForm();

            commandForm.setPeriodKindName(periodKindName);
            commandForm.setPeriodTypeName(periodTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(periodService.createPeriodTypeDescription(initialDataParser.getUserVisit(), commandForm));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("periodType")) {
            initialDataParser.popHandler();
        }
    }

}
