package soselab.easyelarn.client;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("test")
public class NoteClientTest {
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("easylearn-note", "localhost", 8085, this);
    private String note = "{\n" +
            "    \"id\": \"note1479018239603\",\n" +
            "    \"content\": \"a\",\n" +
            "    \"createTime\": 1479018239605,\n" +
            "    \"comment\": [],\n" +
            "    \"userId\": \"id\",\n" +
            "    \"userName\": \"Bernie\"\n" +
            "  }";
    @Autowired
    private NoteClient client;

    @Test
    @PactVerification
    public void getUserPacks() throws Exception {
        client.addNote(note);
    }

    @Pact(consumer = "easylearn-webback")
    public PactFragment createFragment(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Add note")
                .path("/")
                .body(new JSONObject(note))
                .method("Post")
                .willRespondWith()
                .status(200)
                .toFragment();
    }
}