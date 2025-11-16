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

package com.echothree.model.control.item.server.control;

import com.echothree.model.control.item.common.transfer.HarmonizedTariffScheduleCodeResultTransfer;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.data.item.common.pk.HarmonizedTariffScheduleCodePK;
import com.echothree.model.data.item.server.factory.HarmonizedTariffScheduleCodeFactory;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.server.control.BaseModelControl;
import javax.enterprise.inject.spi.CDI;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class HarmonizedTariffScheduleCodeControl
        extends BaseModelControl {

    /** Creates a new instance of HarmonizedTariffScheduleCodeControl */
    protected HarmonizedTariffScheduleCodeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Harmonized Tariff Schedule Code Searches
    // --------------------------------------------------------------------------------

    public List<HarmonizedTariffScheduleCodeResultTransfer> getHarmonizedTariffScheduleCodeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var harmonizedTariffScheduleCodeResultTransfers = new ArrayList<HarmonizedTariffScheduleCodeResultTransfer>();
        var includeHarmonizedTariffScheduleCode = false;

        var options = session.getOptions();
        if(options != null) {
            includeHarmonizedTariffScheduleCode = options.contains(SearchOptions.HarmonizedTariffScheduleCodeResultIncludeHarmonizedTariffScheduleCode);
        }

        try {
            var itemControl = Session.getModelController(ItemControl.class);
            var ps = SearchResultFactory.getInstance().prepareStatement(
                    "SELECT eni_entityuniqueid " +
                            "FROM searchresults, entityinstances " +
                            "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid " +
                            "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid " +
                            "_LIMIT_");

            ps.setLong(1, search.getPrimaryKey().getEntityId());

            try (var rs = ps.executeQuery()) {
                while(rs.next()) {
                    var harmonizedTariffScheduleCode = HarmonizedTariffScheduleCodeFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new HarmonizedTariffScheduleCodePK(rs.getLong(1)));
                    var harmonizedTariffScheduleCodeDetail = harmonizedTariffScheduleCode.getLastDetail();

                    harmonizedTariffScheduleCodeResultTransfers.add(new HarmonizedTariffScheduleCodeResultTransfer(
                            harmonizedTariffScheduleCodeDetail.getCountryGeoCode().getLastDetail().getGeoCodeName(),
                            harmonizedTariffScheduleCodeDetail.getHarmonizedTariffScheduleCodeName(),
                            includeHarmonizedTariffScheduleCode ? itemControl.getHarmonizedTariffScheduleCodeTransfer(userVisit, harmonizedTariffScheduleCode) : null));
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return harmonizedTariffScheduleCodeResultTransfers;
    }

}
