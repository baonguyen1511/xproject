/**
 * 
 */
package com.xproject.demo.payload.response;

/**
 * @author bao.nguyentx
 *
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}