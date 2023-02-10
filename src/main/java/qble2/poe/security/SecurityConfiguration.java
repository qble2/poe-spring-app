package qble2.poe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Profile("!test")
public class SecurityConfiguration {

  @Autowired
  private CustomAuthenticationProvider customAuthProvider;

  @Bean
  @Order(1)
  SecurityFilterChain filterChainRestApi(HttpSecurity http) throws Exception {
    http

        .csrf().disable()

        .antMatcher("/api/**").authorizeRequests()

        .antMatchers("/api/**").permitAll();

    return http.build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain filterChainWebApp(HttpSecurity http) throws Exception {
    http

        // disabling csrf allows post method without _csfr
        // .cors().and().csrf().disable()

        // alternative to disabling csrf
        // thymeleaf already adds the csrf token to all post requests
        // for htmx requests, it can be:
        // #1 added directly to the url: ?${_csrf.parameterName}=${_csrf.token}
        // #2 added to every AJAX requests with a JavaScript eventlistener on 'htmx:configRequest'

        .antMatcher("/**")

        .authorizeRequests()

        // allow static resources
        .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/image/**", "/img/**",
            "/webjars/**")
        .permitAll()

        .anyRequest().authenticated()

        .and().formLogin().usernameParameter("accountName").passwordParameter("poeSessionId")
        .loginPage("/login").loginProcessingUrl("/login").failureUrl("/login?error")
        // permit requests to the login page
        .permitAll()

        .defaultSuccessUrl("/")

        .and().logout().logoutUrl("/logout").permitAll().logoutSuccessUrl("/login?logout")
        .deleteCookies("JSESSIONID").invalidateHttpSession(true)

    // .and().exceptionHandling().accessDeniedPage("/access-denied")

    ;

    // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(1)
    // .expiredUrl("/login?expired")
    // ? redirecting twice /origin -> /login?expired -> /login
    // .and().invalidSessionUrl("/login?expired")
    // ;

    return http.build();
  }

  @Bean
  AuthenticationManager authManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(customAuthProvider);
    // authenticationManagerBuilder.eraseCredentials(false);

    return authenticationManagerBuilder.build();
  }

}
