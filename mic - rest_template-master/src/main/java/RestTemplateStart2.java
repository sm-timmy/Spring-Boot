import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestTemplateStart2 {
    static final String URL = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) throws JsonProcessingException {

        //RestTemplate это специальный клиент для отправки запросов в Spring
        RestTemplate restTemplate = new RestTemplate();
        //GET http запрос, ответ получить в формате string

        // Send request with GET method and default Headers.
        User[] list = restTemplate.getForObject(URL, User[].class);

        if (list != null) {
            for (User e : list) {
                System.out.println("User: " + e.getName() + " - " + e.getLastName());
            }
        }
    }
}