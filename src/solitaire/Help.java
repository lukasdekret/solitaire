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
public class Help {

    private List<GeneralStack> list;
    private Game game;

    public Help(Game game) {
        this.list = game.getList();
        this.game = game;
    }

    /**
     * Checks and realises help, when there is way
     * @return true if help is complete, false if not
     */
    public boolean canIHelpYou() {
        if (!helpFromHelpShownStack()) {
            if (!helpFromWorkingToFinalStack()) {
                if (!helpFromWorkingToWorkingStack()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkOne(int index, Card card) {

        if (list.get(index).canPutCard(card)) {
            shiftUndoStackIn();
            return true;
        }
        return false;
    }

    private void putOne(int index, Stack<Card> stack) {
        game.removeGameRender();
        list.get(index).getStack().push(stack.pop());
        game.initializeGame();
    }


    private boolean checkStack(Card card, int index) {

        if (list.get(index).canPutCard(card)) {
            shiftUndoStackIn();
            return true;
        }
        return false;
    }

    private boolean helpFromWorkingToWorkingStack() {
        for (int i = 9; i <= 15; i++) {
            Stack<Card> stack = list.get(i).getStack();
            if (stack.isEmpty()) continue;


            for (int j = 9; j <= 15; j++) {

                if (i == j) continue;

                for (int k = 0; k < stack.size(); k++) {
                    if (checkStack(stack.get(k), j)) {
                        Solitaire.undo = new Undo(game, 22, i-7, j-7, list.get(i-7).getStack(), stack, list.get(j).getStack());
                        Stack<Card> localStack = list.get(i).getStack(stack.get(k));
                        if (list.get(i).getStack().isEmpty()) {
                            list.get(i).setOnTopColor("NONE");
                        }
                        game.workingWorkingCommunication(i-7, j-7, localStack, i-9, j-9);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean helpFromWorkingToFinalStack() {
        for (int i = 9; i <= 15; i++) {
            if (list.get(i).getStack().isEmpty()) {
                return false;
            }

            Card card = list.get(i).getStack().peek();
            if (checkOne(16, card)) {
                Solitaire.undo = new Undo(game, 21, 16, i-7, list.get(i-7).getStack(), list.get(i).getStack(), list.get(16).getStack());
                putOne(16, list.get(i).getStack());
                return true;
            } else if (checkOne(17, card)) {
                Solitaire.undo = new Undo(game, 21, 17, i-7, list.get(i-7).getStack(), list.get(i).getStack(), list.get(17).getStack());
                putOne(17, list.get(i).getStack());
                return true;
            } else if (checkOne(18, card)) {
                Solitaire.undo = new Undo(game, 21, 18, i-7, list.get(i-7).getStack(), list.get(i).getStack(), list.get(18).getStack());
                putOne(18, list.get(i).getStack());
                return true;
            } else if (checkOne(19, card)) {
                Solitaire.undo = new Undo(game, 21, 19, i-7, list.get(i-7).getStack(), list.get(i).getStack(), list.get(19).getStack());
                putOne(19, list.get(i).getStack());
                return true;
            }
        }
        return false;
    }

    private boolean helpFromHelpShownStack() {
        if (list.get(1).getStack().isEmpty()) {
            return false;
        }

        Stack<Card> stack = list.get(1).getStack();
        Card card = stack.peek();

        if (checkOne(9, card)) {
            Solitaire.undo = new Undo(game, 12, 0, 2, stack, list.get(2).getStack(), list.get(9).getStack());
            putOne(9, stack);
            return true;
        } else if (checkOne(10, card)) {
            Solitaire.undo = new Undo(game, 12, 0, 3, stack, list.get(3).getStack(), list.get(10).getStack());
            putOne(10, stack);
            return true;
        } else if (checkOne(11, card)) {
            Solitaire.undo = new Undo(game, 12, 0, 4, stack, list.get(4).getStack(), list.get(11).getStack());
            putOne(11, stack);
            return true;
        } else if (checkOne(12, card)) {
            Solitaire.undo = new Undo(game, 12, 0, 5, stack, list.get(5).getStack(), list.get(12).getStack());
            putOne(12, stack);
            return true;
        } else if (checkOne(13, card)) {
            Solitaire.undo = new Undo(game, 12, 0, 6, stack, list.get(6).getStack(), list.get(13).getStack());
            putOne(13, stack);
            return true;
        } else if (checkOne(14, card)) {
            Solitaire.undo = new Undo(game, 12, 0, 7, stack, list.get(7).getStack(), list.get(14).getStack());
            putOne(14, stack);
            return true;
        } else if (checkOne(15, card)) {
            Solitaire.undo = new Undo(game, 12, 0, 8, stack, list.get(8).getStack(), list.get(15).getStack());
            putOne(15, stack);
            return true;
        } else if (checkOne(16, card)) {
            Solitaire.undo = new Undo(game, 11, 16, 0, stack, list.get(16).getStack(), null);
            putOne(16, stack);
            return true;
        } else if (checkOne(17, card)) {
            Solitaire.undo = new Undo(game, 11, 17, 0, stack, list.get(17).getStack(), null);
            putOne(17, stack);
            return true;
        } else if (checkOne(18, card)) {
            Solitaire.undo = new Undo(game, 11, 18, 0, stack, list.get(18).getStack(), null);
            putOne(18, stack);
            return true;
        } else if (checkOne(19, card)) {
            Solitaire.undo = new Undo(game, 11, 19, 0, stack, list.get(19).getStack(), null);
            putOne(19, stack);
            return true;
        }
        return false;
    }

    private void shiftUndoStackIn() {
        if (Solitaire.numberOfUndo != 5) {
            Solitaire.numberOfUndo++;
        }

        Solitaire.undo4 = Solitaire.undo3;
        Solitaire.undo3 = Solitaire.undo2;
        Solitaire.undo2 = Solitaire.undo1;
        Solitaire.undo1 = Solitaire.undo;
    }
}
