import java.io.Serializable;

public class Card  implements Serializable {
    private static final long serialVersionUID = 1L;
    char suit;
    int value;
    Card(char suit, int value){
        this.suit = suit;
        this.value = value;
    }

    public String stringSuit(){
        if (suit == 'C') return "c";
        else if (suit == 'D') return "d";
        else if (suit == 'H') return "h";
        else return "s";
    }

    public char getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        String valueStr;
        switch(value) {
            case 11:
                valueStr = "J";
                break;
            case 12:
                valueStr = "Q";
                break;
            case 13:
                valueStr = "K";
                break;
            case 14:
                valueStr = "A";
                break;
            default:
                valueStr = String.valueOf(value);
        }
        return valueStr + stringSuit();
    }

    public String getImagePath() {
        return "@.../images/" + stringSuit().toLowerCase() + value + ".png";
    }
}
