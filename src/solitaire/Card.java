/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import javafx.scene.image.ImageView;

/**
 *
 * @author lukasdekret
 */
public interface Card {

    public static enum Symbol {
        SPADES, CLUBS, DIAMONDS, HEARTS, NONE;
    }

    public boolean similarColorTo(Card card);
    public boolean sameSymbolAs(Card card);
    
    public int getCardValue(); 
    public void setCardValue(int cardValue);
    public String getCardColor();
    public void setCardColor(String cardColor);
    public Card.Symbol getCardSymbol();
    public void setCardSymbol(Card.Symbol cardSymbol);
    public int getCardID();
    public void setCardID(int cardID);
    public String getName();
    public void setName(String name);
    public boolean getFaceUp();
    public void setFaceUp(boolean faceUp);
    public ImageView getBackC();
    public ImageView getFrontC();
    public double getBaseX();
    public double getBaseY();
    public void setBaseX(double baseX);
    public void setBaseY(double baseY);

    public void setBackCard();
    public void setFrontC();

}
