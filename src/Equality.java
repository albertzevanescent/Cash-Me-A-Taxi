/**
* Author: Albert Zhou
* Revised: 19/08/2020
* Description: Interface for equality.
*/ 

package src;

/**
* @brief An interface for equality.
*/

public interface Equality<T> {
	/**
	* @brief Check for equality.
	* @details Checks for equality of the same type.
	* @param a the other object.
	* @returns whether a is equal to self.
	*/
	public boolean eq(T a);
}
