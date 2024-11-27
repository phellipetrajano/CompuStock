package br.com.compustock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.compustock.model.Funcionario;
import br.com.compustock.repository.FuncionarioRepository;

@Repository
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		
		
		Funcionario funcionario = this.funcionarioRepository.getOneByEmail(cpf);
		
		
		if (funcionario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}
		
	
		return new User(funcionario.getEmail(),funcionario.getPassword(),true,true,true,true,funcionario.getAuthorities());
		
		
		
	}


   

}