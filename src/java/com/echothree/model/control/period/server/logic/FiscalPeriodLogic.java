// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.model.control.period.server.logic;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.period.common.PeriodConstants;
import com.echothree.model.control.period.server.PeriodControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.PartyCompany;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.model.data.period.server.entity.PeriodKindDescription;
import com.echothree.model.data.period.server.entity.PeriodType;
import com.echothree.model.data.period.server.entity.PeriodTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

public class FiscalPeriodLogic {

    private FiscalPeriodLogic() {
        super();
    }

    private static class FiscalPeriodLogicHolder {
        static FiscalPeriodLogic instance = new FiscalPeriodLogic();
    }

    public static FiscalPeriodLogic getInstance() {
        return FiscalPeriodLogicHolder.instance;
    }
    
    private void createMonth(final PeriodKind periodKind, final Period quarterPeriod, final ZonedDateTime yearStart, final int month,
            final PartyPK createdBy) {
        var periodControl = (PeriodControl)Session.getModelController(PeriodControl.class);
        int year = yearStart.getYear();
        StringBuilder periodName = new StringBuilder().append(year).append('_').append('M');
        PeriodType monthPeriodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_MONTH);
        ZonedDateTime monthStart = yearStart.withMonth(month);
        ZonedDateTime monthEnd = monthStart.plusMonths(1).minusNanos(1);
        
        new Formatter(periodName, Locale.US).format("%02d", month);
        Period monthPeriod = PeriodLogic.getInstance().createPeriod(periodKind, periodName.toString(), quarterPeriod, monthPeriodType,
                monthStart.toInstant().toEpochMilli(), monthEnd.toInstant().toEpochMilli(), createdBy);
        
        List<PeriodKindDescription> periodKindDescriptions = periodControl.getPeriodKindDescriptionsByPeriodKind(periodKind);
        PeriodType yearPeriodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_YEAR);
        periodKindDescriptions.stream().forEach((periodKindDescription) -> {
            Language language = periodKindDescription.getLanguage();
            PeriodTypeDescription yearPeriodTypeDescription = periodControl.getPeriodTypeDescription(yearPeriodType, language);
            if (yearPeriodTypeDescription != null) {
                PeriodTypeDescription monthPeriodTypeDescription = periodControl.getPeriodTypeDescription(monthPeriodType, language);
                if (monthPeriodTypeDescription != null) {
                    StringBuilder description = new StringBuilder(periodKindDescription.getDescription())
                            .append(' ').append(yearPeriodTypeDescription.getDescription())
                            .append(' ').append(year)
                            .append(' ').append(monthPeriodTypeDescription.getDescription())
                            .append(' ').append(month);

                    periodControl.createPeriodDescription(monthPeriod, language, description.toString(), createdBy);
                }
            }
        });
    }
    
    private void createQuarter(final PeriodKind periodKind, final Period yearPeriod, final ZonedDateTime yearStart, final int quarter,
            final PartyPK createdBy) {
        var periodControl = (PeriodControl)Session.getModelController(PeriodControl.class);
        int year = yearStart.getYear();
        String periodName = new StringBuilder().append(year).append('_').append('Q').append(quarter).toString();
        PeriodType quarterPeriodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_QUARTER);
        int monthOfQuarterStart = 1 + (quarter - 1) * 3;
        ZonedDateTime quarterStart = yearStart.withMonth(monthOfQuarterStart);
        ZonedDateTime quarterEnd = quarterStart.plusMonths(3).minusNanos(1);
        Period quarterPeriod = PeriodLogic.getInstance().createPeriod(periodKind, periodName, yearPeriod, quarterPeriodType,
                quarterStart.toInstant().toEpochMilli(), quarterEnd.toInstant().toEpochMilli(), createdBy);
        
        List<PeriodKindDescription> periodKindDescriptions = periodControl.getPeriodKindDescriptionsByPeriodKind(periodKind);
        PeriodType yearPeriodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_YEAR);
        periodKindDescriptions.stream().forEach((periodKindDescription) -> {
            Language language = periodKindDescription.getLanguage();
            PeriodTypeDescription yearPeriodTypeDescription = periodControl.getPeriodTypeDescription(yearPeriodType, language);
            if (yearPeriodTypeDescription != null) {
                PeriodTypeDescription quarterPeriodTypeDescription = periodControl.getPeriodTypeDescription(quarterPeriodType, language);
                if (quarterPeriodTypeDescription != null) {
                    StringBuilder description = new StringBuilder(periodKindDescription.getDescription())
                            .append(' ').append(yearPeriodTypeDescription.getDescription())
                            .append(' ').append(year)
                            .append(' ').append(quarterPeriodTypeDescription.getDescription())
                            .append(' ').append(quarter);

                    periodControl.createPeriodDescription(quarterPeriod, language, description.toString(), createdBy);
                }
            }
        });

        for(int monthOffset = 0; monthOffset < 3; monthOffset++) {
            createMonth(periodKind, quarterPeriod, yearStart, monthOfQuarterStart + monthOffset, createdBy);
        }
    }
    
    private Period createYear(final ExecutionErrorAccumulator eea, final int year, final ZoneId zone, final PartyPK createdBy) {
        var periodControl = (PeriodControl)Session.getModelController(PeriodControl.class);
        String periodName = new StringBuilder().append(year).toString();
        PeriodKind periodKind = periodControl.getPeriodKindByName(PeriodConstants.PeriodKind_FISCAL);
        Period yearPeriod = periodControl.getPeriodByName(periodKind, periodName);

        if(yearPeriod == null) {
            PeriodType periodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_YEAR);
            ZonedDateTime yearStart = ZonedDateTime.of(year, 1, 1, 0, 0, 0, 0, zone);
            ZonedDateTime yearEnd = yearStart.plusYears(1).minusNanos(1);

            yearPeriod = PeriodLogic.getInstance().createPeriod(periodKind, periodName, null, periodType,
                    yearStart.toInstant().toEpochMilli(), yearEnd.toInstant().toEpochMilli(), createdBy);
            
            List<PeriodKindDescription> periodKindDescriptions = periodControl.getPeriodKindDescriptionsByPeriodKind(periodKind);
            for(PeriodKindDescription periodKindDescription: periodKindDescriptions) {
                Language language = periodKindDescription.getLanguage();
                PeriodTypeDescription periodTypeDescription = periodControl.getPeriodTypeDescription(periodType, language);
                
                if(periodTypeDescription != null) {
                    StringBuilder description = new StringBuilder(periodKindDescription.getDescription())
                            .append(' ').append(periodTypeDescription.getDescription())
                            .append(' ').append(year);
                    
                    periodControl.createPeriodDescription(yearPeriod, language, description.toString(), createdBy);
                }
            }
            
            for(int quarter = 1; quarter < 5; quarter++) {
                createQuarter(periodKind, yearPeriod, yearStart, quarter, createdBy);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.DuplicatePeriodName.name(), PeriodConstants.PeriodKind_FISCAL, periodName);
        }
        
        return yearPeriod;
    }
    
    public Period createFiscalYear(final ExecutionErrorAccumulator eea, final Integer year, final PartyPK createdBy) {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        PartyCompany defaultPartyCompany = partyControl.getDefaultPartyCompany();
        Period fiscalYear = null;
        
        if(defaultPartyCompany != null) {
            String javaTimeZoneName = partyControl.getPreferredTimeZone(defaultPartyCompany.getParty()).getLastDetail().getJavaTimeZoneName();
            ZoneId zone = ZoneId.of(javaTimeZoneName);
            
            fiscalYear = createYear(eea, year, zone, createdBy);
        } else {
            eea.addExecutionError(ExecutionErrors.MissingDefaultCompany.name());
        }
        
        return fiscalYear;
    }
    
    private Period getFiscalPeriodByName(final ExecutionErrorAccumulator eea, final String periodName,
            final EntityPermission entityPermission) {
        var periodControl = (PeriodControl)Session.getModelController(PeriodControl.class);
        PeriodKind periodKind = periodControl.getPeriodKindByName(PeriodConstants.PeriodKind_FISCAL);
        Period period = periodControl.getPeriodByName(periodKind, periodName, entityPermission);
        
        if(periodKind != null) {
            period = periodControl.getPeriodByName(periodKind, periodName, entityPermission);
            
            if(period == null) {
                eea.addExecutionError(ExecutionErrors.UnknownPeriodName.name(), PeriodConstants.PeriodKind_FISCAL, periodName);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownPeriodKindName.name(), PeriodConstants.PeriodKind_FISCAL);
        }
        
        return period;
    }
    
    public Period getFiscalPeriodByName(final ExecutionErrorAccumulator eea, final String periodName) {
        return getFiscalPeriodByName(eea, periodName, EntityPermission.READ_ONLY);
    }
    
    public Period getFiscalPeriodByNameForUpdate(final ExecutionErrorAccumulator eea, final String periodName) {
        return getFiscalPeriodByName(eea, periodName, EntityPermission.READ_WRITE);
    }
    
}
