// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.control.user.queue.server;

import com.echothree.control.user.queue.common.QueueRemote;
import com.echothree.control.user.queue.common.form.*;
import com.echothree.control.user.queue.server.command.*;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;
import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;

@Stateless
public class QueueBean
        extends QueueFormsImpl
        implements QueueRemote, QueueLocal {
    
    // -------------------------------------------------------------------------
    //   Testing
    // -------------------------------------------------------------------------
    
    @Override
    public String ping() {
        return "QueueBean is alive!";
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Types
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createQueueType(UserVisitPK userVisitPK, CreateQueueTypeForm form) {
        return CDI.current().select(CreateQueueTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getQueueTypeChoices(UserVisitPK userVisitPK, GetQueueTypeChoicesForm form) {
        return CDI.current().select(GetQueueTypeChoicesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getQueueType(UserVisitPK userVisitPK, GetQueueTypeForm form) {
        return CDI.current().select(GetQueueTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getQueueTypes(UserVisitPK userVisitPK, GetQueueTypesForm form) {
        return CDI.current().select(GetQueueTypesCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult setDefaultQueueType(UserVisitPK userVisitPK, SetDefaultQueueTypeForm form) {
        return CDI.current().select(SetDefaultQueueTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editQueueType(UserVisitPK userVisitPK, EditQueueTypeForm form) {
        return CDI.current().select(EditQueueTypeCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteQueueType(UserVisitPK userVisitPK, DeleteQueueTypeForm form) {
        return CDI.current().select(DeleteQueueTypeCommand.class).get().run(userVisitPK, form);
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Type Descriptions
    // --------------------------------------------------------------------------------
    
    @Override
    public CommandResult createQueueTypeDescription(UserVisitPK userVisitPK, CreateQueueTypeDescriptionForm form) {
        return CDI.current().select(CreateQueueTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getQueueTypeDescription(UserVisitPK userVisitPK, GetQueueTypeDescriptionForm form) {
        return CDI.current().select(GetQueueTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult getQueueTypeDescriptions(UserVisitPK userVisitPK, GetQueueTypeDescriptionsForm form) {
        return CDI.current().select(GetQueueTypeDescriptionsCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult editQueueTypeDescription(UserVisitPK userVisitPK, EditQueueTypeDescriptionForm form) {
        return CDI.current().select(EditQueueTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
    @Override
    public CommandResult deleteQueueTypeDescription(UserVisitPK userVisitPK, DeleteQueueTypeDescriptionForm form) {
        return CDI.current().select(DeleteQueueTypeDescriptionCommand.class).get().run(userVisitPK, form);
    }
    
}
