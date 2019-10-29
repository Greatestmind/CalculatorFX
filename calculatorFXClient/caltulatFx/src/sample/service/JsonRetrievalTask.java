package sample.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import sample.model.Operacion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsonRetrievalTask {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private String JSON_URL = "http://localhost:8080/operacion";

    private Task<List<Operacion>> fetchList = new Task() {

        @Override
        protected List<Operacion> call() throws Exception {
            List<Operacion> listOp = null;
            try {
                listOp = new Gson().fromJson(readUrl(JSON_URL), new TypeToken<List<Operacion>>() {
                }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.removeAll(list);
            for (int i = 0; i <= listOp.size(); i++) {
                list.add(listOp.get(i).getText());
            }
            return listOp;
        }
    };

    public ObservableList getList() {
        return list;
    }

    ObservableList list = FXCollections.observableArrayList(executorService.submit(fetchList));

    public void postJson(String text) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("text", text);
        String postUrl = JSON_URL;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(postUrl);
        StringEntity postingString = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        try {
            HttpResponse response = httpClient.execute(post);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
