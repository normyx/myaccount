package org.mgoulene.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Myaccount.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    public final SFTP sftp = new SFTP();

    public SFTP getSFTP() {
        return sftp;
    }

    public static class SFTP {
        private boolean enabled = false;
        private String server;
        private String username;
        private String password;

        /**
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * @return the enabled
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * @param enabled the enabled to set
         */
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * @return the server
         */
        public String getServer() {
            return server;
        }

        /**
         * @param server the server to set
         */
        public void setServer(String server) {
            this.server = server;
        }

        /**
         * @param username the username to set
         */
        public void setUsername(String username) {
            this.username = username;
        }
    }
}
