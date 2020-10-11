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

package com.echothree.control.user.party.server.command;

import com.echothree.control.user.party.common.form.CreateDateTimeFormatForm;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateDateTimeFormatCommand
        extends BaseSimpleCommand<CreateDateTimeFormatForm> {

    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;

    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("DateTimeFormatName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("JavaShortDateFormat", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("JavaAbbrevDateFormat", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("JavaAbbrevDateFormatWeekday", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("JavaLongDateFormat", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("JavaLongDateFormatWeekday", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("JavaTimeFormat", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("JavaTimeFormatSeconds", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("UnixShortDateFormat", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("UnixAbbrevDateFormat", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("UnixAbbrevDateFormatWeekday", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("UnixLongDateFormat", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("UnixLongDateFormatWeekday", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("UnixTimeFormat", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("UnixTimeFormatSeconds", FieldType.STRING, true, 1L, 60L),
                new FieldDefinition("ShortDateSeparator", FieldType.STRING, true, 1L, 1L),
                new FieldDefinition("TimeSeparator", FieldType.STRING, true, 1L, 1L),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of CreateDateTimeFormatCommand */
    public CreateDateTimeFormatCommand(UserVisitPK userVisitPK, CreateDateTimeFormatForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        String dateTimeFormatName = form.getDateTimeFormatName();
        DateTimeFormat dateTimeFormat = partyControl.getDateTimeFormatByName(dateTimeFormatName);
        
        if(dateTimeFormat == null ) {
            String javaShortDateFormat = form.getJavaShortDateFormat();
            String javaAbbrevDateFormat = form.getJavaAbbrevDateFormat();
            String javaAbbrevDateFormatWeekday = form.getJavaAbbrevDateFormatWeekday();
            String javaLongDateFormat = form.getJavaLongDateFormat();
            String javaLongDateFormatWeekday = form.getJavaLongDateFormatWeekday();
            String javaTimeFormat = form.getJavaTimeFormat();
            String javaTimeFormatSeconds = form.getJavaTimeFormatSeconds();
            String unixShortDateFormat = form.getUnixShortDateFormat();
            String unixAbbrevDateFormat = form.getUnixAbbrevDateFormat();
            String unixAbbrevDateFormatWeekday = form.getUnixAbbrevDateFormatWeekday();
            String unixLongDateFormat = form.getUnixLongDateFormat();
            String unixLongDateFormatWeekday = form.getUnixLongDateFormatWeekday();
            String unixTimeFormat = form.getUnixTimeFormat();
            String unixTimeFormatSeconds = form.getUnixTimeFormatSeconds();
            String shortDateSeparator = form.getShortDateSeparator();
            String timeSeparator = form.getTimeSeparator();
            var isDefault = Boolean.valueOf(form.getIsDefault());
            var sortOrder = Integer.valueOf(form.getSortOrder());
            var description = form.getDescription();
            var partyPK = getPartyPK();
            
            dateTimeFormat = partyControl.createDateTimeFormat(dateTimeFormatName, javaShortDateFormat, javaAbbrevDateFormat,
                    javaAbbrevDateFormatWeekday, javaLongDateFormat, javaLongDateFormatWeekday, javaTimeFormat,
                    javaTimeFormatSeconds, unixShortDateFormat, unixAbbrevDateFormat, unixAbbrevDateFormatWeekday,
                    unixLongDateFormat, unixLongDateFormatWeekday, unixTimeFormat, unixTimeFormatSeconds, shortDateSeparator,
                    timeSeparator, isDefault, sortOrder, partyPK);
            
            if(description != null) {
                partyControl.createDateTimeFormatDescription(dateTimeFormat, getPreferredLanguage(), description, partyPK);
            }
        } else {
            addExecutionError(ExecutionErrors.DuplicateDateTimeFormatName.name(), dateTimeFormatName);
        }
        
        return null;
    }
    
}
