package com.echothree.model.control.core.server.graphql;

import graphql.annotations.annotationTypes.GraphQLUnion;

@GraphQLUnion(possibleTypes = {EntityBooleanAttributeObject.class, EntityCollectionAttributesObject.class, EntityClobAttributeObject.class,
        EntityDateAttributeObject.class, EntityEntityAttributeObject.class, EntityGeoPointAttributeObject.class, EntityIntegerAttributeObject.class,
        EntityListItemAttributeObject.class, EntityLongAttributeObject.class, EntityMultipleListItemAttributesObject.class, EntityNameAttributeObject.class,
        EntityStringAttributeObject.class, EntityTimeAttributeObject.class})
public interface AttributeInterface {

}
