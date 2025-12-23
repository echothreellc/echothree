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

package com.echothree.ui.cli.amazon.batch.order.tasks;

import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrder;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrderLine;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrderShipmentGroup;
import com.echothree.ui.cli.amazon.batch.order.content.AmazonOrders;
import com.echothree.util.common.string.StringUtils;
import com.google.common.base.Splitter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BuildOrders {

    private final Log LOG = LogFactory.getLog(this.getClass());

    private static final Splitter TAB_SPLITTER = Splitter.on('\t')
           .trimResults();

    String filename;
    AmazonOrders amazonOrders;
    int depth;
    
    private String indent;

    private List<String> headerFields;
    private long orderCount = 0;
    private long orderShipmentGroupCount = 0;
    private long orderLineCount = 0;

    private void init(String filename, AmazonOrders amazonOrders, int depth)
            throws IOException {
        this.filename = filename;
        this.amazonOrders = amazonOrders;
        this.depth = depth;
        
        indent = StringUtils.getInstance().repeatingStringFromChar(' ', depth);
    }

    /** Creates a new instance of BuildOrders */
    public BuildOrders(String filename, AmazonOrders amazonOrders, int depth)
            throws IOException {
        init(filename, amazonOrders, depth);
    }

    private void handleHeaderFields(Iterable<String> headerFields) {
        this.headerFields = new ArrayList<>();

        for(var headerField : headerFields) {
            this.headerFields.add(headerField);
        }

        LOG.debug(indent + "Header fields: " + this.headerFields);
    }

    private Map<String, String> handleDataFields(long line, Iterable<String> dataFields) {
        Map<String, String> dataFieldMap = new HashMap<>();
        var headerFieldsIter = this.headerFields.iterator();

        for(var dataField : dataFields) {
            if(headerFieldsIter.hasNext()) {
                dataFieldMap.put(headerFieldsIter.next(), dataField);
            } else {
                LOG.debug(indent + "Line: " + line + ", discarding extra field: " + dataField);
            }
        }

        LOG.debug(indent + "Line: " + line + ": " + dataFieldMap);

        return dataFieldMap;
    }

    private AmazonOrder getOrderFromDataFields(AmazonOrders amazonOrders, Map<String, String> dataFieldMap) {
        var order = new AmazonOrder(amazonOrders, dataFieldMap);
        var orderKey = order.getKey();
        var existingOrder = amazonOrders.getAmazonOrders().get(orderKey);

        if(existingOrder == null) {
            amazonOrders.getAmazonOrders().put(orderKey, order);
            existingOrder = order;
            orderCount++;
        }

        return existingOrder;
    }

    private AmazonOrderShipmentGroup getOrderShipmentGroupFromDataFields(AmazonOrder order, Map<String, String> dataFieldMap) {
        var orderShipmentGroup =  new AmazonOrderShipmentGroup(order, dataFieldMap);
        var orderShipmentGroupKey = orderShipmentGroup.getKey();
        var amazonOrderShipmentGroups = order.getAmazonOrderShipmentGroups();
        var existingOrderShipmentGroup = amazonOrderShipmentGroups.get(orderShipmentGroupKey);

        if(existingOrderShipmentGroup == null) {
            amazonOrderShipmentGroups.put(orderShipmentGroupKey, orderShipmentGroup);
            existingOrderShipmentGroup = orderShipmentGroup;
            orderShipmentGroupCount++;
        }

        return existingOrderShipmentGroup;
    }

    private AmazonOrderLine getOrderLineFromDataFields(AmazonOrderShipmentGroup orderShipmentGroup, Map<String, String> dataFieldMap) {
        var orderLine = new AmazonOrderLine(orderShipmentGroup, dataFieldMap);
        var orderLineKey = orderLine.getKey();
        var amazonOrderLines = orderShipmentGroup.getAmazonOrderLines();
        var existingOrderLine = amazonOrderLines.get(orderLineKey);

        if(existingOrderLine == null) {
            amazonOrderLines.put(orderLineKey, orderLine);
            existingOrderLine = orderLine;
            orderLineCount++;
        }

        return existingOrderLine;
    }

    private void buildOrder(AmazonOrders amazonOrders, Map<String, String> dataFieldMap) {
        getOrderLineFromDataFields(getOrderShipmentGroupFromDataFields(getOrderFromDataFields(amazonOrders, dataFieldMap), dataFieldMap), dataFieldMap);
    }

    public void execute()
            throws IOException {
        LOG.info(indent + "Using filename: " + filename);

        var fis = new FileInputStream(new File(filename));
        var in = new BufferedReader(new InputStreamReader(fis, Charset.forName("windows-1252")));
        long count = 0;

        for(var line = in.readLine(); line != null; line = in.readLine()) {
            var fields = TAB_SPLITTER.split(new String(line.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));

            count++;

            if(count == 1) {
                handleHeaderFields(fields);
            } else {
                buildOrder(amazonOrders, handleDataFields(count, fields));
            }
        }

        LOG.info(indent + "Read " + count + " line" + (count == 1 ? "" : "s") + ".");
    }
    
}
