package com.retro.food.web;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.SessionScope;

import com.retro.food.TestBaseImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:war/WEB-INF/dispatcher-servlet.xml")
public class VendorControllerTest extends TestBaseImpl {
    
    @Autowired
    private WebApplicationContext wac;
    @Autowired MockServletContext servletContext; // cached
    @Autowired MockHttpSession session;
    @Autowired MockHttpServletRequest request;
    @Autowired MockHttpServletResponse response;
    @Autowired ServletWebRequest webRequest;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new VendorController()).build();
    }

    @Test
    public final void testView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testCreateVendor() throws Exception {
        
        // get the vendor controller
//        VendorController controller = getCtx().getBean("vendorController",VendorController.class);
//        // call the method
//        controller.createVendor(model);
        mockMvc.perform(MockMvcRequestBuilders.get("/vendor").accept(MediaType.APPLICATION_JSON));
//        mockMvc.perform(MockMvcRequestBuilders.post("/hotels/{id}", 42).accept(MediaType.APPLICATION_JSON));
        // mockMvc.perform(post("/hotels/{id}", 42).accept(MediaType.APPLICATION_JSON));
        // mockMvc.perform(get("/hotels").param("foo", "bar"));
        
        
    }

    @Test
    public final void testSaveVendorBindingResultModel() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testCreateVendorItem() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void testSaveLongVendorItemBindingResultModel() {
        fail("Not yet implemented"); // TODO
    }

}
