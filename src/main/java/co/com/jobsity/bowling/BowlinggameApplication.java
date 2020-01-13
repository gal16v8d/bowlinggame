package co.com.jobsity.bowling;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import co.com.jobsity.bowling.components.game.BowlingGame;

@SpringBootApplication
@ComponentScan({ BowlinggameApplication.BASE_PACKAGE, BowlinggameApplication.COMP_PACKAGE })
public class BowlinggameApplication implements CommandLineRunner {

	public static final String BASE_PACKAGE = "co.com.jobsity.bowling";
	public static final String COMP_PACKAGE = "co.com.jobsity.bowling.components";
    private BowlingGame game;

    public BowlinggameApplication(@Autowired BowlingGame game) {
        this.game = game;
    }

    public static void main(String[] args) {
        SpringApplication.run(BowlinggameApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    	if (Objects.isNull(args)) {
    		game.executeGame();
    	}
    }

}
