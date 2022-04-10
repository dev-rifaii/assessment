package personal.assessment.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private final Customer validCustomer =
            Customer.builder()
                    .name("Test")
                    .email("test@test.co")
                    .phoneNumber("15442255")
                    .build();

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void should_create_customer_and_return_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .content(objectMapper.writeValueAsString(validCustomer)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Customer created"));
    }


    @Test
    void should_return_error_message_given_invalid_customer() throws Exception {
        Customer customer = Customer.builder()
                .name("")
                .email("test@test.co")
                .phoneNumber("12345678")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .content(objectMapper.writeValueAsString(customer)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"Errors: \":[\"Name can't be blank\"]}"));
    }
}