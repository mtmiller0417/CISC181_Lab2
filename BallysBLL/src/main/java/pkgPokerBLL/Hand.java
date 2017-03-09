package pkgPokerBLL;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import pkgPokerEnum.eCardNo;
import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;



public class Hand {

	private UUID HandID;
	private boolean bIsScored;
	private HandScore HS;
	private ArrayList<Card> CardsInHand = new ArrayList<Card>();
	
	public Hand()
	{
		
	}
	
	public void AddCardToHand(Card c)
	{
		CardsInHand.add(c);
	}

	public ArrayList<Card> getCardsInHand() {
		return CardsInHand;
	}
	
	public HandScore getHandScore()
	{
		return HS;
	}
	
	public Hand EvaluateHand()
	{
		Hand h = Hand.EvaluateHand(this);
		return h;
	}
	
	private static Hand EvaluateHand(Hand h)  {

		Collections.sort(h.getCardsInHand());
		
		//	Another way to sort
		//	Collections.sort(h.getCardsInHand(), Card.CardRank);
		
		HandScore hs = new HandScore();
		try {
			Class<?> c = Class.forName("pkgPokerBLL.Hand");

			for (eHandStrength hstr : eHandStrength.values()) {
				Class[] cArg = new Class[2];
				cArg[0] = pkgPokerBLL.Hand.class;
				cArg[1] = pkgPokerBLL.HandScore.class;

				Method meth = c.getMethod(hstr.getEvalMethod(), cArg);
				Object o = meth.invoke(null, new Object[] { h, hs });

				// If o = true, that means the hand evaluated- skip the rest of
				// the evaluations
				if ((Boolean) o) {
					break;
				}
			}

			h.bIsScored = true;
			h.HS = hs;

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return h;
	}
	
	
	
	//HOW TO FIND SUITE OF A CARD
/*	eSuit suit = h.getCardsInHand().get(0).geteSuitNbr();*/	
	
	
	public static boolean isFlush(Hand h, HandScore hs)//Helper Method
	{
		boolean isFlush =  false;
		if((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit().getiSuitNbr()) == (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteSuit().getiSuitNbr()) &&
				(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteSuit().getiSuitNbr())  == (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteSuit().getiSuitNbr()) &&
				(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteSuit().getiSuitNbr()) == (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteSuit().getiSuitNbr())  &&
				(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteSuit().getiSuitNbr())  == (h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteSuit().getiSuitNbr()))
		{
			isFlush = true;
			return true;
		}
		//(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteSuit().getiSuitNbr())
		return isFlush;
	}
	
	//TODO: Implement This Method
	public static boolean isHandRoyalFlush(Hand h, HandScore hs)
	{
		boolean isRoyalFlush = false;
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE ) &&
				(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING) &&
				(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == eRank.QUEEN) &&
				(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == eRank.JACK)&&
				(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.TEN))
		{
			if(isFlush(h, hs))
			{
						isRoyalFlush = true;	
						hs.setHandStrength(eHandStrength.RoyalFlush);
						//hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());// Only a high hand for straight bc it requires all 5 cards
			}
					
		}
		else
		{
			isRoyalFlush = false;
		}
		return isRoyalFlush;
	}
	
	//TODO: Implement This Method
	public static boolean isHandStraightFlush(Hand h, HandScore hs)
	{
		boolean isStraightflush = false;
		if((isFlush(h, hs) && isStraight(h.getCardsInHand())) == true)
		{
			isStraightflush = true;
			hs.setHandStrength(eHandStrength.StraightFlush);
			//hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}
		return isStraightflush;
	}		
	
	//TODO: Implement This Method
	public static boolean isHandFlush(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		boolean isFlush = false;
		if (isFlush(h, hs))
		{
			isFlush = true;
			hs.setHandStrength(eHandStrength.Flush);
			//hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}
		
		return isFlush;
	}	
	
	
	
	//TODO: Implement This Method
	public static boolean isHandFourOfAKind(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		
		boolean isFourOfAKind = false;
		//if first and fourth are the same rank
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank()))
		{
			isFourOfAKind = true;
			hs.setHandStrength(eHandStrength.FourOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));		
			hs.setKickers(kickers);
			
		}
		
		else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank()))				
			{
			isFourOfAKind = true;
			hs.setHandStrength(eHandStrength.FourOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			//hs.setLoHand(null);
			
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));				
			hs.setKickers(kickers);
			
			}
		return isFourOfAKind;
	}	
		
	//TODO: Implement This Method
	public static boolean isHandStraight(Hand h, HandScore hs)
	{
		boolean isHandStraight = false;
		boolean isAce = isAce(h.getCardsInHand());
		
		if( (isStraight(h.getCardsInHand()) == true) && !isAce)
		{
			isHandStraight = true;	
			hs.setHandStrength(eHandStrength.Straight);
			//hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());// Only a high hand for straight b/c it requires all 5 cards
		}
		else if(isAce && isStraight(h.getCardsInHand()))
		{
			
			isHandStraight = true;	
			hs.setHandStrength(eHandStrength.Straight);
			//hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());// Only a high hand for straight b/c it requires all 5 cards
		
		}
		else
		{
			isHandStraight = false;
		}
		return isHandStraight;
	}	
	
	public static boolean isAce(ArrayList<Card> cards)//Helper Method
	{
		//returns true if the first card in the hand is an ace
		if (cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public static boolean isStraight(ArrayList<Card> cards)// Helper Method
	{
		boolean isStraight = false;
	boolean isAce = isAce(cards);
		
		
		if ((cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE) &&
				(cards.get(eCardNo.ThirdCard.getCardNo()).geteRank() == eRank.FOUR) &&
				(cards.get(eCardNo.FourthCard.getCardNo()).geteRank() == eRank.THREE) &&
				(cards.get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.TWO)&&
				(cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE))
		{
			isStraight = true;		
		}
		else if((cards.get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.ACE) &&
				(cards.get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.KING) &&
				(cards.get(eCardNo.ThirdCard.getCardNo()).geteRank() == eRank.QUEEN) &&
				(cards.get(eCardNo.FourthCard.getCardNo()).geteRank() == eRank.JACK)&&
				(cards.get(eCardNo.FifthCard.getCardNo()).geteRank() == eRank.TEN))
		{
			isStraight = true;
		}
			
		else if((cards.get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()) &&
				(cards.get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()) &&
				(cards.get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()) &&
				(cards.get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (cards.get(eCardNo.FifthCard.getCardNo()).geteRank().getiRankNbr()) &&
				isAce != true)
		{
			isStraight = true;
		}
		
		
		
		return isStraight;
		
	}
	
	//TODO: Implement This Method
	public static boolean isHandThreeOfAKind(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		
		boolean isThreeOfAKind = false;
		//if first and third the same and fourth card is greater than fifth
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()))
		{
			isThreeOfAKind = true;
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);//?
			
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			
			hs.setKickers(kickers);
			
		}
		
		else if ((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank())) 
		{
			isThreeOfAKind = true;
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(null);
			
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));			
			hs.setKickers(kickers);
			
		}
		else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank())) 
		{
			isThreeOfAKind = true;
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(null);
			
			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));			
			hs.setKickers(kickers);
			
		}
		return isThreeOfAKind;
	}
	
	//TODO: Implement This Method
	public static boolean isHandTwoPair(Hand h, HandScore hs)
	{		
		ArrayList<Card> kickers = new ArrayList<Card>();
		boolean isTwoPair = false;
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FourthCard.getCardNo()).geteRank())) 
		{
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.setKickers(kickers);
		}
		
		else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) 
		{
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.setKickers(kickers);
		}
		
		else if ((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) 
		{
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			hs.setKickers(kickers);
				
		}		
		
		return isTwoPair;
	}
	
	//TODO: Implement This Method
	public static boolean isHandPair(Hand h, HandScore hs)
	{
		boolean isHandPair = false;
		//5 5 3 2 1
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank()))
		{
			isHandPair = true;
			hs.setHandStrength(eHandStrength.Pair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);

			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

			hs.setKickers(kickers);

		}
		//6 5 5 3 2
		else if((h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank()))

		{
			isHandPair = true;
			hs.setHandStrength(eHandStrength.Pair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank());
			hs.setLoHand(null);

			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

			hs.setKickers(kickers);
		}
		//7 6 5 5 3
		else if((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FourthCard.getCardNo()).geteRank()))

		{
			isHandPair = true;
			hs.setHandStrength(eHandStrength.Pair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(null);

			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));

			hs.setKickers(kickers);
		}
		//8 7 6 5 5
		else if((h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank()))

			{
			isHandPair = true;
			hs.setHandStrength(eHandStrength.Pair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
			hs.setLoHand(null);

			kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));

			hs.setKickers(kickers);
			}
		return isHandPair;
	}
		
	
	//TODO: Implement This Method
	public static boolean isAcesAndEights(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		boolean isAcesAndEights = false;
		if(Hand.isHandTwoPair(h, hs))
		{
			if((hs.getHiHand() == eRank.ACE) && 
					(hs.getLoHand() == eRank.EIGHT))
			{
				isAcesAndEights = true;
				hs.setHandStrength(eHandStrength.AcesAndEights);
				hs.setHiHand(eRank.ACE);
				hs.setLoHand(eRank.EIGHT);
				
				/*kickers.add(hs.getKickers().get(eCardNo.ThirdCard.getCardNo()));
				hs.setKickers(kickers);
*/			}
		}
		return isAcesAndEights;
	}	
	
	
	public static boolean isHandFullHouse(Hand h, HandScore hs) {

		boolean isFullHouse = false;
		
		ArrayList<Card> kickers = new ArrayList<Card>();
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank());
		} else if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.SecondCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank())) {
			isFullHouse = true;
			hs.setHandStrength(eHandStrength.FullHouse);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		}

		return isFullHouse;

	}
	
	//TODO: Implement This Method
	public static boolean isHandHighCard(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		boolean isHighCard = false;
		h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank();
		isHighCard = true;
		
		hs.setHandStrength(eHandStrength.HighCard);
		hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
		kickers.add(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
		kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
		hs.setKickers(kickers);
		return isHighCard;
		
		
	}
}
