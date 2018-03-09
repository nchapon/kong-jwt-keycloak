package fr.cnp.examples.jwt.backend;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.StringReader;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by nchapon on 08/03/18.
 */
public class JwtConverterTest {


    private static String jwtToken="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDOE9EUW9SMEduN0xIUm93c01sQlloWDF2alNjRVA5ZGU3TW9PNjJGRzFBIn0.eyJqdGkiOiJhNGEwZTM0Ny0xNTI5LTQ4NmQtYjcwNi05ZGUwNDU1N2Q0MGUiLCJleHAiOjE1MjA2MTMzODMsIm5iZiI6MCwiaWF0IjoxNTIwNjEzMzIzLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiZGVtbyIsInN1YiI6ImVjOTEzMjViLWQzMmMtNDQ2YS1iNDViLWM0ZjMzZDRkYzc1OCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImRlbW8iLCJub25jZSI6IjYzMTA0Yzg0LWEzYmItNDM2ZC04MGQ3LTJhODhiNDkzNTg2NSIsImF1dGhfdGltZSI6MTUyMDYxMzMxMywic2Vzc2lvbl9zdGF0ZSI6ImVkZWUwMmFjLTZhZTMtNGZlMS1hNjEzLTdhMzY4Mjk4MWRjMyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZXNvdXJjZV9hY2Nlc3MiOnsiZGVtbyI6eyJyb2xlcyI6WyJ1c2VyIl19fSwibmFtZSI6Ik5pY29sYXMgQ2hhcG9uIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlciIsImdpdmVuX25hbWUiOiJOaWNvbGFzIiwiZmFtaWx5X25hbWUiOiJDaGFwb24iLCJlbWFpbCI6Im5jaGFwb25AZ21haWwuY29tIn0.S_eke37uuQJsHRgPgAgetLCVxLdqSSql8FIzbGF5hw99OE_Ejc7gpzYeU9Ye-mpXFcbntd4zb0cLsj6rZSBnzAhR5rnAsb8gR3jTy3OyYzYhV5tAPcVyiHjhaPAgOsm-okagM4OU8eHxtj1w-PnrG7AngIBPOe9TDjUtfzs_ZeGWcQMuXG7iniZVYCzqnbb9y3BLqRFeIUVCkOY-rioHN9CDvyXO7ZYjHNS6efJPT88dhdPwlH62a5iq5ZT23iiFH_3PFsn9lDW1nRRDPBcX8sfTvzBxpI3vUeflsJIkpR4rQtOwQUTAr4BWCYga1kXUVNx5cr5WJLSlwDe7RAES4w";


    //private final String PUBLIC_KEY_PEM="-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqMfSxELrtOS4vnDIAO5lekjOeWbEyJXKkk922O2XCmV8q60FiQKi3Q3ZiY3x23M8OLH50oGp75LbhepuDPe3i3akC3yFC/JSN1nliVw7gyNsOskGzCbgD704I41X8LJ3Zq2TeS3F6UvJxMdQ6UkHwctEocRI8Tn3CRlPg2d07MNspwQ2z11pKjc4VE9sHZplroA0WR94IBhRQf8CnoDUSA+HX6g9U+cFHAMOYJ8cRDPGBB46OQSJtJMLQupzLlYVaSMBYdigH1CE62D8EVbD7OjF2t6iFURdTQn2SFasULGbIYoInHiqXueSmSGhvqhcd3rCUf0eDiVg5wjGmPfsjwIDAQAB\n-----END PUBLIC KEY-----";
    private final String PUBLIC_KEY_PEM="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqMfSxELrtOS4vnDIAO5lekjOeWbEyJXKkk922O2XCmV8q60FiQKi3Q3ZiY3x23M8OLH50oGp75LbhepuDPe3i3akC3yFC/JSN1nliVw7gyNsOskGzCbgD704I41X8LJ3Zq2TeS3F6UvJxMdQ6UkHwctEocRI8Tn3CRlPg2d07MNspwQ2z11pKjc4VE9sHZplroA0WR94IBhRQf8CnoDUSA+HX6g9U+cFHAMOYJ8cRDPGBB46OQSJtJMLQupzLlYVaSMBYdigH1CE62D8EVbD7OjF2t6iFURdTQn2SFasULGbIYoInHiqXueSmSGhvqhcd3rCUf0eDiVg5wjGmPfsjwIDAQAB";


    @Test
    public void shouldExtractJwtPayload() throws Exception{


        Claims claims = Jwts
                .parser()
                .setSigningKey(decodePublicKey(Base64Utils.decodeFromString(PUBLIC_KEY_PEM)))
                .setAllowedClockSkewSeconds(1000000L)
                .parseClaimsJws(jwtToken)
                .getBody();


        assertThat(claims.getSubject()).isEqualTo("ec91325b-d32c-446a-b45b-c4f33d4dc758");
        assertThat(claims.getIssuer()).isEqualTo("http://localhost:8080/auth/realms/master");
        assertThat(claims.getAudience()).isEqualTo("demo");
        assertThat(claims.get("name",String.class)).isEqualTo("Nicolas Chapon");
        LinkedHashMap resource_access = claims.get("resource_access", LinkedHashMap.class);


        //String roles = (String) resource_access.get("demo");
        assertThat(extractRoles(resource_access)).hasSize(1).contains("user");

    }

    private List<String> extractRoles(LinkedHashMap resource_access) {

        LinkedHashMap demo = (LinkedHashMap) resource_access.get("demo");

        return (List) demo.get("roles");

    }


    private PublicKey decodePublicKey(byte[] der) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {

        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(der));
        return pubKey;
    }


    @Test
    public void testCreateJWT() {

        KeyPair keyPair = RsaProvider.generateKeyPair();


        String jwtToken = Jwts.builder().setSubject("Joe").signWith(SignatureAlgorithm.RS256, keyPair.getPrivate()).compact();


        PublicKey publicKey = keyPair.getPublic();
        System.out.printf("Format=%s",publicKey.getFormat());

        System.out.println(publicKey.toString());


        String subject = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwtToken).getBody().getSubject();

        assertThat(subject).isEqualTo("Joe");


    }
}
