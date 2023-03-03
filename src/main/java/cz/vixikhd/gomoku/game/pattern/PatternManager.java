package cz.vixikhd.gomoku.game.pattern;

import com.google.gson.reflect.TypeToken;
import cz.vixikhd.gomoku.GomokuApplication;
import cz.vixikhd.gomoku.data.SimplePattern;
import cz.vixikhd.gomoku.game.pattern.symbol.PatternParseException;
import cz.vixikhd.gomoku.math.Vector2i;

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
                    loadSimplePatterns(dir.listFiles());
                }
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

//        PatternManager.loadDefensivePatterns();
//        PatternManager.loadOffensivePatterns();
    }

    private static void loadSimplePatterns(File[] patternFiles) throws FileNotFoundException {
        System.out.println("Loading Simple patterns...");

        Gson gson = new Gson();
        for(File patternFile : patternFiles) {
            List<SimplePattern> loadedPatterns = gson.fromJson(new FileReader(patternFile), new TypeToken<List<SimplePattern>>(){}.getType());
            for(SimplePattern patternData : loadedPatterns) {
                try {
                    if(patternData.getType() == PatternType.OFFENSIVE) {
                        PatternManager.offensivePatterns.addAll(patternData.toPattern());
                    } else if(patternData.getType() == PatternType.DEFENSIVE){
                        PatternManager.defensivePatterns.addAll(patternData.toPattern());
                    }
                } catch (PatternParseException e) {
                    System.out.println("Error whilst loading pattern file " + patternFile.getName() + ": " + e.getMessage());
                }
            }
        }
    }
    
    private static void loadDefensivePatterns() {

        // ----------------------
        // Merged Patterns
        // ----------------------

        char[][][] parts = new char[][][] {
            {
                {'N'},
                {'$'},
                {'O'},
                {'O'},
                {'N'}
            },
            {
                {'N'},
                {'$'},
                {'N'},
                {'O'},
                {'O'},
                {'N'}
            },
            {
                {'N'},
                {'$'},
                {'O'},
                {'N'},
                {'O'},
                {'N'}
            },
            {
                {'N'},
                {'O'},
                {'$'},
                {'O'},
                {'N'}
            },
            {
                {'N'},
                {'O'},
                {'$'},
                {'N'},
                {'O'},
                {'N'}
            },
            {
                {'N', '$', 'O', 'O', 'N'},
            },
            {
                {'N', '$', 'N', 'O', 'O', 'N'},
            },
            {
                {'N', '$', 'O', 'N', 'O', 'N'},
            },
            {
                {'N', 'O', '$', 'O', 'N'},
            },
            {
                {'N', 'O', '$', 'N', 'O', 'N'},
            },
            {
                {'N', 'A', 'A', 'A', 'A'},
                {'A', '$', 'A', 'A', 'A'},
                {'A', 'A', 'O', 'A', 'A'},
                {'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'N', 'A', 'A', 'A', 'A', 'A'},
                {'A', '$', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'O', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'N', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'N', 'A', 'A', 'A', 'A', 'A'},
                {'A', '$', 'A', 'A', 'A', 'A'},
                {'A', 'A', 'N', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'O', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'N', 'A', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A'},
                {'A', 'A', '$', 'A', 'A'},
                {'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'N', 'A', 'A', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A', 'A'},
                {'A', 'A', '$', 'A', 'A', 'A'},
                {'A', 'A', 'A', 'N', 'A', 'A'},
                {'A', 'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', 'A', 'A', 'N'}
            },
            {
                {'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', '$', 'A'},
                {'A', 'A', 'O', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A'}
            },
            {
                {'A', 'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', 'A', '$', 'A'},
                {'A', 'A', 'A', 'O', 'A', 'A'},
                {'A', 'A', 'N', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A', 'A'}
            },
            {
                {'A', 'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', 'A', '$', 'A'},
                {'A', 'A', 'A', 'N', 'A', 'A'},
                {'A', 'A', 'O', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A', 'A'}
            },
            {
                {'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', '$', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A'}
            },
            {
                {'A', 'A', 'A', 'A', 'A', 'N'},
                {'A', 'A', 'A', 'A', 'O', 'A'},
                {'A', 'A', 'A', '$', 'A', 'A'},
                {'A', 'A', 'N', 'A', 'A', 'A'},
                {'A', 'O', 'A', 'A', 'A', 'A'},
                {'N', 'A', 'A', 'A', 'A', 'A'}
            },
        };

        int n = 0;
        for(int i = 0, j = parts.length - 1; i < j; ++i) {
            for(int k = i + 1; k < parts.length; ++k) {
                char[][] merged = PatternManager.mergePatterns(parts[i], parts[k], '$', '2');
                if(merged == null) {
                    continue;
                }

                defensivePatterns.add(new Pattern("Two And Two", ++n + ". Variation", Pattern.PatternSymbol.parsePatternSymbolGrid(merged)));
            }
        }
    }

    private static char[][] mergePatterns(char[][] pattern1, char[][] pattern2, char mergeSymbol, char mergeSymbolReplacement) {
        Vector2i pattern1Pos = null, pattern2Pos = null;
        int mergeX = -1, mergeY = -1;

        for(int y = 0; y < pattern1.length; ++y) {
            for(int x = 0; x < pattern1[y].length; ++x) {
                if(pattern1[y][x] == mergeSymbol) {
                    if(x > mergeX)
                        mergeX = x;
                    if(y > mergeY)
                        mergeY = y;

                    pattern1Pos = new Vector2i(x, y);
                }
            }
        }
        
        for(int y = 0; y < pattern2.length; ++y) {
            for(int x = 0; x < pattern2[y].length; ++x) {
                if(pattern2[y][x] == mergeSymbol) {
                    if(x > mergeX)
                        mergeX = x;
                    if(y > mergeY)
                        mergeY = y;

                    pattern2Pos = new Vector2i(x, y);
                }
            }
        }

        assert pattern1Pos != null;
        assert pattern2Pos != null;

        int sizeX = Math.max(pattern1Pos.getX(), pattern2Pos.getX()) + Math.max(pattern1[0].length - pattern1Pos.getX(), pattern2[0].length - pattern2Pos.getX());
        int sizeY = Math.max(pattern1Pos.getY(), pattern2Pos.getY()) + Math.max(pattern1.length - pattern1Pos.getY(), pattern2.length - pattern2Pos.getY());

        char[][] merged = new char[sizeY][sizeX];
        for(int y = 0; y < sizeY; ++y) {
            for(int x = 0; x < sizeX; ++x) {
                merged[y][x] = 'A';
            }
        }

        Vector2i motion = new Vector2i(mergeX, mergeY).subtractVector(pattern1Pos);

        for(int y = 0; y < pattern1.length; ++y) {
            for(int x = 0; x < pattern1[y].length; ++x) {
                merged[y + motion.getY()][x + motion.getX()] = pattern1[y][x];
            }
        }

        motion = new Vector2i(mergeX, mergeY).subtractVector(pattern2Pos);
        for(int y = 0; y < pattern2.length; ++y) {
            for(int x = 0; x < pattern2[y].length; ++x) {
                char currentSymbol = merged[y + motion.getY()][x + motion.getX()];
                if(currentSymbol != 'A' && currentSymbol != pattern2[y][x]) {
                    if(pattern2[y][x] == 'A') {
                        continue;
                    }

                    return null;
                }

                merged[y + motion.getY()][x + motion.getX()] = pattern2[y][x];
            }
        }

        merged[mergeY][mergeX] = mergeSymbolReplacement;

        return merged;
    }

    private static void loadOffensivePatterns() {
        // Four in row -> Five
        offensivePatterns.add(new Pattern("Four in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'P', 'P', 'P', '0'}
        })));
        offensivePatterns.add(new Pattern("Four in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'A', 'A', 'A', 'A'},
            {'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', 'P', 'A', 'A'},
            {'A', 'A', 'A', 'P', 'A'},
            {'A', 'A', 'A', 'A', '0'}
        })));
        // Three plus one
        offensivePatterns.add(new Pattern("Three plus one", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'P', 'P', '0', 'P'}
        })));
        offensivePatterns.add(new Pattern("Three plus one", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'A', 'A', 'A', 'A'},
            {'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', 'P', 'A', 'A'},
            {'A', 'A', 'A', '0', 'A'},
            {'A', 'A', 'A', 'A', 'P'}
        })));
        // Two plus two
        offensivePatterns.add(new Pattern("Two plus two", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'P', '0', 'P', 'P'}
        })));
        offensivePatterns.add(new Pattern("Diagonal two plus two", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'P', 'A', 'A', 'A', 'A'},
            {'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', '0', 'A', 'A'},
            {'A', 'A', 'A', 'P', 'A'},
            {'A', 'A', 'A', 'A', 'P'}
        })));

        // Open three -> Four
        offensivePatterns.add(new Pattern("Open three in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', '1', 'P', 'P', 'P', '1', 'N'}
        })));
        offensivePatterns.add(new Pattern("Four in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', 'A', 'A', 'A', 'A', 'A', 'A'},
            {'A', '1', 'A', 'A', 'A', 'A', 'A'},
            {'A', 'A', 'P', 'A', 'A', 'A', 'A'},
            {'A', 'A', 'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', 'A', 'A', 'P', 'A', 'A'},
            {'A', 'A', 'A', 'A', 'A', '1', 'A'},
            {'A', 'A', 'A', 'A', 'A', 'A', 'N'}
        })));

        offensivePatterns.add(new Pattern("Open three in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', 'P', 'P', '1', 'P', 'N'}
        })));
        offensivePatterns.add(new Pattern("Four in row", "Finish opened, or half opened four.", Pattern.PatternSymbol.parsePatternSymbolGrid(new char[][]{
            {'N', 'A', 'A', 'A', 'A', 'A'},
            {'A', 'P', 'A', 'A', 'A', 'A'},
            {'A', 'A', 'P', 'A', 'A', 'A'},
            {'A', 'A', 'A', '1', 'A', 'A'},
            {'A', 'A', 'A', 'A', 'P', 'A'},
            {'A', 'A', 'A', 'A', 'A', 'N'}
        })));
    }

    public static List<Pattern> getOffensivePatterns() {
        return PatternManager.offensivePatterns;
    }

    public static List<Pattern> getDefensivePatterns() {
        return PatternManager.defensivePatterns;
    }
}
