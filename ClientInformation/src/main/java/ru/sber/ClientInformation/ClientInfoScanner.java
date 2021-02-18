package ru.sber.ClientInformation;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ClientInfoScanner {

    private static String jsonFileToString(String filepath) throws IOException {
        Path path = Paths.get(filepath);

        if (Files.exists(path)) {
            return Files.readString(path);
        } else {
            throw new IllegalArgumentException("File does not exist.");
        }
    }

    public static Client getClientInfoSimple(String filepath)
            throws IOException {

        String jsonStr = jsonFileToString(filepath);

        // using Jackson lib to map the json
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(jsonStr, Map.class);

        return switch(ClientType.valueOf((String) map.get("clientType"))) {
        // using switch expression without fall-through to build and return
        // Client object

            case LEGAL_ENTITY ->
                    new LegalEntityClient(
                            (String) map.get("name"),
                            (int) map.get("inn"),
                            (boolean) map.get("isSanctioned")
                    );

            case INDIVIDUAL ->
                    new IndividualClient(
                            (String) map.get("name"),
                            (String) map.get("surname"),
                            (int) map.get("inn")
                    );

            case HOLDING ->
                    new HoldingClient(
                            (String) map.get("name"),
                            (int) map.get("inn"),
                            (boolean) map.get("isSanctioned"),
                            (int) map.get("authorizedCapital"),
                            (int) map.get("regNum")
                    );
        };
    }

    public static Client getClientInfo(String filepath) throws IOException {

        String jsonStr = jsonFileToString(filepath);

        // using Jackson lib to map the json
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(jsonStr, Map.class);

        ClientType clientType =
                ClientType.valueOf((String) map.get("clientType"));

        // using design pattern Strategy to return specific client object:
        // enum value determines which of the overridden createClient() method
        // versions will be called
        return clientType.createClient(map);
    }
}

