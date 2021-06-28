import com.fasterxml.jackson.core.JsonProcessingException;
import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class RestTemplateStart3 {
    static final String URL = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) throws JsonProcessingException {

        //RestTemplate это специальный клиент для отправки запросов в Spring
        RestTemplate restTemplate = new RestTemplate();
        //GET http запрос, ответ получить в формате string

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        // Request to return XML format
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.set("my_other_key", "my_other_value");

        // HttpEntity<User[]>: To get result as User[].
        HttpEntity<User[]> entity = new HttpEntity<User[]>(headers);

        // Send request with GET method, and Headers.
        ResponseEntity<User[]> response = restTemplate.exchange(URL, //
                HttpMethod.GET, entity, User[].class);

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);

        // Status Code: 200
        if (statusCode == HttpStatus.OK) {
            // Response Body Data
            User[] list = response.getBody();

            if (list != null) {
                for (User e : list) {
                    System.out.println("User: " + e.getName() + " - " + e.getLastName());
                }

            }
        }
    }
}
