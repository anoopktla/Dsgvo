package de.logicline.adv.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.logicline.adv.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ResourceService {

    @Autowired
    public ResourceService(ObjectMapper objectMapper,ResourceLoader resourceLoader){
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }
    private List<Country> countryList;

    private ObjectMapper objectMapper;

    private ResourceLoader resourceLoader;
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PATH_PREFIX = "classpath:json";
    private static final String FILE_NAME = "EU_countries.json";

    @PostConstruct
    public void init() throws IOException {
        Resource resource = resourceLoader.getResource(PATH_PREFIX + FILE_SEPARATOR + FILE_NAME );
        InputStream fileAsInputStream = resource.getInputStream();
        countryList = objectMapper.readValue(fileAsInputStream, new TypeReference<List<Country>>() {});
    }

    public List<Country> getCountryList() {
        return countryList;
    }


}




