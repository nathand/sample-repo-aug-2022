package com.test.branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


@Data
@AllArgsConstructor
public class GithubInfo {
    @JsonProperty("user_name")
    private String username;
    @JsonProperty("display_name")
    private String displayName;
    private String avatar;
    @JsonProperty("geo_location")
    private String geoLocation;
    private String email;
    private String url;
    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;
    private List<RepoInfo> repos;
}