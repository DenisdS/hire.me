package com.idshorten.controller;

import com.idshorten.controller.request.CustomerRequest;
import com.idshorten.service.CustomerService;
import com.idshorten.service.ShortenURLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Mock
    CustomerRequest customerRequest;

    @Mock
    ShortenURLService shortenURLService;

    @Mock
    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = standaloneSetup(customerController)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void testPathInvalid() throws Exception{
        MockHttpServletRequestBuilder createMessage = put("/");
        MockHttpServletResponse response = this.mockMvc.perform(createMessage)
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse();

        assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value());
    }



    @Test
    public void created() throws Exception {
        String alias = "bimobi";

        MockHttpServletRequestBuilder request = get("/created?url=http://www.bemobi.com.br&customAlias="+alias);

        when(customerService.requestIsValid(Mockito.anyString(),Mockito.anyString())).thenReturn(customerRequest);
        when(shortenURLService.verifyAlias(Mockito.anyString())).thenReturn(alias);

        MockHttpServletResponse response = this.mockMvc.perform(request)
                .andReturn()
                .getResponse();

        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    public void addExtensionFailure() throws Exception {
        MockHttpServletRequestBuilder createMessage = post("/addExtension");
        MockHttpServletResponse response = this.mockMvc.perform(createMessage)
                .andReturn()
                .getResponse();

        assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void addExtension() throws Exception {
        MockHttpServletRequestBuilder createMessage = post("/addExtension?extension=.com.br");
        MockHttpServletResponse response = this.mockMvc.perform(createMessage)
                .andReturn()
                .getResponse();

        assertEquals(response.getStatus(), HttpStatus.ACCEPTED.value());
    }

}
