package com.sergio.curso.sprinboot.app.springboot_crud_api_restfull.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//Esta clase es un filtro de autenticación que esta basado en JWT. Su proposito es interceptar cada solicitud HTTP y verificar si el usuario tiene un token JWT valido para acceder a los recursos protegidos.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUsersDetailsService customUsersDetailsService;
    private final JwtGenerador jwtGenerador;

    // 🔥 Constructor con inyección de dependencias
    public JwtAuthenticationFilter(CustomUsersDetailsService customUsersDetailsService, JwtGenerador jwtGenerador) {
        this.customUsersDetailsService = customUsersDetailsService;
        this.jwtGenerador = jwtGenerador;
    }



    //    Extrae el token JWT de la cabecera Authorization de la solicitud HTTP
    private String obtenerTokenDeSolicitud(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // Obtenemos el token que viene de la solicitud HTTP del cliente
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //    Intercepta cada solicitud HTTP antes de que llegue al controlador
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String tokenExtraido = obtenerTokenDeSolicitud(request); // Extraemos el JWT.

            if (StringUtils.hasText(tokenExtraido) && jwtGenerador.validarToken(tokenExtraido)) {
                String username = jwtGenerador.obtenerUsernameDeJwt(tokenExtraido); // Si el token no es NULO y es VALIDO, se extrae el nombre del usuario(username) del JWT

                UserDetails userDetails = customUsersDetailsService.loadUserByUsername(username); // Se obtiene el objeto con la información del usuario.

                List<String> userRoles = userDetails // Se obtiene la lista de roles del usuario.
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();
                if (userRoles.contains("ROLE_USER") || userRoles.contains("ROLE_ADMIN")) { // Se verifica que el usuario tenga cualquiera de estos dos roles, si tiene uno de ellos, entonces se autentica la solicitud.

//                Autenticación de Spring Security
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o no autorizado");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
