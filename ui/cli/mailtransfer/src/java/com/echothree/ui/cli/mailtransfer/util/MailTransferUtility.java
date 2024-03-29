// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.ui.cli.mailtransfer.util;

import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.communication.common.CommunicationService;
import com.echothree.control.user.communication.common.CommunicationUtil;
import com.echothree.control.user.communication.common.form.CommunicationFormFactory;
import com.echothree.control.user.communication.common.form.CreateCommunicationEventForm;
import com.echothree.control.user.communication.common.form.GetCommunicationSourcesForm;
import com.echothree.control.user.communication.common.result.GetCommunicationSourcesResult;
import com.echothree.model.control.communication.common.CommunicationConstants;
import com.echothree.model.control.communication.common.CommunicationOptions;
import com.echothree.model.control.communication.common.transfer.CommunicationEmailSourceTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceTransfer;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.persistence.type.ByteArray;
import com.google.common.base.Charsets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.SocketException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.naming.NamingException;
import org.apache.commons.net.pop3.POP3;
import org.apache.commons.net.pop3.POP3Client;
import org.apache.commons.net.pop3.POP3MessageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailTransferUtility {
    
    private boolean doVerbose;
    
    public boolean isDoVerbose() {
        return doVerbose;
    }
    
    public void setDoVerbose(boolean doVerbose) {
        this.doVerbose = doVerbose;
    }

    private static Logger logger = LoggerFactory.getLogger(MailTransferUtility.class);
    private UserVisitPK userVisitPK = null;
    
    private AuthenticationService getAuthenticationService()
            throws NamingException {
        return AuthenticationUtil.getHome();
    }
    
    private CommunicationService getCommunicationService()
            throws NamingException {
        return CommunicationUtil.getHome();
    }
    
    private UserVisitPK getUserVisit() {
        if(userVisitPK == null) {
            try {
                userVisitPK = getAuthenticationService().getDataLoaderUserVisit();
            } catch (NamingException ne) {
                System.err.println("Unable to locate authentication service");
            }
        }
        
        return userVisitPK;
    }
    
    private void clearUserVisit() {
        if(userVisitPK != null) {
            try {
                getAuthenticationService().invalidateUserVisit(userVisitPK);
                userVisitPK = null;
            } catch (NamingException ne) {
                System.err.println("Unable to locate authentication service");
            }
            
            userVisitPK = null;
        }
    }
    
    public void transfer()
            throws Exception {
        try {
            GetCommunicationSourcesForm commandForm = CommunicationFormFactory.getGetCommunicationSourcesForm();
            
            commandForm.setCommunicationSourceTypeName(CommunicationConstants.CommunicationSourceType_EMAIL);
            
            Set<String> options = new HashSet<>();
            options.add(CommunicationOptions.CommunicationSourceIncludeRelated);
            commandForm.setOptions(options);
            
            CommandResult commandResult = getCommunicationService().getCommunicationSources(getUserVisit(), commandForm);
            ExecutionResult executionResult = commandResult.getExecutionResult();
            GetCommunicationSourcesResult result = (GetCommunicationSourcesResult)executionResult.getResult();
            List<CommunicationSourceTransfer> communicationSources = result.getCommunicationSources();
            
            for(CommunicationSourceTransfer communicationSource: communicationSources) {
                transferFromServer(communicationSource);
            }
        } catch (NamingException ne) {
            System.err.println("Unable to locate communication service");
        }
        
        clearUserVisit();
    }
    
    private void transferFromServer(CommunicationSourceTransfer communicationSource)
            throws Exception {
        CommunicationEmailSourceTransfer communicationEmailSource = communicationSource.getCommunicationEmailSource();
        POP3Client pop3Client = null;
        
        try {
            String serverName = communicationEmailSource.getServer().getServerName();
            
            pop3Client = new POP3Client();
            pop3Client.connect(serverName);
            if(pop3Client.getState() == POP3.AUTHORIZATION_STATE) {
                String username = communicationEmailSource.getUsername();
                
                pop3Client.login(username, communicationEmailSource.getPassword());
                
                if(pop3Client.getState() == POP3.TRANSACTION_STATE) {
                    POP3MessageInfo[] pop3MessageInfos = pop3Client.listMessages();

                    logger.info("message count: " + pop3MessageInfos.length);
                    
                    if(pop3MessageInfos.length > 0) {
                        int successfulMessages = 0;
                        
                        for(int i = 0; i < pop3MessageInfos.length && successfulMessages < 10; i++) {
                            POP3MessageInfo pop3MessageInfo = pop3MessageInfos[i];
                            int messageId = pop3MessageInfo.number;
                            Reader reader = pop3Client.retrieveMessage(messageId);
                            BufferedReader bufferedReader = new BufferedReader(reader);
                            StringBuilder stringBuilder = new StringBuilder();

                            logger.info("message " + pop3MessageInfo.number + ", size = " + pop3MessageInfo.size);
                            
                            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                                stringBuilder.append(line);
                                stringBuilder.append('\n');
                            }
                            
                            CreateCommunicationEventForm form = CommunicationFormFactory.getCreateCommunicationEventForm();
                            
                            form.setCommunicationSourceName(communicationSource.getCommunicationSourceName());
                            form.setCommunicationEventTypeName(CommunicationConstants.CommunicationEventType_EMAIL);
                            form.setBlobDocument(new ByteArray(stringBuilder.toString().getBytes(Charsets.UTF_8)));
                            
                            CommandResult commandResult = getCommunicationService().createCommunicationEvent(getUserVisit(), form);
                            
                            if(!commandResult.hasErrors()) {
                                successfulMessages++;
                                pop3Client.deleteMessage(messageId);
                            } else {
                                logger.error(commandResult.toString());
                            }
                        }
                    }
                    
                    pop3Client.logout();
                } else {
                    logger.error("login to " + username + "@" + serverName + " failed");
                }
            } else {
                logger.error("connection to " + serverName + " failed");
            }
        } catch(SocketException se) {
            logger.error("An Exception occurred:", se);
        } catch(IOException ioe) {
            logger.error("An Exception occurred:", ioe);
        } finally {
            if(pop3Client != null) {
                try {
                    if(pop3Client.getState() == POP3.TRANSACTION_STATE) {
                        pop3Client.logout();
                    }
                    
                    pop3Client.disconnect();
                } catch(IOException ioe) {
                    logger.error("An Exception occurred:", ioe);
                }
            }
        }
    }
    
}
