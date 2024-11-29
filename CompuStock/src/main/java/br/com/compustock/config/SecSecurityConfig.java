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
	 
	    http.authorizeHttpRequests(
	            auth -> auth.requestMatchers("/","/login","/static/imagens","/dashboard").permitAll()  // Permitir acesso a todos
	            
	            // Permitir que o administrador tenha acesso total
	            .requestMatchers("/dashboard/funcionarios/**").hasAuthority("administrador") // Acesso total para administradores
	            .requestMatchers("/dashboard/fornecedores/**","/dashboard/fornecedores/novo", "/dashboard/fornecedores/editar/**", "/dashboard/fornecedores/deletar/**").hasAuthority("administrador") // Acesso total para administradores
	            .requestMatchers("/dashboard/produtos/**").hasAuthority("administrador") // Acesso total para administradores
	            
	            // Restringir acesso do funcionário
	            .requestMatchers("/dashboard/funcionarios/novo", "/dashboard/funcionarios/editar/**").hasAuthority("administrador") // Apenas administradores podem cadastrar ou editar funcionários
	            
	            .anyRequest().authenticated() // Qualquer outra requisição deve ser autenticada
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
		// Serve de exemplo para gerar uma senha criptografada
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		System.out.println(b.encode("123456"));
		// Criptografa a senha para salvar no banco de dados
		auth.userDetailsService(userDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	}
}