/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import java.util.Stack;

/**
 *
 * @author lukasdekret
 */
public class HiddenHelpStack extends GeneralStack {

    public HiddenHelpStack(int position, int numberOfCards) {
        super(2, position, numberOfCards);
    }

    /**
     * Realises deep copy of a stack
     * @param stack this stack is copied
     */
    public void setStackDeep(Stack<Card> stack) {
        for (int i = 0; i < stack.size(); i++) {
            int value = stack.get(i).getCardValue();
            Card.Symbol symbol = stack.get(i).getCardSymbol();
            int ID = stack.get(i).getCardID();
            boolean faceUp = stack.get(i).getFaceUp();
            Card card = new OneCard(value, symbol, ID, faceUp);
            this.stack.push(card);
        }
    }
}
