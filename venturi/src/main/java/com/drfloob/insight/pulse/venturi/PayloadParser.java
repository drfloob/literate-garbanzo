package com.drfloob.insight.pulse.venturi;

import com.drfloob.insight.pulse.venturi.schema.Root;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by aj on 9/20/16.
 */
public class PayloadParser {

    private PayloadParser() {
    }

    private static ObjectMapper mapper = new ObjectMapper();


    /*----------------------------------------------------------------
     Parse To-User
     ----------------------------------------------------------------*/

    public static String getToUser(Root r) {
        String type = r.getType().toString();

        // Todo: figure out how to parse these
//        ArrayList<String> createSet = new ArrayList<String>();
//        createSet.add("CreateEvent");
//        createSet.add("DeleteEvent");

        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(r.getPayload().toString());
        } catch(IOException e) {
            System.err.println("Oops: json mapper failed to parse the payload");
            // TODO: better error handling
            return null;
        }

        ArrayList<String> issuesSet = new ArrayList<String>();
        issuesSet.add("IssueCommentEvent");
        issuesSet.add("IssuesEvent");

        ArrayList<String> repoExtractSet = new ArrayList<String>();
        repoExtractSet.add("PushEvent");
        repoExtractSet.add("ReleaseEvent");
        repoExtractSet.add("WatchEvent");

        if (type.equals("CommitCommentEvent"))
            return extractToUserFromComment(rootNode);
        else if (issuesSet.contains(type))
            return extractToUserFromIssueComment(rootNode);
        else if (type.equals("ForkEvent"))
            return extractToUserFromFork(rootNode);
        else if (type.equals("MemberEvent"))
            return extractToUserFromMember(rootNode);
        else if (type.equals("PullRequestEvent"))
            return extractToUserFromPullRequest(rootNode);
        else if (type.equals("PullRequestReviewCommentEvent"))
            return extractToUserFromPullRequestReviewComment(rootNode);
        else if (repoExtractSet.contains(type))
            return extractToUserFromRepoName(r);


        return null;
    }

    private static String extractToUserFromComment(JsonNode rootNode) {
        return rootNode.path("comment").path("repository").path("owner").path("login").asText();
    }
    private static String extractToUserFromFork(JsonNode rootNode) {
        return rootNode.path("forkee").path("owner").path("login").asText();
    }
    private static String extractToUserFromIssueComment(JsonNode rootNode) {
        return rootNode.path("issue").path("user").path("login").asText();
    }
    private static String extractToUserFromMember(JsonNode rootNode) {
        return rootNode.path("member").path("login").asText();
    }
    private static String extractToUserFromPullRequest(JsonNode rootNode) {
        return rootNode.path("pull_request").path("base").path("user").path("login").asText();
    }
    private static String extractToUserFromPullRequestReviewComment(JsonNode rootNode) {
        return rootNode.path("pull_request").path("user").path("login").asText();
    }
    private static String extractToUserFromRepoName(Root r) {
        String name = r.getRepo().getName().toString();
        return name.subSequence(0, name.indexOf("/")).toString();
    }


    /*----------------------------------------------------------------
     Parse URL
     ----------------------------------------------------------------*/


    public static String getUrl(Root r) {
        String type = r.getType().toString();

        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(r.getPayload().toString());
        } catch(IOException e) {
            System.err.println("Oops: json mapper failed to parse the payload");
            // TODO: better error handling
            return null;
        }

        ArrayList<String> issuesSet = new ArrayList<String>();
        issuesSet.add("IssueCommentEvent");
        issuesSet.add("IssuesEvent");

        ArrayList<String> repoExtractSet = new ArrayList<String>();
        repoExtractSet.add("PushEvent");
        repoExtractSet.add("ReleaseEvent");
        repoExtractSet.add("WatchEvent");

        if (type.equals("CommitCommentEvent"))
            return extractURLFromComment(rootNode);
        else if (issuesSet.contains(type))
            return extractURLFromIssueComment(rootNode);
        else if (type.equals("ForkEvent"))
            return extractURLFromFork(rootNode);
        else if (type.equals("MemberEvent"))
            return extractURLFromRoot(r);
        else if (type.equals("PullRequestEvent"))
            return extractURLFromRoot(r);
        else if (type.equals("PullRequestReviewCommentEvent"))
            return extractURLFromComment(rootNode);
        else if (repoExtractSet.contains(type))
            return extractURLFromRoot(r);

        return null;
    }

    private static String extractURLFromComment(JsonNode rootNode) {
        return rootNode.path("comment").path("url").asText();
    }
    private static String extractURLFromIssueComment(JsonNode rootNode) {
        return rootNode.path("issue").path("url").asText();
    }
    private static String extractURLFromFork(JsonNode rootNode) {
        return rootNode.path("forkee").path("url").asText();
    }
    private static String extractURLFromRoot(Root r) {
        return r.getRepo().getUrl().toString();
    }
}
