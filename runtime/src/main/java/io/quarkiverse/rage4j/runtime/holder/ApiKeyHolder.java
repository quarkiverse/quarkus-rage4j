package io.quarkiverse.rage4j.runtime.holder;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApiKeyHolder {
    private String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
