package com.frontend.vj5frontend.components.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frontend.vj5frontend.components.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class FrontEndService {

    @Autowired
    ObjectMapper objectMapper;

    public List<Client> findPageable(int pageNo, int pageSize, String sortField, String sortDirection) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/clients/page?pageNo=" + pageNo + "&pageSize=" + pageSize +
                        "&sortField" + sortField + "&sortDirection" + sortDirection))
                .GET()
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var json = response.body();
            var jsonNode = objectMapper.readTree(json);
            var sub = jsonNode.path("content");
            return objectMapper.readValue(objectMapper.treeAsTokens(jsonNode), new TypeReference<>() {
            });
        } catch (Exception ex) { throw new Exception(ex); }

    }
}
