package anrix.model.service;

import java.util.UUID;

public class UUIDService {
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
