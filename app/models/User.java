package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

import org.mindrot.jbcrypt.BCrypt;

import play.data.validation.Constraints.*;

/**
 * User entity managed by Ebean
 */
@Entity 
@Table(name="account")
public class User extends Model {

    @Id
    @Constraints.Required
    @Formats.NonEmpty
    public String email;
    
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public String password;
    
    // -- Queries
    
    public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);
    
    /**
     * Retrieve all users.
     */
    public static List<User> all() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
    
    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("password", password)
            .findUnique();
    }

    /**
     * Authenticate a User.
     */
    public static User validateEmail(String email) {
        return find.where()
            .eq("email", email)
            .findUnique();
    }

    /**
     * Authenticate a User.
     */
    public static boolean validatePassword(String email, String password) {
        User user = User.findByEmail(email);
		String userEncryptedPwd = user.password;
		return User.checkPassword(password, userEncryptedPwd);
    }

	/**
	 * Create a User.
	 */
  public static void create(User user) {
    user.password= User.createPassword(user.password);
    user.save();
  }

//    public static User create(String email, String name, String password) {
//		User user = new User();
//		user.name= name;
//		user.email= email;
//		user.password= password;
////		user.password= User.createPassword(password);
//        user.save();
//        return user;
//    }
    
    // --
    
    public String toString() {
        return "User(" + email + ")";
    }

	/**
	 * Create an encrypted password from a clear string.
	 *
	 * @param clearString
	 *            the clear string
	 * @return an encrypted password of the clear string
	 * @throws AppException
	 *             APP Exception, from NoSuchAlgorithmException
	 */
	public static String createPassword(String clearString) {
		if (clearString == null) {
		    return "false";
		}
		return BCrypt.hashpw(clearString, BCrypt.gensalt());
	}

	/**
	 * Method to check if entered user password is the same as the one that is
	 * stored (encrypted) in the database.
	 *
	 * @param candidate
	 *            the clear text
	 * @param encryptedPassword
	 *            the encrypted password string to check.
	 * @return true if the candidate matches, false otherwise.
	 */
	public static boolean checkPassword(String candidate, String encryptedPassword) {
		if (candidate == null) {
		    return false;
		}
		if (encryptedPassword == null) {
		    return false;
		}
		return BCrypt.checkpw(candidate, encryptedPassword);
	}

}

