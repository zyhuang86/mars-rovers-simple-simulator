import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

class MarsRoverConfigurationLoader {
   private static final String CONFIG_FILE_NAME = "app.yml";
   private MarsRoverConfiguration configuration;
   private static final Logger LOG = Logger.getLogger(MarsRoverConfigurationLoader.class.getName());

   MarsRoverConfigurationLoader() throws IOException {
      this.configuration = loadConfiguration();
   }

   MarsRoverConfiguration getConfiguration() throws IOException {
      return configuration;
   }

   private MarsRoverConfiguration loadConfiguration() throws IOException {
      URL url = getConfigURL();
      InputStream inputStream = url.openConnection().getInputStream();
      return new Yaml().loadAs(inputStream, MarsRoverConfiguration.class);
   }

   private URL getConfigURL() throws IOException {
      URL url = getClass().getClassLoader().getResource("configuration/" + CONFIG_FILE_NAME);
      if (url != null) {
         url.openStream().close();
      } else {
         LOG.warning("Configuration file: " + CONFIG_FILE_NAME + " is not found!\n");
      }
      return url;
   }
}
