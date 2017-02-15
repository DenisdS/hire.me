package com.idshorten.service;

import com.idshorten.enums.MappedErrorEnum;
import com.idshorten.model.ShortenURL;
import com.idshorten.repository.ShortenURLRepository;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
public class ShortenURLService {

    private static final Logger LOG = LoggerFactory.getLogger(ShortenURLService.class);

    @Autowired
    ShortenURLRepository shortenURLRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    DicionaryService dicionaryService;

    private String lastPart;
    private String secondPart;
    private String thirdPart;
    private String firstPart;

    private String dot =".";

    @Value("${idshort.prefixURL}")
    private String prefixUrl;

    @Value("${idshort.separator}")
    private String separator;

    @Value("${idshort.protocol}")
    private String protocol;

    private String shortendURL;

    public ShortenURL create(String url, String customAlias) throws IOException, ConfigurationException {
        LOG.info("Inicio do encurtamento da URL");
        String alias = generatedCustomAlias(customAlias);
        String shortenedUrl = generatedShortenURL(url, alias);

        ShortenURL shortenURLObj = new ShortenURL(url,alias, shortenedUrl, System.currentTimeMillis());
        shortenURLObj.setCustomAlias(secondPart);

        LOG.info("Salvando as informacoes da URL {} na base de dados", url);
        shortenURLRepository.addShorten(shortenURLObj);
        LOG.info("As informacoes da URL foram salvas com sucesso");

        return shortenURLObj;
    }

    public Boolean verifyURL(String url){
        LOG.info("Verificando se a URL {}  ja existe na base de dados", url);
        Object shortenURLReturn = shortenURLRepository.getSqlGetURL(url);
        LOG.info("Fim da verificacao da URL");
        if(shortenURLReturn==null)
            return false;
        return true;
    }

    public String addingNewExtension(String extension) throws ConfigurationException, IOException {
        LOG.info("Inicio da adicao da extensao {} passada no request", extension);
        String valueOfExtension = null;

        extension = dicionaryService.validNewExtension(extension);

        if(!dicionaryService.existsKey(extension)){
            dicionaryService.associateValueOfPropertie(extension);
        }
        valueOfExtension =  dicionaryService.getValueOfPropertie(extension);

        LOG.info("A extensao foi adicionada");
        return valueOfExtension;
    }

    public Object getInformations(String url){
        LOG.info("Capturando informacoes sobre customAlias");
        Object informations = getInformationAboutURL(url);

        LOG.info("As informacoes da {} foram capturadas", url);
        return informations;
    }

    public Object verifyAlias(String aliasInput) {
        List<String> alias = shortenURLRepository.getAllAlias();

        if(alias.contains(aliasInput)) {
            String error = "ERR_CODE: " + MappedErrorEnum.CUSTOM_ALIAS_ALREADY_EXISTS.valueOfError + " Description: " + MappedErrorEnum.CUSTOM_ALIAS_ALREADY_EXISTS;
            LOG.error(error);
            return error;
        }else{
            return aliasInput;
        }
    }

    public Object existsURLAssociate(String shortendURL) {
        LOG.info("Verificando se a URLEncurtada {} possui URL Original", shortendURL);

        Object originalURL = shortenURLRepository.getURL(shortendURL);
        if(originalURL==null){
            String error = "ERR_CODE: "+MappedErrorEnum.SHORTENED_URL_NOT_FOUND.valueOfError+" Description: "+MappedErrorEnum.SHORTENED_URL_NOT_FOUND;
            LOG.error(error);
            return error;
        }else{
            return originalURL.toString();
        }
    }

    private String generatedShortenURL(String url, String customAlias) throws IOException, ConfigurationException {
        LOG.info("Gerando as informacoes da URL {} na base de dados", url);
        String valueOfExtension = getExtension(url);

        LOG.info("Definicao das partes da URL encurtada");
        firstPart = firstPart.replace(firstPart,prefixUrl);

        shortendURL = protocol.concat(firstPart).concat(separator).concat(customAlias).concat(valueOfExtension);
        LOG.info("A URL encurtada {} esta definida", shortendURL);

        return shortendURL;
    }

    private String getExtension(String url) throws IOException, ConfigurationException {
        LOG.info("Capturando a extensao da URL");
        String keyDicionary = splitURL(url);

        LOG.info("Enviando para o Dicionary a chave {} ", keyDicionary);
        String returnDicionary = dicionaryService.verifyDicionary(keyDicionary);
        if(returnDicionary==null || returnDicionary.isEmpty()){
            dicionaryService.associateValueOfPropertie(keyDicionary);
        }

        returnDicionary = dicionaryService.getValueOfPropertie(keyDicionary);

        LOG.info("Fim da captura da extensao da URL");
        return returnDicionary;
    }

    private String invertDomain(String domain) {
        LOG.info("Invertendo a parte principal da URL");
        StringBuilder builder = new StringBuilder(domain);
        builder.reverse();
        String result = builder.toString();
        LOG.info("Temos {} como a parte principal da URL",result);
        return result;
    }

    private String generatedCustomAlias(String customAlias) throws IOException {

        if(customAlias==null || customAlias.equals("undefined")){
            secondPart = secondPart.replace(secondPart,invertDomain(secondPart));
            InputStream inputStream = IOUtils.toInputStream(customAlias, "UTF-8");
            String aliasMD5 = DigestUtils.md5Digest(inputStream).toString();
            customAlias = customAlias.substring(4);
            secondPart = secondPart.replace(secondPart, aliasMD5.substring(3));
            return secondPart;
        }

        return customAlias;
    }

    private String splitURL(String url) {
        LOG.info("Separando a URL em partes");

        String[] partsOfURL = StringUtils.split(url,".");
        Integer size = partsOfURL.length;

        for (int i = 0; i < size; i++) {
            firstPart = partsOfURL[0];
            secondPart = partsOfURL[1];
            thirdPart = partsOfURL[2];
            lastPart = partsOfURL[size-1];
        }
        LOG.info("Fim da separacao da URL {}", url);

        String country = lastPart.substring(0,2);
        String keyDicionary =  dot.concat(thirdPart).concat(dot).concat(country);

        return keyDicionary;
    }

    private Object getInformationAboutURL(String url){
        Object informations = shortenURLRepository.getInformations(url);
        return informations;
    }

}
