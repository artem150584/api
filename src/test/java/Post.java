import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Post {
    private String url = "https://api.ru/test-api/api/v4/entity";

    @Test
    public void checkNewEntity() throws UnirestException {
        final String POST_PARAMS = "{\n" +
                "  \"firstName\": \"Name\",\n" +
                "  \"lastName\": \"SecondName\",\n" +
                "  \"birthDate\": \"2000-05-16T07:49:03.175Z\"\n" +
                "}";

        HttpResponse<JsonNode> jsonPostResponse
                = Unirest.post(url)
                .header("accept", "application/json")
                .header("userId", "a1bcdefgh")
                .body(POST_PARAMS)
                .asJson();

        assertEquals(200, jsonPostResponse.getStatus());
        assertNotNull(jsonPostResponse.getBody()); // TODO there are no real data. Need to check all fields in the body

        String firstId = jsonPostResponse.getBody().getObject().get("id").toString();

        HttpResponse<JsonNode> jsonGetResponse
                = Unirest.get(url)
                .header("accept", "application/json")
                .queryString("firstId", firstId)
                .asJson();

        assertEquals(200, jsonPostResponse.getStatus());
        assertNotNull(jsonPostResponse.getBody()); // TODO there are no real data. Need to check all fields in the body
    }
}
