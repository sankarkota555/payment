package com.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.payment.security.PaymentHeaderWriter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private PaymentHeaderWriter paymentHeaderWriter;

  /*
   * @Autowired private CustomLogoutHandler customLogoutHandler;
   */

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/pymt/admin").hasRole("ADMIN").antMatchers("/pymt/**")
        .hasAnyRole("ADMIN", "USER")// configuring url for security
        .and().formLogin();// for login page

    // allow basic authentication
    http.httpBasic();

    // enable session management
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .enableSessionUrlRewriting(false).maximumSessions(1).maxSessionsPreventsLogin(false)
        .expiredUrl("/sessionExpired");

    // configure logout
    http.logout().clearAuthentication(true).deleteCookies("XSRF-TOKEN", "JSESSIONID")
        .invalidateHttpSession(true).logoutSuccessUrl("/login");

   /* RequestMatcher matcher = new AntPathRequestMatcher("/pymt/javax.faces.resource/**");
    DelegatingRequestMatcherHeaderWriter headerWriter = new DelegatingRequestMatcherHeaderWriter(
        matcher, paymentHeaderWriter);

    // disable cache-control header by spring, set cache-control in csrf filter
    http.headers().addHeaderWriter(headerWriter);*/

    // http.authorizeRequests().antMatchers("/pymt/javax.faces.resource/**").

  }


  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService); // for authenticating using username and password
                                                 // from database.
  }

}
