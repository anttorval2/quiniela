package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Quiniela;
import domain.User;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class UserService {

	// Managed repository ---------------------------------------

	@Autowired
	private UserRepository userRepository;

	// Supporting services --------------------------------------

	// Constructors ---------------------------------------------

	public UserService() {
		super();
	}

	// Simple CRUD methods --------------------------------------

	public Collection<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(int userId) {
		return userRepository.findOne(userId);
	}

	public User findByPrincipal() {
		UserAccount userAccount = LoginService.getPrincipal();
		return findByUserAccount(userAccount);
	}

	public User findByUserAccount(UserAccount userAccount) {

		User result;

		result = userRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public User create() {
		
		User user = new User();

		UserAccount ua = new UserAccount();
		Authority au = new Authority();
		au.setAuthority("USER");
		ua.getAuthorities().add(au);
		user.setUserAccount(ua);
		

		user.setQuinielas(new ArrayList<Quiniela>());
		user.setUserAccount(ua);
		return user;

	}

	public void save(User user) {
		
        Md5PasswordEncoder encoder;
        String pass = user.getUserAccount().getPassword();
        encoder = new Md5PasswordEncoder();
        String hash = encoder.encodePassword(pass, null);
        user.getUserAccount().setPassword(hash);
        
		userRepository.save(user);

	}
}
