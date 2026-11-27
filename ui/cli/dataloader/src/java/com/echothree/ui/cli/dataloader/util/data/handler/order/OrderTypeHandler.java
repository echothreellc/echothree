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

package com.echothree.ui.cli.dataloader.util.data.handler.order;

import com.echothree.control.user.order.common.OrderUtil;
import com.echothree.control.user.order.common.OrderService;
import com.echothree.control.user.order.common.form.OrderFormFactory;
import com.echothree.ui.cli.dataloader.util.data.InitialDataParser;
import com.echothree.ui.cli.dataloader.util.data.handler.BaseHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class OrderTypeHandler
        extends BaseHandler {

    OrderService orderService;
    String orderTypeName;
    
    /** Creates a new instance of OrderTypeHandler */
    public OrderTypeHandler(InitialDataParser initialDataParser, BaseHandler parentHandler, String orderTypeName)
            throws SAXException {
        super(initialDataParser, parentHandler);
        
        orderService = lookupService(OrderService.class, OrderUtil::getHome);
        
        this.orderTypeName = orderTypeName;
    }
    
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attrs)
    throws SAXException {
        if(localName.equals("orderTypeDescription")) {
            var commandForm = OrderFormFactory.getCreateOrderTypeDescriptionForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(orderService.createOrderTypeDescription(initialDataParser.getUserVisit(), commandForm));
        } else if(localName.equals("orderTimeType")) {
            var commandForm = OrderFormFactory.getCreateOrderTimeTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(orderService.createOrderTimeType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new OrderTimeTypeHandler(initialDataParser, this, orderTypeName, commandForm.getOrderTimeTypeName()));
        } else if(localName.equals("orderPriority")) {
            var commandForm = OrderFormFactory.getCreateOrderPriorityForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(orderService.createOrderPriority(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new OrderPriorityHandler(initialDataParser, this, orderTypeName, commandForm.getOrderPriorityName()));
        } else if(localName.equals("orderAliasType")) {
            var commandForm = OrderFormFactory.getCreateOrderAliasTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(orderService.createOrderAliasType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new OrderAliasTypeHandler(initialDataParser, this, orderTypeName, commandForm.getOrderAliasTypeName()));
        } else if(localName.equals("orderAdjustmentType")) {
            var commandForm = OrderFormFactory.getCreateOrderAdjustmentTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(orderService.createOrderAdjustmentType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new OrderAdjustmentTypeHandler(initialDataParser, this, orderTypeName, commandForm.getOrderAdjustmentTypeName()));
        } else if(localName.equals("orderLineAdjustmentType")) {
            var commandForm = OrderFormFactory.getCreateOrderLineAdjustmentTypeForm();

            commandForm.setOrderTypeName(orderTypeName);
            commandForm.set(getAttrsMap(attrs));

            checkCommandResult(orderService.createOrderLineAdjustmentType(initialDataParser.getUserVisit(), commandForm));

            initialDataParser.pushHandler(new OrderLineAdjustmentTypeHandler(initialDataParser, this, orderTypeName, commandForm.getOrderLineAdjustmentTypeName()));
        }
    }
    
    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {
        if(localName.equals("orderType")) {
            initialDataParser.popHandler();
        }
    }
    
}
