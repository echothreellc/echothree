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

package com.echothree.cucumber.core;

import com.echothree.control.user.core.common.CoreUtil;
import com.echothree.control.user.core.common.result.GetLinksResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.model.control.core.common.transfer.LinkTransfer;
import com.echothree.model.data.core.common.LinkConstants;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.transfer.Limit;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static com.echothree.cucumber.util.command.CommandResultAssertions.requireSuccess;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkSteps implements En {

    private record Endpoint(String name, String entityRef, String uuid, boolean directionality) {
    }

    private final Map<String, Endpoint> endpoints = new LinkedHashMap<>();
    private final Map<String, String> linkTypes = new LinkedHashMap<>();
    private final String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    private LinkTransfer returnedLink;
    private GetLinksResult returnedLinks;

    public LinkSteps() {
        Given("the user has disposable Link endpoints", () -> {
            for(var alias : List.of("A", "B", "C", "D", "X")) {
                var name = "CucumberLink" + alias + suffix;
                var directionality = alias.equals("X");
                String entityRef;

                if(directionality) {
                    var form = CoreUtil.getHome().getCreateLinkDirectionalityForm();
                    form.setLinkDirectionalityName(name);
                    form.setIsDefault("false");
                    form.setSortOrder("10");

                    var result = CoreUtil.getHome().createLinkDirectionality(CurrentPersona.persona.userVisitPK, form);
                    requireSuccess(result);

                    entityRef = result.getExecutionResult().getResult().getEntityRef();
                } else {
                    var form = CoreUtil.getHome().getCreateLinkCardinalityForm();
                    form.setLinkCardinalityName(name);
                    form.setIsDefault("false");
                    form.setSortOrder("10");

                    var result = CoreUtil.getHome().createLinkCardinality(CurrentPersona.persona.userVisitPK, form);
                    requireSuccess(result);

                    entityRef = result.getExecutionResult().getResult().getEntityRef();
                }

                endpoints.put(alias, new Endpoint(name, entityRef, null, directionality));

                var form = CoreUtil.getHome().getGenerateUuidForm();
                form.setEntityRef(entityRef);
                form.setForceRegeneration("false");

                var result = CoreUtil.getHome().generateUuid(CurrentPersona.persona.userVisitPK, form);
                requireSuccess(result);

                endpoints.put(alias, new Endpoint(name, entityRef, result.getExecutionResult().getResult().getUuid(), directionality));
            }
        });

        Given("the user has Link type {string} with cardinality {string} and directionality {string}",
                (String alias, String cardinality, String directionality) -> {
                    var name = "CucumberLinks" + alias + suffix;

                    var form = CoreUtil.getHome().getCreateLinkTypeForm();
                    form.setLinkTypeName(name);
                    form.setSourceComponentVendorName("ECHO_THREE");
                    form.setSourceEntityTypeName("LinkCardinality");
                    form.setTargetComponentVendorName("ECHO_THREE");
                    form.setTargetEntityTypeName("LinkCardinality");
                    form.setLinkCardinalityName(cardinality);
                    form.setLinkDirectionalityName(directionality);
                    form.setIsTransitive("false");
                    form.setSortOrder("10");

                    var result = CoreUtil.getHome().createLinkType(CurrentPersona.persona.userVisitPK, form);
                    requireSuccess(result);

                    linkTypes.put(alias, name);
                });

        When("the user {word} a Link with:", (String operation, DataTable values) -> {
            assertThat(operation).isIn("creates", "retrieves", "deletes");
            returnedLink = null;

            switch(operation) {
                case "creates" -> {
                    var form = CoreUtil.getHome().getCreateLinkForm();
                    apply(values, form);
                    var result = CoreUtil.getHome().createLink(CurrentPersona.persona.userVisitPK, form);
                    LastCommandResult.commandResult = result;
                    if(result.getExecutionResult() != null && result.getExecutionResult().getResult() != null) {
                        returnedLink = result.getExecutionResult().getResult().getLink();
                    }
                }
                case "retrieves" -> {
                    var form = CoreUtil.getHome().getGetLinkForm();
                    apply(values, form);
                    var result = CoreUtil.getHome().getLink(CurrentPersona.persona.userVisitPK, form);
                    LastCommandResult.commandResult = result;
                    if(!result.hasErrors()) {
                        returnedLink = result.getExecutionResult().getResult().getLink();
                    }
                }
                case "deletes" -> {
                    var form = CoreUtil.getHome().getDeleteLinkForm();
                    apply(values, form);
                    LastCommandResult.commandResult = CoreUtil.getHome().deleteLink(CurrentPersona.persona.userVisitPK, form);
                }
            }
        });

        When("the user lists Links with:", (DataTable values) -> list(values, null));

        When("the user lists Links with limit {string} and:", (String limit, DataTable values) -> list(values, limit));

        Then("the returned Link connects {word} to {word}", (String source, String target) -> {
            assertThat(returnedLink).isNotNull();
            assertThat(returnedLink.getSourceEntityInstance().getEntityRef()).isEqualTo(endpoints.get(source).entityRef());
            assertThat(returnedLink.getTargetEntityInstance().getEntityRef()).isEqualTo(endpoints.get(target).entityRef());
        });

        Then("the returned Links contain {int} row(s)", (Integer rowCount) -> {
            assertThat(returnedLinks).isNotNull();
            assertThat(returnedLinks.getLinks()).hasSize(rowCount);
        });

        Then("the returned Link total count is {long}", (Long totalCount) -> {
            assertThat(returnedLinks.getLinkCount()).isEqualTo(totalCount);
        });

        Then("the returned Link filters are:", (DataTable values) -> {
            assertThat(returnedLinks).isNotNull();

            values.asMap(String.class, String.class).forEach((field, expected) -> {
                switch(field) {
                    case "LinkType" -> {
                        if(expected.equals("<none>")) {
                            assertThat(returnedLinks.getLinkType()).isNull();
                        } else {
                            assertThat(returnedLinks.getLinkType()).isNotNull();
                            assertThat(returnedLinks.getLinkType().getLinkTypeName()).isEqualTo(linkTypes.get(expected));
                        }
                    }
                    case "SourceEntityInstance", "TargetEntityInstance" -> {
                        var entityInstance = field.equals("SourceEntityInstance") ? returnedLinks.getSourceEntityInstance()
                                : returnedLinks.getTargetEntityInstance();
                        if(expected.equals("<none>")) {
                            assertThat(entityInstance).as(field).isNull();
                        } else {
                            assertThat(entityInstance).as(field).isNotNull();
                            assertThat(entityInstance.getEntityRef()).as(field).isEqualTo(endpoints.get(expected).entityRef());
                        }
                    }
                    default -> throw new IllegalArgumentException("Unknown Link result filter: " + field);
                }
            });

        });

        Then("the returned Links are:", (DataTable values) -> {
            var expected = values.asMaps().stream().map(row -> List.of(linkTypes.get(row.get("Type")),
                    endpoints.get(row.get("Source")).entityRef(), endpoints.get(row.get("Target")).entityRef())).toList();
            var actual = returnedLinks.getLinks().stream().map(link -> List.of(link.getLinkType().getLinkTypeName(),
                    link.getSourceEntityInstance().getEntityRef(), link.getTargetEntityInstance().getEntityRef())).toList();

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        });

        Then("the Link field {word} validation error {word} should occur", (String field, String error) -> {
            var result = LastCommandResult.commandResult;

            assertThat(result.hasValidationErrors()).isTrue();
            assertThat(result.getValidationResult().getValidationMessages().containsKey(field, error)).isTrue();
        });

        When("the user deletes Link test type {string}", (String alias) -> {
            var form = CoreUtil.getHome().getDeleteLinkTypeForm();

            form.setLinkTypeName(linkTypes.get(alias));

            var result = CoreUtil.getHome().deleteLinkType(CurrentPersona.persona.userVisitPK, form);
            LastCommandResult.commandResult = result;

            if(!result.hasErrors()) linkTypes.remove(alias);
        });

        When("the user deletes Link test endpoint {word}", this::deleteEndpoint);

        After(Integer.MAX_VALUE, () -> {
            for(var alias : new ArrayList<>(linkTypes.keySet())) {
                var form = CoreUtil.getHome().getDeleteLinkTypeForm();

                form.setLinkTypeName(linkTypes.get(alias));

                requireSuccess(CoreUtil.getHome().deleteLinkType(CurrentPersona.persona.userVisitPK, form));

                linkTypes.remove(alias);
            }

            for(var alias : new ArrayList<>(endpoints.keySet())) deleteEndpoint(alias);
        });
    }

    private void apply(DataTable values, BaseForm form) {
        values.asMap(String.class, String.class).forEach((field, value) -> {
            String resolved = value;

            switch(field) {
                case "LinkTypeName" -> resolved = linkTypes.getOrDefault(value, value);
                case "SourceEntityRef", "TargetEntityRef" -> {
                    if(endpoints.containsKey(value))
                        resolved = endpoints.get(value).entityRef();
                }
                case "SourceUuid", "TargetUuid" -> {
                    if(endpoints.containsKey(value))
                        resolved = endpoints.get(value).uuid();
                }
            }

            form.set(field, resolved);
        });
    }

    private void list(DataTable values, String limit) throws Exception {
        var form = CoreUtil.getHome().getGetLinksForm();

        apply(values, form);

        if(limit != null)
            form.setLimits(Map.of(LinkConstants.ENTITY_TYPE_NAME, new Limit(limit)));

        var result = CoreUtil.getHome().getLinks(CurrentPersona.persona.userVisitPK, form);
        LastCommandResult.commandResult = result;

        returnedLinks = result.hasErrors() ? null : result.getExecutionResult().getResult();
    }

    private void deleteEndpoint(String alias) throws Exception {
        var endpoint = endpoints.get(alias);

        CommandResult<?> result;
        if(endpoint.directionality()) {
            var form = CoreUtil.getHome().getDeleteLinkDirectionalityForm();

            form.setLinkDirectionalityName(endpoint.name());

            result = CoreUtil.getHome().deleteLinkDirectionality(CurrentPersona.persona.userVisitPK, form);
        } else {
            var form = CoreUtil.getHome().getDeleteLinkCardinalityForm();

            form.setLinkCardinalityName(endpoint.name());

            result = CoreUtil.getHome().deleteLinkCardinality(CurrentPersona.persona.userVisitPK, form);
        }

        requireSuccess(result);
        LastCommandResult.commandResult = result;

        endpoints.remove(alias);
    }
}
