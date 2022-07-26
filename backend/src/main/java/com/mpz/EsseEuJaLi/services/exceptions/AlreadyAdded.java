package com.mpz.EsseEuJaLi.services.exceptions;

public class AlreadyAdded extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AlreadyAdded(String message) {
		super(message);
	}
}
