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
public interface Deck {

    public Stack<Card> shuffleDeck(Stack<Card> stack);
    public Stack<Card> createDeck();

}
