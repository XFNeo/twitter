package ru.xfneo.twitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.xfneo.twitter.controllers.MessageController;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("neo")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-message-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-message-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MessageController messageController;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='navbarSupportedContent']/div").string("Neo"));
    }

    @Test
    public void messageList() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='messagesContainer']/div").nodeCount(4));

    }

    @Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/main").param("filter","tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='messagesContainer']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='messagesContainer']/div[@data-id='1']").exists())
                .andExpect(xpath("//div[@id='messagesContainer']/div[@data-id='2']").exists());
    }

    @Test
    public void addMessageTest() throws Exception{
        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file("file", "qwerty".getBytes())
                .param("text", "fifth")
                .param("tag", "tag5")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='messagesContainer']/div").nodeCount(5))
                .andExpect(xpath("//div[@id='messagesContainer']/div[@data-id='10']").exists())
                .andExpect(xpath("//div[@id='messagesContainer']/div[@data-id='10']/div/span").string("fifth"))
                .andExpect(xpath("//div[@id='messagesContainer']/div[@data-id='10']/div/i").string("#tag5"));
    }


}
