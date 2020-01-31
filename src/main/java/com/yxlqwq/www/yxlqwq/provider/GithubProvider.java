package com.yxlqwq.www.yxlqwq.provider;

import com.alibaba.fastjson.JSON;
import com.yxlqwq.www.yxlqwq.dto.AccessTokenDto;
import com.yxlqwq.www.yxlqwq.dto.GithubUserDto;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            Pattern p = Pattern.compile("access_token=([0-9a-zA-Z]+)&");
            Matcher m = p.matcher(string);
            if(m.find()){
                return m.group(1);
            }
//            AccessTokenCallbackDto accessTokenCallbackDto= JSON.parseObject(string, AccessTokenCallbackDto.class);
//            System.out.println(accessTokenCallbackDto.getAccess_token());
        } catch (IOException e) {
        }
        return null;
    }
    public GithubUserDto getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUserDto githubUserDto = JSON.parseObject(string, GithubUserDto.class);
            return githubUserDto;
        } catch (IOException e) {
        }
        return null;
    }
}
