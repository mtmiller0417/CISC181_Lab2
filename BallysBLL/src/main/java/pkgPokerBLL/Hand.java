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
	
	
	public static boolean isAce(ArrayList<Card> cards)
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
	
	public static boolean isStraight(Hand h, HandScore hs)
	{
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()) &&
				(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()) &&
				(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()) &&
				(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr()) - 1 == (h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()).geteRank().getiRankNbr()))
		{			
			return true;
		}
		return false;
	}
	
	
	
	//TODO: Implement This Method
	public static boolean isHandRoyalFlush(Hand h, HandScore hs)
	{
		return false;
	}
	
	//TODO: Implement This Method
	public static boolean isHandStraightFlush(Hand h, HandScore hs)
	{
		return false;
	}		//TODO: Implement This Method
	public static boolean isHandFlush(Hand h, HandScore hs)
	{
		return false;
	}
	//TODO: Implement This Method
	public static boolean isHandFourOfAKind(Hand h, HandScore hs)
	{
		return false;
	}	
	
		
	
	//TODO: Implement This Method
	public static boolean isHandStraight(Hand h, HandScore hs)
	{
		boolean isHandStraight = false;
		boolean isAce = isAce(h.getCardsInHand());
		if(isAce)
		{ 
			if(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FIVE )
			{
				//Check if it's a straight from 5 to 2, only this specific case
				if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == eRank.FIVE) &&
						(h.getCardsInHand().get(eCardNo.SecondCard.getCardNo()).geteRank() == eRank.FOUR) &&
						(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == eRank.THREE) &&
						(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank() == eRank.TWO))
						{
							isHandStraight = true;	
							hs.setHandStrength(eHandStrength.Straight);
							hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());// Only a high hand for straight bc it requires all 5 cards
						}
				else
				{
					isHandStraight = true;
				}
			}
			else
			{
				//Check if it's a general straight?
				if (isStraight(h, hs) == true)
				{
					isHandStraight = true;				
					hs.setHandStrength(eHandStrength.Straight);
					hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());// Only a high hand for straight bc it requires all 5 cards
				}
				else
				{
					isHandStraight = false;
				}
			}
		}
		else if(!isAce)
		{
			//Check if it's a general straight?
			if (isStraight(h, hs) == true)
			{
				isHandStraight = true;				
				hs.setHandStrength(eHandStrength.Straight);
				hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());// Only a high hand for straight bc it requires all 5 cards
			}
		}
		return isHandStraight;
	}	
	
	//TODO: Implement This Method
	public static boolean isHandThreeOfAKind(Hand h, HandScore hs)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		
		boolean isThreeOfAKind = false;
		//if first and third the same and fourth card is greater than fifth
		if ((h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.ThirdCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()).geteRank().getiRankNbr() != h.getCardsInHand()
						.get(eCardNo.FifthCard.getCardNo()).geteRank().getiRankNbr())){
			isThreeOfAKind = true;
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(null);//?
			
			kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FourthCard.getCardNo()));
			
			hs.setKickers(kickers);
			
		}
		
		else if ((h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank() == h.getCardsInHand()
				.get(eCardNo.FifthCard.getCardNo()).geteRank())
				&& (h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank().getiRankNbr() != h.getCardsInHand()
						.get(eCardNo.SecondCard.getCardNo()).geteRank().getiRankNbr())) {
			isThreeOfAKind = true;
			hs.setHandStrength(eHandStrength.ThreeOfAKind);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.setLoHand(null);
			
			kickers.add(h.getCardsInHand().get(0));
			kickers.add(h.getCardsInHand().get(1));
			
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
						.get(eCardNo.FourthCard.getCardNo()).geteRank())) {
			isTwoPair = true;
			hs.setHandStrength(eHandStrength.TwoPair);
			hs.setHiHand(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()).geteRank());
			hs.setLoHand(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()).geteRank());
			hs.getKickers().add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
			kickers.add(h.getCardsInHand().get(eCardNo.FifthCard.getCardNo()));
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
				hs.getKickers().add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
				kickers.add(h.getCardsInHand().get(eCardNo.ThirdCard.getCardNo()));
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
				hs.getKickers().add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
				
				kickers.add(h.getCardsInHand().get(eCardNo.FirstCard.getCardNo()));
				
			}		
		
		return false;
	}
	
	//TODO: Implement This Method
	public static boolean isHandPair(Hand h, HandScore hs)
	{
		return false;
	}	
	
	//TODO: Implement This Method
	public static boolean isHandHighCard(Hand h, HandScore hs)
	{
		return false;
	}	
	
	//TODO: Implement This Method
	public static boolean isAcesAndEights(Hand h, HandScore hs)
	{
		boolean isAcesAndEights = false;
		if(Hand.isHandTwoPair(h, hs))
		{
			if((hs.getHiHand() == eRank.ACE) && 
					(hs.getLoHand() == eRank.EIGHT))
			{
				isAcesAndEights = true;
			}
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
}
