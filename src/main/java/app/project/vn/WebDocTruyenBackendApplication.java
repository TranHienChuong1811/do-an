package app.project.vn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;

@SpringBootApplication(exclude = {GroovyTemplateAutoConfiguration.class})
public class WebDocTruyenBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebDocTruyenBackendApplication.class, args);
    }

}
