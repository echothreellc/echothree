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

package com.echothree.ui.cli.dataloader.util.data.handler.training;

import com.echothree.control.user.training.common.TrainingUtil;
import com.echothree.control.user.training.common.TrainingService;
import com.echothree.control.user.training.common.form.TrainingFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class TrainingClassPageHandler
        extends BaseHandler {
    
    TrainingService trainingService;
    String trainingClassName;
    String trainingClassSectionName;
    String trainingClassPageName;
    
    /** Creates a new instance of TrainingClassPageHandler */
    public TrainingClassPageHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String trainingClassName, String trainingClassSectionName,
            String trainingClassPageName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            trainingService = TrainingUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.trainingClassName = trainingClassName;
        this.trainingClassSectionName = trainingClassSectionName;
        this.trainingClassPageName = trainingClassPageName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("trainingClassPageTranslation")) {
            var commandForm = TrainingFormFactory.getCreateTrainingClassPageTranslationForm();

            commandForm.setTrainingClassName(trainingClassName);
            commandForm.setTrainingClassSectionName(trainingClassSectionName);
            commandForm.setTrainingClassPageName(trainingClassPageName);
            commandForm.set(getAttrsMap(attrs));

            trainingService.createTrainingClassPageTranslation(initialDataParser.getUserVisit(), commandForm);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("trainingClassPage")) {
            initialDataParser.popHandler();
        }
    }
    
}
