package br.com.devoliga.service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.devoliga.Repository.UsuarioRepository;
import br.com.devoliga.model.Usuario;
import br.com.devoliga.model.UsuarioLogin;
import br.com.devoliga.service.exception.ObjectNotFoundException;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public String encoder(String senha) {
		return encoder.encode(senha);

	}

	public List<Usuario> listarUsuario() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> buscarUsuarioId(long id) {

		return usuarioRepository.findById(id);
	}

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário já existe", null);

		int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();

		if (idade < 18)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário é menor de 18", null);

		usuario.setSenha(encoder(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));

	}

	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		try {
		if (usuarioRepository.findByUsuario(usuario.getUsuario()) != null) {

			int idade = Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears();

			if (idade < 18)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O Usuário é menor de idade!", null);

			usuario.setSenha(encoder(usuario.getSenha()));

			return Optional.of(usuarioRepository.save(usuario));

			
		} else {

			throw new ObjectNotFoundException("Não é possível mudar o nome do usuario");

		}}catch(org.springframework.dao.DataIntegrityViolationException e) {
			throw new ObjectNotFoundException("Esse usuario já existe e não é possivel mudar o nome usuario");
		} 

	}

	public Optional<UsuarioLogin> loginUsuario(Optional<UsuarioLogin> usuarioLogin) {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {

			if (encoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				usuarioLogin.get().setToken(authHeader);

				return usuarioLogin;

			}

		}

		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login invalido, não autorizado!", null);
	}
	
	
}
