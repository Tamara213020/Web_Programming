package mk.ukim.finki.wp.jan2022.g1.config;


import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * This class is used to configure user login on path '/login' and logout on path '/logout'.
 * The only public page in the application should be '/'.
 * All other pages should be visible only for the authenticated users.
 * Furthermore, in the "list.html" template, the 'Edit', 'Delete', 'Add' buttons should only be
 * visible for a user with role 'ROLE_MANAGER'.
 * <p>
 * For login the users from the database should be used.
 */

public class SecurityConfig  {


}
