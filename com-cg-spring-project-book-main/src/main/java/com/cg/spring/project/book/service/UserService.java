package com.cg.spring.project.book.service;

	import java.util.List;
	import java.util.Optional;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	import com.cg.spring.project.book.exception.UserAlreadyExistsException;
	import com.cg.spring.project.book.exception.UserNotFoundException;
import com.cg.spring.project.book.model.Book;
import com.cg.spring.project.book.model.User;
	import com.cg.spring.project.book.repository.UserRepository;

	@Service
	public class UserService implements UserService {

		private final Logger LOG = LoggerFactory.getLogger(this.getClass());

		@Autowired
		private UserRepository UserRepository;

		User loggedInUser;

		public List<AppUser> getAllUsers() {
			List<AppUser> userList = UserRepository.findAll();
			if (userList.isEmpty()) {
				String exceptionMessage = "AppUsers don't exist in the database.";
				LOG.warn(exceptionMessage);
				throw new UserNotFoundException(exceptionMessage);
			} else {
				LOG.info("depList returned successfully.");
				return userList;
			}
		}

		@Override
		public User registerUser(User user) {
			LOG.info(User.toString());
			Optional<User> userOptional = UserRepository.findById(User.getUserName());
			if (userOptional.isEmpty()) {
				return UserRepository.save(User);
			} else {
				String exceptionMessage = "User with userName " + User.getUserName() + " already exists.";
				throw new UserAlreadyExistsException(exceptionMessage);
			}
		}

		@Override
		public User loginUser(User User) {
			LOG.info(User.toString());
			Optional<User> userOptional = UserRepository.findById(User.getUserName());
			if (userOptional.isPresent()) {
				if (User.equals(userOptional.get())) {
					LOG.info(userOptional.get().toString());
					loggedInUser = User; // successful login
					return User;
				} else {
					String exceptionMessage = "Wrong password!";
					LOG.warn(exceptionMessage);
					throw new UserNotFoundException(exceptionMessage);
				}
			} else {
				String exceptionMessage = "Wrong userName!";
				LOG.warn(exceptionMessage);
				throw new UserNotFoundException(exceptionMessage);
			}
		}

		@Override
		public String logoutUser(String userName) {
			if (loggedInUser.getUserName().equals(userName)) {

				loggedInUser = null;
				return userName;
			} else {
				String exceptionMessage = "User with userName " + userName + " is not logged in.";
				LOG.warn(exceptionMessage);
				throw new AppUserNotFoundException(exceptionMessage);
			}
		}

		@Override
		public AppUser updateUser(AppUser appUser) {
			Optional<AppUser> userOptional = appUserRepository.findById(appUser.getUserName());
			if (userOptional.isPresent()) {
				LOG.info(userOptional.get().toString());
				return appUserRepository.save(appUser);
			} else {
				String exceptionMessage = "AppUser with userName " + appUser.getUserName() + " not found!";
				LOG.warn(exceptionMessage);
				throw new AppUserNotFoundException(exceptionMessage);
			}
		}

		public List<Book> getAllBooks() {
			// TODO Auto-generated method stub
			return null;
		}

		public Book getUserById(int userid) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
