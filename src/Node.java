/**
* Author: Albert Zhou
* Revised: 1/09/2020
* Description: Module for a node.
*/ 

package src;

/**
 * @brief A module for a node.
 */
public class Node{
	
	private int id;

    /**
    * @brief Create a node.
    * @details Sets id to x.
	* @param x the node id.
    */
	public Node(int x){
		this.id = x;
	}

    /**
    * @brief Get id.
    * @details Returns the id value.
    * @return id the node id.
    */
	public int getId(){
		return this.id;
	}

	/**
	* @brief Check for equality.
	* @details Checks for equality by id.
	* @param n the other Node.
	* @returns whether a is equal to self.
	*/
	public boolean equals(Object n){
		if (n == null || n.getClass() != this.getClass())
			return false;
		Node n2 = (Node) n;
		return this.id == n2.getId();
    }
    
    public String toString(){
		return Integer.toString(this.id);
	}

}
