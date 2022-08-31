package com.test.branch;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class BranchApplicationTests {

	@MockBean
	private RestTemplate rt;

	@Autowired
	private RepoService repoService;

	@Test
	void contextLoads() {

	}
	@Test
	void getGithubInfo_Failure() throws IOException {
		Map<String, Object> response = getResourceAsMap("data/notfound.json");
		when(rt.getForObject(anyString(), any())).thenThrow(new RestClientException("invalid user")).thenReturn(response);
		GithubInfo data = repoService.getUser("demo");
		assertNull(data);
	}
	@Test
	void getGithubInfo_Success() throws IOException {
		Map<String, Object> response = getResourceAsMap("data/user.json");
		when(rt.getForObject(anyString(), any())).thenReturn(response);
		GithubInfo data = repoService.getUser("demo");
		assertEquals("octocat", data.getUsername());
		assertEquals(null, data.getEmail());
	}

	@Test
	void getRepoInfo_success() throws IOException {
		List<Map<String, Object>> response = getResourceAsList("data/repos.json");
		when(rt.getForObject(anyString(), any())).thenReturn(response);
		List<RepoInfo> repos = repoService.getUserRepos("demo");
		RepoInfo first = repos.get(0);
		assertEquals("boysenberry-repo-1", first.getName());
		assertEquals("https://github.com/octocat/boysenberry-repo-1", first.getUrl());
		assertEquals(8, repos.size());
	}



	public Map<String, Object> getResourceAsMap(String fileLocation) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Resource file = new ClassPathResource(fileLocation);
		try (BufferedReader r = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String resource = r.lines().collect(Collectors.joining());
			Map<String, Object> entity = mapper.readValue(resource, Map.class);
			return entity;
		}
	}
	public List<Map<String, Object>> getResourceAsList(String fileLocation) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Resource file = new ClassPathResource(fileLocation);
		try (BufferedReader r = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String resource = r.lines().collect(Collectors.joining());
			List<Map<String, Object>> entity = mapper.readValue(resource, List.class);
			return entity;
		}
	}
}
