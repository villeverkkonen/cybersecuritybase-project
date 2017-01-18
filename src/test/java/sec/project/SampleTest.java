package sec.project;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sec.project.repository.MonkepoHunterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    private MonkepoHunterRepository monkepoHunterRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void registerationAddsDataToDatabase() throws Throwable {
        mockMvc.perform(post("/monkepo").param("username", "ash").param("password", "qwerty1234")).andReturn();
        assertEquals(1L, monkepoHunterRepository.findAll().stream().filter(s -> s.getUsername().equals("ash") && s.getAuthority().equals("USER")).count());
    }
}
