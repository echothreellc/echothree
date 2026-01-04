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

package com.echothree.ui.cli.amazon.batch.order.content;

public interface AmazonOrderFields {

    // Order:
    //   order-id
    //   payments-date
    //   total-price
    //   purchase-date
    //   buyer-email
    //   buyer-name
    //
    // Order Shipment Group:
    //   recipient-name
    //   ship-address-1
    //   ship-address-2
    //   ship-city
    //   ship-state
    //   ship-zip
    //   ship-country
    //   ship-method
    //
    // Order Line:
    //   order-item-id
    //   item-name
    //   listing-id
    //   sku
    //   price
    //   shipping-fee
    //   quantity-purchased
    //   upc
    //
    // Unused:
    //   payments-status
    //   payments-transaction-id
    //   batch-id
    //   special-comments

    static final String Field_PaymentsStatus = "payments-status";
    static final String Field_OrderId = "order-id";
    static final String Field_OrderItemId = "order-item-id";
    static final String Field_PaymentsDate = "payments-date";
    static final String Field_PaymentsTransactionId = "payments-transaction-id";
    static final String Field_ItemName = "item-name";
    static final String Field_ListingId = "listing-id";
    static final String Field_Sku = "sku";
    static final String Field_Price = "price";
    static final String Field_ShippingFee = "shipping-fee";
    static final String Field_QuantityPurchased = "quantity-purchased";
    static final String Field_TotalPrice = "total-price";
    static final String Field_PurchaseDate = "purchase-date";
    static final String Field_BatchId = "batch-id";
    static final String Field_BuyerEmail = "buyer-email";
    static final String Field_BuyerName = "buyer-name";
    static final String Field_RecipientName = "recipient-name";
    static final String Field_ShipAddress1 = "ship-address-1";
    static final String Field_ShipAddress2 = "ship-address-2";
    static final String Field_ShipCity = "ship-city";
    static final String Field_ShipState = "ship-state";
    static final String Field_ShipZip = "ship-zip";
    static final String Field_ShipCountry = "ship-country";
    static final String Field_SpecialComments = "special-comments";
    static final String Field_Upc = "upc";
    static final String Field_ShipMethod = "ship-method";

}
