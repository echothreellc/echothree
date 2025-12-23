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

package com.echothree.ui.web.cms.action;

import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.GetItemDescriptionResult;
import com.echothree.control.user.item.common.result.GetItemDescriptionTypeResult;
import com.echothree.model.control.core.common.CoreProperties;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.core.common.MimeTypes;
import com.echothree.model.control.core.common.transfer.EntityAttributeTypeTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeUsageTypeTransfer;
import com.echothree.model.control.item.common.ItemOptions;
import com.echothree.model.control.item.common.ItemProperties;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTransfer;
import com.echothree.model.control.item.common.transfer.ItemDescriptionTypeTransfer;
import com.echothree.model.control.item.common.transfer.ItemTransfer;
import com.echothree.ui.web.cms.framework.ByteArrayStreamInfo;
import com.echothree.ui.web.cms.framework.CmsBaseDownloadAction;
import com.echothree.ui.web.cms.persistence.CmsCacheBean;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.view.client.web.struts.sprout.annotation.SproutAction;
import com.echothree.view.client.web.struts.sprout.annotation.SproutProperty;
import com.echothree.view.client.web.struts.sslext.config.SecureActionMapping;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.enterprise.inject.spi.Unmanaged;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.infinispan.Cache;

@SproutAction(
    path = "/ItemDescription",
    mappingClass = SecureActionMapping.class,
    properties = {
        @SproutProperty(property = "secure", value = "any")
    }
)
public class ItemDescriptionAction
        extends CmsBaseDownloadAction {

    private static Unmanaged<CmsCacheBean> unmanagedCmsCacheBean = new Unmanaged<>(CmsCacheBean.class);

    private static final String fqnItemDescription = "/com/echothree/ui/web/cms/ItemDescription";
    private static final String cacheCompositeImage = "CompositeImage";
    private static final String cacheTransferObject = "TransferObject";
    private static final String attributeCompositeImage = "CompositeImage";
    private static final String attributeTransferObject = "TransferObject";

    /** Creates a new instance of ItemDescriptionAction */
    public ItemDescriptionAction() {
        super(false, false);
    }

    protected ItemDescriptionTypeTransfer getItemDescriptionType(HttpServletRequest request, ItemDescriptionNames itemDescriptionNames)
            throws NamingException {
        ItemDescriptionTypeTransfer itemDescriptionType = null;
        var commandForm = ItemUtil.getHome().getGetItemDescriptionTypeForm();

        commandForm.setItemDescriptionTypeName(itemDescriptionNames.itemDescriptionTypeName);
        
        commandForm.setTransferProperties(new TransferProperties()
                .addClassAndProperty(ItemDescriptionTypeTransfer.class, ItemProperties.PREFERRED_MIME_TYPE)
                .addClassAndProperty(ItemDescriptionTypeTransfer.class, ItemProperties.PREFERRED_HEIGHT)
                .addClassAndProperty(ItemDescriptionTypeTransfer.class, ItemProperties.PREFERRED_WIDTH)
                .addClassAndProperty(ItemDescriptionTypeTransfer.class, ItemProperties.MIME_TYPE_USAGE_TYPE)
                .addClassAndProperty(MimeTypeUsageTypeTransfer.class, CoreProperties.MIME_TYPE_USAGE_TYPE_NAME));

        var commandResult = ItemUtil.getHome().getItemDescriptionType(getUserVisitPK(request), commandForm);
        
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemDescriptionTypeResult)executionResult.getResult();
            
            itemDescriptionType = result.getItemDescriptionType();
        }
        
        return itemDescriptionType;
    }
    
    protected ItemDescriptionTransfer getItemDescription(HttpServletRequest request, ItemDescriptionNames itemDescriptionNames, Set<String> options)
            throws NamingException {
        var commandForm = ItemUtil.getHome().getGetItemDescriptionForm();
        ItemDescriptionTransfer itemDescription = null;

        commandForm.setItemDescriptionTypeName(itemDescriptionNames.itemDescriptionTypeName);
        commandForm.setItemName(itemDescriptionNames.itemName);
        commandForm.setLanguageIsoName(itemDescriptionNames.languageIsoName);
        commandForm.setReferrer(request.getHeader("Referer"));
        
        commandForm.setOptions(options);

        commandForm.setTransferProperties(new TransferProperties()
                .addClassAndProperty(ItemDescriptionTransfer.class, ItemProperties.ITEM)
                .addClassAndProperty(ItemDescriptionTransfer.class, ItemProperties.MIME_TYPE)
                .addClassAndProperty(ItemDescriptionTransfer.class, ItemProperties.HEIGHT)
                .addClassAndProperty(ItemDescriptionTransfer.class, ItemProperties.WIDTH)
                .addClassAndProperty(ItemTransfer.class, ItemProperties.ENTITY_INSTANCE)
                .addClassAndProperty(EntityInstanceTransfer.class, CoreProperties.ENTITY_TIME)
                .addClassAndProperty(EntityTimeTransfer.class, CoreProperties.UNFORMATTED_CREATED_TIME)
                .addClassAndProperty(EntityTimeTransfer.class, CoreProperties.UNFORMATTED_MODIFIED_TIME)
                .addClassAndProperty(MimeTypeTransfer.class, CoreProperties.MIME_TYPE_NAME)
                .addClassAndProperty(MimeTypeTransfer.class, CoreProperties.ENTITY_ATTRIBUTE_TYPE)
                .addClassAndProperty(EntityAttributeTypeTransfer.class, CoreProperties.ENTITY_ATTRIBUTE_TYPE_NAME));

        var commandResult = ItemUtil.getHome().getItemDescription(getUserVisitPK(request), commandForm);
        if(!commandResult.hasErrors()) {
            var executionResult = commandResult.getExecutionResult();
            var result = (GetItemDescriptionResult)executionResult.getResult();

            itemDescription = result.getItemDescription();
        }

        return itemDescription;
    }

    protected ItemDescriptionTransfer getCachedItemDescription(HttpServletRequest request, Cache<String, Object> cache, String fqn, ItemDescriptionNames itemDescriptionNames)
            throws NamingException {
        var itemDescription = (ItemDescriptionTransfer)getCacheEntry(cache, fqn, cacheTransferObject);

        if(itemDescription == null) {
            Set<String> options = new HashSet<>();
            options.add(ItemOptions.ItemDescriptionIncludeBlob);
            options.add(ItemOptions.ItemDescriptionIncludeClob);
            options.add(ItemOptions.ItemDescriptionIncludeString);
            options.add(ItemOptions.ItemDescriptionIncludeETag);
            options.add(ItemOptions.ItemDescriptionIncludeImageDescription);

            itemDescription = getItemDescription(request, itemDescriptionNames, options);

            if(itemDescription != null) {
                putCacheEntry(cache, fqn, cacheTransferObject, itemDescription);
            }
        }

        return itemDescription;
    }

    protected String getFqn(ItemDescriptionNames itemDescriptionNames) {
        // Build the Fqn from the most general component, to the most specific component. When the maximum cache size is exceeded,
        // it will prune the last component's contents, and that portion of the fqn off the tree. The first two components will
        // continue to exist.
        var itemName = itemDescriptionNames.itemName;
        var fqn = new StringBuilder(fqnItemDescription).append('/')
                .append(itemDescriptionNames.languageIsoName.toLowerCase(Locale.getDefault())).append('/')
                .append(itemDescriptionNames.itemDescriptionTypeName.toLowerCase(Locale.getDefault())).append('/');

        // Must prefer to use the itemName over the itemNames.
        if(itemName == null) {
            var itemNames = itemDescriptionNames.itemNames;
            
            for(var i = 0; i < itemNames.length ; i++) {
                if(i != 0) {
                    fqn.append(':');
                }
                
                fqn.append(itemNames[i].toLowerCase(Locale.getDefault()));
            }
        } else {
            fqn.append(itemDescriptionNames.itemName.toLowerCase(Locale.getDefault()));
        }
                
        return fqn.toString();
    }

    protected Object getCacheEntry(Cache<String, Object> cache, String fqn, String key) {
        Object cacheEntry = null;

        if(cache != null && fqn != null) {
            cacheEntry = cache.get(fqn + "/" + key);
        }

        return cacheEntry;
    }

    protected void putCacheEntry(Cache<String, Object> cache, String fqn, String key, Object value) {
        if(cache != null && fqn != null) {
            cache.put(fqn + "/" + key, value);
        }
    }

    protected void removeCacheEntry(Cache<String, Object> cache, String fqn, String key) {
        if(cache != null && fqn != null) {
            cache.remove(fqn + "/" + key);
        }
    }
    
    protected ImageReader getImageReader(ItemDescriptionTransfer itemDescription) {
        var memoryCacheImageInputStream = new MemoryCacheImageInputStream(itemDescription.getBlobDescription().getByteArrayInputStream());
        var imageReaders = ImageIO.getImageReadersByMIMEType(itemDescription.getMimeType().getMimeTypeName());
        var imageReader = imageReaders.hasNext() ? imageReaders.next() : null;

        if(imageReader != null) {
            imageReader.setInput(memoryCacheImageInputStream);

            try {
                // If there isn't at least one image, then return null.
                if(imageReader.getNumImages(true) == 0) {
                    imageReader = null;
                }
            } catch (IOException ioe) {
                // Nothing, height and width stay null.
            }
        }

        return imageReader;
    }
    
    protected CompositeImage buildCompositeImage(HttpServletRequest request, Cache<String, Object> cache, String fqn, ItemDescriptionNames itemDescriptionNames)
            throws NamingException {
        CompositeImage compositeImage = null;
        var itemDescriptionType = getItemDescriptionType(request, itemDescriptionNames);

        if(itemDescriptionType != null) {
            var mimeTypeUsageTypeName = itemDescriptionType.getMimeTypeUsageType().getMimeTypeUsageTypeName();

            if(mimeTypeUsageTypeName.equals(MimeTypeUsageTypes.IMAGE.name())) {
                var preferredMimeType = itemDescriptionType.getPreferredMimeType();
                var mimeTypeName = preferredMimeType == null ? null : preferredMimeType.getMimeTypeName();
                var preferredHeight = itemDescriptionType.getPreferredHeight();
                var preferredWidth = itemDescriptionType.getPreferredWidth();

                if(preferredHeight != null && preferredWidth != null) {
                    var itemNames = itemDescriptionNames.itemNames;
                    var compositeWidth = preferredWidth * itemNames.length;
                    var bufferedImage = new BufferedImage(compositeWidth, preferredHeight, BufferedImage.TYPE_INT_RGB);
                    var graphics2D = bufferedImage.createGraphics();
                    var imagesIncluded = new BitSet(itemNames.length);
                    var hasMissingItemDescription = false;
                    var hasItemDescriptionError = false;
                    long maximumModifiedTime = 0;

                    graphics2D.setColor(new Color(255, 255, 255)); // Always white.
                    graphics2D.fillRect (0, 0, compositeWidth, preferredHeight);

                    for(var i = 0; i < itemNames.length ; i++) {
                        itemDescriptionNames.itemName = itemDescriptionNames.itemNames[i];
                        var individualFqn = getFqn(itemDescriptionNames);
                        var itemDescription = getCachedItemDescription(request, cache, individualFqn, itemDescriptionNames);
                        itemDescriptionNames.itemName = null;

                        if(itemDescription != null) {
                            var imageReader = getImageReader(itemDescription);

                            if(imageReader != null) {
                                BufferedImage originalBufferedImage = null;

                                imagesIncluded.set(i);

                                // If preferredMimeType was null, use the MimeType of the first image encountered.
                                // There must be a valid MimeType when leaving here.
                                if(mimeTypeName == null) {
                                    mimeTypeName = itemDescription.getMimeType().getMimeTypeName();
                                }

                                try {
                                    originalBufferedImage = imageReader.read(0);
                                } catch(IOException ioe) {
                                    // Ignore, image reading failed, leave originalBufferedImage null.
                                }

                                if(originalBufferedImage != null) {
                                    var width = Math.min(preferredWidth, itemDescription.getWidth());
                                    var height = Math.min(preferredHeight, itemDescription.getHeight());

                                    graphics2D.drawImage(originalBufferedImage, preferredWidth * i, 0, width, height, null);
                                } else {
                                    hasItemDescriptionError = true;
                                    break;
                                }
                            } else {
                                hasItemDescriptionError = true;
                                break;
                            }

                            var entityTime = itemDescription.getItem().getEntityInstance().getEntityTime();
                            var unformattedCreatedTime = entityTime.getUnformattedCreatedTime();
                            var unformattedModifiedTime = entityTime.getUnformattedModifiedTime();

                            if(unformattedModifiedTime == null) {
                                unformattedModifiedTime = unformattedCreatedTime;
                            }

                            maximumModifiedTime = Math.max(maximumModifiedTime, unformattedModifiedTime);
                        } else {
                            hasMissingItemDescription = true;
                        }
                    }

                    if(!hasItemDescriptionError) {
                        var imageWriters = ImageIO.getImageWritersByMIMEType(mimeTypeName);
                        var imageWriter = imageWriters.hasNext() ? imageWriters.next() : null;

                        if(imageWriter != null) {
                            var byteArrayOutputStream = new ByteArrayOutputStream();
                            var quality = itemDescriptionType.getQuality();

                            if(quality == null) {
                                quality = 90; // Default quality
                            }

                            try {
                                var scaledQualtity = (float)quality / 100.0f;
                                var iwp = imageWriter.getDefaultWriteParam();

                                if(iwp.canWriteCompressed()) {
                                    var compressionTypes = iwp.getCompressionTypes();

                                    iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

                                    // JPEG doesn't appear to "need" this, but GIF throws an IllegalStateException without it. Pick the first choice.
                                    if(compressionTypes.length > 0) {
                                        iwp.setCompressionType(compressionTypes[0]);
                                    }

                                    iwp.setCompressionQuality(scaledQualtity);
                                }

                                ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(byteArrayOutputStream);
                                var img = new IIOImage(bufferedImage, null, null);

                                imageWriter.setOutput(imageOutputStream);
                                imageWriter.write(null, img, iwp);
                                imageWriter.dispose();
                                imageOutputStream.close();
                            } catch (IOException ioe) {
                                // Exception? Nuke the result.
                                byteArrayOutputStream = null;
                            }

                            if(byteArrayOutputStream != null) {
                                var imagesIncludedETagComponent = new StringBuilder();
                                var chunks = itemNames.length / 64 + (itemNames.length % 64 == 0 ? 0 : 1);
                                var currentBit = 0;

                                for(var i = 0; i < chunks; i++) {
                                    long value = 0;
                                    var chunkLength = Math.min(itemNames.length - i * 64, 64);

                                    for(var j = 0; j < chunkLength; j++) {
                                        value |= imagesIncluded.get(currentBit++) ? 1L << j : 0L;
                                    }

                                    imagesIncludedETagComponent.append(String.format("%016x", value));
                                }

                                compositeImage = new CompositeImage(mimeTypeName, new ByteArray(byteArrayOutputStream.toByteArray()),
                                        imagesIncludedETagComponent.toString(), maximumModifiedTime, hasMissingItemDescription);
                            }
                        }
                    }

                    if(graphics2D != null) {
                        graphics2D.dispose();
                    }
                }
            }
        }

        return compositeImage;
    }

    @Override
    protected String getETag(HttpServletRequest request)
            throws NamingException {
        var itemDescriptionNames = new ItemDescriptionNames(request);
        String eTag = null;

        if(itemDescriptionNames.hasAllNames()) {
            var cmsCacheBeanInstance = unmanagedCmsCacheBean.newInstance();
            var cmsCacheBean = cmsCacheBeanInstance.produce().inject().postConstruct().get();
            var cache = cmsCacheBean.getCache();
            var fqn = getFqn(itemDescriptionNames);

            if(itemDescriptionNames.itemName == null) {
                // Multiple item images.
                CompositeImage compositeImage = null;

                try {
                    // This is protection in case this web application is redeployed. CompositeImages coming out of
                    // the cache will cause a ClassCastException to be thrown. This simply removes them and regenerates
                    // the image.
                    compositeImage = (CompositeImage)getCacheEntry(cache, fqn, cacheCompositeImage);
                } catch (ClassCastException cce) {
                    removeCacheEntry(cache, fqn, cacheCompositeImage);
                }

                if(compositeImage == null) {
                    compositeImage = buildCompositeImage(request, cache, fqn, itemDescriptionNames);

                    if(compositeImage != null && !compositeImage.hasMissingItemDescription) {
                        putCacheEntry(cache, fqn, cacheCompositeImage, compositeImage);
                    }
                }

                if(compositeImage != null) {
                    request.setAttribute(attributeCompositeImage, compositeImage);

                    eTag = new StringBuilder(compositeImage.imagesIncludedETagComponent).append('-')
                            .append(Integer.toHexString(compositeImage.blobDescription.length())).append('-')
                            .append(Long.toHexString(compositeImage.lastModified)).toString();
                }
            } else {
                // Single item image.
                var itemDescription = getCachedItemDescription(request, cache, fqn, itemDescriptionNames);

                if(itemDescription != null) {
                    request.setAttribute(attributeTransferObject, itemDescription);

                    eTag = itemDescription.getETag();
                }
            }

            cmsCacheBeanInstance.preDestroy().dispose();
        }

        return eTag;
    }

    @Override
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        StreamInfo streamInfo = null;
        var itemDescription = (ItemDescriptionTransfer)request.getAttribute(attributeTransferObject);

        if(itemDescription == null) {
            var compositeImage = (CompositeImage)request.getAttribute(attributeCompositeImage);
            
            if(compositeImage != null) {
                streamInfo = new ByteArrayStreamInfo(compositeImage.mimeTypeName, new ByteArrayInputStream(compositeImage.blobDescription.byteArrayValue()),
                        compositeImage.lastModified);
            }
        } else {
            var mimeType = itemDescription.getMimeType();
            var entityAttributeType = mimeType == null ? null : mimeType.getEntityAttributeType();
            var entityAttributeTypeName = entityAttributeType == null ? null : entityAttributeType.getEntityAttributeTypeName();
            var entityTime = itemDescription.getItem().getEntityInstance().getEntityTime();
            var modifiedTime = entityTime.getUnformattedModifiedTime();
            byte bytes[] = null;

            if(entityAttributeTypeName == null || EntityAttributeTypes.CLOB.name().equals(entityAttributeTypeName)) {
                if(entityAttributeTypeName == null) {
                    bytes = itemDescription.getStringDescription().getBytes(StandardCharsets.UTF_8);
                } else {
                    bytes = itemDescription.getClobDescription().getBytes(StandardCharsets.UTF_8);
                }
            } else {
                if(EntityAttributeTypes.BLOB.name().equals(entityAttributeTypeName)) {
                    bytes = itemDescription.getBlobDescription().byteArrayValue();
                }
            }

            if(bytes != null) {
                streamInfo = new ByteArrayStreamInfo(mimeType == null ? MimeTypes.TEXT_PLAIN.mimeTypeName() : mimeType.getMimeTypeName(),
                        new ByteArrayInputStream(bytes), modifiedTime == null ? entityTime.getUnformattedCreatedTime() : modifiedTime);
            }
        }

        return streamInfo;
    }
    
}
