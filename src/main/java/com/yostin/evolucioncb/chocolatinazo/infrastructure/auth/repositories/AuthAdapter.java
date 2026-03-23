package com.yostin.evolucioncb.chocolatinazo.infrastructure.auth.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.repositories.AuthRepository;
import com.yostin.evolucioncb.chocolatinazo.dto.SignUpDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Repository
@Slf4j
public class AuthAdapter implements AuthRepository {

    @Value("${aws.cognito.clientId}")
    private String clientId;

    @Value("${aws.cognito.clientSecret}")
    private String clientSecret;

    private final CognitoIdentityProviderClient cognitoClient;

    public AuthAdapter(CognitoIdentityProviderClient cognitoClient) {
        this.cognitoClient = cognitoClient;
    }

    @Override
    public void signUp(SignUpDto signUpDto) {
            try {
                cognitoClient.signUp(SignUpRequest.builder()
                        .clientId(clientId)
                        .secretHash(calculateSecretHash(signUpDto.email()))
                        .username(signUpDto.email())
                        .password(signUpDto.password())
                        .userAttributes(
                                AttributeType.builder().name("email").value(signUpDto.email()).build(),
                                AttributeType.builder().name("preferred_username").value(signUpDto.username()).build()
                        )
                        .build());
            }catch (CognitoIdentityProviderException  e ){
                log.error("e: ", e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
    private String calculateSecretHash(String username) throws Exception {
        try {
            String message = username + clientId;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(
                    clientSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"
            ));
            return Base64.getEncoder().encodeToString(
                    mac.doFinal(message.getBytes(StandardCharsets.UTF_8))
            );
        } catch (Exception e) {
            throw new Exception("Error calculating secret hash", e);
        }
    }
}
