package com.adtimokhin.util.token;

import com.adtimokhin.exceptions.TokenOverflowException;
import com.adtimokhin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author adtimokhin
 * 11.11.2021
 **/

@Component
public class SimpleEmailTokenGenerator implements TokenGenerator {

    private final static int TOKEN_LENGTH = 5; // number of symbols per token

    private final int[] symbols; // symbols contains a list of ascii characters that will be used for token generation

    private final Random random = new Random();

    @Autowired
    private UserService userService;

    public SimpleEmailTokenGenerator() {
        symbols = new int[10];
        int k = 48;
        for (int i = 0; i < 10; i++) {
            symbols[i] = k + i;
        }
    }

    @Override
    public String generate() throws TokenOverflowException{
        List<String> currentTokens = userService.findAllEmailTokens();


        // doing initial checks that identify a necessity for token generation and compartments of it in the future.
        if (currentTokens.size() == 0) {
            return generateRest(0);
        }

        if(currentTokens.size() ==  Math.pow((TOKEN_LENGTH) , symbols.length)){
            throw new TokenOverflowException("Overflow of email tokens.");
        }


        // creating and comparing the actual token
        StringBuilder tokenBuilder = new StringBuilder();

        for (int i = 0; i < TOKEN_LENGTH; i++) {

            // step 1 - add new character to token
            tokenBuilder.append(addRandomCharacter());

            // step 2 - check how many tokens use this letter in that position
            List<String> totallyDifferentTokens = new ArrayList<>();

            for (String currentToken : currentTokens) {
                if ((currentToken.charAt(i)) != tokenBuilder.toString().charAt(i)) {
                    totallyDifferentTokens.add(currentToken);
                }
            }

            // step 3 - remove from the list all tokens that don't contain that symbol in that position
            currentTokens.removeAll(totallyDifferentTokens.subList(0, totallyDifferentTokens.size()));

            // step 4 - return completely auto-generated token if zero tokens are still valid
            if (currentTokens.size() == 0) {
                return (tokenBuilder.append(generateRest(i + 1))).toString();
            }

            // step 5 - generate a new token if there physically can't be any new tokens
            if(currentTokens.size() ==  Math.pow((TOKEN_LENGTH - i - 1) , symbols.length)){
                return generate();
            }
        }

        return generate();
    }

    private char addRandomCharacter() {
        return (char) symbols[random.nextInt(symbols.length - 1)];
    }

    private String generateRest(int position) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < TOKEN_LENGTH - position; i++) {
            stringBuilder.append(addRandomCharacter());
        }

        return stringBuilder.toString();
    }
}
