// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

package com.echothree.model.control.security.server.logic;

import com.echothree.model.control.security.server.SecurityControl;
import com.echothree.model.control.training.server.TrainingControl;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic;
import com.echothree.model.control.training.server.logic.PartyTrainingClassLogic.PreparedPartyTrainingClass;
import com.echothree.model.control.training.common.training.PartyTrainingClassStatusConstants;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplate;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateRole;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateTrainingClass;
import com.echothree.model.data.security.server.entity.PartySecurityRoleTemplateUse;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.training.server.entity.PartyTrainingClass;
import com.echothree.model.data.training.server.entity.TrainingClass;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartySecurityRoleTemplateLogic {

    private PartySecurityRoleTemplateLogic() {
        super();
    }

    private static class PartySecurityRoleTemplateLogicHolder {
        static PartySecurityRoleTemplateLogic instance = new PartySecurityRoleTemplateLogic();
    }

    public static PartySecurityRoleTemplateLogic getInstance() {
        return PartySecurityRoleTemplateLogicHolder.instance;
    }

    // --------------------------------------------------------------------------------
    //   Party Security Role Template Roles
    // --------------------------------------------------------------------------------

    public PartySecurityRoleTemplateRole createPartySecurityRoleTemplateRole(final PartySecurityRoleTemplate partySecurityRoleTemplate,
            final SecurityRole securityRole, final BasePK createdBy) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        PartySecurityRoleTemplateRole partySecurityRoleTemplateRole = securityControl.createPartySecurityRoleTemplateRole(partySecurityRoleTemplate,
                securityRole, createdBy);

        // Add SecurityRole to any Parties that are currently assigned to the PartySecurityRoleTemplate.
        List<PartySecurityRoleTemplateUse> partySecurityRoleTemplateUses = securityControl.getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(partySecurityRoleTemplate);
        partySecurityRoleTemplateUses.stream().map((partySecurityRoleTemplateUse) -> partySecurityRoleTemplateUse.getParty()).filter((party) -> (securityControl.getPartySecurityRole(party, securityRole) == null)).forEach((party) -> {
            securityControl.createPartySecurityRole(party, securityRole, createdBy);
        });

        return partySecurityRoleTemplateRole;
    }

    public void deletePartySecurityRoleTemplateRole(final PartySecurityRoleTemplateRole partySecurityRoleTemplateRole, final BasePK deletedBy) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        PartySecurityRoleTemplate partySecurityRoleTemplate = partySecurityRoleTemplateRole.getPartySecurityRoleTemplate();

        securityControl.deletePartySecurityRoleTemplateRole(partySecurityRoleTemplateRole, deletedBy);

        // Remove SecurityRole from any Parties that may be assigned to its PartySecurityRoleTemplate.
        List<PartySecurityRoleTemplateUse> partySecurityRoleTemplateUses = securityControl.getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(partySecurityRoleTemplate);
        if(partySecurityRoleTemplateUses.size() > 0) {
            SecurityRole securityRole = partySecurityRoleTemplateRole.getSecurityRole();

            partySecurityRoleTemplateUses.stream().map((partySecurityRoleTemplateUse) -> partySecurityRoleTemplateUse.getParty()).map((party) -> securityControl.getPartySecurityRoleForUpdate(party, securityRole)).filter((partySecurityRole) -> (partySecurityRole != null)).forEach((partySecurityRole) -> {
                securityControl.deletePartySecurityRole(partySecurityRole, deletedBy);
            });
        }
    }
    
    public void deletePartySecurityRoleTemplateRoles(final List<PartySecurityRoleTemplateRole> partySecurityRoleTemplateRoles, final BasePK deletedBy) {
        partySecurityRoleTemplateRoles.stream().forEach((partySecurityRoleTemplateRole) -> {
            deletePartySecurityRoleTemplateRole(partySecurityRoleTemplateRole, deletedBy);
        });
    }

    public void deletePartySecurityRoleTemplateRoleByPartySecurityRoleTemplate(final PartySecurityRoleTemplate partySecurityRoleTemplate, final BasePK deletedBy) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);

        deletePartySecurityRoleTemplateRoles(securityControl.getPartySecurityRoleTemplateRolesForUpdate(partySecurityRoleTemplate), deletedBy);
    }

    public void deletePartySecurityRoleTemplateRolesBySecurityRole(final SecurityRole securityRole, final BasePK deletedBy) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);

        deletePartySecurityRoleTemplateRoles(securityControl.getPartySecurityRoleTemplateRolesBySecurityRoleForUpdate(securityRole), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Party Security Role Template Training Classes
    // --------------------------------------------------------------------------------

    public static class PreparedPartySecurityRoleTemplateTrainingClass {

        private PartySecurityRoleTemplate partySecurityRoleTemplate;
        private TrainingClass trainingClass;
        private Set<PreparedPartyTrainingClass> preparedPartyTrainingClasses;

        /**
         * @return the partySecurityRoleTemplate
         */
        public PartySecurityRoleTemplate getPartySecurityRoleTemplate() {
            return partySecurityRoleTemplate;
        }

        /**
         * @param partySecurityRoleTemplate the partySecurityRoleTemplate to set
         */
        public void setPartySecurityRoleTemplate(PartySecurityRoleTemplate partySecurityRoleTemplate) {
            this.partySecurityRoleTemplate = partySecurityRoleTemplate;
        }

        /**
         * @return the trainingClass
         */
        public TrainingClass getTrainingClass() {
            return trainingClass;
        }

        /**
         * @param trainingClass the trainingClass to set
         */
        public void setTrainingClass(TrainingClass trainingClass) {
            this.trainingClass = trainingClass;
        }

        /**
         * @return the preparedPartyTrainingClasses
         */
        public Set<PreparedPartyTrainingClass> getPreparedPartyTrainingClasses() {
            return preparedPartyTrainingClasses;
        }

        /**
         * @param preparedPartyTrainingClasses the preparedPartyTrainingClasses to set
         */
        public void setPreparedPartyTrainingClasses(Set<PreparedPartyTrainingClass> preparedPartyTrainingClasses) {
            this.preparedPartyTrainingClasses = preparedPartyTrainingClasses;
        }

    }

    public PreparedPartySecurityRoleTemplateTrainingClass preparePartySecurityRoleTemplateTrainingClass(final ExecutionErrorAccumulator eea,
            final PartySecurityRoleTemplate partySecurityRoleTemplate, final TrainingClass trainingClass) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        PreparedPartySecurityRoleTemplateTrainingClass preparedPartySecurityRoleTemplateTrainingClass = new PreparedPartySecurityRoleTemplateTrainingClass();

        preparedPartySecurityRoleTemplateTrainingClass.setPartySecurityRoleTemplate(partySecurityRoleTemplate);
        preparedPartySecurityRoleTemplateTrainingClass.setTrainingClass(trainingClass);

        // If Party does not have a PartyTrainingClass for this TrainingClass in either ASSIGNED or TRAINING, then assign it to them.
        List<PartySecurityRoleTemplateUse> partySecurityRoleTemplateUses = securityControl.getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(partySecurityRoleTemplate);
        Set<PreparedPartyTrainingClass> preparedPartyTrainingClasses = new HashSet<>();
        if(partySecurityRoleTemplateUses.size() > 0) {
            TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);

            for(PartySecurityRoleTemplateUse partySecurityRoleTemplateUse: partySecurityRoleTemplateUses) {
                Party party = partySecurityRoleTemplateUse.getParty();
                Set<PartyTrainingClass> partyTrainingClasses = trainingControl.getPartyTrainingClassesByStatuses(party, trainingClass,
                        PartyTrainingClassStatusConstants.WorkflowStep_ASSIGNED, PartyTrainingClassStatusConstants.WorkflowStep_TRAINING);

                if(partyTrainingClasses.isEmpty()) {
                    preparedPartyTrainingClasses.add(PartyTrainingClassLogic.getInstance().preparePartyTrainingClass(eea, party, trainingClass, null, null));
                }

                if(eea.hasExecutionErrors()) {
                    break;
                }
            }
        }

        preparedPartySecurityRoleTemplateTrainingClass.setPreparedPartyTrainingClasses(preparedPartyTrainingClasses);

        return preparedPartySecurityRoleTemplateTrainingClass;
    }

    public PartySecurityRoleTemplateTrainingClass createPartySecurityRoleTemplateTrainingClass(final Session session,
            final PreparedPartySecurityRoleTemplateTrainingClass preparedPartySecurityRoleTemplateTrainingClass, final BasePK createdBy) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        TrainingClass trainingClass = preparedPartySecurityRoleTemplateTrainingClass.getTrainingClass();
        PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass = securityControl.createPartySecurityRoleTemplateTrainingClass(preparedPartySecurityRoleTemplateTrainingClass.partySecurityRoleTemplate,
            trainingClass, createdBy);

        // If Party does not have a PartyTrainingClass for this TrainingClass in either ASSIGNED or TRAINING, then assign it to them.
        preparedPartySecurityRoleTemplateTrainingClass.getPreparedPartyTrainingClasses().stream().forEach((preparedPartyTrainingClass) -> {
            PartyTrainingClassLogic.getInstance().createPartyTrainingClass(session, preparedPartyTrainingClass, createdBy);
        });

        return partySecurityRoleTemplateTrainingClass;
    }

    public void deletePartySecurityRoleTemplateTrainingClass(final PartySecurityRoleTemplateTrainingClass partySecurityRoleTemplateTrainingClass,
            final BasePK deletedBy) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);
        PartySecurityRoleTemplate partySecurityRoleTemplate = partySecurityRoleTemplateTrainingClass.getPartySecurityRoleTemplate();

        securityControl.deletePartySecurityRoleTemplateTrainingClass(partySecurityRoleTemplateTrainingClass, deletedBy);

        // If Party has a PartyTrainingClass in ASSIGNED status for this TrainingClass, then delete it.
        List<PartySecurityRoleTemplateUse> partySecurityRoleTemplateUses = securityControl.getPartySecurityRoleTemplateUsesByPartySecurityRoleTemplate(partySecurityRoleTemplate);
        if(partySecurityRoleTemplateUses.size() > 0) {
            TrainingControl trainingControl = (TrainingControl)Session.getModelController(TrainingControl.class);
            TrainingClass trainingClass = partySecurityRoleTemplateTrainingClass.getTrainingClass();

            partySecurityRoleTemplateUses.stream().map((partySecurityRoleTemplateUse) -> partySecurityRoleTemplateUse.getParty()).map((party) -> trainingControl.getPartyTrainingClassesByStatusesForUpdate(party, trainingClass,
                    PartyTrainingClassStatusConstants.WorkflowStep_ASSIGNED)).filter((partyTrainingClasses) -> (partyTrainingClasses.size() > 0)).forEach((partyTrainingClasses) -> {
                        partyTrainingClasses.stream().forEach((partyTrainingClass) -> {
                            PartyTrainingClassLogic.getInstance().deletePartyTrainingClass(partyTrainingClass, deletedBy);
                });
            });
        }
    }

    public void deletePartySecurityRoleTemplateTrainingClasses(final List<PartySecurityRoleTemplateTrainingClass> partySecurityRoleTemplateTrainingClasses,
            final BasePK deletedBy) {
        partySecurityRoleTemplateTrainingClasses.stream().forEach((partySecurityRoleTemplateTrainingClass) -> {
            deletePartySecurityRoleTemplateTrainingClass(partySecurityRoleTemplateTrainingClass, deletedBy);
        });
    }

    public void deletePartySecurityRoleTemplateTrainingClassByPartySecurityRoleTemplate(final PartySecurityRoleTemplate partySecurityRoleTemplate,
            final BasePK deletedBy) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);

        deletePartySecurityRoleTemplateTrainingClasses(securityControl.getPartySecurityRoleTemplateTrainingClassesForUpdate(partySecurityRoleTemplate), deletedBy);
    }

    public void deletePartySecurityRoleTemplateTrainingClassesByTrainingClass(final TrainingClass trainingClass, final BasePK deletedBy) {
        SecurityControl securityControl = (SecurityControl)Session.getModelController(SecurityControl.class);

        deletePartySecurityRoleTemplateTrainingClasses(securityControl.getPartySecurityRoleTemplateTrainingClassesByTrainingClassForUpdate(trainingClass), deletedBy);
    }

}