package br.com.vendas.udemy.config;

import br.com.vendas.udemy.security.jwt.JwtAuthFilter;
import br.com.vendas.udemy.security.jwt.JwtService;
import br.com.vendas.udemy.service.impl.UsuarioServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceimpl usuarioService;

    @Autowired
    private JwtService jwtService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //ele sempre vai gerar um hash diferente
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    //Traz os objetos que vão fazer autenticação dos usuarios. Adicionando os mesmos, ao contexto spring security
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    //este aqui trabalha com a parte de autorização. Ele pega o Usuaro que será autenticado no metodo de cima, e verifica se ele tem autorização pra esta pagina
    //definimos quem tem acesso ao que do sistema
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //csrf uma configuração que permite segurança entre web e backand
                .authorizeRequests() //apartir daqui iremos definir quem acessa o que
                    .antMatchers("/api/clientes/**")
                .hasAnyRole("USER", "ADMIN")
                    .antMatchers("api/pedidos/**")
                .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/produtos/**")
                .hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/usuario/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
