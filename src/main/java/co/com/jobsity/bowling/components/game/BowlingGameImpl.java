package co.com.jobsity.bowling.components.game;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.jobsity.bowling.components.parser.FileProcessor;
import co.com.jobsity.bowling.components.parser.InputParser;
import co.com.jobsity.bowling.model.PlayerData;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class BowlingGameImpl implements BowlingGame {

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

    public void beginGame() {
        log.info("Init execution");
        String filePath = getScanner().nextLine();
        List<String> lines = getFileProcessor().getFileLines(filePath);
        Map<String, PlayerData> play = getInputParser().processInput(lines);
        getInputParser().showOutPut(play);
        log.info("End execution");
    }

    public void getScannerMenu() {
        log.info("For read a file press 'r', for exit, press 'e'");
        String input = getScanner().nextLine();
        if (EXIT.equalsIgnoreCase(input)) {
            System.exit(0);
        } else if (READ.equalsIgnoreCase(input)) {
            beginGame();
            getScannerMenu();
        } else {
            getScannerMenu();
        }
    }

    public static Scanner getScanner() {
        return SCANNER;
    }

}
