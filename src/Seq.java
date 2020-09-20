/**
* Author: Albert Zhou
* Revised: 31/08/2020
* Description: Module for a generic sequence.
*/ 

package src;

import java.util.ArrayList;

/**
* @brief A module for a generic sequence.
*/
public class Seq<T>{
	
	private ArrayList<T> S;

    /**
    * @brief Create a sequence.
    * @details Sets S to empty.
    */
	public Seq(){
		this.S = new ArrayList<T>();
	}

	/**
    * @brief Append on to the sequence.
	* @details Add a to the end of the sequence.
	* @param a the object to add.
    */
	public void append(T a){
		this.S.add(a);
	}

	/**
    * @brief Remove the object somewhere in the sequence.
	* @details Remove the xth object in the sequence.
	* @param x the index of the object.
	* @throws IllegalArgumentException if x < 0 or x > size().
    */
	public void rm(int x){
        if (x < 0 || x >= this.size())
			throw new IllegalArgumentException("Index outside of sequence");
		this.S.remove(x);
	}

	/**
    * @brief Determine if an object is a member of the sequence.
	* @details Check if x is a member of the sequence.
	* @param x the object to check.
    */
	public boolean member(T x){
		for(int i = 0; i < this.size(); i++){
			if (this.S.get(i).equals(x))
				return true;
		}
		return false;
	}

	/**
    * @brief Get the size of the sequence.
	* @details Find the size of the sequence.
	* @return s the size of the sequence.
    */
	public int size(){
		return this.S.size();
	}

	/**
    * @brief Get an object in the sequence.
	* @details Get the xth object of the sequence.
	* @param x the index of the object.
	* @return o the object.
	* @throws IllegalArgumentException if x < 0 or x > size().
    */
	public T get(int x){
		if (x < 0 || x >= this.size())
			throw new IllegalArgumentException("Index outside of sequence");
		return this.S.get(x);
	}

	/**
    * @brief Find an object in the sequence.
	* @details Find x in the sequence.
	* @param x the object to find.
	* @return i the index of the object. If not found return -1.
    */
	public int find(T x){
		for(int i = 0; i < this.size(); i++){
			// if (x instanceof Node){
			// 	System.out.println("aaa   " + this.S.get(i) + " " + x);
			// 	System.out.println("aaa   " + this.S.get(i).getId() + " " + x.getId());
			// 	System.out.println("bbb   " + this.S.get(i).equals(x));
			// }

			if (this.S.get(i).equals(x))
				return i;
		}
		return -1;
	}

	/**
    * @brief Determine the count of an object in the sequence.
	* @details Determine how many times x appears in the sequence.
	* @param x the object to count.
	* @return c the number of times x appears in the sequence..
    */
	private int count(T x){
		int c = 0;
		for(int i = 0; i < this.size(); i++){
			if (this.S.get(i).equals(x))
				c++;
		}
		return c;
	}

	/**
	* @brief Check for equality.
	* @details Checks for equality by contents and order.
	* @param a the other Seq.
	* @returns whether a is equal to self.
	*/
	public boolean equals(Seq<T> a){
		for(int i = 0; i < this.size(); i++){
			if (!(this.S.get(i).equals(a.get(i)))) 
				return false;
		}
		return this.size() == a.size();
	}

	public void print(){
		for (int i = 0; i < this.size(); i++) {
			System.out.println(this.S.get(i));
		}
		
	}

	public static void main(String[] args) {

		// Seq a = new Seq();
		// System.out.println(a.S);
		// a.append(new Node(1));
		// a.append(new Node(2));
		// a.append(new Node(3));

		// System.out.println(a.S);
		// a.rm(1);
		// System.out.println(a.S);
		// System.out.println(a.member(new Node(2)));
		// System.out.println(a.get(1));

		// Seq b = new Seq();
		// b.append(new Node(1));
		// b.append(new Node(3));

		// System.out.println(a.eq(b));
		// System.out.println(a.find(new Node(3)));

		// Node x = new Node(1);
		// Node y = new Node(2);
		// Node z = new Node(3);

		// System.out.println(x+ " " +y+" "+z);

	}

}
