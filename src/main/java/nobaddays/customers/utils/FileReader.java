package nobaddays.customers.utils;

import nobaddays.customers.Constants;
import nobaddays.customers.pojo.Customer;
import nobaddays.customers.pojo.GPSCoordinate;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReader {

    private static final Logger LOGGER = Logger.getLogger(FileReader.class.getName());

    private FileReader() {
    }

    public static List<Customer> getCustomers(String url) throws IOException {
        List<JSONObject> customers = new ArrayList<>();

        InputStream inputStream = new URL(url).openStream();
            LOGGER.log(Level.INFO, () -> "Reading Customer data from " + Constants.JSON_TXT_FILE_INPUT_URL);

        try (BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(Constants.JSON_TXT_FILE_ENCODING)))) {
            String line;
            while ((line = rd.readLine()) != null) {
                customers.add(new JSONObject(line));
            }
        }
        return getCustomersListFromJsonList(customers);
    }

    private static List<Customer> getCustomersListFromJsonList(List<JSONObject> customerJsonObjects){
        List<Customer> customers = new ArrayList<>();
        for(JSONObject jsonObject: customerJsonObjects){
            GPSCoordinate location = new GPSCoordinate(
                    Double.parseDouble(jsonObject.get(Constants.JSON_CUSTOMER_LATITUDE_STRING).toString()),
                    Double.parseDouble(jsonObject.get(Constants.JSON_CUSTOMER_LONGITUDE_STRING).toString()));
            Customer customer = new Customer(
                    Integer.parseInt( jsonObject.get(Constants.JSON_CUSTOMER_USER_ID_STRING).toString()),
                    jsonObject.get(Constants.JSON_CUSTOMER_NAME_STRING).toString(),
                    location
            );
            customers.add(customer);
        }

        return customers;
    }

}
