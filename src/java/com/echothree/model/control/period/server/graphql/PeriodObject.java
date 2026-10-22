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

package com.echothree.model.control.period.server.graphql;

import com.echothree.control.user.period.common.PeriodUtil;
import com.echothree.control.user.period.server.command.GetFiscalPeriodsCommand;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.period.server.control.PeriodControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.period.common.PeriodConstants;
import com.echothree.model.data.period.server.entity.Period;
import com.echothree.model.data.period.server.entity.PeriodDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import javax.enterprise.inject.spi.CDI;
import javax.naming.NamingException;
import static com.echothree.model.control.period.common.PeriodConstants.PeriodKind_FISCAL;

public abstract class PeriodObject
        extends BaseEntityInstanceObject {

    protected final Period period; // Always Present

    protected PeriodObject(Period period) {
        super(period.getPrimaryKey());

        this.period = period;
    }

    private PeriodDetail periodDetail; // Optional, use getPeriodDetail()

    protected PeriodDetail getPeriodDetail() {
        if(periodDetail == null) {
            periodDetail = period.getLastDetail();
        }

        return periodDetail;
    }

    public static PeriodInterface toPeriodInterface(Period period) {
        return period == null ?
                null :
                switch(period.getLastDetail().getPeriodKind().getLastDetail().getPeriodKindName()) {
                    case PeriodKind_FISCAL -> new FiscalPeriodObject(period);
                    default -> null;
                };
    }

    @GraphQLField
    @GraphQLNonNull
    public String getPeriodName() {
        return getPeriodDetail().getPeriodName();
    }

    @GraphQLField
    public PeriodKindObject getPeriodKind(final DataFetchingEnvironment env) {
        return PeriodSecurityUtils.getHasPeriodKindAccess(env) ? new PeriodKindObject(getPeriodDetail().getPeriodKind()) : null;
    }

    @GraphQLField
    public PeriodTypeObject getPeriodType(final DataFetchingEnvironment env) {
        if(PeriodSecurityUtils.getHasPeriodTypeAccess(env)) {
            var periodType = getPeriodDetail().getPeriodType();

            return periodType == null ? null : new PeriodTypeObject(periodType);
        } else {
            return null;
        }
    }

    @GraphQLField
    public PeriodInterface getParentPeriod(final DataFetchingEnvironment env) {
        return PeriodSecurityUtils.getHasFiscalPeriodAccess(env) ? toPeriodInterface(getPeriodDetail().getParentPeriod()) : null;
    }

    @GraphQLField
    @GraphQLNonNull
    public TimeObject getStartTime() {
        return new TimeObject(getPeriodDetail().getStartTime());
    }

    @GraphQLField
    @GraphQLNonNull
    public TimeObject getEndTime() {
        return new TimeObject(getPeriodDetail().getEndTime());
    }

    @GraphQLField
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var periodControl = Session.getModelController(PeriodControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return periodControl.getBestPeriodDescription(period, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("child periods")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<PeriodInterface> getPeriods(final DataFetchingEnvironment env) {
        return getPeriods(env, getPeriodDetail().getPeriodKind().getLastDetail().getPeriodKindName(),
                getPeriodName(), null);
    }

    public static CountingPaginatedData<PeriodInterface> getPeriods(final DataFetchingEnvironment env,
            final String periodKindName, final String parentPeriodName, final String periodTypeName) {
        if(PeriodKind_FISCAL.equals(periodKindName) && PeriodSecurityUtils.getHasFiscalPeriodsAccess(env)) {
            try {
                var form = PeriodUtil.getHome().getGetFiscalPeriodsForm();
                var command = CDI.current().select(GetFiscalPeriodsCommand.class).get();

                form.setParentPeriodName(parentPeriodName);
                form.setPeriodTypeName(periodTypeName);

                var total = command.getTotalEntitiesForGraphQl(BaseGraphQl.getUserVisitPK(env), form);
                if(total == null) {
                    return Connections.emptyConnection();
                }

                try(var objectLimiter = new ObjectLimiter(env, PeriodConstants.COMPONENT_VENDOR_NAME,
                        PeriodConstants.ENTITY_TYPE_NAME, total)) {
                    var entities = command.getEntitiesForGraphQl(BaseGraphQl.getUserVisitPK(env), form);
                    var objects = new ArrayList<PeriodInterface>(entities.size());

                    for(var entity : entities) {
                        objects.add(toPeriodInterface(entity));
                    }

                    return new CountedObjects<>(objectLimiter, objects);
                }
            } catch(NamingException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
