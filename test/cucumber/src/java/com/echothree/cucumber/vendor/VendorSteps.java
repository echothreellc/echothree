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

package com.echothree.cucumber.vendor;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateVendorResult;
import com.echothree.control.user.vendor.common.VendorUtil;
import com.echothree.control.user.vendor.common.result.EditVendorResult;
import com.echothree.cucumber.util.persona.BasePersona;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.EditMode;
import io.cucumber.java8.En;
import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class VendorSteps
        implements En {

    public VendorSteps() {
        When("^the user begins entering a new vendor$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorForm).isNull();
                    assertThat(persona.vendorUniversalSpec).isNull();

                    persona.createVendorForm = PartyUtil.getHome().getCreateVendorForm();
                });

        When("^the user adds the new vendor$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorForm).isNotNull();

                    var partyUtil = PartyUtil.getHome();
                    var createVendorForm = partyUtil.getCreateVendorForm();

                    createVendorForm.set(persona.createVendorForm.get());

                    var commandResult = partyUtil.createVendor(persona.userVisitPK, createVendorForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateVendorResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastVendorName = commandResult.getHasErrors() ? null : result.getVendorName();
                        persona.lastPartyName = commandResult.getHasErrors() ? null : result.getPartyName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createVendorForm = null;
                });

        When("^the user sets the status of the last vendor added to ([^\"]*)$",
                (String vendorStatusChoice) -> {
                    var persona = CurrentPersona.persona;

                    setVendorStatus(persona, persona.lastVendorName, vendorStatusChoice);
                });
        
        When("^the user begins specifying a vendor to edit$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createVendorForm).isNull();
                    assertThat(persona.entityListItemUniversalSpec).isNull();

                    persona.vendorUniversalSpec = VendorUtil.getHome().getVendorUniversalSpec();
                });

        When("^the user begins editing the vendor$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.vendorUniversalSpec;

                    assertThat(spec).isNotNull();

                    var commandForm = VendorUtil.getHome().getEditVendorForm();

                    commandForm.setSpec(spec);
                    commandForm.setEditMode(EditMode.LOCK);

                    var commandResult = VendorUtil.getHome().editVendor(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    if(!commandResult.getHasErrors()) {
                        var executionResult = commandResult.getExecutionResult();
                        var result = (EditVendorResult)executionResult.getResult();

                        persona.vendorEdit = result.getEdit();
                    }
                });

        When("^the user finishes editing the vendor$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var spec = persona.vendorUniversalSpec;
                    var edit = persona.vendorEdit;

                    assertThat(edit).isNotNull();

                    var commandForm = VendorUtil.getHome().getEditVendorForm();

                    commandForm.setSpec(spec);
                    commandForm.setEdit(edit);
                    commandForm.setEditMode(EditMode.UPDATE);

                    var commandResult = VendorUtil.getHome().editVendor(persona.userVisitPK, commandForm);
                    LastCommandResult.commandResult = commandResult;

                    persona.vendorUniversalSpec = null;
                    persona.vendorEdit = null;
                });

        When("^the user sets the vendor's vendor name to ([a-zA-Z0-9-_]*)$",
                (String vendorName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorSpec = persona.vendorUniversalSpec;

                    assertThat(createVendorForm != null || vendorSpec != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setVendorName(vendorName);
                    } else {
                        vendorSpec.setVendorName(vendorName);
                    }
                });

        When("^the user sets the vendor's vendor name to the last vendor added$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorSpec = persona.vendorUniversalSpec;

                    assertThat(createVendorForm != null || vendorSpec != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setVendorName(persona.lastVendorName);
                    } else {
                        vendorSpec.setVendorName(persona.lastVendorName);
                    }
                });

        When("^the user sets the vendor's new vendor name to ([a-zA-Z0-9-_]*)$",
                (String vendorName) -> {
                    var persona = CurrentPersona.persona;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(vendorEdit != null).isTrue();

                    vendorEdit.setVendorName(vendorName);
                });

        When("^the user sets the vendor's type to ([a-zA-Z0-9-_]*)$",
                (String vendorTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setVendorTypeName(vendorTypeName);
                    } else {
                        vendorEdit.setVendorTypeName(vendorTypeName);
                    }
                });

        When("^the user sets the vendor's cancellation policy to ([a-zA-Z0-9-_]*)$",
                (String cancellationPolicyName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setCancellationPolicyName(cancellationPolicyName);
                    } else {
                        vendorEdit.setCancellationPolicyName(cancellationPolicyName);
                    }
                });

        When("^the user sets the vendor's return policy to ([a-zA-Z0-9-_]*)$",
                (String returnPolicyName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setReturnPolicyName(returnPolicyName);
                    } else {
                        vendorEdit.setReturnPolicyName(returnPolicyName);
                    }
                });

        When("^the user sets the vendor's AP GL account to ([a-zA-Z0-9-_]*)$",
                (String apGlAccountName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setApGlAccountName(apGlAccountName);
                    } else {
                        vendorEdit.setApGlAccountName(apGlAccountName);
                    }
                });

        When("^the user sets the vendor's minimum purchase order lines to ([a-zA-Z0-9-_]*)$",
                (String minimumPurchaseOrderLines) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setMinimumPurchaseOrderLines(minimumPurchaseOrderLines);
                    } else {
                        vendorEdit.setMinimumPurchaseOrderLines(minimumPurchaseOrderLines);
                    }
                });

        When("^the user sets the vendor's maximum purchase order lines to ([a-zA-Z0-9-_]*)$",
                (String maximumPurchaseOrderLines) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setMinimumPurchaseOrderLines(maximumPurchaseOrderLines);
                    } else {
                        vendorEdit.setMinimumPurchaseOrderLines(maximumPurchaseOrderLines);
                    }
                });

        When("^the user sets the vendor's minimum purchase order amount to ([^\"]*)$",
                (String minimumPurchaseOrderAmountName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setVendorTypeName(minimumPurchaseOrderAmountName);
                    } else {
                        vendorEdit.setVendorTypeName(minimumPurchaseOrderAmountName);
                    }
                });

        When("^the user sets the vendor's maximum purchase order amount to ([^\"]*)$",
                (String maximumPurchaseOrderAmountName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setVendorTypeName(maximumPurchaseOrderAmountName);
                    } else {
                        vendorEdit.setVendorTypeName(maximumPurchaseOrderAmountName);
                    }
                });

        When("^the user sets the vendor to (use|not use) use item purchasing categories$",
                (String useItemPurchasingCategories) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    useItemPurchasingCategories = Boolean.valueOf(useItemPurchasingCategories.equals("use")).toString();
                    if(createVendorForm != null) {
                        createVendorForm.setUseItemPurchasingCategories(useItemPurchasingCategories);
                    } else {
                        vendorEdit.setUseItemPurchasingCategories(useItemPurchasingCategories);
                    }
                });

        When("^the user sets the vendor's default item alias type to ([a-zA-Z0-9-_]*)$",
                (String defaultItemAliasTypeName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setDefaultItemAliasTypeName(defaultItemAliasTypeName);
                    } else {
                        vendorEdit.setDefaultItemAliasTypeName(defaultItemAliasTypeName);
                    }
                });

        When("^the user sets the vendor's first name to \"([^\"]*)\"$",
                (String firstName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setFirstName(firstName);
                    } else {
                        vendorEdit.setFirstName(firstName);
                    }
                });

        When("^the user sets the vendor's middle name to \"([^\"]*)\"$",
                (String middleName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setMiddleName(middleName);
                    } else {
                        vendorEdit.setMiddleName(middleName);
                    }
                });

        When("^the user sets the vendor's last name to \"([^\"]*)\"$",
                (String lastName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setLastName(lastName);
                    } else {
                        vendorEdit.setLastName(lastName);
                    }
                });

        When("^the user sets the vendor's name to \"([^\"]*)\"$",
                (String name) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setName(name);
                    } else {
                        vendorEdit.setName(name);
                    }
                });

        When("^the user sets the vendor's preferred language to \"([^\"]*)\"$",
                (String preferredLanguageIsoName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setPreferredLanguageIsoName(preferredLanguageIsoName);
                    } else {
                        vendorEdit.setPreferredLanguageIsoName(preferredLanguageIsoName);
                    }
                });

        When("^the user sets the vendor's preferred currency to \"([^\"]*)\"$",
                (String preferredCurrencyIsoName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
                    } else {
                        vendorEdit.setPreferredCurrencyIsoName(preferredCurrencyIsoName);
                    }
                });

        When("^the user sets the vendor's preferred time zone to \"([^\"]*)\"$",
                (String preferredJavaTimeZoneName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
                    } else {
                        vendorEdit.setPreferredJavaTimeZoneName(preferredJavaTimeZoneName);
                    }
                });

        When("^the user sets the vendor's preferred date time format to \"([^\"]*)\"$",
                (String preferredDateTimeFormatName) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(createVendorForm != null || vendorEdit != null).isTrue();

                    if(createVendorForm != null) {
                        createVendorForm.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
                    } else {
                        vendorEdit.setPreferredDateTimeFormatName(preferredDateTimeFormatName);
                    }
                });

        When("^the user sets the vendor email address to \"([^\"]*)\"$",
                (String emailAddress) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;

                    assertThat(createVendorForm).isNotNull();

                    createVendorForm.setEmailAddress(emailAddress);
                });

        When("^the user indicates the vendor (does|does not) allow solicitations to the email address$",
                (String allowSolicitation) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;

                    assertThat(createVendorForm).isNotNull();

                    allowSolicitation = Boolean.valueOf(allowSolicitation.equals("does")).toString();
                    createVendorForm.setAllowSolicitation(allowSolicitation);
                });

        When("^the user indicates the vendor default hold until complete should be (set|not set)$",
                (String holdUntilComplete) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;

                    assertThat(createVendorForm).isNotNull();

                    holdUntilComplete = Boolean.valueOf(holdUntilComplete.equals("set")).toString();
                    createVendorForm.setHoldUntilComplete(holdUntilComplete);
                });

        When("^the user indicates the vendor default for allow backorders should be (set|not set)$",
                (String allowBackorders) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;

                    assertThat(createVendorForm).isNotNull();

                    allowBackorders = Boolean.valueOf(allowBackorders.equals("set")).toString();
                    createVendorForm.setAllowBackorders(allowBackorders);
                });

        When("^the user indicates the vendor default for allow substitutions should be (set|not set)$",
                (String allowSubstitutions) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;

                    assertThat(createVendorForm).isNotNull();

                    allowSubstitutions = Boolean.valueOf(allowSubstitutions.equals("set")).toString();
                    createVendorForm.setAllowSubstitutions(allowSubstitutions);
                });

        When("^the user indicates the vendor default for allow combining shipments should be (set|not set)$",
                (String allowCombiningShipments) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;

                    assertThat(createVendorForm).isNotNull();

                    allowCombiningShipments = Boolean.valueOf(allowCombiningShipments.equals("set")).toString();
                    createVendorForm.setAllowCombiningShipments(allowCombiningShipments);
                });

        When("^the user indicates the vendor default for allow reference duplicates should be (set|not set)$",
                (String allowReferenceDuplicates) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;

                    assertThat(createVendorForm).isNotNull();

                    allowReferenceDuplicates = Boolean.valueOf(allowReferenceDuplicates.equals("set")).toString();
                    createVendorForm.setAllowReferenceDuplicates(allowReferenceDuplicates);
                });

        When("^the user indicates the vendor default for hold until complete should be (set|not set)$",
                (String holdUntilComplete) -> {
                    var persona = CurrentPersona.persona;
                    var createVendorForm = persona.createVendorForm;

                    assertThat(createVendorForm).isNotNull();

                    holdUntilComplete = Boolean.valueOf(holdUntilComplete.equals("set")).toString();
                    createVendorForm.setHoldUntilComplete(holdUntilComplete);
                });

        When("^the user sets the vendor's reference validation pattern to \"([^\"]*)\"$",
                (String referenceValidationPattern) -> {
                    var persona = CurrentPersona.persona;
                    var vendorEdit = persona.vendorEdit;

                    assertThat(vendorEdit).isNotNull();

                    vendorEdit.setReferenceValidationPattern(referenceValidationPattern);
                });
    }

    private void setVendorStatus(BasePersona persona, String vendorName, String vendorStatusChoice)
            throws NamingException {
        var partyUtil = PartyUtil.getHome();
        var setVendorStatusForm = partyUtil.getSetVendorStatusForm();

        setVendorStatusForm.setVendorName(vendorName);
        setVendorStatusForm.setVendorStatusChoice(vendorStatusChoice);

        var commandResult = partyUtil.setVendorStatus(persona.userVisitPK, setVendorStatusForm);

        LastCommandResult.commandResult = commandResult;
    }

}
