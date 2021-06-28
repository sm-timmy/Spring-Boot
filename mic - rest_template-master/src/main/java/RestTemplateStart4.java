import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestTemplateStart4 {
    static final String URL = "http://91.241.64.178:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.getForEntity(URL, String.class);

        HttpHeaders headers = result.getHeaders();
        String set_cookie = headers.getFirst(HttpHeaders.SET_COOKIE);

//        Objects.requireNonNull(result.getHeaders().get("Set-Cookie")).stream().forEach(System.out::println);


        User user = new User(3L,"James","Brown", (byte) 25);

        RestTemplate postTemplate = new RestTemplate();
        HttpHeaders myHeaders = new HttpHeaders();

        myHeaders.set("Cookie", set_cookie);
        myHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> requestBody = new HttpEntity<>(user, myHeaders);

        ResponseEntity<String> responseEntity = postTemplate.postForEntity(URL, requestBody, String.class);
//
//        headers.set("Cookie",cookies.stream().collect(Collectors.joining(";")));

        System.out.println("\n" + result + "\n\n");
        System.out.println(set_cookie + "\n\n");
        System.out.println(requestBody + "\n\n");
        System.out.println(responseEntity + "\n\n");

        ResponseEntity<String> request2 = restTemplate.exchange(
                URL, HttpMethod.GET, new HttpEntity<String>(myHeaders), String.class);
        System.out.println("srv -> cli (get reply) \n" + request2 + "\n\n");
    }
}