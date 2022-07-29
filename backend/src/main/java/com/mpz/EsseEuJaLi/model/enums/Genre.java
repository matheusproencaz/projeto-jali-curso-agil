package com.mpz.EsseEuJaLi.model.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Genre {

	Acao(1, "Ação"),
	Aventura(2, "Aventura"),
	Classico(3, "Clássico"),
	Quadrinhos(4, "Quadrinhos"),
	Misterio(5, "Mistério"),
	Fantasia(6, "Fantasia"),
	Historico(7, "Histórico"),
	Romance(8, "Romance"),
	Biografia(9, "Biografia"),
	Comida(10, "Comida"), 	
	Drama(11, "Drama"),
	Terror(12, "Terror"),
	Poesia(13, "Poesia"),
	FiccaoCientifica(14, "Ficção científica"),
	Politica(15, "Política");
	
	private int cod;
	private String description;
	
	private static List<String> descriptions = new ArrayList<>(Arrays.asList("Ação", "Aventura", "Clássico", "Quadrinhos", "Mistério",
			"Fantasia", "Histórico", "Romance", "Biografia", "Comida", "Drama", "Terror",  "Poesia", "Ficção científica", "Política"));
	
	private Genre(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	public static Genre toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(Genre x : Genre.values()) {
			if(cod.equals(x.getCod())){
				return x;
			}
		}
		throw new IllegalArgumentException("Id Inválido: " + cod);
	}
	
	public int getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}
	
	public static List<String> getDescriptions() {
		return descriptions;
	}
}
