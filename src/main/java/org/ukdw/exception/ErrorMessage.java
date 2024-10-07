package org.ukdw.exception;

import lombok.Data;

/**
 * Creator: dendy
 * Date: 6/30/2020
 * Time: 7:59 AM
 * Description : Error message POJO
 */

@Data
public class ErrorMessage {

	private String status;
	private String message;

	/**
	 * @param status
	 * @param message
	 */
	public ErrorMessage(String status, String message) {
		this.status = status;
		this.message = message;
	}
}
