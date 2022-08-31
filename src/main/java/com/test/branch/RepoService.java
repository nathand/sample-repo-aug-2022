package com.test.branch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RepoService {

    @Autowired
    private RestTemplate restTemplate;

    private String userUrl = "https://api.github.com/users/%s";
    private String repoUrl = "https://api.github.com/users/%s/repos";

    public GithubInfo getGithubInfo(String username) {
        if (username.contains("%")) { // reject input with any sign of url encoding.
            return null;
        }
        GithubInfo info = getUser(username);
        if (info != null) {
            List<RepoInfo> repos = getUserRepos(username);
            info.setRepos(repos);
        }
        return info;
    }

    protected GithubInfo getUser(String user) {
        Map<String, Object> response;
        GithubInfo info = null;
        try {
            response = restTemplate.getForObject(String.format(userUrl, user), Map.class);
            var username = (String) response.get("login");
            var displayName = (String) response.get("name");
            var email = (String) response.get("email");
            var geoLocation = (String) response.get("location");
            var avatar = (String) response.get("avatar_url");
            var url = (String) response.get("html_url");
            var createdAt = OffsetDateTime.parse((String)response.get("created_at"));
            info = new GithubInfo(username, displayName, avatar, geoLocation, email, url, createdAt, new ArrayList<>());
        } catch (RestClientException e) {
            // TODO log something useful here
        }
        return info;            

    }

    protected List<RepoInfo> getUserRepos(String user) {
        List<RepoInfo> repos = new ArrayList<>();
        try {
            List<Map<String, Object>> response = restTemplate.getForObject(String.format(repoUrl, user), List.class);
            response.forEach(m -> {
                repos.add(new RepoInfo((String)m.get("name"), (String)m.get("html_url")));
            });
        } catch (RestClientException e) {
            // TODO log something useful here
        }
        return repos;
    }
}