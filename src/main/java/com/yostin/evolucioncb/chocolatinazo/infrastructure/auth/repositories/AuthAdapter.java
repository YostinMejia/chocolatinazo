package com.yostin.evolucioncb.chocolatinazo.infrastructure.auth.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.dto.AuthResult;
import com.yostin.evolucioncb.chocolatinazo.domain.exceptions.AuthException;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
    public void signUp(String email, String password, String username, String role) {
        try {
            cognitoClient.signUp(SignUpRequest.builder()
                    .clientId(clientId)
                    .secretHash(calculateSecretHash(email))
                    .username(email)
                    .password(password)
                    .userAttributes(
                            AttributeType.builder().name("email").value(email).build(),
                            AttributeType.builder().name("preferred_username").value(username).build(),
                            AttributeType.builder().name("custom:role").value(role).build()

                    )
                    .build());
        } catch (CognitoIdentityProviderException e) {
            throw new AuthException(e.getMessage(), e);
        }
    }

    /**
     * Authenticates a user against AWS Cognito using the USER_PASSWORD_AUTH flow.
     *
     * <p>On success, Cognito returns three tokens:</p>
     * <ul>
     *   <li><b>idToken</b>: contains user claims including custom:role — use this for authorization</li>
     *   <li><b>accessToken</b>: used to call Cognito-protected endpoints</li>
     *   <li><b>refreshToken</b>: used to renew the other two tokens when they expire</li>
     * </ul>
     *
     * @see <a href="https://docs.aws.amazon.com/cognito/latest/developerguide/amazon-cognito-user-pools-authentication-flow.html">
     *      AWS Cognito - Authentication flow</a>
     */
    @Override

    public AuthResult login(String email, String password) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("USERNAME", email);
            params.put("PASSWORD", password);
            params.put("SECRET_HASH", calculateSecretHash(email));

            InitiateAuthResponse response = cognitoClient.initiateAuth(
                    InitiateAuthRequest.builder()
                            .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
                            .clientId(clientId)
                            .authParameters(params)
                            .build()
            );

            AuthenticationResultType result = response.authenticationResult();
            return new AuthResult(
                    result.idToken(),
                    result.accessToken(),
                    result.refreshToken()
            );

        } catch (NotAuthorizedException | UserNotConfirmedException | UserNotFoundException  e) {
            throw new AuthException("Authentication failed:", e);
        }  catch (CognitoIdentityProviderException e) {
            throw new AuthException("Authentication failed: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new AuthException("Unexpected error during login", e);
        }
    }

    @Override
    public void confirmSignUp(String email, String confirmationCode) {
        try {
            cognitoClient.confirmSignUp(
                    ConfirmSignUpRequest.builder()
                            .clientId(clientId)
                            .secretHash(calculateSecretHash(email))
                            .username(email)
                            .confirmationCode(confirmationCode)
                            .build()
            );
        } catch (CodeMismatchException e) {
            throw new AuthException("Invalid confirmation code", e);
        } catch (ExpiredCodeException e) {
            throw new AuthException("Confirmation code has expired", e);
        } catch (UserNotFoundException e) {
            throw new AuthException("User not found", e);
        } catch (CognitoIdentityProviderException e) {
            throw new AuthException("Confirmation failed: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new AuthException("Unexpected error during confirmation", e);
        }
    }

    private String calculateSecretHash(String username) {
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
            throw new AuthException(e.getMessage(),e);
        }
    }
}
