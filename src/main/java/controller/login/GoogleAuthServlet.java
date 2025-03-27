package controller.login;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleAuthServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String CLIENT_ID = "135248440996-9ngr32lpe612ll97qived6c1rqd7mrbp.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8080/GoogleAuthServlet";

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            try {
                AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JSON_FACTORY,
                        CLIENT_ID,
                        CLIENT_SECRET,
                        Collections.singletonList("https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"))
                        .setAccessType("offline")
                        .build();

                AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI);
                response.sendRedirect(authorizationUrl.build());
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(GoogleAuthServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JSON_FACTORY,
                        CLIENT_ID,
                        CLIENT_SECRET,
                        code,
                        REDIRECT_URI)
                        .execute();

                GoogleIdToken idToken = tokenResponse.parseIdToken();
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");

                request.getSession().setAttribute("user_email", email);
                request.getSession().setAttribute("user_name", name);

                response.sendRedirect("homepage.jsp");
            } catch (GeneralSecurityException ex) {
                Logger.getLogger(GoogleAuthServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
