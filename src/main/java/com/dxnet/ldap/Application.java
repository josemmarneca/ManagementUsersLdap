package com.dxnet.ldap;

import com.dxnet.ldap.configs.LdapApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * The type Application.
 *
 * @author Givantha Kalansuriya
 */
@SpringBootApplication
public class Application {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
	}
}
