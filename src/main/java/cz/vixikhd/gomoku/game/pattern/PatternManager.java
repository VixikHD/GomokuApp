package cz.vixikhd.gomoku.game.pattern;

import com.google.gson.reflect.TypeToken;
import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.data.MergedPattern;
import cz.vixikhd.gomoku.data.SimplePattern;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;

public class PatternManager {
    final private static String RESOURCE_SIMPLE_PATH = "simple";
    final private static String RESOURCE_MERGED_PATH = "merged";

    private static boolean initialized = false;

    final private static List<Pattern> registeredPatterns = new ArrayList<>();
    final private static List <String> registeredPatternHash = new ArrayList<>();

    public static void lazyInit() {
        if(PatternManager.initialized) {
            return;
        }
        PatternManager.initialized = true;

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
                } else if(dir.getName().equals(RESOURCE_MERGED_PATH)) {
                    PatternManager.loadMergedPatterns(dir.listFiles());
                }
            }
        } catch(FileNotFoundException | URISyntaxException e) {
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
                    PatternManager.registerPatterns(patternData.toPattern());
                } catch (PatternParseException e) {
                    System.out.println("Error whilst loading pattern file " + patternFile.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    private static void loadMergedPatterns(File[] patternFiles) throws FileNotFoundException {
        System.out.println("Loading merged patterns");

        Gson gson = new Gson();
        for(File patternFile : patternFiles) {
            List<MergedPattern> loadedPatterns = gson.fromJson(new FileReader(patternFile), new TypeToken<List<MergedPattern>>(){}.getType());
            for(MergedPattern patternData : loadedPatterns) {
                try {
                    PatternManager.registerPatterns(patternData.toPattern());
                } catch (PatternParseException e) {
                    System.out.println("Error whilst loading pattern file " + patternFile.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    public static void registerPattern(Pattern pattern) {
        if(PatternManager.registeredPatternHash.contains(pattern.getPatternHash())) {
            return;
        }

        PatternManager.registeredPatternHash.add(pattern.getPatternHash());
        PatternManager.registeredPatterns.add(pattern);
    }

    public static void registerPatterns(Collection<Pattern> patterns) {
        for(Pattern pattern : patterns) {
            PatternManager.registerPattern(pattern);
        }
    }

    public static int getRegisteredPatternCount() {
        return PatternManager.registeredPatterns.size();
    }

    public static int getRegisteredVariationCount() {
        int count = 0;
        for(Pattern pattern : PatternManager.registeredPatterns) {
            count += pattern.getVariations().size();
        }
        return count;
    }

    @Deprecated
    public static List<Pattern> getOffensivePatterns() {
        return PatternManager.registeredPatterns.stream().filter(pattern -> pattern.getType().equals(Pattern.Type.OFFENSIVE)).collect(Collectors.toList());
    }

    @Deprecated
    public static List<Pattern> getDefensivePatterns() {
        return PatternManager.registeredPatterns.stream().filter(pattern -> pattern.getType().equals(Pattern.Type.DEFENSIVE)).collect(Collectors.toList());
    }
}
