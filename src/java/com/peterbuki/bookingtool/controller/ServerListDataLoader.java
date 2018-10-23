package com.peterbuki.bookingtool.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class ServerListDataLoader {
    private ServerService serverService;
    private String url;

    private static final Logger logger = LoggerFactory.getLogger(ServerListDataLoader.class);

    @Autowired
    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    @Value("${loader.url}")
    public void setLoaderUrl(String url) {
        this.url = url;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData() throws Exception {
        List<Server> servers = loadObjectList(Server.class, url);

        serverService.addAll(servers);
    }

    public <T> List<T> loadObjectList(Class<T> type, String url) {

        try {
            final CsvMapper mapper = new CsvMapper();

            final CsvSchema schema = CsvSchema.builder().setUseHeader(true).build();

            DeserializationProblemHandler deserializationProblemHandler = new DeserializationProblemHandler() {
                @Override
                public Object handleWeirdStringValue(DeserializationContext ctxt,
                                                     Class<?> targetType,
                                                     String valueToConvert,
                                                     String failureMsg)
                        throws IOException {
                    if (targetType == Date.class) {
                        return new Date();
                    }
                    else {
                        return "Error: remove commas from your data!";
                    }
                }
            };
            MappingIterator<T> readValues =
                    mapper.enable(CsvParser.Feature.TRIM_SPACES)
                            // FIXME: objects with extra fields should be marked as invalid
                            .enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE)
                            // FIXME: objects with invalid Dates should be marked s invalid
                            .addHandler(deserializationProblemHandler)
                            .reader(schema).forType(Server.class)
                            .readValues(new URL(url));
            return readValues.readAll();
        } catch (Exception e) {
            logger.error("Error occurred while loading object list from file ", e);
            return Collections.emptyList();
        }
    }


}
