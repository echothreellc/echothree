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

package com.echothree.ui.cli.amazon.batch.order.tasks;

import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrders;
import com.google.common.base.Splitter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DumpOrders {

    private final Log LOG = LogFactory.getLog(this.getClass());

    private static final Splitter TAB_SPLITTER = Splitter.on('\t')
           .trimResults();

    private AmazonOrders amazonOrders;

    private final String Dashes     = "-------------------------------------------------------------------------------------------------------------------------------";
    private final String ItemHeader = "Order Item     Listing Id  UPC          Sku             Product Name                                 Unit Qty Shipping    Total";

    private void init(AmazonOrders amazonOrders) {
        this.amazonOrders = amazonOrders;
    }

    /** Creates a new instance of DumpOrders */
    public DumpOrders(AmazonOrders amazonOrders) {
        init(amazonOrders);
    }

    private String limitLength(String str, int length) {
        var result = str;

        if(str.length() > length) {
            result = str.substring(0, length - 3) + "...";
        }

        return result;
    }

    public void execute() {
        amazonOrders.getAmazonOrders().values().stream().map((order) -> {
            LOG.info(Dashes);
            return order;
        }).map((order) -> {
            LOG.info("");
            return order;
        }).map((order) -> {
            LOG.info("Order Id: " + order.getOrderId());
            return order;
        }).map((order) -> {
            LOG.info("");
            return order;
        }).map((order) -> {
            LOG.info("Purchase Date: " + order.getPurchaseDate());
            return order;
        }).map((order) -> {
            LOG.info("Payments Date: " + order.getPaymentsDate());
            return order;
        }).map((order) -> {
            LOG.info("");
            return order;
        }).map((order) -> {
            LOG.info("Buyer:");
            return order;
        }).map((order) -> {
            LOG.info("   Name: " + order.getBuyerName());
            return order;
        }).map((order) -> {
            LOG.info("  Email: " + order.getBuyerEmail());
            return order;
        }).map((order) -> {
            LOG.info("");
            return order;
        }).forEach((order) -> {
            order.getAmazonOrderShipmentGroups().values().stream().map((orderShipmentGroup) -> {
                var shipAddress2 = orderShipmentGroup.getShipAddress2();
                LOG.info("Shipment Group:");
                LOG.info("  " + orderShipmentGroup.getRecipientName());
                LOG.info("  " + orderShipmentGroup.getShipAddress1());
                if(shipAddress2 != null && shipAddress2.length() > 0) {
                    LOG.info("    " + shipAddress2);
                }
                LOG.info("  " + orderShipmentGroup.getShipCity() + ", " + orderShipmentGroup.getShipState() + " " + orderShipmentGroup.getShipZip() + " " + orderShipmentGroup.getShipCountry());
                return orderShipmentGroup;
            }).map((orderShipmentGroup) -> {
                LOG.info("");
                return orderShipmentGroup;
            }).map((orderShipmentGroup) -> {
                LOG.info("  Shipping Method: " + orderShipmentGroup.getShipMethod());
                return orderShipmentGroup;
            }).map((orderShipmentGroup) -> {
                LOG.info("");
                return orderShipmentGroup;
            }).map((orderShipmentGroup) -> {
                LOG.info(ItemHeader);
                return orderShipmentGroup;
            }).map((orderShipmentGroup) -> {
                orderShipmentGroup.getAmazonOrderLines().values().forEach((orderLine) -> {
                    LOG.info(String.format("%14s %11s %-12s %-15s %-40s %8s %3s %8s %8s",
                            orderLine.getOrderItemId(), orderLine.getListingId(), orderLine.getUpc(), orderLine.getSku(), limitLength(orderLine.getItemName(), 40),
                            orderLine.getPrice(), orderLine.getQuantityPurchased(), orderLine.getShippingFee(), orderLine.getTotalPrice()));
                });
                return orderShipmentGroup;
            }).forEach((_item) -> {
                LOG.info("");
            });
        });

        LOG.info(Dashes);
    }

}
