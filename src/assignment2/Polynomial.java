//Dafne Culha - 260785524 - A2
package assignment2;

import java.math.BigInteger;

public class Polynomial {

	private SLinkedList <Term> polynomial;

	public int size() {
		return polynomial.size();
	}

	private Polynomial(SLinkedList<Term> p) {
		polynomial = p;
	}

	public Polynomial() {
		polynomial = new SLinkedList<Term>();
	}

	// Returns a deep copy of the object.
	public Polynomial deepClone() {
		return new Polynomial(polynomial.deepClone());
	}

	/* 
	 * TODO: Add new term to the polynomial. Also ensure the polynomial is
	 * in decreasing order of exponent.
	 */

	public void addTerm(Term t) {

		if((t.getExponent() < 0) || (t.getCoefficient().equals(new BigInteger("0")))) {
			return;
		}
		int i = 0;

		if((polynomial.size() == 0)|| (getTerm(0).getExponent() <t.getExponent())) {
			polynomial.addFirst(t);
			return;
		}

		for(Term currentTerm: polynomial) {
			

			if(currentTerm.getExponent() == t.getExponent()){
				BigInteger coefficientSum = t.getCoefficient().add(currentTerm.getCoefficient());
				currentTerm.setCoefficient(coefficientSum);

				if (currentTerm.getCoefficient().equals(new BigInteger ("0"))) {
					polynomial.remove(i);
				}
				return;
			}

			else if(t.getExponent() > currentTerm.getExponent()) {
				polynomial.add(i, t);
				return;
			}
			i++;
		}
		polynomial.addLast(t);
	}
	
	/**** ADD CODE HERE ****/


	// Hint: Notice that the function SLinkedList.get(index) method is O(n), 
	// so if this method were to call the get(index) 
	// method n times then the method would be O(n^2).
	// Instead, use a Java enhanced for loop to iterate through 
	// the terms of an SLinkedList.

	/*  The for loop iterates over each term in the polynomial!!
	 *  Example: System.out.println(currentTerm.getExponent()) should print the exponents of each term in the polynomial when it is not empty.  
	 */

	public Term getTerm(int index) {

		return polynomial.get(index);
	}

	//TODO: Add two polynomial without modifying either

	/*public static Polynomial add (Polynomial p1, Polynomial p2) {

		// add first polynomial p1
		// start adding terms of second one p2
		for (int i = 0; i < p2.size() ; i++ )

			//	polynomial = p1;

			p1.addTerm (p2.getTerm(i));
		return p1;
	} */

	public static Polynomial add (Polynomial p1, Polynomial p2) {

		Polynomial poly1 = p1.deepClone();
		Polynomial poly2 = p2.deepClone();

		Polynomial pResult = new Polynomial();

		while (true) {
			if ((poly1.size() ==0)||(poly2.size() == 0)) break;

			Term t1 = poly1.getTerm(0);
			Term t2 = poly2.getTerm(0);

			if (t1.getExponent() < t2.getExponent()) {
				pResult.polynomial.addLast(t2);
				poly2.polynomial.remove(0);
			}

			else if (t1.getExponent() == t2.getExponent()) {
				BigInteger newCoef = (poly1.getTerm(0).getCoefficient()).add(poly2.getTerm(0).getCoefficient());
				int newExp = poly1.getTerm(0).getExponent();
				if (newCoef.equals(new BigInteger("0"))) {
					poly1.polynomial.remove(0);
					poly2.polynomial.remove(0);
				}
				else { // if newCoef != 0
					pResult.polynomial.addLast(new Term(newExp,newCoef));
					poly1.polynomial.remove(0);
					poly2.polynomial.remove(0);
				}
			}

			else if (t1.getExponent() > t2.getExponent()) {
				pResult.polynomial.addLast(t1);
				poly1.polynomial.remove(0);
			}
		}

		while (true) {
			if (poly1.size()==0) break;
			Term t = poly1.polynomial.get(0);
			pResult.polynomial.addLast(t);
			poly1.polynomial.remove(0);
		}
		while (true) {
			if (poly2.size()==0) break;
			Term t = poly2.polynomial.get(0);
			pResult.polynomial.addLast(t);
			poly2.polynomial.remove(0);
		}

		return pResult;
	}

	//TODO: multiply this polynomial by a given term.
	private void multiplyTerm(Term t) {

		/**** ADD CODE HERE ****/
		if (polynomial.size() !=0)  {
			//BigInteger newCoef = null;
			//int newPow;


			// add powers multiply coefficients	
			for (Term currentTerm: polynomial) {
				currentTerm.setCoefficient(currentTerm.getCoefficient().multiply(t.getCoefficient()));
				currentTerm.setExponent(t.getExponent() + currentTerm.getExponent());
				//System.out.print(polynomial);
			}
		}
	}

	//TODO: multiply two polynomials /////////////////////////
	
	public static Polynomial multiply(Polynomial p1, Polynomial p2)  {
		
		Polynomial polyMultiplied = new Polynomial();
		Polynomial p1Clone = p1.deepClone();
		Polynomial p2Clone = p2.deepClone();
		
		int k = 0;
		for (int i=0; i < p1Clone.size(); i++) {
			// multiply p2Clone with each term of p1Clone
			p2Clone.multiplyTerm(p1Clone.polynomial.get(k));
			// p2 Clone is now multiplied with p1. Add this to polyMultiplied.
			polyMultiplied = Polynomial.add(p2Clone, polyMultiplied);
			// Set p2Clone to be equal to p2 again.
			p2Clone = p2.deepClone();
			// Increase the index.
			k++;
		}
		return polyMultiplied;
	}
	
	/* public static Polynomial multiply(Polynomial p1, Polynomial p2) {

		Polynomial polyMultiplied = new Polynomial();
		Polynomial p1Clone = p1.deepClone();
		Polynomial p2Clone = p2.deepClone();

		for(int i = 0; i < p1.size(); i++){
			p2.multiplyTerm(p1.getTerm(i));

			polyMultiplied = add(polyMultiplied, p2);
			p2 = p2Clone;	
		}

		//System.out.println("Received:" + pMultiplied);
		return polyMultiplied;

	} 
	 */ 
	///////////////////////////////////
	/*
	 * public static Polynomial multiply(Polynomial p1, Polynomial p2) {

		Polynomial polyMultiplied = new Polynomial();
		Polynomial p1Clone = p1.deepClone();
		Polynomial p2Clone = p2.deepClone();

		while (true) {
			Term t1 = p1Clone.getTerm(0);
		
			for (Term currentTerm: p2.polynomial) {

				BigInteger newCoef = t1.getCoefficient().multiply(currentTerm.getCoefficient());
				int newExp = t1.getExponent() + currentTerm.getExponent();
				Term newTerm = new Term (newExp, newCoef);
				polyMultiplied.addTerm(newTerm);
			}
			//System.out.println("Received:" + polyMultiplied);
			p1Clone.polynomial.remove(0);
			if ((p1Clone.size()==0)) break;
		}
		return polyMultiplied;
	} */

	//TODO: evaluate this polynomial.
	// Hint:  The time complexity of eval() must be order O(m), 
	// where m is the largest degree of the polynomial. Notice 
	// that the function SLinkedList.get(index) method is O(m), 
	// so if your eval() method were to call the get(index) 
	// method m times then your eval method would be O(m^2).
	// Instead, use a Java enhanced for loop to iterate through 
	// the terms of an SLinkedList.

	public BigInteger eval(BigInteger x) {

		BigInteger result = new BigInteger ("0");
		if (this.size() !=0) {
			int power = this.getTerm(0).getExponent();

			for (Term currentTerm: polynomial) {
				int missing = power - currentTerm.getExponent();
				result = result.multiply(x.pow(missing)).add(currentTerm.getCoefficient());
				power = currentTerm.getExponent();
			}
			result = result.multiply(x.pow(power));
			return result;
		} 
		else {

			return result;
		}
	}

	// Checks if this polynomial is same as the polynomial in the argument

	public boolean checkEqual(Polynomial p) {
		if (polynomial == null || p.polynomial == null || size() != p.size())
			return false;
		int index = 0;
		for (Term term0 : polynomial) {
			Term term1 = p.getTerm(index);
			if (term0.getExponent() != term1.getExponent() ||
					term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)
				return false;
			index++;
		}
		return true;
	}

	// This method blindly adds a term to the end of LinkedList polynomial. 
	// Avoid using this method in your implementation as it is only used for testing.
	public void addTermLast(Term t) {
		polynomial.addLast(t);
	}

	// This is used for testing multiplyTerm
	public void multiplyTermTest(Term t) {
		multiplyTerm(t);
	}

	@Override
	public String toString() {
		if (polynomial.size() == 0) return "0";
		return polynomial.toString();
	}
}