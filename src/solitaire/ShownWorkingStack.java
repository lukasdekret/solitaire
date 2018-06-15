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
public class ShownWorkingStack extends GeneralStack {

    private int onTopValue;
    private String onTopColor;

    //TODO -> nova farba NONE...
    public ShownWorkingStack(int position, int numberOfCards, int onTopValue, String onTopColor) {
        super(5, position, numberOfCards);
        
        this.onTopValue = onTopValue + 1;

        if (onTopColor.equals("BLACK")) {
            this.onTopColor = "RED";
        } else {
            this.onTopColor = "BLACK";
        }


    }

    /**
     * Puts card on the top if it is possible
     * @param card this card is to be put
     * @return true if user can put that card on this stack, false if not
     */
    @Override
    public boolean putCard(Card card) {

        if (this.onTopValue - 1 == card.getCardValue()) {
            if (!this.onTopColor.equals(card.getCardColor())) {
                super.putCard(card);
                this.onTopValue--;
                this.onTopColor = card.getCardColor();
                card.setFaceUp(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks in case that both hidden and shown stacks are empty on that position
     * @param card this card is being checked
     * @return true if user can put card to this stack, false if not
     */
    @Override
    public boolean putOnlyK(Card card) {
        if (card.getCardValue() == 13) {
            this.numberOfCards = 1;
            this.onTopColor = card.getCardColor();
            this.onTopValue = card.getCardValue();
            return true;
        }
        return false;
    }

    /**
     * Checks if user can put card to this stack
     * @param card this card is to be checked
     * @return true if user can put card to this stack, false if not
     */
    @Override
    public boolean canPutCard(Card card) {
        if ("NONE".equals(this.onTopColor) && card.getCardValue() == 13) {
            this.numberOfCards++;
            this.onTopColor = card.getCardColor();
            this.onTopValue = card.getCardValue();
            return true;
        }

        if (card.getCardValue()+1 == this.onTopValue && card.getCardColor() != this.onTopColor) {
            this.numberOfCards++;
            this.onTopColor = card.getCardColor();
            this.onTopValue = card.getCardValue();
            return true;
        }
        return false;
    }

    /**
     * Realizes shallow putting of stack
     * @param stack is put to this stack
     */
    @Override
    public void putStack(Stack<Card> stack) {

        int number = stack.size();
        for (int i = 0; i < number; i++) {
            Card card = stack.remove(0);
            this.stack.push(card);
            this.setOnTopColor(card.getCardColor());
            this.setOnTopValue(card.getCardValue());
        }
    }

    /**
     * Removes N cards from the top of stack
     * @param number number of cards that are going to be put away
     */
    @Override
    public void removeTopN(int number) {
        for (int i = 0; i < number; i++) {
            this.stack.pop();
        }
    }

    /**
     * Gets sub-stack from card defined by parameter card
     * @param card defines from where the stack is going to be substacked
     * @return sub-stack
     */
    @Override
    public Stack<Card> getStack(Card card) {

        Stack<Card> newStack = new Stack<>();

        int index = this.stack.indexOf(card);
        for (int i = index; i < this.stack.size(); i++) {
            this.numberOfCards--;
            newStack.push(this.stack.get(i));
        }

        return newStack;
    }

    /**
     * Checks if stack is empty.
     * @return true if it is empty, false, if not
     */
    public boolean isEmpty() {
        if (super.numberOfCards == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int getOnTopValue() {
        return onTopValue;
    }

    @Override
    public void setOnTopValue(int onTopValue) {
        this.onTopValue = onTopValue;
    }

    @Override
    public String getOnTopColor() {
        return onTopColor;
    }

    @Override
    public void setOnTopColor(String onTopColor) {
        this.onTopColor = onTopColor;
    }
}
