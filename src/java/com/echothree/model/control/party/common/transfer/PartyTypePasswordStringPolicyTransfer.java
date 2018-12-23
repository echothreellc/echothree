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

package com.echothree.model.control.party.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PartyTypePasswordStringPolicyTransfer
        extends BaseTransfer {
    
    private PartyTypeTransfer partyType;
    private Boolean forceChangeAfterCreate;
    private Boolean forceChangeAfterReset;
    private Boolean allowChange;
    private Integer passwordHistory;
    private Long unformattedMinimumPasswordLifetime;
    private String minimumPasswordLifetime;
    private Long unformattedMaximumPasswordLifetime;
    private String maximumPasswordLifetime;
    private Long unformattedExpirationWarningTime;
    private String expirationWarningTime;
    private Integer expiredLoginsPermitted;
    private Integer minimumLength;
    private Integer maximumLength;
    private Integer requiredDigitCount;
    private Integer requiredLetterCount;
    private Integer requiredUpperCaseCount;
    private Integer requiredLowerCaseCount;
    private Integer maximumRepeated;
    private Integer minimumCharacterTypes;
    
    /** Creates a new instance of PartyTypePasswordStringPolicyTransfer */
    public PartyTypePasswordStringPolicyTransfer(PartyTypeTransfer partyType, Boolean forceChangeAfterCreate, Boolean forceChangeAfterReset,
            Boolean allowChange, Integer passwordHistory, Long unformattedMinimumPasswordLifetime, String minimumPasswordLifetime,
            Long unformattedMaximumPasswordLifetime, String maximumPasswordLifetime, Long unformattedExpirationWarningTime, String expirationWarningTime,
            Integer expiredLoginsPermitted, Integer minimumLength, Integer maximumLength, Integer requiredDigitCount, Integer requiredLetterCount,
            Integer requiredUpperCaseCount, Integer requiredLowerCaseCount, Integer maximumRepeated, Integer minimumCharacterTypes) {
        this.partyType = partyType;
        this.forceChangeAfterCreate = forceChangeAfterCreate;
        this.forceChangeAfterReset = forceChangeAfterReset;
        this.allowChange = allowChange;
        this.passwordHistory = passwordHistory;
        this.unformattedMinimumPasswordLifetime = unformattedMinimumPasswordLifetime;
        this.minimumPasswordLifetime = minimumPasswordLifetime;
        this.unformattedMaximumPasswordLifetime = unformattedMaximumPasswordLifetime;
        this.maximumPasswordLifetime = maximumPasswordLifetime;
        this.unformattedExpirationWarningTime = unformattedExpirationWarningTime;
        this.expirationWarningTime = expirationWarningTime;
        this.expiredLoginsPermitted = expiredLoginsPermitted;
        this.minimumLength = minimumLength;
        this.maximumLength = maximumLength;
        this.requiredDigitCount = requiredDigitCount;
        this.requiredLetterCount = requiredLetterCount;
        this.requiredUpperCaseCount = requiredUpperCaseCount;
        this.requiredLowerCaseCount = requiredLowerCaseCount;
        this.maximumRepeated = maximumRepeated;
        this.minimumCharacterTypes = minimumCharacterTypes;
    }

    /**
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    /**
     * @return the forceChangeAfterCreate
     */
    public Boolean getForceChangeAfterCreate() {
        return forceChangeAfterCreate;
    }

    /**
     * @param forceChangeAfterCreate the forceChangeAfterCreate to set
     */
    public void setForceChangeAfterCreate(Boolean forceChangeAfterCreate) {
        this.forceChangeAfterCreate = forceChangeAfterCreate;
    }

    /**
     * @return the forceChangeAfterReset
     */
    public Boolean getForceChangeAfterReset() {
        return forceChangeAfterReset;
    }

    /**
     * @param forceChangeAfterReset the forceChangeAfterReset to set
     */
    public void setForceChangeAfterReset(Boolean forceChangeAfterReset) {
        this.forceChangeAfterReset = forceChangeAfterReset;
    }

    /**
     * @return the allowChange
     */
    public Boolean getAllowChange() {
        return allowChange;
    }

    /**
     * @param allowChange the allowChange to set
     */
    public void setAllowChange(Boolean allowChange) {
        this.allowChange = allowChange;
    }

    /**
     * @return the passwordHistory
     */
    public Integer getPasswordHistory() {
        return passwordHistory;
    }

    /**
     * @param passwordHistory the passwordHistory to set
     */
    public void setPasswordHistory(Integer passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    /**
     * @return the unformattedMinimumPasswordLifetime
     */
    public Long getUnformattedMinimumPasswordLifetime() {
        return unformattedMinimumPasswordLifetime;
    }

    /**
     * @param unformattedMinimumPasswordLifetime the unformattedMinimumPasswordLifetime to set
     */
    public void setUnformattedMinimumPasswordLifetime(Long unformattedMinimumPasswordLifetime) {
        this.unformattedMinimumPasswordLifetime = unformattedMinimumPasswordLifetime;
    }

    /**
     * @return the minimumPasswordLifetime
     */
    public String getMinimumPasswordLifetime() {
        return minimumPasswordLifetime;
    }

    /**
     * @param minimumPasswordLifetime the minimumPasswordLifetime to set
     */
    public void setMinimumPasswordLifetime(String minimumPasswordLifetime) {
        this.minimumPasswordLifetime = minimumPasswordLifetime;
    }

    /**
     * @return the unformattedMaximumPasswordLifetime
     */
    public Long getUnformattedMaximumPasswordLifetime() {
        return unformattedMaximumPasswordLifetime;
    }

    /**
     * @param unformattedMaximumPasswordLifetime the unformattedMaximumPasswordLifetime to set
     */
    public void setUnformattedMaximumPasswordLifetime(Long unformattedMaximumPasswordLifetime) {
        this.unformattedMaximumPasswordLifetime = unformattedMaximumPasswordLifetime;
    }

    /**
     * @return the maximumPasswordLifetime
     */
    public String getMaximumPasswordLifetime() {
        return maximumPasswordLifetime;
    }

    /**
     * @param maximumPasswordLifetime the maximumPasswordLifetime to set
     */
    public void setMaximumPasswordLifetime(String maximumPasswordLifetime) {
        this.maximumPasswordLifetime = maximumPasswordLifetime;
    }

    /**
     * @return the unformattedExpirationWarningTime
     */
    public Long getUnformattedExpirationWarningTime() {
        return unformattedExpirationWarningTime;
    }

    /**
     * @param unformattedExpirationWarningTime the unformattedExpirationWarningTime to set
     */
    public void setUnformattedExpirationWarningTime(Long unformattedExpirationWarningTime) {
        this.unformattedExpirationWarningTime = unformattedExpirationWarningTime;
    }

    /**
     * @return the expirationWarningTime
     */
    public String getExpirationWarningTime() {
        return expirationWarningTime;
    }

    /**
     * @param expirationWarningTime the expirationWarningTime to set
     */
    public void setExpirationWarningTime(String expirationWarningTime) {
        this.expirationWarningTime = expirationWarningTime;
    }

    /**
     * @return the expiredLoginsPermitted
     */
    public Integer getExpiredLoginsPermitted() {
        return expiredLoginsPermitted;
    }

    /**
     * @param expiredLoginsPermitted the expiredLoginsPermitted to set
     */
    public void setExpiredLoginsPermitted(Integer expiredLoginsPermitted) {
        this.expiredLoginsPermitted = expiredLoginsPermitted;
    }

    /**
     * @return the minimumLength
     */
    public Integer getMinimumLength() {
        return minimumLength;
    }

    /**
     * @param minimumLength the minimumLength to set
     */
    public void setMinimumLength(Integer minimumLength) {
        this.minimumLength = minimumLength;
    }

    /**
     * @return the maximumLength
     */
    public Integer getMaximumLength() {
        return maximumLength;
    }

    /**
     * @param maximumLength the maximumLength to set
     */
    public void setMaximumLength(Integer maximumLength) {
        this.maximumLength = maximumLength;
    }

    /**
     * @return the requiredDigitCount
     */
    public Integer getRequiredDigitCount() {
        return requiredDigitCount;
    }

    /**
     * @param requiredDigitCount the requiredDigitCount to set
     */
    public void setRequiredDigitCount(Integer requiredDigitCount) {
        this.requiredDigitCount = requiredDigitCount;
    }

    /**
     * @return the requiredLetterCount
     */
    public Integer getRequiredLetterCount() {
        return requiredLetterCount;
    }

    /**
     * @param requiredLetterCount the requiredLetterCount to set
     */
    public void setRequiredLetterCount(Integer requiredLetterCount) {
        this.requiredLetterCount = requiredLetterCount;
    }

    /**
     * @return the requiredUpperCaseCount
     */
    public Integer getRequiredUpperCaseCount() {
        return requiredUpperCaseCount;
    }

    /**
     * @param requiredUpperCaseCount the requiredUpperCaseCount to set
     */
    public void setRequiredUpperCaseCount(Integer requiredUpperCaseCount) {
        this.requiredUpperCaseCount = requiredUpperCaseCount;
    }

    /**
     * @return the requiredLowerCaseCount
     */
    public Integer getRequiredLowerCaseCount() {
        return requiredLowerCaseCount;
    }

    /**
     * @param requiredLowerCaseCount the requiredLowerCaseCount to set
     */
    public void setRequiredLowerCaseCount(Integer requiredLowerCaseCount) {
        this.requiredLowerCaseCount = requiredLowerCaseCount;
    }

    /**
     * @return the maximumRepeated
     */
    public Integer getMaximumRepeated() {
        return maximumRepeated;
    }

    /**
     * @param maximumRepeated the maximumRepeated to set
     */
    public void setMaximumRepeated(Integer maximumRepeated) {
        this.maximumRepeated = maximumRepeated;
    }

    /**
     * @return the minimumCharacterTypes
     */
    public Integer getMinimumCharacterTypes() {
        return minimumCharacterTypes;
    }

    /**
     * @param minimumCharacterTypes the minimumCharacterTypes to set
     */
    public void setMinimumCharacterTypes(Integer minimumCharacterTypes) {
        this.minimumCharacterTypes = minimumCharacterTypes;
    }
    
}
