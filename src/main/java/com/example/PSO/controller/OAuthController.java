package com.example.PSO.controller;

import com.example.PSO.models.User;
import com.example.PSO.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private UserService userService;

    @GetMapping("/loginSuccess")
    public void getUserInfo(OAuth2AuthenticationToken authentication, HttpServletResponse response) throws IOException {
        User loggedUser = null;
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
            HttpEntity entity = new HttpEntity("", headers);
            ResponseEntity<Map> mapResponse = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = mapResponse.getBody();
            Optional<User> userByEmail = userService.getUserByEmail((String) userAttributes.get("email"));
            if(userByEmail.isPresent())
                loggedUser = userByEmail.get();
            else {
                String[] names = {"NO DATA", "NO DATA"};
                if(userAttributes.get("name")!=null)
                    names = userAttributes.get("name").toString().split(" ");
                loggedUser = new User("NO DATA", "NO DATA", "0000000000", names[0], names[1],
                        userAttributes.get("email").toString(), "zaq1@WSX");
                ResponseEntity<User> registerResponse = userService.registerUser(loggedUser);
                if (!registerResponse.getStatusCode().is2xxSuccessful())
                    response.sendRedirect("/login");
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(loggedUser, "", loggedUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            response.sendRedirect("/panel");
        }
    }
}
