package bank.braavos.transactions;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


import static java.util.stream.Collectors.toCollection;

@RestController
@RequestMapping("/api/v1")
@Component
public class TransactionsApi {

   private static final String DEFAULT_URL = "https://sandbox.askfractal.com/banking/11/accounts/fakeaccount1/transactions";
   private static final String DEFAULT_API_KEY = "nQn0R33ZDuaJL5FZjaPTN8o89NeCOi71a3KkXHWp";
   private static  String DEFAULT_PARTNER_ID = "3ee57cb4b842476ca4a7acee6d1bbe0b";
   private static  String DEFAULT_AUTHORISATION  = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzYW5kYm94VXNlciIsIm5hbWUiOiJGcmFjQm94IiwiaWF0IjoxNTE2MjM5MDIyLCJleHBpcmVzIjoxODAwfQ.A-Xk_RwJu3BZQ7gsUgq7nK4UPJpqIKJtxbBxkz2eJU4";

   @Autowired
   @Inject
   private TransactionClient client;
   private static ApplicationContext context;

   @GetMapping("/alltransactions")
   public List<Transactions> getAllTransactions() throws IOException {

      MediaType mediaType = MediaType.parse("application/json");
      Request request = buildReqHeader();

      Response response = client.getHttpClient().newCall(request).execute();
      String json = convertStreamToString(response.body().byteStream());
      String[] str = json.split(",");
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < str.length; i++) {
         String nextString = str[i];
         if(nextString.contains("[")) {
            int begin = nextString.indexOf("[");
            String beginArray = nextString.substring(begin,nextString.length());
            sb.append(beginArray+",");
            continue;
         }
         if(nextString.contains("]")) {
            int end = nextString.indexOf("]");
            String endArray = nextString.substring(0,end+1);
             sb.append(endArray);
            break;
         }
         sb.append(nextString +",");
      }
      String toBePassed = sb.toString();
      Gson gson = new Gson();
      Transactions[] lt = gson.fromJson(toBePassed, Transactions[].class);
      ArrayList<Transactions> result = new ArrayList(Arrays.asList(lt));
      return result;
   }

   @GetMapping("/transactions")
   public List<Transactions> getTransactions(@RequestParam String categoryId1, @RequestParam String categoryId2) throws IOException {

        List<Transactions> result1 = getAllTransactions();
        List<Transactions> result = result1.stream().
        filter(e->e.getCategory().equals(categoryId1)).
        collect(toCollection(LinkedList::new));
        List<Transactions> result2 = result1.stream().
                filter(e->e.getCategory().equals(categoryId2)).
                collect(toCollection(LinkedList::new));

       List<Transactions> newList = new ArrayList<Transactions>(result);
       newList.addAll(result2);
       return newList;
    }

    /**
     *
     * @return
     */
    @PutMapping("/updateTransactionId")
    // @PutMapping(path ="/updateTransactionId", consumes ="application/json;charset=UTF-8", produces = "application/json")
    public List<Transactions> updateTransactionId(@RequestParam String transId, @RequestParam String categoryId, @RequestParam String compId) throws JSONException, IOException {

        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");
        JSONObject jBody = new JSONObject();
        jBody.put("transactionId",transId);
        jBody.put("categoryId", categoryId);
        jBody.put("companyId", compId);

        com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(mediaType, jBody.toString());
        Request request = buildPut(body);

        Response response = client.getHttpClient().newCall(request).execute();

        return null;

    }

    Request buildReqHeader() {
        MediaType mediaType = MediaType.parse("application/json");
        com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(mediaType, "");
        String api_key = DEFAULT_API_KEY;
        String partner_id = DEFAULT_PARTNER_ID;
        String authorisation = DEFAULT_AUTHORISATION;
        String urlString = DEFAULT_URL;

        if(client.getProps()!=null) {
            api_key = client.getProps().getProperty("openbanking.api_key");
            partner_id = client.getProps().getProperty("openbanking.partner_id");
            authorisation = client.getProps().getProperty("openbanking.authorization");
            urlString = client.getProps().getProperty("openbanking.url");
        }
        Request request = new Request.Builder()
                //.url("https://sandbox.askfractal.com/banking/11/accounts/fakeaccount1/transactions?companyId=2&pg=1")
                .url(urlString)
                .get()
                .addHeader("X-Api-Key", api_key)
                .addHeader("X-Partner-Id", partner_id)
                .addHeader("Authorization", authorisation)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }

    Request buildPut(com.squareup.okhttp.RequestBody body) {
        MediaType mediaType = MediaType.parse("application/json");
        String api_key = client.getProps().getProperty("openbanking.api_key");
        String partner_id = client.getProps().getProperty("openbanking.partner_id");
        String authorisation = client.getProps().getProperty("openbanking.authorization");
        String urlString = client.getProps().getProperty("openbanking.url");

        Request request = new Request.Builder()
                //.url("https://sandbox.askfractal.com/banking/11/accounts/fakeaccount1/transactions?companyId=2&pg=1")
                .url(urlString)
                .put(body)
                .addHeader("X-Api-Key", api_key)
                .addHeader("X-Partner-Id", partner_id)
                .addHeader("Authorization", authorisation)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }


    /**
     *
     * @param is InputStream
     * @return String
     */
   private static String convertStreamToString(InputStream is) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();

      String line = null;
      try {
         while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            is.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return sb.toString();
   }

}
