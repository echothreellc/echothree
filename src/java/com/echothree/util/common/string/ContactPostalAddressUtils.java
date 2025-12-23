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

package com.echothree.util.common.string;

import com.echothree.model.control.contact.common.ContactOptions;
import com.echothree.model.control.contact.common.PostalAddressElementTypes;
import com.echothree.model.control.contact.common.transfer.ContactPostalAddressTransfer;
import com.echothree.model.control.contact.common.transfer.PostalAddressLineTransfer;
import com.echothree.model.control.geo.common.GeoCodeAliasTypes;
import com.echothree.model.control.geo.common.GeoOptions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContactPostalAddressUtils {
    
    private ContactPostalAddressUtils() {
        super();
    }
    
    private static class ContactPostalAddressUtilsHolder {
        static ContactPostalAddressUtils instance = new ContactPostalAddressUtils();
    }
    
    public static ContactPostalAddressUtils getInstance() {
        return ContactPostalAddressUtilsHolder.instance;
    }
    
    public Set<String> addOptions(Set<String> options) {
        options.add(ContactOptions.PostalAddressFormatIncludeLines);
        options.add(ContactOptions.PostalAddressLineIncludeElements);
        options.add(GeoOptions.CountryIncludeAliases);
        options.add(GeoOptions.StateIncludeAliases);
        
        return options;
    }
    
    private String getCountryAlias(final String geoCodeAliasTypeName, final ContactPostalAddressTransfer contactPostalAddress) {
        String addition;
        var country = contactPostalAddress.getCountryGeoCode();
        
        if(country == null) {
            throw new IllegalArgumentException("CountryGeoCode is not available to get " + geoCodeAliasTypeName + " Alias on ContactPostalAddress TO");
        } else {
            var geoCodeAliases = country.getGeoCodeAliases();

            if(geoCodeAliases == null) {
                throw new IllegalArgumentException("CountryIncludeAliases is a required Option to format ContactPostalAddress TO");
            } else {
                var geoCodeAlias = geoCodeAliases.getMap().get(geoCodeAliasTypeName);

                if(geoCodeAlias == null) {
                    throw new IllegalArgumentException(geoCodeAliasTypeName + " Alias is not available on ContactPostalAddress TO");
                } else {
                    addition = geoCodeAlias.getAlias();
                }
            }
        }
        
        return addition;
    }
    
    private String getLineElementAddition(final ContactPostalAddressTransfer contactPostalAddress, final String postalAddressElementTypeName) {
        String addition = null;
        
        if(postalAddressElementTypeName.equals(PostalAddressElementTypes.PERSONAL_TITLE.name())) {
            var personalTitle = contactPostalAddress.getPersonalTitle();
            
            if(personalTitle != null) {
                addition = personalTitle.getDescription();
            }
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.FIRST_NAME.name())) {
            addition = contactPostalAddress.getFirstName();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.MIDDLE_NAME.name())) {
            addition = contactPostalAddress.getMiddleName();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.LAST_NAME.name())) {
            addition = contactPostalAddress.getLastName();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.NAME_SUFFIX.name())) {
            var nameSuffix = contactPostalAddress.getNameSuffix();
            
            if(nameSuffix != null) {
                addition = nameSuffix.getDescription();
            }
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.COMPANY_NAME.name())) {
            addition = contactPostalAddress.getCompanyName();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.ATTENTION.name())) {
            addition = contactPostalAddress.getAttention();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.ADDRESS_1.name())) {
            addition = contactPostalAddress.getAddress1();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.ADDRESS_2.name())) {
            addition = contactPostalAddress.getAddress2();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.ADDRESS_3.name())) {
            addition = contactPostalAddress.getAddress3();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.CITY.name())) {
            var city = contactPostalAddress.getCityGeoCode();
            
            addition = city == null? contactPostalAddress.getCity(): city.getDescription();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.COUNTY.name())) {
            var county = contactPostalAddress.getCountyGeoCode();
            
            if(county != null) {
                addition = county.getDescription();
            }
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.STATE.name())) {
            var state = contactPostalAddress.getStateGeoCode();
            
            addition = state == null? contactPostalAddress.getState(): state.getDescription();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.STATE_POSTAL_2_LETTER.name())) {
            var state = contactPostalAddress.getStateGeoCode();
            
            if(state == null) {
                throw new IllegalArgumentException("StateGeoCode is not available to get STATE_POSTAL_2_LETTER Alias on ContactPostalAddress TO");
            } else {
                var geoCodeAliases = state.getGeoCodeAliases();
                
                if(geoCodeAliases == null) {
                    throw new IllegalArgumentException("StateIncludeAliases is a required Option to format ContactPostalAddress TO");
                } else {
                    var geoCodeAlias = geoCodeAliases.getMap().get(GeoCodeAliasTypes.POSTAL_2_LETTER.name());
                    
                    if(geoCodeAlias == null) {
                        throw new IllegalArgumentException("STATE_POSTAL_2_LETTER Alias is not available on ContactPostalAddress TO");
                    } else {
                        addition = geoCodeAlias.getAlias();
                    }
                }
            }
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.POSTAL_CODE.name())) {
            var postalCode = contactPostalAddress.getPostalCodeGeoCode();
            
            addition = postalCode == null? contactPostalAddress.getPostalCode(): postalCode.getDescription();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.COUNTRY.name())) {
            addition = contactPostalAddress.getCountryGeoCode().getDescription();
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.COUNTRY_ISO_3_NUMBER.name())) {
            addition = getCountryAlias(GeoCodeAliasTypes.ISO_3_NUMBER.name(), contactPostalAddress);
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.COUNTRY_ISO_3_LETTER.name())) {
            addition = getCountryAlias(GeoCodeAliasTypes.ISO_3_LETTER.name(), contactPostalAddress);
        } else if(postalAddressElementTypeName.equals(PostalAddressElementTypes.COUNTRY_ISO_2_LETTER.name())) {
            addition = getCountryAlias(GeoCodeAliasTypes.ISO_2_LETTER.name(), contactPostalAddress);
        }
        
        return addition == null? "": addition;
    }
    
    private StringBuilder formatContactPostalAddressLine(final ContactPostalAddressTransfer contactPostalAddress, final PostalAddressLineTransfer postalAddressLine) {
        var postalAddressLineElements = postalAddressLine.getPostalAddressLineElements();
        var lineAddition = new StringBuilder();
        
        postalAddressLineElements.getList().forEach((postalAddressLineElement) -> {
            var lineElement = getLineElementAddition(contactPostalAddress, postalAddressLineElement.getPostalAddressElementType().getPostalAddressElementTypeName());
            var lineElementHasLength = lineElement.length() != 0;
            boolean alwaysIncludePrefix = postalAddressLineElement.getAlwaysIncludePrefix();
            boolean alwaysIncludeSuffix = postalAddressLineElement.getAlwaysIncludeSuffix();
            var addedToLine = false;
            if (lineElementHasLength || alwaysIncludePrefix || alwaysIncludeSuffix) {
                var prefix = postalAddressLineElement.getPrefix();
                var suffix = postalAddressLineElement.getSuffix();
                if(prefix != null && (lineElementHasLength || alwaysIncludePrefix)) {
                    lineAddition.append(prefix);
                    addedToLine = true;
                }
                if(lineElement.length() > 0) {
                    lineAddition.append(lineElement);
                    addedToLine = true;
                }
                if(suffix != null && (lineElementHasLength || alwaysIncludeSuffix)) {
                    lineAddition.append(suffix);
                    addedToLine = true;
                }
                if (addedToLine) {
                    lineAddition.append(' ');
                }
            }
        });
        
        return lineAddition;
    }
    
    public List<String> formatContactPostalAddress(final ContactPostalAddressTransfer contactPostalAddress) {
        var postalAddressFormat = contactPostalAddress.getCountryGeoCode().getPostalAddressFormat();
        var postalAddressLines = postalAddressFormat.getPostalAddressLines();
        List<String> result;
        
        if(postalAddressLines == null) {
            throw new IllegalArgumentException("PostalAddressFormatIncludeLines is a required Option to format ContactPostalAddress TO");
        } else {
            result = new ArrayList<>(postalAddressLines.getSize());
            
            for(var postalAddressLine: postalAddressLines.getList()) {
                var resultLine = new StringBuilder();
                var postalAddressLineElements = postalAddressLine.getPostalAddressLineElements();
                
                if(postalAddressLineElements == null) {
                    throw new IllegalArgumentException("PostalAddressLineIncludeElements is a required Option to format ContactPostalAddress TO");
                } else {
                    resultLine.append(formatContactPostalAddressLine(contactPostalAddress, postalAddressLine));

                    var resultLineHasLength = resultLine.length() != 0;
                    if(resultLineHasLength || !postalAddressLine.getCollapseIfEmpty()) {
                        boolean alwaysIncludePrefix = postalAddressLine.getAlwaysIncludePrefix();
                        boolean alwaysIncludeSuffix = postalAddressLine.getAlwaysIncludeSuffix();

                        if(resultLineHasLength || alwaysIncludePrefix || alwaysIncludeSuffix) {
                            var prefix = postalAddressLine.getPrefix();
                            var suffix = postalAddressLine.getSuffix();

                            if(prefix != null && (resultLineHasLength || alwaysIncludePrefix)) {
                                resultLine = new StringBuilder(prefix).append(' ').append(resultLine);
                            }

                            if(suffix != null && (resultLineHasLength || alwaysIncludeSuffix)) {
                                resultLine.append(suffix);
                            }
                        }

                        result.add(resultLine.toString().trim());
                    }
                }
            }
        }
        
        return result;
    }
    
}
