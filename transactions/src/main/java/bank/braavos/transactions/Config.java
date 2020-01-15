package bank.braavos.transactions;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URL;

/*****************/
@Configuration
@Component
@ComponentScan(basePackageClasses = TransactionClient.class)
public class Config {

    static private URL    url;
    static private String api_key;
    static private String partner_id;
    static private String authorization;

    final static String propFile = "application.properties";


    public static String getAuthorization() {
        return authorization;
    }

    public static void setAuthorization(String authorization) {
        Config.authorization = authorization;
    }

    public static URL getUrl() {
        return url;
    }

    public static void setUrl(URL url) {
        Config.url = url;
    }

    public static String getApi_key() {
        return api_key;
    }

    public static void setApi_key(String api_key) {
        Config.api_key = api_key;
    }

    public static String getPartner_id() {
        return partner_id;
    }

    public static void setPartner_id(String partner_id) {
        Config.partner_id = partner_id;
    }


    @Bean
    public OkHttpClient getHttpClient() {
        return new OkHttpClient();
    }

}
