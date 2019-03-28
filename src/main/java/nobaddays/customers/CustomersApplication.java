package nobaddays.customers;

import nobaddays.customers.pojo.Customer;
import nobaddays.customers.utils.CustomerUtils;
import nobaddays.customers.utils.FileReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.lineSeparator;

@SpringBootApplication
public class CustomersApplication {

	private static final Logger LOGGER = Logger.getLogger(CustomersApplication.class.getName());

	public static void main(String[] args){
        SpringApplication.run(CustomersApplication.class, args);
        // if args[0] is set, use it as the range to search within.
        final double validRange = args.length > 0 ? Double.parseDouble(args[0]) : Constants.DEFAULT_VALID_RANGE_IN_KM;
        // if args[1] is set, use it as the json.txt url.
        final String fileUrl = args.length > 1 ? args[1] : Constants.DEFAULT_JSON_TXT_FILE_INPUT_URL;

        processCustomerData(fileUrl, validRange);
	}

	private static void processCustomerData(String fileUrl, double validRange){
        LOGGER.log(Level.INFO, () -> "Getting List of customers within " +
                validRange + "km Range of (" + Constants.OFFICE_GPS_COORDINATE + ")");

        try {

            LOGGER.log(Level.INFO, () -> "Reading Customer data from " + fileUrl);
            InputStream inputStream = new URL(fileUrl).openStream();
            List<Customer> customers = FileReader.getCustomers(inputStream);
            List<Customer> customersInRange = CustomerUtils.getCustomersWithinDistance(
                    customers, Constants.OFFICE_GPS_COORDINATE, validRange);

            LOGGER.log(Level.INFO, () -> "Total customers read from file " + customers.size() + ", of which " +
                    customersInRange.size() + " are within a " + validRange + "km Range.");

            customersInRange.sort(Comparator.comparingInt(Customer::getUserId));
            StringBuilder sb = new StringBuilder();
            for (Customer customer : customersInRange) {
                sb.append(customer).append(lineSeparator());
            }

            LOGGER.log(Level.INFO, () -> "List of customers in range: " + lineSeparator() + lineSeparator() + sb);
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "An Error has occurred, Exiting Application: " + e.toString());
        }
    }

}
