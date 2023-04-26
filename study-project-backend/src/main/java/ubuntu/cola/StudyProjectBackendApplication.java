package ubuntu.cola;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StudyProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyProjectBackendApplication.class, args);
		log.info("StudyProjectQingKongUbuntu 项目启动成功 ");
	}

}
