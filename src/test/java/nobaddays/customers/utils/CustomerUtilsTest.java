package nobaddays.customers.utils;

import nobaddays.customers.Constants;
import nobaddays.customers.pojo.Customer;
import nobaddays.customers.pojo.GPSCoordinate;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerUtilsTest {

    @Test
    public void getCustomersWithinDistance() {

        List<Customer> customersToFilter = new ArrayList<>();
        customersToFilter.add(new Customer(2, "Adam", new GPSCoordinate(53.2451022d,-6.238335d)));
        customersToFilter.add(new Customer(8, "Dave", new GPSCoordinate(55.033d,-8.112d)));
        customersToFilter.add(new Customer(4, "Beth", new GPSCoordinate(53d,-7d)));
        customersToFilter.add(new Customer(6, "Chad", new GPSCoordinate(51.92893d,-10.27699d)));

        GPSCoordinate filterLocation = new GPSCoordinate(53.339428,-6.257664);

        Double kilometerRange = 100d;

        List<Customer> customersInRange = CustomerUtils.getCustomersWithinDistance(customersToFilter, filterLocation, kilometerRange);

        assertEquals(customersInRange.size(), 2);
        assertEquals(customersInRange.get(0).getUserId(), 2);
        assertEquals(customersInRange.get(1).getUserId(), 4);

    }

    @Test
    public void processCustomerData() {

        List<Customer> customers = CustomerUtils.processCustomerData(Constants.DEFAULT_JSON_TXT_FILE_INPUT_URL,
                Constants.DEFAULT_VALID_RANGE_IN_KM);

        assertEquals(customers.get(0).getName(), "Ian Kehoe");
        assertEquals(customers.get(4).getUserId(), 11);

    }

    @Test
    public void processCustomerDataError(){

        List<Customer> customers = CustomerUtils.processCustomerData("Some URL that won't work",
                Constants.DEFAULT_VALID_RANGE_IN_KM);

        assertTrue(customers.isEmpty());

    }
}