import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ExternalApiMockImpl {
    public static void main(String[] args)
    {
        WireMockServer wireMockServer = new WireMockServer(8000);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8000);
        stubFor(get(urlEqualTo("/api/real-estates/SL_POL?page=1")).willReturn(aResponse().withBody("{\n" +
                "\"totalPages\":1,\n" +
                "\"data\":[\n" +
                "{\n" +
                "\"id\":\"d8834ae1-8129-4a7d-8ecc-e8b4b53531e9\",\n" +
                "\"type\":\"detached_house\",\n" +
                "\"price\":\"760332.99\",\n" +
                "\"description\":\"A really nice house you should buy it\",\n" +
                "\"area\":\"120\",\n" +
                "\"rooms\":4\n" +
                "},\n" +
                "{\n" +
                "\"id\":\"e2145cc8-730f-40e2-a7a0-f90816eae55c\",\n" +
                "\"type\":\"flat\",\n" +
                "\"price\":\"490534\",\n" +
                "\"description\":\"Live your dreams at the heart of Katowice\",\n" +
                "\"area\":\"44.5\",\n" +
                "\"rooms\":2\n" +
                "},\n" +
                "{\n" +
                "\"id\":\"5d301374-f8f8-48c7-ac79-4e8e68659b39\",\n" +
                "\"type\":\"flat\",\n" +
                "\"price\":\"540534\",\n" +
                "\"description\":\"A space for you and your universe\",\n" +
                "\"area\":\"70.34\",\n" +
                "\"rooms\":3\n" +
                "},\n" +
                "{\n" +
                "\"id\":\"4caec08d-3cb7-46e0-9883-691eae200584\",\n" +
                "\"type\":\"semi_detached_house\",\n" +
                "\"price\":\"778098\",\n" +
                "\"description\":\"Your new place on earth\",\n" +
                "\"area\":\"150\",\n" +
                "\"rooms\":3\n" +
                "},\n" +
                "{\n" +
                "\"id\":\"0db65236-dfb0-4d70-990f-63411af8213c\",\n" +
                "\"type\":\"terraced_house\",\n" +
                "\"price\":\"431098\",\n" +
                "\"description\":\"The greenest area in Poland for you and your family\",\n" +
                "\"area\":\"90\",\n" +
                "\"rooms\":4\n" +
                "}\n" +
                "]\n" +
                "}")));

    }
}
