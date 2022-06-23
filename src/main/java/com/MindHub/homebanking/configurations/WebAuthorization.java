package com.MindHub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http.authorizeRequests()
                        .antMatchers("/rest/**","/h2-console").hasAuthority("ADMIN")
                        .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                        .antMatchers("/web/index.html", "/web/styles/style.css", "web/assets/**", "/web/scripts/index.js").permitAll()
                        .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAuthority("CLIENT")
                        .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
                        .antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("CLIENT")
                        .antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("CLIENT")
                        .antMatchers(HttpMethod.GET, "/api/loans").permitAll()
                        .antMatchers("/api/clients/current").hasAnyAuthority("CLIENT", "ADMIN")
                        .antMatchers("/web/accounts.html", "/web/account.html", "/web/cards.html", "/web/create_card.html",
                                "/web/loan_application.html", "/web/transfers.html").hasAnyAuthority("CLIENT", "ADMIN");

        http.formLogin()
                .usernameParameter("mail")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");


        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint(((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)));
        http.formLogin().successHandler(((request, response, authentication) -> clearAuthenticationAttributes(request)));
        http.formLogin().failureHandler(((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null){
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
