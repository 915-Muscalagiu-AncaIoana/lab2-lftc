package src;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {
    SymbolsTable<Object> constantsSymbolsTable = new SymbolsTable<>(191);
    SymbolsTable<String> identifiersSymbolsTable = new SymbolsTable<>(191);
    List<String> tokens = new ArrayList<>();

    PIF pif = new PIF();

    String pifOut = "PIF.OUT";
    String symbolsTableOut = "ST.OUT";

    public Scanner () {
        Path filePath = Paths.get("symbolsTable/token.in");
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            tokens.addAll(lines.stream().map(String::strip).toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    int matchTokenList (String line, int index) {
        for (String token : tokens) {
            if (line.indexOf(token) == 0) {
                pif.add(new Pair<>(token, -1));
                return index + token.length();
            }
        }
        return index;
    }

    int matchConstant (String line, int index) {
        Pattern intPattern = Pattern.compile("^0|([+-])?[1-9]([0-9])*");//. represents single character
        Pattern charPattern = Pattern.compile("^'[a-zA-Z0-9]'");
        Pattern stringPattern = Pattern.compile("^\"([a-zA-Z0-9])*\"");
        Pattern boolPattern = Pattern.compile("^true|false");
        List<Pattern> patterns = Arrays.asList(intPattern, charPattern, stringPattern, boolPattern);
        for (Pattern pattern : patterns) {
            Matcher tokenMatcher = pattern.matcher(line);
            if (tokenMatcher.find() && tokenMatcher.start() == 0) {
                String matchedToken = tokenMatcher.group();
                Pair<Integer, Integer> pair = constantsSymbolsTable.add(matchedToken);
                pif.add(new Pair<>("const", pair));
                return index + matchedToken.length();
            }
        }
        return index;
    }

    int matchIdentifier (String line, int index) {
        Pattern identifierPattern = Pattern.compile("\\$[a-zA-Z][a-zA-Z0-9]*");
        Matcher tokenMatcher = identifierPattern.matcher(line);
        if (tokenMatcher.find() && tokenMatcher.start()==0) {
            String matchedToken = tokenMatcher.group();
            Pair<Integer, Integer> pair = identifiersSymbolsTable.add(matchedToken);
            pif.add(new Pair<>("id", pair));
            return index + matchedToken.length();
        }
        return index;
    }

    void lookAhead (String fileName) {
        Path filePath = Paths.get(fileName);
        Charset charset = StandardCharsets.UTF_8;
        Boolean flagIsCorrect = true;
        String errors = "";
        int nrline = 0;
        int index = 0;


        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            for (String line : lines) {
                if (!line.isBlank()){
                    index = 0;
                    while (index < line.length()) {
                        if (line.charAt(index) == ' ') {
                            index++;
                            continue;
                        }
                        int newIndex = matchTokenList(line.substring(index), index);
                        if (newIndex != index) {
                            index = newIndex;
                            continue;
                        }
                        newIndex = matchConstant(line.substring(index), index);
                        if (newIndex != index) {
                            index = newIndex;
                            continue;
                        }
                        newIndex = matchIdentifier(line.substring(index), index);
                        if (newIndex != index) {
                            index = newIndex;
                            continue;
                        }

                        errors += ("lexical error, line: " + nrline + ", index: " + index +", token: "+line.substring(index));
                        errors += '\n';
                        flagIsCorrect = false;
                        break;
                    }
                    if (!flagIsCorrect) {
                        break;
                    }
                }
                nrline++;
            }
            Formatter fmt;
            BufferedWriter writer = new BufferedWriter(new FileWriter(symbolsTableOut));
            writer.write("Hash table with separate chaining\n");
            System.out.println("-------Identifiers Symbols Table-------");
            fmt = identifiersSymbolsTable.getTable();
            writer.write(fmt.toString());
            writer.write("---------------------------------------------\n");
            System.out.println("-------Constants Symbols Table-------");
            fmt = constantsSymbolsTable.getTable();
            writer.write(fmt.toString());
            writer.close();
            System.out.println("-----------------PIF----------------");
            writer = new BufferedWriter(new FileWriter(pifOut));
            fmt = pif.getTable();
            writer.write(fmt.toString());
            writer.close();
            if (flagIsCorrect) {
                System.out.println("lexically correct");
            } else {
                System.out.println(errors);
            }
        } catch (
                IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }
}
