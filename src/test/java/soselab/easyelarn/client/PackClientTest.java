package soselab.easyelarn.client;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "easylearn-user.ribbon.listOfServers=localhost:8085"})
@DirtiesContext
@ActiveProfiles("test")
public class PackClientTest {
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("easylearn-pack", "localhost", 8085, this);

    @Autowired
    private PackClient client;

    @Test
    public void updateVersion() throws Exception {

    }

    @Test
    @PactVerification
    public void getUserPacks() throws Exception {
        client.updateVersion("new version");
    }

    @Pact(consumer = "easylearn-webback")
    public PactFragment createFragment(PactDslWithProvider builder) {
        return builder
                .uponReceiving("ExampleJavaConsumerPactRuleTest test interaction")
                .path("/pack")
                .method("Put")
                .willRespondWith()
                .status(200)
                .body("[\"packid\", \"testPackId\"]", "application/json")
                .toFragment();
    }

}