package co.com.jobsity.bowling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BowlinggameApplicationTests {
	
	@Autowired
    ApplicationContext ctx;

    @Test
    void contextLoads() throws Exception {
    	CommandLineRunner runner = ctx.getBean(CommandLineRunner.class);
        runner.run("test");
    }

}
