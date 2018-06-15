/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
/**
 *
 * @author lukasdekret
 */
public class FinalStack extends GeneralStack {

    private Card.Symbol stackSymbol;
    private int onTopValue;

    /**
     * Constructor.
     * @param position on which position is going to be this final stack rendered
     * @param numberOfCards how many cards do I have in this stack
     * @param symbol what symbol this stack accepts
     */
    public FinalStack(int position, int numberOfCards, Card.Symbol symbol) {
        super(1, position, numberOfCards);
        this.stackSymbol = symbol;
        this.onTopValue = 1;
    }

    /**
     * Checks if the card can be put to final stack
     * @param card this card is checked
     * @return true if user can put the card on the stack
     */
    @Override
    public boolean canPutCard(Card card) {
        if (stackSymbol == Card.Symbol.NONE) {
            if (card.getCardValue() == 1) {
                stackSymbol = card.getCardSymbol();
                onTopValue = 1;
                return true;
            } else {
                return false;
            }
        }
        if (stackSymbol == card.getCardSymbol() && onTopValue+1 == card.getCardValue()) {
            onTopValue = card.getCardValue();
            return true;
        }
        return false;
    }

    public void putingCard(Card card) {
        stack.push(card);
        onTopValue = card.getCardValue();
        stackSymbol = card.getCardSymbol();
    }

    @Override
    public Card.Symbol getStackSymbol() {
        return stackSymbol;
    }

    @Override
    public void setStackSymbol(Card.Symbol stackSymbol) {
        this.stackSymbol = stackSymbol;
    }

    public int getOnTopValue() {
        return onTopValue;
    }

    public void setOnTopValue(int onTopValue) {
        this.onTopValue = onTopValue;
    }
}
