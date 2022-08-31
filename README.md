# Getting Started

### Decisions

One of the more noticible decisions made in this repo was the decision to use a `Map` and `List` over defined POJOs for use with the `RestTemplate` when fetching data from the Github endpoints. I would contend that this is a sub-optimal solution since Jackson sereializes the http responses to the generic types. I then manually parse the response to fill out a central GithubInfo object. `RestTemplate` can serialize directly to more complex objects, but I opted to work with `String` and generic types to facilitate faster iteration over the test dataset (`src/test/resources/data/`). `MockRestServiceServer` may be the solution to this, but it just was not explored in the interest of time.

### Architecture

* Http Related code is isolated to the `WebController`, which calls `RepoService.getGithubInfo()` to perform the github lookup
* `getGithubInfo()` is the integration of 2 independent methods that do the rest calls to Github and fetch user and repo information.


# How to use

## Setup
Project is built on [JDK 17](https://adoptium.net/), install if necessary.

## Build
From a terminal, run `./gradlew build` to build the codebase. Or `./gradlew.bat build` for Windows. The output should say, BUILD SUCCESSFUL. Then the application can be run.

## Run
After builing successfully, run `java -jar build/libs/branch-0.0.1.jar` to start the application. You can start to use the application once the terminal returns the output: `Tomcat started on port(s): 8080 (http) with context path ''`.

## Use
Perform a GET using a preferred HTTP client to `http://localhost:8080/username/<username>`, replacing `<username>` with a target Github username. 