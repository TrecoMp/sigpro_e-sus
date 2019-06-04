package com.sistema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sistema.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImplementacao userDetailsImplementacao;

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/").hasAnyRole("ADMIN","USER")
		.antMatchers("/inicio").hasAnyRole("ADMIN","USER")
		.antMatchers("/pessoa/formpessoa").hasRole("ADMIN")
		.antMatchers("/pessoa/adicionar").hasRole("ADMIN")
		.antMatchers("/pessoa/attpessoas").hasRole("ADMIN")
		.antMatchers("/pessoa/autoatt").hasAnyRole("ADMIN","USER")
		.antMatchers("/pessoa/att/*").hasRole("ADMIN")
		.antMatchers("/pessoa/rempessoas").hasRole("ADMIN")
		.antMatchers("/pessoa/listar").hasAnyRole("ADMIN", "USER")
		.antMatchers("/pessoa/excluir/*").hasRole("ADMIN")
		.antMatchers("/pessoa/*").hasAnyRole("ADMIN", "USER")
		.antMatchers("/profissional/formprofissional").hasRole("ADMIN")
		.antMatchers("/profissional/adicionar").hasRole("ADMIN")
		.antMatchers("/profissional/excluir/*").hasRole("ADMIN")
		.antMatchers("/profissional/editar/*").hasRole("ADMIN")
		.antMatchers("/profissional/listarem").hasRole("ADMIN")
		.antMatchers("/profissional/listaredt").hasRole("ADMIN")
		.antMatchers("/profissional/listar").hasAnyRole("ADMIN","USER")
		.antMatchers("/profissional/fichas/*").hasAnyRole("ADMIN","USER")
		.antMatchers("/registro/*").hasAnyRole("ADMIN","USER")
		.antMatchers("/registro/remover").hasRole("ADMIN")
		.antMatchers("/registro/excluir/*").hasAnyRole("ADMIN", "USER")
		.antMatchers("/registro/formrelatorio").hasRole("ADMIN")
		.antMatchers("/registro/gerarelarotio").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/pessoa/logar").defaultSuccessUrl("/inicio",true)
		.failureUrl("/pessoa/logar").permitAll()
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/pessoa/logar").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsImplementacao).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}
}


