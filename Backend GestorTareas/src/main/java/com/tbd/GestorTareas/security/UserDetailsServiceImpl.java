package com.tbd.GestorTareas.security;

import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrNick) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailOrNick(emailOrNick);

        // Validacion usuario existe
        Usuario usuario = usuarioOpt.orElseThrow(() ->
                new UsernameNotFoundException("Usuario no encontrado"));

        return new UserDetailsImpl(usuario);
    }
}
