package servlet_basics.intializer;

import jakarta.servlet.ServletContext;
import java.util.Set;

/**
 * This interface is listed as one of the values for the {@link jakarta.servlet.annotation.HandlesTypes} annotation so {@link SecurityFilterInitializer}
 * Each implementation of this interface will be passed to the {@link jakarta.servlet.ServletContainerInitializer#onStartup(Set, ServletContext)} method.
 **/
public interface OtherSecurityInitializer {
}
