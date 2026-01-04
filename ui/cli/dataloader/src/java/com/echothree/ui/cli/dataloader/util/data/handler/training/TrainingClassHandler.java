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

public class TrainingClassHandler
        extends BaseHandler {
    
    TrainingService trainingService;
    String trainingClassName;
    
    /** Creates a new instance of TrainingClassHandler */
    public TrainingClassHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String trainingClassName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            trainingService = TrainingUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.trainingClassName = trainingClassName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("trainingClassTranslation")) {
            var commandForm = TrainingFormFactory.getCreateTrainingClassTranslationForm();

            commandForm.setTrainingClassName(trainingClassName);
            commandForm.set(getAttrsMap(attrs));

            trainingService.createTrainingClassTranslation(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("trainingClassSection")) {
            var commandForm = TrainingFormFactory.getCreateTrainingClassSectionForm();

            commandForm.setTrainingClassName(trainingClassName);
            commandForm.set(getAttrsMap(attrs));

            trainingService.createTrainingClassSection(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new TrainingClassSectionHandler(initialDataParser, this, trainingClassName,
                    commandForm.getTrainingClassSectionName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("trainingClass")) {
            initialDataParser.popHandler();
        }
    }
    
}
