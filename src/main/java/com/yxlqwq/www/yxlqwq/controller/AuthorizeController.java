package com.yxlqwq.www.yxlqwq.controller;

import com.yxlqwq.www.yxlqwq.dto.AccessTokenDto;
import com.yxlqwq.www.yxlqwq.dto.GithubUserDto;
import com.yxlqwq.www.yxlqwq.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.url}")
    private String clientUrl;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state") String state,
                           Model model){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setRedirect_uri(clientUrl);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUserDto user=githubProvider.getUser(accessToken);
        model.addAttribute("name",user.getName());
        return "index";
    }
}
