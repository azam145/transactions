package bank.braavos.transactions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;


//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class TransactionsApplicationTests {
    // assumes the current class is called MyLogger
    private final static Logger LOGGER = Logger.getLogger(TransactionsApplicationTests.class.getName());

    @Autowired
    private static Properties props;
    private MockMvc mockMvc;

    @Mock
    private TransactionsApi api;

    @InjectMocks
    private TransactionClient mockClient = new TransactionClient();

    TransactionsApplicationTests() throws IOException {
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(api).isNotNull();
    }

    @Test
    public void findAllTransactionEntries() throws IOException {
        List<Transactions> lt = new ArrayList<Transactions>();
        Mockito
                .when(api.getAllTransactions())
          .thenReturn(lt);
        assertEquals(lt.size(),0);
    }

    @Test
    public void findAll_TransactionsFound_ShouldReturnFoundTransactionEntries() throws Exception {

        Transactions first = new Transactions()
                .setDescription("Test description")
                .setAccountId("fakeAcct11")
                .setBankId(8);

        Transactions second = new Transactions()
                .setDescription("Test description")
                .setAccountId("fakeAcct1")
                .setBankId(7);

        TransactionsApi apiMock = mock(TransactionsApi.class);


        when(apiMock.getAllTransactions()).thenReturn(Arrays.asList(first, second));

        List<Transactions> lt = Arrays.asList(first, second);

        assertEquals(apiMock.getAllTransactions(), lt);
        assertEquals(lt.size(),2);
        assertEquals(lt.get(0).getAccountId(), "fakeAcct11");
        assertEquals(lt.get(1).getAccountId(), "fakeAcct1");

    }

}
