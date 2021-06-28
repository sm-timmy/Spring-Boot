import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestTemplateStart {
    static final String URL = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) throws JsonProcessingException {

        //RestTemplate это специальный клиент для отправки запросов в Spring
        RestTemplate restTemplate = new RestTemplate();
        //GET http запрос, ответ получить в формате string
        ResponseEntity<String> result = restTemplate.getForEntity(URL, String.class);
        HttpHeaders headers = result.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

        System.out.println("cli -> srv ask headers\n" + result + "\n\n");
        System.out.println("get Cookies\n" + set_cookie + "\n\n");

        User user = new User(3L,"James","Brown", (byte) 25);

        HttpHeaders myHeaders = new HttpHeaders();
        myHeaders.set("Cookie", set_cookie);
        myHeaders.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        System.out.println("usr to Json\n" + jsonString + "\n\n");

        HttpEntity<String> requestBody = new HttpEntity<>(jsonString, myHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, requestBody, String.class);
        System.out.println("cli -> srv (post request) \n" + requestBody + "\n\n");
        System.out.println("srv -> cli (post reply) \n" + responseEntity+ "\n\n");

        ResponseEntity<String> request2 = restTemplate.exchange(
                URL, HttpMethod.GET, new HttpEntity<String>(myHeaders), String.class);
        System.out.println("srv -> cli (get reply) \n" + request2 + "\n\n");

        user.setName("Thomas");
        user.setLastName("Shelby");
        String jsonString2 = mapper.writeValueAsString(user);
        System.out.println("usr to Json\n" + jsonString2 + "\n\n");
        HttpEntity<String> requestBody2 = new HttpEntity<>(jsonString2, myHeaders);

        ResponseEntity<String> responseEntity2 = restTemplate.exchange(URL, HttpMethod.PUT, requestBody2, String.class);
        System.out.println("cli -> srv (put request) \n" + requestBody2 + "\n\n");
        System.out.println("srv -> cli (put reply) \n" + responseEntity2 + "\n\n");

        request2 = restTemplate.exchange(
                URL, HttpMethod.GET, new HttpEntity<String>(myHeaders), String.class);
        System.out.println("srv -> cli (get reply) \n" + request2 + "\n\n");

        ResponseEntity<String> responseEntity3 = restTemplate.exchange(URL+"/3", HttpMethod.DELETE, requestBody2, String.class);
        System.out.println("cli -> srv (delete request) \n" + requestBody2 + "\n\n");
        System.out.println("srv -> cli (delete reply) \n" + responseEntity3 + "\n\n");

        request2 = restTemplate.exchange(
                URL, HttpMethod.GET, new HttpEntity<String>(myHeaders), String.class);
        System.out.println("srv -> cli (get reply) \n" + request2 + "\n\n");
    }
}