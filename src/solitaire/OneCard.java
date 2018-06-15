/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author lukasdekret
 */
public class OneCard implements Card {

    private int cardValue; // value of the card
    private String cardColor; // color of the card RED/BLACK TODO --> NONE??
    private Card.Symbol cardSymbol; // symbol of the card CLUBS, HEARTS, DIAMONDS, SPADES
    private int cardID; // ID of the card 
    private boolean faceUp; // if the card is face up or not
    private ImageView backC; // representation of back card
    private ImageView frontC; // representation of front card

    private double baseX, baseY;

    private String name; // string representation of string

    public OneCard(int value, Card.Symbol symbol, int cardID, boolean faceUp) {
        this.cardValue = value;
        this.cardSymbol = symbol;
        this.faceUp = faceUp;
        this.baseX = 0;
        this.cardID = cardID;
        this.baseY = 0;

        frontC = new ImageView();
        Image image = new Image("file:lib/img/" + cardID + ".jpg");
        frontC.setImage(image);

        if (symbol == Card.Symbol.CLUBS || symbol == Card.Symbol.SPADES) {
            this.cardColor = "BLACK";
        } else {
            this.cardColor = "RED";
        }
        this.name = this.toString();
        setBackCard();
    }

    /**
     * Sets the background image for card
     */
    @Override
    public void setBackCard() {
        backC = new ImageView();
        Image img = new Image("file:lib/img/back.jpg");
        backC.setImage(img);
    }

    /**
     * If two cards have similar color
     * @param card one of the card
     * @return true if they have similar color, false if not
     */
    @Override
    public boolean similarColorTo(Card card) {
        return this.cardColor.equals(card.getCardColor());
    }

    /**
     * If two cards have same symbol
     * @param card one of the card
     * @return true if they have same symbols, false if not
     */
    @Override
    public boolean sameSymbolAs(Card card) {
        return this.cardSymbol == card.getCardSymbol();
    }

    @Override
    public double getBaseX() {
        return this.baseX;
    }

    @Override
    public double getBaseY() {
        return this.baseY;
    }

    @Override
    public void setBaseX(double baseX) {
        this.baseX = baseX;
    }

    @Override
    public void setBaseY(double baseY) {
        this.baseY = baseY;
    }

    @Override
    public int getCardValue() {
        return cardValue;
    }

    @Override
    public void setCardValue(int cardValue) {
        this.cardValue = cardValue;
    }

    @Override
    public String getCardColor() {
        return cardColor;
    }

    @Override
    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }

    @Override
    public Card.Symbol getCardSymbol() {
        return cardSymbol;
    }

    @Override
    public void setCardSymbol(Card.Symbol cardSymbol) {
        this.cardSymbol = cardSymbol;
    }

    @Override
    public int getCardID() {
        return cardID;
    }

    @Override
    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean getFaceUp() {
        return this.faceUp;
    }

    @Override
    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.cardValue;
        hash = 59 * hash + Objects.hashCode(this.cardSymbol);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OneCard other = (OneCard) obj;
        if (this.cardValue != other.getCardValue()) {
            return false;
        }
        if (this.cardSymbol != other.getCardSymbol()) {
            return false;
        }
        return true;
    }



    // pomocne na vypisy
    private String correctValue(int value) {
        if (value == 1) {
            return "A";
        } else if (value <= 10) {
            return value + "";
        } else if (value == 11) {
            return "J";
        } else if (value == 12) {
            return "Q";
        } else {
            return "K";
        }
    }

    @Override
    public String toString() {

        switch (this.cardSymbol) {
            case HEARTS:
                return correctValue(this.cardValue) + "(H)";
            case SPADES:
                return correctValue(this.cardValue) + "(S)";
            case DIAMONDS:
                return correctValue(this.cardValue) + "(D)";
            case CLUBS:
                return correctValue(this.cardValue) + "(C)";
            case NONE:
                return "(N)";
            default:
                return null;
        }
    }

    @Override
    public ImageView getBackC() {
        return this.backC;
    }

    @Override
    public ImageView getFrontC() {
        return this.frontC;
    }

    @Override
    public void setFrontC() {
        frontC = new ImageView();
        Image image = new Image("file:lib/img/" + cardID + ".jpg");
        frontC.setImage(image);
    }

    public static enum Symbol {
        SPADES, CLUBS, DIAMONDS, HEARTS, NONE;
    }
}
