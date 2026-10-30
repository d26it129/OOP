public class Driver {
    public static void main(String[] args) {
        Card[] incoming = { new Card("Ace", "Spades"), new Card("Queen", "Hearts"),
                            new Card("Ten", "Clubs"), new Card("Ace", "Spades"),
                            new Card("Two", "Diamonds") };
        Card[] deck = new Card[incoming.length];
        int count = 0;
        for (Card c : incoming) {
            boolean duplicate = false;
            for (int i = 0; i < count; i++) {
                if (deck[i].equals(c)) { duplicate = true; break; }
            }
            if (duplicate) {
                System.out.println("Duplicate found: " + c);
                break;
            }
            deck[count++] = c;
            System.out.println("Added: " + c);
        }
    }
}
