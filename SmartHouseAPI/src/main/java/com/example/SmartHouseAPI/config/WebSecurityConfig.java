package com.example.SmartHouseAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.SmartHouseAPI.auth.RestAuthenticationEntryPoint;
import com.example.SmartHouseAPI.auth.TokenAuthenticationFilter;
import com.example.SmartHouseAPI.service.UsersService;
import com.example.SmartHouseAPI.util.TokenUtils;

@SuppressWarnings("deprecation")
@Configuration
// Anotacije za autorizacione provere
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	// Authentication manager iz Springa koji vrsi autentifikaciju za nas.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	// Definisemo nacin utvrdjivanja korisnika pri autentifikaciji
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			// Definisemo uputstva AuthenticationManager-u:
		
			// 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
			// prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
			.userDetailsService(userService) 
			
			// 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu 
			// da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
			.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
		// komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
		// ovo znaci da server ne pamti nikakvo stanje, tokeni se ne cuvaju na serveru 
		// ovo nije slucaj kao sa sesijama koje se cuvaju na serverskoj strani - STATEFULL aplikacija
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

		// sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

		// svim korisnicima dopusti da pristupe sledecim putanjama:
		.authorizeRequests().antMatchers("/auth/**").permitAll()	
							.antMatchers("/reg/**").permitAll()		
							.antMatchers("/realestates/**").permitAll()
							.antMatchers("/certificate/**").permitAll()		
							.antMatchers("/csr/**").permitAll()
							.antMatchers("/users/**").permitAll()
							.antMatchers("/logs/**").permitAll()
							.antMatchers("/devices/**").permitAll()
		// ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
		// koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
		// samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin: 
		// .antMatchers("/admin").hasRole("ADMIN") ili .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
						       
		// za svaki drugi zahtev korisnik mora biti autentifikovan
		.anyRequest().authenticated().and()
		
		// za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
		.cors().and()
		// umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
		.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userService), BasicAuthenticationFilter.class);
	
	// zbog jednostavnosti primera ne koristimo Anti-CSRF token (https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
	http.csrf().disable();

	// Aktiviramo ugrađenu zaštitu od XSS napada da browser ne bi izvršavao maliciozne skripte
	http
		.headers()
		.xssProtection()
		.and()
		.contentSecurityPolicy("script-src 'self'");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		
		// Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
//		 web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
		 
		// Ovim smo dozvolili pristup statickim resursima aplikacije
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js");
	}
}
