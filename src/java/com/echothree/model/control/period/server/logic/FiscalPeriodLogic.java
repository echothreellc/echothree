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

package com.echothree.model.control.period.server.logic;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.period.common.PeriodConstants;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodKind;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Formatter;
import java.util.Locale;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class FiscalPeriodLogic {

    protected FiscalPeriodLogic() {
        super();
    }

    public static FiscalPeriodLogic getInstance() {
        return CDI.current().select(FiscalPeriodLogic.class).get();
    }
    
    private void createMonth(final PeriodKind periodKind, final Period quarterPeriod, final ZonedDateTime yearStart, final int month,
            final PartyPK createdBy) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var year = yearStart.getYear();
        var periodName = new StringBuilder().append(year).append('_').append('M');
        var monthPeriodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_MONTH);
        var monthStart = yearStart.withMonth(month);
        var monthEnd = monthStart.plusMonths(1).minusNanos(1);
        
        new Formatter(periodName, Locale.US).format("%02d", month);
        var monthPeriod = PeriodLogic.getInstance().createPeriod(periodKind, periodName.toString(), quarterPeriod, monthPeriodType,
                monthStart.toInstant().toEpochMilli(), monthEnd.toInstant().toEpochMilli(), createdBy);

        var periodKindDescriptions = periodControl.getPeriodKindDescriptionsByPeriodKind(periodKind);
        var yearPeriodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_YEAR);
        periodKindDescriptions.forEach((periodKindDescription) -> {
            var language = periodKindDescription.getLanguage();
            var yearPeriodTypeDescription = periodControl.getPeriodTypeDescription(yearPeriodType, language);
            if (yearPeriodTypeDescription != null) {
                var monthPeriodTypeDescription = periodControl.getPeriodTypeDescription(monthPeriodType, language);
                if (monthPeriodTypeDescription != null) {
                    var description = periodKindDescription.getDescription() +
                            ' ' + yearPeriodTypeDescription.getDescription() +
                            ' ' + year +
                            ' ' + monthPeriodTypeDescription.getDescription() +
                            ' ' + month;

                    periodControl.createPeriodDescription(monthPeriod, language, description, createdBy);
                }
            }
        });
    }
    
    private void createQuarter(final PeriodKind periodKind, final Period yearPeriod, final ZonedDateTime yearStart, final int quarter,
            final PartyPK createdBy) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var year = yearStart.getYear();
        var periodName = String.valueOf(year) + '_' + 'Q' + quarter;
        var quarterPeriodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_QUARTER);
        var monthOfQuarterStart = 1 + (quarter - 1) * 3;
        var quarterStart = yearStart.withMonth(monthOfQuarterStart);
        var quarterEnd = quarterStart.plusMonths(3).minusNanos(1);
        var quarterPeriod = PeriodLogic.getInstance().createPeriod(periodKind, periodName, yearPeriod, quarterPeriodType,
                quarterStart.toInstant().toEpochMilli(), quarterEnd.toInstant().toEpochMilli(), createdBy);

        var periodKindDescriptions = periodControl.getPeriodKindDescriptionsByPeriodKind(periodKind);
        var yearPeriodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_YEAR);
        periodKindDescriptions.forEach((periodKindDescription) -> {
            var language = periodKindDescription.getLanguage();
            var yearPeriodTypeDescription = periodControl.getPeriodTypeDescription(yearPeriodType, language);
            if (yearPeriodTypeDescription != null) {
                var quarterPeriodTypeDescription = periodControl.getPeriodTypeDescription(quarterPeriodType, language);
                if (quarterPeriodTypeDescription != null) {
                    var description = periodKindDescription.getDescription() +
                            ' ' + yearPeriodTypeDescription.getDescription() +
                            ' ' + year +
                            ' ' + quarterPeriodTypeDescription.getDescription() +
                            ' ' + quarter;

                    periodControl.createPeriodDescription(quarterPeriod, language, description, createdBy);
                }
            }
        });

        for(var monthOffset = 0; monthOffset < 3; monthOffset++) {
            createMonth(periodKind, quarterPeriod, yearStart, monthOfQuarterStart + monthOffset, createdBy);
        }
    }
    
    private Period createYear(final ExecutionErrorAccumulator eea, final Period perpetualPeriod, final int year,
            final ZoneId zone, final PartyPK createdBy) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var periodName = String.valueOf(year);
        var periodKind = periodControl.getPeriodKindByName(PeriodConstants.PeriodKind_FISCAL);
        var yearPeriod = periodControl.getPeriodByName(periodKind, periodName);

        if(yearPeriod == null) {
            var periodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_YEAR);
            var yearStart = ZonedDateTime.of(year, 1, 1, 0, 0, 0, 0, zone);
            var yearEnd = yearStart.plusYears(1).minusNanos(1);

            yearPeriod = PeriodLogic.getInstance().createPeriod(periodKind, periodName, perpetualPeriod, periodType,
                    yearStart.toInstant().toEpochMilli(), yearEnd.toInstant().toEpochMilli(), createdBy);

            var periodKindDescriptions = periodControl.getPeriodKindDescriptionsByPeriodKind(periodKind);
            for(var periodKindDescription : periodKindDescriptions) {
                var language = periodKindDescription.getLanguage();
                var periodTypeDescription = periodControl.getPeriodTypeDescription(periodType, language);
                
                if(periodTypeDescription != null) {
                    var description = periodKindDescription.getDescription() +
                            ' ' + periodTypeDescription.getDescription() +
                            ' ' + year;
                    
                    periodControl.createPeriodDescription(yearPeriod, language, description, createdBy);
                }
            }
            
            for(var quarter = 1; quarter < 5; quarter++) {
                createQuarter(periodKind, yearPeriod, yearStart, quarter, createdBy);
            }
        } else {
            eea.addExecutionError(ExecutionErrors.DuplicatePeriodName.name(), PeriodConstants.PeriodKind_FISCAL, periodName);
        }
        
        return yearPeriod;
    }

    public Period ensurePerpetual(final ExecutionErrorAccumulator eea, final PartyPK createdBy) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var periodKind = periodControl.getPeriodKindByName(PeriodConstants.PeriodKind_FISCAL);
        var perpetualPeriod = periodControl.getPeriodByName(periodKind, PeriodConstants.Period_PERPETUAL);

        if(perpetualPeriod == null) {
            var periodType = periodControl.getPeriodTypeByName(periodKind, PeriodConstants.PeriodType_PERPETUAL);

            perpetualPeriod = PeriodLogic.getInstance().createPeriod(periodKind, PeriodConstants.Period_PERPETUAL, null,
                    periodType, 0L, Long.MAX_VALUE, createdBy);

        }

        return perpetualPeriod;
    }
    
    public Period createFiscalYear(final ExecutionErrorAccumulator eea, final Integer year, final PartyPK createdBy) {
        var partyControl = Session.getModelController(PartyControl.class);
        var defaultPartyCompany = partyControl.getDefaultPartyCompany();
        Period fiscalYear = null;
        
        if(defaultPartyCompany != null) {
            var perpetualPeriod = ensurePerpetual(eea, createdBy);
            var javaTimeZoneName = partyControl.getPreferredTimeZone(defaultPartyCompany.getParty()).getLastDetail().getJavaTimeZoneName();
            var zone = ZoneId.of(javaTimeZoneName);
            
            fiscalYear = createYear(eea, perpetualPeriod, year, zone, createdBy);
        } else {
            eea.addExecutionError(ExecutionErrors.MissingDefaultCompany.name());
        }
        
        return fiscalYear;
    }
    
    private Period getFiscalPeriodByName(final ExecutionErrorAccumulator eea, final String periodName,
            final EntityPermission entityPermission) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var periodKind = periodControl.getPeriodKindByName(PeriodConstants.PeriodKind_FISCAL);
        var period = periodControl.getPeriodByName(periodKind, periodName, entityPermission);
        
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
