package pt.ua.deti.store.entities;

public class TokenResponse {
    private final String token;
    private final Boolean success;

    public TokenResponse(String token, Boolean success) {
        this.token = token;
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public Boolean isSuccess() {
        return success;
    }
}
