package pe.colegio.security;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import pe.colegio.service.UsuarioServIMPL;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UsuarioServIMPL service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//obtenemos el token de la solicitud HTTP
		String token = obtenerJWTdeLaSolicitud(request);
		
		//validamos el token
		if(StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
			//obtenemos el username del token
			String username = jwtTokenProvider.obtenerUsernameDelJWT(token);
			
			//cargamos el usuario asociado al token
			UserDetails userDetails = service.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			//establecemos la seguridad
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		filterChain.doFilter(request, response);
	}

	//Bearer token de acceso
	private String obtenerJWTdeLaSolicitud(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}


}
