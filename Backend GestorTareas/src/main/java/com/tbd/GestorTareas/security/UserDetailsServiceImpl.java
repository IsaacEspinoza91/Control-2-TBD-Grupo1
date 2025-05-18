package com.tbd.GestorTareas.security;

import com.tbd.GestorTareas.entities.Usuario;
import com.tbd.GestorTareas.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailOrNick(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con credencial: " + email);
        }

        return new UserDetailsImpl(usuario);
    }
}
