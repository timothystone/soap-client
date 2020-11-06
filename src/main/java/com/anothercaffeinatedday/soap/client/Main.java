package com.anothercaffeinatedday.soap.client;

import com.anothercaffeinatedday.CustomerOrdersPortType;
import com.anothercaffeinatedday.GetOrdersRequest;
import com.anothercaffeinatedday.GetOrdersResponse;
import com.anothercaffeinatedday.Order;
import com.anothercaffeinatedday.wsdl.CustomerOrdersWSImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static final String WSDL_LOCATION = "http://localhost:8081/wsdlfirst/customerordersservice?wsdl";

    public static void main(String[] args) throws MalformedURLException {
        CustomerOrdersWSImplService service = new CustomerOrdersWSImplService(new URL(WSDL_LOCATION));
        CustomerOrdersPortType customerOrdersWSImplPort = service.getCustomerOrdersWSImplPort();

        GetOrdersRequest request = new GetOrdersRequest();
        request.setCustomerId(1);
        GetOrdersResponse response = customerOrdersWSImplPort.getOrders(request);
        List<Order> orders = response.getOrders();

        LOGGER.debug("Number of orders for Customer {}: {}", orders.get(0).getId(), orders.size());
        LOGGER.debug(orders.get(0).toString());

    }
}
