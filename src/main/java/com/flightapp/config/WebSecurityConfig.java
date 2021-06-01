package com.flightapp.config;

import com.flightapp.filter.JWTAuthenticationFilter;
import com.flightapp.filter.JWTAuthorizationFilter;
import com.flightapp.service.UserDetailsServiceImpl;
// import com.flightapp.handler.AuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.flightapp.filter.SecurityConstants.COOKIE_NAME;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  
  @Qualifier("userDetailsServiceImpl")
  @Autowired
  private UserDetailsService userDetailsService;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  // private AuthenticationFailureHandler authenticationFailureHandler;

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
      auth.authenticationProvider(authenticationProvider());
  }

  @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    String[] staticResources  =  {
      "/css/**",
      "/images/**",
      "/js/**"
  };

    http.cors().and().csrf().disable()
      .authorizeRequests()
      .antMatchers("/", "/error", "/loginFailed", "/login*", "/register", "/guestcancel/**", "/findGuestBookings", "/search", "/bookFlight", "/book/**").permitAll()
      // .antMatchers("/user").access("hasAnyAuthority('ADMIN','EXECUTIVEUSER')")
      .antMatchers(staticResources).permitAll()
      // .antMatchers("/executiveUserHome").access("hasAuthority('ADMIN')")
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .loginPage("/login")
      .loginProcessingUrl("/login")
      // .defaultSuccessUrl("/executiveUserHome", true)
      // .successHandler(authenticationSuccessHandler)
      //.failureUrl("/loginFailed")
      //.failureHandler(authenticationFailureHandler)
      .permitAll()
      .and()
      .logout()
      .logoutUrl("/logout")
      .deleteCookies(COOKIE_NAME)
      .permitAll()
      .and()
      .addFilter(new JWTAuthenticationFilter(authenticationManager()))
      .addFilter(new JWTAuthorizationFilter(authenticationManager()))
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      configuration.setAllowedOrigins(Arrays.asList("localhost"));
      source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
      return source;
  }

}
