package top.xing.duqingplus.until;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class RequestUtil {
    public static String get(String url) {
        final String[] result = {""};
       OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
      Call call = client.newCall(request);
      call.enqueue(new Callback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {
              throw new IllegalArgumentException(e.getMessage());
          }

          @Override
          public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
              result[0] = Objects.requireNonNull(response.body()).string();
          }
      });
      return result[0];
    }

    public static String getCover(String qq) {
        String result = get("https://yuxinghe.top/api/qq.php?qq="+qq);
        try {
            return new ObjectMapper().readTree(result).get("cover").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
