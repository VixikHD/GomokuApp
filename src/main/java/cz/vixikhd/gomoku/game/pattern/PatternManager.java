package cz.vixikhd.gomoku.game.pattern;

import com.google.gson.reflect.TypeToken;
import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.data.SimplePattern;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class PatternManager {
    final private static String RESOURCE_SIMPLE_PATH = "simple";
    final private static String RESOURCE_MERGED_PATH = "merged";

    final private static List<Pattern> defensivePatterns = new ArrayList<>();
    final private static List<Pattern> offensivePatterns = new ArrayList<>();

    public static void init() {
        if(!PatternManager.defensivePatterns.isEmpty()) {
            return;
        }

        try {
            URL res = GomokuApplication.class.getResource("patterns/");
            assert res != null;
            File[] subdirectories = Paths.get(res.toURI()).toFile().listFiles(File::isDirectory);
            if(subdirectories == null) {
                throw new RuntimeException("Patterns ain't compiled with the program");
            }

            for(File dir : subdirectories) {
                if(dir.getName().equals(RESOURCE_SIMPLE_PATH)) {
                    PatternManager.loadSimplePatterns(dir.listFiles());
                }
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadSimplePatterns(File[] patternFiles) throws FileNotFoundException {
        System.out.println("Loading Simple patterns...");

        Gson gson = new Gson();
        for(File patternFile : patternFiles) {
            List<SimplePattern> loadedPatterns = gson.fromJson(new FileReader(patternFile), new TypeToken<List<SimplePattern>>(){}.getType());
            for(SimplePattern patternData : loadedPatterns) {
                try {
                    if(patternData.getType() == Pattern.Type.OFFENSIVE) {
                        PatternManager.offensivePatterns.addAll(patternData.toPattern());
                    } else if(patternData.getType() == Pattern.Type.DEFENSIVE){
                        PatternManager.defensivePatterns.addAll(patternData.toPattern());
                    }
                } catch (PatternParseException e) {
                    System.out.println("Error whilst loading pattern file " + patternFile.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    public static List<Pattern> getOffensivePatterns() {
        return PatternManager.offensivePatterns;
    }

    public static List<Pattern> getDefensivePatterns() {
        return PatternManager.defensivePatterns;
    }
}
