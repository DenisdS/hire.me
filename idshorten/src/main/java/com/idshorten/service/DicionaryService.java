package com.idshorten.service;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Properties;

@Service
public class DicionaryService {

    private static final Logger LOG = LoggerFactory.getLogger(DicionaryService.class);

    @Value("${idshort.dot}")
    private String dot;

    public String extension;

    public String verifyDicionary(String key) throws IOException, ConfigurationException {
        LOG.info("Verificando o dicionario de extensoes");
        if(!existsKey(key)){
            associateValueOfPropertie(key);
        }
        extension = getValueOfPropertie(key);
        return extension;
    }

    public Boolean existsKey(String key) {
        LOG.info("Verificando se a extensao {} existe no dicionario", key);
        ArrayList<String> lst = getKeys();
        if(lst.contains(key)){
            return true;
        }
        return false;
    }

    public boolean existsValueAssociate(String key) {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("src/dicionary.properties");
            Object value = config.getProperty(key);
            if (value.toString().contains("?")) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getValueOfPropertie(String key) throws IOException {
        Properties properties = new Properties();
        String file = new File("src/dicionary.properties").getAbsolutePath();
        FileInputStream input = new FileInputStream(file);
        LOG.info("Carregando o dicionario");
        properties.load(input);

        LOG.info("Capturando o valor da chave");
        String value = properties.getProperty(key);

        LOG.info("Temos o valor {} referente a chave {} ", value, key);
        return value;
    }

    public ArrayList<String> getKeys() {
        String keysFile = null;
        ArrayList<String> lstKeys = new ArrayList<>();

        try {
            LOG.info("Acessando o dicionario");
            PropertiesConfiguration config = new PropertiesConfiguration("src/dicionary.properties");
            Iterator<String> keys = config.getKeys();

            while (keys.hasNext()) {
                keysFile = (String) keys.next();
                lstKeys.add(keysFile);
            }
            LOG.info("Fim da captura de todas as chaves do dicionario");

            return lstKeys;
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return lstKeys;
    }

    public void associateValueOfPropertie(String key) throws ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration("src/dicionary.properties");
        LOG.info("Associando valor a chave {}", key);
        config.setProperty(key, RandomStringUtils.randomAlphabetic(3).toLowerCase());

        LOG.info("Salvando chave e valor no dicionario");
        config.save();
    }

    public String validNewExtension(String extension){
        LOG.info("Validando a extensao {} ", extension);
       String formatExtension = extension;
        if(!extension.startsWith(dot)) {
            formatExtension = dot.concat(extension);
        }
        return formatExtension;
    }
}
