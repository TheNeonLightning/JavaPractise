import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.sber.ClientInformation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ClientInfoScannerTest {

    private static final String holdingClient= "HoldingClient.json";
    private static final String individualClient = "IndividualClient.json";
    private static final String legalEntityClient = "LegalEntityClient.json";

    private static String holdingClientFilePath;
    private static String individualClientFilePath;
    private static String LegalEntityClientFilePath;

    @BeforeClass
    public static void getHoldingClientFileAbsolutePath() {
        Path resourceDirectory =
                Paths.get("src","test","resources", holdingClient);
        holdingClientFilePath = resourceDirectory.toFile().getAbsolutePath();
    }

    @BeforeClass
    public static void getIndividualClientFileAbsolutePath() {
        Path resourceDirectory =
                Paths.get("src","test","resources", individualClient);
        individualClientFilePath = resourceDirectory.toFile().getAbsolutePath();
    }

    @BeforeClass
    public static void getLegalEntityClientFileAbsolutePath() {
        Path resourceDirectory =
                Paths.get("src","test","resources", legalEntityClient);
        LegalEntityClientFilePath = resourceDirectory.toFile().getAbsolutePath();
    }


    private void checkHoldingClient(HoldingClient client) {

        Assert.assertNotNull(client);

        Assert.assertEquals(client.getClientType(), ClientType.HOLDING);

        Assert.assertEquals(client.getName(), "Агрохолдинг");

        Assert.assertEquals(client.getInn(), 13245552);

        Assert.assertEquals(client.isSanctioned(), false);

        Assert.assertEquals(client.getAuthorizedCapital(), 2343554);

        Assert.assertEquals(client.getRegNum(), 5381019);
    }

    private void checkIndividualClient(IndividualClient client) {

        Assert.assertNotNull(client);

        Assert.assertEquals(client.getClientType(), ClientType.INDIVIDUAL);

        Assert.assertEquals(client.getName(), "Имя");

        Assert.assertEquals(client.getSurname(), "Фамилия");

        Assert.assertEquals(client.getInn(), 2131284);
    }

    private void checkLegalEntityClient(LegalEntityClient client) {

        Assert.assertNotNull(client);

        Assert.assertEquals(client.getClientType(), ClientType.LEGAL_ENTITY);

        Assert.assertEquals(client.getName(), "ООО Матрешка");

        Assert.assertEquals(client.getInn(), 13242352);

        Assert.assertEquals(client.isSanctioned(), true);
    }

    @Test
    public void testSimpleScannerOnHoldingClient() throws IOException {

        HoldingClient holdingClient = (HoldingClient)
                ClientInfoScanner.getClientInfoSimple(holdingClientFilePath);
        checkHoldingClient(holdingClient);
    }

    @Test
    public void testSimpleScannerOnIndividualClient() throws IOException {
        IndividualClient individualClient = (IndividualClient)
                ClientInfoScanner.getClientInfoSimple(individualClientFilePath);
        checkIndividualClient(individualClient);
    }

    @Test
    public void testSimpleScannerOnLegalEntityClient() throws IOException {
        LegalEntityClient legalEntityClient = (LegalEntityClient)
                ClientInfoScanner.getClientInfoSimple(LegalEntityClientFilePath);
        checkLegalEntityClient(legalEntityClient);
    }

    @Test
    public void testScannerOnHoldingClient() throws IOException {
        HoldingClient holdingClient = (HoldingClient)
                ClientInfoScanner.getClientInfo(holdingClientFilePath);
        checkHoldingClient(holdingClient);
    }

    @Test
    public void testScannerOnIndividualClient() throws IOException {
        IndividualClient individualClient = (IndividualClient)
                ClientInfoScanner.getClientInfo(individualClientFilePath);
        checkIndividualClient(individualClient);
    }

    @Test
    public void testClientInfoScannerOnLegalEntityClient() throws IOException {
        LegalEntityClient legalEntityClient = (LegalEntityClient)
                ClientInfoScanner.getClientInfo(LegalEntityClientFilePath);
        checkLegalEntityClient(legalEntityClient);
    }
}
