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

package com.echothree.model.control.core.server.logic;

import com.echothree.control.user.core.common.edit.LinkTypeEdit;
import com.echothree.control.user.core.common.edit.LinkTypeTranslationEdit;
import com.echothree.control.user.core.common.spec.LinkTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.LinkCardinalities;
import com.echothree.model.control.core.common.LinkDirectionalities;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.exception.DuplicateLinkTypeNameException;
import com.echothree.model.control.core.common.exception.InvalidLinkTypeEndpointsException;
import com.echothree.model.control.core.common.exception.InvalidLinkTypeInverseException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.LinkTypeInUseException;
import com.echothree.model.control.core.common.exception.LinkTypeParentCycleException;
import com.echothree.model.control.core.common.exception.MissingRequiredLinkTypeDescriptionException;
import com.echothree.model.control.core.common.exception.UnknownLinkTypeNameException;
import com.echothree.model.control.core.server.control.LinkTypeControl;
import com.echothree.model.data.core.common.pk.LinkTypePK;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.LinkCardinality;
import com.echothree.model.data.core.server.entity.LinkDirectionality;
import com.echothree.model.data.core.server.entity.LinkType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.HashSet;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LinkTypeLogic
        extends BaseLogic {

    @Inject
    LinkTypeControl linkTypeControl;

    @Inject
    EntityInstanceLogic entityInstanceLogic;

    @Inject
    EntityTypeLogic entityTypeLogic;

    @Inject
    LinkCardinalityLogic linkCardinalityLogic;

    @Inject
    LinkDirectionalityLogic linkDirectionalityLogic;

    @Inject
    MimeTypeLogic mimeTypeLogic;

    protected LinkTypeLogic() {
        super();
    }

    public record Definition(LinkType parentLinkType, EntityType sourceEntityType, EntityType targetEntityType,
            LinkCardinality linkCardinality, LinkDirectionality linkDirectionality, boolean isTransitive,
            LinkType inverseLinkType, int sortOrder) {
    }

    public LinkType getLinkTypeByName(ExecutionErrorAccumulator eea, String linkTypeName,
            EntityPermission entityPermission) {
        var linkType = linkTypeControl.getLinkTypeByName(linkTypeName, entityPermission);

        if(linkType == null) {
            handleExecutionError(UnknownLinkTypeNameException.class, eea,
                    ExecutionErrors.UnknownLinkTypeName.name(), linkTypeName);
        }

        return linkType;
    }

    public LinkType getLinkTypeByName(ExecutionErrorAccumulator eea, String linkTypeName) {
        return getLinkTypeByName(eea, linkTypeName, EntityPermission.READ_ONLY);
    }

    public LinkType getLinkTypeByNameForUpdate(ExecutionErrorAccumulator eea, String linkTypeName) {
        return getLinkTypeByName(eea, linkTypeName, EntityPermission.READ_WRITE);
    }

    public LinkType getLinkTypeByUniversalSpec(ExecutionErrorAccumulator eea,
            LinkTypeUniversalSpec linkTypeUniversalSpec, EntityPermission entityPermission) {
        LinkType linkType = null;
        var linkTypeName = linkTypeUniversalSpec.getLinkTypeName();
        var parameterCount = (linkTypeName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(linkTypeUniversalSpec);

        if(parameterCount == 1) {
            if(linkTypeName != null) {
                linkType = getLinkTypeByName(eea, linkTypeName, entityPermission);
            } else {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, linkTypeUniversalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.LinkType.name());

                if(!hasErrors(eea)) {
                    linkType = linkTypeControl.getLinkTypeByEntityInstance(entityInstance, entityPermission);
                }
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return linkType;
    }

    public LinkType getLinkTypeByUniversalSpec(ExecutionErrorAccumulator eea, LinkTypeUniversalSpec linkTypeUniversalSpec) {
        return getLinkTypeByUniversalSpec(eea, linkTypeUniversalSpec, EntityPermission.READ_ONLY);
    }

    public LinkType getLinkTypeByUniversalSpecForUpdate(ExecutionErrorAccumulator eea, LinkTypeUniversalSpec linkTypeUniversalSpec) {
        return getLinkTypeByUniversalSpec(eea, linkTypeUniversalSpec, EntityPermission.READ_WRITE);
    }

    private static LinkCardinalities getCardinality(String linkCardinalityName) {
        LinkCardinalities linkCardinality = null;

        if(linkCardinalityName != null) {
            try {
                linkCardinality = LinkCardinalities.valueOf(linkCardinalityName);
            } catch(IllegalArgumentException ignored) {
                // Unknown names are handled by definition validation.
            }
        }

        return linkCardinality;
    }

    private static LinkDirectionalities getDirectionality(String linkDirectionalityName) {
        LinkDirectionalities linkDirectionality = null;

        if(linkDirectionalityName != null) {
            try {
                linkDirectionality = LinkDirectionalities.valueOf(linkDirectionalityName);
            } catch(IllegalArgumentException ignored) {
                // Unknown names are handled by definition validation.
            }
        }

        return linkDirectionality;
    }

    public static LinkCardinalities getInverseCardinality(LinkCardinalities linkCardinality) {
        return linkCardinality == null ? null : switch(linkCardinality) {
            case ONE_TO_ONE -> LinkCardinalities.ONE_TO_ONE;
            case ONE_TO_MANY -> LinkCardinalities.MANY_TO_ONE;
            case MANY_TO_ONE -> LinkCardinalities.ONE_TO_MANY;
            case MANY_TO_MANY -> LinkCardinalities.MANY_TO_MANY;
        };
    }

    public static boolean isCardinalityCompatible(LinkCardinalities childCardinality, LinkCardinalities parentCardinality) {
        var childHasManyTargets = childCardinality == LinkCardinalities.ONE_TO_MANY || childCardinality == LinkCardinalities.MANY_TO_MANY;
        var childHasManySources = childCardinality == LinkCardinalities.MANY_TO_ONE || childCardinality == LinkCardinalities.MANY_TO_MANY;
        var parentHasManyTargets = parentCardinality == LinkCardinalities.ONE_TO_MANY || parentCardinality == LinkCardinalities.MANY_TO_MANY;
        var parentHasManySources = parentCardinality == LinkCardinalities.MANY_TO_ONE || parentCardinality == LinkCardinalities.MANY_TO_MANY;

        return childCardinality != null && parentCardinality != null
                && (!childHasManyTargets || parentHasManyTargets) && (!childHasManySources || parentHasManySources);
    }

    public Definition getDefinition(ExecutionErrorAccumulator eea, LinkTypeEdit edit, LinkType currentLinkType) {
        Definition definition = null;

        var sourceEntityType = entityTypeLogic.getEntityTypeByName(eea,
                edit.getSourceComponentVendorName(), edit.getSourceEntityTypeName());
        if(!hasErrors(eea)) {
            var targetEntityType = entityTypeLogic.getEntityTypeByName(eea, edit.getTargetComponentVendorName(), edit.getTargetEntityTypeName());

            if(!hasErrors(eea)) {
                var linkCardinality = linkCardinalityLogic.getLinkCardinalityByNameForUpdate(eea, edit.getLinkCardinalityName());

                if(!hasErrors(eea)) {
                    var linkDirectionality = linkDirectionalityLogic.getLinkDirectionalityByNameForUpdate(eea, edit.getLinkDirectionalityName());

                    if(!hasErrors(eea)) {
                        var parentLinkType = edit.getParentLinkTypeName() == null ? null : getLinkTypeByNameForUpdate(eea, edit.getParentLinkTypeName());

                        if(!hasErrors(eea)) {
                            var inverseLinkType = edit.getInverseLinkTypeName() == null ? null : getLinkTypeByNameForUpdate(eea, edit.getInverseLinkTypeName());

                            if(!hasErrors(eea)) {
                                definition = new Definition(parentLinkType, sourceEntityType, targetEntityType, linkCardinality, linkDirectionality,
                                        Boolean.parseBoolean(edit.getIsTransitive()), inverseLinkType, Integer.parseInt(edit.getSortOrder()));

                                validateDefinition(eea, edit.getLinkTypeName(), currentLinkType, definition);
                            }
                        }
                    }
                }
            }
        }

        return hasErrors(eea) ? null : definition;
    }

    public void validateDefinition(ExecutionErrorAccumulator eea, String linkTypeName,
            LinkType currentLinkType, Definition definition) {
        var linkCardinality = getCardinality(definition.linkCardinality().getLastDetail().getLinkCardinalityName());
        var linkDirectionality = getDirectionality(definition.linkDirectionality().getLastDetail().getLinkDirectionalityName());
        var isSymmetric = linkDirectionality == LinkDirectionalities.SYMMETRIC;

        var valid = true;

        if(getInverseCardinality(linkCardinality) == null || (linkDirectionality != LinkDirectionalities.DIRECTED && !isSymmetric)
                || (isSymmetric && linkCardinality != getInverseCardinality(linkCardinality))) {
            handleExecutionError(InvalidLinkTypeEndpointsException.class, eea, ExecutionErrors.InvalidLinkTypeEndpoints.name(), linkTypeName);
            valid = false;
        }

        if(valid && (isSymmetric || definition.isTransitive()) && !definition.sourceEntityType().equals(definition.targetEntityType())) {
            handleExecutionError(InvalidLinkTypeEndpointsException.class, eea, ExecutionErrors.InvalidLinkTypeEndpoints.name(), linkTypeName);
            valid = false;
        }

        var visited = new HashSet<LinkTypePK>();
        for(var parentLinkType = definition.parentLinkType(); valid && parentLinkType != null;
                parentLinkType = parentLinkType.getLastDetail().getParentLinkType()) {
            if(parentLinkType.equals(currentLinkType) || !visited.add(parentLinkType.getPrimaryKey())) {
                handleExecutionError(LinkTypeParentCycleException.class, eea, ExecutionErrors.LinkTypeParentCycle.name(), linkTypeName);
                valid = false;
            }

            var parentLinkTypeDetail = parentLinkType.getLastDetail();
            if(valid && (!definition.sourceEntityType().equals(parentLinkTypeDetail.getSourceEntityType())
                    || !definition.targetEntityType().equals(parentLinkTypeDetail.getTargetEntityType())
                    || !isCardinalityCompatible(linkCardinality, getCardinality(parentLinkTypeDetail.getLinkCardinality().getLastDetail().getLinkCardinalityName()))
                    || (getDirectionality(parentLinkTypeDetail.getLinkDirectionality().getLastDetail().getLinkDirectionalityName()) == LinkDirectionalities.SYMMETRIC && !isSymmetric))) {
                handleExecutionError(InvalidLinkTypeEndpointsException.class, eea, ExecutionErrors.InvalidLinkTypeEndpoints.name(), linkTypeName, parentLinkTypeDetail.getLinkTypeName());
                valid = false;
            }
        }

        var inverseLinkType = definition.inverseLinkType();
        if(valid && inverseLinkType != null) {
            var inverseLinkTypeDetail = inverseLinkType.getLastDetail();

            if(isSymmetric || inverseLinkType.equals(currentLinkType)
                    || !definition.sourceEntityType().equals(inverseLinkTypeDetail.getTargetEntityType())
                    || !definition.targetEntityType().equals(inverseLinkTypeDetail.getSourceEntityType())
                    || getInverseCardinality(linkCardinality) != getCardinality(inverseLinkTypeDetail.getLinkCardinality().getLastDetail().getLinkCardinalityName())
                    || !definition.linkDirectionality().equals(inverseLinkTypeDetail.getLinkDirectionality())
                    || definition.isTransitive() != inverseLinkTypeDetail.getIsTransitive()
                    || (inverseLinkTypeDetail.getInverseLinkType() != null && !inverseLinkTypeDetail.getInverseLinkType().equals(currentLinkType))) {
                handleExecutionError(InvalidLinkTypeInverseException.class, eea, ExecutionErrors.InvalidLinkTypeInverse.name(), linkTypeName, inverseLinkTypeDetail.getLinkTypeName());
                valid = false;
            }
        }

        if(valid && currentLinkType != null) {
            var childLinkTypes = linkTypeControl.getLinkTypesForUpdate(linkTypeControl.getLinkTypeFilter(currentLinkType, null, null, null, null, null));
            var childLinkTypeIterator = childLinkTypes.iterator();

            while(valid && childLinkTypeIterator.hasNext()) {
                var childLinkType = childLinkTypeIterator.next();
                var childLinkTypeDetail = childLinkType.getLastDetail();

                if(!definition.sourceEntityType().equals(childLinkTypeDetail.getSourceEntityType())
                        || !definition.targetEntityType().equals(childLinkTypeDetail.getTargetEntityType())
                        || !isCardinalityCompatible(getCardinality(childLinkTypeDetail.getLinkCardinality().getLastDetail().getLinkCardinalityName()), linkCardinality)
                        || (isSymmetric && getDirectionality(childLinkTypeDetail.getLinkDirectionality().getLastDetail().getLinkDirectionalityName()) != LinkDirectionalities.SYMMETRIC)) {
                    handleExecutionError(InvalidLinkTypeEndpointsException.class, eea, ExecutionErrors.InvalidLinkTypeEndpoints.name(), linkTypeName, childLinkTypeDetail.getLinkTypeName());
                    valid = false;
                }
            }
        }
    }

    public MimeType checkGuidance(ExecutionErrorAccumulator eea, LinkTypeTranslationEdit edit) {
        return mimeTypeLogic.checkMimeType(eea, edit.getGuidanceMimeTypeName(), edit.getGuidance(), MimeTypeUsageTypes.TEXT.name(),
                ExecutionErrors.MissingRequiredGuidanceMimeTypeName.name(), ExecutionErrors.MissingRequiredGuidance.name(),
                ExecutionErrors.UnknownGuidanceMimeTypeName.name(), ExecutionErrors.UnknownGuidanceMimeTypeUsage.name());
    }

    public LinkType createLinkType(ExecutionErrorAccumulator eea, LinkTypeEdit edit, Language language, BasePK createdBy) {
        LinkType linkType = null;

        if(linkTypeControl.getLinkTypeByName(edit.getLinkTypeName()) != null) {
            handleExecutionError(DuplicateLinkTypeNameException.class, eea, ExecutionErrors.DuplicateLinkTypeName.name(), edit.getLinkTypeName());
        } else {
            var definition = getDefinition(eea, edit, null);

            if(!hasErrors(eea)) {
                var guidanceMimeType = checkGuidance(eea, edit);

                if(edit.getDescription() == null && edit.getGuidance() != null) {
                    handleExecutionError(MissingRequiredLinkTypeDescriptionException.class, eea, ExecutionErrors.MissingRequiredLinkTypeDescription.name());
                }

                if(!hasErrors(eea)) {
                    linkType = linkTypeControl.createLinkType(edit.getLinkTypeName(), definition.parentLinkType(), definition.sourceEntityType(),
                            definition.targetEntityType(), definition.linkCardinality(), definition.linkDirectionality(), definition.isTransitive(),
                            definition.inverseLinkType(), definition.sortOrder(), createdBy);

                    if(edit.getDescription() != null) {
                        linkTypeControl.createLinkTypeTranslation(linkType, language, edit.getDescription(), guidanceMimeType, edit.getGuidance(), createdBy);
                    }
                    updateInverse(linkType, null, definition.inverseLinkType(), createdBy);
                }
            }
        }

        return linkType;
    }

    public void updateLinkType(LinkType linkType, LinkTypeEdit edit, Definition definition, BasePK updatedBy) {
        var oldInverseLinkType = linkType.getLastDetail().getInverseLinkType();
        var linkTypeDetailValue = linkTypeControl.getLinkTypeDetailValueForUpdate(linkType);

        linkTypeDetailValue.setLinkTypeName(edit.getLinkTypeName());
        linkTypeDetailValue.setParentLinkTypePK(definition.parentLinkType() == null ? null : definition.parentLinkType().getPrimaryKey());
        linkTypeDetailValue.setSourceEntityTypePK(definition.sourceEntityType().getPrimaryKey());
        linkTypeDetailValue.setTargetEntityTypePK(definition.targetEntityType().getPrimaryKey());
        linkTypeDetailValue.setLinkCardinalityPK(definition.linkCardinality().getPrimaryKey());
        linkTypeDetailValue.setLinkDirectionalityPK(definition.linkDirectionality().getPrimaryKey());
        linkTypeDetailValue.setIsTransitive(definition.isTransitive());
        linkTypeDetailValue.setInverseLinkTypePK(definition.inverseLinkType() == null ? null : definition.inverseLinkType().getPrimaryKey());
        linkTypeDetailValue.setSortOrder(definition.sortOrder());
        linkTypeControl.updateLinkTypeFromValue(linkTypeDetailValue, updatedBy);

        updateInverse(linkType, oldInverseLinkType, definition.inverseLinkType(), updatedBy);
    }

    private void updateInverse(LinkType linkType, LinkType oldInverseLinkType, LinkType newInverseLinkType, BasePK updatedBy) {
        if(oldInverseLinkType != null && !Objects.equals(oldInverseLinkType, newInverseLinkType)) {
            var oldInverseLinkTypeDetailValue = linkTypeControl.getLinkTypeDetailValueForUpdate(oldInverseLinkType);

            oldInverseLinkTypeDetailValue.setInverseLinkTypePK(null);

            linkTypeControl.updateLinkTypeFromValue(oldInverseLinkTypeDetailValue, updatedBy);
        }

        if(newInverseLinkType != null) {
            var newInverseLinkTypeDetailValue = linkTypeControl.getLinkTypeDetailValueForUpdate(newInverseLinkType);

            newInverseLinkTypeDetailValue.setInverseLinkTypePK(linkType.getPrimaryKey());

            linkTypeControl.updateLinkTypeFromValue(newInverseLinkTypeDetailValue, updatedBy);
        }
    }

    public void deleteLinkType(ExecutionErrorAccumulator eea, LinkType linkType, BasePK deletedBy) {
        if(linkTypeControl.countLinkTypes(linkTypeControl.getLinkTypeFilter(linkType, null, null, null, null, null)) != 0) {
            handleExecutionError(LinkTypeInUseException.class, eea, ExecutionErrors.LinkTypeInUse.name(), linkType.getLastDetail().getLinkTypeName());
        } else {
            updateInverse(linkType, linkType.getLastDetail().getInverseLinkType(), null, deletedBy);
            linkTypeControl.deleteLinkType(linkType, deletedBy);
        }
    }
}
