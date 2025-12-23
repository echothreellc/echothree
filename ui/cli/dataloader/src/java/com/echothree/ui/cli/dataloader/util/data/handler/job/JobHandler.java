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

package com.echothree.ui.cli.dataloader.util.data.handler.job;

import com.echothree.control.user.job.common.JobUtil;
import com.echothree.control.user.job.common.JobService;
import com.echothree.control.user.job.common.form.JobFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import javax.naming.NamingException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class JobHandler
        extends BaseHandler {
    JobService jobService;
    String jobName;
    
    /** Creates a new instance of JobHandler */
    public JobHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String jobName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        try {
            jobService = JobUtil.getHome();
        } catch (NamingException ne) {
            throw new SAXException(ne);
        }
        
        this.jobName = jobName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
            throws SAXException {
        if(localName.equals("jobDescription")) {
            var commandForm = JobFormFactory.getCreateJobDescriptionForm();
            
            commandForm.setJobName(jobName);
            commandForm.set(getAttrsMap(attrs));
            
            jobService.createJobDescription(initialDataParser.getUserVisit(), commandForm);
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if(localName.equals("job")) {
            initialDataParser.popHandler();
        }
    }
    
}
