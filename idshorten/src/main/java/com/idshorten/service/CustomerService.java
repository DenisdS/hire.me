package com.idshorten.service;

import com.idshorten.controller.request.CustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;


@Service
public class CustomerService {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    DicionaryService dicionaryService;

    @Value("${idshort.protocol}")
    private String protocol;

    @Value("${idshort.separator}")
    private String separator;

    private String urlComplete;

    public CustomerRequest requestIsValid(String url, String customAlias) throws MalformedURLException {
        LOG.info("Validando o request");
        CustomerRequest customerRequest = new CustomerRequest();

        validURL(url);

        customerRequest.setUrl(urlComplete);
        customerRequest.setCustomAlias(customAlias);

        LOG.info("Fim da validacao do request");
        return customerRequest;
    }

    private void validURL(String url) throws MalformedURLException {
        LOG.info("Validando a URL enviada pelo usuario");

        try {
            URL urlValidator = new URL(url);
            urlValidator.getProtocol();
            LOG.info("A URL possui o protocolo{}", urlValidator.getProtocol());
            urlComplete = url;

        } catch (MalformedURLException e) {
            urlComplete = protocol.concat(url);
            LOG.info("Ajustando a URL no padrao HTTP/HTTPS {}", urlComplete);

        }

        LOG.info("Fim da validacao da URL {}", urlComplete);
    }

}



