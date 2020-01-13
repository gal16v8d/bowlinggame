package co.com.jobsity.bowling.components.game;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import co.com.jobsity.bowling.components.parser.FileProcessor;
import co.com.jobsity.bowling.components.parser.InputParser;
import co.com.jobsity.bowling.model.PlayerData;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
@Profile("dev")
public class BowlingGameImpl implements BowlingGame, CommandLineRunner {

    protected static final String EXIT = "e";
    protected static final String READ = "r";
    private static final Scanner SCANNER = new Scanner(System.in);
    @Autowired
    private FileProcessor fileProcessor;
    @Autowired
    private InputParser inputParser;

    @Override
    public void executeGame() {
        try {
            getScannerMenu();
        } finally {
            getScanner().close();
        }
    }

    @Override
    public void getScannerMenu() {
        log.info("For read a file press 'r', for exit, press 'e'");
        String input = getScanner().nextLine();
        if (EXIT.equalsIgnoreCase(input)) {
            log.info("Finish execution");
            System.exit(0);
        } else if (READ.equalsIgnoreCase(input)) {
            log.info("Please write the full file path to process the bowling game");
            log.info("for example: E:\\bowlinggame\\src\\test\\resources\\sample.txt");
            String filePath = getScanner().nextLine();
            beginGame(filePath);
            getScannerMenu();
        } else {
            getScannerMenu();
        }
    }

    @Override
    public void beginGame(String filePath) {
        log.info("Init execution");
        List<String> lines = getFileProcessor().getFileLines(filePath);
        Map<String, PlayerData> play = getInputParser().processInput(lines);
        log.info(getInputParser().showOutPut(play));
        log.info("End execution");
    }

    @Override
    public void run(String... args) throws Exception {
        executeGame();
    }

    public static Scanner getScanner() {
        return SCANNER;
    }

}
