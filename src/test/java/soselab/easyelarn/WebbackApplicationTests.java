package soselab.easyelarn;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import soselab.easyelarn.client.NoteClient;
import soselab.easyelarn.client.PackClient;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WebbackApplicationTests {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private PackClient packClient;
    @MockBean
    private NoteClient noteClient;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() {

        final ArgumentCaptor<String> packClientCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> noteClientCaptor = ArgumentCaptor.forClass(String.class);

        String json = "{\n" +
                "  \"noteId\": \"note1479018239603\",\n" +
                "  \"note\": {\n" +
                "    \"id\": \"note1479018239603\",\n" +
                "    \"content\": \"a\",\n" +
                "    \"createTime\": 1479018239605,\n" +
                "    \"comment\": [],\n" +
                "    \"userId\": \"id\",\n" +
                "    \"userName\": \"Bernie\"\n" +
                "  },\n" +
                "  \"packId\": \"pack1478670701680\",\n" +
                "  \"versionId\": \"version1478670701680\",\n" +
                "  \"newContent\": \"<p class=\\\"first\\\">美國總統大選目前開票中，紐約時報預測川普當選率高達95%，而他本人也在YouTube上開直播，還一度定調為「完勝演說」。加拿大移民部網站因此被塞爆，加拿大政府官網連線速度也相當緩慢。</p>\\n<p>「Move&nbsp;to&nbsp;Canada」是許多人誓言如果川普當選就要<span class=\\\"note mdl-color--indigo-100\\\" id=\\\"note1479018239603\\\">移民</span>去加拿大，以躲避川普統治的美國，當初多被以玩笑看待的這句話，如今越來越有可能成真。<br><br>目前加拿大移民部網站無法進入，而加拿大政府官網雖可連線但速度緩慢，移民相關資訊也無法讀取。</p>\\n<p>在台發展的義大利籍藝人韋佳德也在<a href=\\\"https://www.facebook.com/ruike85/photos/a.1482087922036077.1073741827.1482081335370069/1846985895546276/?type=3&amp;theater\\\">臉書PO</a>出加拿大移民部網站遭塞爆的截圖，並表達「覺得遭透了」的心情。</p>\"\n" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = this.restTemplate.exchange("/note", HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(packClient, times(1)).updateVersion(packClientCaptor.capture());
        verify(noteClient, times(1)).addNote(noteClientCaptor.capture());


        String pack = "{\n" +
                "  \"noteId\": \"note1479018239603\",\n" +
                "  \"packId\": \"pack1478670701680\",\n" +
                "  \"versionId\": \"version1478670701680\",\n" +
                "  \"newContent\": \"<p class=\\\"first\\\">美國總統大選目前開票中，紐約時報預測川普當選率高達95%，而他本人也在YouTube上開直播，還一度定調為「完勝演說」。加拿大移民部網站因此被塞爆，加拿大政府官網連線速度也相當緩慢。</p>\\n<p>「Move&nbsp;to&nbsp;Canada」是許多人誓言如果川普當選就要<span class=\\\"note mdl-color--indigo-100\\\" id=\\\"note1479018239603\\\">移民</span>去加拿大，以躲避川普統治的美國，當初多被以玩笑看待的這句話，如今越來越有可能成真。<br><br>目前加拿大移民部網站無法進入，而加拿大政府官網雖可連線但速度緩慢，移民相關資訊也無法讀取。</p>\\n<p>在台發展的義大利籍藝人韋佳德也在<a href=\\\"https://www.facebook.com/ruike85/photos/a.1482087922036077.1073741827.1482081335370069/1846985895546276/?type=3&amp;theater\\\">臉書PO</a>出加拿大移民部網站遭塞爆的截圖，並表達「覺得遭透了」的心情。</p>\"\n" +
                "}";

        String note = "{\n" +
                "    \"id\": \"note1479018239603\",\n" +
                "    \"content\": \"a\",\n" +
                "    \"createTime\": 1479018239605,\n" +
                "    \"comment\": [],\n" +
                "    \"userId\": \"id\",\n" +
                "    \"userName\": \"Bernie\"\n" +
                "  }";
        JSONAssert.assertEquals(packClientCaptor.getValue(), pack, true);
        JSONAssert.assertEquals(noteClientCaptor.getValue(), note, true);

    }

}
