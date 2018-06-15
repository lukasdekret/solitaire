/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import java.util.Stack;
import java.util.Random;

/**
 *
 * @author lukasdekret
 */
public class CardDeck implements Deck {

    @Override
    public Stack<Card> createDeck() {
        int counter = 1;
        Stack<Card> stack = new Stack<>();

        for (int i = 1; i < 14; i++) {
            stack.push(new OneCard(i, Card.Symbol.CLUBS, counter++, false));
        }
        for (int i = 1; i < 14; i++) {
            stack.push(new OneCard(i, Card.Symbol.DIAMONDS, counter++, false));
        }
        for (int i = 1; i < 14; i++) {
            stack.push(new OneCard(i, Card.Symbol.HEARTS, counter++, false));
        }
        for (int i = 1; i < 14; i++) {
            stack.push(new OneCard(i, Card.Symbol.SPADES, counter++, false));
        }

        return shuffleDeck(stack);
    }

    /**
     * Shuffles given stack
     * @param stack this stack is going to be shuffled
     * @return shuffled stack
     */
    @Override
    public Stack<Card> shuffleDeck(Stack<Card> stack) {
        Random rand = new Random();
        int counter = 52;
        Stack<Card> newStack = new Stack<>();

        for (int i = 0; i < 52; i++) {
            int n = rand.nextInt(counter--);
            //System.out.print(i);

            newStack.push(stack.remove(n));
        }

        return newStack;
    }
}
