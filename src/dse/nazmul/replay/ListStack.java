/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

public class ListStack implements Stack
{
    /**
     * Construct the stack.
     */
    public ListStack( )
    {
        topOfStack = null;
    }

    /**
     * Test if the stack is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return topOfStack == null;
    }

    /**
     * Make the stack logically empty.
     */
    public void makeEmpty( )
    {
        topOfStack = null;
    }

    /**
     * Insert a new item into the stack.
     * @param x the item to insert.
     */
    public void push( Object x )
    {
        topOfStack = new ListNode( x, topOfStack );
    }

    /**
     * Remove the most recently inserted item from the stack.
     * @throws UnderflowException if the stack is empty.
     */
    public void pop( )
    {
        if( isEmpty( ) )
            throw new UnderFlowException( "ListStack pop" );
        topOfStack = topOfStack.next;
    }

    /**
     * Get the most recently inserted item in the stack.
     * Does not alter the stack.
     * @return the most recently inserted item in the stack.
     * @throws UnderflowException if the stack is empty.
     */
    public Object top( )
    {
        if( isEmpty( ) )
            throw new UnderFlowException( "ListStack top" );
        return topOfStack.element;
    }

    /**
     * Return and remove the most recently inserted item
     * from the stack.
     * @return the most recently inserted item in the stack.
     * @throws UnderflowException if the stack is empty.
     */
    public Object topAndPop( )
    {
        if( isEmpty( ) )
            throw new UnderFlowException( "ListStack topAndPop" );

        Object topItem = topOfStack.element;
        topOfStack = topOfStack.next;
        return topItem;
    }

    private ListNode topOfStack;
}

class ListNode
{
      // Constructors
    public ListNode( Object theElement )
    {
        this( theElement, null );
    }

    public ListNode( Object theElement, ListNode n )
    {
        element = theElement;
        next    = n;
    }

    public Object   element;
    public ListNode next;
}