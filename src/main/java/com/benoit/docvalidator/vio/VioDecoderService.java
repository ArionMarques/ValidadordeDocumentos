package com.benoit.docvalidator.vio;

public class VioDecoderService {

    private final VioAuthService authService;
    private final VioClient client;

    public VioDecoderService(
        VioAuthService authService,
        VioClient client
    ) {
        this.authService = authService;
        this.client = client;
    }

    public String decode(String payload) {

        String token = authService.getToken();

        return client.decode(token, payload);

    }

}