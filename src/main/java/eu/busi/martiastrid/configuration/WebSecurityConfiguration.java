//package eu.busi.martiastrid.configuration;
//
//import eu.busi.martiastrid.eventHandler.LoginSuccessHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Autowired
//    private LoginSuccessHandler loginSuccessHandler;
//
//    @Autowired
//    @Qualifier("userDetailServiceImplementation")
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/checkout/**").hasRole("USER")
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .and()
//                .formLogin()
//                .loginPage("/showMyLoginPage") // to put in a GetMapping that will redirect to the login page
//                .loginProcessingUrl("/authenticateTheUser")// no
//                .successHandler(loginSuccessHandler)// controller needed :) used in the login form
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/access-denied") // needs @GetMapping
//                .and()
//                .rememberMe().key("iDontKnowWhetToPutInHere");
//    }
//}