package com.fine.hug_backend.config.auth;

import com.fine.hug_backend.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity /* Spring security 설정 활성화 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() /* URL별 권한 관리를 설정하는 옵션의 시작점 */
                .antMatchers("/", "/css/**", "/images/**", "/js/**").permitAll() /* 권한 관리 대상을 지정하는 옵션 (URL, HTTP 메소드별로 관리 가능 */
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated() /* 설정한 값 이외의 URL, authenticated: 인증된 사용자만 허용*/
                .and()
                .logout() /* 로그아웃 설정 진입점 */
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint() /* OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당 */
                .userService(customOAuth2UserService); /* 소셜 로그인 성공 후 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시 */
    }
}
