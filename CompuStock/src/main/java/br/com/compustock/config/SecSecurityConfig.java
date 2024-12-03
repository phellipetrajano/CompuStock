package br.com.compustock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {
    
    @Autowired    
    private UserDetailServiceImpl userDetailServiceImpl;
    
    @Bean 
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
        http.authorizeHttpRequests(auth -> auth
                // Permitir acesso a todos
                .requestMatchers("/", "/login", "/static/imagens", "/dashboard").permitAll()
               
                // Permissões do administrador (tem acesso total)
                .requestMatchers("/dashboard/funcionarios/**", "/dashboard/fornecedores/**", "/dashboard/produtos/**").hasAuthority("administrador")

                // Permissões do funcionário (acesso a produtos e fornecedores apenas)
                .requestMatchers("/dashboard/produtos/**", "/dashboard/fornecedores/**", "/dashboard/fornecedores/novo", "/dashboard/fornecedores/editar/**", "/dashboard/fornecedores/deletar/**")
                .hasAnyAuthority("funcionario", "administrador")

                // Bloquear acesso de funcionários a páginas de administração
                .requestMatchers("/dashboard/funcionarios/**").hasAuthority("administrador")

                // Qualquer outra requisição deve ser autenticada
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                    .defaultSuccessUrl("/dashboard", true)
                    .loginPage("/login")
                    .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe.key("AbcdEfghIjkl..."))
            .logout(logout -> logout.logoutUrl("/signout").permitAll());

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        System.out.println(b.encode("123456"));
        // Criptografa a senha para salvar no banco de dados
        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }
}
