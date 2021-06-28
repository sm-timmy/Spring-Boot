import com.fasterxml.jackson.core.JsonProcessingException;
import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestTemplateStart31 {
    static final String URL = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) throws JsonProcessingException {

        //RestTemplate это специальный клиент для отправки запросов в Spring
        RestTemplate restTemplate = new RestTemplate();
        //GET http запрос, ответ получить в формате string

        // HttpHeaders
        HttpHeaders headers = new HttpHeaders();

        // Request to return XML format
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("my_other_key", "my_other_value");

        // HttpEntity<User[]>: To get result as User[].
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // Send request with GET method, and Headers.
        ResponseEntity<String> response = restTemplate.exchange(URL, //
                HttpMethod.GET, entity, String.class);

        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Response Satus Code: " + statusCode);

        System.out.println("Response: " + response);
        response.getBody();

        }
}
