/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author lukasdekret
 */
public class Game {

    private Pane pane; // local pane for this game

    public List<GeneralStack> list; // list of all stacks
    private int scope; // to recognize if there is big screen or small

    private Game game;

    public Game(Pane pane, int scope, int position) {
        this.pane = pane;
        this.scope = scope;
        this.game = this;
    }

    public Game(Pane pane, int scope) {
        this.pane = pane;
        this.scope = scope;
        this.game = this;

        Deck deck = new CardDeck();

        Stack<Card> stack = deck.createDeck(); // creating and shuffling the deck of 52 cards

        list = new ArrayList<>();

        fillList(stack); // fills initial list

        initializeGame(); // renderes initial state of game

    }

    public Game(Pane pane, int scope, List<GeneralStack> oldList) {
        this.pane = pane;
        this.scope = scope;

        list = oldList;
        this.game = this;

        initializeGame(); // renderes initial state of game

    }

    // filling the stacks
    public void fillList(Stack<Card> stack) {

        /********************************/
        /*  GeneralStack(f,g)           */
        /*                              */
        /*  f = position of stack       */
        /*  g = number of cards         */
        /********************************/

        list.add(new HiddenHelpStack(1, 0));
        for (int i = 0; i < 24; i++) {
            list.get(0).putCard(stack.pop());
        }

        list.add(new ShownHelpStack(2, 0));

        list.add(new HiddenWorkingStack(7, 0));
        list.add(new HiddenWorkingStack(8, 0));
        for (int i = 0; i < 1; i++) {
            list.get(3).putCard(stack.pop());
        }

        list.add(new HiddenWorkingStack(9, 0));
        for (int i = 0; i < 2; i++) {
            list.get(4).putCard(stack.pop());
        }

        list.add(new HiddenWorkingStack(10, 0));
        for (int i = 0; i < 3; i++) {
            list.get(5).putCard(stack.pop());
        }

        list.add(new HiddenWorkingStack(11, 0));
        for (int i = 0; i < 4; i++) {
            list.get(6).putCard(stack.pop());
        }

        list.add(new HiddenWorkingStack(12, 0));
        for (int i = 0; i < 5; i++) {
            list.get(7).putCard(stack.pop());
        }

        list.add(new HiddenWorkingStack(13, 0));
        for (int i = 0; i < 6; i++) {
            list.get(8).putCard(stack.pop());
        }

        list.add(new ShownWorkingStack(7, 0, stack.peek().getCardValue(), stack.peek().getCardColor()));
        list.get(9).putCard(stack.pop());

        list.add(new ShownWorkingStack(8, 0, stack.peek().getCardValue(), stack.peek().getCardColor()));
        list.get(10).putCard(stack.pop());

        list.add(new ShownWorkingStack(9, 0, stack.peek().getCardValue(), stack.peek().getCardColor()));
        list.get(11).putCard(stack.pop());

        list.add(new ShownWorkingStack(10, 0, stack.peek().getCardValue(), stack.peek().getCardColor()));
        list.get(12).putCard(stack.pop());

        list.add(new ShownWorkingStack(11, 0, stack.peek().getCardValue(), stack.peek().getCardColor()));
        list.get(13).putCard(stack.pop());

        list.add(new ShownWorkingStack(12, 0, stack.peek().getCardValue(), stack.peek().getCardColor()));
        list.get(14).putCard(stack.pop());
        
        list.add(new ShownWorkingStack(13, 0, stack.peek().getCardValue(), stack.peek().getCardColor()));
        list.get(15).putCard(stack.pop());

        list.add(new FinalStack(3, 0, Card.Symbol.NONE));
        list.add(new FinalStack(4, 0, Card.Symbol.NONE));
        list.add(new FinalStack(5, 0, Card.Symbol.NONE));
        list.add(new FinalStack(6, 0, Card.Symbol.NONE));

    }

    /**
     * Initializes game
     */
    public void initializeGame() {

        renderHiddenHelp(list.get(0).getStack());
        renderShownHelp(list.get(1).getStack());
        renderFinalBack();

        renderWorking(list.get(2).getStack(), list.get(9).getStack(), 0);
        renderWorking(list.get(3).getStack(), list.get(10).getStack(), 1);
        renderWorking(list.get(4).getStack(), list.get(11).getStack(), 2);
        renderWorking(list.get(5).getStack(), list.get(12).getStack(), 3);
        renderWorking(list.get(6).getStack(), list.get(13).getStack(), 4);
        renderWorking(list.get(7).getStack(), list.get(14).getStack(), 5);
        renderWorking(list.get(8).getStack(), list.get(15).getStack(), 6);

        renderFinal(list.get(16).getStack(), 450/scope);
        renderFinal(list.get(17).getStack(), 605/scope);
        renderFinal(list.get(18).getStack(), 760/scope);
        renderFinal(list.get(19).getStack(), 915/scope);
            
    }

    /**
     * Removes whole render
     */
    public void removeGameRender() {
        removeHiddenHelpRender();
        removeShownHelpRender();
        removeWorkingRender(2);
        removeWorkingRender(3);
        removeWorkingRender(4);
        removeWorkingRender(5);
        removeWorkingRender(6);
        removeWorkingRender(7);
        removeWorkingRender(8);
        removeFinalRender(16);
        removeFinalRender(17);
        removeFinalRender(18);
        removeFinalRender(19);
    }

    /**
     * Removes final render
     * @param index index to list where final stack is
     */
    public void removeFinalRender(int index) {
        Stack<Card> finalStack = list.get(index).getStack();
        int count = finalStack.size();
        for (int j = 0; j < count; j++) {
            pane.getChildren().remove(finalStack.get(j).getFrontC());
        }
    }

    /**
     * Renderes final stack
     * @param stack stack that is going to be rendered
     * @param posX on this position
     */
    public void renderFinal(Stack<Card> stack, int posX) {
        for (int i = 0; i < stack.size(); i++) {
            renderOrdinaryCard(stack.elementAt(i), posX, 20/scope);

        }
    }

    /**
     * Renderes green rectangles on which final stacks will be
     */
    public void renderFinalBack() {
        for (int i = 0; i < 4; i++) {
            Rectangle r = new Rectangle();
            r.setWidth(120/scope);
            r.setHeight(190/scope);
            r.setTranslateX((450 + i * 155)/scope);
            r.setTranslateY(20/scope);
            r.setFill(Color.GREEN);
            pane.getChildren().add(r);
        }
    }

    /**
     * Removes hidden help render
     */
    public void removeHiddenHelpRender() {
        Stack<Card> hiddenHelp = list.get(0).getStack();
        for (int i = 0; i < hiddenHelp.size(); i++) {
            pane.getChildren().remove(hiddenHelp.get(i).getBackC());
        }
    }

    /**
     * Removes working render.. both hidden and shown
     *
     * @param index index to hidden stack in list
     */
    public void removeWorkingRender(int index) {
        Stack<Card> hiddenStack = list.get(index).getStack();
        Stack<Card> shownStack = list.get(index+7).getStack();
        for (int i = 0; i < hiddenStack.size(); i++) {
            pane.getChildren().remove(hiddenStack.get(i).getBackC());
        }
        for (int i = 0; i < shownStack.size(); i++) {
            pane.getChildren().remove(shownStack.get(i).getFrontC());
        }
    }

    /**
     * Removes shown help render
     */
    public void removeShownHelpRender() {
        Stack<Card> localStack = list.get(1).getStack();
        int count = localStack.size();
        for (int i = 0; i < count; i++) {
            pane.getChildren().remove(localStack.get(i).getFrontC());
        }
    }

    /**
     * Renderes both hidden and shown working stacks on one position
     * @param hiddenStack hidden stack that is going to be rendered
     * @param shownStack shown stack that is going to be rendered
     * @param position position on which those two stacks are 0-6
     */
    public void renderWorking(Stack<Card> hiddenStack, Stack<Card> shownStack, int position) {
       
        if (!hiddenStack.isEmpty() && shownStack.isEmpty()) { // flop card from hidden to shown
            Card card = hiddenStack.pop();
            shownStack.push(card);
            list.get(position+9).setOnTopColor(card.getCardColor());
            list.get(position+9).setOnTopValue(card.getCardValue());

        } else if (hiddenStack.isEmpty() && shownStack.isEmpty()) { // sets color NONE when both stacks are empty
            list.get(position+9).setOnTopColor("NONE");

        } else { // sets color and value of card on top of the stack
            list.get(position+9).setOnTopColor(shownStack.peek().getCardColor());
            list.get(position+9).setOnTopValue(shownStack.peek().getCardValue());
        }

        int finalPosX = (45 + position*155)/scope;
        int finalPosY = 250/scope;
        int height = 190/scope;
        int width = 120/scope;
        int indexOfShown = position + 9; // index to list of shown stack
        int indexOfHidden = position + 2; // index to list of hidden stack

        int halfX = width/2; // when dragging, where the mouse is going to be on card
        int halfY = 10/scope;

        Rectangle r = new Rectangle();
        r.setWidth(120/scope);
        r.setHeight(190/scope);
        r.setTranslateX(finalPosX);
        r.setTranslateY(finalPosY);
        r.setFill(Color.GREEN);
        pane.getChildren().add(r); // green rectangle under every working stack

        for (int i = 0; i < hiddenStack.size(); i++) { // rendering hidden stack
            ImageView help = hiddenStack.get(i).getBackC();
            help.setTranslateX(finalPosX);
            help.setTranslateY(finalPosY);
            help.setFitHeight(height);
            help.setFitWidth(width);
            pane.getChildren().add(help);
            finalPosY += 20/scope; // how far are rendered cards from each other
        }

        for (int i = 0; i < shownStack.size(); i++) { // rendering shown stack
            ImageView help = shownStack.get(i).getFrontC();
            help.setTranslateX(finalPosX);
            help.setTranslateY(finalPosY);
            help.setFitHeight(height);
            help.setFitWidth(width);
            pane.getChildren().add(help);
            
            shownStack.get(i).setBaseX(finalPosX); // setting base of X axis
            shownStack.get(i).setBaseY(finalPosY); // setting base of Y axis

            finalPosY += 30/scope; // how far are rendered shown cards from each other

            // local stack of all dragging cards
            Stack<Card> localStack = list.get(indexOfShown).getStack(shownStack.get(i));

            int j = localStack.size(); // size of dragged stack

            help.setOnMouseDragged(new EventHandler<MouseEvent>() { // setting event to every card
                @Override
                public void handle(MouseEvent event) {

                    for (int i = 0; i < j; i++) {
                        ImageView image = localStack.get(i).getFrontC();
                        image.toFront(); // getting card to front

                        // setting X,Y axis
                        image.setTranslateX(event.getX()+image.getTranslateX()-halfX);
                        image.setTranslateY(event.getY()+image.getTranslateY()-halfY);

                    }
                }
            });

            help.setOnMouseReleased(new EventHandler<MouseEvent>() { // setting event released

                @Override
                public void handle(MouseEvent event) {
                    double posX = event.getX()+help.getTranslateX();
                    double posY = event.getY()+help.getTranslateY();

                    // finding, where user dropped stack
                    recognizeFieldFromWorking(localStack, posX, posY, indexOfHidden);
                }
            });
        }
    }

    /**
     * Setting whole dragged stack to original position
     * @param stack this stack is being set to original position
     */
    public void returnNotSuitableStack(Stack<Card> stack) {
        for (int i = 0; i < stack.size(); i++) {
            ImageView help = stack.get(i).getFrontC();
            help.setTranslateX(stack.get(i).getBaseX());
            help.setTranslateY(stack.get(i).getBaseY());
        }
    }

    public boolean youWin() {
        for (int i = 16; i <= 19 ; i++) {
            if (list.get(i).getStack().size() < 13) {
                return false;
            }
        }
        return true;
    }

    public void winner() {
        ImageView help = new ImageView();
        Image image = new Image("file:lib/img/win.jpg");
        help.setImage(image);
        help.setTranslateX(0);
        help.setTranslateY(0);
        help.setFitHeight(635/scope);
        help.setFitWidth(1160/scope);
        pane.getChildren().add(help);
    }

    /**
     * Re-renderes changed stacks
     * @param card dragged from working to final
     * @param posX where is final stack set
     * @param posY where is final stack set
     * @param shownStack I am removing from this stack
     * @param hiddenStack stack under shownStack
     * @param indexOfHidden index to list of stacks
     * @param finalIndex index list of stacks to final stack
     */
    public void workingFinalCommunication(Card card, int posX, int posY, Stack<Card> shownStack, Stack<Card> hiddenStack, int indexOfHidden, int finalIndex) {
        int position = indexOfHidden - 2;
        removeWorkingRender(indexOfHidden);
        list.get(finalIndex).getStack().push(shownStack.pop());
        renderWorking(hiddenStack, shownStack, position);
        renderOrdinaryCard(card, posX, posY);
        if (youWin()) {
            winner();
        }
    }

    /**
     * Recognizes where user put dragged items
     * @param stack is being dragged and dropped
     * @param posX stack is dropped on this X position
     * @param posY stack is dropped on this Y position
     * @param indexOfHidden stack is from shown stack on list.get(indexOfHidden) from which I am dragging
     */
    public void recognizeFieldFromWorking(Stack<Card> stack, double posX, double posY, int indexOfHidden) {
        Stack<Card> hiddenStack = list.get(indexOfHidden).getStack();
        Stack<Card> shownStack = list.get(indexOfHidden+7).getStack();
        int position = indexOfHidden - 2; // finding position of rendered stack 0-6

        if (stack.size() == 1) { // if user dropped stack of one card
            if (posX > 450/scope && posX < 570/scope) {
                if (posY > 20/scope && posY < 210/scope) { // user dropped stack on these coordinates
                    if (list.get(16).canPutCard(stack.get(0))) { // checking if this card can be on final stack
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 21, 16, indexOfHidden, hiddenStack, shownStack, list.get(16).getStack());
                        workingFinalCommunication(stack.get(0), 450/scope, 20/scope, shownStack, hiddenStack, indexOfHidden, 16);
                    } else {
                        System.out.println(list.get(1).getOnTopValue());
                        System.out.println(list.get(1).getOnTopColor());
                        System.out.println(list.get(1).getNumberOfCards());
                        System.out.println(list.get(10).getStack().get(0).getCardColor());
                        System.out.println(list.get(10).getStack().get(0).getCardID());
                        System.out.println(list.get(10).getStack().get(0).getCardValue());
                        System.out.println(list.get(10).getStack().get(0).getCardSymbol());
                        returnNotSuitableStack(stack);
                    }
                    return;
                }
            }
            if (posX > 605/scope && posX < 725/scope) {
                if (posY > 20/scope && posY < 210/scope) {
                    if (list.get(17).canPutCard(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 21, 17, indexOfHidden, hiddenStack, shownStack, list.get(17).getStack());
                        workingFinalCommunication(stack.get(0), 605/scope, 20/scope, shownStack, hiddenStack, indexOfHidden, 17);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                    return;
                }
            }
            if (posX > 760/scope && posX < 880/scope) {
                if (posY > 20/scope && posY < 210/scope) {
                    if (list.get(18).canPutCard(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 21, 18, indexOfHidden, hiddenStack, shownStack, list.get(18).getStack());
                        workingFinalCommunication(stack.get(0), 760/scope, 20/scope, shownStack, hiddenStack, indexOfHidden, 18);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                    return;
                }
            }
            if (posX > 915/scope && posX < 1035/scope) {
                if (posY > 20/scope && posY < 210/scope) {
                    if (list.get(19).canPutCard(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 21, 19, indexOfHidden, hiddenStack, shownStack, list.get(19).getStack());
                        workingFinalCommunication(stack.get(0), 915/scope, 20/scope, shownStack, hiddenStack, indexOfHidden, 19);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                    return;
                }
            }
        }
        if (posY > 250/scope) { // if user dropped stack bellow 250
            if (posX > 45/scope && posX < 165/scope) { // if on these coordinates
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) { // if both shown and hidden are empty
                    if (list.get(9).putOnlyK(stack.get(0))) { // only K can be there
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 22, indexOfHidden, 2, hiddenStack, shownStack, list.get(9).getStack());
                        workingWorkingCommunication(indexOfHidden, 2, stack, position, 0);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                } else if (list.get(9).canPutCard(stack.get(0))) { // if one of them is not empty
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 22, indexOfHidden, 2, hiddenStack, shownStack, list.get(9).getStack());
                    workingWorkingCommunication(indexOfHidden, 2, stack, position, 0);
                } else {
                    returnNotSuitableStack(stack);
                }

            } else if (posX > 200/scope && posX < 320/scope) {
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(10).putOnlyK(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 22, indexOfHidden, 3, hiddenStack, shownStack, list.get(10).getStack());
                        workingWorkingCommunication(indexOfHidden, 3, stack, position, 1);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                } else if (list.get(10).canPutCard(stack.get(0))) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 22, indexOfHidden, 3, hiddenStack, shownStack, list.get(10).getStack());
                    workingWorkingCommunication(indexOfHidden, 3, stack, position, 1);
                } else {
                    returnNotSuitableStack(stack);
                }

            } else if (posX > 355/scope && posX < 475/scope) {
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(11).putOnlyK(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 22, indexOfHidden, 4, hiddenStack, shownStack, list.get(11).getStack());
                        workingWorkingCommunication(indexOfHidden, 4, stack, position, 2);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                } else if (list.get(11).canPutCard(stack.get(0))) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 22, indexOfHidden, 4, hiddenStack, shownStack, list.get(11).getStack());
                    workingWorkingCommunication(indexOfHidden, 4, stack, position, 2);
                } else {
                    returnNotSuitableStack(stack);
                }

            } else if (posX > 510/scope && posX < 630/scope) {
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(12).putOnlyK(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 22, indexOfHidden, 5, hiddenStack, shownStack, list.get(12).getStack());
                        workingWorkingCommunication(indexOfHidden, 5, stack, position, 3);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                } else if (list.get(12).canPutCard(stack.get(0))) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 22, indexOfHidden, 5, hiddenStack, shownStack, list.get(12).getStack());
                    workingWorkingCommunication(indexOfHidden, 5, stack, position, 3);
                } else {
                    returnNotSuitableStack(stack);
                }

            } else if (posX > 665/scope && posX < 785/scope) {
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(13).putOnlyK(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 22, indexOfHidden, 6, hiddenStack, shownStack, list.get(13).getStack());
                        workingWorkingCommunication(indexOfHidden, 6, stack, position, 4);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                } else if (list.get(13).canPutCard(stack.get(0))) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 22, indexOfHidden, 6, hiddenStack, shownStack, list.get(13).getStack());
                    workingWorkingCommunication(indexOfHidden, 6, stack, position, 4);
                } else {
                    returnNotSuitableStack(stack);
                }

            } else if (posX > 820/scope && posX < 940/scope) {
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(14).putOnlyK(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 22, indexOfHidden, 7, hiddenStack, shownStack, list.get(14).getStack());
                        workingWorkingCommunication(indexOfHidden, 7, stack, position, 5);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                } else if (list.get(14).canPutCard(stack.get(0))) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 22, indexOfHidden, 7, hiddenStack, shownStack, list.get(14).getStack());
                    workingWorkingCommunication(indexOfHidden, 7, stack, position, 5);
                } else {
                    returnNotSuitableStack(stack);
                }

            } else if (posX > 975/scope && posX < 1095/scope) {
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(15).putOnlyK(stack.get(0))) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 22, indexOfHidden, 8, hiddenStack, shownStack, list.get(15).getStack());
                        workingWorkingCommunication(indexOfHidden, 8, stack, position, 6);
                    } else {
                        returnNotSuitableStack(stack);
                    }
                } else if (list.get(15).canPutCard(stack.get(0))) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 22, indexOfHidden, 8, hiddenStack, shownStack, list.get(15).getStack());
                    workingWorkingCommunication(indexOfHidden, 8, stack, position, 6);
                } else {
                    returnNotSuitableStack(stack);
                }
                
            } else {
                returnNotSuitableStack(stack);
            }
        } else {
            returnNotSuitableStack(stack);
        }
    }

    /**
     * When user dropped stack from working to other working stack
     * @param indexOld index of hidden stack, from which we are grabbing
     * @param indexNew index of hidden stack, to where we put stack
     * @param stack dragged stack
     * @param posOld position on which the old stack is 0-6
     * @param posNew position on which the new stack is 0-6
     */
    public void workingWorkingCommunication(int indexOld, int indexNew, Stack<Card> stack, int posOld, int posNew) {
        Stack<Card> hiddenStack = list.get(indexOld).getStack();
        Stack<Card> shownStack = list.get(indexOld + 7).getStack();
        Stack<Card> hiddenStackNew = list.get(indexNew).getStack();
        Stack<Card> shownStackNew = list.get(indexNew + 7).getStack();

        removeWorkingRender(indexOld); // removes old render
        removeWorkingRender(indexNew);

        int count = stack.size();
        list.get(indexNew+7).putStack(stack);
        list.get(indexOld+7).removeTopN(count);

        if (list.get(indexOld+7).getStack().isEmpty()) {
            list.get(indexOld+7).setOnTopColor("NONE");
        }

        renderWorking(hiddenStack, shownStack, posOld); // renders new render
        renderWorking(hiddenStackNew, shownStackNew, posNew);
    }

    /**
     *
     * @param card this card is being put back on its position
     */
    public void returnNotSuitableCard(Card card) {
        card.getFrontC().setTranslateX(card.getBaseX());
        card.getFrontC().setTranslateY(card.getBaseY());
    }
    
    /**
     *
     * @param shownStack this stack is being rendered
     */
    public void renderShownHelp(Stack<Card> shownStack) {

        int finalPosX = 200/scope;
        int finalPosY = 20/scope;
        int height = 190/scope;
        int width = 120/scope;
        int halfX = width/2;
        int halfY = height/2;

        int count = shownStack.size();

        for (int i = 0; i < count; i++) { // for every card in stack
            ImageView help = shownStack.get(i).getFrontC();
            help.setTranslateX(finalPosX);
            help.setTranslateY(finalPosY);
            help.setFitHeight(height);
            help.setFitWidth(width);
            pane.getChildren().add(help);

            Card card = shownStack.get(i);

            card.setBaseX(finalPosX); // setting its base X,Y position
            card.setBaseY(finalPosY);

            help.setOnMouseDragged(new EventHandler<MouseEvent>() { // when user drags card
                @Override
                public void handle(MouseEvent event) {

                    help.toFront();
                    help.setTranslateX(event.getX()+help.getTranslateX()-halfX);
                    help.setTranslateY(event.getY()+help.getTranslateY()-halfY);
                }
            });

            help.setOnMouseReleased(new EventHandler<MouseEvent>() { // when user releases card
                @Override
                public void handle(MouseEvent event) {

                        double posX = event.getX()+help.getTranslateX();
                        double posY = event.getY()+help.getTranslateY();

                        // finding where user released card
                        recognizeFieldFromHelp(card, posX, posY);
                }
            });
        }
    }

    /**
     * Renderes ordinary card without handlers
     * @param card is being rendered
     * @param posX on this X position
     * @param posY on this Y position
     */
    public void renderOrdinaryCard(Card card, int posX, int posY) {
        ImageView help = card.getFrontC();
        help.setTranslateX(posX);
        help.setTranslateY(posY);
        help.setFitHeight(190/scope);
        help.setFitWidth(120/scope);
        pane.getChildren().add(help);
        help.setOnMouseDragged(e -> e.consume());
        help.setOnMouseReleased(e -> e.consume());
    }

    /**
     * Card from help is being put to final
     * @param card this card is being put to final
     * @param index index to list on final stack
     * @param posX on this position is card being rendered
     */
    public void helpFinalCommunication(Card card, int index, int posX) {
        removeShownHelpRender();
        list.get(index).getStack().push(list.get(1).getStack().pop());
        renderOrdinaryCard(card, posX/scope, 20/scope);
        renderShownHelp(list.get(1).getStack());
        if (youWin()) {
            winner();
        }
    }

    /**
     * Recognizing where the card from help stack has been dropped
     * @param card dragged card
     * @param posX on this X position the card was dropped
     * @param posY on this Y position the card was dropped
     */
    public void recognizeFieldFromHelp(Card card, double posX, double posY) {
        if (posX > 450/scope && posX < 570/scope) {
            if (posY > 20/scope && posY < 210/scope) { // dropped between these coordinates
                if (list.get(16).canPutCard(card)) { // check if I can put the card to that final stack
                    System.out.println(card.getCardColor());
                    System.out.println(card.getCardID());
                    System.out.println(card.getCardValue());
                    System.out.println(card.getCardSymbol());
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 11, 16, 0, list.get(1).getStack(), list.get(16).getStack(), null);
                    helpFinalCommunication(card, 16, 450); // card is being put
                } else {
                    System.out.println(list.get(1).getOnTopValue());
                    System.out.println(list.get(1).getOnTopColor());
                    System.out.println(list.get(1).getNumberOfCards());
                    returnNotSuitableCard(card); // card is returned to original position
                }
                return;
            } 
        }
        if (posX > 605/scope && posX < 725/scope) {
            if (posY > 20/scope && posY < 210/scope) {
                if (list.get(17).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 11, 17, 0, list.get(1).getStack(), list.get(17).getStack(), null);
                    helpFinalCommunication(card, 17, 605);
                } else {
                    returnNotSuitableCard(card);
                }
                return;
            }
        }
        if (posX > 760/scope && posX < 880/scope) {
            if (posY > 20/scope && posY < 210/scope) {
                if (list.get(18).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 11, 18, 0, list.get(1).getStack(), list.get(18).getStack(), null);
                    helpFinalCommunication(card, 18, 760);
                } else {
                    returnNotSuitableCard(card);
                }
                return;
            }
        }
        if (posX > 915/scope && posX < 1035/scope) {
            if (posY > 20/scope && posY < 210/scope) {
                if (list.get(19).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 11, 19, 0, list.get(1).getStack(), list.get(19).getStack(), null);
                    helpFinalCommunication(card, 19, 915);
                } else {
                    returnNotSuitableCard(card);
                }
                return;
            }
        }

        if (posY > 250/scope) { // I dropped it bellow 250

            if (posX > 45/scope && posX < 165/scope) {
                Stack<Card> hiddenStack = list.get(2).getStack();
                Stack<Card> shownStack = list.get(9).getStack();
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) { // if both stacks are empty, I can put there only K
                    if (list.get(9).putOnlyK(card)) { // check if I am putting K
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 12, 0, 2, list.get(1).getStack(), list.get(2).getStack(), list.get(9).getStack());
                        helpWorkingComunication(2,0);
                    } else {
                        returnNotSuitableCard(card);
                    }
                } else if (list.get(9).canPutCard(card)) { // if one of them is not empty
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 12, 0, 2, list.get(1).getStack(), list.get(2).getStack(), list.get(9).getStack());
                    helpWorkingComunication(2,0);
                } else {
                    returnNotSuitableCard(card);
                }

            } else if (posX > 200/scope && posX < 320/scope) {
                Stack<Card> hiddenStack = list.get(3).getStack();
                Stack<Card> shownStack = list.get(10).getStack();
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(10).putOnlyK(card)) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 12, 0, 3, list.get(1).getStack(), list.get(3).getStack(), list.get(10).getStack());
                        helpWorkingComunication(3,1);
                    } else {
                        returnNotSuitableCard(card);
                    }
                } else if (list.get(10).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 12, 0, 3, list.get(1).getStack(), list.get(3).getStack(), list.get(10).getStack());
                    helpWorkingComunication(3,1);
                } else {
                    returnNotSuitableCard(card);
                }

            } else if (posX > 355/scope && posX < 475/scope) {
                Stack<Card> hiddenStack = list.get(4).getStack();
                Stack<Card> shownStack = list.get(11).getStack();
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(11).putOnlyK(card)) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 12, 0, 4, list.get(1).getStack(), list.get(4).getStack(), list.get(11).getStack());
                        helpWorkingComunication(4,2);
                    } else {
                        returnNotSuitableCard(card);
                    }
                } else if (list.get(11).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 12, 0, 4, list.get(1).getStack(), list.get(4).getStack(), list.get(11).getStack());
                    helpWorkingComunication(4,2);
                } else {
                    returnNotSuitableCard(card);
                }

            } else if (posX > 510/scope && posX < 630/scope) {
                Stack<Card> hiddenStack = list.get(5).getStack();
                Stack<Card> shownStack = list.get(12).getStack();
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(12).putOnlyK(card)) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 12, 0, 5, list.get(1).getStack(), list.get(5).getStack(), list.get(12).getStack());
                        helpWorkingComunication(5,3);
                    } else {
                        returnNotSuitableCard(card);
                    }
                } else if (list.get(12).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 12, 0, 5, list.get(1).getStack(), list.get(5).getStack(), list.get(12).getStack());
                    helpWorkingComunication(5,3);
                } else {
                    returnNotSuitableCard(card);
                }

            } else if (posX > 665/scope && posX < 785/scope) {
                Stack<Card> hiddenStack = list.get(6).getStack();
                Stack<Card> shownStack = list.get(13).getStack();
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(13).putOnlyK(card)) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 12, 0, 6, list.get(1).getStack(), list.get(6).getStack(), list.get(13).getStack());
                        helpWorkingComunication(6,4);
                    } else {
                        returnNotSuitableCard(card);
                    }
                } else if (list.get(13).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 12, 0, 6, list.get(1).getStack(), list.get(6).getStack(), list.get(13).getStack());
                    helpWorkingComunication(6,4);
                } else {
                    returnNotSuitableCard(card);
                }

            } else if (posX > 820/scope && posX < 940/scope) {
                Stack<Card> hiddenStack = list.get(7).getStack();
                Stack<Card> shownStack = list.get(14).getStack();
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(14).putOnlyK(card)) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 12, 0, 7, list.get(1).getStack(), list.get(7).getStack(), list.get(14).getStack());
                        helpWorkingComunication(7,5);
                    } else {
                        returnNotSuitableCard(card);
                    }
                } else if (list.get(14).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 12, 0, 7, list.get(1).getStack(), list.get(7).getStack(), list.get(14).getStack());
                    helpWorkingComunication(7,5);
                } else {
                    returnNotSuitableCard(card);
                }

            } else if (posX > 975/scope && posX < 1095/scope) {
                Stack<Card> hiddenStack = list.get(8).getStack();
                Stack<Card> shownStack = list.get(15).getStack();
                if (hiddenStack.isEmpty() && shownStack.isEmpty()) {
                    if (list.get(15).putOnlyK(card)) {
                        shiftUndoStackIn();
                        Solitaire.undo = new Undo(game, 12, 0, 8, list.get(1).getStack(), list.get(8).getStack(), list.get(15).getStack());
                        helpWorkingComunication(8,6);
                    } else {
                        returnNotSuitableCard(card);
                    }
                } else if (list.get(15).canPutCard(card)) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 12, 0, 8, list.get(1).getStack(), list.get(8).getStack(), list.get(15).getStack());
                    helpWorkingComunication(8,6);
                } else {
                    returnNotSuitableCard(card);
                }
                
            } else {
                returnNotSuitableCard(card);
            }
        } else {
            returnNotSuitableCard(card);
        }
    }

    /**
     * User dragged from help stack to working stack
     * @param indexOfWorkingHidden index of hidden stack to list
     * @param position position where user dropped card
     */
    public void helpWorkingComunication(int indexOfWorkingHidden, int position) {
        Stack<Card> shownStack = list.get(indexOfWorkingHidden+7).getStack();
        Stack<Card> hiddenStack = list.get(indexOfWorkingHidden).getStack();

        removeShownHelpRender(); // removing old render
        Card helpCard = list.get(1).getStack().pop();
        renderShownHelp(list.get(1).getStack());
        removeWorkingRender(indexOfWorkingHidden);

        shownStack.push(helpCard); // creating new render
        renderWorking(hiddenStack, shownStack, position);
    }

    /**
     * Renders hidden help stack
     * @param hiddenStack stack that is being rendered
     */
    public void renderHiddenHelp(Stack<Card> hiddenStack) {

        ImageView imageView = new ImageView(); // return arrow at the bottom of stack
        Image image = new Image("file:lib/img/return.png");
        imageView.setImage(image);
        imageView.setFitHeight(190/scope);
        imageView.setFitWidth(120/scope);
        imageView.setTranslateX(46/scope);
        imageView.setTranslateY(20/scope);
        pane.getChildren().add(imageView);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() { // when user clicks on it, stack is being rerendered
            @Override
            public void handle(MouseEvent event) {
                shiftUndoStackIn();
                Solitaire.undo = new Undo(game, 32, 0, 0, list.get(1).getStack(), list.get(0).getStack(), null);

                Stack<Card> stackHH = list.get(0).getStack();
                Stack<Card> stackHS = list.get(1).getStack();
                int number = stackHS.size();
                for (int i = 0; i < number; i++) {
                    Card card = stackHS.pop();
                    pane.getChildren().remove(card.getFrontC());
                    stackHH.push(card);
                }
                pane.getChildren().remove(imageView);
                renderHiddenHelp(stackHH);
            }
        });

        int finalPosX = 46/scope;
        int finalPosY = 20/scope;
        int height = 190/scope;
        int width = 120/scope;

        int number = hiddenStack.size();
        for (int i = 0; i < number; i++) { // for each card in stack
            ImageView help = hiddenStack.get(i).getBackC();
            help.setTranslateX(finalPosX);
            help.setTranslateY(finalPosY);
            help.setFitHeight(height);
            help.setFitWidth(width);
        
            pane.getChildren().add(help);

            help.setOnMouseClicked(new EventHandler<MouseEvent>() { // if clicked, it is sent to shown help stack

                @Override
                public void handle(MouseEvent event) {
                    shiftUndoStackIn();
                    Solitaire.undo = new Undo(game, 31, 0, 0, list.get(0).getStack(), list.get(1).getStack(), null);
                    Card card = hiddenStack.pop();
                    pane.getChildren().remove(help);
                    removeShownHelpRender();
                    list.get(1).getStack().push(card);
                    renderShownHelp(list.get(1).getStack());
                }
            });
        }

    }

    /**
     * Realises shallow copy of list
     * @return list of GeneralStack objects
     */
    public List<GeneralStack> getList() {
        return this.list;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    // for adding to undo pipeline
    private void shiftUndoStackIn() {
        if (Solitaire.numberOfUndo != 5) {
            Solitaire.numberOfUndo++;
        }

        Solitaire.undo4 = Solitaire.undo3;
        Solitaire.undo3 = Solitaire.undo2;
        Solitaire.undo2 = Solitaire.undo1;
        Solitaire.undo1 = Solitaire.undo;
    }


    // for testing
    public void showFinal() {
        for (int i = 0; i < list.size(); i++) {
            Stack<Card> stack = list.get(i).getStack();
            for (int j = 0; j < stack.size(); j++) {
                System.out.print(stack.get(j).toString() + " " + stack.get(j).getCardID() + " ");
            }
            System.out.println("");
        }
    }

    // for testing
    public void showStack(Stack<Card> stack) {
        for (int i = 0; i < stack.size(); i++) {
            System.out.print(stack.get(i).toString() + " ");
            if (i%10 == 9) {
                System.out.println("");
            }
        }
        System.out.println("");
    }

    public void setList(List<GeneralStack> list) {
        this.list = list;
        this.initializeGame();
    }
}
