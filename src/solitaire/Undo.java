/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author lukasdekret
 */
public class Undo {

    /*********************************/
    /* -=encoding=-                  */
    /* 11 = helpShown -> final       */
    /* 12 = helpShown -> working     */
    /* 21 = working -> final         */
    /* 22 = working -> working       */
    /* 31 = helpHidden -> helpShown  */
    /* 32 = helpShown -> helpHidden  */
    /*********************************/
    private int encoding;

    // helping stacks
    private Stack<Card> first;
    private Stack<Card> second;
    private Stack<Card> third;
    private int indexFinal;
    private int indexHidden;
    private Game game;

    /**
     *
     * @param game where undo is realized
     * @param encoding of given undo
     * @param indexFinal index to final (or in 22 to working) stack
     * @param indexHidden index to hidden stack
     * @param first helping stack
     * @param second helping stack
     * @param third helping stack
     */
    public Undo(Game game, int encoding, int indexFinal, int indexHidden, Stack<Card> first, Stack<Card> second, Stack<Card> third) {
        this.encoding = encoding;
        this.indexFinal = indexFinal;
        this.indexHidden = indexHidden;
        this.game = game;
        this.first = new Stack<>();
        this.second = new Stack<>();
        this.third = new Stack<>();
        makeDeepCopy(this.first, first);
        makeDeepCopy(this.second, second);
        if (third != null) {
            makeDeepCopy(this.third, third);
        }
    }

    // makes deep copy of a given stack
    private void makeDeepCopy(Stack<Card> newStack, Stack<Card> oldStack) {
        for (int i = 0; i < oldStack.size(); i++) {
            Card helpCard = oldStack.elementAt(i);

            int cardValue = helpCard.getCardValue();
            Card.Symbol cardSymbol = helpCard.getCardSymbol();
            int cardID = helpCard.getCardID();
            boolean faceUp = helpCard.getFaceUp();

            double baseX = helpCard.getBaseX();
            double baseY = helpCard.getBaseY();

            Card card = new OneCard(cardValue, cardSymbol, cardID, faceUp);
            card.setBaseX(baseX);
            card.setBaseY(baseY);
            newStack.push(card);
        }

    }
    
    /**
     * Toto by sa po pravde dalo este zoptimalizovat, ze by som nerusil
     * vsetky rendre ale len tie co treba :D
     */
    public void getUndoState() {
        switch (encoding) {
            case 11: {
                    //game.removeGameRender();
                    game.removeShownHelpRender();
                    game.removeFinalRender(indexFinal);
                    List<GeneralStack> list = game.getList();
                    list.get(1).setStackShallow(first);
                    list.get(indexFinal).setStackShallow(second);
                    onTopValueCorrection(list.get(indexFinal));
                    //game.initializeGame();
                    game.renderFinal(list.get(indexFinal).getStack(), indexFinal-15);
                    game.renderShownHelp(first);
                    break;
                }
            case 12: {
                    //game.removeGameRender();
                    game.removeShownHelpRender();
                    game.removeWorkingRender(indexHidden);
                    List<GeneralStack> list = game.getList();
                    list.get(1).setStackShallow(first);
                    list.get(indexHidden).setStackShallow(second);
                    list.get(indexHidden+7).setStackShallow(third);
                    game.renderShownHelp(first);
                    game.renderWorking(list.get(indexHidden).getStack(), list.get(indexHidden+7).getStack(), indexHidden-2);
                    //game.initializeGame();
                    break;
                }
            case 21: {
                    game.removeGameRender();
                    List<GeneralStack> list = game.getList();
                    list.get(indexHidden).setStackShallow(first);
                    list.get(indexHidden+7).setStackShallow(second);
                    list.get(indexFinal).setStackShallow(third);
                    onTopValueCorrection(list.get(indexFinal));
                    game.initializeGame();
                    break;
                }
            case 22: {
                    game.removeGameRender();
                    List<GeneralStack> list = game.getList();
                    list.get(indexHidden+7).setStackShallow(third);
                    list.get(indexFinal).setStackShallow(first);
                    list.get(indexFinal+7).setStackShallow(second);
                    game.initializeGame();
                    break;
                }
            case 31: {
                    game.removeGameRender();
                    List<GeneralStack> list = game.getList();
                    list.get(0).setStackShallow(first);
                    list.get(1).setStackShallow(second);
                    game.initializeGame();
                    break;
                }
            case 32: {
                    game.removeGameRender();
                    List<GeneralStack> list = game.getList();
                    list.get(0).setStackShallow(second);
                    list.get(1).setStackShallow(first);
                    game.initializeGame();
                    break;
                }
            default:
                break;
        }
    }

    // makes correction of card on top
    private void onTopValueCorrection(GeneralStack stack) {
        if (stack.getStack().isEmpty()) {
            stack.setStackSymbol(Card.Symbol.NONE);
            stack.setNumberOfCards(0);
            
        } else {
            stack.setOnTopValue(stack.getStack().peek().getCardValue());
            stack.setStackSymbol(stack.getStack().peek().getCardSymbol());
            stack.setNumberOfCards(stack.getStack().size());
        }
    }
}
