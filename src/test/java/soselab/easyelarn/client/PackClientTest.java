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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "easylearn-pack.ribbon.listOfServers=localhost:8085"})
@DirtiesContext
@ActiveProfiles("test")
public class PackClientTest {
    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("easylearn-pack", "localhost", 8085, this);
    private String pack = "{\n" +
            "  \"noteId\": \"note1479018239603\",\n" +
            "  \"packId\": \"pack1478670701680\",\n" +
            "  \"versionId\": \"version1478670701680\",\n" +
            "  \"newContent\": \"<p class=\\\"first\\\">美國總統大選目前開票中，紐約時報預測川普當選率高達95%，而他本人也在YouTube上開直播，還一度定調為「完勝演說」。加拿大移民部網站因此被塞爆，加拿大政府官網連線速度也相當緩慢。</p>\\n<p>「Move&nbsp;to&nbsp;Canada」是許多人誓言如果川普當選就要<span class=\\\"note mdl-color--indigo-100\\\" id=\\\"note1479018239603\\\">移民</span>去加拿大，以躲避川普統治的美國，當初多被以玩笑看待的這句話，如今越來越有可能成真。<br><br>目前加拿大移民部網站無法進入，而加拿大政府官網雖可連線但速度緩慢，移民相關資訊也無法讀取。</p>\\n<p>在台發展的義大利籍藝人韋佳德也在<a href=\\\"https://www.facebook.com/ruike85/photos/a.1482087922036077.1073741827.1482081335370069/1846985895546276/?type=3&amp;theater\\\">臉書PO</a>出加拿大移民部網站遭塞爆的截圖，並表達「覺得遭透了」的心情。</p>\"\n" +
            "}";
    @Autowired
    private PackClient client;

    @Test
    @PactVerification
    public void getUserPacks() throws Exception {
        client.updateVersion(pack);
    }

    @Pact(consumer = "easylearn-webback")
    public PactFragment createFragment(PactDslWithProvider builder) {
        return builder
                .uponReceiving("Modify version content after adding note")
                .path("/version")
                .body(new JSONObject(pack))
                .method("Put")
                .willRespondWith()
                .status(200)
                .toFragment();
    }

}