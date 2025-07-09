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

package com.echothree.model.control.item.server.logic;

import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.MimeTypeUsageTypes;
import com.echothree.model.control.item.common.exception.InvalidItemDescriptionTypeException;
import com.echothree.model.control.item.common.exception.UnknownItemDescriptionTypeNameException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.item.server.entity.ItemBlobDescription;
import com.echothree.model.data.item.server.entity.ItemDescription;
import com.echothree.model.data.item.server.entity.ItemDescriptionType;
import com.echothree.model.data.item.server.value.ItemImageDescriptionTypeValue;
import com.echothree.model.data.item.server.value.ItemImageTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

public class ItemDescriptionLogic
        extends BaseLogic {
    
    private ItemDescriptionLogic() {
        super();
    }
    
    private static class ItemDescriptionLogicHolder {
        static ItemDescriptionLogic instance = new ItemDescriptionLogic();
    }
    
    public static ItemDescriptionLogic getInstance() {
        return ItemDescriptionLogicHolder.instance;
    }

    public String getIndexDefaultItemDescriptionTypeName() {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionType = itemControl.getIndexDefaultItemDescriptionType();
        
        return itemDescriptionType.getLastDetail().getItemDescriptionTypeName();
    }
    
    public boolean isImage(ItemDescriptionType itemDescriptionType) {
        var mimeTypeUsageType = itemDescriptionType.getLastDetail().getMimeTypeUsageType();
        var result = false;

        if(mimeTypeUsageType != null) {
            result = mimeTypeUsageType.getMimeTypeUsageTypeName().equals(MimeTypeUsageTypes.IMAGE.name());
        }

        return result;
    }

    // Find the first available parent ItemDescription.
    public ItemDescription getBestParent(ItemControl itemControl, ItemDescriptionType itemDescriptionType, Item item, Language language) {
        ItemDescription itemDescription = null;
        var itemDescriptionTypeDetail = itemDescriptionType.getLastDetail();

        if(itemDescriptionTypeDetail.getUseParentIfMissing()) {
            var parentItemDescriptionType = itemDescriptionTypeDetail.getParentItemDescriptionType();

            if(parentItemDescriptionType != null) {
                var parentItemDescription = itemControl.getItemDescription(parentItemDescriptionType, item, language);

                if(parentItemDescription == null) {
                    // If there isn't a parent, or if the parent is scaled, then try the parent's parent
                    itemDescription = getBestParent(itemControl, parentItemDescriptionType, item, language);
                } else {
                    // If the parent image wasn't scaled, then we'll use that one.
                    itemDescription = parentItemDescription;
                }
            }
        }

        return itemDescription;
    }
    
    // Find the first available parent ItemDescription based on the Party's preferred Language.
    public ItemDescription getBestParent(final ItemDescriptionType itemDescriptionType, final Item item, final Party party) {
        var itemControl = Session.getModelController(ItemControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var language = party == null ? partyControl.getDefaultLanguage() : partyControl.getPreferredLanguage(party);
        
        return getBestParent(itemControl, itemDescriptionType, item, language);
    }
    
    // Find the first available parent ItemDescription based on the Party's preferred Language.
    public ItemDescription getBestParentUsingNames(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeName, final Item item,
            final Party party) {
        var itemControl = Session.getModelController(ItemControl.class);
        var itemDescriptionType = itemControl.getItemDescriptionTypeByName(itemDescriptionTypeName);
        
        if(itemDescriptionType == null) {
            handleExecutionError(UnknownItemDescriptionTypeNameException.class, eea, ExecutionErrors.UnknownItemDescriptionTypeName.name(), itemDescriptionTypeName);
        }
        
        return (eea == null ? false : eea.hasExecutionErrors()) ? null : getBestParent(itemDescriptionType, item, party);
    }
    
    // Find the first available parent ItemDescription based on the Party's preferred Language.
    public String getBestStringUsingNames(final ExecutionErrorAccumulator eea, final String itemDescriptionTypeName, final Item item, final Party party) {
        var itemDescription = getBestParentUsingNames(eea, itemDescriptionTypeName, item, party);
        String stringDescription = null;
        
        if(itemDescription != null) {
            var mimeType = itemDescription.getLastDetail().getMimeType();
            
            if(mimeType == null) {
                var itemControl = Session.getModelController(ItemControl.class);
                var itemStringDescription = itemControl.getItemStringDescription(itemDescription);

                stringDescription = itemStringDescription.getStringDescription();
            } else {
                handleExecutionError(InvalidItemDescriptionTypeException.class, eea, ExecutionErrors.InvalidItemDescriptionType.name(), itemDescriptionTypeName);
            }
        }
        
        return stringDescription;
    }
    
    // Find the highest quality parent ItemDescription.
    public ItemDescription getBestParentImage(ItemControl itemControl, ItemDescriptionType itemDescriptionType, Item item, Language language) {
        ItemDescription itemDescription = null;
        var parentItemDescriptionType = itemDescriptionType.getLastDetail().getParentItemDescriptionType();

        if(parentItemDescriptionType != null) {
            var parentItemDescription = itemControl.getItemDescription(parentItemDescriptionType, item, language);

            if(parentItemDescription == null || itemControl.getItemImageDescription(parentItemDescription).getScaledFromParent()) {
                // If there isn't a parent, or if the parent is scaled, then try the parent's parent
                itemDescription = getBestParentImage(itemControl, parentItemDescriptionType, item, language);
            } else {
                // If the parent image wasn't scaled, then we'll use that one.
                itemDescription = parentItemDescription;
            }
        }


        return itemDescription;
    }

    public ImageReader getImageReader(MimeType mimeType, ItemBlobDescription itemBlobDescription) {
        var memoryCacheImageInputStream = new MemoryCacheImageInputStream(itemBlobDescription.getBlobDescription().getByteArrayInputStream());
        var imageReaders = ImageIO.getImageReadersByMIMEType(mimeType.getLastDetail().getMimeTypeName());
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

    // Based on: http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in downscaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is
     *    smaller than the original dimensions, and generally only when
     *    the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
    public BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
        var type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        var ret = (BufferedImage)img;
        int w, h;

        if(higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if(higherQuality && w > targetWidth) {
                w /= 2;
                if(w < targetWidth) {
                    w = targetWidth;
                }
            }

            if(higherQuality && h > targetHeight) {
                h /= 2;
                if(h < targetHeight) {
                    h = targetHeight;
                }
            }

            var tmp = new BufferedImage(w, h, type);
            var g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while(w != targetWidth || h != targetHeight);

        return ret;
    }

    // http://www.nearinfinity.com/blogs/jim_clark/thumbnail_generation_gotchas.html
    public double scaleToFit(double w1, double h1, double w2, double h2) {
        var scale = 1.0D;

        if(w1 > h1) {
            if(w1 > w2)
                scale = w2 / w1;
            h1 *= scale;
            if(h1 > h2)
                scale *= h2 / h1;
        } else {
            if(h1 > h2)
                scale = h2 / h1;
            w1 *= scale;
            if(w1 > w2)
                scale *= w2 / w1;
            }

        return scale;
    }

    public ItemDescription searchForItemDescription(ItemDescriptionType itemDescriptionType, Item item, Language language, BasePK createdBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        ItemDescription itemDescription = null;

        if(isImage(itemDescriptionType)) {
            var itemImageDescriptionType = itemControl.getItemImageDescriptionType(itemDescriptionType);

            if(itemImageDescriptionType.getScaleFromParent()) {
                var preferredHeight = itemImageDescriptionType.getPreferredHeight();
                var preferredWidth = itemImageDescriptionType.getPreferredWidth();

                // preferredHeight and preferredWidth are used as the target sizes for the scaling. Without them, it isn't going to happen.
                if(preferredHeight != null && preferredWidth != null) {
                    var originalItemDescription = getBestParentImage(itemControl, itemDescriptionType, item, language);

                    if(originalItemDescription != null) {
                        var originalItemDescriptionDetail = originalItemDescription.getLastDetail();
                        var originalItemImageDescription = itemControl.getItemImageDescription(originalItemDescription);

                        // BLOBs only.
                        if(originalItemDescriptionDetail.getMimeType().getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(EntityAttributeTypes.BLOB.name())) {
                            var originalMimeType = originalItemDescriptionDetail.getMimeType();
                            var preferredMimeType = itemImageDescriptionType.getPreferredMimeType();
                            var quality = itemImageDescriptionType.getQuality();
                            var originalItemImageType = originalItemImageDescription.getItemImageType();
                            var originalItemImageTypeDetail = originalItemImageType.getLastDetail();
                            var originalItemBlobDescription = itemControl.getItemBlobDescription(originalItemDescription);

                            // ItemImageType settings override any that came from the ItemImageDescriptionType
                            if(originalItemImageTypeDetail.getPreferredMimeType() != null) {
                                preferredMimeType = originalItemImageTypeDetail.getPreferredMimeType();
                            }

                            if(originalItemImageTypeDetail.getQuality() != null) {
                                quality = originalItemImageTypeDetail.getQuality();
                            }

                            // If there's still no preferredMimeType, fall back to the original image's MimeType.
                            if(preferredMimeType == null) {
                                preferredMimeType = originalMimeType;
                            }

                            if(quality == null) {
                                quality = 90; // Default quality
                            }

                            var imageReader = getImageReader(originalMimeType, originalItemBlobDescription);
                            if(imageReader != null) {
                                BufferedImage originalBufferedImage = null;

                                try {
                                    originalBufferedImage = imageReader.read(0);
                                } catch (IOException ioe) {
                                    // Ignore, image reading failed, leave originalBufferedImage null.
                                }

                                if(originalBufferedImage != null) {
                                    var originalHeight = originalBufferedImage.getHeight();
                                    var originalWidth = originalBufferedImage.getWidth();
                                    var scale = scaleToFit(originalWidth, originalHeight, preferredWidth, preferredHeight);
                                    var mimeTypeName = preferredMimeType.getLastDetail().getMimeTypeName();

                                    var scaledBufferedImage = getScaledInstance(originalBufferedImage, (int)Math.round(originalWidth * scale),
                                                                       (int)Math.round(originalHeight * scale), RenderingHints.VALUE_INTERPOLATION_BILINEAR,
                                                                       true);

                                    var imageWriters = ImageIO.getImageWritersByMIMEType(mimeTypeName);
                                    var imageWriter = imageWriters.hasNext() ? imageWriters.next() : null;

                                    if(imageWriter != null) {
                                        var byteArrayOutputStream = new ByteArrayOutputStream();

                                        try {
                                            var scaledQuality = (float)quality / 100.0f;
                                            var iwp = imageWriter.getDefaultWriteParam();

                                            if(iwp.canWriteCompressed()) {
                                                var compressionTypes = iwp.getCompressionTypes();

                                                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);

                                                // JPEG doesn't appear to "need" this, but GIF throws an IllegalStateException without it. Pick the first choice.
                                                if(compressionTypes.length > 0) {
                                                    iwp.setCompressionType(compressionTypes[0]);
                                                }

                                                iwp.setCompressionQuality(scaledQuality);
                                            }

                                            ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(byteArrayOutputStream);
                                            var img = new IIOImage(scaledBufferedImage, null, null);

                                            imageWriter.setOutput(imageOutputStream);
                                            imageWriter.write(null, img, iwp);
                                            imageWriter.dispose();
                                            imageOutputStream.close();
                                        } catch (IOException ioe) {
                                            // Exception? Nuke the result, no scaling.
                                            byteArrayOutputStream = null;
                                        }

                                        if(byteArrayOutputStream != null) {
                                            try {
                                                itemDescription = itemControl.createItemDescription(itemDescriptionType, item, language, preferredMimeType, createdBy);
                                                itemControl.createItemImageDescription(itemDescription, originalItemImageType, scaledBufferedImage.getHeight(),
                                                        scaledBufferedImage.getWidth(), true, createdBy);
                                                itemControl.createItemBlobDescription(itemDescription, new ByteArray(byteArrayOutputStream.toByteArray()), createdBy);
                                            } catch(PersistenceDatabaseException pde) {
                                                if(PersistenceUtils.getInstance().isIntegrityConstraintViolation(pde)) {
                                                    itemDescription = itemControl.getItemDescription(itemDescriptionType, item, language);
                                                    
                                                    if(itemDescription != null) {
                                                        pde = null;
                                                    }
                                                }

                                                if(pde != null) {
                                                    throw pde;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                itemDescription = getBestParent(itemControl, itemDescriptionType, item, language);
            }
        } else {
            itemDescription = getBestParent(itemControl, itemDescriptionType, item, language);
        }

        return itemDescription;
    }

    public static class ImageDimensions {

        private Integer height;
        private Integer width;

        public ImageDimensions(Integer height, Integer width) {
            this.height = height;
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

    }

    public ImageDimensions getImageDimensions(String mimeTypeName, ByteArray blobDescription) {
        var memoryCacheImageInputStream = blobDescription.getMemoryCacheImageInputStream();
        var imageReaders = ImageIO.getImageReadersByMIMEType(mimeTypeName);
        var imageReader = imageReaders.hasNext() ? imageReaders.next() : null;
        ImageDimensions result = null;

        if(imageReader != null) {
            imageReader.setInput(memoryCacheImageInputStream);

            try {
                if(imageReader.getNumImages(true) > 0) {
                    result = new ImageDimensions(imageReader.getHeight(0), imageReader.getWidth(0));
                }
            } catch (IOException ioe) {
                // Nothing, result stays null.
            }
        }

        return result;
    }

    public void deleteItemImageDescriptionChildren(ItemDescriptionType itemDescriptionType, Item item, Language language, BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var childItemDescriptionTypes = itemControl.getItemDescriptionTypesByParentItemDescriptionType(itemDescriptionType);

        childItemDescriptionTypes.forEach((childItemDescriptionType) -> {
            var childItemDescription = itemControl.getItemDescriptionForUpdate(childItemDescriptionType, item, language);
            var childWasScaled = true;
            if(childItemDescription != null) {
                var childItemImageDescription = itemControl.getItemImageDescription(childItemDescription);

                childWasScaled = childItemImageDescription.getScaledFromParent();

                if(childWasScaled) {
                    itemControl.deleteItemDescription(childItemDescription, deletedBy);
                }
            }

            // If no child description existed, or if it was scaled, then go through and make sure there are no scaled children under it.
            if (childWasScaled) {
                deleteItemImageDescriptionChildren(childItemDescriptionType, item, language, deletedBy);
            }
        });
    }
    
    public void deleteItemImageDescriptionChildren(ItemDescription itemDescription, BasePK deletedBy) {
        var itemDescriptionDetail = itemDescription.getLastDetail();

        deleteItemImageDescriptionChildren(itemDescriptionDetail.getItemDescriptionType(), itemDescriptionDetail.getItem(), itemDescriptionDetail.getLanguage(), deletedBy);
    }

    public void deleteItemDescription(ItemDescription itemDescription, BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var mimeTypeUsageType = itemDescription.getLastDetail().getItemDescriptionType().getLastDetail().getMimeTypeUsageType();

        itemControl.deleteItemDescription(itemDescription, deletedBy);

        if(mimeTypeUsageType != null &&  mimeTypeUsageType.getMimeTypeUsageTypeName().equals(MimeTypeUsageTypes.IMAGE.name())) {
            var itemImageDescription = itemControl.getItemImageDescription(itemDescription);

            if(!itemImageDescription.getScaledFromParent()) {
                ItemDescriptionLogic.getInstance().deleteItemImageDescriptionChildren(itemDescription, deletedBy);
            }
        }
    }
    
    public void updateItemImageDescriptionTypeFromValue(ItemImageDescriptionTypeValue itemImageDescriptionTypeValue, BasePK updatedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.updateItemImageDescriptionTypeFromValue(itemImageDescriptionTypeValue, updatedBy);

        if(itemImageDescriptionTypeValue.getPreferredHeightHasBeenModified() || itemImageDescriptionTypeValue.getPreferredWidthHasBeenModified()
                || itemImageDescriptionTypeValue.getPreferredMimeTypePKHasBeenModified() || itemImageDescriptionTypeValue.getQualityHasBeenModified()
                || itemImageDescriptionTypeValue.getScaleFromParent()) {
            itemControl.deleteItemDescriptions(itemControl.getScaledItemDescriptionsByItemDescriptionTypePKForUpdate(itemImageDescriptionTypeValue.getItemDescriptionTypePK()), updatedBy);
        }
    }

    public void updateItemImageTypeFromValue(ItemImageTypeDetailValue itemImageTypeDetailValue, BasePK updatedBy) {
        var itemControl = Session.getModelController(ItemControl.class);

        itemControl.updateItemImageTypeFromValue(itemImageTypeDetailValue, updatedBy);

        if(itemImageTypeDetailValue.getPreferredMimeTypePKHasBeenModified() || itemImageTypeDetailValue.getQualityHasBeenModified()) {
            itemControl.deleteItemDescriptions(itemControl.getScaledItemDescriptionsByItemImageTypePKForUpdate(itemImageTypeDetailValue.getItemImageTypePK()), updatedBy);
        }
    }

}
