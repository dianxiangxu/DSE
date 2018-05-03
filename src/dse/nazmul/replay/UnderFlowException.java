/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dse.nazmul.replay;

public class UnderFlowException extends RuntimeException
{
    /**
     * Construct this exception object.
     * @param message the error message.
     */
    public UnderFlowException( String message )
    {
        super( message );
    }
}
