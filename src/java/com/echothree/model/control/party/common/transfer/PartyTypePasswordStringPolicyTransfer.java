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
     * Returns the partyType.
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * Sets the partyType.
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    /**
     * Returns the forceChangeAfterCreate.
     * @return the forceChangeAfterCreate
     */
    public Boolean getForceChangeAfterCreate() {
        return forceChangeAfterCreate;
    }

    /**
     * Sets the forceChangeAfterCreate.
     * @param forceChangeAfterCreate the forceChangeAfterCreate to set
     */
    public void setForceChangeAfterCreate(Boolean forceChangeAfterCreate) {
        this.forceChangeAfterCreate = forceChangeAfterCreate;
    }

    /**
     * Returns the forceChangeAfterReset.
     * @return the forceChangeAfterReset
     */
    public Boolean getForceChangeAfterReset() {
        return forceChangeAfterReset;
    }

    /**
     * Sets the forceChangeAfterReset.
     * @param forceChangeAfterReset the forceChangeAfterReset to set
     */
    public void setForceChangeAfterReset(Boolean forceChangeAfterReset) {
        this.forceChangeAfterReset = forceChangeAfterReset;
    }

    /**
     * Returns the allowChange.
     * @return the allowChange
     */
    public Boolean getAllowChange() {
        return allowChange;
    }

    /**
     * Sets the allowChange.
     * @param allowChange the allowChange to set
     */
    public void setAllowChange(Boolean allowChange) {
        this.allowChange = allowChange;
    }

    /**
     * Returns the passwordHistory.
     * @return the passwordHistory
     */
    public Integer getPasswordHistory() {
        return passwordHistory;
    }

    /**
     * Sets the passwordHistory.
     * @param passwordHistory the passwordHistory to set
     */
    public void setPasswordHistory(Integer passwordHistory) {
        this.passwordHistory = passwordHistory;
    }

    /**
     * Returns the unformattedMinimumPasswordLifetime.
     * @return the unformattedMinimumPasswordLifetime
     */
    public Long getUnformattedMinimumPasswordLifetime() {
        return unformattedMinimumPasswordLifetime;
    }

    /**
     * Sets the unformattedMinimumPasswordLifetime.
     * @param unformattedMinimumPasswordLifetime the unformattedMinimumPasswordLifetime to set
     */
    public void setUnformattedMinimumPasswordLifetime(Long unformattedMinimumPasswordLifetime) {
        this.unformattedMinimumPasswordLifetime = unformattedMinimumPasswordLifetime;
    }

    /**
     * Returns the minimumPasswordLifetime.
     * @return the minimumPasswordLifetime
     */
    public String getMinimumPasswordLifetime() {
        return minimumPasswordLifetime;
    }

    /**
     * Sets the minimumPasswordLifetime.
     * @param minimumPasswordLifetime the minimumPasswordLifetime to set
     */
    public void setMinimumPasswordLifetime(String minimumPasswordLifetime) {
        this.minimumPasswordLifetime = minimumPasswordLifetime;
    }

    /**
     * Returns the unformattedMaximumPasswordLifetime.
     * @return the unformattedMaximumPasswordLifetime
     */
    public Long getUnformattedMaximumPasswordLifetime() {
        return unformattedMaximumPasswordLifetime;
    }

    /**
     * Sets the unformattedMaximumPasswordLifetime.
     * @param unformattedMaximumPasswordLifetime the unformattedMaximumPasswordLifetime to set
     */
    public void setUnformattedMaximumPasswordLifetime(Long unformattedMaximumPasswordLifetime) {
        this.unformattedMaximumPasswordLifetime = unformattedMaximumPasswordLifetime;
    }

    /**
     * Returns the maximumPasswordLifetime.
     * @return the maximumPasswordLifetime
     */
    public String getMaximumPasswordLifetime() {
        return maximumPasswordLifetime;
    }

    /**
     * Sets the maximumPasswordLifetime.
     * @param maximumPasswordLifetime the maximumPasswordLifetime to set
     */
    public void setMaximumPasswordLifetime(String maximumPasswordLifetime) {
        this.maximumPasswordLifetime = maximumPasswordLifetime;
    }

    /**
     * Returns the unformattedExpirationWarningTime.
     * @return the unformattedExpirationWarningTime
     */
    public Long getUnformattedExpirationWarningTime() {
        return unformattedExpirationWarningTime;
    }

    /**
     * Sets the unformattedExpirationWarningTime.
     * @param unformattedExpirationWarningTime the unformattedExpirationWarningTime to set
     */
    public void setUnformattedExpirationWarningTime(Long unformattedExpirationWarningTime) {
        this.unformattedExpirationWarningTime = unformattedExpirationWarningTime;
    }

    /**
     * Returns the expirationWarningTime.
     * @return the expirationWarningTime
     */
    public String getExpirationWarningTime() {
        return expirationWarningTime;
    }

    /**
     * Sets the expirationWarningTime.
     * @param expirationWarningTime the expirationWarningTime to set
     */
    public void setExpirationWarningTime(String expirationWarningTime) {
        this.expirationWarningTime = expirationWarningTime;
    }

    /**
     * Returns the expiredLoginsPermitted.
     * @return the expiredLoginsPermitted
     */
    public Integer getExpiredLoginsPermitted() {
        return expiredLoginsPermitted;
    }

    /**
     * Sets the expiredLoginsPermitted.
     * @param expiredLoginsPermitted the expiredLoginsPermitted to set
     */
    public void setExpiredLoginsPermitted(Integer expiredLoginsPermitted) {
        this.expiredLoginsPermitted = expiredLoginsPermitted;
    }

    /**
     * Returns the minimumLength.
     * @return the minimumLength
     */
    public Integer getMinimumLength() {
        return minimumLength;
    }

    /**
     * Sets the minimumLength.
     * @param minimumLength the minimumLength to set
     */
    public void setMinimumLength(Integer minimumLength) {
        this.minimumLength = minimumLength;
    }

    /**
     * Returns the maximumLength.
     * @return the maximumLength
     */
    public Integer getMaximumLength() {
        return maximumLength;
    }

    /**
     * Sets the maximumLength.
     * @param maximumLength the maximumLength to set
     */
    public void setMaximumLength(Integer maximumLength) {
        this.maximumLength = maximumLength;
    }

    /**
     * Returns the requiredDigitCount.
     * @return the requiredDigitCount
     */
    public Integer getRequiredDigitCount() {
        return requiredDigitCount;
    }

    /**
     * Sets the requiredDigitCount.
     * @param requiredDigitCount the requiredDigitCount to set
     */
    public void setRequiredDigitCount(Integer requiredDigitCount) {
        this.requiredDigitCount = requiredDigitCount;
    }

    /**
     * Returns the requiredLetterCount.
     * @return the requiredLetterCount
     */
    public Integer getRequiredLetterCount() {
        return requiredLetterCount;
    }

    /**
     * Sets the requiredLetterCount.
     * @param requiredLetterCount the requiredLetterCount to set
     */
    public void setRequiredLetterCount(Integer requiredLetterCount) {
        this.requiredLetterCount = requiredLetterCount;
    }

    /**
     * Returns the requiredUpperCaseCount.
     * @return the requiredUpperCaseCount
     */
    public Integer getRequiredUpperCaseCount() {
        return requiredUpperCaseCount;
    }

    /**
     * Sets the requiredUpperCaseCount.
     * @param requiredUpperCaseCount the requiredUpperCaseCount to set
     */
    public void setRequiredUpperCaseCount(Integer requiredUpperCaseCount) {
        this.requiredUpperCaseCount = requiredUpperCaseCount;
    }

    /**
     * Returns the requiredLowerCaseCount.
     * @return the requiredLowerCaseCount
     */
    public Integer getRequiredLowerCaseCount() {
        return requiredLowerCaseCount;
    }

    /**
     * Sets the requiredLowerCaseCount.
     * @param requiredLowerCaseCount the requiredLowerCaseCount to set
     */
    public void setRequiredLowerCaseCount(Integer requiredLowerCaseCount) {
        this.requiredLowerCaseCount = requiredLowerCaseCount;
    }

    /**
     * Returns the maximumRepeated.
     * @return the maximumRepeated
     */
    public Integer getMaximumRepeated() {
        return maximumRepeated;
    }

    /**
     * Sets the maximumRepeated.
     * @param maximumRepeated the maximumRepeated to set
     */
    public void setMaximumRepeated(Integer maximumRepeated) {
        this.maximumRepeated = maximumRepeated;
    }

    /**
     * Returns the minimumCharacterTypes.
     * @return the minimumCharacterTypes
     */
    public Integer getMinimumCharacterTypes() {
        return minimumCharacterTypes;
    }

    /**
     * Sets the minimumCharacterTypes.
     * @param minimumCharacterTypes the minimumCharacterTypes to set
     */
    public void setMinimumCharacterTypes(Integer minimumCharacterTypes) {
        this.minimumCharacterTypes = minimumCharacterTypes;
    }
    
}
