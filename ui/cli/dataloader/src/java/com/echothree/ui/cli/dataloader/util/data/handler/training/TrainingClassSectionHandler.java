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

public class TrainingClassSectionHandler
        extends BaseHandler {
    
    TrainingService trainingService;
    String trainingClassName;
    String trainingClassSectionName;
    
    /** Creates a new instance of TrainingClassSectionHandler */
    public TrainingClassSectionHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String trainingClassName, String trainingClassSectionName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            trainingService = TrainingUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.trainingClassName = trainingClassName;
        this.trainingClassSectionName = trainingClassSectionName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("trainingClassSectionTranslation")) {
            var commandForm = TrainingFormFactory.getCreateTrainingClassSectionTranslationForm();

            commandForm.setTrainingClassName(trainingClassName);
            commandForm.setTrainingClassSectionName(trainingClassSectionName);
            commandForm.set(getAttrsMap(attrs));

            trainingService.createTrainingClassSectionTranslation(initialDataParser.getUserVisit(), commandForm);
        } else if(localName.equals("trainingClassPage")) {
            var commandForm = TrainingFormFactory.getCreateTrainingClassPageForm();

            commandForm.setTrainingClassName(trainingClassName);
            commandForm.setTrainingClassSectionName(trainingClassSectionName);
            commandForm.set(getAttrsMap(attrs));

            trainingService.createTrainingClassPage(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new TrainingClassPageHandler(initialDataParser, this, trainingClassName, trainingClassSectionName,
                    commandForm.getTrainingClassPageName()));
        } else if(localName.equals("trainingClassQuestion")) {
            var commandForm = TrainingFormFactory.getCreateTrainingClassQuestionForm();

            commandForm.setTrainingClassName(trainingClassName);
            commandForm.setTrainingClassSectionName(trainingClassSectionName);
            commandForm.set(getAttrsMap(attrs));

            trainingService.createTrainingClassQuestion(initialDataParser.getUserVisit(), commandForm);
            
            initialDataParser.pushHandler(new TrainingClassQuestionHandler(initialDataParser, this, trainingClassName, trainingClassSectionName,
                    commandForm.getTrainingClassQuestionName()));
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("trainingClassSection")) {
            initialDataParser.popHandler();
        }
    }
    
}
