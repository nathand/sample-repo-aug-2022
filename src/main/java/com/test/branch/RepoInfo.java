package com.test.branch;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RepoInfo {
    private String name;
    private String url;
}