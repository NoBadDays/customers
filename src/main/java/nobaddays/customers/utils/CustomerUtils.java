package nobaddays.customers.utils;

import nobaddays.customers.pojo.Customer;
import nobaddays.customers.pojo.GPSCoordinate;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CustomerUtils {

    private CustomerUtils() {
    }

    public static List<Customer> getCustomersWithinDistance(List<Customer> customersToFilter, GPSCoordinate filterLocation , Double kilometerRange){
        List<Customer> customersInRange = new ArrayList<>();

        for (Customer customer: customersToFilter){
            if( kilometerRange.compareTo(GPSCoordinateUtils.getDistanceInKm(customer.getLocation(),filterLocation)) > 0 ){
                customersInRange.add(customer);
            }
        }

        return customersInRange;
    }

    public static String printListOfCustomersAsJson(List<Customer> customers){
        return new JSONArray(customers).toString(4);
    }

}
