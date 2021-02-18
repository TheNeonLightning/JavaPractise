package ru.sber.ClientInformation;


import java.util.Map;

public enum ClientType {
    LEGAL_ENTITY {
        @Override
        public Client createClient(Map<String, Object> map) {
            return new LegalEntityClient(
                    (String) map.get("name"),
                    (int) map.get("inn"),
                    (boolean) map.get("isSanctioned")
            );
        }
    },
    INDIVIDUAL {
        @Override
        public Client createClient(Map<String, Object> map) {
            return new IndividualClient(
                    (String) map.get("name"),
                    (String) map.get("surname"),
                    (int) map.get("inn")
            );
        }
    },
    HOLDING {
        @Override
        public Client createClient(Map<String, Object> map) {
            return new HoldingClient(
                    (String) map.get("name"),
                    (int) map.get("inn"),
                    (boolean) map.get("isSanctioned"),
                    (int) map.get("authorizedCapital"),
                    (int) map.get("regNum")
            );
        }
    };

    public abstract Client createClient(Map<String, Object> map);
}
