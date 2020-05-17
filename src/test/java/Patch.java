import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Patch {
    private String url = "https://api.ru/test-api/api/v4/entity";

    @Test
    public void checkNotexistingEntity() throws UnirestException {
        String notexistingFirstId;
        HttpResponse<JsonNode> jsonGetResponse;
        do {
            notexistingFirstId = UUID.randomUUID().toString();
            jsonGetResponse
                    = Unirest.get(url)
                    .header("accept", "application/json")
                    .queryString("firstId", notexistingFirstId)
                    .asJson();
        } while (!jsonGetResponse.getBody().getObject().toString().equals("{}"));


        final String PATCH_PARAMS = "{\n" +
                "  \"firstName\": \"Name\",\n" +
                "  \"lastName\": \"SecondName\",\n" +
                "  \"birthDate\": \"2000-05-16T07:49:03.175Z\"\n" +
                "}";

        HttpResponse<JsonNode> jsonPostResponse
                = Unirest.patch(url)
                .header("accept", "application/json")
                .header("userId", "a1bcdefgh")
                .body(PATCH_PARAMS)
                .asJson();

        assertEquals(422, jsonPostResponse.getStatus());

        JsonNode body = jsonPostResponse.getBody();
        assertNotNull(body);

        // TODO there are no real data. Need to check all fields in the body
        assertEquals(body.getObject().get("techMsg"), "Some techMsg");
        assertEquals(body.getObject().get("userMsg"), "Some userMsg");
        assertEquals(body.getObject().get("timestamp"), "Some timestamp");
    }
}
