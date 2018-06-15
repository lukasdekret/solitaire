/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.solitaire;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author lukasdekret
 */
public class Save {

    Scanner file;

    public Save(Game main, Game game1, Game game2, Game game3, Game game4, int count, String filename) throws FileNotFoundException {
        try{
        PrintWriter writer = new PrintWriter(filename, "UTF-8");
        writer.println(count);
        if (count == 1) {
           printGame(main, writer);
        } else if (count == 2) {
           printGame(game1, writer);
           printGame(game2, writer);
        } else if (count == 3) {
           printGame(game1, writer);
           printGame(game2, writer);
           printGame(game3, writer);
        } else {
           printGame(game1, writer);
           printGame(game2, writer);
           printGame(game3, writer);
           printGame(game4, writer);
        }


        writer.close();
        } catch (IOException e) {
            // do something
        }
    }

    private void printGame(Game main, PrintWriter writer) {
        
        int count1 = main.getList().get(0).getStack().size();
        writer.println(count1);
        Stack<Card> s1 = main.getList().get(0).getStack();
        for (int i = 0; i < count1; i++) {
            writer.println(cardToString(s1.get(i)));
        }

        count1 = main.getList().get(1).getStack().size();
        writer.println(count1);
        s1 = main.getList().get(1).getStack();
        for (int i = 0; i < count1; i++) {
            writer.println(cardToString(s1.get(i)));
        }

        for (int i = 2; i < 9; i++) {
            count1 = main.getList().get(i).getStack().size();
            writer.println(count1);
            //writer.println(i+5);
            s1 = main.getList().get(i).getStack();
            for (int j = 0; j < count1; j++) {
                writer.println(cardToString(s1.get(j)));
            }
        }

        for (int i = 9; i < 16; i++) {
            count1 = main.getList().get(i).getStack().size();
            writer.println(count1);
            //writer.println(i-2);
            s1 = main.getList().get(i).getStack();
            GeneralStack s2 = main.getList().get(i);
            writer.println(s2.getOnTopValue());
            writer.println(s2.getOnTopColor());
            for (int j = 0; j < count1; j++) {
                writer.println(cardToString(s1.get(j)));
            }
        }

        for (int i = 16; i < 20; i++) {
            count1 = main.getList().get(i).getStack().size();
            writer.println(count1);
            //writer.println(i-13);
            s1 = main.getList().get(i).getStack();
            GeneralStack s2 = main.getList().get(i);
            writer.println(symbolOfFinal(s2));
            for (int j = 0; j < count1; j++) {
                writer.println(cardToString(s1.get(j)));
            }
        }
    }

    private String symbolOfFinal(GeneralStack g) {
        Card.Symbol symbol = g.getStackSymbol();
        switch (symbol) {
            case HEARTS:
                return "H";
            case SPADES:
                return "S";
            case DIAMONDS:
                return "D";
            case CLUBS:
                return "C";
            case NONE:
                return "N";
            default:
                return "";
        }
    }

    private String cardToString(Card card) {
        int value = card.getCardValue();

        int ID = card.getCardID();

        Card.Symbol symbol = card.getCardSymbol();
        String name;
        switch (symbol) {
            case HEARTS:
                name = "H";
                break;
            case SPADES:
                name = "S";
                break;
            case DIAMONDS:
                name = "D";
                break;
            case CLUBS:
                name = "C";
                break;
            case NONE:
                name = "N";
                break;
            default:
                name = "";
        }
        name = value + name + ID;
        return name;
    }
}
