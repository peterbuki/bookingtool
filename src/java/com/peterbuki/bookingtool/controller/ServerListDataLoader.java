package com.peterbuki.bookingtool.controller;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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

    //@EventListener(ApplicationReadyEvent.class)
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
                    if (targetType == LocalDateTime.class) {
                        return LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
                    } else {
                        return "Error: remove commas from your data!";
                    }
                }
            };
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addDeserializer(LocalDateTime.class,
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            mapper.registerModule(javaTimeModule);
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
