package pkgPokerBLL;

import static org.junit.Assert.*;

import org.junit.Test;

import pkgPokerEnum.eHandStrength;
import pkgPokerEnum.eRank;
import pkgPokerEnum.eSuit;

public class TestHands {

	@Test
	public void TestFullHouse() {
		
		Hand h = new Hand();
		h.AddCardToHand(new Card(eRank.THREE,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.THREE,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.FOUR,eSuit.CLUBS));
		h.AddCardToHand(new Card(eRank.FOUR,eSuit.DIAMONDS));
		h.AddCardToHand(new Card(eRank.FOUR,eSuit.SPADES));		
		h.EvaluateHand();
		
		//	Hand better be a full house
		assertEquals(eHandStrength.FullHouse.getHandStrength(),
				h.getHandScore().getHandStrength().getHandStrength());
		
		//	HI hand better be 'Four'
		assertEquals(eRank.FOUR.getiRankNbr(),
				h.getHandScore().getHiHand().getiRankNbr());
		
		//	LO hand better be 'Three'
		assertEquals(eRank.THREE.getiRankNbr(),
				h.getHandScore().getLoHand().getiRankNbr());
		
		//	Full House has no kickers.
		assertEquals(0,h.getHandScore().getKickers().size());
		
		Hand hh = new Hand();
		hh.AddCardToHand(new Card(eRank.THREE,eSuit.CLUBS));
		hh.AddCardToHand(new Card(eRank.THREE,eSuit.DIAMONDS));
		hh.AddCardToHand(new Card(eRank.THREE,eSuit.CLUBS));
		hh.AddCardToHand(new Card(eRank.FOUR,eSuit.DIAMONDS));
		hh.AddCardToHand(new Card(eRank.FOUR,eSuit.SPADES));		
		hh.EvaluateHand();
		
		assertEquals(eHandStrength.FullHouse.getHandStrength(),
				hh.getHandScore().getHandStrength().getHandStrength());
			//HI hand better be 'Four'
			assertEquals(eRank.FOUR.getiRankNbr(),
					h.getHandScore().getHiHand().getiRankNbr());
			
			//	LO hand better be 'Three'
			assertEquals(eRank.THREE.getiRankNbr(),
					h.getHandScore().getLoHand().getiRankNbr());
			
			//	Full House has no kickers.
			assertEquals(0,h.getHandScore().getKickers().size());
	}
	
	@Test
	public void TestRoyalFlush() 
	{
		// Testing Royal Flush below
		Hand a = new Hand();
		a.AddCardToHand(new Card(eRank.JACK,eSuit.SPADES));
		a.AddCardToHand(new Card(eRank.QUEEN,eSuit.SPADES));
		a.AddCardToHand(new Card(eRank.TEN,eSuit.SPADES));
		a.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));
		a.AddCardToHand(new Card(eRank.KING,eSuit.SPADES));		
		a.EvaluateHand();
		
		assertEquals(eHandStrength.RoyalFlush.getHandStrength(), 
				a.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(0,a.getHandScore().getKickers().size());
		
	}
	
	@Test
	public void TestStraightFlush() {
		//Testing Straight Flush below		
		Hand a = new Hand();
		a.AddCardToHand(new Card(eRank.FIVE,eSuit.HEARTS));
		a.AddCardToHand(new Card(eRank.FOUR,eSuit.HEARTS));
		a.AddCardToHand(new Card(eRank.THREE,eSuit.HEARTS));
		a.AddCardToHand(new Card(eRank.TWO,eSuit.HEARTS));
		a.AddCardToHand(new Card(eRank.ACE,eSuit.HEARTS));		
		a.EvaluateHand();
		assertEquals(eHandStrength.StraightFlush.getHandStrength(), 
				a.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(0,a.getHandScore().getKickers().size());
	}
	
	@Test
	public void TestTwoPair() {
		//Testing Two Pair Below
		Hand c = new Hand();
		c.AddCardToHand(new Card(eRank.JACK,eSuit.SPADES));
		c.AddCardToHand(new Card(eRank.JACK,eSuit.HEARTS));
		c.AddCardToHand(new Card(eRank.FIVE,eSuit.DIAMONDS));
		c.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));
		c.AddCardToHand(new Card(eRank.ACE,eSuit.CLUBS));		
		c.EvaluateHand();
		
		assertEquals(eHandStrength.TwoPair.getHandStrength(), 
				c.getHandScore().getHandStrength().getHandStrength());

		assertEquals(eRank.JACK.getiRankNbr(),
				c.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(eRank.FIVE.getiRankNbr(),
				c.getHandScore().getLoHand().getiRankNbr());
		
		assertEquals(1, c.getHandScore().getKickers().size());
		
		Hand d = new Hand();
		d.AddCardToHand(new Card(eRank.ACE,eSuit.CLUBS));	
		d.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));
		d.AddCardToHand(new Card(eRank.JACK,eSuit.HEARTS));
		d.AddCardToHand(new Card(eRank.FIVE,eSuit.DIAMONDS));
		d.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));	
		d.EvaluateHand();
		
		assertEquals(eHandStrength.TwoPair.getHandStrength(), 
				d.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.ACE.getiRankNbr(),
				d.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(eRank.FIVE.getiRankNbr(),
				d.getHandScore().getLoHand().getiRankNbr());
		
		assertEquals(1,d.getHandScore().getKickers().size());
		
		Hand e = new Hand();
		e.AddCardToHand(new Card(eRank.ACE,eSuit.DIAMONDS));	
		e.AddCardToHand(new Card(eRank.ACE,eSuit.CLUBS));
		e.AddCardToHand(new Card(eRank.JACK,eSuit.SPADES));
		e.AddCardToHand(new Card(eRank.JACK,eSuit.DIAMONDS));
		e.AddCardToHand(new Card(eRank.FIVE,eSuit.CLUBS));	
		e.EvaluateHand();
		
		assertEquals(eHandStrength.TwoPair.getHandStrength(), 
				e.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.ACE.getiRankNbr(),
				e.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(eRank.JACK.getiRankNbr(),
				e.getHandScore().getLoHand().getiRankNbr());
		
		assertEquals(1,e.getHandScore().getKickers().size());
	}
		
	@Test
	public void TestThreeOfAKind() {
		//Below Testing Three of a kind 
		
		Hand f = new Hand();
		f.AddCardToHand(new Card(eRank.ACE,eSuit.DIAMONDS));	
		f.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));
		f.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));
		f.AddCardToHand(new Card(eRank.SEVEN,eSuit.HEARTS));
		f.AddCardToHand(new Card(eRank.TWO,eSuit.CLUBS));	
		f.EvaluateHand();
		
		assertEquals(eHandStrength.ThreeOfAKind.getHandStrength(), 
				f.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.ACE.getiRankNbr(),
				f.getHandScore().getHiHand().getiRankNbr());
		
		
		assertEquals(2,f.getHandScore().getKickers().size());
		
		Hand g = new Hand();
		g.AddCardToHand(new Card(eRank.SEVEN,eSuit.HEARTS));
		g.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		g.AddCardToHand(new Card(eRank.FOUR,eSuit.DIAMONDS));	
		g.AddCardToHand(new Card(eRank.FOUR,eSuit.SPADES));
		g.AddCardToHand(new Card(eRank.FOUR,eSuit.SPADES));	
		g.EvaluateHand();
		
		assertEquals(eHandStrength.ThreeOfAKind.getHandStrength(), 
				g.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.FOUR.getiRankNbr(),
				g.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(2,g.getHandScore().getKickers().size());
		
		Hand gg = new Hand();
		gg.AddCardToHand(new Card(eRank.SEVEN,eSuit.HEARTS));
		gg.AddCardToHand(new Card(eRank.FOUR,eSuit.CLUBS));
		gg.AddCardToHand(new Card(eRank.FOUR,eSuit.DIAMONDS));	
		gg.AddCardToHand(new Card(eRank.FOUR,eSuit.SPADES));
		gg.AddCardToHand(new Card(eRank.TWO,eSuit.SPADES));	
		gg.EvaluateHand();
		
		assertEquals(eHandStrength.ThreeOfAKind.getHandStrength(), 
				gg.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.FOUR.getiRankNbr(),
				gg.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(2,gg.getHandScore().getKickers().size());
	}
	
	@Test
	public void TestPair() {
		//Below Testing Pair
		Hand i = new Hand();
		i.AddCardToHand(new Card(eRank.ACE,eSuit.HEARTS));
		i.AddCardToHand(new Card(eRank.ACE,eSuit.CLUBS));
		i.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));	
		i.AddCardToHand(new Card(eRank.FOUR,eSuit.DIAMONDS));	
		i.AddCardToHand(new Card(eRank.TWO,eSuit.SPADES));
		i.EvaluateHand();
		
		assertEquals(eHandStrength.Pair.getHandStrength(), 
				i.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.ACE.getiRankNbr(),
				i.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(3,i.getHandScore().getKickers().size());
		
		Hand j = new Hand();
		j.AddCardToHand(new Card(eRank.ACE,eSuit.HEARTS));
		j.AddCardToHand(new Card(eRank.FIVE,eSuit.CLUBS));
		j.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));	
		j.AddCardToHand(new Card(eRank.FOUR,eSuit.DIAMONDS));	
		j.AddCardToHand(new Card(eRank.TWO,eSuit.SPADES));
		j.EvaluateHand();
		
		assertEquals(eHandStrength.Pair.getHandStrength(), 
				j.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.FIVE.getiRankNbr(),
				j.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(3,j.getHandScore().getKickers().size());
		
		Hand k = new Hand();
		k.AddCardToHand(new Card(eRank.ACE,eSuit.HEARTS));
		k.AddCardToHand(new Card(eRank.JACK,eSuit.CLUBS));
		k.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));	
		k.AddCardToHand(new Card(eRank.FIVE,eSuit.DIAMONDS));	
		k.AddCardToHand(new Card(eRank.TWO,eSuit.SPADES));
		k.EvaluateHand();
		
		assertEquals(eHandStrength.Pair.getHandStrength(), 
				k.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.FIVE.getiRankNbr(),
				k.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(3,k.getHandScore().getKickers().size());
		
		Hand l = new Hand();
		l.AddCardToHand(new Card(eRank.ACE,eSuit.HEARTS));
		l.AddCardToHand(new Card(eRank.JACK,eSuit.CLUBS));
		l.AddCardToHand(new Card(eRank.SEVEN,eSuit.SPADES));	
		l.AddCardToHand(new Card(eRank.FIVE,eSuit.DIAMONDS));	
		l.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));
		l.EvaluateHand();
		
		assertEquals(eHandStrength.Pair.getHandStrength(), 
				l.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.FIVE.getiRankNbr(),
				l.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(3,l.getHandScore().getKickers().size());
	}
	
	@Test
	public void TestAcesAndEights() {
		//Below Testing Aces and Eights
		
		Hand m = new Hand();
		m.AddCardToHand(new Card(eRank.EIGHT,eSuit.HEARTS));
		m.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		m.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));	
		m.AddCardToHand(new Card(eRank.FIVE,eSuit.DIAMONDS));	
		m.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));
		m.EvaluateHand();
		
		assertEquals(eHandStrength.AcesAndEights.getHandStrength(), 
				m.getHandScore().getHandStrength().getHandStrength());
		assertEquals(eRank.ACE.getiRankNbr(),
				m.getHandScore().getHiHand().getiRankNbr());
		assertEquals(eRank.EIGHT.getiRankNbr(),
				m.getHandScore().getLoHand().getiRankNbr());
		
		Hand mm = new Hand();
		mm.AddCardToHand(new Card(eRank.EIGHT,eSuit.HEARTS));
		mm.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		mm.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));	
		mm.AddCardToHand(new Card(eRank.NINE,eSuit.DIAMONDS));	
		mm.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));
		mm.EvaluateHand();
		
		assertEquals(eHandStrength.AcesAndEights.getHandStrength(), 
				mm.getHandScore().getHandStrength().getHandStrength());
		assertEquals(eRank.ACE.getiRankNbr(),
				mm.getHandScore().getHiHand().getiRankNbr());
		assertEquals(eRank.EIGHT.getiRankNbr(),
				mm.getHandScore().getLoHand().getiRankNbr());
		
	}
	
	@Test
	public void TestFlush() {
		//Below Testing Flush
		Hand n = new Hand();
		n.AddCardToHand(new Card(eRank.FOUR,eSuit.CLUBS));
		n.AddCardToHand(new Card(eRank.SIX,eSuit.CLUBS));
		n.AddCardToHand(new Card(eRank.TWO,eSuit.CLUBS));	
		n.AddCardToHand(new Card(eRank.NINE,eSuit.CLUBS));	
		n.AddCardToHand(new Card(eRank.EIGHT,eSuit.CLUBS));
		n.EvaluateHand();
		
		assertEquals(eHandStrength.Flush.getHandStrength(), 
				n.getHandScore().getHandStrength().getHandStrength());
		assertEquals(0,n.getHandScore().getKickers().size());
	}
	
	@Test
	public void TestStraight() 
	{
		//Below Testing Straight
		Hand o = new Hand();
		o.AddCardToHand(new Card(eRank.TWO,eSuit.HEARTS));
		o.AddCardToHand(new Card(eRank.THREE,eSuit.CLUBS));
		o.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));	
		o.AddCardToHand(new Card(eRank.FOUR,eSuit.CLUBS));	
		o.AddCardToHand(new Card(eRank.FIVE,eSuit.DIAMONDS));
		o.EvaluateHand();
		
		assertEquals(eHandStrength.Straight.getHandStrength(), 
				o.getHandScore().getHandStrength().getHandStrength());
		assertEquals(0,o.getHandScore().getKickers().size());
		
		Hand oo = new Hand();
		oo.AddCardToHand(new Card(eRank.TEN,eSuit.SPADES));
		oo.AddCardToHand(new Card(eRank.JACK,eSuit.HEARTS));
		oo.AddCardToHand(new Card(eRank.ACE,eSuit.CLUBS));	
		oo.AddCardToHand(new Card(eRank.KING,eSuit.HEARTS));	
		oo.AddCardToHand(new Card(eRank.QUEEN,eSuit.DIAMONDS));
		oo.EvaluateHand();
		
		assertEquals(eHandStrength.Straight.getHandStrength(), 
				oo.getHandScore().getHandStrength().getHandStrength());
		assertEquals(0,oo.getHandScore().getKickers().size());
		
		Hand ooo = new Hand();
		ooo.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));
		ooo.AddCardToHand(new Card(eRank.SIX,eSuit.HEARTS));
		ooo.AddCardToHand(new Card(eRank.SEVEN,eSuit.CLUBS));	
		ooo.AddCardToHand(new Card(eRank.EIGHT,eSuit.HEARTS));	
		ooo.AddCardToHand(new Card(eRank.NINE,eSuit.DIAMONDS));
		ooo.EvaluateHand();
		
		assertEquals(eHandStrength.Straight.getHandStrength(), 
				ooo.getHandScore().getHandStrength().getHandStrength());
		assertEquals(0,ooo.getHandScore().getKickers().size());
	}
	
	@Test
	public void TestFourOfAKind() 
	{
		//Below I am testing 4 of a kind
		Hand p = new Hand();
		p.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));
		p.AddCardToHand(new Card(eRank.FIVE,eSuit.HEARTS));
		p.AddCardToHand(new Card(eRank.FIVE,eSuit.CLUBS));	
		p.AddCardToHand(new Card(eRank.FIVE,eSuit.HEARTS));	
		p.AddCardToHand(new Card(eRank.NINE,eSuit.DIAMONDS));
		p.EvaluateHand();
		
		assertEquals(eHandStrength.FourOfAKind.getHandStrength(), 
				p.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.FIVE.getiRankNbr(),
				p.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(1,p.getHandScore().getKickers().size());
		
		Hand pp = new Hand();
		pp.AddCardToHand(new Card(eRank.FIVE,eSuit.SPADES));
		pp.AddCardToHand(new Card(eRank.FIVE,eSuit.HEARTS));
		pp.AddCardToHand(new Card(eRank.FIVE,eSuit.CLUBS));	
		pp.AddCardToHand(new Card(eRank.FIVE,eSuit.HEARTS));	
		pp.AddCardToHand(new Card(eRank.THREE,eSuit.DIAMONDS));
		pp.EvaluateHand();
		
		assertEquals(eHandStrength.FourOfAKind.getHandStrength(), 
				pp.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.FIVE.getiRankNbr(),
				pp.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(1,pp.getHandScore().getKickers().size());
	}
		
	@Test
		public void TestHighCard() 
		{
		//Below I am Testing High Card
		Hand q = new Hand();
		q.AddCardToHand(new Card(eRank.ACE,eSuit.SPADES));
		q.AddCardToHand(new Card(eRank.FIVE,eSuit.HEARTS));
		q.AddCardToHand(new Card(eRank.TWO,eSuit.CLUBS));	
		q.AddCardToHand(new Card(eRank.THREE,eSuit.HEARTS));	
		q.AddCardToHand(new Card(eRank.NINE,eSuit.DIAMONDS));
		q.EvaluateHand();
		
		assertEquals(eHandStrength.HighCard.getHandStrength(), 
				q.getHandScore().getHandStrength().getHandStrength());
		
		assertEquals(eRank.ACE.getiRankNbr(),
				q.getHandScore().getHiHand().getiRankNbr());
		
		assertEquals(4,q.getHandScore().getKickers().size());
		}
	}
