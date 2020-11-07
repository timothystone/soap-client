package com.anothercaffeinatedday.soap.client;

import com.anothercaffeinatedday.CreateOrdersRequest;
import com.anothercaffeinatedday.CreateOrdersResponse;
import com.anothercaffeinatedday.CustomerOrdersPortType;
import com.anothercaffeinatedday.GetOrdersRequest;
import com.anothercaffeinatedday.GetOrdersResponse;
import com.anothercaffeinatedday.Order;
import com.anothercaffeinatedday.Product;
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

        getOrders(customerOrdersWSImplPort);
        createOrder(customerOrdersWSImplPort);
        getOrders(customerOrdersWSImplPort);

    }

    private static void createOrder(CustomerOrdersPortType customerOrdersWSImplPort) {
        CreateOrdersRequest request = new CreateOrdersRequest();
        Product product = new Product();
        product.setId("123-B");
        product.setDescription("Red Box D&D");
        product.setQuantity(1);
        Order order = new Order();
        order.getProducts().add(product);
        request.setCustomerId(1);
        request.setOrder(order);
        CreateOrdersResponse response = customerOrdersWSImplPort.createOrders(request);

        if (response.isResponse()) {
            LOGGER.debug("Successfully created order {} for customer {}", order.getId(), request.getCustomerId());
        } else {
            LOGGER.error("Response was {}.", response.isResponse());
        }
    }

    private static void getOrders(CustomerOrdersPortType customerOrdersWSImplPort) {
        GetOrdersRequest request = new GetOrdersRequest();
        request.setCustomerId(1);
        GetOrdersResponse response = customerOrdersWSImplPort.getOrders(request);
        List<Order> orders = response.getOrders();

        LOGGER.debug("Number of orders for Customer {}: {}", orders.get(0).getId(), orders.size());
        if(orders.size() > 1) {
            for( Order order : orders) {
                LOGGER.debug(order.toString());
            }
        } else {
            LOGGER.debug(orders.get(0).toString());
        }
    }
}
