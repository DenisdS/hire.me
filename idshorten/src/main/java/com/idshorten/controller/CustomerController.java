package com.idshorten.controller;

import com.idshorten.controller.request.CustomerRequest;
import com.idshorten.controller.response.CustomerResponse;
import com.idshorten.exception.URLNotExists;
import com.idshorten.model.ShortenURL;
import com.idshorten.service.CustomerService;
import com.idshorten.service.ShortenURLService;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@EnableAutoConfiguration
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    CustomerRequest customerRequest;

    @Autowired
    ShortenURLService shortenURLService;

    ShortenURL shortenURLResult;

    @RequestMapping(value ="created", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object created(@RequestParam(required = true) String url, @RequestParam(required = false) String customAlias) throws IOException, ConfigurationException, URLNotExists {
        LOG.info("Inicio da aplicacao Shorten URL {} ", url);
        customerRequest = customerService.requestIsValid(url, customAlias);
        LOG.info("cusomer {} ",customerRequest.getCustomAlias());

        Boolean result = shortenURLService.verifyURL(customerRequest.getUrl());
        if(result){
            Object informationURL = shortenURLService.getInformations(customerRequest.getUrl());
            return informationURL;
        }else{
            Object informationAlias = shortenURLService.verifyAlias(customerRequest.getCustomAlias());
            if(informationAlias.toString().startsWith("ERR")){
                return informationAlias;
            }
            shortenURLResult = shortenURLService.create(customerRequest.getUrl(), informationAlias.toString());
        }
        LOG.info("Fim da aplicacao Shorten");
        return shortenURLResult;
    }

    @RequestMapping(value ="searchURL", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object searchURL(@RequestParam(required = true) String url) throws IOException, ConfigurationException, URLNotExists {
        LOG.info("Inicio da aplicacao Shorten");

        Object urlComplete = shortenURLService.existsURLAssociate(url);
        LOG.info("Temos como resultado dessa busca {} ", urlComplete.toString());

        LOG.info("Fim da aplicacao Shorten");
        return urlComplete;
    }

    @RequestMapping(value ="addExtension", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> addExtension (@RequestParam(required = true) String extension) throws IOException, ConfigurationException {
        LOG.info("Inicio da servico que adiciona novas extensoes");

        String valueOfExtension = shortenURLService.addingNewExtension(extension);

        CustomerResponse renewResponse = new CustomerResponse();
        renewResponse.setResponseCode(HttpStatus.ACCEPTED.value());
        renewResponse.setResponseDescription("The new extension " +extension+" = "+valueOfExtension+" created with success");

        LOG.info("Fim da aplicacao");
        return new ResponseEntity<CustomerResponse>(renewResponse, HttpStatus.valueOf(renewResponse.getResponseCode()));
    }


}