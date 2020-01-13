package bank.braavos.transactions;

import com.squareup.okhttp.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;


@Component
public class TransactionClient {

    private URL baseURL_i;
    private URL basedAdminURL_i;
    private OkHttpClient httpClient;
    private static Properties props;


    @Bean
    public static Properties getProps() {
        return props;
    }

    public static void setProps(Properties props) {
        TransactionClient.props = props;
    }

    // assumes the current class is called MyLogger
    private final static Logger LOGGER = Logger.getLogger(TransactionClient.class.getName());

    public TransactionClient() throws IOException {
        this.httpClient = new OkHttpClient();
        props = new Properties();
        InputStream inputstream = getClass().getClassLoader().getResourceAsStream(Config.propFile);
        if(inputstream !=null ){
            props.load(inputstream);
            String api_key = props.getProperty("openbanking.api_key");
            LOGGER.info("api_ki:"+api_key);
        } else {
            throw new FileNotFoundException("prop file'"+ Config.propFile + "' not found in classpath" );
        }
    }

    /**
     *  Get HTTP client
     * @return An instance of OkHttpClient
     */
      public OkHttpClient getHttpClient() {
          return httpClient;
      }

      public void setHttpClient(OkHttpClient client) {
        httpClient = client;
    }
}
