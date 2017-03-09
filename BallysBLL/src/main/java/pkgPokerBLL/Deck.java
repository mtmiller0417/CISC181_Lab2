package pkgPokerBLL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import pkgPokerEnum.*;
//import pkgPokerEnum.eRank;

public class Deck {

	private UUID DeckID;
	private ArrayList<Card> DeckCards = new ArrayList<Card>();
	
	public Deck()
	{
		//TODO: Implement This Constructor (no-arg Deck should build up a deck with 52 cards)
		
		for (eSuit Suit : eSuit.values())
		{
			for (eRank Rank : eRank.values()) 
			{
				Card x = new Card(Rank, Suit);
				DeckCards.add(x);
				//System.out.println(Rank.getiRankNbr());
			}
		}
		
		Collections.shuffle(DeckCards);

		
	}
	
	public Card DrawCard()
	{
		//	TODO: Implement this method... should draw a card from the deck.
		
		Card k = DeckCards.remove(0);
		return k;
	}
}
