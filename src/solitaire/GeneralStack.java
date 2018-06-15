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
public class GeneralStack {
    
    protected int numberOfCards;
    protected int kindOfStack;
    protected int stackPosition;
    protected Stack<Card> stack;

    /**
     * Constructor
     * @param kind this parameter determine, what kind of stack it is
     * @param position where the stack is 1-13
     * @param numberOfCards how many cards are in the stack
     */
    public GeneralStack(int kind, int position, int numberOfCards) {
        this.kindOfStack = kind;
        this.stackPosition = position;
        this.numberOfCards = numberOfCards;
        this.stack = new Stack<>();
    }

    public boolean putCard(Card card) {
        this.numberOfCards++;
        this.stack.push(card);
        return true;
    }

    public Card getCard() {
        this.numberOfCards--;
        return this.stack.pop();
    }

    /* GETTERS SETTERS */
    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public int getKindOfStack() {
        return kindOfStack;
    }

    public void setKindOfStack(int kindOfStack) {
        this.kindOfStack = kindOfStack;
    }

    public int getStackPosition() {
        return stackPosition;
    }

    public void setStackPosition(int stackPosition) {
        this.stackPosition = stackPosition;
    }

    public Stack<Card> getStack() {
        return stack;
    }

    public void setStackShallow(Stack<Card> stack) {
        this.stack = stack;
    }

    public Stack<Card> getStack(Card card) {
        return null;
    }

    public boolean putOnlyK(Card card) {
        return true;
    }

    public boolean canPutCard(Card card) {
        return true;
    }

    public int getOnTopValue() {
        return 0;
    }

    public void setOnTopValue(int onTopValue) {}

    public String getOnTopColor() {
        return "nic";
    }

    public void setOnTopColor(String onTopColor) {}

    public void putStack(Stack<Card> stack) {}

    public void removeTopN(int number) {}

    public void setStackSymbol(Card.Symbol stackSymbol) {}

    public Card.Symbol getStackSymbol() {return null;}
}
